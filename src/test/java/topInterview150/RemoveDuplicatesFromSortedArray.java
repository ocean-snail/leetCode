package topInterview150;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

/**
 * 
 * Given an integer array nums sorted in non-decreasing order, remove the
 * duplicates in-place such that each unique element appears only once. The
 * relative order of the elements should be kept the same.
 * 
 * Consider the number of unique elements in nums to be k​​​​​​​​​​​​​​. After
 * removing duplicates, return the number of unique elements k.
 * 
 * The first k elements of nums should contain the unique numbers in sorted
 * order. The remaining elements beyond index k - 1 can be ignored.
 * 
 * Custom Judge:
 * 
 * The judge will test your solution with the following code:
 * 
 * int[] nums = [...]; // Input array
 * int[] expectedNums = [...]; // The expected answer with correct length
 * 
 * int k = removeDuplicates(nums); // Calls your implementation
 * 
 * assert k == expectedNums.length;
 * for (int i = 0; i < k; i++) {
 * assert nums[i] == expectedNums[i];
 * }
 * If all assertions pass, then your solution will be accepted.
 * 
 * 
 * 
 * ? Example 1:
 * 
 * Input: nums = [1,1,2]
 * Output: 2, nums = [1,2,_]
 * Explanation: Your function should return k = 2, with the first two elements
 * of nums being 1 and 2 respectively.
 * It does not matter what you leave beyond the returned k (hence they are
 * underscores).
 * 
 * ? Example 2:
 * 
 * Input: nums = [0,0,1,1,1,2,2,3,3,4]
 * Output: 5, nums = [0,1,2,3,4,_,_,_,_,_]
 * Explanation: Your function should return k = 5, with the first five elements
 * of nums being 0, 1, 2, 3, and 4 respectively.
 * It does not matter what you leave beyond the returned k (hence they are
 * underscores).
 * 
 * 
 * ! Constraints:
 * 
 * 1 <= nums.length <= 3 * 104
 * -100 <= nums[i] <= 100
 * nums is sorted in non-decreasing order.
 * 
 * 
 */

public class RemoveDuplicatesFromSortedArray {

    // ---------- Solution ----------
    int removeDuplicates(int[] nums) {
        int write = 1; // nums[0] is always a unique element (length >= 1 guaranteed)
        for (int read = 1; read < nums.length; read++) {
            if (nums[read] != nums[write - 1]) {
                nums[write] = nums[read];
                write++;
            }
        }
        return write;
    }

    // ---------- Tests ----------

    @Test
    void example1_smallArrayWithOneDuplicate() {
        int[] nums = { 1, 1, 2 };
        int k = removeDuplicates(nums);
        assertEquals(2, k);
        assertArrayEquals(new int[] { 1, 2 }, Arrays.copyOfRange(nums, 0, k));
    }

    @Test
    void example2_multipleDuplicateRuns() {
        int[] nums = { 0, 0, 1, 1, 1, 2, 2, 3, 3, 4 };
        int k = removeDuplicates(nums);
        assertEquals(5, k);
        assertArrayEquals(new int[] { 0, 1, 2, 3, 4 }, Arrays.copyOfRange(nums, 0, k));
    }

    @Test
    void singleElement_returnsOne() {
        int[] nums = { 7 };
        int k = removeDuplicates(nums);
        assertEquals(1, k);
        assertArrayEquals(new int[] { 7 }, Arrays.copyOfRange(nums, 0, k));
    }

    @Test
    void allDuplicates_collapseToOne() {
        int[] nums = { 5, 5, 5, 5, 5 };
        int k = removeDuplicates(nums);
        assertEquals(1, k);
        assertArrayEquals(new int[] { 5 }, Arrays.copyOfRange(nums, 0, k));
    }

    @Test
    void noDuplicates_arrayUnchanged() {
        int[] nums = { 1, 2, 3, 4 };
        int k = removeDuplicates(nums);
        assertEquals(4, k);
        assertArrayEquals(new int[] { 1, 2, 3, 4 }, Arrays.copyOfRange(nums, 0, k));
    }

    @Test
    void boundaryValues_negativeAndPositiveExtremes() {
        int[] nums = { -100, -100, 0, 100, 100 };
        int k = removeDuplicates(nums);
        assertEquals(3, k);
        assertArrayEquals(new int[] { -100, 0, 100 }, Arrays.copyOfRange(nums, 0, k));
    }
}