/*
 * You are given a stream of integers.
 * Use the filter() method to return all odd numbers as a List.
 * Use only Stream methods.
 */

package _100.streams;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class _01_Odd {

    public static void main(String[] args) {
        List<Integer> oddNumbers = getOddNumbers(Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9));
        oddNumbers.forEach(System.out::println);
    }

    public static List<Integer> getOddNumbers(Stream<Integer> numbers) {
        return numbers
                .filter(n -> n % 2 != 0)
                .collect(Collectors.toList());
    }
}