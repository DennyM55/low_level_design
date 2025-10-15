# Liskov Substitution Principle (LSP)

**Package:** `com.denny.lld.fundamentals.solid`

## Simple Meaning

If code expects a parent type, any child type should work without surprising the caller.

The child class should preserve the behavior promised by the parent abstraction.

---

## What This Demo Shows

This example uses birds to show why inheritance should not force every subclass into the same behavior.

Important types:

- `Bird` — common bird data and behavior
- `FlyingBird` — a separate capability for birds that can fly
- `Eagle` — a bird that can also fly
- `Penguin` — a bird that cannot fly
- `BirdCareService` — works with any bird
- `FlightShowService` — works only with flying birds

The key idea is that the caller should depend on the correct abstraction.

---

## Why This Is Better

If `fly()` were placed directly on `Bird`, then a penguin would be forced into a behavior it does not support.

That creates a broken promise:

- the code compiles
- but the object cannot really fulfill the contract

By splitting the flying ability into `FlyingBird`, we avoid that mismatch.

---

## Code Walkthrough

In `Main.java`:

1. `BirdCareService` feeds both the eagle and the penguin.
2. `FlightShowService` accepts only a `FlyingBird`.
3. `Eagle` can be used in both places.
4. `Penguin` is safely excluded from flying behavior.

This is what safe substitution looks like.

---

## Interview Explanation

You can say:

> Liskov Substitution Principle means inheritance should not create broken promises. A child type must behave correctly wherever the parent type is expected.

---

## Run

From `src`, run:

```bat
cd com.denny.lld.fundamentals.solid\_03_l_liskov_substitution
javac Main.java
java com.denny.lld.fundamentals.solid._03_l_liskov_substitution.Main
```
