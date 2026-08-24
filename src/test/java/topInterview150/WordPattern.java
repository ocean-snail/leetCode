package topInterview150;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Given a pattern and a string s, find if s follows the same pattern.
 * 
 * Here follow means a full match, such that there is a bijection between a
 * letter in pattern and a non-empty word in s. Specifically:
 * 
 * Each letter in pattern maps to exactly one unique word in s.
 * Each unique word in s maps to exactly one letter in pattern.
 * No two letters map to the same word, and no two words map to the same letter.
 * 
 * 
 * ? Example 1:
 * 
 * Input: pattern = "abba", s = "dog cat cat dog"
 * 
 * Output: true
 * 
 * Explanation:
 * 
 * The bijection can be established as:
 * 
 * 'a' maps to "dog".
 * 'b' maps to "cat".
 * 
 * ? Example 2:
 * 
 * Input: pattern = "abba", s = "dog cat cat fish"
 * 
 * Output: false
 * 
 * ? Example 3:
 * 
 * Input: pattern = "aaaa", s = "dog cat cat dog"
 * 
 * Output: false
 * 
 * 
 * ! Constraints:
 * 
 * 1 <= pattern.length <= 300
 * pattern contains only lower-case English letters.
 * 1 <= s.length <= 3000
 * s contains only lowercase English letters and spaces ' '.
 * s does not contain any leading or trailing spaces.
 * All the words in s are separated by a single space.
 */

public class WordPattern {

    // ------------------------------------------------------------------
    // Solution
    // ------------------------------------------------------------------

    /**
     * Decides whether a bijection exists between the letters of pattern and the
     * words of s.
     *
     * * Time: O(n + m) - one pass over the m characters of s to split it, then one
     * pass over the
     * n characters of pattern where each iteration performs O(1) expected hash map
     * work
     * plus one string comparison bounded by the word length, summing to O(m)
     * overall.
     * * Space: O(m) - the split array plus the two maps hold at most every word of
     * s
     * once.
     *
     * @param pattern a string of lower-case letters, each letter acting as one
     *                placeholder
     * @param s       a space-separated sentence of lower-case words
     * @return true when the i-th letter binds to the i-th word consistently in both
     *         directions
     */
    public boolean wordPattern(String pattern, String s) {
        String[] words = s.split(" ");
        if (pattern.length() != words.length) {
            return false;
        }
        Map<Character, String> letterToWord = new HashMap<>();
        Map<String, Character> wordToLetter = new HashMap<>();
        for (int i = 0; i < pattern.length(); i++) {
            char letter = pattern.charAt(i);
            String word = words[i];
            String boundWord = letterToWord.putIfAbsent(letter, word);
            if (boundWord != null && !boundWord.equals(word)) {
                return false;
            }
            Character boundLetter = wordToLetter.putIfAbsent(word, letter);
            if (boundLetter != null && boundLetter.charValue() != letter) {
                return false;
            }
        }
        return true;
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------

    @Test
    @DisplayName("Pattern abba over dog cat cat dog admits a bijection and returns true")
    void mirroredPatternMatchesMirroredWords_returnsTrue() {
        assertTrue(wordPattern("abba", "dog cat cat dog"));
    }

    @Test
    @DisplayName("Pattern abba over dog cat cat fish breaks the binding of a at the last position")
    void lastWordContradictsEarlierBinding_returnsFalse() {
        assertFalse(wordPattern("abba", "dog cat cat fish"));
    }

    @Test
    @DisplayName("Pattern aaaa cannot bind the single letter a to two different words")
    void oneLetterBoundToTwoWords_returnsFalse() {
        assertFalse(wordPattern("aaaa", "dog cat cat dog"));
    }

    @Test
    @DisplayName("Pattern abba over dog dog dog dog maps two letters onto one word")
    void twoLettersBoundToOneWord_returnsFalse() {
        assertFalse(wordPattern("abba", "dog dog dog dog"));
    }

    @Test
    @DisplayName("A pattern shorter than the word list returns false before any binding is made")
    void patternShorterThanWordList_returnsFalse() {
        assertFalse(wordPattern("ab", "dog cat cat"));
    }

    @Test
    @DisplayName("A pattern longer than the word list returns false before any binding is made")
    void patternLongerThanWordList_returnsFalse() {
        assertFalse(wordPattern("abc", "dog cat"));
    }

    @Test
    @DisplayName("The shortest legal input, one letter and one word, returns true")
    void singleLetterAndSingleWord_returnsTrue() {
        assertTrue(wordPattern("a", "dog"));
    }

    @Test
    @DisplayName("A repeated word bound to a repeated letter at non-adjacent positions returns true")
    void nonAdjacentRepeatsAgree_returnsTrue() {
        assertTrue(wordPattern("abab", "dog cat dog cat"));
    }

    @Test
    @DisplayName("A word that is a prefix of another word is not treated as equal")
    void prefixWordIsNotEqualToLongerWord_returnsFalse() {
        assertFalse(wordPattern("aa", "do dog"));
    }
}
