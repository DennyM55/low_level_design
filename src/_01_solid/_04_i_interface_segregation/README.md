# Interface Segregation Principle (ISP)

**Package:** `_01_solid._04_i_interface_segregation`

## Simple Meaning

Do not force a class to implement methods it does not need.

Smaller interfaces are usually better than one large interface that pushes unrelated behavior onto every implementation.

---

## What This Demo Shows

This example uses a restaurant team to show why separate interfaces are easier to work with.

Important types:

- `Cook` — role for cooking food
- `Server` — role for serving customers
- `Cleaner` — role for cleaning tables
- `Chef` — implements only `Cook`
- `Waiter` — implements `Server` and `Cleaner`
- `RestaurantService` — depends on the exact role it needs

This is much cleaner than using one huge interface for everyone.

---

## Why This Is Better

If we used one giant interface like `RestaurantWorker`, every class would have to implement all methods.

That would lead to:

- empty methods
- fake implementations
- confusing code
- more maintenance work

By splitting behavior into smaller role-based interfaces, each class only implements the methods it actually supports.

---

## Code Walkthrough

In `Main.java`:

1. `Chef` is used for preparing food.
2. `Waiter` is used for serving and cleaning.
3. `RestaurantService` asks only for the correct role.

The caller depends on the smallest useful interface.

---

## Interview Explanation

You can say:

> Interface Segregation Principle means smaller role-based interfaces are better than one large interface that forces classes to implement methods they do not need.

---

## Run

From `src`, run:

```bat
cd _01_solid\_04_i_interface_segregation
javac Main.java
java _01_solid._04_i_interface_segregation.Main
```
