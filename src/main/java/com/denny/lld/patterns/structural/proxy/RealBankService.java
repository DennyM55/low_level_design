package com.denny.lld.patterns.structural.proxy;

public class RealBankService implements BankService {

    @Override
    public void withdraw(int amount) {
        System.out.println("Withdrawn: Rs. " + amount);
    }
}