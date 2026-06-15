# Builder Design Pattern

## Simple Meaning

Builder helps create a complex object step by step without writing large, fragile constructors.

---

## Why Builder Exists — The Real Motivation

Start with a simple `HotelRoom` class used to model a reservation:

```java
class HotelRoom {
    String roomType;

    public HotelRoom(String roomType) {
        this.roomType = roomType;
    }
}

HotelRoom room = new HotelRoom("SUITE");
```

Now the product team adds two more fields — bed count and floor number:

```java
public HotelRoom(String roomType, int bedCount, int floorNumber) { ... }
```

Now all existing code that called `new HotelRoom("SUITE")` breaks. This is the **backward compatibility problem**. Adding an attribute forces you to update every place that calls the constructor.

As requirements grow (add `hasBalcony`, `smokingAllowed`, `viewType`, `pricePerNight`...), the problems multiply:

- The constructor signature becomes enormous and unreadable.
- Parameters of the same type (two `int`s, two `boolean`s) are easy to swap by mistake.
- You cannot skip optional fields — every caller must pass every parameter.
- Existing working code breaks every time the class evolves.

---

## Failed Approaches

### Constructor Overloading

Have one constructor per combination of attributes. Old code uses old constructors, new code uses new constructors.

**Problem:** With N attributes, you need up to `2^N` constructors to cover every combination. Completely unscalable.

### Pass a Config Object

```java
public HotelRoom(RoomConfig config) {
    this.roomType = config.roomType;
    // ...
}
```

**Problem:** The same issue repeats for the `RoomConfig` class — how do you construct *that* object cleanly?

---

## The Builder Solution

A static inner `Builder` class holds all the fields and provides a fluent setter method for each. When the caller is done configuring, they call `build()` which creates the actual object.

```java
public class HotelRoom {
    private final String roomType;
    private final int    bedCount;
    private final int    floorNumber;
    private final boolean hasBalcony;

    private HotelRoom(Builder builder) {
        this.roomType   = builder.roomType;
        this.bedCount   = builder.bedCount;
        this.floorNumber = builder.floorNumber;
        this.hasBalcony = builder.hasBalcony;
    }

    public static class Builder {
        String  roomType;     // mandatory
        int     bedCount;
        int     floorNumber;
        boolean hasBalcony;

        public Builder(String roomType) {
            this.roomType = roomType;  // mandatory field in Builder constructor
        }

        public Builder setBedCount(int bedCount) {
            this.bedCount = bedCount;
            return this;    // returns this so calls can be chained
        }

        public Builder setFloorNumber(int floorNumber) {
            this.floorNumber = floorNumber;
            return this;
        }

        public Builder setHasBalcony(boolean hasBalcony) {
            this.hasBalcony = hasBalcony;
            return this;
        }

        public HotelRoom build() {
            return new HotelRoom(this);
        }
    }
}
```

### Usage

```java
HotelRoom room = new HotelRoom.Builder("SUITE")
                               .setFloorNumber(12)
                               .setHasBalcony(true)
                               // bedCount is optional — we simply skip it
                               .build();
```

---

## How This Solves Every Problem

| Problem | Builder Solution |
|---|---|
| Constructor grows with attributes | Builder has one method per attribute; easy to extend |
| Backward compatibility breaks | Old callers skip new setter methods — no change needed |
| Cannot skip optional fields | Any setter can be omitted; the object defaults the value |
| Wrong parameter order | Named setter methods make the intent explicit |
| No validation before creation | `build()` can check mandatory fields and throw if missing |

---

## Enforcing Mandatory Fields

Put required fields in the Builder's constructor:

```java
public Builder(String roomType) {
    this.roomType = roomType;  // mandatory
}
```

The caller can still call `setRoomType()` again later to override, but at least they cannot forget to provide it entirely.

---

## Making the Object Immutable

Make the outer class constructor private (only the `Builder` can call it), and use `final` on the fields. Once `build()` runs, the resulting object cannot be changed.

```java
public class HotelRoom {
    private final String roomType;
    private final int    floorNumber;

    private HotelRoom(Builder builder) { ... }
}
```

---

## When Builder is the Right Choice

- Class has many fields (more than 3–4 is a good rule of thumb).
- Some fields are optional.
- The class might gain more fields over time.
- You want the constructed object to be immutable.
- Validation before creation is important.

---

## This Folder's Example

`Booking.java` models a cinema booking with fields like `bookingId`, `userId`, `showId`, `seats`, `bookingTime`, `coupon`, `foodCombo`, and `status`.

`Booking.Builder` lets callers set only the fields they care about and enforces required fields (bookingId, userId, showId) through the Builder's constructor.

`Main.java` creates bookings with and without optional fields (coupon, food) to show that both work cleanly without any overloaded constructors.

---

## Interview Line

> Use Builder when object construction has many fields, some of which are optional, when you want to validate before creating the object, or when you want the final object to be immutable.

---

## Key Differences: Builder vs Constructor vs Setters

| | Constructor | Setters | Builder |
|---|---|---|---|
| Mandatory fields | Enforced at compile time | Not enforced | Enforced in Builder constructor |
| Optional fields | Not possible cleanly | Supported | Supported via skipping setters |
| Immutability | Possible | Hard (setters imply mutability) | Easy with final fields |
| Readability | Poor with many params | OK but allows half-built objects | Good — fluent and readable |
| Backward compatible | No | Yes | Yes |

---

## Run

From the `src` directory:

```bash
javac _03_builder/Booking.java _03_builder/Main.java
java _03_builder.Main
```

---

## Expected Output

```text
Booking{id=B001, userId=U1, showId=S1, seats=[A1, A2], coupon=SUMMER20, food=Popcorn+Coke}
Booking{id=B002, userId=U2, showId=S3, seats=[C5], coupon=null, food=null}
```
