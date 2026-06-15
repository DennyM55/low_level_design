# LLD Contest — Practice Problems

This folder contains interview-level design problems meant to be attempted from scratch before looking at the notes.

---

## How To Use This Folder

1. Read the problem statement.
2. Apply the 5-step framework from `_09_lld_interview_approach`.
3. Identify entities, draw a class diagram on paper, then write code.
4. Compare your solution with the hints below.

---

## Problem 1 — Vending Machine

### Problem Statement

Design a vending machine.

The machine:
- Has multiple products, each with a quantity and price
- Accepts coins/notes as payment
- Dispenses the product if enough money is inserted
- Returns change if overpaid
- Shows an error if a product is out of stock or the money is insufficient

### Key Entities

```
VendingMachine, Product, Slot, PaymentModule, ChangeDispenser
```

### Key States

The machine behaves differently depending on its current state:

```
IDLE → product selected → WAITING_FOR_PAYMENT → paid → DISPENSING → back to IDLE
```

### State Transitions

| Current State | Event | Next State |
|---|---|---|
| Idle | selectProduct(productCode) | ProductSelected |
| ProductSelected | insertMoney(amount — enough) | Dispensing |
| ProductSelected | insertMoney(amount — not enough) | Stay in ProductSelected |
| ProductSelected | cancel() | Idle |
| Dispensing | dispense() | Idle |

### Relevant Patterns

- **State Pattern** for the machine's internal states
- **Singleton** for the machine itself
- **Strategy** for pricing (fixed price vs discount)

---

## Problem 2 — Library Management System

### Problem Statement

Design a library management system.

Features:
- A library has books. Each book can have multiple physical copies.
- A member can search for a book by title or author.
- A member can borrow and return a copy.
- A fine is charged for late returns.
- A librarian can add and remove books.

### Key Entities

```
Library, Book, BookItem (physical copy), Member, Librarian,
BorrowRecord, FineCalculator
```

### Key Relationships

```
Library has-a (composition) List<Book>
Book has-a (composition) List<BookItem>
BorrowRecord links Member and BookItem with borrow and return dates
```

### Relevant Patterns

- **Strategy** for fine calculation (daily fine vs weekly fine)
- **Observer** for notifying reserved users when a book becomes available
- **Factory** for creating different fine strategies

---

## Problem 3 — Elevator System

### Problem Statement

Design an elevator system for a building.

Features:
- The building has multiple floors.
- There are multiple elevators.
- Users press a button on a floor to call an elevator.
- The controller assigns the best available elevator.
- Each elevator has a current floor, direction, and status.

### Key Entities

```
Building, ElevatorController, Elevator, Floor, Button, Request
```

### Key States (per elevator)

```
IDLE, MOVING_UP, MOVING_DOWN, MAINTENANCE
```

### Dispatch Strategy

| Strategy | Description |
|---|---|
| Nearest elevator | Assign the closest elevator |
| SCAN algorithm | Elevator services all requests in one direction before reversing |

### Relevant Patterns

- **State Pattern** for elevator state
- **Strategy Pattern** for elevator selection/dispatch
- **Observer** for floor arrival notifications

---

## Problem 4 — ATM Machine

### Problem Statement

Design an ATM machine.

Features:
- User inserts a card and enters a PIN.
- User can check balance, withdraw, or deposit.
- The machine dispenses cash using available denominations.
- Maximum withdrawal limit per transaction.
- Card is blocked after 3 wrong PIN attempts.

### Key Entities

```
ATM, Card, Account, CashDispenser, TransactionRecord
```

### Cash Dispenser Algorithm

Greedy approach: use the largest denomination possible, then work down.

### Relevant Patterns

- **State Pattern** for ATM states (NoCard → CardInserted → PINVerified → Transaction → Done)
- **Chain of Responsibility** for authentication (card valid → PIN check → account check)
- **Strategy** for cash denomination selection

---

## Problem 5 — StackOverflow

### Problem Statement

Design a simplified version of StackOverflow.

Features:
- Anyone can search and view questions.
- To post a question, answer, or comment, or to vote, you must be a registered member.
- Each question can have an active bounty (prize for best answer).
- Users can earn badges for activity milestones.
- Moderators can close questions.
- Admins can block users.

### Key Use Cases

```
Search questions (Guest + Member)
Post question (Member)
Post answer (Member)
Post comment (Member)
Vote up/down (Member)
Add bounty to question (Member)
Earn badge (automatic)
Close question (Moderator)
Block user (Admin)
```

### Key Entities

```
Question, Answer, Comment, Bounty, Photo, Badge,
Member, Moderator, Admin, Account, Guest
```

### Entity Sketch

```java
class Question:
    status: Enum (OPEN, CLOSED)
    title: String
    description: String
    votes: int
    answers: List<Answer>
    comments: List<Comment>
    bounty: Bounty        // optional
    photos: List<Photo>

    void close()

class Answer:
    content: String
    votes: int
    accepted: boolean
    comments: List<Comment>

class Comment:
    content: String
    votes: int

class Bounty:
    prize: int
    expiry: DateTime
    void claim()

class Badge:
    text: String
    // Note: same badge can be shared by many members, not composition

class Member:
    age: int
    def create_question()

class Moderator extends Member:
    def close_question()

class Admin extends Member:
    def block_member()

class Account:
    password: String
    creationDate: DateTime
    def delete_account()
```

### Key Relationship Notes

- **Member → Question:** Association, not composition. Deleting a Member should not delete their Questions.
- **Question → Answer/Comment:** Composition. If a Question is deleted, its Answers and Comments go too.
- **Member ↔ Badge:** Association (many-to-many). Deleting a Badge should not delete Members, and vice versa.
- **Guest:** Does NOT extend Member. Guest can search but cannot interact.

### Relevant Patterns

- **Observer** — notify the question author when a new answer is posted
- **Strategy** — different search implementations (by keyword, by tag, by user)
- **Decorator** — wrap content with photo attachments

---

## Problem 6 — Hotel Management System

### Problem Statement

Design a hotel management system.

Features:
- Support booking different room types (suite, king, delux, standard).
- Guests and receptionists can search and book rooms.
- Track housekeeping tasks.
- Customers can request room service (food, massage).
- Payment is supported via card and cash.

### Key Use Cases

```
Search rooms by type
Book a room (Guest or Receptionist)
Check in / check out
Track housekeeping task
Request room service
Generate invoice at checkout
Cancel a booking
```

### Key Entities

```
HotelBrand, HotelBranch, Room, RoomKey, Person,
Guest, Receptionist, Housekeeper, Account,
Booking, HouseKeepingTask, RoomService, InvoiceItem, Invoice
```

### Entity Sketch

See full design in `_27_design_hotel_management`.

### Relevant Patterns

- **Builder** for Booking (many optional fields)
- **Factory** for Room type creation
- **Observer** for housekeeping task notifications
- **Strategy** for payment method selection

---

## Evaluation Checklist

After writing your solution, check:

- [ ] Did you ask clarifying questions first?
- [ ] Did you identify all entities?
- [ ] Did you draw a class diagram?
- [ ] Did you use enums for type/status?
- [ ] Did you apply at least one design pattern?
- [ ] Does each class have a single clear responsibility?
- [ ] Is the code readable without comments?
- [ ] Could the system be extended without changing core classes?
- [ ] Did you handle edge cases (out of stock, double booking, invalid input)?

---

## Key Takeaway

Practice these problems under timed conditions (45 minutes each).

The goal is not perfection. The goal is to show structured thinking, clean design, and recognizing where patterns apply and why.
