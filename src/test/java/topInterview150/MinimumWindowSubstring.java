package topInterview150;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Given two strings s and t of lengths m and n respectively, return the minimum
 * window substring of s such that every character in t (including duplicates)
 * is included in the window. If there is no such substring, return the empty
 * string "".
 * 
 * The testcases will be generated such that the answer is unique.
 * 
 * 
 * ? Example 1:
 * 
 * Input: s = "ADOBECODEBANC", t = "ABC"
 * Output: "BANC"
 * Explanation: The minimum window substring "BANC" includes 'A', 'B', and 'C'
 * from string t.
 * 
 * ? Example 2:
 * 
 * Input: s = "a", t = "a"
 * Output: "a"
 * Explanation: The entire string s is the minimum window.
 * 
 * ? Example 3:
 * 
 * Input: s = "a", t = "aa"
 * Output: ""
 * Explanation: Both 'a's from t must be included in the window.
 * Since the largest window of s only has one 'a', return empty string.
 * 
 * 
 * ! Constraints:
 * 
 * m == s.length
 * n == t.length
 * 1 <= m, n <= 105
 * s and t consist of uppercase and lowercase English letters.
 * 
 * 
 * Follow up: Could you find an algorithm that runs in O(m + n) time?
 */

public class MinimumWindowSubstring {

    // ------------------------------------------------------------------
    // Solution
    // ------------------------------------------------------------------

    /**
     * Returns the shortest substring of s that contains every character of t
     * including duplicates.
     *
     * * Time: O(m + n) - the loop over t runs n times; index right advances m times
     * and index left
     * advances at most m times in total across all iterations of the inner while
     * loop.
     * * Space: O(1) - one fixed 128-element int array, independent of m and n.
     *
     * @param s the source string that is scanned once from left to right
     * @param t the requirement string whose character multiset must be covered
     * @return the leftmost shortest covering substring, or the empty string when
     *         none exists
     */
    public String minWindow(String s, String t) {
        int m = s.length();
        int n = t.length();
        int[] need = new int[128];
        for (int i = 0; i < n; i++) {
            need[t.charAt(i)]++;
        }
        int missing = n;
        int bestStart = 0;
        int bestLength = Integer.MAX_VALUE;
        int left = 0;
        for (int right = 0; right < m; right++) {
            char entering = s.charAt(right);
            if (need[entering] > 0) {
                missing--;
            }
            need[entering]--;
            while (missing == 0) {
                if (right - left + 1 < bestLength) {
                    bestLength = right - left + 1;
                    bestStart = left;
                }
                char leaving = s.charAt(left);
                need[leaving]++;
                if (need[leaving] > 0) {
                    missing++;
                }
                left++;
            }
        }
        return bestLength == Integer.MAX_VALUE ? "" : s.substring(bestStart, bestStart + bestLength);
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------

    @Test
    @DisplayName("LeetCode example 1: the shortest window of ADOBECODEBANC covering ABC is BANC")
    void leetcodeExampleOne_returnsBanc() {
        assertEquals("BANC", minWindow("ADOBECODEBANC", "ABC"));
    }

    @Test
    @DisplayName("LeetCode example 2: a single-character source that equals t returns that source")
    void sourceEqualsSingleCharacterTarget_returnsWholeSource() {
        assertEquals("a", minWindow("a", "a"));
    }

    @Test
    @DisplayName("LeetCode example 3: t needs two copies of a but s holds one, so no window exists")
    void targetNeedsMoreCopiesThanSourceHolds_returnsEmptyString() {
        assertEquals("", minWindow("a", "aa"));
    }

    @Test
    @DisplayName("A target of three identical characters selects three consecutive copies")
    void duplicateTargetCharacters_returnsRunOfThatCharacter() {
        assertEquals("aaa", minWindow("aaflslflsldkalskaaa", "aaa"));
    }

    @Test
    @DisplayName("Uppercase A and lowercase a are different characters, so no window covers A")
    void caseDiffersBetweenSourceAndTarget_returnsEmptyString() {
        assertEquals("", minWindow("ab", "A"));
    }

    @Test
    @DisplayName("A target character absent from the source yields no window")
    void targetCharacterMissingFromSource_returnsEmptyString() {
        assertEquals("", minWindow("abc", "d"));
    }

    @Test
    @DisplayName("A target longer than the source yields no window")
    void targetLongerThanSource_returnsEmptyString() {
        assertEquals("", minWindow("ab", "abc"));
    }

    @Test
    @DisplayName("When every source character is required, the whole source is the answer")
    void everySourceCharacterRequired_returnsWholeSource() {
        assertEquals("abcdefgh", minWindow("abcdefgh", "hgfedcba"));
    }

    @Test
    @DisplayName("The order of characters inside t does not constrain the window")
    void targetOrderReversedRelativeToSource_returnsSameWindow() {
        assertEquals("ba", minWindow("bba", "ab"));
    }

    @Test
    @DisplayName("The window may contain characters that appear in neither position of t")
    void windowContainsUnrelatedInteriorCharacters_returnsFourCharacterWindow() {
        assertEquals("cwae", minWindow("cabwefgewcwaefgcf", "cae"));
    }

    @Test
    @DisplayName("A shorter window found later replaces an earlier longer covering window")
    void laterShorterWindowFound_replacesEarlierCandidate() {
        assertEquals("baca", minWindow("acbbaca", "aba"));
    }

    @Test
    @DisplayName("A window ending at the last index of the source is reported correctly")
    void windowSitsAtSourceEnd_returnsSuffix() {
        assertEquals("b", minWindow("ab", "b"));
    }

    @Test
    @DisplayName("A window starting at index 0 is reported correctly")
    void windowSitsAtSourceStart_returnsPrefix() {
        assertEquals("b", minWindow("ba", "b"));
    }

    @Test
    @DisplayName("Target characters spread across the source force a window spanning them all")
    void targetCharactersSpreadOut_returnsSpanningWindow() {
        assertEquals("aybzc", minWindow("xaybzc", "abc"));
    }

    @Test
    @DisplayName("Requiring a second copy of A extends the window far beyond the single-copy answer")
    void extraCopyOfTargetCharacterRequired_returnsLongerWindow() {
        assertEquals("ADOBECODEBA", minWindow("ADOBECODEBANC", "ABCA"));
    }

    @Test
    @DisplayName("Two covering windows of equal minimum length resolve to the leftmost one")
    void twoMinimalWindowsOfEqualLength_returnsLeftmost() {
        assertEquals("ab", minWindow("aba", "ab"));
    }
}