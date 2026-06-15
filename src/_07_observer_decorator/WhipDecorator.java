package _07_observer_decorator;

public class WhipDecorator extends CoffeeDecorator {

    public WhipDecorator(Coffee wrapped) {
        super(wrapped);
    }

    @Override
    public double getCost() {
        return wrapped.getCost() + 20.0;
    }

    @Override
    public String getDescription() {
        return wrapped.getDescription() + ", Whip";
    }
}
