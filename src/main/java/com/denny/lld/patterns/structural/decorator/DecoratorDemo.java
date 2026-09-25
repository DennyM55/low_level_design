package com.denny.lld.patterns.structural.decorator;

public class DecoratorDemo {

    public static void main(String[] args) {
        System.out.println("=== Decorator Pattern Demo ===");

        Coffee coffee = new SimpleCoffee();
        System.out.println(coffee.getDescription() + ": Rs. " + coffee.getCost());

        coffee = new MilkDecorator(coffee);
        System.out.println(coffee.getDescription() + ": Rs. " + coffee.getCost());

        coffee = new SugarDecorator(coffee);
        System.out.println(coffee.getDescription() + ": Rs. " + coffee.getCost());

        coffee = new WhipDecorator(coffee);
        System.out.println(coffee.getDescription() + ": Rs. " + coffee.getCost());
    }
}
