package topInterview150;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Given an input string s, reverse the order of the words.
 * 
 * A word is defined as a sequence of non-space characters. The words in s will
 * be separated by at least one space.
 * 
 * Return a string of the words in reverse order concatenated by a single space.
 * 
 * Note that s may contain leading or trailing spaces or multiple spaces between
 * two words. The returned string should only have a single space separating the
 * words. Do not include any extra spaces.
 * 
 * 
 * ? Example 1:
 * 
 * Input: s = "the sky is blue"
 * Output: "blue is sky the"
 * 
 * ? Example 2:
 * 
 * Input: s = " hello world "
 * Output: "world hello"
 * Explanation: Your reversed string should not contain leading or trailing
 * spaces.
 * 
 * ? Example 3:
 * 
 * Input: s = "a good example"
 * Output: "example good a"
 * Explanation: You need to reduce multiple spaces between two words to a single
 * space in the reversed string.
 * 
 * 
 * ! Constraints:
 * 
 * 1 <= s.length <= 104
 * s contains English letters (upper-case and lower-case), digits, and spaces '
 * '.
 * There is at least one word in s.
 * 
 * 
 * * Follow-up: If the string data type is mutable in your language, can you
 * solve
 * * it in-place with O(1) extra space?
 */
public class ReverseWordsInAString {

    // ------------------------------------------------------------------
    // Solution
    // ------------------------------------------------------------------

    /**
     * Walks the string from the last character to the first, collecting each word
     * in the order encountered.
     * * Time: O(n) - the pointer only ever decreases, so each of the n characters
     * is
     * examined once; each word character is then copied once into the builder.
     * * Space: O(n) - the StringBuilder holds the answer, whose length is at most
     * n;
     * no other storage grows with the input.
     *
     * @param s the input string, guaranteed non-empty and containing at least one
     *          word
     * @return the words of s in reverse order, single-spaced, with no leading or
     *         trailing space
     */
    public String reverseWords(String s) {
        StringBuilder result = new StringBuilder(s.length());

        int right = s.length() - 1;
        while (right >= 0) {
            while (right >= 0 && s.charAt(right) == ' ') {
                right--;
            }
            if (right < 0) {
                break;
            }

            int wordEnd = right;
            while (right >= 0 && s.charAt(right) != ' ') {
                right--;
            }

            if (!result.isEmpty()) {
                result.append(' ');
            }
            result.append(s, right + 1, wordEnd + 1);
        }

        return result.toString();
    }

    /**
     * Answers the follow-up: reverse-the-whole-then-reverse-each-part, performed on
     * a mutable char[] buffer.
     * * Time: O(n) - one compaction pass over n characters, one reversal of the
     * whole
     * buffer (n/2 swaps), and per-word reversals totalling another n/2 swaps.
     * * Space: O(n) - Java's String is immutable, so the buffer is a copy; the
     * algorithm itself uses only a fixed number of int variables beyond that
     * buffer, which is the O(1) the follow-up asks for.
     *
     * @param s the input string, guaranteed non-empty and containing at least one
     *          word
     * @return the words of s in reverse order, single-spaced, with no leading or
     *         trailing space
     */
    public String reverseWordsInPlace(String s) {
        char[] buffer = s.toCharArray();

        int length = compactSpaces(buffer);

        reverse(buffer, 0, length - 1);

        int wordStart = 0;
        for (int i = 0; i <= length; i++) {
            if (i == length || buffer[i] == ' ') {
                reverse(buffer, wordStart, i - 1);
                wordStart = i + 1;
            }
        }

        return new String(buffer, 0, length);
    }

    /**
     * Rewrites the buffer in place so that words are separated by exactly one
     * space, with no leading or trailing space.
     * * Time: O(n) - the read pointer never moves backwards, so every character is
     * inspected once.
     * * Space: O(1) - two int pointers, regardless of buffer size.
     *
     * @param buffer the mutable character buffer, modified in place
     * @return the number of valid characters now at the front of the buffer
     */
    private int compactSpaces(char[] buffer) {
        int write = 0;
        int read = 0;
        int n = buffer.length;

        while (read < n) {
            while (read < n && buffer[read] == ' ') {
                read++;
            }
            while (read < n && buffer[read] != ' ') {
                buffer[write++] = buffer[read++];
            }
            while (read < n && buffer[read] == ' ') {
                read++;
            }
            if (read < n) {
                buffer[write++] = ' ';
            }
        }

        return write;
    }

    /**
     * Reverses buffer[lo..hi] inclusive; does nothing when the range is empty or a
     * single element.
     * * Time: O(hi - lo) - one swap per pair, so half the range length.
     * * Space: O(1) - a single char temporary.
     *
     * @param lo     the first index of the range, inclusive
     * @param hi     the last index of the range, inclusive
     * @param buffer the mutable character buffer, modified in place
     */
    private void reverse(char[] buffer, int lo, int hi) {
        while (lo < hi) {
            char tmp = buffer[lo];
            buffer[lo++] = buffer[hi];
            buffer[hi--] = tmp;
        }
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------
    @Test
    @DisplayName("Example 1: single-spaced sentence is reversed word by word")
    void singleSpacedSentence_wordsReversed() {
        assertEquals("blue is sky the", reverseWords("the sky is blue"));
        assertEquals("blue is sky the", reverseWordsInPlace("the sky is blue"));
    }

    @Test
    @DisplayName("Example 2: leading and trailing spaces are removed")
    void leadingAndTrailingSpaces_paddingRemoved() {
        assertEquals("world hello", reverseWords("  hello world  "));
        assertEquals("world hello", reverseWordsInPlace("  hello world  "));
    }

    @Test
    @DisplayName("Example 3: an interior run of spaces collapses to one")
    void multipleInteriorSpaces_collapsedToSingleSpace() {
        assertEquals("example good a", reverseWords("a good   example"));
        assertEquals("example good a", reverseWordsInPlace("a good   example"));
    }

    @Test
    @DisplayName("A one-character input is returned unchanged")
    void singleCharacter_returnedUnchanged() {
        assertEquals("a", reverseWords("a"));
        assertEquals("a", reverseWordsInPlace("a"));
    }

    @Test
    @DisplayName("A lone word wrapped in spaces loses the padding and stays intact")
    void singleWordSurroundedBySpaces_paddingRemoved() {
        assertEquals("hello", reverseWords("   hello   "));
        assertEquals("hello", reverseWordsInPlace("   hello   "));
    }

    @Test
    @DisplayName("Spaces only at the front are removed without shifting the words")
    void onlyLeadingSpaces_paddingRemoved() {
        assertEquals("world hello", reverseWords("   hello world"));
        assertEquals("world hello", reverseWordsInPlace("   hello world"));
    }

    @Test
    @DisplayName("Spaces only at the end are removed without shifting the words")
    void onlyTrailingSpaces_paddingRemoved() {
        assertEquals("world hello", reverseWords("hello world   "));
        assertEquals("world hello", reverseWordsInPlace("hello world   "));
    }

    @Test
    @DisplayName("Digits and mixed case are preserved exactly inside each word")
    void digitsAndMixedCase_charactersPreserved() {
        assertEquals("C3 b2 A1", reverseWords("A1 b2   C3"));
        assertEquals("C3 b2 A1", reverseWordsInPlace("A1 b2   C3"));
    }

    @Test
    @DisplayName("Every word is separated by a single space and the result has no padding")
    void anyValidInput_outputHasNoDoubleOrEdgeSpaces() {
        String output = reverseWords("  a  bb   ccc    dddd  ");
        assertEquals("dddd ccc bb a", output);
        assertEquals(output.trim(), output);
        assertEquals(false, output.contains("  "));
    }

}