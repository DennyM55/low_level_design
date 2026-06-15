package _01_solid._05_d_dependency_inversion;

interface Notifier {
    void notifyCustomer(Order order, String message);
}
