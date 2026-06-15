package _01_solid._05_d_dependency_inversion;

class EmailNotifier implements Notifier {
    public void notifyCustomer(Order order, String message) {
        System.out.println("Email sent to " + order.getCustomerEmail() + ": " + message);
    }
}
