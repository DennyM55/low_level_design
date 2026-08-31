package com.denny.lld.patterns.creational.abstractfactory;

public class WindowsButton implements Button {
    @Override public void render()  { System.out.println("[Windows] Rendering Windows-style button"); }
    @Override public void onClick() { System.out.println("[Windows] Windows button clicked"); }
}
