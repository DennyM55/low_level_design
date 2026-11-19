
package com.denny.lld.patterns.creational.builder;

public final class Student {

    private final String name;
    private final int age;
    private final String email;
    private final String batchId;

    // Only the Builder can construct a Student
    private Student(StudentBuilder builder) {
        this.name = builder.name;
        this.age = builder.age;
        this.email = builder.email;
        this.batchId = builder.batchId;
    }

    // Entry point for creating a Builder
    public static StudentBuilder builder() {
        return new StudentBuilder();
    }

    // Static nested Builder class
    public static class StudentBuilder {

        private String name;
        private int age;
        private String email;
        private String batchId;

        public StudentBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public StudentBuilder setAge(int age) {
            this.age = age;
            return this;
        }

        public StudentBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public StudentBuilder setBatchId(String batchId) {
            this.batchId = batchId;
            return this;
        }

        public Student build() {

            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException(
                        "Name cannot be empty"
                );
            }

            if (age < 18) {
                throw new IllegalArgumentException(
                        "Age must be at least 18"
                );
            }

            if (email == null || !email.contains("@")) {
                throw new IllegalArgumentException(
                        "Invalid email address"
                );
            }

            if (batchId == null || batchId.isBlank()) {
                throw new IllegalArgumentException(
                        "Batch ID cannot be empty"
                );
            }

            return new Student(this);
        }
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getBatchId() {
        return batchId;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", batchId='" + batchId + '\'' +
                '}';
    }
}
