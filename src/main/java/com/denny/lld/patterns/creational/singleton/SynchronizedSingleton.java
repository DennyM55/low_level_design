
package com.denny.lld.patterns.creational.singleton;

public class SynchronizedSingleton {

    private static SynchronizedSingleton instance;

    private SynchronizedSingleton() {
        System.out.println("Synchronized Singleton created");
    }

    public static synchronized SynchronizedSingleton getInstance() {
        if (instance == null) {
            instance = new SynchronizedSingleton();
        }
        return instance;
    }

    public void execute() {
        System.out.println("Synchronized Singleton executing");
    }
}
/*
* Concept: Lazy initialization with thread safety.
*
* Limitation: Every getInstance() call acquires the class lock, even after initialization.
* */