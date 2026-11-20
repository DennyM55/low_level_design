
package com.denny.lld.patterns.creational.singleton;

public class DoubleCheckedLockingSingleton {

    private static volatile DoubleCheckedLockingSingleton instance;

    private DoubleCheckedLockingSingleton() {
        System.out.println("Double-Checked Singleton created");
    }

    public static DoubleCheckedLockingSingleton getInstance() {

        if (instance == null) {
            synchronized (DoubleCheckedLockingSingleton.class) {
                if (instance == null) {
                    instance = new DoubleCheckedLockingSingleton();
                }
            }
        }

        return instance;
    }

    public void execute() {
        System.out.println("Double-Checked Singleton executing");
    }
}
/**
 * Concept: Thread-safe lazy initialization with reduced synchronization overhead.
 * <p>
 * Important: volatile is required for correct double-checked locking.
 */