package com.denny.lld.patterns.behavioral.observer;

public class EmailAlertObserver implements Observer {

    @Override
    public void update(String stockName, double newPrice) {
        System.out.println("[Email Alert] " + stockName + " price changed to " + newPrice);
    }
}
