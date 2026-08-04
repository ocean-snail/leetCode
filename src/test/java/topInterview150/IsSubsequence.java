package topInterview150;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Given two strings s and t, return true if s is a subsequence of t, or false
 * otherwise.
 * 
 * A subsequence of a string is a new string that is formed from the original
 * string by deleting some (can be none) of the characters without disturbing
 * the relative positions of the remaining characters. (i.e., "ace" is a
 * subsequence of "abcde" while "aec" is not).
 * 
 * 
 * ? Example 1:
 * 
 * Input: s = "abc", t = "ahbgdc"
 * Output: true
 * 
 * ? Example 2:
 * 
 * Input: s = "axc", t = "ahbgdc"
 * Output: false
 * 
 * 
 * ! Constraints:
 * 
 * 0 <= s.length <= 100
 * 0 <= t.length <= 104
 * s and t consist only of lowercase English letters.
 * 
 * 
 * Follow up: Suppose there are lots of incoming s, say s1, s2, ..., sk where k
 * >= 109, and you want to check one by one to see if t has its subsequence. In
 * this scenario, how would you change your code?
 */
public class IsSubsequence {

    // ------------------------------------------------------------------
    // Solution
    // ------------------------------------------------------------------

    /**
     * Reports whether every character of {@code s} appears in {@code t} in the same
     * left-to-right order, with any number of {@code t} characters skipped between
     * them.
     *
     * * Time: O(n) where n = t.length() - textIndex increases by exactly 1 per
     * iteration
     * and the loop stops at textIndex == n, so at most n iterations run.
     * * Space: O(1) - two int cursors, no allocation.
     *
     * @param s the candidate subsequence, 0 <= s.length() <= 100
     * @param t the text being searched, 0 <= t.length() <= 10000
     * @return true when s is a subsequence of t, false otherwise
     */
    public boolean isSubsequence(String s, String t) {
        int sourceIndex = 0;
        int textIndex = 0;
        while (sourceIndex < s.length() && textIndex < t.length()) {
            if (s.charAt(sourceIndex) == t.charAt(textIndex)) {
                sourceIndex++;
            }
            textIndex++;
        }
        return sourceIndex == s.length();
    }

    /**
     * Follow-up form: preprocesses one fixed text so that each later query costs
     * O(s.length()) instead of O(t.length()). Built once, queried k times.
     */
    public static final class SubsequenceMatcher {

        private final int[][] nextOccurrence;
        private final int textLength;

        /**
         * Builds nextOccurrence[i][c] = the smallest index j >= i with t.charAt(j) ==
         * c,
         * or textLength when the character does not occur at or after i.
         *
         * * Time: O(26 * n) - one row of 26 cells copied per character of t.
         * * Space: O(26 * n) - the table itself; 260026 ints (~1.0 MB) at n = 10000.
         *
         * @param t the fixed text every later query is checked against
         */
        public SubsequenceMatcher(String t) {
            textLength = t.length();
            nextOccurrence = new int[textLength + 1][26];
            Arrays.fill(nextOccurrence[textLength], textLength);
            for (int i = textLength - 1; i >= 0; i--) {
                System.arraycopy(nextOccurrence[i + 1], 0, nextOccurrence[i], 0, 26);
                nextOccurrence[i][t.charAt(i) - 'a'] = i;
            }
        }

        /**
         * * Time: O(m) where m = s.length() - one table lookup per source character,
         * independent of the text length.
         * * Space: O(1) - one int cursor beyond the prebuilt table.
         *
         * @param s the candidate subsequence
         * @return true when s is a subsequence of the preprocessed text
         */
        public boolean isSubsequence(String s) {
            int position = 0;
            for (int i = 0; i < s.length(); i++) {
                position = nextOccurrence[position][s.charAt(i) - 'a'];
                if (position == textLength) {
                    return false;
                }
                position++;
            }
            return true;
        }
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------

    @Test
    @DisplayName("Example 1: s='abc' is spread through t='ahbgdc' in order")
    void example1_returnsTrue() {
        assertTrue(isSubsequence("abc", "ahbgdc"));
    }

    @Test
    @DisplayName("Example 2: t='ahbgdc' contains no 'x' at all")
    void example2_returnsFalse() {
        assertFalse(isSubsequence("axc", "ahbgdc"));
    }

    @Test
    @DisplayName("Empty s is a subsequence of any t (delete every character of t)")
    void emptySource_returnsTrue() {
        assertTrue(isSubsequence("", "ahbgdc"));
    }

    @Test
    @DisplayName("Both strings empty: the loop never runs and 0 == 0 holds")
    void bothStringsEmpty_returnsTrue() {
        assertTrue(isSubsequence("", ""));
    }

    @Test
    @DisplayName("Empty t cannot supply the single character of s")
    void emptyTextWithNonEmptySource_returnsFalse() {
        assertFalse(isSubsequence("a", ""));
    }

    @Test
    @DisplayName("s longer than t: t runs out before s is finished")
    void sourceLongerThanText_returnsFalse() {
        assertFalse(isSubsequence("abc", "ab"));
    }

    @Test
    @DisplayName("s equal to t: every character matches at its own index")
    void sourceEqualsText_returnsTrue() {
        assertTrue(isSubsequence("abc", "abc"));
    }

    @Test
    @DisplayName("Relative order is required: 'aec' is not a subsequence of 'abcde'")
    void charactersPresentButOutOfOrder_returnsFalse() {
        assertFalse(isSubsequence("aec", "abcde"));
    }

    @Test
    @DisplayName("Repeated characters: the second 'a' of 'aab' must match a later 'a'")
    void repeatedCharacters_returnsTrue() {
        assertTrue(isSubsequence("aab", "abab"));
    }

    @Test
    @DisplayName("s as a prefix of t: the loop exits early on sourceIndex")
    void sourceIsPrefixOfText_returnsTrue() {
        assertTrue(isSubsequence("ahb", "ahbgdc"));
    }

    @Test
    @DisplayName("s as a suffix of t: the loop runs to the last character of t")
    void sourceIsSuffixOfText_returnsTrue() {
        assertTrue(isSubsequence("gdc", "ahbgdc"));
    }

    @Test
    @DisplayName("Constraint ceiling, matching: |s| = 100, |t| = 10000")
    void maximumSizedInputThatMatches_returnsTrue() {
        assertTrue(isSubsequence("ab".repeat(50), "ab".repeat(5_000)));
    }

    @Test
    @DisplayName("Constraint ceiling, not matching: 100 'z' against 10000 'a'")
    void maximumSizedInputThatDoesNotMatch_returnsFalse() {
        assertFalse(isSubsequence("z".repeat(100), "a".repeat(10_000)));
    }

    @Test
    @DisplayName("Follow-up matcher answers the two examples from one preprocessed text")
    void preprocessedMatcherOnExamples_matchesTwoPointerResults() {
        SubsequenceMatcher matcher = new SubsequenceMatcher("ahbgdc");
        assertTrue(matcher.isSubsequence("abc"));
        assertFalse(matcher.isSubsequence("axc"));
        assertTrue(matcher.isSubsequence(""));
        assertTrue(matcher.isSubsequence("ahbgdc"));
    }
}
