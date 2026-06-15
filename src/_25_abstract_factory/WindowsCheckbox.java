package _25_abstract_factory;

public class WindowsCheckbox implements Checkbox {
    @Override public void render() { System.out.println("[Windows] Rendering Windows-style checkbox"); }
    @Override public void toggle() { System.out.println("[Windows] Windows checkbox toggled"); }
}
