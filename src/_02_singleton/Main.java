package _02_singleton;

public class Main {
    public static void main(String[] args) {
        ApplicationLogger firstLogger = ApplicationLogger.getInstance();
        ApplicationLogger secondLogger = ApplicationLogger.getInstance();

        firstLogger.log("User service started");
        secondLogger.log("Payment service started");

        System.out.println("Same logger instance: " + (firstLogger == secondLogger));
        System.out.println("First logger hash code: " + firstLogger.hashCode());
        System.out.println("Second logger hash code: " + secondLogger.hashCode());
    }
}
