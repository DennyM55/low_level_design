package com.denny.lld.fundamentals.solid.singleresponsibility;

class InvoiceRepository {
    void save(Invoice invoice) {
        System.out.println("Saved invoice " + invoice.getInvoiceNumber());
    }
}
