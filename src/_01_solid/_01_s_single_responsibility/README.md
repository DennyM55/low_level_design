## Single Responsibility Principle

**Simple meaning:** A class should have one main job.

A class should not change for many unrelated reasons. If one class calculates invoice totals, prints invoices, and saves invoices to a database, it has too many responsibilities.

---

## Real Problem

Imagine an invoice module.

At first, we may write one class like this:

```text
InvoiceService
    calculate total
    print invoice
    save invoice
```

This looks easy in the beginning. But later:

* Tax rule changes.
* Print format changes.
* Database logic changes.
* File saving logic changes.

Now the same class keeps changing again and again for different reasons.

That is the problem Single Responsibility Principle solves.

---

## Better Design

Instead of keeping everything in one class, we split the work:

```text
Invoice
    stores invoice data

Product
    stores product details

InvoiceCalculator
    calculates invoice total

InvoicePrinter
    prepares printable invoice text

InvoiceRepository
    saves invoice
```

Now each class has one clear responsibility.

---

## Why This Is Better

If the tax rule changes, update only:

```text
InvoiceCalculator
```

If the print format changes, update only:

```text
InvoicePrinter
```

If the database or saving logic changes, update only:

```text
InvoiceRepository
```

This makes the code easier to understand, test, and modify.

---

## Code Walkthrough

In `Main.java`:

1. `Product` represents one product.
2. `Invoice` holds the invoice number and product list.
3. `InvoiceCalculator` calculates the total amount.
4. `InvoicePrinter` creates readable invoice output.
5. `InvoiceRepository` simulates saving the invoice.
6. `Main` connects all these classes together.

The important idea is this:

```text
One class = one clear job
```

---

## Interview Explanation

You can say:

```text
Single Responsibility Principle means a class should have only one main reason to change.

For example, invoice calculation, invoice printing, and invoice saving should not be inside the same class because they change for different reasons. So we separate them into different classes.
```

---

## Run

Open CMD inside this folder:

```text
01-solid\01-s-single-responsibility
```

Then run:

```bat
javac Main.java
java Main
```

---

## Expected Output

```text
Invoice saved successfully.

Invoice Number: INV-101
Items:
Keyboard - Rs. 1500.0
Mouse - Rs. 800.0
Monitor - Rs. 12000.0
Total: Rs. 14300.0
```

---

## Key Takeaway

Single Responsibility Principle does not mean a class should have only one method.

It means a class should focus on one business responsibility.

A class can have multiple methods, but all those methods should support the same responsibility.
