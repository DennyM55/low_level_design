# Prototype Pattern

## Simple Meaning

Prototype Pattern creates a new object by copying (cloning) an existing object instead of building it from scratch.

```text
You have a fully configured object.
Instead of calling new + setting all fields again,
you clone the existing object and adjust only what is different.
```

---

## Why It Exists

Object creation can be expensive when:

- The object has many fields to set.
- Some fields are fetched from a database or external service.
- A template object exists and most fields stay the same across copies.

Prototype avoids recreating the same setup repeatedly.

---

## Real-World Analogy

Think of a product listing on an e-commerce site.

A seller has a base product template: category, return policy, shipping details.

When creating 50 similar products, the seller clones the template and changes only the name and price.

That is the Prototype Pattern in action.

---

## What This Demo Shows

A user profile cloning system.

Types used:

- `UserProfile` — the prototype class that implements `Cloneable`
- `Address` — a nested object inside `UserProfile`
- `Main` — demonstrates shallow copy vs deep copy

---

## Shallow Copy vs Deep Copy

This is the most important interview topic for Prototype Pattern.

| | Shallow Copy | Deep Copy |
|---|---|---|
| Primitive fields | Copied by value | Copied by value |
| Object reference fields | Copied as a reference (both objects point to the same child) | A new child object is created |
| Risk | Modifying child in one copy affects the other | Each copy is independent |

### Shallow Copy Example

```java
UserProfile original = new UserProfile("Alice", new Address("Delhi"));
UserProfile copy = (UserProfile) original.clone();

copy.getAddress().setCity("Mumbai");

// original.getAddress().getCity() is now also "Mumbai" ← shared reference!
```

### Deep Copy Example

```java
@Override
protected Object clone() throws CloneNotSupportedException {
    UserProfile cloned = (UserProfile) super.clone();
    cloned.address = new Address(this.address.getCity()); // new object
    return cloned;
}
```

Now changing the city in the copy does not affect the original.

---

## Java Implementation Notes

To enable cloning in Java:

1. Implement `Cloneable` interface on the class.
2. Override the `clone()` method.
3. Call `super.clone()` to get the shallow copy from `Object`.
4. Replace mutable field references with new objects to get a deep copy.

```java
public class UserProfile implements Cloneable {
    private String name;
    private Address address;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        UserProfile cloned = (UserProfile) super.clone();
        cloned.address = new Address(this.address.getCity());
        return cloned;
    }
}
```

---

## Code Walkthrough

In `Main.java`:

1. An original `UserProfile` is created with a name and city.
2. The profile is cloned using `clone()`.
3. The cloned profile gets a different name and city.
4. The original is printed again to confirm it was not affected.

This shows that the deep copy created a fully independent object.

---

## Interview Explanation

You can say:

> Prototype Pattern clones an existing object to create a new one. The key is understanding shallow vs deep copy. A shallow copy shares references to nested objects, which can cause unexpected mutations. A deep copy creates independent copies of all fields.

---

## When To Use

- When object creation is expensive and similar objects are needed.
- When you want to create copies of an object without coupling to its class.
- When a default template object exists and variations only change a few fields.

---

## Run

From the `src` directory:

```bash
mvn compile
java -cp target/classes com.denny.lld.patterns.creational.prototype.Main
```

---

## Expected Output

```text
Original: UserProfile{name='Alice', address=Address{city='Delhi'}}
Clone 1:  UserProfile{name='Bob', address=Address{city='Mumbai'}}
Original after clone edit: UserProfile{name='Alice', address=Address{city='Delhi'}}
```

The last line confirms that editing the clone did not change the original.

---

## Key Takeaway

- Prototype clones objects to avoid repeated construction.
- Always use deep copy when the object contains mutable reference fields.
- Java's `Cloneable` + `super.clone()` gives a shallow copy by default.
- Deep copy requires manually creating new objects for each mutable field.
