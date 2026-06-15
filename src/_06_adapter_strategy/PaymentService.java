package _06_adapter_strategy;

public class PaymentService {

    private final PaymentGateway gateway;

    public PaymentService(PaymentGateway gateway) {
        this.gateway = gateway;
    }

    public void checkout(double amount, String currency) {
        gateway.pay(amount, currency);
    }
}
