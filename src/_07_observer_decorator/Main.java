package _07_observer_decorator;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== Observer Pattern Demo ===");

        StockMarket appleStock = new StockMarket("AAPL", 170.0);
        MobileAlertObserver mobileAlert = new MobileAlertObserver();
        EmailAlertObserver emailAlert = new EmailAlertObserver();

        appleStock.register(mobileAlert);
        appleStock.register(emailAlert);
        appleStock.setPrice(175.5);
        appleStock.setPrice(180.0);

        appleStock.remove(emailAlert);
        System.out.println("EmailAlertObserver removed.");
        appleStock.setPrice(172.3);

        System.out.println();
        System.out.println("=== Decorator Pattern Demo ===");

        Coffee coffee = new SimpleCoffee();
        System.out.println(coffee.getDescription() + ": Rs. " + coffee.getCost());

        coffee = new MilkDecorator(coffee);
        System.out.println(coffee.getDescription() + ": Rs. " + coffee.getCost());

        coffee = new SugarDecorator(coffee);
        System.out.println(coffee.getDescription() + ": Rs. " + coffee.getCost());

        coffee = new WhipDecorator(coffee);
        System.out.println(coffee.getDescription() + ": Rs. " + coffee.getCost());
    }
}
