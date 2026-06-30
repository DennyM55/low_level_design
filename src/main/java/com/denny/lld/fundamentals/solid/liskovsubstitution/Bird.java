package com.denny.lld.fundamentals.solid.liskovsubstitution;

abstract class Bird {
    private final String name;

    Bird(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }

    abstract void eat();
}
