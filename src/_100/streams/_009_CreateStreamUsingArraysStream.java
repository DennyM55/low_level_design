package _100.streams;

import java.util.Arrays;
import java.util.stream.Stream;

public class _009_CreateStreamUsingArraysStream {

    public static void main(String[] args) {

        String[] fruits = {
                "Apple",
                "Banana",
                "Orange",
                "Mango"
        };

        Stream<String> fruitStream = Arrays.stream(fruits);

        fruitStream.forEach(System.out::println);
    }
}