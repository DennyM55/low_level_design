# Factory Design Pattern

## Simple Meaning

Factory Pattern lets you create objects without the calling code knowing or deciding which concrete class to instantiate.

Instead of writing `new SomeConcreteClass()` everywhere, you ask a factory for an object by type — and the factory figures out the rest.

---

## Why Factory Exists — The Real Motivation

Consider a payment processing system. Different payment methods have completely different integration requirements:

- Credit card payments go through a card processing network (PCI-DSS compliance, card tokenization).
- PayPal uses OAuth and a REST API.
- Bank transfers use SWIFT codes and routing numbers.
- Crypto payments use wallet addresses and blockchain confirmations.

So you create separate handler classes for each method:

```java
public class CreditCardProcessor {
    public boolean charge(double amount) { ... }
}

public class PayPalProcessor {
    public boolean charge(double amount) { ... }
}
```

Now a checkout service writes:

```java
public class CheckoutService {
    public boolean processPayment(String method, double amount) {
        if (method.equals("CREDIT_CARD"))
            return new CreditCardProcessor().charge(amount);
        else if (method.equals("PAYPAL"))
            return new PayPalProcessor().charge(amount);
        // ...
    }
}
```

### Problems with this approach

1. The service must know the exact class name for every payment method.
2. A typo in the string ("Credit_Card" vs "CREDIT_CARD") silently returns null.
3. An unrecognized method causes a null pointer exception with no clear error.
4. Adding a new payment method means editing every `if/else` block in every service.
5. Testing is harder because the service is tightly coupled to concrete classes.

---

## Step 1 — Code Against an Interface

The first fix is to pull all processors behind a common interface. This is called **runtime polymorphism** — the reference type is `PaymentProcessor`, but the object at runtime is `CreditCardProcessor`, `PayPalProcessor`, etc.

```java
public interface PaymentProcessor {
    boolean charge(double amount);
}

public class CreditCardProcessor implements PaymentProcessor {
    public boolean charge(double amount) { ... }
}

public class PayPalProcessor implements PaymentProcessor {
    public boolean charge(double amount) { ... }
}
```

Now the service can hold a `PaymentProcessor` reference without caring which method it is.

---

## Step 2 — Centralize the Mapping (Factory)

Who should decide which `PaymentProcessor` to return for which method string? Not the service. The factory does.

```java
class PaymentProcessorFactory {
    private static Map<String, PaymentProcessor> processors = new HashMap<>();

    private PaymentProcessorFactory() {
        processors.put("CREDIT_CARD", new CreditCardProcessor());
        processors.put("PAYPAL",      new PayPalProcessor());
        processors.put("BANK",        new BankTransferProcessor());
    }

    public PaymentProcessor getProcessor(String method) {
        if (!processors.containsKey(method)) {
            throw new IllegalArgumentException("Unsupported payment method: " + method);
        }
        return processors.get(method);
    }
}
```

Now the service becomes clean:

```java
public class CheckoutService {
    public boolean processPayment(String method, double amount) {
        return PaymentProcessorFactory.getInstance()
                                      .getProcessor(method)
                                      .charge(amount);
    }
}
```

The service knows only two things: there is a `PaymentProcessor` interface, and there is a factory that gives one. The service never touches `CreditCardProcessor` or `PayPalProcessor` directly.

---

## Factory + Singleton

Creating a `HashMap` of processors is expensive — each processor might also set up connections or load configuration. The factory should be created only once and shared.

**Rule:** Factories should almost always be Singleton.

```java
class PaymentProcessorFactory {
    private static PaymentProcessorFactory INSTANCE;
    private Map<String, PaymentProcessor> processors;

    private PaymentProcessorFactory() {
        processors = new HashMap<>();
        processors.put("CREDIT_CARD", new CreditCardProcessor());
        processors.put("PAYPAL",      new PayPalProcessor());
    }

    public static PaymentProcessorFactory getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PaymentProcessorFactory();
        }
        return INSTANCE;
    }

    public PaymentProcessor getProcessor(String method) {
        if (!processors.containsKey(method))
            throw new IllegalArgumentException("Unsupported method: " + method);
        return processors.get(method);
    }
}
```

---

## Three Variations

| Type | Description |
|---|---|
| Simple Factory | A static method that creates objects; not formally a GoF pattern but widely used |
| Factory Method | An abstract method in a parent class; each subclass decides which object to create |
| Abstract Factory | Creates a family of related objects (e.g., UI components for Windows vs Mac) |

The payment processor example above is a **Simple Factory / Singleton Factory**. The Factory Method pattern uses inheritance instead of a map.

---

## This Folder's Example

A notification system where the factory selects between `EmailNotification`, `SmsNotification`, and `PushNotification` based on a `NotificationType` enum.

```java
Notification n = NotificationFactory.create(NotificationType.EMAIL);
n.send("Your order has been placed.");
```

The caller never writes `new EmailNotification()`. Adding a new notification channel (e.g., WhatsApp) means adding one class and one line in the factory — no caller changes required.

---

## Key Insight: Factory Removes the if/else from the Caller

| Without Factory | With Factory |
|---|---|
| Caller has `if/else` or `switch` for every type | Caller calls `factory.getProcessor(type)` |
| Adding a type = updating all callers | Adding a type = one new class + one factory update |
| Caller tightly coupled to concrete classes | Caller only knows the abstract interface |
| Duplicate logic across callers | Single source of truth in the factory |

---

## Interview Explanation

> Factory Pattern is used when the calling code should not be responsible for choosing which concrete class to instantiate. The factory centralises that decision and returns the correct object through a common interface. The caller depends only on the abstraction, not the implementation.

---

## When to Use

- Object creation depends on a condition, type, or configuration value.
- Multiple subclasses share a common interface.
- The caller should not change when a new subtype is added.
- You are shipping a library and want clients to use processors, validators, or services without knowing the concrete classes.

---

## Run

From the `src` directory:

```bash
mvn compile
java -cp target/classes com.denny.lld.patterns.creational.factory.Main
```

---

## Expected Output

```text
[EMAIL] Sending email notification: Your order has been placed.
[SMS] Sending SMS notification: Your OTP is 482910.
[PUSH] Sending push notification: You have a new message.
```

---

## Key Takeaway

- Factory removes the `new ConcreteClass()` decision from the caller.
- The caller depends on the abstract type, not the concrete implementation.
- Adding a new type = add a new class + update the factory. No caller code changes.
- Factories are almost always Singleton because they hold mappings and are expensive to rebuild.
