package com.denny.lld.patterns.behavioral.chainofresponsibility;

/**
 * Common contract for all address validators.
 *
 * Coding against this interface (runtime polymorphism) allows the
 * ValidatorCascade to hold and invoke validators of any type — zip,
 * state, city, lines — through a single loop.
 */
public interface Validator {
    boolean validate(Address address);
}
