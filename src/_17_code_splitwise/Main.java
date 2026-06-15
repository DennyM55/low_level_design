package _17_code_splitwise;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        User alice = new User("U1", "Alice", "alice@example.com");
        User bob = new User("U2", "Bob", "bob@example.com");
        User charlie = new User("U3", "Charlie", "charlie@example.com");

        ExpenseService service = new ExpenseService();

        System.out.println("=== Equal Split ===");
        // Alice pays Rs.300, split equally among Alice, Bob, Charlie
        service.addExpense(alice, 300.0,
                List.of(new EqualSplit(alice), new EqualSplit(bob), new EqualSplit(charlie)),
                "Dinner");

        System.out.println();
        System.out.println("=== Exact Split ===");
        // Bob pays Rs.500, Alice owes 200, Charlie owes 300
        service.addExpense(bob, 500.0,
                List.of(new ExactSplit(alice, 200.0), new ExactSplit(bob, 0.0), new ExactSplit(charlie, 300.0)),
                "Hotel booking");

        System.out.println();
        System.out.println("=== Percentage Split ===");
        // Charlie pays Rs.1000, split 50/30/20
        service.addExpense(charlie, 1000.0,
                List.of(new PercentageSplit(alice, 50.0), new PercentageSplit(bob, 30.0), new PercentageSplit(charlie, 20.0)),
                "Trip expenses");

        System.out.println();
        service.showAllBalances();

        System.out.println();
        service.showBalances(alice);
    }
}
