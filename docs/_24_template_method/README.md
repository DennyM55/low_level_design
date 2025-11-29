# Template Method Design Pattern

## Simple Meaning

Template Method defines the skeleton of an algorithm in a base class. The overall sequence of steps is fixed. Specific steps that vary by subclass are declared abstract and overridden in each subclass.

---

## Why It Exists

Many algorithms follow the same high-level structure but differ in specific steps.

For example, parsing different file formats (CSV, XML, JSON) follows the same process:
1. Open the file
2. Parse the content (differs by format)
3. Validate the data (may differ)
4. Store the result

Without Template Method, you duplicate the open/store logic in every parser. With Template Method, the open/store logic lives once in the parent class, and each subclass only provides the parsing logic.

---

## Structure

```
AbstractClass
  + templateMethod()  ← final — defines the skeleton
  + step1()           ← concrete — shared logic
  # step2()           ← abstract — subclass provides this
  # step3()           ← abstract — subclass provides this
  + step4()           ← concrete — shared logic

ConcreteClassA extends AbstractClass
  # step2() — specific to A
  # step3() — specific to A

ConcreteClassB extends AbstractClass
  # step2() — specific to B
  # step3() — specific to B
```

---

## Data Processing Example

```java
public abstract class DataProcessor {

    // Template method — final so subclasses cannot change the sequence
    public final void process(String source) {
        String rawData  = readData(source);       // step 1
        String parsed   = parseData(rawData);     // step 2 — overridden
        boolean valid   = validateData(parsed);   // step 3 — overridden
        if (valid) {
            saveData(parsed);                     // step 4
        } else {
            System.out.println("Validation failed — data not saved.");
        }
    }

    protected String readData(String source) {
        System.out.println("Reading from: " + source);
        return "raw-data-content";
    }

    protected abstract String parseData(String rawData);

    protected boolean validateData(String data) {
        return data != null && !data.isBlank();  // default validation
    }

    protected void saveData(String data) {
        System.out.println("Saving data: " + data);
    }
}

public class CsvProcessor extends DataProcessor {
    @Override
    protected String parseData(String rawData) {
        System.out.println("Parsing CSV...");
        return rawData.replace(",", "|");  // simplified CSV parsing
    }
}

public class JsonProcessor extends DataProcessor {
    @Override
    protected String parseData(String rawData) {
        System.out.println("Parsing JSON...");
        return rawData.trim();  // simplified JSON parsing
    }

    @Override
    protected boolean validateData(String data) {
        return data.startsWith("{") || data.startsWith("[");
    }
}
```

Usage:

```java
DataProcessor csv  = new CsvProcessor();
DataProcessor json = new JsonProcessor();

csv.process("data.csv");
json.process("data.json");
```

---

## Class Diagram

```
DataProcessor (abstract)
  + process(source): void       ← template method (final)
  # readData(source): String    ← concrete (shared)
  # parseData(raw): String      ← abstract (must override)
  # validateData(data): boolean ← concrete with default (can override)
  # saveData(data): void        ← concrete (shared)

CsvProcessor extends DataProcessor
  # parseData(raw): String      ← CSV-specific parsing

JsonProcessor extends DataProcessor
  # parseData(raw): String      ← JSON-specific parsing
  # validateData(data): boolean ← JSON-specific validation
```

---

## Hook Methods

A **hook** is an optional method in the abstract class with a default (often empty) implementation. Subclasses can override it but are not forced to.

```java
public abstract class GameTemplate {

    public final void play() {
        initialize();
        while (!isDone()) {
            takeTurn();
        }
        printResult();
        postGame();    // hook — optional override
    }

    protected abstract void initialize();
    protected abstract void takeTurn();
    protected abstract boolean isDone();
    protected abstract void printResult();

    protected void postGame() {}   // hook — default is empty
}
```

---

## When to Use

- Multiple classes share the same overall algorithm structure but differ in specific steps.
- You want to enforce the sequence of an algorithm while allowing customization of individual steps.
- Code duplication exists across subclasses for common setup/teardown.

---

## Real-World Examples

| System | Template |
|---|---|
| Data parsers | read → parse → validate → store |
| Game engines | initialize → loop (takeTurn → check win) → end |
| HTTP request handling | authenticate → authorize → handle → respond |
| Build tools | compile → test → package → deploy |
| Report generators | load data → format → output |

---

## Template Method vs Strategy

| | Template Method | Strategy |
|---|---|---|
| Mechanism | Inheritance — subclass overrides steps | Composition — inject algorithm object |
| Flexibility | Subclass decides specific steps | Caller decides algorithm at runtime |
| Coupling | Tighter — subclass tied to parent | Looser — algorithm is swappable |

---

## Run

From the `src` directory:

```bash
mvn compile
java -cp target/classes com.denny.lld.patterns.behavioral.templatemethod.Main
```
