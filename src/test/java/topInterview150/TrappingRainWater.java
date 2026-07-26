package topInterview150;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Given n non-negative integers representing an elevation map where the width
 * of each bar is 1, compute how much water it can trap after raining.
 * 
 * 
 * ? Example 1:
 * 
 * 
 * Input: height = [0,1,0,2,1,0,1,3,2,1,2,1]
 * Output: 6
 * Explanation: The above elevation map (black section) is represented by array
 * [0,1,0,2,1,0,1,3,2,1,2,1]. In this case, 6 units of rain water (blue section)
 * are being trapped.
 * 
 * ? Example 2:
 * 
 * Input: height = [4,2,0,3,2,5]
 * Output: 9
 * 
 * 
 * ! Constraints:
 * 
 * n == height.length
 * 1 <= n <= 2 * 104
 * 0 <= height[i] <= 105
 * 
 */

public class TrappingRainWater {

    // ------------------------------------------------------------------
    // Solution
    // ------------------------------------------------------------------

    /**
     * Sums min(leftWall, rightWall) - height[i] over every index i, deciding each
     * column with the wall that is already known (proof in section 2.2).
     *
     * Time: O(n) - single traversal, each index visited once; best, average and
     * worst
     * are identical because no early exit exists.
     * Space: O(1) - five int accumulators, no auxiliary structures.
     *
     * @param height bar heights, each bar of width 1; non-empty per the constraints
     * @return total units of water trapped between the bars
     */
    public int trap(int[] height) {
        int left = 0;
        int right = height.length - 1;
        int leftMax = 0;
        int rightMax = 0;
        int total = 0;

        while (left < right) {
            if (height[left] < height[right]) {
                leftMax = Math.max(leftMax, height[left]);
                total += leftMax - height[left];
                left++;
            } else {
                rightMax = Math.max(rightMax, height[right]);
                total += rightMax - height[right];
                right--;
            }
        }
        return total;
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------

    @Test
    @DisplayName("Example 1: mixed terrain with several separate pools")
    void example1_sixUnitsAcrossThreePools() {
        assertEquals(6, trap(new int[] { 0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1 }));
    }

    @Test
    @DisplayName("Example 2: one deep basin whose right wall is the taller one")
    void example2_nineUnitsInOneBasin() {
        assertEquals(9, trap(new int[] { 4, 2, 0, 3, 2, 5 }));
    }

    @Test
    @DisplayName("Single bar: the loop body never executes")
    void singleBar_returnsZero() {
        assertEquals(0, trap(new int[] { 5 }));
    }

    @Test
    @DisplayName("Two bars: no interior cell exists, so no water")
    void twoBars_returnsZero() {
        assertEquals(0, trap(new int[] { 3, 1 }));
    }

    @Test
    @DisplayName("Strictly increasing: every bar is its own left wall")
    void strictlyIncreasing_returnsZero() {
        assertEquals(0, trap(new int[] { 1, 2, 3, 4, 5 }));
    }

    @Test
    @DisplayName("Strictly decreasing: mirror of the increasing case")
    void strictlyDecreasing_returnsZero() {
        assertEquals(0, trap(new int[] { 5, 4, 3, 2, 1 }));
    }

    @Test
    @DisplayName("All equal heights: min(wall, wall) - height is exactly zero everywhere")
    void allEqualHeights_returnsZero() {
        assertEquals(0, trap(new int[] { 3, 3, 3, 3 }));
    }

    @Test
    @DisplayName("All zeros: no bars, no water, no negative accumulation")
    void allZeros_returnsZero() {
        assertEquals(0, trap(new int[] { 0, 0, 0 }));
    }

    @Test
    @DisplayName("Flat basin: three interior cells filled to height three")
    void flatBasin_returnsNine() {
        assertEquals(9, trap(new int[] { 3, 0, 0, 0, 3 }));
    }

    @Test
    @DisplayName("Tall left wall with a shorter right side: the right wall must cap the water")
    void tallerLeftWall_isNotTheLimit() {
        assertEquals(1, trap(new int[] { 5, 1, 2 }));
    }

    @Test
    @DisplayName("Two pools sharing a wall: the shared bar must not be counted twice")
    void equalWallsWithValleyBetween_returnsTwo() {
        assertEquals(2, trap(new int[] { 2, 1, 2, 1, 2 }));
    }

    @Test
    @DisplayName("Maximum permitted height values")
    void maximumHeightValues_returnsHundredThousand() {
        assertEquals(100_000, trap(new int[] { 100_000, 0, 100_000 }));
    }

    @Test
    @DisplayName("Worst-case total still fits in int: 1,999,800,000 < 2,147,483,647")
    void largestPossibleTotal_doesNotOverflow() {
        int n = 20_000;
        int[] height = new int[n];
        height[0] = 100_000;
        height[n - 1] = 100_000;
        assertEquals(1_999_800_000, trap(height));
    }
}