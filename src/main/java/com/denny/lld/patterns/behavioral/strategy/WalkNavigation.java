
package com.denny.lld.patterns.behavioral.strategy;

public class WalkNavigation implements NavigationStrategy {

    @Override
    public void navigate(Point from, Point to) {
        System.out.println("Calculating walking route");
    }
}
