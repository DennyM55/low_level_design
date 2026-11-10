package com.denny.lld.patterns.structural.proxy;

public class ProxyDemo {

    public static void main(String[] args) {

        BankService bank = new BankServiceProxy(new RealBankService());

        bank.withdraw(5000);
        bank.withdraw(-100);
    }
}