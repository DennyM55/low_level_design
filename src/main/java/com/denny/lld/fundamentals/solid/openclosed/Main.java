package com.denny.lld.fundamentals.solid.openclosed;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        CheckoutService checkoutService = new CheckoutService();
        BigDecimal cartAmount = new BigDecimal("10000");

        System.out.println("Regular customer pays: ₹" + checkoutService.finalAmount(cartAmount, new RegularCustomerDiscount()));
        System.out.println("Premium customer pays: ₹" + checkoutService.finalAmount(cartAmount, new PremiumCustomerDiscount()));
        System.out.println("Employee pays: ₹" + checkoutService.finalAmount(cartAmount, new EmployeeDiscount()));
    }
}
