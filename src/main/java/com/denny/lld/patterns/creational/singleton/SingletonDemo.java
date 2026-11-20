
package com.denny.lld.patterns.creational.singleton;

public class SingletonDemo {

    public static void main(String[] args) {

        System.out.println("=== Basic Singleton ===");

        BasicSingleton basic1 = BasicSingleton.getInstance();
        BasicSingleton basic2 = BasicSingleton.getInstance();

        System.out.println("Same object: " + (basic1 == basic2));
        basic1.execute();


        System.out.println("\n=== Eager Singleton ===");

        EagerSingleton eager1 = EagerSingleton.getInstance();
        EagerSingleton eager2 = EagerSingleton.getInstance();

        System.out.println("Same object: " + (eager1 == eager2));
        eager1.execute();


        System.out.println("\n=== Synchronized Singleton ===");

        SynchronizedSingleton sync1 = SynchronizedSingleton.getInstance();
        SynchronizedSingleton sync2 = SynchronizedSingleton.getInstance();

        System.out.println("Same object: " + (sync1 == sync2));
        sync1.execute();


        System.out.println("\n=== Double-Checked Locking Singleton ===");

        DoubleCheckedLockingSingleton dcl1 =
                DoubleCheckedLockingSingleton.getInstance();

        DoubleCheckedLockingSingleton dcl2 =
                DoubleCheckedLockingSingleton.getInstance();

        System.out.println("Same object: " + (dcl1 == dcl2));
        dcl1.execute();
    }
}
