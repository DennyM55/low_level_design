package _23_command_pattern;

/** Types text into the editor. Undo removes what was typed. */
public class TypeCommand implements Command {
    private final TextEditor editor;
    private final String text;

    public TypeCommand(TextEditor editor, String text) {
        this.editor = editor;
        this.text   = text;
    }

    @Override
    public void execute() {
        editor.type(text);
        System.out.println("[TypeCommand] Typed: \"" + text + "\"");
    }

    @Override
    public void undo() {
        editor.delete(text.length());
        System.out.println("[TypeCommand] Undo: removed \"" + text + "\"");
    }
}
