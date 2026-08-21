package topInterview150;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Given two strings ransomNote and magazine, return true if ransomNote can be
 * constructed by using the letters from magazine and false otherwise.
 * 
 * Each letter in magazine can only be used once in ransomNote.
 * 
 * 
 * ? Example 1:
 * 
 * Input: ransomNote = "a", magazine = "b"
 * Output: false
 * 
 * ? Example 2:
 * 
 * Input: ransomNote = "aa", magazine = "ab"
 * Output: false
 * 
 * ? Example 3:
 * 
 * Input: ransomNote = "aa", magazine = "aab"
 * Output: true
 * 
 * 
 * ! Constraints:
 * 
 * 1 <= ransomNote.length, magazine.length <= 105
 * ransomNote and magazine consist of lowercase English letters.
 */
public class RansomNote {

    // ------------------------------------------------------------------
    // Solution
    // ------------------------------------------------------------------

    /**
     * Reports whether every character of ransomNote can be taken from magazine,
     * consuming each magazine character at most once.
     *
     * * Time: O(1) best case - when ransomNote is longer than magazine the length
     * guard returns before either loop starts.
     * O(n + m) average and worst case - one pass of m iterations over
     * magazine plus at most n iterations over ransomNote, where
     * n = ransomNote.length() and m = magazine.length().
     * * Space: O(1) - a single int[26] array whose size is fixed by the alphabet,
     * not by the input length.
     *
     * @param ransomNote the string that must be assembled from magazine letters
     * @param magazine   the supply of letters, each usable at most once
     * @return true if every ransomNote letter has an unused matching letter in
     *         magazine
     */
    public boolean canConstruct(String ransomNote, String magazine) {
        if (ransomNote.length() > magazine.length()) {
            return false;
        }
        int[] remaining = new int[26];
        for (int i = 0; i < magazine.length(); i++) {
            remaining[magazine.charAt(i) - 'a']++;
        }
        for (int i = 0; i < ransomNote.length(); i++) {
            if (--remaining[ransomNote.charAt(i) - 'a'] < 0) {
                return false;
            }
        }
        return true;
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------

    @Test
    @DisplayName("A single letter missing from magazine cannot be constructed")
    void singleLetterAbsentFromMagazine_returnsFalse() {
        assertFalse(canConstruct("a", "b"));
    }

    @Test
    @DisplayName("Two copies of a letter cannot be taken from a magazine holding one")
    void duplicateLetterWithSingleSupply_returnsFalse() {
        assertFalse(canConstruct("aa", "ab"));
    }

    @Test
    @DisplayName("Two copies of a letter can be taken from a magazine holding two")
    void duplicateLetterWithSufficientSupply_returnsTrue() {
        assertTrue(canConstruct("aa", "aab"));
    }

    @Test
    @DisplayName("A note equal to its magazine is constructible")
    void noteIdenticalToMagazine_returnsTrue() {
        assertTrue(canConstruct("abc", "abc"));
    }

    @Test
    @DisplayName("A note whose letters are a permutation of the magazine is constructible")
    void notePermutesMagazineOfEqualLength_returnsTrue() {
        assertTrue(canConstruct("cba", "abc"));
    }

    @Test
    @DisplayName("A note longer than its magazine is rejected")
    void noteLongerThanMagazine_returnsFalse() {
        assertFalse(canConstruct("aab", "aa"));
    }

    @Test
    @DisplayName("Magazine letters left unused do not prevent construction")
    void magazineHoldsUnusedSurplusLetters_returnsTrue() {
        assertTrue(canConstruct("ab", "zzabzz"));
    }

    @Test
    @DisplayName("A deficit in the last distinct letter is still detected")
    void deficitInFinalDistinctLetter_returnsFalse() {
        assertFalse(canConstruct("abcd", "abcaaa"));
    }

    @Test
    @DisplayName("Every letter of the alphabet is counted in its own slot")
    void allTwentySixLettersRequested_returnsTrue() {
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        assertTrue(canConstruct(alphabet, new StringBuilder(alphabet).reverse().toString()));
    }
}