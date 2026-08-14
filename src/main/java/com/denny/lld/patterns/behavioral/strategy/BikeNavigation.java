
package com.denny.lld.patterns.behavioral.strategy;

public class BikeNavigation implements NavigationStrategy {

    @Override
    public void navigate(Point from, Point to) {
        System.out.println("Calculating bike route");
    }
}
