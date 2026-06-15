package _01_solid._03_l_liskov_substitution;

class Penguin extends Bird {
    Penguin() {
        super("Penguin");
    }

    void eat() {
        System.out.println(getName() + " eats fish.");
    }
}
