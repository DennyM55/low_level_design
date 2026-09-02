package com.denny.lld.patterns.creational.abstractfactory;

/** Creates the Windows family of UI components. */
public class WindowsUIFactory implements UIFactory {
    @Override public Button   createButton()   { return new WindowsButton(); }
    @Override public Checkbox createCheckbox() { return new WindowsCheckbox(); }
}
