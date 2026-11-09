package com.denny.lld.patterns.structural.adapter;

public class StripeApi {
    public void createPayment(double amount) {
        System.out.println("Stripe paid: " + amount);
    }
}