# Java Low-Level Design

A Maven-based Java reference for object-oriented design fundamentals, design patterns, and interview-oriented low-level design case studies. Implementations use plain Java and remain intentionally independent so each example can be studied and run on its own.

## Repository layout

```text
.
|-- src/
|   |-- main/java/com/denny/lld/
|   |   |-- fundamentals/
|   |   |-- patterns/
|   |   |   |-- creational/
|   |   |   |-- structural/
|   |   |   `-- behavioral/
|   |   `-- projects/
|   `-- test/java/com/denny/lld/
|-- docs/
|-- pom.xml
`-- README.md
```

The Java package tree is organized by concept. The `docs` tree retains the numbered course topic names for traceability and keeps design-only case studies separate from working implementations.

## Fundamentals

- [SOLID principles](docs/_01_solid/README.md), with an example for each principle
- [UML diagrams](docs/_08_uml_diagrams/README.md)
- [LLD interview approach](docs/_09_lld_interview_approach/README.md)
- [Revision guide](docs/_20_low_level_design_revision/README.md)

## Design patterns

| Category | Patterns |
|---|---|
| Creational | [Singleton](docs/_02_singleton/README.md), [Builder](docs/_03_builder/README.md), [Factory](docs/_04_factory/README.md), [Prototype](docs/_05_prototype/README.md), [Abstract Factory](docs/_25_abstract_factory/README.md) |
| Structural | [Adapter](docs/_06_adapter_strategy/README.md), [Decorator](docs/_07_observer_decorator/README.md) |
| Behavioral | [Strategy](docs/_06_adapter_strategy/README.md), [Observer](docs/_07_observer_decorator/README.md), [Chain of Responsibility](docs/_21_chain_of_responsibility/README.md), [State](docs/_22_state_pattern/README.md), [Command](docs/_23_command_pattern/README.md), [Template Method](docs/_24_template_method/README.md) |

The historical `_06_adapter_strategy` and `_07_observer_decorator` documentation remains combined, while their Java implementations are separated into the correct pattern packages.

## LLD case studies

| Case study | Design documentation | Java implementation |
|---|---|---|
| Tic-Tac-Toe | [Design](docs/_10_design_tictactoe/README.md) | `com.denny.lld.projects.tictactoe` ([notes](docs/_11_code_tictactoe/README.md)) |
| Parking Lot | [Design](docs/_12_design_parking_lot/README.md) | `com.denny.lld.projects.parkinglot` ([notes](docs/_13_code_parking_lot/README.md)) |
| BookMyShow | [Design](docs/_14_design_bookmyshow/README.md) | `com.denny.lld.projects.bookmyshow` ([notes](docs/_15_code_bookmyshow/README.md)) |
| Splitwise | [Design](docs/_16_design_splitwise/README.md) | `com.denny.lld.projects.splitwise` ([notes](docs/_17_code_splitwise/README.md)) |
| Google Calendar | [Design](docs/_18_design_google_calendar/README.md) | Not implemented |
| Practice contest | [Problems](docs/_19_lld_contest/README.md) | Not implemented |
| Stack Overflow | [Design](docs/_26_design_stackoverflow/README.md) | Not implemented |
| Hotel Management | [Design](docs/_27_design_hotel_management/README.md) | Not implemented |

## Build and run

Java 17 or newer and Maven 3.8+ are recommended.

```bash
mvn clean test
mvn package
```

After compiling, run an example with its fully qualified class name:

```bash
java -cp target/classes com.denny.lld.patterns.structural.adapter.AdapterDemo
java -cp target/classes com.denny.lld.patterns.behavioral.strategy.StrategyDemo
java -cp target/classes com.denny.lld.projects.parkinglot.Main
```

In IntelliJ IDEA, import the root `pom.xml`, select a Java 17+ SDK, reload the Maven project, and run any demo or `Main` class from the editor.

## Old-to-new mapping

| Original topic | Java package or documentation location |
|---|---|
| `_01_solid` | `com.denny.lld.fundamentals.solid.*`; `docs/_01_solid` |
| `_02_singleton` | `com.denny.lld.patterns.creational.singleton`; `docs/_02_singleton` |
| `_03_builder` | `com.denny.lld.patterns.creational.builder`; `docs/_03_builder` |
| `_04_factory` | `com.denny.lld.patterns.creational.factory`; `docs/_04_factory` |
| `_05_prototype` | `com.denny.lld.patterns.creational.prototype`; `docs/_05_prototype` |
| `_06_adapter_strategy` | `com.denny.lld.patterns.structural.adapter`, `com.denny.lld.patterns.behavioral.strategy`; `docs/_06_adapter_strategy` |
| `_07_observer_decorator` | `com.denny.lld.patterns.behavioral.observer`, `com.denny.lld.patterns.structural.decorator`; `docs/_07_observer_decorator` |
| `_08_uml_diagrams` | `docs/_08_uml_diagrams` |
| `_09_lld_interview_approach` | `docs/_09_lld_interview_approach` |
| `_10_design_tictactoe` | `docs/_10_design_tictactoe` |
| `_11_code_tictactoe` | `com.denny.lld.projects.tictactoe`; `docs/_11_code_tictactoe` |
| `_12_design_parking_lot` | `docs/_12_design_parking_lot` |
| `_13_code_parking_lot` | `com.denny.lld.projects.parkinglot`; `docs/_13_code_parking_lot` |
| `_14_design_bookmyshow` | `docs/_14_design_bookmyshow` |
| `_15_code_bookmyshow` | `com.denny.lld.projects.bookmyshow`; `docs/_15_code_bookmyshow` |
| `_16_design_splitwise` | `docs/_16_design_splitwise` |
| `_17_code_splitwise` | `com.denny.lld.projects.splitwise`; `docs/_17_code_splitwise` |
| `_18_design_google_calendar` | `docs/_18_design_google_calendar` |
| `_19_lld_contest` | `docs/_19_lld_contest` |
| `_20_low_level_design_revision` | `docs/_20_low_level_design_revision` |
| `_21_chain_of_responsibility` | `com.denny.lld.patterns.behavioral.chainofresponsibility`; `docs/_21_chain_of_responsibility` |
| `_22_state_pattern` | `com.denny.lld.patterns.behavioral.state`; `docs/_22_state_pattern` |
| `_23_command_pattern` | `com.denny.lld.patterns.behavioral.command`; `docs/_23_command_pattern` |
| `_24_template_method` | `com.denny.lld.patterns.behavioral.templatemethod`; `docs/_24_template_method` |
| `_25_abstract_factory` | `com.denny.lld.patterns.creational.abstractfactory`; `docs/_25_abstract_factory` |
| `_26_design_stackoverflow` | `docs/_26_design_stackoverflow` |
| `_27_design_hotel_management` | `docs/_27_design_hotel_management` |

Detailed explanations and interview notes are preserved in the corresponding files under `docs`.
