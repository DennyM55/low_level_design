package _100.streams;

import java.util.stream.Stream;

public class _011_CreateInfiniteStreamUsingGenerate {

    public static void main(String[] args) {

        Stream.generate(() -> "Hello")
                .limit(5)
                .forEach(System.out::println);

        Stream.generate(Math::random)
                .limit(5)
                .forEach(System.out::println);

        Stream.generate(java.util.UUID::randomUUID)
                .limit(3)
                .forEach(System.out::println);
    }
}