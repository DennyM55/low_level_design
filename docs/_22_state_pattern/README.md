# State Design Pattern

## Simple Meaning

State Pattern allows an object to change its behavior when its internal state changes. Instead of writing a giant `if/else` or `switch` block for every state, each state is a separate class.

---

## Why It Exists

Some objects behave differently depending on what situation they are in. A vending machine that has no product selected behaves differently from one that is waiting for payment. An ATM with no card inserted behaves differently from one where the card is inserted.

Without State Pattern, you handle this with nested `if/else` everywhere:

```java
void insertMoney(double amount) {
    if (currentState == State.IDLE) {
        throw new IllegalStateException("Select a product first");
    } else if (currentState == State.PRODUCT_SELECTED) {
        if (amount >= price) {
            dispense();
            currentState = State.IDLE;
        }
    } else if (currentState == State.DISPENSING) {
        throw new IllegalStateException("Already dispensing");
    }
}
```

Problems:
- Every method has a large `if/else` block.
- Adding a new state means editing every method.
- The class grows uncontrollably.

---

## The State Pattern Solution

1. Create a `State` interface with all the actions the object can perform.
2. Create one class per state, each implementing `State`.
3. The context object (Vending Machine) holds a reference to the current `State` and delegates all method calls to it.

---

## Vending Machine Example

### States

```
IDLE ─── selectProduct() ──> PRODUCT_SELECTED
PRODUCT_SELECTED ─── insertMoney(enough) ──> DISPENSING
PRODUCT_SELECTED ─── cancel() ──> IDLE
DISPENSING ─── dispense() ──> IDLE
```

### State Interface

```java
public interface VendingMachineState {
    void selectProduct(String code, VendingMachine machine);
    void insertMoney(double amount, VendingMachine machine);
    void cancel(VendingMachine machine);
    void dispense(VendingMachine machine);
}
```

### Context

```java
public class VendingMachine {
    private VendingMachineState currentState;
    private String selectedProductCode;
    private double insertedAmount;

    public VendingMachine() {
        this.currentState = new IdleState();
    }

    public void setState(VendingMachineState state) {
        this.currentState = state;
    }

    public void selectProduct(String code) {
        currentState.selectProduct(code, this);
    }

    public void insertMoney(double amount) {
        currentState.insertMoney(amount, this);
    }

    public void cancel() {
        currentState.cancel(this);
    }
}
```

### Idle State

```java
public class IdleState implements VendingMachineState {
    public void selectProduct(String code, VendingMachine machine) {
        machine.setSelectedProduct(code);
        machine.setState(new ProductSelectedState());
        System.out.println("Product selected: " + code);
    }

    public void insertMoney(double amount, VendingMachine machine) {
        System.out.println("Please select a product first.");
    }

    public void cancel(VendingMachine machine) {
        System.out.println("Nothing to cancel.");
    }

    public void dispense(VendingMachine machine) {
        System.out.println("No product selected.");
    }
}
```

---

## Class Diagram

```
VendingMachineState (interface)
  + selectProduct(code, machine): void
  + insertMoney(amount, machine): void
  + cancel(machine): void
  + dispense(machine): void

IdleState implements VendingMachineState
ProductSelectedState implements VendingMachineState
DispensingState implements VendingMachineState

VendingMachine (Context)
  - currentState: VendingMachineState
  - selectedProduct: String
  - insertedAmount: double
  + setState(state): void
  + selectProduct(code): void
  + insertMoney(amount): void
  + cancel(): void
```

---

## When to Use State Pattern

- An object's behavior depends on its current state.
- There are many states and each state has different responses to the same events.
- State transitions need to be controlled and explicit.
- You want to avoid large `if/else` or `switch` blocks that check state.

---

## Real-World Examples

| System | States |
|---|---|
| Vending Machine | IDLE, PRODUCT_SELECTED, DISPENSING |
| ATM | NO_CARD, CARD_INSERTED, PIN_VERIFIED, TRANSACTION |
| Elevator | IDLE, MOVING_UP, MOVING_DOWN, MAINTENANCE |
| Order | PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED |
| Traffic Light | RED, GREEN, YELLOW |
| TCP Connection | CLOSED, LISTEN, SYN_SENT, ESTABLISHED |

---

## State vs Strategy

| | Strategy | State |
|---|---|---|
| Who changes the behavior? | Client swaps the strategy explicitly | State transitions happen internally |
| Does the state know about other states? | No | Usually yes — state sets the next state |
| Number of implementations | Many strategies, used interchangeably | One per state, transitions are controlled |

---

## Run

From the `src` directory:

```bash
mvn compile
java -cp target/classes com.denny.lld.patterns.behavioral.state.Main
```
