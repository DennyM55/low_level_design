package _21_chain_of_responsibility;

/**
 * Demonstrates the Chain of Responsibility / Handler Pipeline pattern.
 *
 * ValidatorCascadeFactory maps use-case keys to ordered validator chains.
 * Calling validate() runs the chain fail-fast: the first failing validator
 * immediately returns false and skips all subsequent validators.
 */
public class Main {

    public static void main(String[] args) {
        ValidatorCascadeFactory factory = ValidatorCascadeFactory.getInstance();

        // --- Scenario 1: Valid address, checkout chain ---
        System.out.println("=== Scenario 1: Valid address (checkout chain) ===");
        Address valid = new Address("10001", "NY", "New York", "123 Main Street");
        boolean r1 = factory.getValidator("checkout").validate(valid);
        System.out.println("Result: " + (r1 ? "VALID" : "INVALID"));
        System.out.println();

        // --- Scenario 2: Invalid zip — chain fails fast, city/lines never run ---
        System.out.println("=== Scenario 2: Bad zip (checkout) — fails fast ===");
        Address badZip = new Address("99999", "NY", "New York", "123 Main Street");
        boolean r2 = factory.getValidator("checkout").validate(badZip);
        System.out.println("Result: " + (r2 ? "VALID" : "INVALID"));
        System.out.println("Notice: CityValidator and LinesValidator never ran.");
        System.out.println();

        // --- Scenario 3: Valid zip, bad city ---
        System.out.println("=== Scenario 3: Bad city (checkout) ===");
        Address badCity = new Address("10001", "NY", "Atlantis", "123 Main Street");
        boolean r3 = factory.getValidator("checkout").validate(badCity);
        System.out.println("Result: " + (r3 ? "VALID" : "INVALID"));
        System.out.println();

        // --- Scenario 4: identity_check — only zip + city, lines not checked ---
        System.out.println("=== Scenario 4: Empty lines OK for identity_check ===");
        Address noLines = new Address("10001", "NY", "New York", "");
        boolean r4 = factory.getValidator("identity_check").validate(noLines);
        System.out.println("Result: " + (r4 ? "VALID" : "INVALID"));
        System.out.println("Notice: LinesValidator is not in the identity_check chain.");
        System.out.println();

        // --- Scenario 5: strict_check — all four validators ---
        System.out.println("=== Scenario 5: Strict check with all four validators ===");
        Address fullValid = new Address("10001", "NY", "New York", "456 Park Avenue");
        boolean r5 = factory.getValidator("strict_check").validate(fullValid);
        System.out.println("Result: " + (r5 ? "VALID" : "INVALID"));
        System.out.println();

        // --- Scenario 6: Singleton verification ---
        ValidatorCascadeFactory factory2 = ValidatorCascadeFactory.getInstance();
        System.out.println("=== Scenario 6: Singleton check ===");
        System.out.println("Same factory instance: " + (factory == factory2));
    }
}
