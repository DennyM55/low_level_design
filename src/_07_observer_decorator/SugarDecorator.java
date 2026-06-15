package _07_observer_decorator;

public class SugarDecorator extends CoffeeDecorator {

    public SugarDecorator(Coffee wrapped) {
        super(wrapped);
    }

    @Override
    public double getCost() {
        return wrapped.getCost() + 10.0;
    }

    @Override
    public String getDescription() {
        return wrapped.getDescription() + ", Sugar";
    }
}
