package _22_state_pattern;

/** Machine is currently dispensing a product. */
public class DispensingState implements VendingMachineState {

    @Override
    public void selectProduct(String productCode, VendingMachine machine) {
        System.out.println("[Dispensing] Please wait — currently dispensing.");
    }

    @Override
    public void insertMoney(double amount, VendingMachine machine) {
        System.out.println("[Dispensing] Please wait — currently dispensing.");
    }

    @Override
    public void cancel(VendingMachine machine) {
        System.out.println("[Dispensing] Cannot cancel during dispensing.");
    }

    @Override
    public void dispense(VendingMachine machine) {
        System.out.println("[Dispensing] Dispensing product: " + machine.getSelectedProduct());
        double change = machine.getInsertedAmount() - 1.50;
        if (change > 0) {
            System.out.printf("[Dispensing] Returning change: %.2f%n", change);
        }
        machine.setInsertedAmount(0);
        machine.setSelectedProduct(null);
        machine.setState(new IdleState());
        System.out.println("[Dispensing] Done. Machine is now IDLE.");
    }
}
