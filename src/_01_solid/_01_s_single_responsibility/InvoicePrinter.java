package _01_solid._01_s_single_responsibility;

import java.math.BigDecimal;

class InvoicePrinter {
    String format(Invoice invoice, BigDecimal total) {
        StringBuilder builder = new StringBuilder();
        builder.append("Invoice: ").append(invoice.getInvoiceNumber()).append(System.lineSeparator());

        for (Product product : invoice.getProducts()) {
            builder.append("- ")
                    .append(product.getName())
                    .append(" : ₹")
                    .append(product.getPrice())
                    .append(System.lineSeparator());
        }

        builder.append("Total: ₹").append(total);
        return builder.toString();
    }
}
