package _100.streams;

import java.util.Arrays;

public class _015_FilterNumbersGreaterThan100 {

    public static void main(String[] args) {

        int[] numbers = {50, 150, 200, 75, 300};

        System.out.println("Numbers greater than 100:");
        filterNumbersGreaterThan100(numbers);
    }

    public static void filterNumbersGreaterThan100(int[] numbers) {
        for (int number : numbers) {
            if (number > 100) {
                System.out.println(number);
            }
        }
        Arrays.stream(numbers)
                .filter(number -> number > 100)
                .forEach(System.out::println);
    }
}
