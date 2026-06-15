# Low Level Design — Complete Revision Guide

A one-stop revision sheet covering all LLD topics in this course.

---

## 1. SOLID Principles

| Principle | One Liner | Classic Violation | Fix |
|---|---|---|---|
| S — Single Responsibility | One class, one reason to change | Invoice class that calculates, prints, and saves | Separate InvoiceCalculator, InvoicePrinter, InvoiceRepository |
| O — Open/Closed | Open for extension, closed for modification | if/else for every discount type | DiscountPolicy interface + separate classes |
| L — Liskov Substitution | Child should work wherever parent is expected | Penguin.fly() throws exception | Extract FlyingBird; Penguin implements Bird only |
| I — Interface Segregation | Do not force classes to implement unused methods | RestaurantWorker with cook/serve/clean | Separate Cook, Server, Cleaner interfaces |
| D — Dependency Inversion | Depend on abstractions, not concretions | OrderService creates MySqlRepository directly | Inject OrderRepository interface |

---

## 2. OOP Fundamentals

### Classes and Objects

A class is a blueprint for an entity. An object is a live instance.

```java
class Vehicle {
    String make;
    int year;
    void drive() { /* ... */ }
}
Vehicle v = new Vehicle();
```

### Inheritance and Method Overriding

Pull shared behavior into a parent class. Child classes override methods that differ.

```java
class ElectricCar extends Vehicle {
    @Override
    void drive() { /* silent motor */ }
}

class Truck extends Vehicle {
    @Override
    void drive() { /* diesel engine */ }
}
```

### Abstraction and Abstract Classes

Abstract classes enforce that every child provides its own implementation of key methods, while sharing common logic in the parent.

```java
abstract class Vehicle {
    abstract void drive();     // every subclass must implement
    void refuel() { /* shared logic */ }
}
```

### Interfaces

Interfaces enforce a behavioral contract across unrelated classes. Multiple interfaces can be implemented by the same class (Java's answer to multiple inheritance).

```java
interface PaymentProcessor {
    boolean charge(double amount);
}

class CreditCardProcessor implements PaymentProcessor { ... }
class PayPalProcessor        implements PaymentProcessor { ... }
```

### Encapsulation

Hide internal state. Expose controlled access via methods.

```java
class BankAccount {
    private double balance;
    public double getBalance() { return balance; }
    public void deposit(double amount) { if (amount > 0) balance += amount; }
}
```

### Stateful vs Stateless

- **Stateful:** Object holds data between calls. Creating it is expensive. Good candidate for Singleton.
- **Stateless:** No shared data. Static methods work fine. Examples: `Math.max()`, `Collections.sort()`.

---

## 3. Creational Patterns

### Singleton

One instance for the lifetime of the app.

**When:** Database connection pool, logger, configuration manager, resource cache.

```java
public class Singleton {
    private static volatile Singleton instance;

    private Singleton() {}

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) instance = new Singleton();
            }
        }
        return instance;
    }
}
```

**Why volatile?** Prevents CPU instruction reordering during object creation.
**Bill Pugh:** Inner static Holder class — clean, lazy, thread-safe without locks.

**Singleton + Factory combination:** Factories are almost always Singleton because building the mapping is expensive.

---

### Builder

Object with many optional fields. Inner static `Builder` class. `build()` validates mandatory fields. Final fields after construction.

**When:** Class has many fields, some optional, and the object should be immutable.

```java
HotelRoom room = new HotelRoom.Builder("SUITE")
                               .setFloorNumber(12)
                               .setHasBalcony(true)
                               .build();
```

**Backward compatibility:** Adding a new field to `HotelRoom` only adds a new setter to `Builder`. Existing callers that do not set the new field continue to work unchanged.

---

### Factory

Central method creates and returns the right subtype. Caller depends on interface, not concrete class.

**When:** Object creation depends on a type or condition. Adding a new type should not require changing callers.

```java
Notification n = NotificationFactory.create(NotificationType.EMAIL);
n.send("Order placed");
```

**Factory vs Abstract Factory:** Factory creates objects of one family. Abstract Factory creates families of related objects (e.g., UI toolkit for Windows vs Mac).

---

### Prototype

Clone an existing object instead of rebuilding from scratch.

**When:** Object creation is expensive; a close variation of an existing object is needed.

```java
UserProfile copy = (UserProfile) original.clone();
// Deep copy creates new Address inside clone()
```

---

## 4. Structural Patterns

### Adapter

Wraps an incompatible class behind a target interface. Neither the target nor the adaptee changes.

```java
class PayPalAdapter implements PaymentGateway {
    private LegacyPayPalClient client;
    public void pay(double amount, String currency) {
        client.processPayment(amount, currency);
    }
}
```

---

### Decorator

Wraps an object and adds behavior. Implements the same interface. Multiple decorators can chain.

```java
Coffee coffee = new WhipDecorator(new MilkDecorator(new SimpleCoffee()));
coffee.getCost(); // 50 + 15 + 20 = 85
```

Real example: `BufferedInputStream` wraps `FileInputStream`.

---

## 5. Behavioral Patterns

### Strategy

Inject an algorithm via interface. Swap at runtime.

```java
SortContext ctx = new SortContext(new QuickSortStrategy());
ctx.sort(data);
ctx.setStrategy(new MergeSortStrategy());
ctx.sort(data);
```

Used in Splitwise: `EqualSplit`, `ExactSplit`, `PercentageSplit` are strategies.

---

### Observer

Subject holds a list of Observer references. Subject calls `notifyObservers()` on state change.

```java
stock.register(mobileAlert);
stock.register(emailAlert);
stock.setPrice(175.5); // automatically notifies both
```

Used in Spring: `ApplicationEventPublisher` and `@EventListener`.

---

### Chain of Responsibility (Cascader)

Pass a request through a sequence of handlers. Each handler either handles it or passes it to the next. Supports fail-fast execution.

**Address validation pipeline:**
- Zip validation is cheapest → check first.
- If zip fails → stop (fail fast). No point running city and lines checks.
- If zip passes → validate city → validate lines.

```java
public class ValidatorCascade implements Validator {
    private final List<Validator> validators;

    public ValidatorCascade(List<Validator> validators) {
        this.validators = validators;
    }

    public boolean validate(Address a) {
        for (Validator v : validators)
            if (!v.validate(a)) return false;  // fail fast
        return true;
    }
}
```

The factory maps use-case keys to pre-built validator chains (Singleton + Factory):

```java
class ValidatorCascadeFactory {  // Singleton + Factory
    private final Map<String, Validator> registry;

    private ValidatorCascadeFactory() {
        registry = new HashMap<>();
        registry.put("checkout", new ValidatorCascade(
            Arrays.asList(new ZipValidator(), new CityValidator(), new LinesValidator())
        ));
        registry.put("identity_check", new ValidatorCascade(
            Arrays.asList(new ZipValidator(), new CityValidator())
        ));
    }

    public Validator getValidator(String useCaseKey) {
        if (!registry.containsKey(useCaseKey))
            throw new IllegalArgumentException("No chain for: " + useCaseKey);
        return registry.get(useCaseKey);
    }
}
```

Caller usage:

```java
ValidatorCascadeFactory.getInstance()
                       .getValidator("checkout")
                       .validate(address);
```

**Why order matters:** Zip failures are far more common than city failures. Fail on the cheapest check first — same idea as branch pruning in recursion: avoid work that cannot yield a valid result.

---

### State

Object changes behavior based on its current state. Each state is a separate class.

Used in: Vending Machine, ATM, Elevator, Traffic Light.

---

### Command

Encapsulate a request as an object. Supports undo/redo, queuing, logging.

Used in: TicTacToe (move history), Splitwise (undo settle).

---

### Template Method

Define the skeleton of an algorithm in a base class. Let subclasses override specific steps without changing the overall structure.

Used in: data parsers (read → parse → validate → store), game turn loops.

---

## 6. System Designs Summary

### StackOverflow

**Entities:** Question, Answer, Comment, Bounty, Photo, Badge, Member, Moderator, Admin, Account, Guest

**Key relationships:**
- Question `<>--` Answer, Comment (composition — deleting Question deletes them)
- Member `--` Badge (association — many-to-many, neither owns the other)
- Moderator, Admin extend Member
- Guest does NOT extend Member

**Key patterns:** Observer (new answer → notify question author), Strategy (search)

---

### Hotel Management System

**Entities:** HotelBrand, HotelBranch, Room, RoomKey, Person, Guest, Receptionist, Housekeeper, Account, Booking, HouseKeepingTask, RoomService, InvoiceItem, Invoice

**Key relationships:**
```
HotelBrand 1 <>---- * HotelBranch      (composition)
HotelBranch 1 <>---- * Room            (composition)
Room 1 <>---- * RoomKey                (composition)
Person <--- ext --- Guest
Person <--- ext --- Receptionist
Person <--- ext --- Housekeeper
Room 1 ---- * Booking                  (association — room outlives bookings)
Booking * -----> 1 Guest               (association)
Housekeeper 1 -----> * HouseKeepingTask (association)
Room 1 -----> * HouseKeepingTask       (association)
RoomBooking 1 -------> 1 Invoice       (association)
InvoiceItem * ------<> 1 Invoice       (composition)
```

**Search is an interface:** `search_by_room_type()`, `search_by_floor()`. Multiple things can be searched — keeping Search as an interface lets you swap implementations.

**Key patterns:** Builder (Booking), Factory (Room type), Observer (housekeeping tasks)

---

### BookMyShow

See full design in `_14_design_bookmyshow`.

**Key distinction:** `HallSeat` (physical chair) vs `ShowSeat` (seat for a specific show). The physical chair does not have an `isReserved` field — that belongs to `ShowSeat`.

**Key patterns:** Builder (Booking), Factory (Payment), Observer (confirmation), Strategy (search)

---

### Splitwise

See full design in `_16_design_splitwise`.

**Balance map:** `Map<owerId, Map<lenderId, amount>>`. Positive = owerId owes lenderId.

**Key patterns:** Strategy (split type), Factory (creating Split subtype)

---

## 7. DB Schema Design Notes

### Why Multiple Tables?

Storing everything in one table causes three types of anomalies:

| Anomaly | Example |
|---|---|
| **Insert anomaly** | Cannot add info about a college until at least one student enrolls |
| **Delete anomaly** | Deleting the last student in a course deletes the course info too |
| **Update anomaly** | Changing a college's rank requires updating thousands of rows |

### Rules

- Each entity → one table. Attributes → columns. Define a primary key.
- Each relationship → one join table with foreign keys from both sides.
- Relationship attributes (enrollment date, marks) go in the join table.
- The join table's primary key is the combination of both entities' primary keys.

### First Normal Form (1NF)

Data must be atomic. Do not store lists in a single column. Create multiple rows instead.

**Bad (violates 1NF):**
```
Student | Languages
Alice   | Java, Python, Go
```

**Good (1NF):**
```
Student | Language
Alice   | Java
Alice   | Python
Alice   | Go
```

### Composition in DB

Use `ON DELETE CASCADE` for composition (child rows should be deleted when the parent is deleted).

Be careful: some databases require manual cascade setup. Safe approach: delete child rows first in code, then delete the parent row.

### SQL Example — Relationship Table

```sql
CREATE TABLE PersonObjects (
    PersonID  INT,
    ObjectID  INT,
    FOREIGN KEY (PersonID) REFERENCES Persons(ID),
    FOREIGN KEY (ObjectID) REFERENCES Objects(ID),
    PRIMARY KEY (PersonID, ObjectID)
);
```

---

## 8. Pattern Selection Cheatsheet

| Situation | Pattern |
|---|---|
| Multiple subclasses to create by type | Factory |
| Object with many optional fields | Builder |
| One shared instance | Singleton |
| Clone an existing object | Prototype |
| Make incompatible interfaces work together | Adapter |
| Add behavior to an object at runtime | Decorator |
| Swap algorithm at runtime | Strategy |
| Notify multiple objects of a change | Observer |
| State-dependent behavior in one object | State |
| Pass request through a chain of handlers | Chain of Responsibility |
| Encapsulate a request as an object | Command |
| Fixed algorithm steps with customizable sub-steps | Template Method |
| Create families of related objects | Abstract Factory |

---

## 9. Interview Dos and Don'ts

**DO:**
- Clarify requirements before designing
- Use enums for status and type fields
- Use interfaces for anything that might change
- Apply Builder for complex objects
- Explain your design decisions as you draw
- Mention fail-fast and validation early

**DON'T:**
- Start coding before clarifying
- Use `String` for type or status
- Put everything in one God class
- Hardcode business rules in logic
- Forget edge cases (full lot, double booking, zero balance, unknown country)

---

## 10. Quick Vocabulary

| Term | Meaning |
|---|---|
| Composition | Has-a (strong). Child cannot exist without parent. |
| Aggregation | Has-a (weak). Child can exist independently. |
| Cohesion | How focused a class is on one thing. High cohesion = good. |
| Coupling | How dependent classes are on each other. Low coupling = good. |
| Encapsulation | Hiding internal state. Access via methods only. |
| Abstraction | Hiding how something works; showing only what it does. |
| Polymorphism | Same interface, different runtime behavior. |
| Runtime Polymorphism | `Validator v = new INValidator()` — calling `v.validate()` dispatches to `INValidator`. |
| Fail Fast | Check the cheapest, most likely failure first. Avoid doing expensive work if early checks fail. |

---

## Key Takeaway

LLD is about designing systems that are easy to read, change, and test.

Every pattern and principle in this course solves one of three problems:
1. Code that is hard to change → SOLID principles
2. Code that creates objects poorly → creational patterns (Singleton, Builder, Factory, Prototype)
3. Code that does not compose well → structural and behavioral patterns (Adapter, Decorator, Strategy, Observer, Chain of Responsibility, State, Command, Template Method)
