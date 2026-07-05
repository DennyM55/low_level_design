package _100.streams;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class _008_CreateStreamFromList {

    public static void main(String[] args) {

        List<String> fruits = Arrays.asList(
                "Apple",
                "Banana",
                "Orange",
                "Mango"
        );

        Stream<String> fruitStream = fruits.stream();

        fruitStream.forEach(System.out::println);
    }
}