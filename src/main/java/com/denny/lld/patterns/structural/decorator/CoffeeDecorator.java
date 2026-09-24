package com.denny.lld.patterns.structural.decorator;

public abstract class CoffeeDecorator implements Coffee {

    protected final Coffee wrapped;

    public CoffeeDecorator(Coffee wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public double getCost() {
        return wrapped.getCost();
    }

    @Override
    public String getDescription() {
        return wrapped.getDescription();
    }
}
