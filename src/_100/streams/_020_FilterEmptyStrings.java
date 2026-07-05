package _100.streams;

public class _020_FilterEmptyStrings {

    public static void main(String[] args) {

        String[] strings = {
                "Apple",
                "",
                "Banana",
                "",
                "Orange",
                "Mango",
                ""
        };

        long count = filterEmptyStrings(strings);

        System.out.println("Count of non-empty strings: " + count);
    }

    public static long filterEmptyStrings(String[] strings) {

        return java.util.Arrays.stream(strings)
                .filter(s -> !s.isEmpty())
                .count();
    }
}
