package com.denny.lld.patterns.behavioral.chainofresponsibility;

import java.util.Set;

/**
 * Validates the ZIP code field of an address.
 *
 * In a real system this would consult a database of valid zip codes.
 * Here we use a small in-memory set to demonstrate the concept.
 * This is the cheapest validator and should always run first in the cascade.
 */
public class ZipValidator implements Validator {

    // Simulates a loaded set of valid zip codes
    private static final Set<String> VALID_ZIPS = Set.of(
        "10001", "10002", "90001", "60601", "110001"
    );

    @Override
    public boolean validate(Address address) {
        if (address.zip == null || address.zip.isBlank()) {
            System.out.println("  [ZipValidator] FAIL — zip is empty");
            return false;
        }
        if (!VALID_ZIPS.contains(address.zip)) {
            System.out.println("  [ZipValidator] FAIL — unknown zip: " + address.zip);
            return false;
        }
        System.out.println("  [ZipValidator] PASS");
        return true;
    }
}
