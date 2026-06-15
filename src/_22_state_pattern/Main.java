package _22_state_pattern;

/**
 * Demonstrates the State Pattern with a VendingMachine.
 *
 * The machine transitions through states automatically based on
 * user actions. Each state handles actions differently.
 */
public class Main {

    public static void main(String[] args) {
        VendingMachine machine = new VendingMachine();

        System.out.println("=== Scenario 1: Normal successful purchase ===");
        machine.selectProduct("A1");
        machine.insertMoney(1.00);
        machine.insertMoney(0.50);  // total = 1.50, triggers dispense
        System.out.println();

        System.out.println("=== Scenario 2: Insert money before selecting product ===");
        machine.insertMoney(2.00);  // should reject — machine is IDLE
        System.out.println();

        System.out.println("=== Scenario 3: Cancel after selecting product ===");
        machine.selectProduct("B2");
        machine.insertMoney(0.50);
        machine.cancel();
        System.out.println();

        System.out.println("=== Scenario 4: Dispense directly (no product selected) ===");
        machine.dispense();
        System.out.println();

        System.out.println("=== Scenario 5: Exact change ===");
        machine.selectProduct("C3");
        machine.insertMoney(1.50);  // exact — no change returned
    }
}
