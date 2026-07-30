package com.denny.lld.patterns.behavioral.command;

/**
 * Demonstrates the Command Pattern with a text editor.
 * Each action is encapsulated as a Command object.
 * Undo reverses the last command by calling undo() on it.
 */
public class Main {

    public static void main(String[] args) {
        TextEditor editor = new TextEditor();
        CommandHistory history = new CommandHistory();

        System.out.println("=== Command Pattern: Text Editor with Undo ===\n");

        // Type three words
        history.execute(new TypeCommand(editor, "Hello "));
        System.out.println("Content: \"" + editor.getContent() + "\"");

        history.execute(new TypeCommand(editor, "World"));
        System.out.println("Content: \"" + editor.getContent() + "\"");

        history.execute(new TypeCommand(editor, "!"));
        System.out.println("Content: \"" + editor.getContent() + "\"");

        System.out.println();
        System.out.println("--- Undo last 2 commands ---");

        history.undo();
        System.out.println("Content: \"" + editor.getContent() + "\"");

        history.undo();
        System.out.println("Content: \"" + editor.getContent() + "\"");

        System.out.println();
        System.out.println("--- Try undo when history has one more entry ---");
        history.undo();
        System.out.println("Content: \"" + editor.getContent() + "\"");

        System.out.println();
        System.out.println("--- Undo on empty history ---");
        history.undo();
    }
}
