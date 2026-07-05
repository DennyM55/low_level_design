package _100.streams;

import java.util.Arrays;
import java.util.stream.Stream;

public class _014_FilterStringsEndingWithX {
    public static void main(String[] args) {

        String[] words = {
                "Apple",
                "Banana",
                "Orange",
                "Mango",
                "Box",
                "Fox"
        };

        Stream<String> wordStream = Arrays.stream(words);

        wordStream.filter(word -> word.endsWith("x"))
                .forEach(System.out::println);
    }
}
