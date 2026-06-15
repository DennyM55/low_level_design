package _01_solid._01_s_single_responsibility;

import java.util.List;

class Invoice {
    private final String invoiceNumber;
    private final List<Product> products;

    Invoice(String invoiceNumber, List<Product> products) {
        this.invoiceNumber = invoiceNumber;
        this.products = products;
    }

    String getInvoiceNumber() {
        return invoiceNumber;
    }

    List<Product> getProducts() {
        return products;
    }
}
