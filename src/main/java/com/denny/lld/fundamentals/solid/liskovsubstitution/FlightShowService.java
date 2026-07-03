package com.denny.lld.fundamentals.solid.liskovsubstitution;

class FlightShowService {
    void startFlyingShow(FlyingBird bird) {
        bird.fly();
    }
}
