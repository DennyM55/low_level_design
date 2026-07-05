/*
 * You are given a stream of integers.
 * Use the allMatch() method to confirm if all numbers in the stream are even.
 * Use only Stream methods.
 */

package _100.streams;

import java.util.stream.Stream;

public class _02_Even_AllMatch {
    public static void main(String[] args) {
        boolean allEven = areAllEven(Stream.of(2, 4, 6, 8, 10));
        System.out.println("Are all numbers even? " + allEven);
    }

    public static boolean areAllEven(Stream<Integer> numbers) {
        return numbers.allMatch(n -> n % 2 == 0);
    }
}
