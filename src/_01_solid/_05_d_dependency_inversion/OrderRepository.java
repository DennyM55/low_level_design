package _01_solid._05_d_dependency_inversion;

interface OrderRepository {
    void save(Order order);
}
