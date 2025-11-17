# Splitwise — LLD Design

## What Is Splitwise?

A social expense management application. When a group of people share costs (a trip, a shared apartment, a dinner), Splitwise tracks who paid what and who owes whom. It simplifies bookkeeping among groups.

---

## Requirements Clarification

Questions to ask:

- Can multiple users form a group and track shared expenses?
- What types of expense splits are supported? (Equal, exact amount, percentage)
- Do we need to support multi-currency?
- Can a user be part of multiple groups?
- Do we need to minimize the number of transactions when settling?
- Should we track a history of all settlements?
- Are there validation rules? (Percentages must add to 100, exact amounts must add to total)
- Should we send notifications when a new expense is added?

## Scope for This Design

- Multiple users can be in a group.
- Three split types: Equal, Exact, Percentage.
- Track who owes what to whom (balance report).
- Optionally simplify debts (reduce the number of transactions).
- No multi-currency in this version.
- Two decimals for amounts (currency precision), two decimals for percentage.

---

## Input/Output Format (Machine Coding Style)

This is a popular machine coding problem. The expected command interface:

```
EXPENSE <who_paid> <amount> <n_members> <member1> <member2> ... <EQUAL|EXACT|PERCENT> [values]
SHOW                     - show all current balances
SHOW <userId>            - show balance of one user against all others
PASSBOOK <userId>        - show full expense history for a user
SIMPLIFY                 - simplify/reduce all debts
```

Example inputs:

```
EXPENSE u1 1000 4 u1 u2 u3 u4 EQUAL
EXPENSE u1 1250 2 u1 u2 EXACT 370 880
EXPENSE u4 1200 4 u1 u2 u3 u4 PERCENT 40 20 20 20

SHOW u1
SHOW
```

Example output format:

```
u2 owes u1: 250.00
u3 owes u1: 250.00
u4 owes u1: 250.00
```

For `SIMPLIFY`:
```
# Before: a -100-> b, b -100-> c
# After:  a -100-> c  (simplified — one transaction instead of two)
```

---

## Entities

### User

Fields: `userId`, `name`, `email`

---

### Group

Fields: `groupId`, `name`, `members`, `expenses`

Methods: `addMember(user)`, `addExpense(expense)`

---

### Expense

The core entity. Tracks one shared expenditure.

Fields: `expenseId`, `description`, `totalAmount`, `paidBy`, `splits`, `createdAt`

The person who paid is recorded. The splits list records what each participant owes.

---

### Split (Abstract Base)

Fields: `user`, `amount`

Methods: `getAmount()` — abstract, each subclass calculates differently.

---

### SplitType (Enum)

Values: `EQUAL`, `EXACT`, `PERCENTAGE`

---

### EqualSplit

The total is divided equally among all participants.

`amount = totalAmount / numberOfMembers`

Note: if the amount does not divide evenly, the fractional cents are distributed to the first few members (the payer usually absorbs the rounding).

---

### ExactSplit

Each participant's share is specified as an exact value.

Validation: The sum of all exact amounts must equal the total.

---

### PercentageSplit

Each participant's share is a percentage of the total.

`amount = totalAmount × percentage / 100`

Validation: All percentages must sum to 100. Both the percentage and the amount should be stored to two decimal places.

---

### Balance

Tracks debt between pairs of users.

Represented as: `Map<String, Map<String, Double>>`

Interpretation: `balances.get(userA).get(userB) = X` means user A owes user B the amount X.

A positive X: A owes B. A negative X: B owes A.

---

### ExpenseService

Central service that manages all expense operations.

Fields: `balances: Map<String, Map<String, Double>>`

Methods:
- `addExpense(paidBy, amount, members, splitType, values)` — creates and stores the expense, updates balances
- `showBalances(userId)` — prints all non-zero balances for a user
- `showAllBalances()` — prints all non-zero balances for every user
- `simplifyDebts()` — runs the greedy debt minimization algorithm

---

## Class Diagram (Text)

```
SplitType (enum): EQUAL, EXACT, PERCENTAGE

User
  - userId: String
  - name: String
  - email: String

Split (abstract)
  - user: User
  - amount: double
  + getAmount(): double

EqualSplit extends Split
ExactSplit extends Split
PercentageSplit extends Split
  - percentage: double

Expense
  - expenseId: String
  - description: String
  - totalAmount: double
  - paidBy: User
  - splits: List<Split>
  - createdAt: LocalDateTime

Group
  - groupId: String
  - name: String
  - members: List<User>
  - expenses: List<Expense>
  + addMember(user): void
  + addExpense(expense): void

ExpenseService
  - balances: Map<String, Map<String, Double>>
  + addExpense(paidBy, amount, splits): Expense
  + showBalances(userId): void
  + showAllBalances(): void
  + simplifyDebts(): void
```

---

## Relationships

```
Group has-a (composition) List<User>           → 1 to many
Group has-a (composition) List<Expense>        → 1 to many
Expense has-a (composition) List<Split>        → 1 to many
Expense is associated with User (paidBy)       → many to 1
Split is associated with User (the debtor)     → 1 to 1
EqualSplit, ExactSplit, PercentageSplit extend Split
```

---

## Balance Update Logic

When User A pays 300 for a group of 3 (A, B, C) with EQUAL split:

Each person's share = 100.

Balance updates:
- B owes A 100 → `balances["B"]["A"] += 100`
- C owes A 100 → `balances["C"]["A"] += 100`

A paid for themselves (100), so no self-debt is recorded.

```java
for (Split s : expense.getSplits()) {
    if (!s.getUser().equals(paidBy)) {
        balances.get(s.getUser().getId())
                .merge(paidBy.getId(), s.getAmount(), Double::sum);
    }
}
```

---

## Settle Up / Simplify Debts

The simplify feature reduces the number of transactions needed to settle all debts.

**Example:**
```
a -100-> b
b -100-> c
```
After simplification:
```
a -100-> c
```

**Algorithm (greedy):**

1. Calculate net balance for each user (positive = they are owed money, negative = they owe money).
2. Use two pointers (or a priority queue): match the largest creditor with the largest debtor.
3. Settle the minimum of the two amounts.
4. Move pointers until all balances are zero.

This reduces the total number of transactions.

---

## Design Patterns Applied

| Pattern | Where | Why |
|---|---|---|
| Strategy | `Split` subclasses | Each split type has a different calculation algorithm |
| Factory | Creating the right `Split` subtype | Caller passes `SplitType`; factory creates the right object |

---

## Validation Rules

- EQUAL: No values needed. Just the list of members.
- EXACT: Number of values must equal number of members. Sum must equal total.
- PERCENT: Number of values must equal number of members. Sum must equal 100.
- All amounts: Use `BigDecimal` or round to 2 decimal places.

---

## See Code

Implementation is in `_17_code_splitwise`.
