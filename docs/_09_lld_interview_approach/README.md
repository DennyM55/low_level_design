# LLD Interview Approach

Low Level Design interviews test your ability to translate a real-world problem into clean, object-oriented code.

The most important thing to understand: **LLD and algorithm questions are fundamentally different.** Algorithm problems are deterministic — there is one correct answer. LLD problems are open-ended. The question is short, but you are expected to ask a lot of clarifying questions before drawing or coding anything.

---

## What Is Low Level Design?

LLD sits between the code and the high-level architecture diagram.

- **High Level Design (HLD):** Very abstract. Shows components and how data flows between them. Does not say which classes exist or how the database looks.
- **Low Level Design (LLD):** Closer to the code, but still above the implementation level. Talks about classes, their attributes, their behavior, and how they relate — but does not implement every line.
- **Code:** The actual implementation.

Think of a function `add(a: int, b: int) -> int`. LLD tells you what the function signature looks like and what it returns. It does not show the implementation. LLD is the skeleton; code is the flesh.

---

## The 5-Step Interview Framework

### Step 1 — Gather Requirements and Use Cases (3–5 minutes)

**Do not start drawing. Do not start coding. Ask questions first.**

Identify:
- **Functional requirements:** What should the system do?
- **Non-functional requirements:** Any scale, concurrency, or consistency concerns?
- **Scope:** What is in scope? What is explicitly out of scope?
- **Actors:** Who uses the system?
- **Use cases:** What can each actor do?

Sample questions:
```text
"Is this a multi-floor parking lot or a single floor?"
"Should we handle concurrent seat booking?"
"Do we support cancellations and refunds?"
"Who are the actors — customer, admin, receptionist?"
"Are discounts and coupons in scope?"
```

Writing requirements on a whiteboard before drawing shows structure. It also protects you — if requirements change mid-interview, you can refer back to what was agreed.

> Entities are usually nouns. Verbs usually describe behavior (methods), not entities.

---

### Step 2 — Identify Entities (5 minutes)

List all the nouns in the requirements. These become your candidate classes.

For a Coffee Machine problem:
```text
Beverage, Ingredient, Recipe, Stock, Menu, User, CoffeeMachine
```

Then categorize:
- **Main entities** — have state and behavior (class)
- **Value objects** — just data, no identity (often a simple POJO or record)
- **Enums** — fixed set of values (status, type, category)
- **Interfaces** — abstractions for behavior that can vary

---

### Step 3 — Identify Relationships

For each pair of entities:

| Question | If Yes |
|---|---|
| Does A own B and B cannot exist without A? | Composition (strong has-a) |
| Does A have B but B can exist independently? | Aggregation (weak has-a) |
| Is A a type of B? | Inheritance (is-a) |
| Does A use B temporarily (method param)? | Dependency |

**Direction of arrows matters.** `Member → Question` means Member has a reference to Question. The arrow points from the owner/user to the owned/used class.

---

### Step 4 — Draw the Class Diagram

Sketch classes with:
- Key fields (not every getter/setter)
- Key methods (behavior that matters)
- Relationships with multiplicity

Points that impress interviewers:
- Interfaces for important abstractions (`Search`, `PaymentGateway`, `NotificationService`)
- Enums for status and type fields
- Builder for complex object construction
- Factory for type selection
- Strategy for interchangeable algorithms

---

### Step 5 — Write Clean Code

| What to check | Expected |
|---|---|
| One class per file | Yes |
| Interfaces for key abstractions | Yes |
| Enums, not strings for status/type | Yes |
| Constructor or Builder for required fields | Yes |
| Single responsibility per class | Yes |
| Error handling — IllegalArgumentException, NullCheck | Yes |
| Mention design patterns used and why | Yes |

---

## Worked Example — Coffee Machine

This is a good example of how to think through an LLD problem from scratch.

### Problem Statement

> Design a coffee machine that makes different beverages based on set ingredients. Recipes are hard-coded but easy to update. The machine shows ingredient stock and the menu on startup. Drink cost is determined by ingredient combinations. If a drink is out of stock it says so. User selects a drink by number, presses R to restock, Q to quit.

### Step 1 — Use Cases

- Display menu (beverages and prices) on startup
- Display ingredient stock
- User selects a beverage by number
- Machine checks if ingredients are available
- Machine dispenses the drink and deducts ingredients
- User presses R to restock all ingredients
- User presses Q to quit

### Step 2 — Identify Entities

```
CoffeeMachine, Beverage, Ingredient, Recipe, Stock, Menu, User
```

### Step 3 — Relationships

```
Beverage has-a Recipe               (composition)
Recipe has-many Ingredient          (composition)
Stock has-many Ingredient           (composition)
Menu has-many Beverage              (composition)
User interacts with CoffeeMachine   (dependency)
```

### Step 4 — Class Sketch

```
Ingredient
  - name: String
  - pricePerUnit: double
  - quantity: int

Recipe
  - ingredients: Map<Ingredient, Integer>  // ingredient → units needed

Beverage
  - name: String
  - recipe: Recipe
  + isAvailable(stock): boolean
  + cost(): double

Stock
  - ingredients: Map<String, Ingredient>
  + hasEnough(recipe): boolean
  + deduct(recipe): void
  + restock(): void

Menu
  - beverages: List<Beverage>  // sorted alphabetically
  + display(stock): void

CoffeeMachine
  - menu: Menu
  - stock: Stock
  + start(): void
  + handleInput(input): void
```

---

## Common Patterns and Where They Appear

| LLD Problem | Patterns Likely Needed |
|---|---|
| Parking Lot | Factory (vehicle→slot type), Singleton (lot), Strategy (fee) |
| BookMyShow | Builder (booking), Factory (payment), Observer (confirmation) |
| Splitwise | Strategy (split type), Command (undo settle) |
| Chess / TicTacToe | Command, State, Template Method |
| Google Calendar | Observer (reminders), Builder (event) |
| Elevator System | State, Strategy (dispatch) |
| Vending Machine | State |
| ATM | State, Chain of Responsibility (auth steps) |
| Hotel Management | Builder, Factory, Observer |
| StackOverflow | Composite (Q+Answers+Comments), Observer |

---

## Red Flags to Avoid

| Mistake | Better Approach |
|---|---|
| Putting everything in one God class | Split by single responsibility |
| Using `String` for type or status | Use enums |
| Mixing creation logic with business logic | Use Factory or Builder |
| `new ConcreteClass()` inside business logic | Use Factory or dependency injection |
| No interfaces for things that might change | Program to interfaces |
| Hardcoded values (prices, limits) | Constants or configuration |
| Starting to code before clarifying | Always clarify requirements first |

---

## What Interviewers Look For

| Skill | Signal |
|---|---|
| Requirements gathering | You ask the right clarifying questions |
| Object thinking | You see the system as objects, not functions |
| SOLID principles | Design stays clean as features are added |
| Pattern knowledge | You recognize when a pattern fits and apply it correctly |
| Code quality | Readable, consistent, minimal duplication |
| Communication | You explain decisions while drawing and coding |

---

## Interview Time Allocation (45-minute round)

| Phase | Time |
|---|---|
| Clarify requirements and use cases | 5 min |
| Identify entities and relationships | 5 min |
| Draw class diagram | 10 min |
| Write core code | 20 min |
| Walk through with interviewer | 5 min |

---

## Pattern Selection Quick Reference

```
Need to create objects of different types?
  → Factory Pattern

Object construction has many optional fields?
  → Builder Pattern

Need one shared instance app-wide?
  → Singleton Pattern

Want to clone an existing object?
  → Prototype Pattern

Want to swap an algorithm at runtime?
  → Strategy Pattern

Want to notify multiple listeners of a change?
  → Observer Pattern

Want to wrap an object with new behavior?
  → Decorator Pattern

Need to make an incompatible interface work?
  → Adapter Pattern

Need to run a request through a sequence of handlers?
  → Chain of Responsibility Pattern

Object behavior depends on its internal state?
  → State Pattern

Multiple steps that must run in a fixed order with some steps customizable?
  → Template Method Pattern
```

---

## Quick Reference — SOLID

| Principle | One-line reminder |
|---|---|
| Single Responsibility | One class, one reason to change |
| Open/Closed | Add via new classes, not by editing old ones |
| Liskov Substitution | Child should work wherever parent is expected |
| Interface Segregation | Small focused interfaces, not fat ones |
| Dependency Inversion | Depend on abstractions, not concrete classes |

---

## Key Takeaway

The best LLD answers are structured, not rushed.

**Requirements → Entities → Relationships → Diagram → Code**

Every step builds on the previous one. Skipping requirements gathering is the most common mistake and the one that is hardest to recover from mid-interview.
