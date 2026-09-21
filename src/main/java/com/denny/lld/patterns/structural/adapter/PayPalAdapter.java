package com.denny.lld.patterns.structural.adapter;

/**
 * Adapter: wraps LegacyPayPalClient and exposes the PaymentGateway interface.
 * The caller talks to PaymentGateway. The adapter translates the call.
 */
public class PayPalAdapter implements PaymentGateway {

    private final LegacyPayPalClient legacyClient;

    public PayPalAdapter(LegacyPayPalClient legacyClient) {
        this.legacyClient = legacyClient;
    }

    @Override
    public void pay(double amount, String currency) {
        legacyClient.processPayment(amount, currency);
        System.out.println("[PayPal via Adapter] Payment of " + amount + " " + currency + " completed");
    }
}
