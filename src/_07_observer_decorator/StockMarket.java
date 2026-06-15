package _07_observer_decorator;

import java.util.ArrayList;
import java.util.List;

public class StockMarket implements StockSubject {

    private final String stockName;
    private double price;
    private final List<Observer> observers = new ArrayList<>();

    public StockMarket(String stockName, double initialPrice) {
        this.stockName = stockName;
        this.price = initialPrice;
    }

    @Override
    public void register(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void remove(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(stockName, price);
        }
    }

    public void setPrice(double newPrice) {
        this.price = newPrice;
        notifyObservers();
    }
}
