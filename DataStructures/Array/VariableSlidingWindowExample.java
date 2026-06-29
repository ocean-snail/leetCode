import java.util.HashSet;
import java.util.Set;

public class VariableSlidingWindowExample {

    public static int lengthOfLongestSubstring(String s) {
        Set<Character> window = new HashSet<>();

        int left = 0;
        int maxLength = 0;

        for (int right = 0; right < s.length(); right++) {
            char current = s.charAt(right);

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