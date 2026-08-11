package topInterview150;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Given a string s, find the length of the longest substring without duplicate
 * characters.
 * 
 * 
 * ? Example 1:
 * 
 * Input: s = "abcabcbb"
 * Output: 3
 * Explanation: The answer is "abc", with the length of 3. Note that "bca" and
 * "cab" are also correct answers.
 * 
 * ? Example 2:
 * 
 * Input: s = "bbbbb"
 * Output: 1
 * Explanation: The answer is "b", with the length of 1.
 * 
 * ? Example 3:
 * 
 * Input: s = "pwwkew"
 * Output: 3
 * Explanation: The answer is "wke", with the length of 3.
 * Notice that the answer must be a substring, "pwke" is a subsequence and not a
 * substring.
 * 
 * 
 * ! Constraints:
 * 
 * 0 <= s.length <= 105
 * s consists of English letters, digits, symbols and spaces.
 */
public class LongestSubstringWithoutRepeatingCharacters {

    // ------------------------------------------------------------------
    // Solution
    // ------------------------------------------------------------------

    // The constraints restrict s to English letters, digits, symbols and spaces,
    // so every character value fits in the 7-bit ASCII range [0, 127].
    private static final int ASCII_TABLE_SIZE = 128;

    /**
     * Returns the length of the longest substring of s that contains no repeated
     * character.
     * * Time: O(n) - right advances once per index and windowStart only ever
     * increases,
     * so the total number of index moves is bounded by 2n.
     * * Space: O(1) - lastSeen always holds ASCII_TABLE_SIZE ints, independent of
     * s.length().
     *
     * @param s the input string, assumed to contain only ASCII characters
     * @return the length of the longest duplicate-free substring, or 0 when s is
     *         empty
     */
    public int lengthOfLongestSubstring(String s) {
        int[] lastSeen = new int[ASCII_TABLE_SIZE];
        Arrays.fill(lastSeen, -1);

        int longest = 0;
        int windowStart = 0;

        for (int right = 0; right < s.length(); right++) {
            char current = s.charAt(right);

            if (lastSeen[current] >= windowStart) {
                windowStart = lastSeen[current] + 1;
            }

            lastSeen[current] = right;

            int windowLength = right - windowStart + 1;
            if (windowLength > longest) {
                longest = windowLength;
            }
        }

        return longest;
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------

    @Test
    @DisplayName("All characters distinct returns the full string length")
    void allCharactersDistinct_returnsFullLength() {
        assertEquals(6, lengthOfLongestSubstring("abcdef"));
    }

    @Test
    @DisplayName("Repeating abc pattern returns three")
    void repeatingAbcPattern_returnsThree() {
        assertEquals(3, lengthOfLongestSubstring("abcabcbb"));
    }

    @Test
    @DisplayName("Same character repeated returns one")
    void sameCharacterRepeated_returnsOne() {
        assertEquals(1, lengthOfLongestSubstring("bbbbb"));
    }

    @Test
    @DisplayName("Adjacent duplicate in the middle returns three")
    void adjacentDuplicateInMiddle_returnsThree() {
        assertEquals(3, lengthOfLongestSubstring("pwwkew"));
    }

    @Test
    @DisplayName("Empty string returns zero")
    void emptyString_returnsZero() {
        assertEquals(0, lengthOfLongestSubstring(""));
    }

    @Test
    @DisplayName("Single character returns one")
    void singleCharacter_returnsOne() {
        assertEquals(1, lengthOfLongestSubstring("a"));
    }

    @Test
    @DisplayName("Duplicate located before the window start returns two")
    void duplicateBeforeWindowStart_returnsTwo() {
        assertEquals(2, lengthOfLongestSubstring("abba"));
    }

    @Test
    @DisplayName("Duplicate splitting an early window returns three")
    void duplicateSplitsEarlyWindow_returnsThree() {
        assertEquals(3, lengthOfLongestSubstring("dvdf"));
    }

    @Test
    @DisplayName("Longest window ending at the last index returns five")
    void longestWindowEndsAtLastIndex_returnsFive() {
        assertEquals(5, lengthOfLongestSubstring("tmmzuxt"));
    }

    @Test
    @DisplayName("Symbols spaces and letters mixed returns four")
    void symbolsSpacesAndLettersMixed_returnsFour() {
        assertEquals(4, lengthOfLongestSubstring("a b!a"));
    }

    @Test
    @DisplayName("Digits and letters mixed returns four")
    void digitsAndLettersMixed_returnsFour() {
        assertEquals(4, lengthOfLongestSubstring("1a1ab2"));
    }
}
