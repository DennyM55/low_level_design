package com.denny.lld.patterns.creational.builder;

public class BuilderDemo {

    public static void main(String[] args) {

        // Valid Student
        Student student = Student.builder()
                .setName("John")
                .setAge(25)
                .setEmail("john@gmail.com")
                .setBatchId("MWF")
                .build();

        System.out.println("=== Valid Student ===");
        System.out.println(student);

        // Another Student with different values
        Student student2 = Student.builder()
                .setName("Alice")
                .setAge(30)
                .setEmail("alice@gmail.com")
                .setBatchId("TTS")
                .build();

        System.out.println("\n=== Another Student ===");
        System.out.println(student2);

        // Invalid Student - validation example
        System.out.println("\n=== Invalid Student ===");

        try {
            Student invalidStudent = Student.builder()
                    .setName("Bob")
                    .setAge(15)
                    .setEmail("bob@gmail.com")
                    .setBatchId("MWF")
                    .build();

            System.out.println(invalidStudent);

        } catch (IllegalArgumentException e) {
            System.out.println("Validation failed: " + e.getMessage());
        }
    }
}
