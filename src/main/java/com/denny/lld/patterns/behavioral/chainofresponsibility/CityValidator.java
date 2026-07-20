package com.denny.lld.patterns.behavioral.chainofresponsibility;

import java.util.Set;

/**
 * Validates the city field of an address.
 * Runs after zip and state — more expensive, so checked later.
 */
public class CityValidator implements Validator {

    private static final Set<String> VALID_CITIES = Set.of(
        "New York", "Los Angeles", "Chicago", "Houston", "Delhi"
    );

    @Override
    public boolean validate(Address address) {
        if (address.city == null || address.city.isBlank()) {
            System.out.println("  [CityValidator] FAIL — city is empty");
            return false;
        }
        if (!VALID_CITIES.contains(address.city)) {
            System.out.println("  [CityValidator] FAIL — unknown city: " + address.city);
            return false;
        }
        System.out.println("  [CityValidator] PASS");
        return true;
    }
}
