package topInterview150;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * You are given a string s and an array of strings words. All the strings of
 * words are of the same length.
 * 
 * A concatenated string is a string that exactly contains all the strings of
 * any permutation of words concatenated.
 * 
 * For example, if words = ["ab","cd","ef"], then "abcdef", "abefcd", "cdabef",
 * "cdefab", "efabcd", and "efcdab" are all concatenated strings. "acdbef" is
 * not a concatenated string because it is not the concatenation of any
 * permutation of words.
 * Return an array of the starting indices of all the concatenated substrings in
 * s. You can return the answer in any order.
 * 
 * 
 * ? Example 1:
 * 
 * Input: s = "barfoothefoobarman", words = ["foo","bar"]
 * 
 * Output: [0,9]
 * 
 * Explanation:
 * 
 * The substring starting at 0 is "barfoo". It is the concatenation of
 * ["bar","foo"] which is a permutation of words.
 * The substring starting at 9 is "foobar". It is the concatenation of
 * ["foo","bar"] which is a permutation of words.
 * 
 * ? Example 2:
 * 
 * Input: s = "wordgoodgoodgoodbestword", words = ["word","good","best","word"]
 * 
 * Output: []
 * 
 * Explanation:
 * 
 * There is no concatenated substring.
 * 
 * ? Example 3:
 * 
 * Input: s = "barfoofoobarthefoobarman", words = ["bar","foo","the"]
 * 
 * Output: [6,9,12]
 * 
 * Explanation:
 * 
 * The substring starting at 6 is "foobarthe". It is the concatenation of
 * ["foo","bar","the"].
 * The substring starting at 9 is "barthefoo". It is the concatenation of
 * ["bar","the","foo"].
 * The substring starting at 12 is "thefoobar". It is the concatenation of
 * ["the","foo","bar"].
 * 
 * 
 * 
 * ! Constraints:
 * 
 * 1 <= s.length <= 104
 * 1 <= words.length <= 5000
 * 1 <= words[i].length <= 30
 * s and words[i] consist of lowercase English letters.
 */

public class SubstringWithConcatenationOfAllWords {

    // ------------------------------------------------------------------
    // Solution
    // ------------------------------------------------------------------

    /**
     * Returns every start index at which a concatenation of all of words occurs in
     * s.
     *
     * * Time: best O(1) - when s is shorter than the total concatenated length the
     * guard
     * returns before any map is built.
     * average and worst O(n * k) - the outer loop runs k times, each pass visits
     * about n / k word slots, and reading plus hashing one slot costs O(k); the
     * shrink loop moves left forward only, so it is amortised into that scan.
     * Building the requirement map adds O(m * k).
     * * Space: O(m * k) - the requirement map and the window map each hold at most
     * m
     * distinct words of k characters. The returned list is excluded.
     *
     * @param s     the text to scan; n denotes its length
     * @param words the multiset of words to concatenate; m denotes its length and k
     *              the
     *              common length of each entry
     * @return the start indices, in no particular order
     */
    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> starts = new ArrayList<>();
        int k = words[0].length();
        int m = words.length;
        int span = k * m;
        if (s.length() < span) {
            return starts;
        }
        Map<String, Integer> need = new HashMap<>();
        for (String word : words) {
            need.merge(word, 1, Integer::sum);
        }
        for (int offset = 0; offset < k; offset++) {
            Map<String, Integer> window = new HashMap<>();
            int left = offset;
            int count = 0;
            for (int right = offset; right + k <= s.length(); right += k) {
                String slot = s.substring(right, right + k);
                if (!need.containsKey(slot)) {
                    window.clear();
                    count = 0;
                    left = right + k;
                    continue;
                }
                window.merge(slot, 1, Integer::sum);
                count++;
                while (window.get(slot) > need.get(slot)) {
                    String leaving = s.substring(left, left + k);
                    window.merge(leaving, -1, Integer::sum);
                    left += k;
                    count--;
                }
                if (count == m) {
                    starts.add(left);
                    String leaving = s.substring(left, left + k);
                    window.merge(leaving, -1, Integer::sum);
                    left += k;
                    count--;
                }
            }
        }
        return starts;
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------

    /**
     * Sorts a copy of the indices so that assertions compare content, not emission
     * order.
     *
     * Time: O(r log r) - one comparison sort of the r returned indices.
     * Space: O(r) - the copied list.
     *
     * @param indices the indices returned by a solution method
     * @return a new list holding the same indices in ascending order
     */
    private static List<Integer> ascending(List<Integer> indices) {
        List<Integer> copy = new ArrayList<>(indices);
        Collections.sort(copy);
        return copy;
    }

    @Test
    @DisplayName("Example 1: two words, two matches at opposite ends")
    void twoWordsWithMatchesAtBothEnds_returnsBothStarts() {
        assertEquals(List.of(0, 9),
                ascending(findSubstring("barfoothefoobarman", new String[] { "foo", "bar" })));
    }

    @Test
    @DisplayName("Example 2: duplicate required word never satisfied")
    void requiredWordCountNeverMet_returnsEmpty() {
        assertEquals(List.of(),
                ascending(findSubstring("wordgoodgoodgoodbestword",
                        new String[] { "word", "good", "best", "word" })));
    }

    @Test
    @DisplayName("Example 3: three overlapping matches one word apart")
    void overlappingMatchesOneWordApart_returnsAllThreeStarts() {
        assertEquals(List.of(6, 9, 12),
                ascending(findSubstring("barfoofoobarthefoobarman",
                        new String[] { "bar", "foo", "the" })));
    }

    @Test
    @DisplayName("Text shorter than the concatenated length returns nothing")
    void textShorterThanSpan_returnsEmpty() {
        assertEquals(List.of(),
                ascending(findSubstring("bar", new String[] { "foo", "bar" })));
    }

    @Test
    @DisplayName("Text exactly as long as the concatenation matches at index 0")
    void textExactlyOneWindowLong_returnsZero() {
        assertEquals(List.of(0),
                ascending(findSubstring("foobar", new String[] { "bar", "foo" })));
    }

    @Test
    @DisplayName("Single word behaves like plain substring search")
    void singleWord_returnsEveryOccurrence() {
        assertEquals(List.of(0, 3, 6),
                ascending(findSubstring("abcabcabc", new String[] { "abc" })));
    }

    @Test
    @DisplayName("Word length one reduces to an anagram window")
    void wordLengthOne_returnsAnagramWindowStarts() {
        assertEquals(List.of(0, 2, 3),
                ascending(findSubstring("abcbac", new String[] { "a", "b", "c" })));
    }

    @Test
    @DisplayName("Identical repeated words match at every offset in the run")
    void identicalRepeatedWords_returnsEveryOffsetInRun() {
        assertEquals(List.of(0, 1, 2),
                ascending(findSubstring("aaaaaa", new String[] { "aa", "aa" })));
    }

    @Test
    @DisplayName("Match placed at the very end of the text is found")
    void matchAtEndOfText_returnsFinalStart() {
        assertEquals(List.of(3),
                ascending(findSubstring("xyzfoobar", new String[] { "foo", "bar" })));
    }

    @Test
    @DisplayName("An unknown word between two halves prevents any match")
    void unknownWordSplitsCandidate_returnsEmpty() {
        assertEquals(List.of(),
                ascending(findSubstring("foozzzbar", new String[] { "foo", "bar" })));
    }

    @Test
    @DisplayName("Surplus copy of a required word shrinks the window instead of resetting it")
    void surplusCopyOfRequiredWord_stillFindsLaterMatch() {
        assertEquals(List.of(3),
                ascending(findSubstring("foofoobar", new String[] { "foo", "bar" })));
    }

    @Test
    @DisplayName("Matches in different remainder classes are all reported")
    void matchesInDifferentRemainderClasses_returnsBoth() {
        assertEquals(List.of(0, 3),
                ascending(findSubstring("aabaaba", new String[] { "aa", "ba" })));
    }

    @Test
    @DisplayName("Match starting off the multiple-of-k grid is still found")
    void matchStartsOffTheWordGrid_returnsThatStart() {
        assertEquals(List.of(1),
                ascending(findSubstring("xfoobar", new String[] { "foo", "bar" })));
    }

    @Test
    @DisplayName("Required word absent from the text returns nothing")
    void requiredWordAbsent_returnsEmpty() {
        assertEquals(List.of(),
                ascending(findSubstring("aaaaaa", new String[] { "aa", "bb" })));
    }

    @Test
    @DisplayName("Maximum-size input stays within the linear-per-offset scan")
    void maximumSizedInput_returnsWithoutQuadraticBlowup() {
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < 10_000; i++) {
            text.append('a');
        }
        String[] words = new String[5_000];
        Arrays.fill(words, "aa");
        assertEquals(List.of(0), ascending(findSubstring(text.toString(), words)));
    }
}