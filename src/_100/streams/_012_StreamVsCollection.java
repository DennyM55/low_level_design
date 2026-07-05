package _100.streams;

import java.util.Arrays;
import java.util.List;

public class _012_StreamVsCollection {

    public static void main(String[] args) {

        List<String> fruits = Arrays.asList(
                "Apple",
                "Banana",
                "Orange",
                "Mango"
        );

        // Collection
        System.out.println(fruits);

        // Stream
        fruits.stream().forEach(System.out::println);
    }
}