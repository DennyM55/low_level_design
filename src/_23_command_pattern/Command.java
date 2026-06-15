package _23_command_pattern;

/**
 * Command interface — all commands must support execute and undo.
 */
public interface Command {
    void execute();
    void undo();
}
