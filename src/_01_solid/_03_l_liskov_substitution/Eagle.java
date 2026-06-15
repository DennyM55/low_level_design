package _01_solid._03_l_liskov_substitution;

class Eagle extends Bird implements FlyingBird {
    Eagle() {
        super("Eagle");
    }

    void eat() {
        System.out.println(getName() + " eats small animals.");
    }

    public void fly() {
        System.out.println(getName() + " flies high.");
    }
}
