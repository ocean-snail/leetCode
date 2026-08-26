package topInterview150;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Given an array of strings strs, group the anagrams together. You can return
 * the answer in any order.
 * 
 * 
 * ? Example 1:
 * 
 * Input: strs = ["eat","tea","tan","ate","nat","bat"]
 * 
 * Output: [["bat"],["nat","tan"],["ate","eat","tea"]]
 * 
 * Explanation:
 * 
 * There is no string in strs that can be rearranged to form "bat".
 * The strings "nat" and "tan" are anagrams as they can be rearranged to form
 * each other.
 * The strings "ate", "eat", and "tea" are anagrams as they can be rearranged to
 * form each other.
 * 
 * ? Example 2:
 * 
 * Input: strs = [""]
 * 
 * Output: [[""]]
 * 
 * ? Example 3:
 * 
 * Input: strs = ["a"]
 * 
 * Output: [["a"]]
 * 
 * 
 * ! Constraints:
 * 
 * 1 <= strs.length <= 104
 * 0 <= strs[i].length <= 100
 * strs[i] consists of lowercase English letters.
 */
public class GroupAnagrams {

    // ------------------------------------------------------------------
    // Solution
    // ------------------------------------------------------------------

    /**
     * Groups strings that contain exactly the same letters with the same
     * multiplicities.
     *
     * * Time: O(n * (k + 26)) average - each of the n strings is read once to fill
     * the count array
     * (k character reads) and the 26 counts are converted into one key; a HashMap
     * lookup on
     * that fixed-length key costs O(26) on average.
     * * Time: O(n * (k + 26 * log g)) worst - if every signature falls into one
     * HashMap bucket, the
     * bucket becomes a red-black tree and one lookup performs O(log g) key
     * comparisons, each
     * comparison scanning a key of at most 78 characters (g = number of distinct
     * groups).
     * * Space: O(n * k + g * 26) - the returned lists hold references to all n
     * input
     * strings, and the
     * map stores one signature key per distinct group.
     *
     * @param strs the input strings, each consisting of lowercase English letters
     * @return one list per anagram class, each holding the input strings of that
     *         class
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> groupsBySignature = new HashMap<>();
        for (String s : strs) {
            groupsBySignature.computeIfAbsent(letterCountSignature(s), key -> new ArrayList<>()).add(s);
        }
        return new ArrayList<>(groupsBySignature.values());
    }

    /**
     * Builds the count signature of one string, for example "ate" produces
     * "1#0#...#1#0#...#1#...".
     *
     * * Time: O(k + 26) - one pass over the k characters of s, then 26 appends.
     * * Space: O(26) - the fixed count array plus a key of at most 78 characters
     * (26
     * counts of at
     * most three digits, each followed by one separator).
     *
     * @param s a string of lowercase English letters
     * @return a string that is equal for two inputs exactly when the inputs are
     *         anagrams
     */
    public String letterCountSignature(String s) {
        int[] counts = new int[26];
        for (int i = 0; i < s.length(); i++) {
            counts[s.charAt(i) - 'a']++;
        }
        StringBuilder signature = new StringBuilder();
        for (int letter = 0; letter < 26; letter++) {
            signature.append(counts[letter]).append('#');
        }
        return signature.toString();
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------

    @Test
    @DisplayName("Example 1: six strings are split into the three anagram classes ate/bat/nat")
    void example1_groupsSixStringsIntoThreeAnagramClasses() {
        String[] strs = { "eat", "tea", "tan", "ate", "nat", "bat" };
        assertEquals(
                List.of(List.of("ate", "eat", "tea"), List.of("bat"), List.of("nat", "tan")),
                canonical(groupAnagrams(strs)));
    }

    @Test
    @DisplayName("Example 2: an input holding only the empty string returns one group holding it")
    void singleEmptyString_returnsOneGroupHoldingTheEmptyString() {
        String[] strs = { "" };
        assertEquals(List.of(List.of("")), canonical(groupAnagrams(strs)));
    }

    @Test
    @DisplayName("Example 3: an input holding one single-character string returns one group")
    void singleOneCharacterString_returnsOneGroupHoldingThatString() {
        String[] strs = { "a" };
        assertEquals(List.of(List.of("a")), canonical(groupAnagrams(strs)));
    }

    @Test
    @DisplayName("Counts of ten or more stay distinguishable: 11 a's + 1 b differs from 1 a + 11 b's")
    void countsOfTenOrMore_areNotMergedWithDifferentCountSplits() {
        String[] strs = { "aaaaaaaaaaab", "abbbbbbbbbbb" };
        assertEquals(
                List.of(List.of("aaaaaaaaaaab"), List.of("abbbbbbbbbbb")),
                canonical(groupAnagrams(strs)));
    }

    @Test
    @DisplayName("A string repeated in the input appears once per occurrence inside its group")
    void repeatedIdenticalStrings_areKeptAsSeparateEntriesInOneGroup() {
        String[] strs = { "ab", "ab", "ba" };
        assertEquals(List.of(List.of("ab", "ab", "ba")), canonical(groupAnagrams(strs)));
    }

    @Test
    @DisplayName("Strings with no shared letters produce one group per string")
    void stringsWithDisjointLetters_returnOneGroupEach() {
        String[] strs = { "a", "b", "c" };
        assertEquals(List.of(List.of("a"), List.of("b"), List.of("c")), canonical(groupAnagrams(strs)));
    }

    @Test
    @DisplayName("Same letter set with different multiplicities: aab and abb stay in separate groups")
    void sameLetterSetDifferentMultiplicities_returnSeparateGroups() {
        String[] strs = { "aab", "abb" };
        assertEquals(List.of(List.of("aab"), List.of("abb")), canonical(groupAnagrams(strs)));
    }

    @Test
    @DisplayName("The last letter z takes part in the signature: az, a and z are three groups")
    void stringsContainingLetterZ_areSeparatedFromStringsWithoutIt() {
        String[] strs = { "az", "a", "z" };
        assertEquals(List.of(List.of("a"), List.of("az"), List.of("z")), canonical(groupAnagrams(strs)));
    }

    @Test
    @DisplayName("Empty strings form their own group next to the non-empty strings")
    void mixedEmptyAndNonEmptyStrings_groupEmptyStringsTogether() {
        String[] strs = { "", "", "a" };
        assertEquals(List.of(List.of("", ""), List.of("a")), canonical(groupAnagrams(strs)));
    }

    @Test
    @DisplayName("Constraint ceiling: 10000 random strings of length 100 form a valid partition")
    void constraintCeilingDistinctStrings_partitionByAnagramClass() {
        Random random = new Random(4949L);
        String[] strs = new String[10_000];
        for (int i = 0; i < strs.length; i++) {
            strs[i] = randomString(random, 100, 26);
        }
        assertPartitionsByAnagramClass(strs, groupAnagrams(strs));
    }

    /**
     * Sorts every group and then sorts the groups, so that two results can be
     * compared with equals
     * even though the problem allows the groups and their contents in any order.
     *
     * @param groups a grouping produced by either implementation
     * @return the same grouping in a fixed order
     */
    static List<List<String>> canonical(List<List<String>> groups) {
        List<List<String>> ordered = new ArrayList<>();
        for (List<String> group : groups) {
            List<String> sortedGroup = new ArrayList<>(group);
            Collections.sort(sortedGroup);
            ordered.add(sortedGroup);
        }
        ordered.sort(GroupAnagrams::compareStringLists);
        return ordered;
    }

    /**
     * Orders two already sorted groups by their first differing element, then by
     * size.
     *
     * @param left  one group
     * @param right the other group
     * @return a negative value, zero, or a positive value as left precedes, equals,
     *         or follows right
     */
    static int compareStringLists(List<String> left, List<String> right) {
        int common = Math.min(left.size(), right.size());
        for (int i = 0; i < common; i++) {
            int order = left.get(i).compareTo(right.get(i));
            if (order != 0) {
                return order;
            }
        }
        return Integer.compare(left.size(), right.size());
    }

    /**
     * Checks the two halves of correctness that do not depend on group order: every
     * input string is
     * placed exactly once, and the groups are the maximal anagram classes.
     *
     * @param strs   the input strings
     * @param groups the grouping under test
     */
    static void assertPartitionsByAnagramClass(String[] strs, List<List<String>> groups) {
        List<String> placed = new ArrayList<>();
        for (List<String> group : groups) {
            placed.addAll(group);
        }
        List<String> expected = new ArrayList<>(Arrays.asList(strs));
        Collections.sort(placed);
        Collections.sort(expected);
        assertEquals(expected, placed);
        Set<String> formsSeen = new HashSet<>();
        for (List<String> group : groups) {
            assertTrue(!group.isEmpty(), "a returned group is empty");
            String form = sortedForm(group.get(0));
            for (String s : group) {
                assertEquals(form, sortedForm(s), "a group holds strings that are not anagrams");
            }
            assertTrue(formsSeen.add(form), "two groups share the same anagram class");
        }
    }

    /**
     * Produces the canonical form used only by the tests: the characters of s in
     * sorted order.
     *
     * @param s a string of lowercase English letters
     * @return the sorted characters of s as a string
     */
    static String sortedForm(String s) {
        char[] characters = s.toCharArray();
        Arrays.sort(characters);
        return new String(characters);
    }

    /**
     * Lists every string of length 0 to maxLength over the first alphabetSize
     * lowercase letters.
     *
     * @param maxLength    the largest length to generate
     * @param alphabetSize how many letters starting at 'a' may appear
     * @return all such strings, shortest first
     */
    static List<String> allStringsUpToLength(int maxLength, int alphabetSize) {
        List<String> universe = new ArrayList<>();
        universe.add("");
        int firstOfPreviousLength = 0;
        for (int length = 1; length <= maxLength; length++) {
            int countBefore = universe.size();
            for (int i = firstOfPreviousLength; i < countBefore; i++) {
                for (int letter = 0; letter < alphabetSize; letter++) {
                    universe.add(universe.get(i) + (char) ('a' + letter));
                }
            }
            firstOfPreviousLength = countBefore;
        }
        return universe;
    }

    /**
     * Builds one random string of the requested length over the first alphabetSize
     * letters.
     *
     * @param random       the source of randomness
     * @param length       the length of the string to build
     * @param alphabetSize how many letters starting at 'a' may appear
     * @return the generated string
     */
    static String randomString(Random random, int length, int alphabetSize) {
        StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            builder.append((char) ('a' + random.nextInt(alphabetSize)));
        }
        return builder.toString();
    }

    /**
     * Reorders the given characters in place so that a new permutation of the same
     * letters is
     * produced, which keeps every generated string in one anagram class.
     *
     * @param random  the source of randomness
     * @param letters the characters to reorder
     */
    static void shuffle(Random random, char[] letters) {
        for (int i = letters.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            char swap = letters[i];
            letters[i] = letters[j];
            letters[j] = swap;
        }
    }
}