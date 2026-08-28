package com.denny.lld.patterns.creational.abstractfactory;

/** Creates the Mac family of UI components. */
public class MacUIFactory implements UIFactory {
    @Override public Button   createButton()   { return new MacButton(); }
    @Override public Checkbox createCheckbox() { return new MacCheckbox(); }
}
