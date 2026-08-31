package topInterview150;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Given an unsorted array of integers nums, return the length of the longest
 * consecutive elements sequence.
 * 
 * You must write an algorithm that runs in O(n) time.
 * 
 * 
 * ? Example 1:
 * 
 * Input: nums = [100,4,200,1,3,2]
 * Output: 4
 * Explanation: The longest consecutive elements sequence is [1, 2, 3, 4].
 * Therefore its length is 4.
 * 
 * ? Example 2:
 * 
 * Input: nums = [0,3,7,2,5,8,4,6,0,1]
 * Output: 9
 * 
 * ? Example 3:
 * 
 * Input: nums = [1,0,1,2]
 * Output: 3
 * 
 * 
 * ! Constraints:
 * 
 * 0 <= nums.length <= 105
 * -109 <= nums[i] <= 109
 */
public class LongestConsecutiveSequence {

    // ------------------------------------------------------------------
    // Solution
    // ------------------------------------------------------------------

    /**
     * Returns the length of the longest run of consecutive integers present in
     * nums.
     *
     * * Time: O(n) - the set is built in n insertions, and the inner while loop
     * advances only from
     * values whose predecessor is absent, so every distinct value is visited by at
     * most one expansion; total set lookups are bounded by a constant times n.
     * * Space: O(n) - the HashSet holds up to n distinct boxed integers.
     *
     * @param nums unsorted array of integers, possibly empty and possibly
     *             containing duplicates
     * @return length of the longest consecutive run, or 0 when nums is empty
     */
    public int longestConsecutive(int[] nums) {
        Set<Integer> distinctValues = new HashSet<>();
        for (int value : nums) {
            distinctValues.add(value);
        }

        int longest = 0;
        for (int value : distinctValues) {
            if (distinctValues.contains(value - 1)) {
                continue;
            }

            int runLength = 1;
            int nextValue = value + 1;
            while (distinctValues.contains(nextValue)) {
                runLength++;
                nextValue++;
            }

            if (runLength > longest) {
                longest = runLength;
            }
        }
        return longest;
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------

    @Test
    @DisplayName("Example 1: scattered values containing the run 1,2,3,4 return length 4")
    void scatteredValuesWithFourLongRun_returnsFour() {
        assertEquals(4, longestConsecutive(new int[] { 100, 4, 200, 1, 3, 2 }));
    }

    @Test
    @DisplayName("Example 2: values 0 through 8 with a repeated 0 return length 9")
    void fullRangeWithOneDuplicate_returnsNine() {
        assertEquals(9, longestConsecutive(new int[] { 0, 3, 7, 2, 5, 8, 4, 6, 0, 1 }));
    }

    @Test
    @DisplayName("Example 3: duplicated 1 does not extend the run 0,1,2")
    void duplicateInsideRun_returnsThree() {
        assertEquals(3, longestConsecutive(new int[] { 1, 0, 1, 2 }));
    }

    @Test
    @DisplayName("An empty array has no run and returns 0")
    void emptyArray_returnsZero() {
        assertEquals(0, longestConsecutive(new int[] {}));
    }

    @Test
    @DisplayName("A single value forms a run of length 1")
    void singleValue_returnsOne() {
        assertEquals(1, longestConsecutive(new int[] { 7 }));
    }

    @Test
    @DisplayName("Values that are all equal collapse to a run of length 1")
    void allValuesEqual_returnsOne() {
        assertEquals(1, longestConsecutive(new int[] { 5, 5, 5, 5 }));
    }

    @Test
    @DisplayName("Values with gaps of two never join, so the answer stays 1")
    void everyValueIsolated_returnsOne() {
        assertEquals(1, longestConsecutive(new int[] { 1, 3, 5, 7, 9 }));
    }

    @Test
    @DisplayName("A run given in strictly decreasing order is still measured as length 5")
    void strictlyDecreasingRun_returnsFive() {
        assertEquals(5, longestConsecutive(new int[] { 5, 4, 3, 2, 1 }));
    }

    @Test
    @DisplayName("The longer of two separate runs is reported, even when it appears second")
    void laterRunIsLonger_returnsFour() {
        assertEquals(4, longestConsecutive(new int[] { 10, 11, 1, 2, 3, 4 }));
    }

    @Test
    @DisplayName("The longer of two separate runs is reported, even when it appears first")
    void earlierRunIsLonger_returnsFour() {
        assertEquals(4, longestConsecutive(new int[] { 1, 2, 3, 4, 10, 11 }));
    }

    @Test
    @DisplayName("A run crossing zero from negative to positive is measured as length 5")
    void runCrossingZero_returnsFive() {
        assertEquals(5, longestConsecutive(new int[] { -2, -1, 0, 1, 2 }));
    }

    @Test
    @DisplayName("Values at the declared constraint bounds are handled without wraparound")
    void constraintBoundaryValues_returnsTwo() {
        assertEquals(2, longestConsecutive(new int[] { -1_000_000_000, -999_999_999, 1_000_000_000 }));
    }
}