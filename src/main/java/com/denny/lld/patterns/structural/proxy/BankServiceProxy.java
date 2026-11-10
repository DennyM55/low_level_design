package com.denny.lld.patterns.structural.proxy;

public class BankServiceProxy implements BankService {

    private final BankService realBank;

    public BankServiceProxy(BankService realBank) {
        this.realBank = realBank;
    }

    @Override
    public void withdraw(int amount) {
        if (amount <= 0) {
            System.out.println("Invalid amount");
            return;
        }

        System.out.println("Checking withdrawal request");
        realBank.withdraw(amount);
    }
}