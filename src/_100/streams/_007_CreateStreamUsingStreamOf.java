package _100.streams;

import java.util.stream.Stream;

public class _007_CreateStreamUsingStreamOf {

    public static void main(String[] args) {

        Stream<String> fruits = Stream.of(
                "Apple",
                "Banana",
                "Orange",
                "Mango"
        );

        fruits.forEach(System.out::println);
    }
}