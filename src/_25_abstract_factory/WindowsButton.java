package _25_abstract_factory;

public class WindowsButton implements Button {
    @Override public void render()  { System.out.println("[Windows] Rendering Windows-style button"); }
    @Override public void onClick() { System.out.println("[Windows] Windows button clicked"); }
}
