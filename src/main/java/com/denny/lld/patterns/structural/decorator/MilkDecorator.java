package com.denny.lld.patterns.structural.decorator;

public class MilkDecorator extends CoffeeDecorator {

    public MilkDecorator(Coffee wrapped) {
        super(wrapped);
    }

    @Override
    public double getCost() {
        return wrapped.getCost() + 15.0;
    }

    @Override
    public String getDescription() {
        return wrapped.getDescription() + ", Milk";
    }
}
