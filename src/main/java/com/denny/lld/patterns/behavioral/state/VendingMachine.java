package com.denny.lld.patterns.behavioral.state;

/**
 * VendingMachine is the Context in the State Pattern.
 *
 * It holds a reference to the current state and delegates all user
 * actions to that state. The state itself decides how to handle the
 * action and which state to transition to next.
 */
public class VendingMachine {

    private VendingMachineState currentState;
    private String selectedProduct;
    private double insertedAmount;

    public VendingMachine() {
        this.currentState   = new IdleState();
        this.selectedProduct = null;
        this.insertedAmount  = 0.0;
    }

    // --- Delegated actions ---

    public void selectProduct(String productCode) {
        currentState.selectProduct(productCode, this);
    }

    public void insertMoney(double amount) {
        currentState.insertMoney(amount, this);
    }

    public void cancel() {
        currentState.cancel(this);
    }

    public void dispense() {
        currentState.dispense(this);
    }

    // --- State management ---

    public void setState(VendingMachineState state) {
        this.currentState = state;
    }

    // --- Product and amount helpers ---

    public String getSelectedProduct() { return selectedProduct; }
    public void setSelectedProduct(String product) { this.selectedProduct = product; }

    public double getInsertedAmount() { return insertedAmount; }
    public void setInsertedAmount(double amount) { this.insertedAmount = amount; }
    public void addInsertedAmount(double amount) { this.insertedAmount += amount; }
}
