package _07_observer_decorator;

public interface StockSubject {

    void register(Observer observer);

    void remove(Observer observer);

    void notifyObservers();
}
