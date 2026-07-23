package com.denny.lld.patterns.behavioral.chainofresponsibility;

import java.util.Set;

/**
 * Validates the state field of an address.
 * More expensive than zip validation — runs second in the cascade.
 */
public class StateValidator implements Validator {

    private static final Set<String> VALID_STATES = Set.of(
        "NY", "CA", "IL", "TX", "DL"
    );

    @Override
    public boolean validate(Address address) {
        if (address.state == null || address.state.isBlank()) {
            System.out.println("  [StateValidator] FAIL — state is empty");
            return false;
        }
        if (!VALID_STATES.contains(address.state.toUpperCase())) {
            System.out.println("  [StateValidator] FAIL — unknown state: " + address.state);
            return false;
        }
        System.out.println("  [StateValidator] PASS");
        return true;
    }
}
