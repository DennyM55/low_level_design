package _100.streams;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class _002_FilterEvenNumbers {

    public static void main(String[] args) {

        Stream<Integer> numbers = Stream.of(
                1, 2, 3, 4, 5, 6, 7, 8, 9, 10
        );

        List<Integer> evenNumbers = getEvenNumbers(numbers);

        System.out.println(evenNumbers);
    }

    public static List<Integer> getEvenNumbers(Stream<Integer> numbers) {

        return numbers
                .filter(number -> number % 2 == 0)
                .collect(Collectors.toList());
    }
}