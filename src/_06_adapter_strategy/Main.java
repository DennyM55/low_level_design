package _06_adapter_strategy;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== Adapter Pattern Demo ===");

        PaymentService razorpayService = new PaymentService(new RazorpayGateway());
        razorpayService.checkout(5000.0, "INR");

        LegacyPayPalClient legacyClient = new LegacyPayPalClient();
        PaymentService payPalService = new PaymentService(new PayPalAdapter(legacyClient));
        payPalService.checkout(2500.0, "USD");

        System.out.println();
        System.out.println("=== Strategy Pattern Demo ===");

        int[] data1 = {64, 34, 25, 12, 22, 11, 90};
        int[] data2 = {10, 7, 8, 9, 1, 5};

        SortContext context = new SortContext(new BubbleSortStrategy());
        context.sort(data1);

        context.setStrategy(new QuickSortStrategy());
        context.sort(data2);
    }
}
