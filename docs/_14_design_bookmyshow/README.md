# BookMyShow — LLD Design

## Requirements Clarification

Questions to ask:

- What can users search by? (Movie title, language, city, theatre, showtime)
- Can multiple users try to book the same seat at the same time? (Concurrency)
- What are the booking steps? (Search → select show → select seats → payment → confirmation)
- Should we support cancellations?
- Should we support coupons/discount codes?
- How many seats can one user book in one transaction?
- Is there a waitlist when shows are full?
- Do we need an admin who can add movies and shows?

## Scope for This Design

- Cinema brands have multiple cinema centres (physical locations).
- Each cinema centre has multiple halls (screens/audis).
- A Hall has physical seats (HallSeat). A show creates virtual ShowSeats mapped to physical seats.
- Movies have a name, director, description, and language.
- A Show represents a specific movie playing in a specific hall at a specific time.
- A customer searches for movies by name or language, selects a show, selects seats, pays, and receives a booking.
- Seat status: AVAILABLE → LOCKED (during payment window) → BOOKED.
- Coupon/discount support.
- Admin can add movies, shows, and block shows.
- A Guest can search but must register to book.

---

## Entities

### CinemaBrand

The top-level brand (e.g., PVR, INOX).

Fields: `brandId`, `name`

---

### CinemaCenter

A physical cinema location.

Fields: `centreId`, `address`, `name`

Relationship: `CinemaBrand 1 <>-- * CinemaCenter` (composition — brand owns centres)

---

### CinemaHall

One screen/audi inside a cinema centre.

Fields: `hallId`, `hallNumber`, `numberOfSeats`

Relationship: `CinemaCenter 1 <>-- * CinemaHall` (composition)

---

### HallSeat

A physical chair in a hall. Does not change per show.

Fields: `seatId`, `location`, `type` (DELUX / NORMAL / etc.)

Note: HallSeat does NOT have `isReserved` — that belongs to ShowSeat. HallSeat does NOT have a price — price is set per show.

Relationship: `CinemaHall 1 <>-- * HallSeat` (composition)

---

### Movie

Fields: `movieId`, `name`, `director`, `description`, `language`

---

### Show

A movie playing in a hall at a specific time.

Fields: `showId`, `createdOn`, `startTime`, `endTime`

Relationships:
- `Movie 1 <>-- * Show` (composition — show belongs to a movie)
- `CinemaHall 1 -- * Show` (a hall can have multiple shows at different times)

---

### ShowSeat

A virtual seat for a particular show. This is what gets reserved.

Fields: `showSeatId`, `seatNumber`, `isReserved`, `price`

Relationships:
- `HallSeat 1 <>-- * ShowSeat` (each physical seat spawns one ShowSeat per show)
- `Show 1 <>-- * ShowSeat`
- `Booking 1 -- * ShowSeat`

Why separate HallSeat and ShowSeat? Because the same physical chair A3 can be reserved for the morning show but available for the evening show. The reservation state belongs to the show, not the physical chair.

---

### Booking

Fields: `bookingId`, `numberOfSeats`, `bookingTime`, `status`

Methods: `cancel()`

Relationship: `Booking * --<> 1 Show`

---

### Payment

Fields: `paymentId`, `amount`, `transactionId`

Subclasses: `CashPayment`, `CardPayment`

Relationship: `Payment 1 -- 0..1 Coupon`, `Payment 1 -- Booking`

---

### Coupon

Fields: `couponId`, `discountAmount`, `expiryDate`

---

### Person (base class)

Fields: `name`, `address`, `email`

Subclasses: `Customer`, `Admin`, `FrontDesk`

---

### Customer

Extends `Person`.

Methods: `makeBooking()`

Relationship: `Customer 1 ----> * Booking`

---

### Admin

Extends `Person`.

Methods: `addMovie()`, `addShow()`, `blockShow()`

---

### FrontDesk

Extends `Person`.

Relationship: `FrontDesk 1 ---> * Booking` (receptionist at the counter can book on behalf of customers)

---

### Guest

Does NOT extend `Person` (a guest has no registered account yet).

Methods: `register()`

---

### Account

Fields: `accountId`, `password`

Relationship: `Account 1 --<> 1 Person` (composition — account belongs to the person)

---

### Search (Interface)

```java
interface Search {
    List<Movie> byName(String name);
    List<Movie> byLanguage(String language);
}
```

Both `Person` instances and `Guest` instances can use Search.

---

### Catalogue

Implements `Search`. Maintains in-memory maps for fast lookup.

Fields:
- `movieTitles: Map<String, List<Movie>>` (name → movies)
- `languageTitles: Map<String, List<Movie>>` (language → movies)

Methods: `byName()`, `byLanguage()`

---

## Full Class Diagram (Text)

```
CinemaBrand
  - brandId: String
  - name: String

CinemaCenter
  - centreId: String
  - name: String
  - address: String

CinemaHall
  - hallId: String
  - hallNumber: int
  - numberOfSeats: int

HallSeat
  - seatId: String
  - location: String
  - type: String  (DELUX / NORMAL)

Movie
  - movieId: String
  - name: String
  - director: String
  - description: String
  - language: String

Show
  - showId: String
  - createdOn: Date
  - startTime: Time
  - endTime: Time

ShowSeat
  - showSeatId: String
  - seatNumber: int
  - isReserved: boolean
  - price: double

Booking
  - bookingId: String
  - numberOfSeats: int
  - bookingTime: DateTime
  - status: BookingStatus
  + cancel(): void

Payment (abstract)
  - paymentId: String
  - amount: double
  - transactionId: String

CashPayment extends Payment
CardPayment extends Payment

Coupon
  - couponId: int
  - discountAmount: double
  - expiryDate: Date

Person
  - name: String
  - address: String
  - email: String

Customer extends Person
  + makeBooking(): void

Admin extends Person
  + addMovie(): void
  + addShow(): void
  + blockShow(): void

FrontDesk extends Person

Account
  - accountId: String
  - password: String

Guest
  + register(): void

<<interface>> Search
  + byName(name): List<Movie>
  + byLanguage(lang): List<Movie>

Catalogue implements Search
  - movieTitles: Map<String, List<Movie>>
  - languageTitles: Map<String, List<Movie>>
```

---

## Relationships Summary

```
CinemaBrand 1 <>---- * CinemaCenter     (composition)
CinemaCenter 1 <>---- * CinemaHall      (composition)
CinemaHall 1 <>---- * HallSeat          (composition)
CinemaHall 1 ---- * Show                (association)
Movie 1 <>---- * Show                   (composition)
Show 1 <>---- * ShowSeat                (composition)
HallSeat 1 <>---- * ShowSeat            (composition)
Booking * ----<> 1 Show                 (composition)
Booking 1 ---- * ShowSeat               (association)
Payment 1 ---- 1 Booking                (association)
Payment 1 ---- 0..1 Coupon              (association)
Account 1 ----<> 1 Person               (composition)
Customer 1 ----> * Booking              (association)
Admin 1 ---- * Show                     (association)
Admin 1 ---- * Movie                    (association)
Person ---uses--> Search                (dependency)
Guest ---uses--> Search                 (dependency)
```

---

## Concurrency Concern — Seat Locking

Two users can try to book seat A5 for the same show at the same time.

Solution:

1. When a user selects seats, lock them (status = LOCKED).
2. Hold the lock for a maximum time window (e.g., 10 minutes).
3. If payment succeeds → status = BOOKED.
4. If payment fails or times out → release lock (status = AVAILABLE).

In a single JVM: `synchronized` on the `lockSeats()` method.

In a distributed system: Redis distributed lock or database row-level lock.

---

## Database Schema Notes

Each entity maps to a table. Attributes become columns. The primary key identifies a unique row.

For relationships, create join tables with the primary keys of both entities as foreign keys.

For example, `Booking` and `ShowSeat` have a many-to-many relationship:

```sql
CREATE TABLE BookingSeats (
    BookingID  INT,
    ShowSeatID INT,
    FOREIGN KEY (BookingID)  REFERENCES Booking(ID),
    FOREIGN KEY (ShowSeatID) REFERENCES ShowSeat(ID),
    PRIMARY KEY (BookingID, ShowSeatID)
);
```

**Normalization principle (1NF):** Data must be atomic. Do not store a list of seat numbers in a single column. Instead, create one row per booking–seat combination.

**Anomaly types to avoid:**
- **Insert anomaly:** Cannot record data about a show until at least one booking exists.
- **Delete anomaly:** Deleting the last booking for a show erases show data.
- **Update anomaly:** Updating a movie's name requires updating thousands of Show rows.

These are solved by mapping each entity to its own table and using foreign keys.

---

## Design Patterns Applied

| Pattern | Where | Why |
|---|---|---|
| Builder | Booking | Many optional fields, validation needed |
| Singleton | Catalogue, BookingService | One shared service instance |
| Strategy | Payment | Support multiple payment methods |
| Observer | Booking confirmation | Notify customer via email/SMS after booking |
| Factory | Payment type selection | Create CashPayment or CardPayment based on method |

---

## Booking Flow (Sequence)

```
User
│
├── Catalogue.byName("Inception")
│   └── Returns List<Movie>
│
├── getShows(movieId, city, date)
│   └── Returns List<Show>
│
├── getAvailableSeats(showId)
│   └── Returns List<ShowSeat> where isReserved == false
│
├── lockSeats(showId, seatIds, userId)
│   └── Sets selected ShowSeats to LOCKED
│   └── Returns lock confirmation + total price
│
├── processPayment(amount, paymentMethod, couponCode)
│   └── Applies coupon if provided
│   └── Calls Payment gateway
│
├── If payment SUCCESS
│   └── confirmBooking() → ShowSeats.isReserved = true
│   └── Creates Booking record
│   └── Sends confirmation (Observer)
│
└── If payment FAILED
    └── releaseSeats() → ShowSeats back to isReserved = false
```

---

## See Code

Implementation is in `_15_code_bookmyshow`.
