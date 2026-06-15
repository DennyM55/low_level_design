package _01_solid._05_d_dependency_inversion;

class Order {
    private final String orderId;
    private final String customerEmail;

    Order(String orderId, String customerEmail) {
        this.orderId = orderId;
        this.customerEmail = customerEmail;
    }

    String getOrderId() {
        return orderId;
    }

    String getCustomerEmail() {
        return customerEmail;
    }
}
