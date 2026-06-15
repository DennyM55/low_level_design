package _17_code_splitwise;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ExpenseService {

    // balances.get(owerId).get(lenderId) = amount owed
    private final Map<String, Map<String, Double>> balances = new HashMap<>();

    public Expense addExpense(User paidBy, double totalAmount, List<Split> splits, String description) {
        validateAndSetAmounts(totalAmount, splits);

        String expenseId = "EXP-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        Expense expense = new Expense(expenseId, description, totalAmount, paidBy, splits);

        for (Split split : splits) {
            User owes = split.getUser();
            if (!owes.getUserId().equals(paidBy.getUserId())) {
                updateBalance(owes.getUserId(), paidBy.getUserId(), split.getAmount());
            }
        }

        System.out.println("Expense added: \"" + description + "\" Rs." + totalAmount + " paid by " + paidBy.getName());
        return expense;
    }

    private void validateAndSetAmounts(double totalAmount, List<Split> splits) {
        if (splits.isEmpty()) {
            throw new IllegalArgumentException("Splits list cannot be empty");
        }

        Split first = splits.get(0);

        if (first instanceof EqualSplit) {
            double share = Math.round((totalAmount / splits.size()) * 100.0) / 100.0;
            for (Split s : splits) {
                s.setAmount(share);
            }

        } else if (first instanceof ExactSplit) {
            double total = splits.stream().mapToDouble(Split::getAmount).sum();
            if (Math.abs(total - totalAmount) > 0.01) {
                throw new IllegalArgumentException("Exact splits must sum to total amount. Expected: "
                        + totalAmount + ", got: " + total);
            }

        } else if (first instanceof PercentageSplit) {
            double totalPct = splits.stream()
                    .mapToDouble(s -> ((PercentageSplit) s).getPercentage())
                    .sum();
            if (Math.abs(totalPct - 100.0) > 0.01) {
                throw new IllegalArgumentException("Percentage splits must sum to 100. Got: " + totalPct);
            }
            for (Split s : splits) {
                PercentageSplit ps = (PercentageSplit) s;
                ps.setAmount(Math.round((totalAmount * ps.getPercentage() / 100.0) * 100.0) / 100.0);
            }
        }
    }

    private void updateBalance(String owerId, String lenderId, double amount) {
        balances.putIfAbsent(owerId, new HashMap<>());
        balances.get(owerId).merge(lenderId, amount, Double::sum);
    }

    public void showBalances(User user) {
        String userId = user.getUserId();
        System.out.println("Balances for " + user.getName() + ":");

        boolean hasBalance = false;
        Map<String, Double> owes = balances.getOrDefault(userId, new HashMap<>());
        for (Map.Entry<String, Double> entry : owes.entrySet()) {
            if (entry.getValue() > 0.001) {
                System.out.println("  Owes " + entry.getKey() + ": Rs." + String.format("%.2f", entry.getValue()));
                hasBalance = true;
            }
        }

        for (Map.Entry<String, Map<String, Double>> outer : balances.entrySet()) {
            Double amount = outer.getValue().get(userId);
            if (amount != null && amount > 0.001) {
                System.out.println("  " + outer.getKey() + " owes them: Rs." + String.format("%.2f", amount));
                hasBalance = true;
            }
        }

        if (!hasBalance) {
            System.out.println("  No outstanding balances.");
        }
    }

    public void showAllBalances() {
        System.out.println("All outstanding balances:");
        boolean any = false;
        for (Map.Entry<String, Map<String, Double>> outer : balances.entrySet()) {
            for (Map.Entry<String, Double> inner : outer.getValue().entrySet()) {
                if (inner.getValue() > 0.001) {
                    System.out.printf("  %s owes %s: Rs.%.2f%n", outer.getKey(), inner.getKey(), inner.getValue());
                    any = true;
                }
            }
        }
        if (!any) System.out.println("  No outstanding balances.");
    }
}
