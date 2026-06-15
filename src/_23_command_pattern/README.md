# Command Design Pattern

## Simple Meaning

Command Pattern encapsulates a request as an object. This lets you parameterize methods with requests, queue or log requests, and support undo/redo operations.

---

## Why It Exists

In a chess game or a TicTacToe game, you want to:
- Record every move (for replay or audit)
- Undo the last move
- Queue moves for replay

Without Command Pattern, the game logic directly applies each move. There is no history, no undo, no queuing.

---

## Core Structure

| Role | Description |
|---|---|
| **Command** (interface) | Declares `execute()` and `undo()` |
| **ConcreteCommand** | Implements Command; holds a reference to the Receiver |
| **Receiver** | The object that does the actual work |
| **Invoker** | Holds the command and calls `execute()` |
| **Client** | Creates ConcreteCommand objects and sets them on the Invoker |

---

## Text Editor Example

```java
public interface Command {
    void execute();
    void undo();
}

public class TypeCommand implements Command {
    private TextEditor editor;
    private String text;

    public TypeCommand(TextEditor editor, String text) {
        this.editor = editor;
        this.text   = text;
    }

    public void execute() { editor.type(text); }
    public void undo()    { editor.delete(text.length()); }
}

public class TextEditor {
    private StringBuilder content = new StringBuilder();

    public void type(String text) { content.append(text); }
    public void delete(int count) {
        int start = Math.max(0, content.length() - count);
        content.delete(start, content.length());
    }
    public String getContent() { return content.toString(); }
}

public class CommandHistory {
    private Deque<Command> history = new ArrayDeque<>();

    public void execute(Command cmd) {
        cmd.execute();
        history.push(cmd);
    }

    public void undo() {
        if (!history.isEmpty()) {
            history.pop().undo();
        }
    }
}
```

---

## Game Move Example (TicTacToe)

```java
public interface GameCommand {
    void execute();
    void undo();
}

public class PlaceMarkCommand implements GameCommand {
    private Board board;
    private int row, col;
    private Player player;

    public PlaceMarkCommand(Board board, int row, int col, Player player) {
        this.board  = board;
        this.row    = row;
        this.col    = col;
        this.player = player;
    }

    public void execute() { board.placeMark(row, col, player.getMark()); }
    public void undo()    { board.clearMark(row, col); }
}
```

---

## Class Diagram

```
Command (interface)
  + execute(): void
  + undo(): void

TypeCommand implements Command
  - editor: TextEditor
  - text: String

DeleteCommand implements Command
  - editor: TextEditor
  - count: int

TextEditor (Receiver)
  - content: StringBuilder
  + type(text): void
  + delete(count): void

CommandHistory (Invoker)
  - history: Deque<Command>
  + execute(command): void
  + undo(): void
```

---

## When to Use

- You want to support undo/redo operations.
- You want to queue requests for later execution.
- You want to log all operations (audit trail).
- You want to implement transactions (execute a set of commands atomically).
- TicTacToe, Chess, text editors, order processing with rollback.

---

## Command vs Strategy

| | Strategy | Command |
|---|---|---|
| Purpose | Swap an algorithm | Encapsulate a request as an object |
| Undo support | No | Yes |
| Queuing | No | Yes |
| Logging | No | Yes |

---

## Run

From the `src` directory:

```bash
javac _23_command_pattern/*.java
java _23_command_pattern.Main
```
