# Parking Lot — Code

This package contains the complete Java implementation of the Parking Lot designed in `_12_design_parking_lot`.

## Files

| File | Role |
|---|---|
| `VehicleType.java` | Enum: BIKE, CAR, TRUCK |
| `SlotType.java` | Enum: TWO_WHEELER, COMPACT, LARGE |
| `Vehicle.java` | Abstract vehicle base class |
| `Bike.java` | Bike extends Vehicle |
| `Car.java` | Car extends Vehicle |
| `Truck.java` | Truck extends Vehicle |
| `ParkingSlot.java` | A single slot on a floor |
| `ParkingFloor.java` | A floor with many slots |
| `Ticket.java` | Entry/exit record for a vehicle |
| `FeeStrategy.java` | Interface for fee calculation |
| `HourlyFeeStrategy.java` | Fee = hours × rate per vehicle type |
| `ParkingLot.java` | Singleton controller |
| `Main.java` | Demo: park and exit multiple vehicles |

## How To Run

From the `src` directory:

```bash
javac _13_code_parking_lot/*.java
java _13_code_parking_lot.Main
```

## Key Design Points

- `ParkingLot` is a Singleton — one lot per application
- Strategy Pattern: fee calculation is injected via `FeeStrategy`
- `ParkingFloor.getAvailableSlot()` maps vehicle type to slot type before searching
- Minimum charge is 1 hour even if parked for less

See `_12_design_parking_lot/README.md` for the full design walkthrough.
