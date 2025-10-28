# Adapter Pattern and Strategy Pattern

This folder covers two structural/behavioral patterns that appear together frequently in real systems and interview questions.

---

## Part 1 — Adapter Pattern

### Simple Meaning

Adapter lets two incompatible interfaces work together without changing either of them.

```text
You have an existing system expecting one interface.
You get a new component with a different interface.
The adapter sits in the middle and translates calls.
```

### Real-World Analogy

A power plug adapter.

Your laptop uses a 3-pin plug. The hotel wall has a 2-pin socket.
You use an adapter that sits between them. Neither the laptop nor the wall changes.

### Why It Exists

- You want to reuse an existing class but its interface does not match what the caller expects.
- You are integrating a third-party library whose API looks different from your system's contracts.
- You want to wrap a legacy class behind a clean modern interface.

### Types of Adapter

| Type | How It Works |
|---|---|
| Object Adapter | Wraps the adaptee as a field (composition) |
| Class Adapter | Inherits from both the target and the adaptee (requires multiple inheritance, common in C++) |

Java typically uses **Object Adapter** via composition.

### What This Demo Shows

A payment system with multiple payment gateways.

The system expects a `PaymentGateway` interface.
A legacy third-party library (`LegacyPayPalClient`) has a different method signature.
An adapter (`PayPalAdapter`) wraps the legacy client and makes it compatible.

Types used:

- `PaymentGateway` — the target interface the system depends on
- `RazorpayGateway` — a native implementation of `PaymentGateway`
- `LegacyPayPalClient` — an external class with a different interface
- `PayPalAdapter` — wraps `LegacyPayPalClient` and implements `PaymentGateway`
- `PaymentService` — the caller that only knows about `PaymentGateway`
- `Main` — wires everything together

### Code Walkthrough

```text
PaymentService depends on → PaymentGateway
RazorpayGateway implements PaymentGateway directly
LegacyPayPalClient has its own method: processPayment(amount, currency)
PayPalAdapter implements PaymentGateway and delegates to LegacyPayPalClient
```

The caller uses `PaymentService` with any `PaymentGateway` object, including the adapted one.

### Interview Explanation

You can say:

> Adapter Pattern wraps an incompatible class behind a target interface so the caller does not need to change. It is used when integrating third-party libraries or legacy code into a system that expects a different interface.

---

## Part 2 — Strategy Pattern

### Simple Meaning

Strategy Pattern lets you choose an algorithm or behavior at runtime by swapping the implementation without changing the class that uses it.

```text
The class does not hardcode how to sort, pay, or validate.
The behavior is injected from outside.
```

### Real-World Analogy

A navigation app.

You pick: fastest route, shortest route, or avoid highways.
The app does not change. Only the routing strategy changes.

### Why It Exists

- Without Strategy, you end up with large if/else blocks or switch statements inside a class.
- Adding a new algorithm requires modifying the existing class (violates Open/Closed Principle).
- Strategy externalises the algorithm so new ones can be added without changing the context class.

### What This Demo Shows

A sorting context that can use different sorting algorithms.

Types used:

- `SortStrategy` — interface with a `sort(int[] data)` method
- `BubbleSortStrategy` — bubble sort implementation
- `QuickSortStrategy` — quick sort implementation
- `MergeSortStrategy` — merge sort implementation
- `SortContext` — context class that holds a `SortStrategy` and delegates sorting
- `Main` — shows how to switch strategies at runtime

### Code Walkthrough

```text
SortContext holds a SortStrategy reference
sort() delegates to the current strategy
The strategy can be replaced at any time using setStrategy()
```

This means the sorting algorithm can be chosen and changed at runtime with no modification to `SortContext`.

### Interview Explanation

You can say:

> Strategy Pattern separates an algorithm from the class that uses it. The class holds a reference to a strategy interface and delegates the work. New algorithms are added as new classes, not as new if/else conditions.

---

## Adapter vs Strategy — Quick Comparison

| | Adapter | Strategy |
|---|---|---|
| Intent | Make incompatible interfaces work together | Swap interchangeable algorithms |
| Problem solved | Interface mismatch | Behavior variation |
| When to use | Integrating external/legacy classes | Multiple ways to do the same operation |

---

## Run

From the `src` directory:

```bash
mvn compile
java -cp target/classes com.denny.lld.patterns.structural.adapter.AdapterDemo
java -cp target/classes com.denny.lld.patterns.behavioral.strategy.StrategyDemo
```

---

## Expected Output

```text
[Razorpay] Processing payment of 5000.0 INR
[PayPal Legacy] processPayment called: amount=2500.0, currency=USD
[PayPal via Adapter] Payment of 2500.0 USD completed
```

---

## Key Takeaways

- Adapter wraps an existing incompatible class. No original code changes.
- Strategy injects an algorithm so the context does not hard-code behavior.
- Both patterns rely on programming to an interface rather than a concrete class.
