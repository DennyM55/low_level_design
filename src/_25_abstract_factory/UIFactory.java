package _25_abstract_factory;

/**
 * Abstract Factory interface.
 * Each concrete factory creates a complete, consistent UI family.
 */
public interface UIFactory {
    Button   createButton();
    Checkbox createCheckbox();
}
