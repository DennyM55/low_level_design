# Singleton Design Pattern

## One-Liner Definition

```text
One class = One object = One shared instance for the lifetime of the application.
```

Singleton is a creational design pattern that restricts a class to a single instance and provides a global, controlled access point to that instance.

---

## Why Singleton Exists — The Real Motivation

Consider an application that sends notifications — email, SMS, and push — to millions of users.

Every time a notification goes out, the system needs to look up user preferences and channel configuration. Those lookups hit an external data store:
- Connection setup to the store is slow.
- Fetching and deserializing the configuration adds latency.
- Opening a fresh connection for every notification would exhaust the connection pool within seconds.

**The fix:** Load the configuration once on application startup, hold it in memory, and share a single object everywhere it is needed.

```java
public class NotificationConfig {
    private static NotificationConfig INSTANCE;

    private final Map<String, String> channelSettings;
    private final Set<String> blockedUsers;

    private NotificationConfig() {
        // expensive: connect to config store, fetch and parse settings
        this.channelSettings = loadChannelSettings();
        this.blockedUsers    = loadBlockedUserList();
    }

    public static NotificationConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NotificationConfig();
        }
        return INSTANCE;
    }

    public String getSetting(String key)      { return channelSettings.get(key); }
    public boolean isBlocked(String userId)   { return blockedUsers.contains(userId); }
}
```

Now the expensive initialization happens only once, on the first call to `getInstance()`. Every subsequent call returns the already-loaded object. A notification sender uses it like this:

```java
class EmailSender {
    void send(String userId, String message) {
        if (NotificationConfig.getInstance().isBlocked(userId)) return;
        // send email...
    }
}
```

This keeps initialization cost to one-time while keeping the code clean and testable.

---

## Core Idea

| Mechanism | How |
|---|---|
| Prevent multiple instances | Make the constructor `private` |
| Create one instance | Use a `static` field inside the class |
| Provide access | Expose a `public static getInstance()` method |

The constructor being private means no outside code can write `new NotificationConfig()`. The only way to get an instance is through `getInstance()`, and that method controls how many instances exist.

---

## Real-World Uses

| Scenario | Why Singleton |
|---|---|
| Database connection pool | One pool shared by all threads |
| Application configuration | One place holding all app properties |
| Logger | One shared writer to avoid interleaved output |
| In-memory cache | Single coordinator for all cached data |
| Spring beans | Singleton scope is the default in Spring |

---

## Java Implementations

### 1. Eager Initialization

The instance is created when the class is first loaded by the JVM. This is the simplest approach.

```java
public class Singleton {
    private static final Singleton INSTANCE = new Singleton();

    private Singleton() {}

    public static Singleton getInstance() {
        return INSTANCE;
    }
}
```

**Pros:** Simple. Thread-safe because JVM class loading is inherently thread-safe.
**Con:** The object is created even if it is never used.

---

### 2. Lazy Initialization

The instance is created only when `getInstance()` is called for the first time.

```java
public class Singleton {
    private static Singleton instance;

    private Singleton() {}

    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
```

**Problem:** This is not thread-safe. Two threads can both enter the `if (instance == null)` block simultaneously and create two separate instances.

---

### 3. Thread-Safe with Double-Checked Locking

This is the most commonly expected implementation in interviews when lazy loading and thread safety are both required.

```java
public class Singleton {
    private static volatile Singleton instance;

    private Singleton() {}

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
```

**Why `volatile`?** Without it, the JVM may reorder instructions: it could assign the reference before finishing initialization. The `volatile` keyword prevents instruction reordering and ensures all threads see the fully constructed object.

**Why two null checks?** The outer check avoids locking once the instance exists (performance). The inner check handles the race condition between the outer check and acquiring the lock.

---

### 4. Bill Pugh Singleton (Initialization-on-Demand Holder)

Clean, lazy, and thread-safe without explicit synchronization.

```java
public class Singleton {
    private Singleton() {}

    private static class Holder {
        private static final Singleton INSTANCE = new Singleton();
    }

    public static Singleton getInstance() {
        return Holder.INSTANCE;
    }
}
```

**Why this works:** The `Holder` inner class is only loaded by the JVM when `getInstance()` is called for the first time. JVM class initialization is guaranteed to be thread-safe. No manual synchronization needed.

This is considered the cleanest production-quality Singleton implementation in Java.

---

## Stateful vs Stateless

Understanding this contrast helps clarify when Singleton is useful.

```java
// Stateful — the object holds data between calls
public class Counter {
    private int count = 0;
    public void increment() { count++; }
    public int get() { return count; }
}
Counter c = new Counter();
c.increment();

// Stateless — no data stored, static method works fine
public class MathHelper {
    public static int square(int n) { return n * n; }
}
int result = MathHelper.square(5);
```

Stateless utility classes (`Math.max`, `Collections.sort`) do not benefit from Singleton. Singleton is valuable when the object needs to hold state — like a loaded configuration set or an active connection pool.

---

## This Folder's Example

`ApplicationLogger.java` demonstrates eager Singleton:

- `private static final ApplicationLogger INSTANCE` creates one shared logger at class load time.
- `private ApplicationLogger()` prevents direct construction from outside.
- `public static ApplicationLogger getInstance()` is the single access point.
- `log(String message)` prints messages with a timestamp.

`Main.java` creates two variables via `getInstance()`, writes logs from both, and confirms they reference the same object.

```java
ApplicationLogger l1 = ApplicationLogger.getInstance();
ApplicationLogger l2 = ApplicationLogger.getInstance();

System.out.println(l1 == l2); // true — same object
```

---

## Interview Checklist

| Question | Answer |
|---|---|
| Why private constructor? | Prevents `new Singleton()` from outside code |
| How to prevent reflection breaking Singleton? | Add a guard in the constructor: if instance already exists, throw an exception |
| How to prevent cloning? | Override `clone()` and throw `CloneNotSupportedException` |
| How to prevent deserialization creating a new instance? | Override `readResolve()` and return the existing instance |
| Eager vs lazy — when to use which? | Eager when the instance is always needed; lazy when creation is expensive and the instance may not be needed |
| Best implementation for interviews? | Bill Pugh (cleanest); double-checked locking (shows threading knowledge) |

---

## Connection to Other Patterns

Singleton is frequently combined with other patterns:

- **Factory + Singleton:** A factory that maps type strings to concrete objects is expensive to build — create it once and share it.
- **Chain of Responsibility + Singleton:** The factory that configures and returns handler chains is a Singleton.
- **Spring beans:** `@Service` and `@Repository` beans are Singleton-scoped by default in Spring's IoC container.

---

## Run

From the `src` directory:

```bash
javac _02_singleton/ApplicationLogger.java _02_singleton/Main.java
java _02_singleton.Main
```

---

## Expected Output

```text
[LOG] 2026-06-15T22:30:00 | User service started
[LOG] 2026-06-15T22:30:00 | Payment service started
Same logger instance: true
First logger hash code:  123456789
Second logger hash code: 123456789
```

Both hash codes are the same, confirming a single shared instance.
