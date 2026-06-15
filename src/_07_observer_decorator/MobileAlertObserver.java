package _07_observer_decorator;

public class MobileAlertObserver implements Observer {

    @Override
    public void update(String stockName, double newPrice) {
        System.out.println("[Mobile Alert] " + stockName + " price changed to " + newPrice);
    }
}
