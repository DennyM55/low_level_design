package _04_factory;

public class Main {

    public static void main(String[] args) {
        Notification emailNotification = NotificationFactory.create(NotificationType.EMAIL);
        emailNotification.send("Your order has been placed.");

        Notification smsNotification = NotificationFactory.create(NotificationType.SMS);
        smsNotification.send("Your OTP is 482910.");

        Notification pushNotification = NotificationFactory.create(NotificationType.PUSH);
        pushNotification.send("You have a new message.");
    }
}
