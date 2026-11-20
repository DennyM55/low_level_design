
package com.denny.lld.patterns.creational.singleton;

public class BasicSingleton {

    private static BasicSingleton instance;

    private BasicSingleton() {
        System.out.println("Basic Singleton created");
    }

    public static BasicSingleton getInstance() {
        if (instance == null) {
            instance = new BasicSingleton();
        }
        return instance;
    }

    public void execute() {
        System.out.println("Basic Singleton executing");
    }
}
/*
Concept: Lazy initialization. The object is created when getInstance() is called for the first time.
Limitation: Not thread-safe.
*/