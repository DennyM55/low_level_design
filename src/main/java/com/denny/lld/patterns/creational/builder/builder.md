
# Builder Design Pattern

**Category:** Creational Design Pattern

## What is Builder?

Builder constructs complex objects step by step.

Instead of passing many parameters to a constructor,
we configure a Builder and call build().

## Problem

Creating objects with many parameters causes:

- Long constructors
- Confusing parameter order
- Too many overloaded constructors
- Difficult handling of optional fields
- Validation complexity

This is known as the Telescoping Constructor problem.

## Solution

Use a Builder to collect values before constructing
the final object.

## Components

1. Student - Target class.
2. StudentBuilder - Static nested Builder.
3. Setter-like methods - Configure attributes.
4. build() - Validate and create the Student.
5. BuilderDemo - Client.

## Execution Flow

Student.builder()
|
v
new StudentBuilder()
|
v
setName()
|
v
setAge()
|
v
setEmail()
|
v
setBatchId()
|
v
build()
|
v
Validate attributes
|
v
new Student(this)
|
v
Student object returned

## Why return this?

Returning the current Builder instance enables
method chaining.

## Why static nested Builder?

The Builder can be created without first creating
a Student object.

## Why private constructor?

It prevents ordinary client code from bypassing
Builder-controlled construction and validation.

## Why final fields?

They prevent reassignment after construction.

The example uses immutable field types and exposes
no setters, making Student immutable.

## Advantages

- Readable object construction
- Supports optional parameters
- Compile-time type safety
- Validation before construction
- Supports immutable objects
- Avoids telescoping constructors

## Disadvantages

- More code
- Additional Builder object
- Unnecessary for simple classes

## Spring Boot

Lombok @Builder can generate Builder methods.

Custom business validation must still be implemented.

## Interview Memory

Builder = Configure -> Validate -> Build.

Singleton controls instance count.
Factory chooses which object to create.
Builder controls how a complex object is assembled.
