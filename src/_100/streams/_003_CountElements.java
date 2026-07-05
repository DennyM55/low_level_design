package _100.streams;

import java.util.stream.Stream;

public class _003_CountElements {

    public static void main(String[] args) {

        Stream<Integer> numbers = Stream.of(
                10, 20, 30, 40, 50, 60
        );

        long count = countElements(numbers);

        System.out.println(count);
    }

    public static long countElements(Stream<Integer> numbers) {

        return numbers.count();

    }
}