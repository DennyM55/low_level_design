package _100.streams;

import java.util.Arrays;

public class _017_FilterPrimeNumbers {

    public static void main(String[] args) {
        int[] numbers = {2, 3, 4, 5, 6, 7, 8, 9, 10};

        System.out.println("Prime numbers in the array:");
        filterPrimeNumbers(numbers);
    }

    public static void filterPrimeNumbers(int[] numbers) {
        for (int number : numbers) {
            if (isPrime(number)) {
                System.out.println(number);
            }
        }
        Arrays.stream(numbers)
                .filter(_017_FilterPrimeNumbers::isPrime)
                .forEach(System.out::println);
    }

    public static boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }
}
