package _06_adapter_strategy;

/**
 * This is a third-party or legacy class that uses a different method signature.
 * We cannot change this class.
 */
public class LegacyPayPalClient {

    public void processPayment(double amount, String currency) {
        System.out.println("[PayPal Legacy] processPayment called: amount=" + amount + ", currency=" + currency);
    }
}
