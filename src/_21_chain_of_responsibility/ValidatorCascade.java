package _21_chain_of_responsibility;

import java.util.List;

/**
 * AddressValidatorChain (also known as ValidatorCascade) chains multiple
 * Validators together in a defined order.
 *
 * It implements Validator itself, so a chain can be used anywhere a single
 * Validator is expected — enabling nested chains if needed.
 *
 * Fail-fast: returns false as soon as the first validator fails.
 * Order matters: validators in the list run from first to last.
 * Use a List (not a Set) because the execution order is significant.
 */
public class ValidatorCascade implements Validator {

    private final List<Validator> validators;

    public ValidatorCascade(List<Validator> validators) {
        this.validators = validators;
    }

    @Override
    public boolean validate(Address address) {
        for (Validator v : validators) {
            if (!v.validate(address)) {
                return false;   // fail fast — skip remaining validators
            }
        }
        return true;
    }
}
