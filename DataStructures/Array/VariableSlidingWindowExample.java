import java.util.HashSet;
import java.util.Set;

public class VariableSlidingWindowExample {

    // * Find the longest substring without repeating characters.
    // * Time - O(n) | Space - O(n)
    public static int lengthOfLongestSubstring(String s) {
        // * Space - O(n)
        Set<Character> window = new HashSet<>();

        int left = 0;
        int maxLength = 0;

        // * Time - O(n)
        for (int right = 0; right < s.length(); right++) {
            char current = s.charAt(right);

            // ! each character enters and leaves the window at most once. -> n + n => 2n
            while (window.contains(current)) {
                window.remove(s.charAt(left));
                left++;
            }

            window.add(current);

            int currentLength = right - left + 1;
            maxLength = Math.max(maxLength, currentLength);
        }

        return maxLength;
    }

    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring("abcabcbb")); // 3
    }
}
