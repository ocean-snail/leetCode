package topInterview150;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Given two strings s and t, determine if they are isomorphic.
 * 
 * Two strings s and t are isomorphic if the characters in s can be replaced to
 * get t.
 * 
 * All occurrences of a character must be replaced with another character while
 * preserving the order of characters. No two characters may map to the same
 * character, but a character may map to itself.
 * 
 * 
 * ? Example 1:
 * 
 * Input: s = "egg", t = "add"
 * 
 * Output: true
 * 
 * Explanation:
 * 
 * The strings s and t can be made identical by:
 * 
 * Mapping 'e' to 'a'.
 * Mapping 'g' to 'd'.
 * 
 * ? Example 2:
 * 
 * Input: s = "f11", t = "b23"
 * 
 * Output: false
 * 
 * Explanation:
 * 
 * The strings s and t can not be made identical as '1' needs to be mapped to
 * both '2' and '3'.
 * 
 * ? Example 3:
 * 
 * Input: s = "paper", t = "title"
 * 
 * Output: true
 * 
 * 
 * 
 * !Constraints:
 * 
 * 1 <= s.length <= 5 * 104
 * t.length == s.length
 * s and t consist of any valid ascii character.
 */
public class IsomorphicStrings {

    // ------------------------------------------------------------------
    // Solution
    // ------------------------------------------------------------------

    /**
     * Returns true when a one-to-one character mapping turns s into t.
     *
     * * Time: O(1) best - the loop cannot stop before index 1, because at index 0
     * both
     * tables hold only zeros and the equality check passes for every character
     * pair.
     * * Time: O(k) average over a mismatching pair - the loop stops at the first
     * index whose
     * two stored positions differ, where k is that index.
     * * Time: O(n) worst - every index of s is read once when the two strings are
     * isomorphic
     * or when the first differing index is the last one.
     * * Space: O(1) - two int arrays of fixed length 128 (256 ints total),
     * independent of n.
     *
     * @param s the source string, length n, ASCII characters only
     * @param t the target string, same length as s, ASCII characters only
     * @return true when s and t are isomorphic, false otherwise
     */
    public boolean isIsomorphic(String s, String t) {
        int[] lastPositionInS = new int[128];
        int[] lastPositionInT = new int[128];

        int n = s.length();
        for (int i = 0; i < n; i++) {
            char sc = s.charAt(i);
            char tc = t.charAt(i);

            if (lastPositionInS[sc] != lastPositionInT[tc]) {
                return false;
            }

            lastPositionInS[sc] = i + 1;
            lastPositionInT[tc] = i + 1;
        }
        return true;
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------

    @Test
    @DisplayName("Example 1: egg and add are isomorphic because e maps to a and g maps to d")
    void twoDistinctLettersWithRepeatedTail_returnsTrue() {
        assertTrue(isIsomorphic("egg", "add"));
    }

    @Test
    @DisplayName("Example 2: f11 and b23 are not isomorphic because 1 would need two targets")
    void oneSourceCharacterNeedingTwoTargets_returnsFalse() {
        assertFalse(isIsomorphic("f11", "b23"));
    }

    @Test
    @DisplayName("Example 3: paper and title are isomorphic")
    void fiveLetterPairWithSharedRepeatPattern_returnsTrue() {
        assertTrue(isIsomorphic("paper", "title"));
    }

    @Test
    @DisplayName("Two different source characters mapping onto one target character returns false")
    void twoSourceCharactersSharingOneTarget_returnsFalse() {
        assertFalse(isIsomorphic("ab", "aa"));
    }

    @Test
    @DisplayName("A single character mapped to itself is isomorphic")
    void singleCharacterMappedToItself_returnsTrue() {
        assertTrue(isIsomorphic("a", "a"));
    }

    @Test
    @DisplayName("A single character mapped to a different character is isomorphic")
    void singleCharacterMappedToAnotherCharacter_returnsTrue() {
        assertTrue(isIsomorphic("a", "b"));
    }

    @Test
    @DisplayName("A pair that agrees on first appearances but differs later returns false")
    void patternsAgreeingOnFirstOccurrencesOnly_returnsFalse() {
        assertFalse(isIsomorphic("abcd", "abab"));
    }

    @Test
    @DisplayName("Identical strings are isomorphic through the identity mapping")
    void identicalStrings_returnsTrue() {
        assertTrue(isIsomorphic("abca", "abca"));
    }

    @Test
    @DisplayName("Characters at both ends of the ASCII range are indexed correctly")
    void asciiCodeZeroAndAsciiCode127_returnsTrue() {
        String low = new String(new char[] { 0, 127, 0 });
        String high = new String(new char[] { 127, 0, 127 });
        assertTrue(isIsomorphic(low, high));
    }

    @Test
    @DisplayName("Sixty-four distinct characters mapped onto sixty-four other characters returns true")
    void sixtyFourDistinctCharactersShiftedByOne_returnsTrue() {
        char[] source = new char[64];
        char[] target = new char[64];
        for (int i = 0; i < 64; i++) {
            source[i] = (char) (i + 32);
            target[i] = (char) (i + 33);
        }
        assertTrue(isIsomorphic(new String(source), new String(target)));
    }

    @Test
    @DisplayName("A 50000-character input at the constraint limit is handled without overflow")
    void inputAtConstraintUpperBound_returnsTrue() {
        int n = 50_000;
        StringBuilder source = new StringBuilder(n);
        StringBuilder target = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            source.append((char) ('a' + (i % 26)));
            target.append((char) ('A' + (i % 26)));
        }
        assertTrue(isIsomorphic(source.toString(), target.toString()));
    }
}