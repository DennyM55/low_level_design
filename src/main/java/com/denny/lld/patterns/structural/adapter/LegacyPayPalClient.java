package com.denny.lld.patterns.structural.adapter;

/**
 * This is a third-party or legacy class that uses a different method signature.
 * We cannot change this class.
 */
public class LegacyPayPalClient {

    public void processPayment(double amount, String currency) {
        System.out.println("[PayPal Legacy] processPayment called: amount=" + amount + ", currency=" + currency);
    }
}
