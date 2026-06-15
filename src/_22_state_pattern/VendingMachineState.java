package _22_state_pattern;

/**
 * State interface — every concrete state must handle all possible actions.
 *
 * Passing the VendingMachine (context) to each method lets the state
 * transition the machine to a new state when appropriate.
 */
public interface VendingMachineState {
    void selectProduct(String productCode, VendingMachine machine);
    void insertMoney(double amount, VendingMachine machine);
    void cancel(VendingMachine machine);
    void dispense(VendingMachine machine);
}
