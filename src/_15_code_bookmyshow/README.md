# BookMyShow — Code

This package contains the Java implementation of the movie booking system designed in `_14_design_bookmyshow`.

## Files

| File | Role |
|---|---|
| `SeatCategory.java` | Enum: SILVER, GOLD, PLATINUM |
| `SeatStatus.java` | Enum: AVAILABLE, LOCKED, BOOKED |
| `BookingStatus.java` | Enum: PENDING, CONFIRMED, CANCELLED |
| `Movie.java` | Movie details |
| `Seat.java` | A single seat in a show |
| `Show.java` | A movie showing with seats and locking logic |
| `User.java` | Customer |
| `Booking.java` | Booking record (Builder Pattern) |
| `BookingService.java` | Orchestrates seat locking, payment, and confirmation |
| `Main.java` | Demo: creates show, books seats |

## How To Run

From the `src` directory:

```bash
javac _15_code_bookmyshow/*.java
java _15_code_bookmyshow.Main
```

## Key Design Points

- `Show.lockSeats()` is `synchronized` to prevent race conditions when two users try to book the same seat.
- `Booking` uses the Builder Pattern for clean construction with validation.
- `BookingService` orchestrates the full flow: lock → pay → confirm or release.

See `_14_design_bookmyshow/README.md` for the full design walkthrough.
