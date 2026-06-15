# Chain of Responsibility / Handler Pipeline Pattern

## Simple Meaning

Chain of Responsibility passes a request through a sequence of handlers. Each handler either processes the request or passes it to the next one. This enables fail-fast validation and flexible, configurable processing pipelines.

---

## Why It Exists — The Real Motivation

Consider verifying a new user registration. Before accepting an account, the system needs to check several things:

- Is the username syntactically valid (length, allowed characters)?
- Is the email address well-formed?
- Is the username already taken?
- Is the email domain on the banned list?

These checks vary in cost:

```
cost(syntax check) < cost(format check) < cost(uniqueness DB lookup) < cost(domain ban list)
```

**Key observation:** Most rejection happens at the cheapest, earliest checks. If the username is too short, there is no point querying the database for uniqueness.

This is the **fail-fast** principle — run the cheapest, most likely failure check first. Skip expensive work as soon as an early check fails. It is the same idea as pruning in search algorithms: avoid exploring branches that cannot succeed.

---

## Step 1 — Naive CombinedChecker

```java
public class RegistrationChecker {
    private static SyntaxChecker    syntax  = new SyntaxChecker();
    private static FormatChecker    format  = new FormatChecker();
    private static UniquenessChecker unique = new UniquenessChecker();
    private static DomainChecker    domain  = new DomainChecker();

    boolean check(RegistrationRequest req) {
        return syntax.check(req)
            && format.check(req)
            && unique.check(req)
            && domain.check(req);
    }
}
```

This already gives us fail-fast: `&&` short-circuits — if syntax fails, uniqueness and domain checks are skipped.

**Problem:** The class is hardcoded. Cannot change the order, add a handler, or remove one without editing this class.

---

## Step 2 — Code Against an Interface (Runtime Polymorphism)

```java
public interface RequestHandler {
    boolean handle(RegistrationRequest req);
}

public class SyntaxChecker implements RequestHandler {
    public boolean handle(RegistrationRequest req) { /* check length/chars */ return true; }
}

public class FormatChecker implements RequestHandler {
    public boolean handle(RegistrationRequest req) { /* check email format */ return true; }
}
```

Now all handlers have a common type.

---

## Step 3 — The Pipeline (Chain)

Instead of hardcoding `&&`, use a list and a loop:

```java
public class HandlerPipeline implements RequestHandler {
    private List<RequestHandler> handlers;

    public HandlerPipeline(List<RequestHandler> handlers) {
        this.handlers = handlers;
    }

    public boolean handle(RegistrationRequest req) {
        for (RequestHandler h : handlers) {
            if (!h.handle(req)) return false;   // fail fast
        }
        return true;
    }
}
```

**Why a List and not a Set?** Order matters. We want syntax first (cheapest), then format, then uniqueness (database), then domain ban (most expensive). A Set has no defined order.

Notice: `HandlerPipeline` itself implements `RequestHandler`. This means a pipeline can contain other pipelines — supporting nested chains.

---

## Step 4 — HandlerPipelineFactory (Singleton + Factory)

Different contexts (different API endpoints) need different combinations of handlers.

- `quick_signup` needs: syntax → format only (fast path)
- `full_signup` needs: syntax → format → uniqueness → domain ban
- `admin_create` needs: syntax → uniqueness only

Creating all these handler objects on every request is expensive. Build them once and store them — this calls for the **Singleton + Factory** combination.

```java
public class HandlerPipelineFactory {
    private static HandlerPipelineFactory INSTANCE;
    private Map<String, RequestHandler> pipelines;

    private HandlerPipelineFactory() {
        pipelines = new HashMap<>();

        pipelines.put("quick_signup", new HandlerPipeline(
            Arrays.asList(new SyntaxChecker(), new FormatChecker())
        ));

        pipelines.put("full_signup", new HandlerPipeline(
            Arrays.asList(
                new SyntaxChecker(),
                new FormatChecker(),
                new UniquenessChecker(),
                new DomainChecker()
            )
        ));

        pipelines.put("admin_create", new HandlerPipeline(
            Arrays.asList(new SyntaxChecker(), new UniquenessChecker())
        ));
    }

    public static HandlerPipelineFactory getInstance() {
        if (INSTANCE == null) {
            synchronized (HandlerPipelineFactory.class) {
                if (INSTANCE == null) INSTANCE = new HandlerPipelineFactory();
            }
        }
        return INSTANCE;
    }

    public RequestHandler getPipeline(String context) {
        if (!pipelines.containsKey(context))
            throw new IllegalArgumentException("No pipeline configured for: " + context);
        return pipelines.get(context);
    }
}
```

---

## Client Code

```java
public class RegistrationController {
    public boolean register(RegistrationRequest req, String signupContext) {
        return HandlerPipelineFactory.getInstance()
                                     .getPipeline(signupContext)
                                     .handle(req);
    }
}
```

The controller knows:
1. There is a `RequestHandler` interface.
2. There is a factory that gives one.

The controller does not know which handlers run, in what order, or how many. That is entirely inside the factory configuration.

---

## This Folder's Example

The demo uses **address validation** to illustrate the same concept with concrete Java code:

1. `Validator` interface — the common contract.
2. `ZipValidator`, `StateValidator`, `CityValidator`, `LinesValidator` — individual handlers.
3. `ValidatorCascade` — chains validators in order, fails fast on the first rejection.
4. `ValidatorCascadeFactory` — Singleton + Factory that registers context-specific chains.
5. `Main` — demonstrates running different chains on the same address.

**Why this specific example:** Address validation makes the fail-fast ordering intuitive — zip code failures are far more common than city/state failures, so checking zip first saves the most work.

---

## Three Patterns Combined

This solution elegantly uses three patterns together:

| Pattern | Where | Why |
|---|---|---|
| **Chain of Responsibility** | `ValidatorCascade` iterates handlers | Decouple sender from receivers; fail-fast ordering |
| **Factory** | `ValidatorCascadeFactory.getValidator()` | Centralize the mapping from context → chain |
| **Singleton** | `ValidatorCascadeFactory.getInstance()` | Build the expensive mapping only once |

---

## Pipeline vs Simple If/Else Chain

| | If/Else Chain | Pipeline Pattern |
|---|---|---|
| Adding a handler | Edit the combined class | Add to the list in factory config |
| Changing order | Edit the combined class | Reorder in the list |
| Different combos per context | Not supported cleanly | One `Map<context, Handler>` entry per context |
| Nesting chains | Not supported | Pipelines can contain other pipelines |
| Open/Closed | Violates OCP | Follows OCP |

---

## Transformation Pipelines (Variation)

The same pattern applies to **transforming** data, not just validating it. For transformations:

- All steps must run (you cannot fail fast — each step transforms the input for the next).
- A `Set` could work for independent transformations; a `List` is needed when order matters.
- Example transformation pipeline: strip whitespace → normalize case → replace abbreviations → reformat postal code.

---

## Class Diagram

```
Validator (interface)
  + validate(Address): boolean

ZipValidator   implements Validator
StateValidator implements Validator
CityValidator  implements Validator
LinesValidator implements Validator

ValidatorCascade implements Validator
  - validators: List<Validator>
  + validate(Address): boolean   // loops through list, fail-fast

ValidatorCascadeFactory (Singleton)
  - INSTANCE: ValidatorCascadeFactory
  - mapping: Map<String, Validator>
  + getInstance(): ValidatorCascadeFactory
  + getValidator(context): Validator
```

---

## When to Use Chain of Responsibility

- A request needs to pass through multiple processing steps.
- The set of steps, or their order, may vary by context.
- Each step is independent and should not know about the others.
- Fail-fast behavior is desirable (stop as soon as one handler rejects the request).
- You are implementing middleware, filter pipelines, authentication chains, or validation chains.

---

## Real-World Examples

| Context | Chain Used |
|---|---|
| Web request filters | Authentication → authorization → rate limiting → logging |
| ATM authentication | Card present → PIN check → account active → withdrawal limit |
| Logging frameworks | DEBUG → INFO → WARN → ERROR level handlers |
| E-commerce checkout | Cart check → inventory check → payment → stock deduction |
| Java Servlet filters | `javax.servlet.Filter` chain |

---

## Run

From the `src` directory:

```bash
javac _21_chain_of_responsibility/Validator.java \
      _21_chain_of_responsibility/ZipValidator.java \
      _21_chain_of_responsibility/StateValidator.java \
      _21_chain_of_responsibility/CityValidator.java \
      _21_chain_of_responsibility/LinesValidator.java \
      _21_chain_of_responsibility/ValidatorCascade.java \
      _21_chain_of_responsibility/ValidatorCascadeFactory.java \
      _21_chain_of_responsibility/Address.java \
      _21_chain_of_responsibility/Main.java
java _21_chain_of_responsibility.Main
```
