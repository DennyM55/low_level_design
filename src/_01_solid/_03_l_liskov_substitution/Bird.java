package _01_solid._03_l_liskov_substitution;

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
