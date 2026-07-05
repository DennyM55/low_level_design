package _100.streams;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class _013_FilterStringsStartingWithA {

    public static void main(String[] args) {

        List<String> fruits = Arrays.asList(
                "Apple",
                "Banana",
                "Apricot",
                "Orange",
                "Avocado",
                "Mango"
        );

        List<String> result = filterStringsStartingWithA(fruits);

        System.out.println(result);
    }

    public static List<String> filterStringsStartingWithA(List<String> fruits) {

        return fruits.stream()
                .filter(fruit -> fruit.startsWith("A"))
                .collect(Collectors.toList());
    }
}