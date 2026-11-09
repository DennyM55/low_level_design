package com.denny.lld.patterns.structural.adapter;

public class AdapterDemo {

    public static void main(String[] args) {

        PaymentGateway payment = new StripeAdapter();
        payment.pay(1000);

        payment = new PayPalAdapter();
        payment.pay(2000);
    }
}