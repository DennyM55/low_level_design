package com.denny.lld.patterns.structural.adapter;

public class PayPalApi {
    public void makePayment(double amount) {
        System.out.println("PayPal paid: " + amount);
    }
}