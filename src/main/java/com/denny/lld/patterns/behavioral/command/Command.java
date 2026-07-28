package com.denny.lld.patterns.behavioral.command;

/**
 * Command interface — all commands must support execute and undo.
 */
public interface Command {
    void execute();
    void undo();
}
