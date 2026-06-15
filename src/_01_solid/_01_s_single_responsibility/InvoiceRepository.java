package _01_solid._01_s_single_responsibility;

class InvoiceRepository {
    void save(Invoice invoice) {
        System.out.println("Saved invoice " + invoice.getInvoiceNumber());
    }
}
