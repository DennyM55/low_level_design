package _04_factory;

public class SmsNotification extends Notification {

    @Override
    public void send(String message) {
        System.out.println("[SMS] Sending SMS notification: " + message);
    }
}
