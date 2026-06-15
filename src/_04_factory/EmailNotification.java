package _04_factory;

public class EmailNotification extends Notification {

    @Override
    public void send(String message) {
        System.out.println("[EMAIL] Sending email notification: " + message);
    }
}
