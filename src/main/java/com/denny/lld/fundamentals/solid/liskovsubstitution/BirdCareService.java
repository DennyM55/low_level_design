package com.denny.lld.fundamentals.solid.liskovsubstitution;

class BirdCareService {
    void feed(Bird bird) {
        bird.eat();
    }
}
