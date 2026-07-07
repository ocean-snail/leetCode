import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ValidPalindromeExample {

    // * Time - O(n), Space - O(1)
    public static boolean isPalindrome(String s) {
        int left = 0;
        int right = s.length() - 1;

        // * Time - O(n)
        while (left < right) {
            if (!Character.isLetterOrDigit(s.charAt(left))) {
                left++;
            } else if (!Character.isLetterOrDigit(s.charAt(right))) {
                right--;
            } else {
                if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))) {
                    return false;
                }
                left++;
                right--;
            }
        }
        return true;
    }

    @Test
    public void test() {
        assertEquals(isPalindrome("A man, a plan, a canal: Panama"), true);
        assertEquals(isPalindrome("race a car"), false);
        assertEquals(isPalindrome(" "), true);
        assertEquals(isPalindrome("0P"), false);
    }

}
