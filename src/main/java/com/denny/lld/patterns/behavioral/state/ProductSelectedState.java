package com.denny.lld.patterns.behavioral.state;

/** A product has been chosen — waiting for payment. */
public class ProductSelectedState implements VendingMachineState {

    private static final double PRODUCT_PRICE = 1.50;

    @Override
    public void selectProduct(String productCode, VendingMachine machine) {
        System.out.println("[ProductSelected] Product already selected. Cancel first.");
    }

    @Override
    public void insertMoney(double amount, VendingMachine machine) {
        machine.addInsertedAmount(amount);
        double total = machine.getInsertedAmount();
        System.out.printf("[ProductSelected] Inserted: %.2f, Total: %.2f, Required: %.2f%n",
                amount, total, PRODUCT_PRICE);

        if (total >= PRODUCT_PRICE) {
            System.out.println("[ProductSelected] Sufficient payment. Dispensing...");
            machine.setState(new DispensingState());
            machine.dispense();
        } else {
            System.out.printf("[ProductSelected] Need %.2f more.%n", PRODUCT_PRICE - total);
        }
    }

    @Override
    public void cancel(VendingMachine machine) {
        System.out.printf("[ProductSelected] Cancelled. Returning %.2f.%n",
                machine.getInsertedAmount());
        machine.setInsertedAmount(0);
        machine.setSelectedProduct(null);
        machine.setState(new IdleState());
    }

    @Override
    public void dispense(VendingMachine machine) {
        System.out.println("[ProductSelected] Insert money first.");
    }
}
