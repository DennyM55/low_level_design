package _100.streams;

import java.util.stream.Stream;

public class _006_CheckIfStreamIsEmpty {

    public static void main(String[] args) {

        Stream<Integer> numbers = Stream.empty();

        boolean empty = isStreamEmpty(numbers);

        System.out.println(empty);
    }

    public static boolean isStreamEmpty(Stream<Integer> numbers) {

        return numbers.findAny().isEmpty();

    }
}