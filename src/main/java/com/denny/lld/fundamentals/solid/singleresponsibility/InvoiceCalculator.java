package com.denny.lld.fundamentals.solid.singleresponsibility;

import java.math.BigDecimal;

class InvoiceCalculator {
    BigDecimal calculateTotal(Invoice invoice) {
        BigDecimal total = BigDecimal.ZERO;
        for (Product product : invoice.getProducts()) {
            total = total.add(product.getPrice());
        }
        return total;
    }
}
