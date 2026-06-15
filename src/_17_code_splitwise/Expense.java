package _17_code_splitwise;

import java.time.LocalDateTime;
import java.util.List;

public class Expense {

    private final String expenseId;
    private final String description;
    private final double totalAmount;
    private final User paidBy;
    private final List<Split> splits;
    private final LocalDateTime createdAt;

    public Expense(String expenseId, String description, double totalAmount, User paidBy, List<Split> splits) {
        this.expenseId = expenseId;
        this.description = description;
        this.totalAmount = totalAmount;
        this.paidBy = paidBy;
        this.splits = splits;
        this.createdAt = LocalDateTime.now();
    }

    public String getExpenseId() { return expenseId; }
    public String getDescription() { return description; }
    public double getTotalAmount() { return totalAmount; }
    public User getPaidBy() { return paidBy; }
    public List<Split> getSplits() { return splits; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
