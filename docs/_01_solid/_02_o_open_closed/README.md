## Open/Closed Principle

**Simple meaning:** Code should allow new behavior without changing already working code again and again.

Open/Closed Principle means:

```text
Open for extension
Closed for modification
```

This means we should be able to add new features by writing new code, instead of repeatedly editing old, tested code.

---

## Real Problem

Imagine a checkout system.

At first, we may write discount logic like this:

```text
CheckoutService
    if customer is regular, apply regular discount
    if customer is premium, apply premium discount
    if customer is employee, apply employee discount
```

This may look fine in the beginning.

But later:

* Student discount is added.
* Festival discount is added.
* Corporate discount is added.
* New membership discount is added.

Now every time a new discount type comes, we keep changing the same `CheckoutService`.

That is risky because one small change can accidentally break existing discount logic.

This is the problem Open/Closed Principle solves.

---

## Better Design

Instead of keeping all discount rules inside one class, we create a common abstraction:

```text
DiscountPolicy
```

Then each discount rule gets its own class:

```text
RegularCustomerDiscount
PremiumCustomerDiscount
EmployeeDiscount
```

The checkout service depends only on `DiscountPolicy`.

It does not need to know the exact discount type.

---

## Why This Is Better

If a new discount type comes, we do not edit the old checkout logic.

We simply add a new class:

```text
StudentDiscount
```

or:

```text
FestivalDiscount
```

The existing code stays stable.

That means:

* Less risk of breaking old code
* Easier testing
* Cleaner design
* Better flexibility

---

## Code Walkthrough

In `Main.java`:

1. `DiscountPolicy` defines the common discount behavior.
2. `RegularCustomerDiscount` gives a regular customer discount.
3. `PremiumCustomerDiscount` gives a premium customer discount.
4. `EmployeeDiscount` gives an employee discount.
5. `CheckoutService` uses `DiscountPolicy` without caring about the actual discount class.
6. `Main` passes different discount policies to the checkout service.

The important idea is this:

```text
Add new behavior using new classes, not by modifying old stable code.
```

---

## Interview Explanation

You can say:

```text
Open/Closed Principle means a class should be open for extension but closed for modification.

For example, in a discount system, instead of writing many if/else conditions inside CheckoutService, we can create a DiscountPolicy interface and separate discount classes. If a new discount type is needed, we add a new class without changing the existing checkout logic.
```

---

## Run

Open CMD inside this folder:

```text
01-solid\02-o-open-closed
```

Then run:

```bat
javac Main.java
java Main
```

---

## Expected Output

```text
Cart Amount: Rs. 10000.0

Regular Customer Final Amount: Rs. 9500.0
Premium Customer Final Amount: Rs. 8500.0
Employee Final Amount: Rs. 7000.0
```

---

## Key Takeaway

Open/Closed Principle does not mean existing code can never be changed.

It means stable business logic should not be repeatedly modified for every new variation.

When new behavior is expected, design the code so new behavior can be added through extension.
