
package com.denny.lld.patterns.behavioral.strategy;

public class Navigator {

    private NavigationStrategy strategy;

    public Navigator(NavigationStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(NavigationStrategy strategy) {
        this.strategy = strategy;
    }

    public void navigate(Point from, Point to) {
        strategy.navigate(from, to);
    }

    public static void main(String[] args) {

        Point from = new Point(0, 0);
        Point to = new Point(10, 10);

        Navigator navigator = new Navigator(new CarNavigation());
        navigator.navigate(from, to);

        navigator.setStrategy(new BikeNavigation());
        navigator.navigate(from, to);

        navigator.setStrategy(new WalkNavigation());
        navigator.navigate(from, to);
    }
}
