# Parking Lot — LLD Design

## Requirements Clarification

Questions to ask:

- How many floors does the parking lot have?
- What types of vehicles does it support? (Bike, Car, Truck)
- Are there different slot types for different vehicles?
- How is pricing calculated? (Flat rate, hourly rate, per-vehicle-type rate)
- Who manages the entry and exit?
- Do we need to support multiple parking lots?
- Should we track ticket history?

## Scope for This Design

- Single parking lot with multiple floors
- Three vehicle types: Bike, Car, Truck
- Three slot types: Two-wheeler, Compact, Large
- Ticket issued at entry, closed at exit
- Fee based on hourly rate per vehicle type
- One entry and one exit gate

---

## Entities

### VehicleType (Enum)

Values: `BIKE`, `CAR`, `TRUCK`

---

### SlotType (Enum)

Values: `TWO_WHEELER`, `COMPACT`, `LARGE`

Mapping rule:
- `BIKE` → `TWO_WHEELER`
- `CAR` → `COMPACT`
- `TRUCK` → `LARGE`

---

### Vehicle (Abstract)

Fields:
- `licensePlate` — unique vehicle identifier
- `vehicleType` — VehicleType enum

Subclasses: `Bike`, `Car`, `Truck`

---

### ParkingSlot

Fields:
- `slotId` — unique identifier
- `slotType` — SlotType enum
- `occupied` — boolean flag
- `parkedVehicle` — current vehicle (null if empty)

Methods:
- `isAvailable()` — returns true if not occupied
- `parkVehicle(vehicle)` — marks as occupied
- `freeSlot()` — clears the vehicle reference

---

### ParkingFloor

Fields:
- `floorNumber` — floor identifier
- `slots` — list of ParkingSlot on this floor

Methods:
- `getAvailableSlot(vehicleType)` — finds and returns the first available matching slot

---

### ParkingLot

The central entity. Can be a Singleton.

Fields:
- `name` — lot name
- `floors` — list of ParkingFloor
- `activeTickets` — map of ticketId to Ticket

Methods:
- `getAvailableSlot(vehicleType)` — searches all floors for a matching slot
- `issueTicket(vehicle)` — finds a slot and creates a ticket
- `processExit(ticketId)` — closes ticket, calculates fee, frees slot

---

### Ticket

Fields:
- `ticketId` — unique identifier
- `vehicle` — the parked vehicle
- `parkingSlot` — the assigned slot
- `entryTime` — when the vehicle entered
- `exitTime` — when the vehicle exited (null until exit)

---

### FeeStrategy (Interface)

Method: `calculateFee(ticket)`

Implementations:
- `HourlyFeeStrategy` — fee = hours parked × rate per vehicle type

---

## Class Diagram (Text)

```
VehicleType (enum): BIKE, CAR, TRUCK
SlotType (enum): TWO_WHEELER, COMPACT, LARGE

Vehicle (abstract)
  - licensePlate: String
  - vehicleType: VehicleType

Bike extends Vehicle
Car extends Vehicle
Truck extends Vehicle

ParkingSlot
  - slotId: String
  - slotType: SlotType
  - occupied: boolean
  - parkedVehicle: Vehicle
  + isAvailable(): boolean
  + parkVehicle(vehicle): void
  + freeSlot(): void

ParkingFloor
  - floorNumber: int
  - slots: List<ParkingSlot>
  + getAvailableSlot(vehicleType): Optional<ParkingSlot>

ParkingLot (Singleton)
  - name: String
  - floors: List<ParkingFloor>
  - activeTickets: Map<String, Ticket>
  + issueTicket(vehicle): Ticket
  + processExit(ticketId): double

Ticket
  - ticketId: String
  - vehicle: Vehicle
  - parkingSlot: ParkingSlot
  - entryTime: LocalDateTime
  - exitTime: LocalDateTime

FeeStrategy (interface)
  + calculateFee(ticket): double

HourlyFeeStrategy implements FeeStrategy
```

---

## Relationships

```
ParkingLot has-a (composition) List<ParkingFloor>  → 1 to many
ParkingFloor has-a (composition) List<ParkingSlot>  → 1 to many
ParkingSlot is associated with Vehicle             → 0 to 1
Ticket is associated with Vehicle and ParkingSlot
Bike, Car, Truck are subtypes of Vehicle
ParkingLot uses FeeStrategy (Strategy Pattern)
```

---

## Design Patterns Applied

| Pattern | Where | Why |
|---|---|---|
| Singleton | ParkingLot | Only one parking lot instance |
| Factory | Vehicle creation | Caller passes VehicleType; factory returns Bike/Car/Truck |
| Strategy | Fee calculation | HourlyFeeStrategy can be swapped for DailyFeeStrategy |

---

## Slot Assignment Logic

```text
issueTicket(vehicle)
│
├── Determine required SlotType from VehicleType
│   BIKE → TWO_WHEELER
│   CAR → COMPACT
│   TRUCK → LARGE
│
├── Search ParkingFloors in order
│   └── ParkingFloor.getAvailableSlot(vehicleType)
│       └── Returns first available matching slot
│
├── If no slot found → throw ParkingLotFullException
│
├── slot.parkVehicle(vehicle)
│
└── Create and return Ticket
```

---

## Exit Logic

```text
processExit(ticketId)
│
├── Look up ticket in activeTickets map
│
├── Set ticket.exitTime = now
│
├── feeStrategy.calculateFee(ticket)
│   └── hours = ChronoUnit.HOURS.between(entryTime, exitTime)
│   └── fee = hours × rate[vehicleType]
│
├── slot.freeSlot()
│
└── Remove ticket from activeTickets
    Return fee amount
```

---

## See Code

Implementation is in `_13_code_parking_lot`.
