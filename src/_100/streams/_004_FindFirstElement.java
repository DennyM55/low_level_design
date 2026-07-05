package _100.streams;

import java.util.Optional;
import java.util.stream.Stream;

public class _004_FindFirstElement {

    public static void main(String[] args) {

        Stream<Integer> numbers = Stream.of(
                10, 20, 30, 40, 50
        );

        Optional<Integer> firstNumber = getFirstNumber(numbers);

        System.out.println(firstNumber.orElse(null));
    }

    public static Optional<Integer> getFirstNumber(Stream<Integer> numbers) {

        return numbers.findFirst();

    }
}