# SOLID Principles — Low Level Design

This folder contains notes and Java examples for the five SOLID design principles.

The goal is not to memorise definitions. The goal is to understand how a simple class slowly becomes difficult to change, and how each SOLID principle helps keep code flexible, testable, and easy to extend.

---

## Why Object-Oriented Programming?

Before SOLID makes sense, it helps to understand why we switched from writing code top-to-bottom in one file to thinking in objects.

**Procedural code** flows from top to bottom. There is no obvious grouping of responsibility, and everything talks to everything else. When the codebase grows, even a small change can break many things.

**Object-Oriented code** splits the system into entities that mirror the real world:

1. Identify every entity in the problem (nouns become classes).
2. For each entity, list its properties (attributes/fields).
3. For each entity, list its behavior (methods).
4. Define how entities relate to each other.

For example, a `Product` class might have `name`, `price`, and `category` as properties, and `applyDiscount()` and `getDescription()` as behavior. An object is a specific snapshot of a class — one particular product with actual values for those properties.

---

## Core OOP Concepts Recap

### Classes and Objects

A class is a blueprint. An object is a live instance of that blueprint.

```java
class Product {
    String name;
    double price;

    public Product(String name, double price) {
        this.name  = name;
        this.price = price;
    }
}

Product p = new Product("Laptop", 999.99);
```

### Inheritance

When multiple entities share common behavior, we pull that behavior into a parent class.

```java
class Notification {
    void send(String message) { /* default: log only */ }
}

class EmailNotification extends Notification {
    @Override
    void send(String message) { /* send via SMTP */ }
}

class SmsNotification extends Notification {
    @Override
    void send(String message) { /* send via SMS gateway */ }
}
```

Without inheritance, you end up with brittle `if/else` chains checking the notification type inside a single `send()` method. Inheritance moves the decision to the right class.

### Abstract Classes and Interfaces

**Problem with plain inheritance:** there is no schema enforcement. A subclass can forget to override `send()` and silently inherit the parent's default.

**Interfaces** enforce a contract. Any class implementing the interface must provide all the required methods. The tradeoff: you cannot put shared logic in an interface (before Java 8 default methods).

**Abstract classes** give you the best of both worlds:
- Common logic lives in the abstract parent.
- Abstract methods force subclasses to provide their own implementation.
- You cannot instantiate an abstract class directly.

```java
public abstract class Notification {
    abstract void send(String message);  // every subclass must implement

    void log(String message) {           // shared logic stays here
        System.out.println("[LOG] " + message);
    }
}
```

### Encapsulation

Keeping internal state private and exposing it only through controlled methods.

```java
class BankAccount {
    private double balance;

    public double getBalance() { return balance; }
    public void deposit(double amount) {
        if (amount > 0) balance += amount;
    }
}
```

Without encapsulation, any code anywhere can set `balance = -9999` directly.

### Abstraction vs Encapsulation

- **Abstraction** = hiding *how* something works (showing only the interface)
- **Encapsulation** = hiding *data* (protecting internal state)

---

## Package Naming Convention

Each subfolder uses a Java-valid package name with a leading underscore so the folder path maps cleanly to the `package` statement inside the Java files.

- `src/_01_solid/_01_s_single_responsibility/Main.java` → `package _01_solid._01_s_single_responsibility;`

---

## Folder Structure

```text
_01_solid/
├── _01_s_single_responsibility/
├── _02_o_open_closed/
├── _03_l_liskov_substitution/
├── _04_i_interface_segregation/
└── _05_d_dependency_inversion/
```

Each subfolder contains:

- `Main.java` — the runnable demo
- `README.md` — concept explanation and code walkthrough

---

## How To Run

From the `src` directory:

```bash
cd _01_solid/_01_s_single_responsibility
javac Main.java
java _01_solid._01_s_single_responsibility.Main
```

---

## The Five Principles

### S — Single Responsibility

> One class should have exactly one reason to change.

If one class calculates invoice totals, formats invoice output, and saves invoices to a database, it changes for three different reasons. Split it into `InvoiceCalculator`, `InvoicePrinter`, and `InvoiceRepository`.

**What this avoids:** God classes that become impossible to test or change safely.

---

### O — Open / Closed

> Open for extension, closed for modification.

Existing, tested code should not need to change when new behavior is added. Instead, write the new behavior in a new class.

Use an interface or abstract class as the contract. New types implement the contract without touching old code.

**What this avoids:** Editing stable code every time a new requirement arrives — which risks breaking what already works.

---

### L — Liskov Substitution

> Wherever a parent type is expected, a child type should work correctly.

If a `Notification` reference is used and we substitute an `EmailNotification`, the program should still work the same way. If a subclass throws an unexpected exception or behaves differently from the parent's contract, the substitution principle is violated.

Classic violation: a `ReadOnlyFile` extends `File`, but `ReadOnlyFile.write()` throws `UnsupportedOperationException`. Any code that expects a `File` and calls `write()` will break. The fix is to introduce a `WritableFile` interface that only writable files implement.

**What this avoids:** Broken inheritance trees where `instanceof` checks are needed to make things work.

---

### I — Interface Segregation

> Do not force a class to implement methods it does not use.

A fat interface with twenty methods forces every implementing class to provide stub implementations for methods it does not need. Break the fat interface into smaller, focused ones.

Example: A restaurant worker interface with `cook()`, `serve()`, and `clean()` forces a `Chef` to implement `serve()` even if a Chef never serves. Better: separate `Cook`, `Server`, and `Cleaner` interfaces.

**What this avoids:** Implementations full of empty or throw-not-supported methods.

---

### D — Dependency Inversion

> High-level modules should not depend on low-level modules. Both should depend on abstractions.

An `OrderService` should not create a `MySqlOrderRepository` directly. It should declare a dependency on an `OrderRepository` interface. The concrete implementation is injected from outside (dependency injection).

This is the principle behind Spring's IoC container and every mock-based unit test framework.

**What this avoids:** Tight coupling where changing the database means rewriting service code.

---

## Quick Summary Table

| Principle | Simple Meaning | What It Avoids |
|---|---|---|
| S — Single Responsibility | One class, one job | God classes |
| O — Open/Closed | Extend via new classes, not by editing old ones | Risky edits to stable code |
| L — Liskov Substitution | Child type works wherever parent is expected | Broken inheritance |
| I — Interface Segregation | Small, focused interfaces | Fat interfaces with unused methods |
| D — Dependency Inversion | Depend on abstractions, not concrete classes | Tight coupling |

---

## How to Explain SOLID in an Interview

> SOLID helps design classes that are easy to change, test, and extend. Single responsibility separates concerns. Open/closed protects stable code. Liskov keeps inheritance honest. Interface segregation keeps contracts small. Dependency inversion removes tight coupling and makes testing via mocks straightforward.

---

## Learning Notes

The examples here use simple business-style scenarios — invoices, discounts, notifications, restaurant workers, and orders — so the ideas stay concrete and easy to recall during an interview.

These principles work together. A class that violates SRP will often end up violating OCP too. A class that violates LSP will often force callers to use `instanceof`, which signals an abstraction problem. Applying all five together produces designs that stay clean as the system grows.
