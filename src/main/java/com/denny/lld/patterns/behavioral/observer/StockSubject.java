package com.denny.lld.patterns.behavioral.observer;

public interface StockSubject {

    void register(Observer observer);

    void remove(Observer observer);

    void notifyObservers();
}
