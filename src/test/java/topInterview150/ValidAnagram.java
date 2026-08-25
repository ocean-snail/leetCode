package topInterview150;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Given two strings s and t, return true if t is an anagram of s, and false
 * otherwise.
 * 
 * 
 * ? Example 1:
 * 
 * Input: s = "anagram", t = "nagaram"
 * 
 * Output: true
 * 
 * ? Example 2:
 * 
 * Input: s = "rat", t = "car"
 * 
 * Output: false
 * 
 * 
 * ! Constraints:
 * 
 * 1 <= s.length, t.length <= 5 * 104
 * s and t consist of lowercase English letters.
 * 
 * 
 * Follow up: What if the inputs contain Unicode characters? How would you adapt
 * your solution to such a case?
 */
public class ValidAnagram {

    // ------------------------------------------------------------------
    // Solution
    // ------------------------------------------------------------------

    /**
     * Decides whether t is a rearrangement of the characters of s.
     *
     * * Time: O(n) - one pass over both strings of length n, plus a fixed 26-slot
     * scan
     * * Space: O(1) - a single int[26] whose size does not depend on n
     *
     * @param s the base string, lowercase English letters only
     * @param t the candidate rearrangement, lowercase English letters only
     * @return true when both strings contain exactly the same letters with the same
     *         multiplicities
     */
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        int[] counts = new int[26];
        for (int i = 0; i < s.length(); i++) {
            counts[s.charAt(i) - 'a']++;
            counts[t.charAt(i) - 'a']--;
        }
        for (int count : counts) {
            if (count != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Follow-up variant: the same counting idea over Unicode code points instead of
     * the 26 lowercase letters, so characters outside the Basic Multilingual Plane
     * are counted as one unit rather than as two char values.
     *
     * * Time: O(n) - one pass per string plus one pass over at most n map entries
     * * Space: O(k) - one map entry per distinct code point, k bounded by n
     *
     * @param s the base string, any Unicode content
     * @param t the candidate rearrangement, any Unicode content
     * @return true when both strings contain the same code points with the same
     *         multiplicities
     */
    public boolean isAnagramUnicode(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        Map<Integer, Integer> counts = new HashMap<>();
        for (int i = 0; i < s.length();) {
            int codePoint = s.codePointAt(i);
            counts.merge(codePoint, 1, Integer::sum);
            i += Character.charCount(codePoint);
        }
        for (int i = 0; i < t.length();) {
            int codePoint = t.codePointAt(i);
            counts.merge(codePoint, -1, Integer::sum);
            i += Character.charCount(codePoint);
        }
        for (int count : counts.values()) {
            if (count != 0) {
                return false;
            }
        }
        return true;
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------

    @Test
    @DisplayName("The letters of anagram rearranged into nagaram are reported as an anagram")
    void rearrangedSameLetters_returnsTrue() {
        assertTrue(isAnagram("anagram", "nagaram"));
    }

    @Test
    @DisplayName("Two equal-length strings built from different letters are rejected")
    void differentLetters_returnsFalse() {
        assertFalse(isAnagram("rat", "car"));
    }

    @Test
    @DisplayName("Strings of different lengths are rejected before any counting happens")
    void differentLengths_returnsFalse() {
        assertFalse(isAnagram("ab", "abb"));
        assertFalse(isAnagram("abb", "ab"));
    }

    @Test
    @DisplayName("Two identical single-character strings are reported as an anagram")
    void singleIdenticalCharacter_returnsTrue() {
        assertTrue(isAnagram("a", "a"));
    }

    @Test
    @DisplayName("Same letter set with different repeat counts is rejected")
    void sameLettersDifferentMultiplicities_returnsFalse() {
        assertFalse(isAnagram("aacc", "ccca"));
    }

    @Test
    @DisplayName("A string reversed is reported as an anagram of itself")
    void wholeAlphabetReversed_returnsTrue() {
        assertTrue(isAnagram("abcdefghijklmnopqrstuvwxyz", "zyxwvutsrqponmlkjihgfedcba"));
    }

    @Test
    @DisplayName("A single swapped letter deep inside two long strings is rejected")
    void oneDifferingLetterInLongInput_returnsFalse() {
        String base = "ab".repeat(5000);
        String altered = base.substring(0, 4321) + "c" + base.substring(4322);
        assertFalse(isAnagram(base, altered));
    }

    @Test
    @DisplayName("Reordered non-Latin characters and emoji are reported as an anagram")
    void reorderedUnicodeCharacters_returnsTrue() {
        assertTrue(isAnagramUnicode("가나다\uD83D\uDE00", "\uD83D\uDE00다나가"));
    }
}