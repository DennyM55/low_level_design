package com.denny.lld.patterns.behavioral.state;

/** Machine is idle — no product selected, no money inserted. */
public class IdleState implements VendingMachineState {

    @Override
    public void selectProduct(String productCode, VendingMachine machine) {
        System.out.println("[Idle] Product selected: " + productCode);
        machine.setSelectedProduct(productCode);
        machine.setState(new ProductSelectedState());
    }

    @Override
    public void insertMoney(double amount, VendingMachine machine) {
        System.out.println("[Idle] Please select a product first.");
    }

    @Override
    public void cancel(VendingMachine machine) {
        System.out.println("[Idle] Nothing to cancel.");
    }

    @Override
    public void dispense(VendingMachine machine) {
        System.out.println("[Idle] No product selected. Cannot dispense.");
    }
}
