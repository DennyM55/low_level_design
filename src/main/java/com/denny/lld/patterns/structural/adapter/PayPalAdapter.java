package com.denny.lld.patterns.structural.adapter;

class PayPalAdapter implements PaymentGateway {

    private final PayPalApi paypal = new PayPalApi();

    public void pay(double amount) {
        paypal.makePayment(amount);
    }
}