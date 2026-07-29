package topInterview150;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Given a string s consisting of words and spaces, return the length of the
 * last word in the string.
 * 
 * A word is a maximal substring consisting of non-space characters only.
 * 
 * 
 * ? Example 1:
 * 
 * Input: s = "Hello World"
 * Output: 5
 * Explanation: The last word is "World" with length 5.
 * 
 * ? Example 2:
 * 
 * Input: s = " fly me to the moon "
 * Output: 4
 * Explanation: The last word is "moon" with length 4.
 * 
 * ? Example 3:
 * 
 * Input: s = "luffy is still joyboy"
 * Output: 6
 * Explanation: The last word is "joyboy" with length 6.
 * 
 * 
 * ! Constraints:
 * 
 * 1 <= s.length <= 104
 * s consists of only English letters and spaces ' '.
 * There will be at least one word in s.
 */
public class LengthOfLastWord {

    // ------------------------------------------------------------------
    // Solution
    // ------------------------------------------------------------------

    /**
     * Returns the length of the last word in the input string.
     * * Time: O(n) - each character is inspected at most once, and only the
     * trailing
     * spaces plus the last word are inspected at all.
     * * Space: O(1) - two int indices are the only extra storage; no copy of s is
     * made.
     *
     * @param s a string of English letters and spaces containing at least one word
     * @return the number of characters in the last word
     */
    public int lengthOfLastWord(String s) {
        int end = s.length() - 1;

        while (end >= 0 && s.charAt(end) == ' ') {
            end--;
        }

        int start = end;
        while (start >= 0 && s.charAt(start) != ' ') {
            start--;
        }

        return end - start;
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------
    @Test
    @DisplayName("Two words separated by one space returns the length of the second word")
    void twoWordsSingleSpace_returnsLengthOfSecondWord() {
        assertEquals(5, lengthOfLastWord("Hello World"));
    }

    @Test
    @DisplayName("Leading, repeated and trailing spaces are ignored")
    void leadingRepeatedAndTrailingSpaces_returnsLengthOfLastWord() {
        assertEquals(4, lengthOfLastWord("   fly me   to   the moon  "));
    }

    @Test
    @DisplayName("Four words with single spaces returns the length of the last word")
    void fourWordsSingleSpaces_returnsLengthOfLastWord() {
        assertEquals(6, lengthOfLastWord("luffy is still joyboy"));
    }

    @Test
    @DisplayName("A string with no space at all is itself the last word")
    void singleWordNoSpaces_returnsWholeStringLength() {
        assertEquals(4, lengthOfLastWord("word"));
    }

    @Test
    @DisplayName("A single letter, the shortest legal input, returns one")
    void singleLetterOnly_returnsOne() {
        assertEquals(1, lengthOfLastWord("a"));
    }

    @Test
    @DisplayName("A one-letter last word preceded only by spaces returns one")
    void oneLetterWordAfterLeadingSpaces_returnsOne() {
        assertEquals(1, lengthOfLastWord("   a"));
    }

    @Test
    @DisplayName("A one-letter last word followed only by spaces returns one")
    void oneLetterWordBeforeTrailingSpaces_returnsOne() {
        assertEquals(1, lengthOfLastWord("a   "));
    }

    @Test
    @DisplayName("The last word is shorter than an earlier word")
    void lastWordShorterThanEarlierWord_returnsLastWordLength() {
        assertEquals(1, lengthOfLastWord("elephant a"));
    }

    @Test
    @DisplayName("Maximum-length input of 10000 letters is handled without truncation")
    void maximumLengthSingleWord_returnsTenThousand() {
        assertEquals(10_000, lengthOfLastWord("a".repeat(10_000)));
    }
}