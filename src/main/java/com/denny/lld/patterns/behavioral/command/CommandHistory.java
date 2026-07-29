package com.denny.lld.patterns.behavioral.command;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Invoker — executes commands and keeps a history for undo support.
 */
public class CommandHistory {
    private final Deque<Command> history = new ArrayDeque<>();

    public void execute(Command command) {
        command.execute();
        history.push(command);
    }

    public void undo() {
        if (!history.isEmpty()) {
            Command last = history.pop();
            last.undo();
        } else {
            System.out.println("[CommandHistory] Nothing to undo.");
        }
    }

    public int size() {
        return history.size();
    }
}
