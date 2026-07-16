package topInterview150;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 * Given an integer array nums, rotate the array to the right by k steps, where
 * k is non-negative.
 * 
 * ? Example 1:
 * 
 * Input: nums = [1,2,3,4,5,6,7], k = 3
 * Output: [5,6,7,1,2,3,4]
 * Explanation:
 * rotate 1 steps to the right: [7,1,2,3,4,5,6]
 * rotate 2 steps to the right: [6,7,1,2,3,4,5]
 * rotate 3 steps to the right: [5,6,7,1,2,3,4]
 * 
 * ? Example 2:
 * 
 * Input: nums = [-1,-100,3,99], k = 2
 * Output: [3,99,-1,-100]
 * Explanation:
 * rotate 1 steps to the right: [99,-1,-100,3]
 * rotate 2 steps to the right: [3,99,-1,-100]
 * 
 * 
 * ! Constraints:
 * 
 * 1 <= nums.length <= 105
 * -231 <= nums[i] <= 231 - 1
 * 0 <= k <= 105
 * 
 * 
 * Follow up:
 * 
 * Try to come up with as many solutions as you can. There are at least three
 * different ways to solve this problem.
 * Could you do it in-place with O(1) extra space?
 */
public class RotateArray {

    // ---------- Solution ----------
    // Time: O(n), Space: O(1)
    void rotate(int[] nums, int k) {
        int n = nums.length;
        k %= n; // normalize: k may exceed n
        if (k == 0) {
            return; // no-op; also skips useless reversals
        }
        reverse(nums, 0, n - 1); // 1) reverse everything
        reverse(nums, 0, k - 1); // 2) fix the first k elements
        reverse(nums, k, n - 1); // 3) fix the remaining n - k elements
    }

    private void reverse(int[] nums, int left, int right) {
        while (left < right) {
            int tmp = nums[left];
            nums[left] = nums[right];
            nums[right] = tmp;
            left++;
            right--;
        }
    }

    // ---------- Tests ----------

    @Test
    void rotatesExampleOneByThree() {
        int[] nums = { 1, 2, 3, 4, 5, 6, 7 };
        rotate(nums, 3);
        assertArrayEquals(new int[] { 5, 6, 7, 1, 2, 3, 4 }, nums);
    }

    @Test
    void rotatesExampleTwoByTwo() {
        int[] nums = { -1, -100, 3, 99 };
        rotate(nums, 2);
        assertArrayEquals(new int[] { 3, 99, -1, -100 }, nums);
    }

    @Test
    void zeroStepsLeavesArrayUnchanged() {
        int[] nums = { 1, 2, 3 };
        rotate(nums, 0);
        assertArrayEquals(new int[] { 1, 2, 3 }, nums);
    }

    @Test
    void kEqualToLengthIsFullRotation() {
        int[] nums = { 1, 2, 3, 4 };
        rotate(nums, 4);
        assertArrayEquals(new int[] { 1, 2, 3, 4 }, nums);
    }

    @Test
    void kGreaterThanLengthWrapsAround() {
        int[] nums = { 1, 2, 3, 4, 5, 6, 7 };
        rotate(nums, 10); // 10 % 7 == 3, same as example one
        assertArrayEquals(new int[] { 5, 6, 7, 1, 2, 3, 4 }, nums);
    }

    @Test
    void singleElementIsAlwaysUnchanged() {
        int[] nums = { 42 };
        rotate(nums, 99);
        assertArrayEquals(new int[] { 42 }, nums);
    }

    @Test
    void twoElementsRotateByOne() {
        int[] nums = { 1, 2 };
        rotate(nums, 1);
        assertArrayEquals(new int[] { 2, 1 }, nums);
    }
}