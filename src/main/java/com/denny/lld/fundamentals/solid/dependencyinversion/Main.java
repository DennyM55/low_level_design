package com.denny.lld.fundamentals.solid.dependencyinversion;

public class Main {
    public static void main(String[] args) {
        OrderRepository repository = new MySqlOrderRepository();
        Notifier notifier = new EmailNotifier();

        OrderService orderService = new OrderService(repository, notifier);
        orderService.placeOrder(new Order("ORD-501", "customer@example.com"));
    }
}
