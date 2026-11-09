package com.denny.lld.patterns.structural.adapter;

class StripeAdapter implements PaymentGateway {

    private final StripeApi stripe = new StripeApi();

    public void pay(double amount) {
        stripe.createPayment(amount);
    }
}