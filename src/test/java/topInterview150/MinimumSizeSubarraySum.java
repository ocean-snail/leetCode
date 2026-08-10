package topInterview150;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Given an array of positive integers nums and a positive integer target,
 * return the minimal length of a subarray whose sum is greater than or equal to
 * target. If there is no such subarray, return 0 instead.
 * 
 * 
 * ? Example 1:
 * 
 * Input: target = 7, nums = [2,3,1,2,4,3]
 * Output: 2
 * Explanation: The subarray [4,3] has the minimal length under the problem
 * constraint.
 * 
 * ? Example 2:
 * 
 * Input: target = 4, nums = [1,4,4]
 * Output: 1
 * 
 * ? Example 3:
 * 
 * Input: target = 11, nums = [1,1,1,1,1,1,1,1]
 * Output: 0
 * 
 * 
 * ! Constraints:
 * 
 * 1 <= target <= 109
 * 1 <= nums.length <= 105
 * 1 <= nums[i] <= 104
 * 
 * 
 * Follow up: If you have figured out the O(n) solution, try coding another
 * solution of which the time complexity is O(n log(n)).
 * 
 */
public class MinimumSizeSubarraySum {

    // ------------------------------------------------------------------
    // Solution
    // ------------------------------------------------------------------

    /**
     * * Time: O(n) - right advances exactly n times; left advances at most n times
     * in
     * total,
     * because it never moves backwards and never passes right.
     * * Space: O(1) - three scalar variables, no auxiliary array.
     *
     * @param target the value the subarray sum must reach or exceed
     * @param nums   the array of positive integers to scan
     * @return the length of the shortest qualifying subarray, or 0 if none exists
     */
    public int minSubArrayLen(int target, int[] nums) {
        long windowSum = 0;
        int left = 0;
        int best = Integer.MAX_VALUE;

        for (int right = 0; right < nums.length; right++) {
            windowSum += nums[right];

            while (windowSum >= target) {
                best = Math.min(best, right - left + 1);
                windowSum -= nums[left];
                left++;
            }
        }

        return best == Integer.MAX_VALUE ? 0 : best;
    }

    // ------------------------------------------------------------------
    // Reference implementation (oracle)
    // ------------------------------------------------------------------

    /**
     * Structurally independent cross-check: build the prefix-sum array, then binary
     * search
     * for the earliest end index that satisfies each start index. This is also the
     * O(n log n)
     * solution the problem's follow-up asks for.
     *
     * * Time: O(n log n) - one O(n) prefix pass, then n binary searches of O(log n)
     * each.
     * * Space: O(n) - one prefix-sum array of length n + 1.
     *
     * @param target the value the subarray sum must reach or exceed
     * @param nums   the array of positive integers to scan
     * @return the length of the shortest qualifying subarray, or 0 if none exists
     */
    public int minSubArrayLenByBinarySearch(int target, int[] nums) {
        int n = nums.length;
        long[] prefix = new long[n + 1];
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + nums[i];
        }

        int best = Integer.MAX_VALUE;
        for (int start = 0; start < n; start++) {
            long needed = prefix[start] + target;
            int end = lowerBound(prefix, needed);
            if (end <= n) {
                best = Math.min(best, end - start);
            }
        }

        return best == Integer.MAX_VALUE ? 0 : best;
    }

    /**
     * * Time: O(log n) - the candidate range halves on every iteration.
     * * Space: O(1) - three index variables.
     *
     * @param prefix a non-decreasing array, valid because every input element is
     *               positive
     * @param needed the threshold to reach
     * @return the smallest index i with prefix[i] >= needed, or prefix.length if
     *         there is none
     */
    private static int lowerBound(long[] prefix, long needed) {
        int low = 0;
        int high = prefix.length;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (prefix[mid] >= needed) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------

    @Test
    @DisplayName("Example 1: target 7 over [2,3,1,2,4,3] returns 2")
    void exampleOne_returnsTwo() {
        assertEquals(2, minSubArrayLen(7, new int[] { 2, 3, 1, 2, 4, 3 }));
    }

    @Test
    @DisplayName("Example 2: target 4 over [1,4,4] returns 1")
    void exampleTwo_returnsOne() {
        assertEquals(1, minSubArrayLen(4, new int[] { 1, 4, 4 }));
    }

    @Test
    @DisplayName("Example 3: total sum below target returns 0")
    void totalSumBelowTarget_returnsZero() {
        assertEquals(0, minSubArrayLen(11, new int[] { 1, 1, 1, 1, 1, 1, 1, 1 }));
    }

    @Test
    @DisplayName("Single element equal to target returns 1")
    void singleElementEqualToTarget_returnsOne() {
        assertEquals(1, minSubArrayLen(5, new int[] { 5 }));
    }

    @Test
    @DisplayName("Single element below target returns 0")
    void singleElementBelowTarget_returnsZero() {
        assertEquals(0, minSubArrayLen(6, new int[] { 5 }));
    }

    @Test
    @DisplayName("Only the whole array reaches target, so the answer is the array length")
    void wholeArrayRequired_returnsArrayLength() {
        assertEquals(4, minSubArrayLen(10, new int[] { 1, 2, 3, 4 }));
    }

    @Test
    @DisplayName("Total sum one below target returns 0")
    void totalSumOneBelowTarget_returnsZero() {
        assertEquals(0, minSubArrayLen(11, new int[] { 1, 2, 3, 4 }));
    }

    @Test
    @DisplayName("Target 1 is met by any single element, so the answer is 1")
    void targetOne_returnsOne() {
        assertEquals(1, minSubArrayLen(1, new int[] { 4, 3, 9, 2 }));
    }

    @Test
    @DisplayName("Shortest window sits at the front of the array")
    void shortestWindowAtFront_returnsTwo() {
        assertEquals(2, minSubArrayLen(9, new int[] { 5, 4, 1, 1, 1, 1, 1, 1, 1, 1 }));
    }

    @Test
    @DisplayName("Shortest window sits at the very end of the array")
    void shortestWindowAtEnd_returnsTwo() {
        assertEquals(2, minSubArrayLen(9, new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 4, 5 }));
    }

    @Test
    @DisplayName("Two windows tie at the shortest length, so that length is returned once")
    void tiedShortestWindows_returnsTwo() {
        assertEquals(2, minSubArrayLen(8, new int[] { 4, 4, 1, 1, 4, 4 }));
    }

    @Test
    @DisplayName("Uniform array requires ceil(target / value) elements")
    void uniformArray_returnsCeilingOfTargetOverValue() {
        // 7 / 3 = 2.33..., so three elements are needed.
        assertEquals(3, minSubArrayLen(7, new int[] { 3, 3, 3, 3 }));
    }

    @Test
    @DisplayName("Maximum possible running sum of 10^9 does not overflow")
    void maximumRunningSum_returnsArrayLength() {
        int[] nums = new int[100_000];
        java.util.Arrays.fill(nums, 10_000);
        assertEquals(100_000, minSubArrayLen(1_000_000_000, nums));
    }

    @Test
    @DisplayName("Follow-up solution on Example 1 returns 2")
    void binarySearchOnExampleOne_returnsTwo() {
        assertEquals(2, minSubArrayLenByBinarySearch(7, new int[] { 2, 3, 1, 2, 4, 3 }));
    }

    @Test
    @DisplayName("Follow-up solution on Example 2 returns 1")
    void binarySearchOnExampleTwo_returnsOne() {
        assertEquals(1, minSubArrayLenByBinarySearch(4, new int[] { 1, 4, 4 }));
    }

    @Test
    @DisplayName("Follow-up solution on Example 3 returns 0")
    void binarySearchOnTotalSumBelowTarget_returnsZero() {
        assertEquals(0, minSubArrayLenByBinarySearch(11, new int[] { 1, 1, 1, 1, 1, 1, 1, 1 }));
    }

    @Test
    @DisplayName("Follow-up solution returns the array length when only the whole array reaches target")
    void binarySearchOnWholeArrayRequired_returnsArrayLength() {
        assertEquals(4, minSubArrayLenByBinarySearch(10, new int[] { 1, 2, 3, 4 }));
    }

    @Test
    @DisplayName("Follow-up solution finds a window that ends at the last index")
    void binarySearchOnShortestWindowAtEnd_returnsTwo() {
        assertEquals(2, minSubArrayLenByBinarySearch(9, new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 4, 5 }));
    }
}