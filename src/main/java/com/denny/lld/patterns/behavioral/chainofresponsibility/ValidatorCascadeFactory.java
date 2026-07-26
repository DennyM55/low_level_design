package com.denny.lld.patterns.behavioral.chainofresponsibility;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * ValidatorCascadeFactory combines the Singleton and Factory patterns.
 *
 * Singleton: building the validator chains is a one-time cost. In production,
 * each validator may load reference data from a database; rebuilding on every
 * request would be wasteful. We build once and reuse.
 *
 * Factory: different use cases need different handler combinations. The factory
 * maps a use-case key to a pre-built chain so callers never need to assemble
 * chains themselves.
 *
 * Client usage:
 *   ValidatorCascadeFactory.getInstance()
 *                           .getValidator("checkout")
 *                           .validate(address);
 */
public class ValidatorCascadeFactory {

    private static volatile ValidatorCascadeFactory INSTANCE;
    private final Map<String, Validator> registry;

    private ValidatorCascadeFactory() {
        registry = new HashMap<>();

        // checkout: zip first (cheapest) → city → full address lines
        registry.put("checkout", new ValidatorCascade(
            Arrays.asList(
                new ZipValidator(),
                new CityValidator(),
                new LinesValidator()
            )
        ));

        // identity_check: only zip and city required (lines not needed)
        registry.put("identity_check", new ValidatorCascade(
            Arrays.asList(
                new ZipValidator(),
                new CityValidator()
            )
        ));

        // strict_check: all four validators in ascending complexity order
        registry.put("strict_check", new ValidatorCascade(
            Arrays.asList(
                new ZipValidator(),
                new StateValidator(),
                new CityValidator(),
                new LinesValidator()
            )
        ));
    }

    public static ValidatorCascadeFactory getInstance() {
        if (INSTANCE == null) {
            synchronized (ValidatorCascadeFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ValidatorCascadeFactory();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Returns the pre-built validator chain for the given use-case key.
     *
     * @param useCaseKey  e.g. "checkout", "identity_check", "strict_check"
     * @throws IllegalArgumentException if no chain is registered for that key
     */
    public Validator getValidator(String useCaseKey) {
        if (!registry.containsKey(useCaseKey)) {
            throw new IllegalArgumentException(
                "No validator chain registered for use-case: " + useCaseKey
            );
        }
        return registry.get(useCaseKey);
    }
}
