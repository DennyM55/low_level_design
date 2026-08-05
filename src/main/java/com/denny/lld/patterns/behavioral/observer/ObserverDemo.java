package com.denny.lld.patterns.behavioral.observer;

public class ObserverDemo {

    public static void main(String[] args) {
        System.out.println("=== Observer Pattern Demo ===");

        StockMarket appleStock = new StockMarket("AAPL", 170.0);
        MobileAlertObserver mobileAlert = new MobileAlertObserver();
        EmailAlertObserver emailAlert = new EmailAlertObserver();

        appleStock.register(mobileAlert);
        appleStock.register(emailAlert);
        appleStock.setPrice(175.5);
        appleStock.setPrice(180.0);

        appleStock.remove(emailAlert);
        System.out.println("EmailAlertObserver removed.");
        appleStock.setPrice(172.3);

    }
}
