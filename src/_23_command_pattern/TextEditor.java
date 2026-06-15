package _23_command_pattern;

/**
 * Receiver — performs the actual work that commands delegate to.
 */
public class TextEditor {
    private final StringBuilder content = new StringBuilder();

    public void type(String text) {
        content.append(text);
    }

    public void delete(int count) {
        int start = Math.max(0, content.length() - count);
        content.delete(start, content.length());
    }

    public String getContent() {
        return content.toString();
    }
}
