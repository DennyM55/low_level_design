package com.denny.lld.patterns.creational.abstractfactory;

public class MacCheckbox implements Checkbox {
    @Override public void render() { System.out.println("[Mac] Rendering Mac-style checkbox"); }
    @Override public void toggle() { System.out.println("[Mac] Mac checkbox toggled"); }
}
