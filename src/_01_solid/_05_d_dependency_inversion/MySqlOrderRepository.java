package _01_solid._05_d_dependency_inversion;

class MySqlOrderRepository implements OrderRepository {
    public void save(Order order) {
        System.out.println("Order " + order.getOrderId() + " saved in MySQL.");
    }
}
