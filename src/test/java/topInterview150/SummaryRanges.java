package topInterview150;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * You are given a sorted unique integer array nums.
 * 
 * A range [a,b] is the set of all integers from a to b (inclusive).
 * 
 * Return the smallest sorted list of ranges that cover all the numbers in the
 * array exactly. That is, each element of nums is covered by exactly one of the
 * ranges, and there is no integer x such that x is in one of the ranges but not
 * in nums.
 * 
 * Each range [a,b] in the list should be output as:
 * 
 * "a->b" if a != b
 * "a" if a == b
 * 
 * 
 * ? Example 1:
 * 
 * Input: nums = [0,1,2,4,5,7]
 * Output: ["0->2","4->5","7"]
 * Explanation: The ranges are:
 * [0,2] --> "0->2"
 * [4,5] --> "4->5"
 * [7,7] --> "7"
 * 
 * ? Example 2:
 * 
 * Input: nums = [0,2,3,4,6,8,9]
 * Output: ["0","2->4","6","8->9"]
 * Explanation: The ranges are:
 * [0,0] --> "0"
 * [2,4] --> "2->4"
 * [6,6] --> "6"
 * [8,9] --> "8->9"
 * 
 * 
 * ! Constraints:
 * 
 * 0 <= nums.length <= 20
 * -231 <= nums[i] <= 231 - 1
 * All the values of nums are unique.
 * nums is sorted in ascending order.
 * 
 */
public class SummaryRanges {

    // ------------------------------------------------------------------
    // Solution
    // ------------------------------------------------------------------

    /**
     * Groups the sorted unique values into maximal runs of consecutive integers.
     *
     * * Time: O(n) - the index i never decreases and the body of each while
     * iteration
     * is constant
     * work, so every element is read a bounded number of times.
     * * Space: O(1) - two int indices and one StringBuilder holding at most 24
     * characters; the
     * returned list is the required output, not auxiliary storage.
     *
     * @param nums values sorted in ascending order with no duplicates
     * @return one entry per maximal run, formatted "a" when the run has length 1
     *         and "a-&gt;b"
     *         otherwise, in ascending order
     */
    public List<String> summaryRanges(int[] nums) {
        List<String> ranges = new ArrayList<>();
        int i = 0;
        while (i < nums.length) {
            int start = i;
            while (i + 1 < nums.length && nums[i] + 1 == nums[i + 1]) {
                i++;
            }
            StringBuilder entry = new StringBuilder();
            entry.append(nums[start]);
            if (start != i) {
                entry.append("->").append(nums[i]);
            }
            ranges.add(entry.toString());
            i++;
        }
        return ranges;
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------

    @Test
    @DisplayName("The first LeetCode example splits into two ranges and one lone value")
    void firstProvidedExample_returnsThreeEntries() {
        assertEquals(
                List.of("0->2", "4->5", "7"),
                summaryRanges(new int[] { 0, 1, 2, 4, 5, 7 }));
    }

    @Test
    @DisplayName("The second LeetCode example alternates lone values and ranges")
    void secondProvidedExample_returnsFourEntries() {
        assertEquals(
                List.of("0", "2->4", "6", "8->9"),
                summaryRanges(new int[] { 0, 2, 3, 4, 6, 8, 9 }));
    }

    @Test
    @DisplayName("An empty array produces an empty list because the outer loop never runs")
    void emptyArray_returnsEmptyList() {
        assertEquals(List.of(), summaryRanges(new int[] {}));
    }

    @Test
    @DisplayName("A single value is printed without an arrow")
    void singleValue_returnsBareNumber() {
        assertEquals(List.of("5"), summaryRanges(new int[] { 5 }));
    }

    @Test
    @DisplayName("A fully consecutive array collapses into one range")
    void fullyConsecutiveArray_returnsOneRange() {
        assertEquals(List.of("1->5"), summaryRanges(new int[] { 1, 2, 3, 4, 5 }));
    }

    @Test
    @DisplayName("Values with gaps everywhere produce one bare number per element")
    void noConsecutivePair_returnsOneEntryPerElement() {
        assertEquals(List.of("1", "3", "5", "7"), summaryRanges(new int[] { 1, 3, 5, 7 }));
    }

    @Test
    @DisplayName("A run crossing zero keeps the negative sign in the left endpoint")
    void runCrossingZero_returnsSignedRange() {
        assertEquals(List.of("-3->1"), summaryRanges(new int[] { -3, -2, -1, 0, 1 }));
    }

    @Test
    @DisplayName("The two extreme values are reported separately despite their difference wrapping")
    void bothIntBounds_returnsTwoBareNumbers() {
        assertEquals(
                List.of("-2147483648", "2147483647"),
                summaryRanges(new int[] { Integer.MIN_VALUE, Integer.MAX_VALUE }));
    }

    @Test
    @DisplayName("A run starting at Integer.MIN_VALUE is joined into one range")
    void runAtLowerBound_returnsSingleRange() {
        assertEquals(
                List.of("-2147483648->-2147483646"),
                summaryRanges(new int[] {
                        Integer.MIN_VALUE, Integer.MIN_VALUE + 1, Integer.MIN_VALUE + 2 }));
    }

    @Test
    @DisplayName("A run ending at Integer.MAX_VALUE is joined into one range")
    void runAtUpperBound_returnsSingleRange() {
        assertEquals(
                List.of("2147483645->2147483647"),
                summaryRanges(new int[] {
                        Integer.MAX_VALUE - 2, Integer.MAX_VALUE - 1, Integer.MAX_VALUE }));
    }

    @Test
    @DisplayName("An input of the maximum allowed length is handled as one range")
    void constraintMaximumLength_returnsSingleRange() {
        int[] nums = new int[20];
        for (int i = 0; i < nums.length; i++) {
            nums[i] = i;
        }
        assertEquals(List.of("0->19"), summaryRanges(nums));
    }
}