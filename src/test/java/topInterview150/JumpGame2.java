package topInterview150;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * You are given a 0-indexed array of integers nums of length n. You are
 * initially positioned at index 0.
 * 
 * Each element nums[i] represents the maximum length of a forward jump from
 * index i. In other words, if you are at index i, you can jump to any index (i
 * + j) where:
 * 
 * 0 <= j <= nums[i] and
 * i + j < n
 * Return the minimum number of jumps to reach index n - 1. The test cases are
 * generated such that you can reach index n - 1.
 * 
 * 
 * ? Example 1:
 * 
 * Input: nums = [2,3,1,1,4]
 * Output: 2
 * Explanation: The minimum number of jumps to reach the last index is 2. Jump 1
 * step from index 0 to 1, then 3 steps to the last index.
 * 
 * ? Example 2:
 * 
 * Input: nums = [2,3,0,1,4]
 * Output: 2
 * 
 * 
 * ! Constraints:
 * 
 * 1 <= nums.length <= 104
 * 0 <= nums[i] <= 1000
 * It's guaranteed that you can reach nums[n - 1].
 */

public class JumpGame2 {

    // * Time: O(n), Space: O(1)
    int jump(int[] nums) {
        int jumps = 0;
        int currentEnd = 0;
        int farthest = 0;

        for (int i = 0; i < nums.length - 1; i++) {
            farthest = Math.max(farthest, i + nums[i]);
            if (i == currentEnd) {
                jumps++;
                currentEnd = farthest;
            }
        }
        return jumps;
    }

    @Test
    void example1_twoJumpsViaIndex1() {
        assertEquals(2, this.jump(new int[] { 2, 3, 1, 1, 4 }));
    }

    @Test
    void example2_zeroMustBeJumpedOver() {
        assertEquals(2, this.jump(new int[] { 2, 3, 0, 1, 4 }));
    }

    @Test
    void singleElement_alreadyAtTarget_zeroJumps() {
        assertEquals(0, this.jump(new int[] { 1 }));
    }

    @Test
    void twoElements_exactlyOneJump() {
        assertEquals(1, this.jump(new int[] { 1, 1 }));
    }

    @Test
    void oneGiantJump_firstElementClearsArray() {
        assertEquals(1, this.jump(new int[] { 5, 9, 9, 9, 9, 9 }));
    }

    @Test
    void forcedStepwise_everyElementIsOne() {
        assertEquals(3, this.jump(new int[] { 1, 1, 1, 1 }));
    }

    @Test
    void landingExactlyOnBoundary() {
        // From 0 you can only reach 1; from 1 you reach exactly index 3.
        assertEquals(2, this.jump(new int[] { 1, 2, 1, 1 }));
    }
}