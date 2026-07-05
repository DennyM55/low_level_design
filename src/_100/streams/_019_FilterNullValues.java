package _100.streams;

import java.util.Objects;

public class _019_FilterNullValues {

    public static void main(String[] args) {
        String[] fruits = {
                "Apple",
                null,
                "Banana",
                null,
                "Orange",
                "Mango"
        };

        long count = countNonNullValues(fruits);

        System.out.println("Count of non-null values: " + count);
    }

    public static long countNonNullValues(String[] fruits) {
        return java.util.Arrays.stream(fruits)
                .filter(Objects::nonNull)
                .count();
    }
}
