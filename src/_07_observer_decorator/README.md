# Observer Pattern and Decorator Pattern

This folder covers two widely used behavioral and structural patterns.

---

## Part 1 — Observer Pattern

### Simple Meaning

Observer Pattern lets multiple objects listen to changes in another object.

When the subject changes state, all registered listeners are automatically notified.

```text
Subject changes → notifies → all registered observers
```

### Real-World Analogies

- YouTube subscription: when a channel publishes a video, all subscribers get notified.
- Stock ticker: when a price changes, all interested dashboards update.
- Event bus in a Spring application.

### Why It Exists

Without Observer, the subject would need to know about every class that cares about its changes:

```java
if (priceChanged) {
    emailService.sendAlert();
    mobileApp.pushNotification();
    dashboardService.refresh();
}
```

Every new listener requires editing the subject. This violates Open/Closed Principle.

With Observer, the subject only knows about the `Observer` interface. Each listener registers itself.

### What This Demo Shows

A stock price notification system.

Types used:

- `Observer` — interface with an `update(String stockName, double newPrice)` method
- `StockSubject` — interface for the subject with register, remove, and notify methods
- `StockMarket` — the concrete subject that holds subscribers and notifies them on price change
- `MobileAlertObserver` — sends a mobile alert when price changes
- `EmailAlertObserver` — sends an email alert when price changes
- `Main` — shows how observers are registered and receive notifications

### Key Design Notes

- The subject holds a list of `Observer` references, not concrete classes.
- Adding a new observer means creating a new class and registering it. No changes to `StockMarket`.
- Observers can be removed at runtime.

### Interview Explanation

You can say:

> Observer Pattern separates the object that produces events from the objects that react to them. The subject maintains a list of observers and notifies them when something important changes. This is the backbone of event-driven programming.

---

## Part 2 — Decorator Pattern

### Simple Meaning

Decorator Pattern adds behavior to an object at runtime without changing its class or using inheritance chains.

```text
Start with a base object.
Wrap it with one or more decorators.
Each decorator adds a behavior.
```

### Real-World Analogies

- Java I/O streams: `BufferedInputStream` wraps `FileInputStream`. `GZIPInputStream` wraps `BufferedInputStream`. Each adds behavior.
- Coffee ordering: start with plain coffee, add milk, add sugar, add syrup. Each is a decorator.

### Why It Exists

Using inheritance to add behavior creates class explosion:

```text
Coffee
CoffeeWithMilk
CoffeeWithSugar
CoffeeWithMilkAndSugar
CoffeeWithMilkAndSugarAndSyrup
```

With Decorator, you compose behaviors dynamically:

```java
Coffee coffee = new MilkDecorator(new SugarDecorator(new SimpleCoffee()));
```

### What This Demo Shows

A coffee order pricing system.

Types used:

- `Coffee` — interface with `getCost()` and `getDescription()` methods
- `SimpleCoffee` — base implementation
- `CoffeeDecorator` — abstract decorator that wraps a `Coffee` instance
- `MilkDecorator` — adds milk and its cost
- `SugarDecorator` — adds sugar and its cost
- `WhipDecorator` — adds whipped cream and its cost
- `Main` — builds different coffee combinations using decorators

### Code Walkthrough

```java
Coffee coffee = new SimpleCoffee();                 // base: Rs. 50
coffee = new MilkDecorator(coffee);                 // +15 = Rs. 65
coffee = new SugarDecorator(coffee);                // +10 = Rs. 75
coffee = new WhipDecorator(coffee);                 // +20 = Rs. 95
```

Each decorator wraps the previous one. The final object chains all behaviors.

### Interview Explanation

You can say:

> Decorator Pattern adds behavior to an object at runtime by wrapping it in decorator objects, each of which delegates to the wrapped object and adds its own behavior. This avoids subclass explosion and keeps each behavior in its own small class.

---

## Observer vs Decorator — Quick Comparison

| | Observer | Decorator |
|---|---|---|
| Intent | Notify multiple objects of changes | Add behavior to a single object at runtime |
| Pattern type | Behavioral | Structural |
| Key mechanism | Subject holds a list of observers | Decorators wrap the original object |
| Use case | Event-driven systems, pub-sub | Adding features or filtering dynamically |

---

## Run

From the `src` directory:

```bash
javac _07_observer_decorator/*.java
java _07_observer_decorator.Main
```

---

## Expected Output

```text
=== Observer Pattern Demo ===
[Mobile Alert] AAPL price changed to 175.5
[Email Alert] AAPL price changed to 175.5
[Mobile Alert] AAPL price changed to 180.0
EmailAlertObserver removed.
[Mobile Alert] AAPL price changed to 172.3

=== Decorator Pattern Demo ===
SimpleCoffee: Rs. 50.0
With Milk: Rs. 65.0
With Milk + Sugar: Rs. 75.0
With Milk + Sugar + Whip: Rs. 95.0
```

---

## Key Takeaways

- Observer is the foundation of event-driven and reactive systems.
- Decorator is how Java I/O streams work and how Spring applies cross-cutting concerns.
- Both patterns rely on interfaces and composition rather than inheritance.
