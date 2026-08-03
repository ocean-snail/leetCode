package topInterview150;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Given two strings needle and haystack, return the index of the first
 * occurrence of needle in haystack, or -1 if needle is not part of haystack.
 * 
 * 
 * ? Example 1:
 * 
 * Input: haystack = "sadbutsad", needle = "sad"
 * Output: 0
 * Explanation: "sad" occurs at index 0 and 6.
 * The first occurrence is at index 0, so we return 0.
 * 
 * ? Example 2:
 * 
 * Input: haystack = "leetcode", needle = "leeto"
 * Output: -1
 * Explanation: "leeto" did not occur in "leetcode", so we return -1.
 * 
 * 
 * ! Constraints:
 * 
 * 1 <= haystack.length, needle.length <= 104
 * haystack and needle consist of only lowercase English characters.
 */
public class FindTheIndexOfTheFirstOccurrenceInAString {

    // ------------------------------------------------------------------
    // Solution
    // ------------------------------------------------------------------

    /**
     * Returns the smallest index at which `needle` occurs inside `haystack`, or -1.
     *
     * * Time: O(n + m) worst and average - the border table reads each of the m
     * needle
     * characters once, the scan reads each of the n haystack characters once, and
     * both inner while-loops are amortised O(1) (proof in section 2.3). Best case
     * is
     * O(m): when the occurrence starts at index 0 the method returns after m
     * iterations of the scan loop, leaving n - m characters unread.
     * * Space: O(m) - one int array of length m; the two strings are inputs, not
     * allocations.
     *
     * @param haystack the text to search, length n, 1 <= n <= 10^4, lowercase
     *                 letters only
     * @param needle   the pattern to find, length m, 1 <= m <= 10^4, lowercase
     *                 letters only
     * @return the index of the first occurrence of `needle` in `haystack`, or -1 if
     *         absent
     */
    public int strStr(String haystack, String needle) {
        int n = haystack.length();
        int m = needle.length();
        if (m > n) {
            return -1;
        }
        int[] border = buildBorderTable(needle);
        int matched = 0;
        for (int i = 0; i < n; i++) {
            char current = haystack.charAt(i);
            while (matched > 0 && needle.charAt(matched) != current) {
                matched = border[matched - 1];
            }
            if (needle.charAt(matched) == current) {
                matched++;
            }
            if (matched == m) {
                return i - m + 1;
            }
        }
        return -1;
    }

    /**
     * Builds the border table: border[k] is the length of the longest proper prefix
     * of
     * needle[0..k] that is also a suffix of needle[0..k].
     *
     * 
     * * Time: O(m) - `length` increases at most once per iteration and every
     * iteration of the
     * inner while-loop strictly decreases it, so the inner loop runs at most m
     * times
     * in total across the whole method.
     * * Space: O(m) - the returned int array; no other allocation.
     *
     * @param needle the pattern whose prefix borders are computed, length m >= 1
     * @return an int array of length m holding the border length of every needle
     *         prefix
     */
    private int[] buildBorderTable(String needle) {
        int m = needle.length();
        int[] border = new int[m];
        int length = 0;
        for (int i = 1; i < m; i++) {
            char current = needle.charAt(i);
            while (length > 0 && needle.charAt(length) != current) {
                length = border[length - 1];
            }
            if (needle.charAt(length) == current) {
                length++;
            }
            border[i] = length;
        }
        return border;
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------

    @Test
    @DisplayName("Example 1: two occurrences, the earlier index is returned")
    void example1_returnsFirstOfTwoOccurrences() {
        assertEquals(0, strStr("sadbutsad", "sad"));
    }

    @Test
    @DisplayName("Example 2: needle absent, -1 is returned")
    void example2_returnsMinusOne() {
        assertEquals(-1, strStr("leetcode", "leeto"));
    }

    @Test
    @DisplayName("Needle longer than haystack: rejected before the scan starts")
    void needleLongerThanHaystack_returnsMinusOne() {
        assertEquals(-1, strStr("abc", "abcd"));
    }

    @Test
    @DisplayName("Shortest legal input, characters equal")
    void singleCharacterMatch_returnsZero() {
        assertEquals(0, strStr("a", "a"));
    }

    @Test
    @DisplayName("Shortest legal input, characters different")
    void singleCharacterMismatch_returnsMinusOne() {
        assertEquals(-1, strStr("a", "b"));
    }

    @Test
    @DisplayName("Needle equals haystack: the match starts at index 0")
    void needleEqualsHaystack_returnsZero() {
        assertEquals(0, strStr("abc", "abc"));
    }

    @Test
    @DisplayName("Match strictly inside the haystack")
    void matchInMiddle_returnsStartIndex() {
        assertEquals(2, strStr("hello", "ll"));
    }

    @Test
    @DisplayName("Match ends at the final haystack character")
    void matchAtLastIndex_returnsLastIndex() {
        assertEquals(3, strStr("aaab", "b"));
    }

    @Test
    @DisplayName("Partial prefix matched then abandoned, real match found later")
    void partialPrefixBeforeMatch_returnsLaterIndex() {
        assertEquals(4, strStr("mississippi", "issip"));
    }

    @Test
    @DisplayName("Partial prefix matched twice, never completed")
    void partialPrefixWithoutMatch_returnsMinusOne() {
        assertEquals(-1, strStr("mississippi", "issipi"));
    }

    @Test
    @DisplayName("All characters identical: border table is 0,1,2,... and the match is immediate")
    void repeatedCharacters_returnsZero() {
        assertEquals(0, strStr("aaaaa", "aaa"));
    }

    @Test
    @DisplayName("All characters identical except the needle's last: worst shape for the naive scan")
    void repeatedCharactersWithFinalMismatch_returnsMinusOne() {
        assertEquals(-1, strStr("aaaaa", "aaab"));
    }

    @Test
    @DisplayName("Mismatch resolved by a non-zero border entry rather than by a reset to 0")
    void borderTableReuseAfterMismatch_returnsIndexFour() {
        assertEquals(4, strStr("aabaaabaaac", "aabaaac"));
    }

    @Test
    @DisplayName("Constraint ceiling, pathological shape: 10^4 characters, needle of 5001")
    void constraintCeilingPathologicalInput_returnsMiddleIndex() {
        String haystack = "a".repeat(9_999) + "b";
        String needle = "a".repeat(5_000) + "b";
        assertEquals(4_999, strStr(haystack, needle));
    }
}