# Low Level Design — Java Notes and Examples

A complete Java reference for Low Level Design concepts, design patterns, and system design problems.

All code is written in Java using plain OOP. Each folder contains:

- A `README.md` with concept explanations, design decisions, and interview notes
- Java source files with working, runnable examples

---

## Topics

| # | Folder | Topic | Description |
|---|---|---|---|
| 01 | `src/_01_solid` | SOLID Principles | All five principles + OOP fundamentals (classes, inheritance, encapsulation, abstraction) |
| 02 | `src/_02_singleton` | Singleton Pattern | Eager, lazy, double-checked, Bill Pugh; ResourceInitializer/DB connection example |
| 03 | `src/_03_builder` | Builder Pattern | Bird/Booking examples; backward compatibility; static inner Builder |
| 04 | `src/_04_factory` | Factory Pattern | Address validator example; country→Validator mapping; runtime polymorphism |
| 05 | `src/_05_prototype` | Prototype Pattern | Cloning objects, shallow vs deep copy |
| 06 | `src/_06_adapter_strategy` | Adapter + Strategy | Adapting incompatible interfaces; swapping algorithms |
| 07 | `src/_07_observer_decorator` | Observer + Decorator | Event-driven notifications; wrapping behavior |
| 08 | `src/_08_uml_diagrams` | UML Diagrams | Class diagrams, sequence diagrams, use case diagrams, actors, system boundary |
| 09 | `src/_09_lld_interview_approach` | Interview Approach | 5-step framework; Coffee Machine walkthrough; pattern selection guide |
| 10 | `src/_10_design_tictactoe` | TicTacToe — Design | Entities, class diagram, game flow |
| 11 | `src/_11_code_tictactoe` | TicTacToe — Code | Full interactive Java implementation |
| 12 | `src/_12_design_parking_lot` | Parking Lot — Design | Multi-floor lot, slots, tickets, fees |
| 13 | `src/_13_code_parking_lot` | Parking Lot — Code | Complete Java implementation with Strategy for fees |
| 14 | `src/_14_design_bookmyshow` | BookMyShow — Design | Full class diagram (CinemaBrand→HallSeat→ShowSeat); DB schema; seat locking |
| 15 | `src/_15_code_bookmyshow` | BookMyShow — Code | Full Java with synchronized seat locking + Builder |
| 16 | `src/_16_design_splitwise` | Splitwise — Design | Equal/exact/percentage splits; balance map; simplify debts; machine coding format |
| 17 | `src/_17_code_splitwise` | Splitwise — Code | Complete expense splitting with balance tracking |
| 18 | `src/_18_design_google_calendar` | Google Calendar — Design | Events, attendees, RSVP, conflict detection |
| 19 | `src/_19_lld_contest` | Practice Problems | Vending Machine, Library, Elevator, ATM, StackOverflow, Hotel Management |
| 20 | `src/_20_low_level_design_revision` | Revision Guide | Complete cheatsheet: all patterns, system designs, DB schema, vocab |
| 21 | `src/_21_chain_of_responsibility` | Chain of Responsibility | Cascader pattern; ValidatorCascadeFactory; fail-fast; Singleton+Factory combo |
| 22 | `src/_22_state_pattern` | State Pattern | Vending Machine states; behavior changes with internal state |
| 23 | `src/_23_command_pattern` | Command Pattern | Text editor with undo/redo; encapsulating requests as objects |
| 24 | `src/_24_template_method` | Template Method | Fixed algorithm skeleton; CSV/JSON processors; hook methods |
| 25 | `src/_25_abstract_factory` | Abstract Factory | Cross-platform UI; families of related objects; factory of factories |
| 26 | `src/_26_design_stackoverflow` | StackOverflow — Design | Question/Answer/Comment/Bounty/Badge; Member/Moderator/Admin hierarchy |
| 27 | `src/_27_design_hotel_management` | Hotel Management — Design | HotelBrand/Branch/Room/RoomKey; housekeeping; invoice; search interface |

---

## SOLID Principles — Sub-folders

| Sub-folder | Principle | Example |
|---|---|---|
| `_01_s_single_responsibility` | Single Responsibility | Invoice split into Calculator, Printer, Repository |
| `_02_o_open_closed` | Open/Closed | Discount policies as separate classes |
| `_03_l_liskov_substitution` | Liskov Substitution | Bird hierarchy with FlyingBird abstraction |
| `_04_i_interface_segregation` | Interface Segregation | Restaurant roles: Cook, Server, Cleaner |
| `_05_d_dependency_inversion` | Dependency Inversion | OrderService depends on OrderRepository interface |

---

## Design Patterns Quick Reference

| Pattern | Type | Key Idea |
|---|---|---|
| Singleton | Creational | One instance, controlled access point |
| Builder | Creational | Step-by-step construction, optional fields, backward compatible |
| Factory | Creational | Central object creation by type; almost always Singleton |
| Prototype | Creational | Clone existing objects (shallow vs deep) |
| Abstract Factory | Creational | Create families of related objects; factory of factories |
| Adapter | Structural | Wrap incompatible interface |
| Decorator | Structural | Add behavior by wrapping |
| Strategy | Behavioral | Inject and swap algorithms at runtime |
| Observer | Behavioral | Notify multiple listeners on state change |
| Chain of Responsibility | Behavioral | Pass request through ordered handlers; fail-fast |
| State | Behavioral | Object behavior changes with internal state |
| Command | Behavioral | Encapsulate request as object; supports undo/redo |
| Template Method | Behavioral | Fixed algorithm skeleton; subclasses customize specific steps |

---

## How To Run Any Example

All examples can be compiled and run from the `src` directory.

### From the command line

```bash
cd src
javac _21_chain_of_responsibility/*.java
java _21_chain_of_responsibility.Main
```

### From IntelliJ IDEA

Open any `Main.java` file and click the green run button. Set project SDK to Java 11 or higher.

---

## Running All New Pattern Examples

```bash
cd src

# Chain of Responsibility (Cascader)
javac _21_chain_of_responsibility/*.java && java _21_chain_of_responsibility.Main

# State Pattern
javac _22_state_pattern/*.java && java _22_state_pattern.Main

# Command Pattern
javac _23_command_pattern/*.java && java _23_command_pattern.Main

# Template Method
javac _24_template_method/*.java && java _24_template_method.Main

# Abstract Factory
javac _25_abstract_factory/*.java && java _25_abstract_factory.Main
```

---

## Java Version

Examples use Java 11+ features (`List.of()`, `Set.of()`, etc.). All packages compile cleanly with Java 21.

---

## Notes

All code and written notes are original and written for learning purposes. Design patterns (Singleton, Factory, Builder, etc.) are standard computer science concepts in the public domain.
