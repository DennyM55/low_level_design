package _01_solid._05_d_dependency_inversion;

class OrderService {
    private final OrderRepository orderRepository;
    private final Notifier notifier;

    OrderService(OrderRepository orderRepository, Notifier notifier) {
        this.orderRepository = orderRepository;
        this.notifier = notifier;
    }

    void placeOrder(Order order) {
        orderRepository.save(order);
        notifier.notifyCustomer(order, "Your order has been placed successfully.");
    }
}
