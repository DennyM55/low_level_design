package _100.streams;

import java.util.stream.Stream;

public class _010_CreateInfiniteStreamUsingIterate {

    public static void main(String[] args) {
        Stream.iterate(1, number -> number + 1)
                .limit(10)
                .forEach(System.out::println);
    }
}