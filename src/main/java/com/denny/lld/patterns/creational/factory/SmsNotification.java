package com.denny.lld.patterns.creational.factory;

public class SmsNotification extends Notification {

    @Override
    public void send(String message) {
        System.out.println("[SMS] Sending SMS notification: " + message);
    }
}
