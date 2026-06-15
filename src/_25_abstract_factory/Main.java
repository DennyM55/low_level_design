package _25_abstract_factory;

/**
 * Demonstrates the Abstract Factory pattern.
 * The same application code works with either UI family.
 * No Windows/Mac components ever mix.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== Abstract Factory Pattern: Cross-Platform UI ===\n");

        System.out.println("--- Running with Windows UI Factory ---");
        renderUI(new WindowsUIFactory());

        System.out.println();
        System.out.println("--- Running with Mac UI Factory ---");
        renderUI(new MacUIFactory());

        System.out.println();
        System.out.println("Key point: the renderUI() method never changed.");
        System.out.println("Only the factory was swapped — the entire UI family changed.");
    }

    /** Application code works with abstract types only. */
    private static void renderUI(UIFactory factory) {
        Button   btn = factory.createButton();
        Checkbox chk = factory.createCheckbox();

        btn.render();
        btn.onClick();
        chk.render();
        chk.toggle();
    }
}
