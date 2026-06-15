package _01_solid._05_d_dependency_inversion;

public class Main {
    public static void main(String[] args) {
        OrderRepository repository = new MySqlOrderRepository();
        Notifier notifier = new EmailNotifier();

        OrderService orderService = new OrderService(repository, notifier);
        orderService.placeOrder(new Order("ORD-501", "customer@example.com"));
    }
}
