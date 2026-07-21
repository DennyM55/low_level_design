package com.denny.lld.patterns.behavioral.chainofresponsibility;

/**
 * Validates the address lines field — the most expensive check.
 * Runs last in the cascade so that cheaper checks can fail fast first.
 */
public class LinesValidator implements Validator {

    private static final int MIN_LENGTH = 5;
    private static final int MAX_LENGTH = 200;

    @Override
    public boolean validate(Address address) {
        if (address.lines == null || address.lines.isBlank()) {
            System.out.println("  [LinesValidator] FAIL — address lines are empty");
            return false;
        }
        if (address.lines.length() < MIN_LENGTH) {
            System.out.println("  [LinesValidator] FAIL — address line too short");
            return false;
        }
        if (address.lines.length() > MAX_LENGTH) {
            System.out.println("  [LinesValidator] FAIL — address line too long");
            return false;
        }
        System.out.println("  [LinesValidator] PASS");
        return true;
    }
}
