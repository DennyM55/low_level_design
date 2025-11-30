# Abstract Factory Design Pattern

## Simple Meaning

Abstract Factory creates families of related objects without specifying their concrete classes. It is a factory of factories.

---

## Difference Between Factory and Abstract Factory

| Factory Method | Abstract Factory |
|---|---|
| Creates one type of object | Creates a family of related objects |
| One factory method per type | One factory interface with multiple creation methods |
| Example: `createButton()` | Example: `createButton()` AND `createCheckbox()` AND `createTextField()` |

**Factory Method:** You need a payment object. The factory gives you `CashPayment` or `CardPayment`.

**Abstract Factory:** You need a complete UI toolkit. The factory gives you a matching set of `Button`, `Checkbox`, and `TextField` — all for the same platform (Windows OR Mac, but not mixed).

---

## Real-World Motivation

Imagine building a cross-platform UI library:

- Windows uses `WindowsButton`, `WindowsCheckbox`, `WindowsTextField`
- Mac uses `MacButton`, `MacCheckbox`, `MacTextField`

You want to ensure consistency: if the app runs on Mac, ALL components are Mac-style. You should never get a `WindowsButton` mixed with a `MacCheckbox`.

Abstract Factory solves this by having one factory per platform that creates the entire family.

---

## Structure

```java
// Product interfaces
interface Button   { void render(); }
interface Checkbox { void render(); }

// Concrete products — Windows family
class WindowsButton   implements Button   { public void render() { System.out.println("Windows Button"); } }
class WindowsCheckbox implements Checkbox { public void render() { System.out.println("Windows Checkbox"); } }

// Concrete products — Mac family
class MacButton   implements Button   { public void render() { System.out.println("Mac Button"); } }
class MacCheckbox implements Checkbox { public void render() { System.out.println("Mac Checkbox"); } }

// Abstract Factory interface
interface UIFactory {
    Button   createButton();
    Checkbox createCheckbox();
}

// Concrete factories — each creates one complete family
class WindowsUIFactory implements UIFactory {
    public Button   createButton()   { return new WindowsButton(); }
    public Checkbox createCheckbox() { return new WindowsCheckbox(); }
}

class MacUIFactory implements UIFactory {
    public Button   createButton()   { return new MacButton(); }
    public Checkbox createCheckbox() { return new MacCheckbox(); }
}
```

Usage:

```java
UIFactory factory;
String os = System.getProperty("os.name");

if (os.contains("Windows"))  factory = new WindowsUIFactory();
else                          factory = new MacUIFactory();

Button   btn = factory.createButton();
Checkbox chk = factory.createCheckbox();

btn.render();   // guaranteed to match the platform
chk.render();   // no mixing of platforms
```

---

## Class Diagram

```
Button (interface)
  + render(): void

Checkbox (interface)
  + render(): void

WindowsButton   implements Button
WindowsCheckbox implements Checkbox
MacButton       implements Button
MacCheckbox     implements Checkbox

UIFactory (interface)
  + createButton(): Button
  + createCheckbox(): Checkbox

WindowsUIFactory implements UIFactory
MacUIFactory     implements UIFactory

Application
  - factory: UIFactory
  - button: Button
  - checkbox: Checkbox
  + renderUI(): void
```

---

## When to Use Abstract Factory

- The system needs to be independent of how its objects are created.
- The system needs to work with multiple families of related products.
- You want to enforce consistency among products in a family — no mixing allowed.
- Adding a new family of products means adding a new factory class, not modifying existing ones.

---

## Real-World Examples

| Context | Families |
|---|---|
| UI toolkits | Windows components, Mac components, Linux components |
| Database drivers | MySQL objects, PostgreSQL objects, SQLite objects |
| Payment systems | Credit card flow (authorize, capture, refund), PayPal flow |
| Theme systems | Light theme widgets, Dark theme widgets |
| Cloud providers | AWS resources (S3, EC2), GCP resources (GCS, GCE) |

---

## Abstract Factory vs Builder

| | Abstract Factory | Builder |
|---|---|---|
| Purpose | Create families of related objects | Construct one complex object step by step |
| Result | Multiple related objects | One object |
| Focus | Consistency across a family | Flexible construction of one object |

---

## Run

From the `src` directory:

```bash
mvn compile
java -cp target/classes com.denny.lld.patterns.creational.abstractfactory.Main
```
