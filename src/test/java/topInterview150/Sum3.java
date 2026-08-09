package topInterview150;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Given an integer array nums, return all the triplets [nums[i], nums[j],
 * nums[k]] such that i != j, i != k, and j != k, and nums[i] + nums[j] +
 * nums[k] == 0.
 * 
 * Notice that the solution set must not contain duplicate triplets.
 * 
 * 
 * ? Example 1:
 * 
 * Input: nums = [-1,0,1,2,-1,-4]
 * Output: [[-1,-1,2],[-1,0,1]]
 * Explanation:
 * nums[0] + nums[1] + nums[2] = (-1) + 0 + 1 = 0.
 * nums[1] + nums[2] + nums[4] = 0 + 1 + (-1) = 0.
 * nums[0] + nums[3] + nums[4] = (-1) + 2 + (-1) = 0.
 * The distinct triplets are [-1,0,1] and [-1,-1,2].
 * Notice that the order of the output and the order of the triplets does not
 * matter.
 * 
 * ? Example 2:
 * 
 * Input: nums = [0,1,1]
 * Output: []
 * Explanation: The only possible triplet does not sum up to 0.
 * 
 * ? Example 3:
 * 
 * Input: nums = [0,0,0]
 * Output: [[0,0,0]]
 * Explanation: The only possible triplet sums up to 0.
 * 
 * 
 * ! Constraints:
 * 
 * 3 <= nums.length <= 3000
 * -105 <= nums[i] <= 105
 */

public class Sum3 {

    // ------------------------------------------------------------------
    // Solution
    // ------------------------------------------------------------------

    /**
     * Returns all distinct zero-sum triplets; nums is sorted in place, so the
     * caller's element order is changed.
     * * Time: O(n^2) - Arrays.sort costs O(n log n), then each of the n outer
     * positions runs an inner scan that moves two indices toward each other and
     * therefore performs at most n steps, and n^2 grows faster than n log n.
     * * Space: O(log n) - no auxiliary array is allocated; the only extra memory is
     * the recursion stack of Arrays.sort, whose depth is O(log n). The returned
     * list is output, not auxiliary space.
     *
     * @param nums the integer array to search, length at least 3
     * @return one list per distinct triplet, the three values of each triplet in
     *         ascending order
     */
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> triplets = new ArrayList<>();
        for (int first = 0; first + 2 < nums.length; first++) {
            if (nums[first] > 0) {
                break;
            }
            if (first > 0 && nums[first] == nums[first - 1]) {
                continue;
            }
            int left = first + 1;
            int right = nums.length - 1;
            while (left < right) {
                int sum = nums[first] + nums[left] + nums[right];
                if (sum < 0) {
                    left++;
                } else if (sum > 0) {
                    right--;
                } else {
                    triplets.add(List.of(nums[first], nums[left], nums[right]));
                    left++;
                    right--;
                    while (left < right && nums[left] == nums[left - 1]) {
                        left++;
                    }
                    while (left < right && nums[right] == nums[right + 1]) {
                        right--;
                    }
                }
            }
        }
        return triplets;
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------

    @Test
    @DisplayName("Example 2 returns an empty list when the only triple does not sum to zero")
    void example2_returnsEmptyList() {
        List<List<Integer>> result = threeSum(new int[] { 0, 1, 1 });

        assertEquals(List.of(), result);
    }

    @Test
    @DisplayName("Example 3 returns the all-zero triplet exactly once")
    void example3_returnsSingleZeroTriplet() {
        List<List<Integer>> result = threeSum(new int[] { 0, 0, 0 });

        assertEquals(List.of(List.of(0, 0, 0)), result);
    }

    @Test
    @DisplayName("Four zeros still return the all-zero triplet exactly once")
    void fourZeros_returnsSingleZeroTriplet() {
        List<List<Integer>> result = threeSum(new int[] { 0, 0, 0, 0 });

        assertEquals(List.of(List.of(0, 0, 0)), result);
    }

    @Test
    @DisplayName("An array of only positive values returns an empty list")
    void allPositiveValues_returnsEmptyList() {
        List<List<Integer>> result = threeSum(new int[] { 1, 2, 3, 4, 5 });

        assertEquals(List.of(), result);
    }

    @Test
    @DisplayName("An array of only negative values returns an empty list")
    void allNegativeValues_returnsEmptyList() {
        List<List<Integer>> result = threeSum(new int[] { -1, -2, -3, -4, -5 });

        assertEquals(List.of(), result);
    }

    @Test
    @DisplayName("Three equal non-zero values return an empty list")
    void threeEqualNonZeroValues_returnsEmptyList() {
        List<List<Integer>> result = threeSum(new int[] { 2, 2, 2 });

        assertEquals(List.of(), result);
    }

    @Test
    @DisplayName("Repeated values on both sides produce the shared triplet only once")
    void repeatedValuesOnBothSides_returnsTripletOnce() {
        List<List<Integer>> result = threeSum(new int[] { -2, 0, 0, 2, 2 });

        assertEquals(List.of(List.of(-2, 0, 2)), result);
    }

    @Test
    @DisplayName("Values at the constraint limits do not overflow the int sum")
    void constraintLimitValues_returnCorrectTriplet() {
        List<List<Integer>> result = threeSum(new int[] { -100_000, -100_000, 0, 100_000, 100_000 });

        assertEquals(List.of(List.of(-100_000, 0, 100_000)), result);
    }

    @Test
    @DisplayName("The input array is left in ascending order because the solution sorts it in place")
    void solutionSortsInputInPlace_inputBecomesAscending() {
        int[] nums = { -1, 0, 1, 2, -1, -4 };

        threeSum(nums);

        assertArrayEquals(new int[] { -4, -1, -1, 0, 1, 2 }, nums);
    }
}