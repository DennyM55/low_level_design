package com.denny.lld.patterns.structural.adapter;

public interface PaymentGateway {

    void pay(double amount, String currency);
}
