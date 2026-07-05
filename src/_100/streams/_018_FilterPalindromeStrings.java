package _100.streams;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class _018_FilterPalindromeStrings {

    public static void main(String[] args) {
        String[] words = {"level", "hello", "world", "radar", "java", "madam"};

        System.out.println("Palindrome words:");
        filterPalindromeWords(words).forEach(System.out::println);
    }

    public static Stream<String> filterPalindromeWords(String[] words) {
        return Arrays.stream(words)
                .filter(_018_FilterPalindromeStrings::isPalindrome);
    }

    public static boolean isPalindrome(String word) {
        String reversed = new StringBuilder(word).reverse().toString();
        return word.equals(reversed);
    }
}
