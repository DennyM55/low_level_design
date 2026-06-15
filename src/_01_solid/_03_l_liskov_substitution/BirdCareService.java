package _01_solid._03_l_liskov_substitution;

class BirdCareService {
    void feed(Bird bird) {
        bird.eat();
    }
}
