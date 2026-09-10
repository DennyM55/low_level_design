package com.denny.lld.patterns.creational.factory;

public class PushNotification extends Notification {

    @Override
    public void send(String message) {
        System.out.println("[PUSH] Sending push notification: " + message);
    }
}
