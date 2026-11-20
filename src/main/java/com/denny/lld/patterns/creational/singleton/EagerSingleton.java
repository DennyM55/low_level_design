
package com.denny.lld.patterns.creational.singleton;

public class EagerSingleton {

    private static final EagerSingleton INSTANCE =
            new EagerSingleton();

    private EagerSingleton() {
        System.out.println("Eager Singleton created");
    }

    public static EagerSingleton getInstance() {
        return INSTANCE;
    }

    public void execute() {
        System.out.println("Eager Singleton executing");
    }
}
/*
*
* Concept: The object is created during class initialization.
*
* Advantage: Thread-safe initialization without explicit synchronization.
*
* Limitation: The instance may be created even if it is never requested.
* */