package com.denny.lld.fundamentals.solid.interfacesegregation;

class Waiter implements Server, Cleaner {
    public void serveCustomer() {
        System.out.println("Waiter is serving the customer.");
    }

    public void cleanTable() {
        System.out.println("Waiter is cleaning the table.");
    }
}
