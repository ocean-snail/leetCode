package topInterview150;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * A phrase is a palindrome if, after converting all uppercase letters into
 * lowercase letters and removing all non-alphanumeric characters, it reads the
 * same forward and backward. Alphanumeric characters include letters and
 * numbers.
 * 
 * Given a string s, return true if it is a palindrome, or false otherwise.
 * 
 * 
 * ? Example 1:
 * 
 * Input: s = "A man, a plan, a canal: Panama"
 * Output: true
 * Explanation: "amanaplanacanalpanama" is a palindrome.
 * 
 * ? Example 2:
 * 
 * Input: s = "race a car"
 * Output: false
 * Explanation: "raceacar" is not a palindrome.
 * 
 * ? Example 3:
 * 
 * Input: s = " "
 * Output: true
 * Explanation: s is an empty string "" after removing non-alphanumeric
 * characters.
 * Since an empty string reads the same forward and backward, it is a
 * palindrome.
 * 
 * 
 * ! Constraints:
 * 
 * 1 <= s.length <= 2 * 105
 * s consists only of printable ASCII characters.
 */
public class ValidPalindrome {

    // ------------------------------------------------------------------
    // Solution
    // ------------------------------------------------------------------

    /**
     * Reports whether s is a palindrome under alphanumeric-only, case-insensitive
     * comparison.
     * * Time: O(n) - left never decreases and right never increases, so the two
     * indices together read each of the n characters at most once.
     * * Space: O(1) - two int indices are the only storage allocated; no filtered
     * copy of the input is built.
     *
     * @param s the input string, made of printable ASCII characters, length at
     *          least 1
     * @return true when the filtered lower-cased character sequence equals its own
     *         reversal
     */
    public boolean isPalindrome(String s) {
        int left = 0;
        int right = s.length() - 1;
        while (left < right) {
            while (left < right && !Character.isLetterOrDigit(s.charAt(left))) {
                left++;
            }
            while (left < right && !Character.isLetterOrDigit(s.charAt(right))) {
                right--;
            }
            if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------

    @Test
    @DisplayName("Example 1: mixed case with commas and a colon is a palindrome")
    void exampleOneWithPunctuationAndMixedCase_returnsTrue() {
        assertTrue(isPalindrome("A man, a plan, a canal: Panama"));
    }

    @Test
    @DisplayName("Example 2: 'race a car' filters to 'raceacar', which is not a palindrome")
    void exampleTwoNotAPalindrome_returnsFalse() {
        assertFalse(isPalindrome("race a car"));
    }

    @Test
    @DisplayName("Example 3: a single space filters to the empty string, which is a palindrome")
    void singleSpace_returnsTrue() {
        assertTrue(isPalindrome(" "));
    }

    @Test
    @DisplayName("A single alphanumeric character has no partner to compare against")
    void singleAlphanumericCharacter_returnsTrue() {
        assertTrue(isPalindrome("a"));
        assertTrue(isPalindrome("0"));
    }

    @Test
    @DisplayName("A string made only of non-alphanumeric characters filters to the empty string")
    void onlyNonAlphanumericCharacters_returnsTrue() {
        assertTrue(isPalindrome(".,"));
        assertTrue(isPalindrome("!@#$"));
    }

    @Test
    @DisplayName("Underscore is not alphanumeric, so it is skipped like punctuation")
    void underscoreIsSkipped_returnsTrue() {
        assertTrue(isPalindrome("_a_"));
        assertTrue(isPalindrome("a_a"));
    }

    @Test
    @DisplayName("A digit never equals a letter after case folding")
    void digitComparedWithLetter_returnsFalse() {
        assertFalse(isPalindrome("0P"));
        assertFalse(isPalindrome("0p"));
        assertFalse(isPalindrome("p0"));
    }

    @Test
    @DisplayName("The same letter in different cases compares equal")
    void sameLetterDifferentCase_returnsTrue() {
        assertTrue(isPalindrome("Aa"));
    }

    @Test
    @DisplayName("Even-length filtered content leaves no middle character")
    void evenLengthFilteredContent_returnsTrue() {
        assertTrue(isPalindrome("abba"));
        assertTrue(isPalindrome("a.b|b?a"));
    }

    @Test
    @DisplayName("Odd-length filtered content leaves one unpaired middle character")
    void oddLengthFilteredContent_returnsTrue() {
        assertTrue(isPalindrome("abcba"));
    }

    @Test
    @DisplayName("Digit-only inputs exercise the branch where no case folding applies")
    void digitsOnly_returnsExpectedResult() {
        assertTrue(isPalindrome("12321"));
        assertFalse(isPalindrome("12345"));
    }

    @Test
    @DisplayName("A mismatch in the outermost pair returns after one comparison")
    void outermostPairMismatch_returnsFalse() {
        assertFalse(isPalindrome("ab"));
    }

    @Test
    @DisplayName("A mismatch found only after several matching pairs still returns false")
    void innerPairMismatch_returnsFalse() {
        assertFalse(isPalindrome("aXbba"));
    }

    @Test
    @DisplayName("Inputs at the constraint's upper length bound of 2 * 10^5 characters")
    void maximumLengthInput_returnsExpectedResult() {
        int maximumLength = 200_000;
        char[] buffer = new char[maximumLength];
        Arrays.fill(buffer, 'a');
        assertTrue(isPalindrome(new String(buffer)));

        buffer[maximumLength - 1] = 'b';
        assertFalse(isPalindrome(new String(buffer)));
    }
}