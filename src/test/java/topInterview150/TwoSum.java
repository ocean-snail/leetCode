package topInterview150;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * You are given an array of integers nums and an integer target, return indices
 * of the two numbers such that they add up to target.
 * 
 * You may assume that each input would have exactly one solution, and you may
 * not use the same element twice.
 * 
 * You can return the answer in any order.
 * 
 * 
 * ? Example 1:
 * 
 * Input: nums = [2,7,11,15], target = 9
 * Output: [0,1]
 * Explanation: Because nums[0] + nums[1] == 9, we return [0, 1].
 * 
 * ? Example 2:
 * 
 * Input: nums = [3,2,4], target = 6
 * Output: [1,2]
 * 
 * ? Example 3:
 * 
 * Input: nums = [3,3], target = 6
 * Output: [0,1]
 * 
 * 
 * ! Constraints:
 * 
 * 2 <= nums.length <= 104
 * -109 <= nums[i] <= 109
 * -109 <= target <= 109
 * Only one valid answer exists.
 * 
 * 
 * Follow-up: Can you come up with an algorithm that is less than O(n2) time
 * complexity?
 */
public class TwoSum {

    // ------------------------------------------------------------------
    // Solution
    // ------------------------------------------------------------------

    /**
     * Finds the two distinct positions whose values add up to target.
     *
     * * Time: O(1) best - the answer is nums[0] + nums[1], so the loop stops at i =
     * 1
     * * Time: O(n) average - one pass over n elements, each doing one expected O(1)
     * HashMap get and at most one expected O(1) HashMap put
     * Time: O(n log n) worst - if every key hashes into a single bin, HashMap
     * converts
     * that bin into a red-black tree, so each get and put costs O(log n)
     * * Space: O(n) worst - the HashMap stores at most one entry per visited
     * element
     * * Space: O(1) best - the answer is found at i = 1, so the map holds one entry
     *
     * @param nums   array of integers containing exactly one valid pair
     * @param target the required sum of the two selected elements
     * @return the two indices in ascending order
     */
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> valueToIndex = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            Integer partnerIndex = valueToIndex.get(complement);
            if (partnerIndex != null) {
                return new int[] { partnerIndex, i };
            }
            valueToIndex.put(nums[i], i);
        }
        return new int[] {};
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------

    @Test
    @DisplayName("LeetCode example 1: nums = [2,7,11,15] and target = 9 give indices [0,1]")
    void leadingPairSumsToTarget_returnsIndicesZeroAndOne() {
        assertArrayEquals(new int[] { 0, 1 }, twoSum(new int[] { 2, 7, 11, 15 }, 9));
    }

    @Test
    @DisplayName("LeetCode example 2: nums = [3,2,4] and target = 6 give indices [1,2]")
    void answerSkipsTheFirstElement_returnsIndicesOneAndTwo() {
        assertArrayEquals(new int[] { 1, 2 }, twoSum(new int[] { 3, 2, 4 }, 6));
    }

    @Test
    @DisplayName("LeetCode example 3: nums = [3,3] and target = 6 give indices [0,1]")
    void twoEqualValuesFormTheAnswer_returnsIndicesZeroAndOne() {
        assertArrayEquals(new int[] { 0, 1 }, twoSum(new int[] { 3, 3 }, 6));
    }

    @Test
    @DisplayName("An element equal to half the target must not be paired with itself")
    void singleElementEqualToHalfTheTarget_pairsWithADifferentIndex() {
        assertArrayEquals(new int[] { 1, 2 }, twoSum(new int[] { 3, 4, 2 }, 6));
    }

    @Test
    @DisplayName("A repeated value outside the answer still yields the only valid pair")
    void repeatedValueOutsideTheAnswer_returnsTheOnlyValidPair() {
        assertArrayEquals(new int[] { 2, 3 }, twoSum(new int[] { 8, 8, 1, 6 }, 7));
    }

    @Test
    @DisplayName("Negative values summing to a negative target return the matching indices")
    void negativeValues_returnsIndicesOfTheOnlyValidPair() {
        assertArrayEquals(new int[] { 1, 3 }, twoSum(new int[] { 5, -4, 9, -7 }, -11));
    }

    @Test
    @DisplayName("An answer at the last two positions is still reported")
    void answerAtTheLastTwoPositions_returnsThoseIndices() {
        assertArrayEquals(new int[] { 3, 4 }, twoSum(new int[] { 1, 2, 4, 8, 16 }, 24));
    }

    @Test
    @DisplayName("Values at the constraint boundary produce no int overflow")
    void boundaryValues_returnsIndicesWithoutIntegerOverflow() {
        assertArrayEquals(new int[] { 0, 1 }, twoSum(new int[] { 1000000000, 1000000000, 7 }, 2000000000));
        assertArrayEquals(new int[] { 0, 2 }, twoSum(new int[] { -1000000000, 7, -1000000000 }, -2000000000));
    }
}