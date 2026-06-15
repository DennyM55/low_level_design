package _06_adapter_strategy;

public interface PaymentGateway {

    void pay(double amount, String currency);
}
