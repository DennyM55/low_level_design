package _100.streams;

import java.util.Optional;
import java.util.stream.Stream;

public class _005_FindAnyElement {

    public static void main(String[] args) {

        Stream<Integer> numbers = Stream.of(
                10, 20, 30, 40, 50
        );

        Optional<Integer> number = getAnyNumber(numbers);

        System.out.println(number.orElse(null));
    }

    public static Optional<Integer> getAnyNumber(Stream<Integer> numbers) {

        return numbers.findAny();

    }
}