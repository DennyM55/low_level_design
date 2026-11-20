
# Singleton Design Pattern

**Category:** Creational Design Pattern

## What is Singleton?

Singleton ensures that a class has only one instance and provides
a common access point to that instance.

## Why do we need it?

Some application components need one shared instance.

Examples:
- Application configuration manager
- Shared registry
- Centralized resource manager

A production database connection pool usually manages multiple
physical connections. Singleton may be appropriate for the pool
manager, not necessarily for an individual database connection.

## How does it work?

1. Make the constructor private.
2. Store the instance in a static field.
3. Expose a public static getInstance() method.
4. Return the existing instance on subsequent calls.

## Implementations

### Basic Lazy Singleton
- Creates the object on first access.
- Not thread-safe.

### Eager Singleton
- Creates the object during class initialization.
- Thread-safe initialization.
- May initialize an unused object.

### Synchronized Singleton
- Uses a synchronized accessor method.
- Thread-safe lazy initialization.
- Acquires a lock on every call.

### Double-Checked Locking
- Checks instance before and inside synchronization.
- Uses volatile for safe publication.
- Avoids locking after initialization.

## Java Concepts

- private constructor
- static fields and methods
- synchronized
- volatile
- lazy initialization
- thread safety

## Spring Boot

Spring beans are singleton-scoped by default.

Spring manages one instance per bean definition within an
application context.

This does not automatically make a bean thread-safe.

## Interview Points

Q: Why is Singleton creational?
A: It controls object creation.

Q: Why is the constructor private?
A: To prevent ordinary external instantiation.

Q: Why is getInstance() static?
A: To access the instance without first creating an object.

Q: Why is basic lazy Singleton not thread-safe?
A: Multiple threads can pass the null check simultaneously.

Q: Why use volatile in double-checked locking?
A: To ensure correct visibility and safe publication.

## Memory Aid

Singleton = One instance + Shared access.
```
