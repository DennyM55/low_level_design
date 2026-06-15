# Hotel Management System — LLD Design

## Requirements Clarification

- Support multiple room types (suite, king, delux, standard)?
- Can both guests and receptionists book rooms?
- How many guests per room booking?
- Track housekeeping tasks?
- Room service (food, massage) — paid separately from room?
- Payment via card and cash?
- Cancel booking — reassign to same room type?

## Scope

- Multiple hotel brands, each with multiple branches (physical buildings).
- Each branch has many rooms of different types.
- Guests and receptionists can search and book rooms.
- Housekeepers track and complete housekeeping tasks.
- Customers request room service.
- Invoice generated at checkout.

---

## Entities and Class Sketch

### HotelBrand / HotelBranch

```java
class HotelBrand {
    String name;
    List<HotelBranch> branches;   // composition
}

class HotelBranch {
    String name;
    Address location;
    List<Room> rooms;             // composition
}
```

`HotelBrand 1 <>---- * HotelBranch`

---

### Room

```java
class Room {
    String roomNumber;
    String roomType;   // SUITE, KING, DELUX, STANDARD
    double price;
    boolean isBooked;

    void checkIn()  {}
    void checkOut() {}
}
```

`HotelBranch 1 <>---- * Room`

---

### RoomKey

```java
class RoomKey {
    String id;
    String barcode;
    Date issuedAt;
    boolean isActive;
}
```

`Room 1 <>---- * RoomKey` (composition — keys belong to a room)

---

### People (Person hierarchy)

```java
class Person {
    String name;
    String phoneNumber;
    String emailId;
}

class Guest extends Person {
    void createBooking() {}
}

class Receptionist extends Person {
    void createBooking() {}
}

class Housekeeper extends Person {
    void addServiceCharge() {}
}
```

`Person <--- ext --- Guest / Receptionist / Housekeeper`

---

### Account

```java
class Account {
    String name;
    String email;
    String password;
}
```

`Person 1 <>------ 1 Account` (composition)

---

### Booking

```java
class Booking {
    String bookingNumber;
    DateTime startTime;
    DateTime endTime;
    BookingStatus status;  // SUCCESSFUL, FAILED, CANCELLED

    void modify() {}
    void cancel() {}
}
```

**Key design question:** If a room is removed (leaking/haunted), do we cancel the booking?  
**Answer:** No — reassign to the same room type. So `Room → Booking` is **association**, not composition.

```
Room 1 ---- * Booking        (association — room can have multiple bookings over time)
Booking * -----> 1 Guest     (association — booking is for a guest)
```

---

### HouseKeepingTask

```java
class HouseKeepingTask {
    String description;
    DateTime startTime;
    Duration duration;
}
```

```
Housekeeper 1 -----> * HouseKeepingTask   (association)
Room 1 -----> * HouseKeepingTask          (association — same room can have many tasks)
```

Note: If room is destroyed, past housekeeping tasks should still be kept for records. Use a soft-delete or status flag on the room.

---

### Room Service

Room service is paid (unlike housekeeping). Different services exist:

```java
class FoodService   { String menuItem; }
class MassageService { String serviceType; }
```

`Housekeeper 1 ------- * Service` (a housekeeper/staff can provide many services)

---

### Invoice

```java
class InvoiceItem {
    double amount;
    DateTime time;
}

class Invoice {
    List<InvoiceItem> items;   // composition
    double totalAmount;
}
```

```
InvoiceItem 1 ------- 1 Service
InvoiceItem * ------<> 1 Invoice   (composition)
RoomBooking 1 -------> 1 Invoice   (association — generated at checkout)
```

Payment happens at the end, not for each service individually.

---

### Search (Interface)

```java
interface Search {
    List<Room> searchByRoomType(String type);
    List<Room> searchByFloor(int floor);
}
```

**Why an interface?** Search can apply to multiple things — rooms, users, tasks. An interface allows different modules to implement search differently.

Who uses Search? `Receptionist` and `Guest`.

---

## Full Class Diagram (Text)

```
HotelBrand
  - name: String
  - branches: List<HotelBranch>    (composition)

HotelBranch
  - name: String
  - location: Address
  - rooms: List<Room>              (composition)

Room
  - roomNumber: String
  - roomType: String
  - price: double
  - isBooked: boolean
  + checkIn(): void
  + checkOut(): void

RoomKey
  - id: String
  - barcode: String
  - issuedAt: Date
  - isActive: boolean

Person
  - name: String
  - phoneNumber: String
  - emailId: String

Guest extends Person
  + createBooking(): void

Receptionist extends Person
  + createBooking(): void

Housekeeper extends Person
  + addServiceCharge(): void

Account
  - name: String
  - email: String
  - password: String

Booking
  - bookingNumber: String
  - startTime: DateTime
  - endTime: DateTime
  - status: BookingStatus
  + modify(): void
  + cancel(): void

HouseKeepingTask
  - description: String
  - startTime: DateTime
  - duration: Duration

InvoiceItem
  - amount: double
  - time: DateTime

Invoice
  - items: List<InvoiceItem>
  - totalAmount: double

<<interface>> Search
  + searchByRoomType(type): List<Room>
  + searchByFloor(floor): List<Room>
```

---

## Relationships Summary

```
HotelBrand 1 <>---- * HotelBranch        (composition)
HotelBranch 1 <>---- * Room              (composition)
Room 1 <>---- * RoomKey                  (composition)
Person <--- ext --- Guest                (inheritance)
Person <--- ext --- Receptionist         (inheritance)
Person <--- ext --- Housekeeper          (inheritance)
Person 1 <>------ 1 Account              (composition)
Room 1 ---- * Booking                    (association)
Booking * -----> 1 Guest                 (association)
Housekeeper 1 -----> * HouseKeepingTask  (association)
Room 1 -----> * HouseKeepingTask         (association)
RoomBooking 1 -------> 1 Invoice         (association)
InvoiceItem * ------<> 1 Invoice         (composition)
```

---

## Design Patterns Applicable

| Pattern | Where | Why |
|---|---|---|
| Builder | Booking | Many optional fields (extras, special requests) |
| Factory | Room type creation | Return correct Room subtype by type string |
| Observer | Housekeeping task assigned | Notify housekeeper of new task |
| Strategy | Payment | CashPayment vs CardPayment |
| Singleton | Search/Catalogue | One shared room index |

---

## DB Schema Notes

**Composition in DB:** Use `ON DELETE CASCADE` for compositions (e.g., deleting a Room cascades to RoomKeys).

**Association in DB:** Use foreign keys without cascade. Deleting a Room should NOT delete Bookings (they are historical records).

**Normalization:** Each entity maps to one table. Many-to-many relationships (e.g., Booking ↔ HouseKeepingTask if needed) get a join table.
