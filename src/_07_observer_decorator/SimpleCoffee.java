package _07_observer_decorator;

public class SimpleCoffee implements Coffee {

    @Override
    public double getCost() {
        return 50.0;
    }

    @Override
    public String getDescription() {
        return "Simple Coffee";
    }
}
