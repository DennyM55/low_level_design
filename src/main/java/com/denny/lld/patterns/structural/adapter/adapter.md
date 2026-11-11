
# Adapter Design Pattern
**Category:** Structural Design Pattern

## 1. What is Adapter?

An Adapter is a class that allows two incompatible interfaces to work together.

It acts like a translator between our application and an existing class or API.

**Application → Adapter → External API**

## 2. Why do we need it?

Suppose our application uses Stripe to process payments.

Stripe provides `createPayment()`, while PayPal provides `makePayment()`.

Both do the same job, but their methods are different.

If our application directly depends on these APIs, switching providers means changing application code.

Instead, we create a common interface called `PaymentGateway` with a `pay()` method.

Each provider gets its own Adapter, which translates `pay()` into the method that provider understands.

This keeps our application independent of provider-specific methods.

## 3. How does it work?

```text
                  Application
                       |
                       v
               PaymentGateway
                  pay(amount)
                       |
             +---------+---------+
             |                   |
             v                   v
       StripeAdapter       PayPalAdapter
             |                   |
             v                   v
          StripeApi           PayPalApi
       createPayment()       makePayment()
```

Our Java implementation has four main components:

- **Client:** `AdapterDemo` — Uses the payment service.
- **Target:** `PaymentGateway` — Common interface our application expects.
- **Adaptee:** `StripeApi` / `PayPalApi` — Existing APIs with different methods.
- **Adapter:** `StripeAdapter` / `PayPalAdapter` — Connects our interface to each API.

## 4. Java Implementation

Our application expects:

```java
interface PaymentGateway {
    void pay(double amount);
}
```

Stripe provides `createPayment()`, so its Adapter translates the call:

```java
class StripeAdapter implements PaymentGateway {
    private final StripeApi stripe = new StripeApi();

    public void pay(double amount) {
        stripe.createPayment(amount);
    }
}
```

Similarly, `PayPalAdapter` calls `paypal.makePayment(amount)`.

The application simply uses:

```java
PaymentGateway payment = new StripeAdapter();
payment.pay(1000);

payment = new PayPalAdapter();
payment.pay(2000);
```

The application always calls `pay()`. Only the Adapter knows which external method to call.

The Adapter holds the external API object (**composition**) and forwards the translated call (**delegation**).

## 5. Where is Adapter used?

**Payment gateways:** Connect Stripe, PayPal or other providers through one application interface.

**Legacy systems:** Convert requests and responses between modern REST services and older SOAP/XML systems.

**Third-party SDKs:** Hide provider-specific methods and data formats from application code.

**Data conversion:** Translate external response objects into the format our application expects.

## 6. Adapter in Spring Boot

In Spring Boot, we can create an Adapter as a Spring-managed bean using `@Component`.

Our service depends on the common interface, while the Adapter communicates with the external API.

```text
OrderService
    |
    v
PaymentGateway
    |
    v
StripeAdapter
    |
    v
Stripe API
```

If the external API changes, we mainly update its Adapter instead of changing the business service.

The Adapter can also convert provider-specific errors and responses into application-friendly formats.

## 7. Key Points

- Adapter connects interfaces that do not match.
- It implements the interface our application expects.
- It wraps the existing API without modifying it.
- It uses composition and delegation.
- It reduces direct dependency on external APIs.
- It supports SRP, OCP and DIP when designed properly.
- It adds extra classes, so use it when there is a meaningful interface or data-format mismatch.

**Adapter = Common Interface + Translation + Existing API**
