package com.denny.lld.patterns.behavioral.templatemethod;

/**
 * Demonstrates the Template Method pattern.
 *
 * CsvProcessor and JsonProcessor share the same read → parse → validate → save
 * algorithm skeleton but implement their own parsing and validation steps.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== Template Method Pattern: Data Processors ===");

        DataProcessor csvProcessor  = new CsvProcessor();
        DataProcessor jsonProcessor = new JsonProcessor();

        csvProcessor.process("employees.csv");
        jsonProcessor.process("config.json");

        System.out.println("\n=== Key observation ===");
        System.out.println("Both processors used the SAME algorithm skeleton:");
        System.out.println("  read → parse → validate → save");
        System.out.println("Only the parse and validate steps differed.");
    }
}
