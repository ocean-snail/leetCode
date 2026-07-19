package topInterview150;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * You are given an integer array nums. You are initially positioned at the
 * array's first index, and each element in the array represents your maximum
 * jump length at that position.
 * 
 * Return true if you can reach the last index, or false otherwise.
 * 
 * 
 * 
 * ? Example 1:
 * 
 * Input: nums = [2,3,1,1,4]
 * Output: true
 * Explanation: Jump 1 step from index 0 to 1, then 3 steps to the last index.
 * 
 * ? Example 2:
 * 
 * Input: nums = [3,2,1,0,4]
 * Output: false
 * Explanation: You will always arrive at index 3 no matter what. Its maximum
 * jump length is 0, which makes it impossible to reach the last index.
 * 
 * 
 * ! Constraints:
 * 
 * 1 <= nums.length <= 104
 * 0 <= nums[i] <= 105
 */

public class JumpGame {

    // Time: O(n), Space: O(1)
    public boolean canJump(int[] nums) {
        int last = nums.length - 1;
        int farthest = 0;

        for (int i = 0; i <= last; i++) {
            if (i > farthest) {
                return false;
            }
            farthest = Math.max(farthest, i + nums[i]);
            if (farthest >= last) {
                return true;
            }
        }
        return false;
    }

    @Test
    void example1_reachableWithMultiplePaths() {
        assertTrue(canJump(new int[] { 2, 3, 1, 1, 4 }));
    }

    @Test
    void example2_forcedIntoZeroTrap() {
        assertFalse(canJump(new int[] { 3, 2, 1, 0, 4 }));
    }

    @Test
    void singleElement_alreadyAtLastIndex() {
        assertTrue(canJump(new int[] { 0 }));
    }

    @Test
    void zeroAtStart_withMoreElementsAfter() {
        assertFalse(canJump(new int[] { 0, 1 }));
    }

    @Test
    void tightestReach_farthestAdvancesByExactlyOne() {
        assertTrue(canJump(new int[] { 1, 1, 1, 1 }));
    }

    @Test
    void zeroInMiddle_canBeJumpedOver() {
        assertTrue(canJump(new int[] { 2, 0, 1 }));
    }

    @Test
    void farthestLandsExactlyOnLastIndex() {
        assertTrue(canJump(new int[] { 1, 0 }));
    }

    @Test
    void trailingZero_isValidDestination() {
        assertTrue(canJump(new int[] { 2, 0, 0 }));
    }
}