package topInterview150;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Given an integer array nums and an integer k, return true if there are two
 * distinct indices i and j in the array such that nums[i] == nums[j] and abs(i
 * - j) <= k.
 * 
 * 
 * ? Example 1:
 * 
 * Input: nums = [1,2,3,1], k = 3
 * Output: true
 * 
 * ? Example 2:
 * 
 * Input: nums = [1,0,1,1], k = 1
 * Output: true
 * 
 * ? Example 3:
 * 
 * Input: nums = [1,2,3,1,2,3], k = 2
 * Output: false
 * 
 * 
 * ! Constraints:
 * 
 * 1 <= nums.length <= 105
 * -109 <= nums[i] <= 109
 * 0 <= k <= 105
 */
public class ContainsDuplicateII {

    // ------------------------------------------------------------------
    // Solution
    // ------------------------------------------------------------------

    /**
     * Reports whether two different indices hold equal values and lie at most k
     * apart.
     *
     * * Time: O(n) - one pass over nums, and each index causes at most one HashSet
     * add and one
     * HashSet remove, both O(1) on average
     * * Space: O(min(n, k + 1)) - the HashSet never holds more than k + 1 values at
     * once, and it
     * never holds more values than the array length
     *
     * @param nums the input array of integers
     * @param k    the largest index distance that still counts as a match
     * @return true if some pair of distinct indices i and j satisfies nums[i] ==
     *         nums[j] and
     *         abs(i - j) <= k, false otherwise
     */
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Set<Integer> window = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            if (i > k) {
                window.remove(nums[i - k - 1]);
            }
            if (!window.add(nums[i])) {
                return true;
            }
        }
        return false;
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------

    @Test
    @DisplayName("Example 1: nums = [1,2,3,1] with k = 3 has equal values exactly 3 apart")
    void example1_pairAtDistanceThreeWithKThree_returnsTrue() {
        assertTrue(containsNearbyDuplicate(new int[] { 1, 2, 3, 1 }, 3));
    }

    @Test
    @DisplayName("Example 2: nums = [1,0,1,1] with k = 1 has equal values at indices 2 and 3")
    void example2_adjacentEqualValuesWithKOne_returnsTrue() {
        assertTrue(containsNearbyDuplicate(new int[] { 1, 0, 1, 1 }, 1));
    }

    @Test
    @DisplayName("Example 3: nums = [1,2,3,1,2,3] with k = 2 keeps every equal pair 3 apart")
    void example3_everyEqualPairFartherThanK_returnsFalse() {
        assertFalse(containsNearbyDuplicate(new int[] { 1, 2, 3, 1, 2, 3 }, 2));
    }

    @Test
    @DisplayName("A single element cannot form a pair of distinct indices")
    void singleElement_returnsFalse() {
        assertFalse(containsNearbyDuplicate(new int[] { 7 }, 100000));
    }

    @Test
    @DisplayName("k = 0 rejects even neighbouring equal values because abs(i - j) >= 1")
    void kZeroWithAllValuesEqual_returnsFalse() {
        assertFalse(containsNearbyDuplicate(new int[] { 1, 1, 1, 1 }, 0));
    }

    @Test
    @DisplayName("An equal pair at distance exactly k is inside the allowed range")
    void pairAtDistanceExactlyK_returnsTrue() {
        assertTrue(containsNearbyDuplicate(new int[] { 1, 2, 3, 4, 1 }, 4));
    }

    @Test
    @DisplayName("An equal pair at distance k + 1 is outside the allowed range")
    void pairAtDistanceKPlusOne_returnsFalse() {
        assertFalse(containsNearbyDuplicate(new int[] { 1, 2, 3, 4, 1 }, 3));
    }

    @Test
    @DisplayName("A far pair must not mask a near pair that appears later in the array")
    void farPairFollowedByNearPair_returnsTrue() {
        assertTrue(containsNearbyDuplicate(new int[] { 1, 2, 3, 1, 5, 5 }, 1));
    }

    @Test
    @DisplayName("An array of pairwise different values has no equal pair at any k")
    void allValuesDistinct_returnsFalse() {
        assertFalse(containsNearbyDuplicate(new int[] { 5, -3, 0, 9, -1 }, 100000));
    }

    @Test
    @DisplayName("k larger than the array length allows any equal pair in the array")
    void kGreaterThanArrayLength_returnsTrue() {
        assertTrue(containsNearbyDuplicate(new int[] { 4, 8, 15, 16, 23, 4 }, 100000));
    }

    @Test
    @DisplayName("Values at the constraint bounds -10^9 and 10^9 are matched by equality")
    void extremeValuesRepeatedWithinK_returnsTrue() {
        assertTrue(containsNearbyDuplicate(new int[] { -1000000000, 1000000000, -1000000000 }, 2));
    }

    @Test
    @DisplayName("Negative and positive values are never treated as equal by magnitude")
    void oppositeSignsOfEqualMagnitude_returnsFalse() {
        assertFalse(containsNearbyDuplicate(new int[] { -1000000000, 1000000000 }, 1));
    }

    @Test
    @DisplayName("Deleting the value at index i - k - 1 must not remove a value still inside the window")
    void repeatedValueSpanningTheEviction_returnsTrue() {
        assertTrue(containsNearbyDuplicate(new int[] { 1, 2, 1, 3, 1 }, 2));
    }
}