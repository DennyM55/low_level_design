package com.denny.lld.patterns.creational.factory;

public class EmailNotification extends Notification {

    @Override
    public void send(String message) {
        System.out.println("[EMAIL] Sending email notification: " + message);
    }
}
