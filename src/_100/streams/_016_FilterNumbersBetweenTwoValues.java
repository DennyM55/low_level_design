package _100.streams;

import java.util.Arrays;

public class _016_FilterNumbersBetweenTwoValues {

    public static void main(String[] args) {
        int[] numbers = {1, 5, 10, 15, 20, 25, 30};

        int lowerBound = 10;
        int upperBound = 25;

        System.out.println("Numbers between " + lowerBound + " and " + upperBound + ":");
        filterNumbersBetweenTwoValues(numbers, lowerBound, upperBound);
    }

    public static void filterNumbersBetweenTwoValues(int[] numbers, int lowerBound, int upperBound) {
        for (int number : numbers) {
            if (number >= lowerBound && number <= upperBound) {
                System.out.println(number);
            }
        }
        Arrays.stream(numbers)
                .filter(number -> number >= lowerBound && number <= upperBound)
                .forEach(System.out::println);
    }
}
