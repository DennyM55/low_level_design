package com.denny.lld.fundamentals.solid.dependencyinversion;

class MySqlOrderRepository implements OrderRepository {
    public void save(Order order) {
        System.out.println("Order " + order.getOrderId() + " saved in MySQL.");
    }
}
