package com.denny.lld.patterns.structural.adapter;

public class AdapterDemo {

    public static void main(String[] args) {
        System.out.println("=== Adapter Pattern Demo ===");

        PaymentService razorpayService = new PaymentService(new RazorpayGateway());
        razorpayService.checkout(5000.0, "INR");

        LegacyPayPalClient legacyClient = new LegacyPayPalClient();
        PaymentService payPalService = new PaymentService(new PayPalAdapter(legacyClient));
        payPalService.checkout(2500.0, "USD");

    }
}
