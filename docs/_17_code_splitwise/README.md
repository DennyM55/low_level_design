# Splitwise — Code

This package contains the Java implementation of the expense splitting system designed in `_16_design_splitwise`.

## Files

| File | Role |
|---|---|
| `SplitType.java` | Enum: EQUAL, EXACT, PERCENTAGE |
| `User.java` | A person in the system |
| `Split.java` | Abstract split class |
| `EqualSplit.java` | Equal division |
| `ExactSplit.java` | User specifies exact amount |
| `PercentageSplit.java` | User specifies percentage |
| `Expense.java` | An expense with payer and splits |
| `ExpenseService.java` | Core logic: add expense, update balances, show balances |
| `Main.java` | Demo: three expense types |

## How To Run

From the `src` directory:

```bash
mvn compile
java -cp target/classes com.denny.lld.projects.splitwise.Main
```

## Key Design Points

- `ExpenseService` maintains a nested map: `balances[owerId][lenderId] = amount`
- `EqualSplit` amounts are set during validation in `ExpenseService.validateAndSetAmounts()`
- `PercentageSplit` amounts are computed from percentage × total
- `ExactSplit` amounts are validated to ensure they sum to the total

See `_16_design_splitwise/README.md` for the full design walkthrough.
