package com.denny.lld.fundamentals.solid.dependencyinversion;

class EmailNotifier implements Notifier {
    public void notifyCustomer(Order order, String message) {
        System.out.println("Email sent to " + order.getCustomerEmail() + ": " + message);
    }
}
