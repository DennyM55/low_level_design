package com.denny.lld.patterns.structural.adapter;

public class RazorpayGateway implements PaymentGateway {

    @Override
    public void pay(double amount, String currency) {
        System.out.println("[Razorpay] Processing payment of " + amount + " " + currency);
    }
}
