package topInterview150;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * You are given an integer array height of length n. There are n vertical lines
 * drawn such that the two endpoints of the ith line are (i, 0) and (i,
 * height[i]).
 * 
 * Find two lines that together with the x-axis form a container, such that the
 * container contains the most water.
 * 
 * Return the maximum amount of water a container can store.
 * 
 * Notice that you may not slant the container.
 * 
 * 
 * ? Example 1:
 * 
 * 
 * Input: height = [1,8,6,2,5,4,8,3,7]
 * Output: 49
 * Explanation: The above vertical lines are represented by array
 * [1,8,6,2,5,4,8,3,7]. In this case, the max area of water (blue section) the
 * container can contain is 49.
 * 
 * ? Example 2:
 * 
 * Input: height = [1,1]
 * Output: 1
 * 
 * 
 * ! Constraints:
 * 
 * n == height.length
 * 2 <= n <= 105
 * 0 <= height[i] <= 104
 */
public class ContainerWithMostWater {

    // ------------------------------------------------------------------
    // Solution
    // ------------------------------------------------------------------

    /**
     * Returns the largest area formed by two lines and the x-axis.
     * * Time: O(n) - the two indices start n - 1 apart, exactly one of them changes
     * by 1 per
     * iteration, and the loop stops when they are equal, so the body runs
     * exactly n - 1 times; best, average and worst cases are identical because
     * no statement exits the loop early.
     * * Space: O(1) - three int variables are allocated, whatever the length of
     * height.
     *
     * @param height heights of the vertical lines; line i spans (i, 0) to (i,
     *               height[i])
     * @return the maximum amount of water a container can store
     */
    public int maxArea(int[] height) {
        int left = 0;
        int right = height.length - 1;
        int best = 0;
        while (left < right) {
            int shorter = Math.min(height[left], height[right]);
            int area = (right - left) * shorter;
            if (area > best) {
                best = area;
            }
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }
        return best;
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------

    @Test
    @DisplayName("The problem statement example returns 49")
    void statementExample_returns49() {
        assertEquals(49, maxArea(new int[] { 1, 8, 6, 2, 5, 4, 8, 3, 7 }));
    }

    @Test
    @DisplayName("Two lines of equal height at the minimum length return 1")
    void twoLinesOfEqualHeight_returnsOne() {
        assertEquals(1, maxArea(new int[] { 1, 1 }));
    }

    @Test
    @DisplayName("The shorter of the two walls sets the depth, not the taller one")
    void adjacentTallLines_useShorterWallAsDepth() {
        assertEquals(17, maxArea(new int[] { 2, 3, 4, 5, 18, 17, 6 }));
    }

    @Test
    @DisplayName("All heights zero returns 0")
    void allHeightsZero_returnsZero() {
        assertEquals(0, maxArea(new int[] { 0, 0, 0, 0 }));
    }

    @Test
    @DisplayName("A zero-height line forces the pair area to 0")
    void oneLineOfHeightZero_returnsZero() {
        assertEquals(0, maxArea(new int[] { 0, 5 }));
    }

    @Test
    @DisplayName("Strictly increasing heights return 6")
    void strictlyIncreasingHeights_returnsSix() {
        assertEquals(6, maxArea(new int[] { 1, 2, 3, 4, 5 }));
    }

    @Test
    @DisplayName("Strictly decreasing heights return the mirrored value 6")
    void strictlyDecreasingHeights_returnsSix() {
        assertEquals(6, maxArea(new int[] { 5, 4, 3, 2, 1 }));
    }

    @Test
    @DisplayName("Tall lines at both ends beat every inner pair")
    void tallLinesAtBothEnds_returnsWidthTimesShorterWall() {
        assertEquals(40000, maxArea(new int[] { 10000, 1, 1, 1, 10000 }));
    }

    @Test
    @DisplayName("The largest legal input stays inside the int range")
    void widestAndTallestInput_staysWithinIntRange() {
        int[] height = new int[100_000];
        Arrays.fill(height, 10_000);
        assertEquals(999_990_000, maxArea(height));
    }
}