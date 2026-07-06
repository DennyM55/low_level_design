package com.denny.lld.fundamentals.solid.liskovsubstitution;

class Penguin extends Bird {
    Penguin() {
        super("Penguin");
    }

    void eat() {
        System.out.println(getName() + " eats fish.");
    }
}
