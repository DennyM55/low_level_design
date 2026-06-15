# UML Diagrams for Low Level Design

UML (Unified Modeling Language) diagrams let you communicate a design before writing a single line of code.

In an LLD interview, you are expected to sketch a class diagram and sometimes a sequence diagram before jumping into implementation. A good diagram demonstrates structured, senior-level thinking.

---

## Two Ways to Explain a System

Any system can be described in two ways:

1. **Structural explanation** — what exists and how things relate.
   - Classes, their fields and methods, and the relationships between them.
   - This is what a **class diagram** captures.

2. **Behavioral explanation** — what happens at runtime.
   - How a use case unfolds step by step.
   - What the system is *responsible* for and, just as importantly, what it is *not* responsible for.
   - This is what **use case diagrams** and **sequence diagrams** capture.

Both are important. A non-technical stakeholder can understand a use case diagram. A developer needs the class diagram to write code.

---

## Use Case Diagrams

A use case diagram shows what the system does and who uses it — without showing how.

### Key Elements

| Element | Meaning |
|---|---|
| **Actor** | Someone or something that interacts with the system (person, external system) |
| **Use Case** | A specific action the system can perform (oval shape) |
| **System Boundary** | Rectangle showing what is inside the scope of the system |
| **Association** | Line connecting actor to use case — "this actor participates in this use case" |
| **Include** | One use case always triggers another (e.g., checkout always includes payment) |
| **Extend** | One use case optionally extends another (e.g., search by category extends search) |

### Use Case vs Test Case

| | Use Case | Test Case |
|---|---|---|
| Level | High level, anyone can understand | Specific, technical |
| Example (Library) | "Search for a book" | "What happens if the search term is empty?" |
| Written by | Business analyst, product manager | Developer, QA |

### Example — Online Shopping System

**Actors:** Customer, Admin

**Use cases:**
- Search product (by category, by name)
- Add to cart
- Checkout cart
- Make payment
- Manage inventory (Admin only)

**System boundary:** Everything inside the box — the shopping system itself. The actual payment processor (Stripe, PayPal) sits outside the boundary.

**Relationships:**
- "Checkout cart" includes "Make payment" (payment always happens at checkout)
- "Search by category" and "Search by name" both extend "Search product"

---

## Types of UML Diagrams

| Category | Diagram | Used For |
|---|---|---|
| Structural | Class Diagram | Classes, fields, methods, relationships |
| Structural | Object Diagram | Snapshot of objects at a specific moment |
| Structural | Component Diagram | High-level components and connections |
| Behavioral | Sequence Diagram | How objects interact over time in one scenario |
| Behavioral | Activity Diagram | Workflow or business logic flow |
| Behavioral | Use Case Diagram | Actor-system interactions |
| Behavioral | State Diagram | How an object transitions between states |

In LLD interviews, **Class Diagrams** and **Sequence Diagrams** are used most frequently.

---

## Class Diagrams

A class diagram shows:

- Classes in the system
- Their attributes (fields)
- Their methods
- Relationships and their direction

### Class Notation

```
+---------------------+
|    ClassName        |
+---------------------+
| - privateField      |
| # protectedField    |
| + publicField       |
+---------------------+
| + publicMethod()    |
| - privateMethod()   |
+---------------------+
```

Visibility symbols:

| Symbol | Meaning |
|---|---|
| `+` | public |
| `-` | private |
| `#` | protected |
| `~` | package-private |

---

## Relationships in Class Diagrams

### 1. Association

Two classes know about each other. A general relationship.

```
Order ————————— Customer
```

---

### 2. Aggregation (Has-A, weak)

One class contains a reference to another, but the contained object can exist independently.

```
Department ◇————— Employee
```

If the Department is removed, the Employee still exists. Open diamond on the container side.

---

### 3. Composition (Has-A, strong)

One class owns another. The contained object cannot exist without the parent.

```
House ◆————— Room
```

If the House is deleted, the Rooms are also deleted. Filled diamond on the container side.

---

### 4. Inheritance / Generalization (Is-A)

A child class extends a parent class.

```
Animal ◁————— Dog
Animal ◁————— Cat
```

Open triangle/arrow pointing to the parent class.

---

### 5. Realization / Implementation

A class implements an interface.

```
Flyable ◁- - - Eagle
```

Dashed line with open triangle pointing to the interface.

---

### 6. Dependency

A class temporarily uses another class (method parameter or local variable).

```
OrderService - - → EmailService
```

Dashed arrow.

---

## Multiplicity Notation

| Notation | Meaning |
|---|---|
| `1` | Exactly one |
| `0..1` | Zero or one (optional) |
| `*` | Zero or more |
| `1..*` | One or more (required) |
| `2..5` | Between 2 and 5 |

Example — One Order has one or more OrderItems:

```
Order 1 ————— 1..* OrderItem
```

---

## Composition vs Association — How to Decide

Ask: "If the parent is deleted, should the child be deleted too?"

- **Yes** → Composition (strong has-a). `◆`
- **No** → Association or Aggregation (weak has-a). `◇` or plain line.

Example from Hotel Management:
- If a Room is removed, should its RoomKeys be deleted? Probably yes — keys for a non-existent room serve no purpose. → **Composition**
- If a Room booking is cancelled, should the Guest record be deleted? No — the guest still exists. → **Association**

Example from StackOverflow:
- Deleting a Question should delete its Answers and Comments. → **Composition**
- Deleting a Member should not delete their Badges, and deleting a Badge should not delete the Member. → **Association** (many-to-many)

---

## Sequence Diagrams

A sequence diagram shows how objects interact over time in one specific scenario.

Read top to bottom. Each vertical bar is an object's lifeline.

```
Actor        OrderService    PaymentService    Database
  |               |                |               |
  |  placeOrder() |                |               |
  |───────────────>               |               |
  |               |  processPayment()             |
  |               |───────────────>               |
  |               |               |  save(order)  |
  |               |               |───────────────>
  |               |               |   success     |
  |               |               <───────────────|
  |               |  payment OK   |               |
  |               <───────────────               |
  |  confirmed    |                               |
  <───────────────                               |
```

### Elements

| Element | Symbol | Meaning |
|---|---|---|
| Lifeline | Vertical dashed line | Object exists over time |
| Activation | Thick rectangle on lifeline | Object is currently executing |
| Synchronous message | Solid arrow `──>` | Caller waits for response |
| Return message | Dashed arrow `- ->` | Response sent back |
| Self call | Arrow looping back on same lifeline | Object calls its own method |

---

## When to Use Each in an Interview

| Situation | Diagram to Use |
|---|---|
| Explaining what the system does (to anyone) | Use Case Diagram |
| Explaining the static class structure | Class Diagram |
| Explaining how a use case works step by step | Sequence Diagram |
| Explaining object state changes | State Diagram |

---

## Practical Tips for LLD Interview Drawing

1. **Start with the main entities.** What are the nouns? (User, Order, Product, Seat, etc.)
2. **Draw relationships next.** Is-A for inheritance, Has-A for composition/aggregation, uses for dependency.
3. **Add key methods.** Skip getters/setters. Focus on meaningful behavior.
4. **Use interfaces for key abstractions.** `PaymentGateway`, `NotificationService`, `Search` interface.
5. **Mark multiplicities.** One ParkingLot has many Floors. One Floor has many Slots.
6. **Draw before coding.** Even a rough sketch shows structured thinking.
7. **Explain as you draw.** The interviewer wants to follow your reasoning.

---

## Example — Parking Lot Class Diagram

```
ParkingLot
  - id: String
  - name: String
  - floors: List<ParkingFloor>
  + getAvailableSlot(vehicleType): ParkingSlot

ParkingFloor
  - floorNumber: int
  - slots: List<ParkingSlot>
  + getAvailableSlot(vehicleType): ParkingSlot

ParkingSlot
  - slotId: String
  - slotType: SlotType
  - occupied: boolean
  + isAvailable(): boolean

Vehicle (abstract)
  - licensePlate: String
  - vehicleType: VehicleType

Car extends Vehicle
Bike extends Vehicle
Truck extends Vehicle

Ticket
  - ticketId: String
  - vehicle: Vehicle
  - slot: ParkingSlot
  - entryTime: LocalDateTime

SlotType (enum): TWO_WHEELER, COMPACT, LARGE
VehicleType (enum): BIKE, CAR, TRUCK
```

---

## Key Takeaway

- Class diagrams show *structure* — what exists and how things relate.
- Use case diagrams show *scope* — what the system does and who uses it.
- Sequence diagrams show *behavior* — what happens step by step.
- Knowing when to draw each type is itself a signal of LLD maturity.
- Composition and aggregation are the most frequently tested relationships.
