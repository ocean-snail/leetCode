package topInterview150;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Write a function to find the longest common prefix string amongst an array of
 * strings.
 * 
 * If there is no common prefix, return an empty string "".
 * 
 * 
 * ? Example 1:
 * 
 * Input: strs = ["flower","flow","flight"]
 * Output: "fl"
 * 
 * ? Example 2:
 * 
 * Input: strs = ["dog","racecar","car"]
 * Output: ""
 * Explanation: There is no common prefix among the input strings.
 * 
 * 
 * ! Constraints:
 * 
 * 1 <= strs.length <= 200
 * 0 <= strs[i].length <= 200
 * strs[i] consists of only lowercase English letters if it is non-empty.
 */
public class LongestCommonPrefix {

    // ------------------------------------------------------------------
    // Solution
    // ------------------------------------------------------------------

    /**
     * Returns the longest common prefix of every string in the array.
     * * Time: O(n * p) - the outer loop runs p + 1 times at most (p = answer
     * length),
     * and each iteration
     * compares one character in each of the other n - 1 strings; worst case p = m
     * (shortest string),
     * giving O(n * m), which is bounded by the total input size S.
     * * Space: O(p) - only the returned substring is allocated; String.substring
     * copies its characters into
     * a new backing array (Java 7+), and no other data structure grows with the
     * input.
     *
     * @param strs the array of strings to inspect; guaranteed non-empty by the
     *             constraints,
     *             though individual elements may be the empty string
     * @return the longest common prefix, or "" when the strings share no leading
     *         character
     */
    public String longestCommonPrefix(String[] strs) {
        String first = strs[0];

        for (int i = 0; i < first.length(); i++) {
            char expected = first.charAt(i);

            for (int j = 1; j < strs.length; j++) {
                String other = strs[j];

                if (i >= other.length() || other.charAt(i) != expected) {
                    return first.substring(0, i);
                }
            }
        }

        return first;
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------
    @Test
    @DisplayName("Example 1: [flower, flow, flight] shares the first two columns -> \"fl\"")
    void exampleOne_returnsFl() {
        assertEquals("fl", longestCommonPrefix(new String[] { "flower", "flow", "flight" }));
    }

    @Test
    @DisplayName("Example 2: [dog, racecar, car] disagrees at column 0 -> \"\"")
    void exampleTwo_returnsEmptyString() {
        assertEquals("", longestCommonPrefix(new String[] { "dog", "racecar", "car" }));
    }

    @Test
    @DisplayName("Single string: the inner loop never runs, so the string is its own prefix")
    void singleString_returnsThatString() {
        assertEquals("solo", longestCommonPrefix(new String[] { "solo" }));
    }

    @Test
    @DisplayName("All strings identical: no column ever fails, so the whole string is returned")
    void allStringsIdentical_returnsWholeString() {
        assertEquals("abc", longestCommonPrefix(new String[] { "abc", "abc", "abc" }));
    }

    @Test
    @DisplayName("First string empty: the outer loop body never executes -> \"\"")
    void firstStringEmpty_returnsEmptyString() {
        assertEquals("", longestCommonPrefix(new String[] { "", "abc", "ab" }));
    }

    @Test
    @DisplayName("Later string empty: the i >= other.length() branch fires at i = 0 -> \"\"")
    void laterStringEmpty_returnsEmptyString() {
        assertEquals("", longestCommonPrefix(new String[] { "abc", "", "ab" }));
    }

    @Test
    @DisplayName("Shortest string is itself the prefix: scan ends by exhausting a shorter string")
    void shortestStringIsThePrefix_returnsShortestString() {
        assertEquals("ab", longestCommonPrefix(new String[] { "abcd", "ab", "abc" }));
    }

    @Test
    @DisplayName("Yardstick is not the shortest string: strs[0] longer than the answer")
    void firstStringLongerThanAnswer_returnsCommonPart() {
        assertEquals("inter", longestCommonPrefix(new String[] { "interstellar", "interval", "interior" }));
    }

    @Test
    @DisplayName("Single character strings that agree -> that character")
    void singleCharacterStringsAgree_returnsThatCharacter() {
        assertEquals("a", longestCommonPrefix(new String[] { "a", "a", "a" }));
    }

    @Test
    @DisplayName("Single character strings that differ -> \"\"")
    void singleCharacterStringsDiffer_returnsEmptyString() {
        assertEquals("", longestCommonPrefix(new String[] { "a", "b" }));
    }

    @Test
    @DisplayName("Maximum constraint size: 200 strings of 200 identical characters")
    void maximumSizeInput_returnsFullCommonPrefix() {
        String longString = "a".repeat(200);
        String[] strs = new String[200];
        Arrays.fill(strs, longString);

        assertEquals(longString, longestCommonPrefix(strs));
    }
}
