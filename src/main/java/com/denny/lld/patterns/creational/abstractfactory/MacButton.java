package com.denny.lld.patterns.creational.abstractfactory;

public class MacButton implements Button {
    @Override public void render()  { System.out.println("[Mac] Rendering Mac-style button"); }
    @Override public void onClick() { System.out.println("[Mac] Mac button clicked"); }
}
