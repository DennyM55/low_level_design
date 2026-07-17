package com.denny.lld.fundamentals.solid.singleresponsibility;

import java.math.BigDecimal;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Invoice invoice = new Invoice(
                "INV-101",
                List.of(
                        new Product("Keyboard", new BigDecimal("1500")),
                        new Product("Mouse", new BigDecimal("700"))
                )
        );

        InvoiceCalculator calculator = new InvoiceCalculator();
        InvoicePrinter printer = new InvoicePrinter();
        InvoiceRepository repository = new InvoiceRepository();

        BigDecimal total = calculator.calculateTotal(invoice);
        System.out.println(printer.format(invoice, total));
        repository.save(invoice);
    }
}
