# Dependency Inversion Principle (DIP)

**Package:** `com.denny.lld.fundamentals.solid`

## Simple Meaning

High-level business logic should depend on abstractions, not on concrete low-level classes.

That makes the code easier to test, easier to replace, and less tightly coupled.

---

## What This Demo Shows

This example uses order placement to show why business logic should talk to interfaces.

Important types:

- `Order` — simple order data
- `OrderRepository` — abstraction for saving orders
- `Notifier` — abstraction for notifying the customer
- `MySqlOrderRepository` — concrete repository implementation
- `EmailNotifier` — concrete notification implementation
- `OrderService` — high-level business logic

`OrderService` does not create the concrete classes by itself. It receives abstractions and works through them.

---

## Why This Is Better

If `OrderService` directly creates `MySqlOrderRepository` and `EmailNotifier`, then changing the database or notification system would require editing the business logic.

That creates tight coupling.

By depending on interfaces, we can swap implementations without changing `OrderService`.

---

## Code Walkthrough

In `Main.java`:

1. Concrete classes are created outside the service.
2. The service receives `OrderRepository` and `Notifier` as constructor arguments.
3. `placeOrder()` saves the order and sends a notification.

This is dependency inversion in action: the core business class depends on interfaces.

---

## Interview Explanation

You can say:

> Dependency Inversion Principle means high-level business classes should depend on abstractions so database, API, email, or payment implementations can be replaced without changing core logic.

---

## Run

From `src`, run:

```bat
cd com.denny.lld.fundamentals.solid\_05_d_dependency_inversion
javac Main.java
java com.denny.lld.fundamentals.solid._05_d_dependency_inversion.Main
```
