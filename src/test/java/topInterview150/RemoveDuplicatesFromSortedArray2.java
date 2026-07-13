package topInterview150;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;

/**
 * Given an integer array nums sorted in non-decreasing order, remove some
 * duplicates in-place such that each unique element appears at most twice. The
 * relative order of the elements should be kept the same.
 * 
 * Since it is impossible to change the length of the array in some languages,
 * you must instead have the result be placed in the first part of the array
 * nums. More formally, if there are k elements after removing the duplicates,
 * then the first k elements of nums should hold the final result. It does not
 * matter what you leave beyond the first k elements.
 * 
 * Return k after placing the final result in the first k slots of nums.
 * 
 * Do not allocate extra space for another array. You must do this by modifying
 * the input array in-place with O(1) extra memory.
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
 * Input: nums = [1,1,1,2,2,3]
 * Output: 5, nums = [1,1,2,2,3,_]
 * Explanation: Your function should return k = 5, with the first five elements
 * of nums being 1, 1, 2, 2 and 3 respectively.
 * It does not matter what you leave beyond the returned k (hence they are
 * underscores).
 * 
 * ? Example 2:
 * 
 * Input: nums = [0,0,1,1,1,1,2,3,3]
 * Output: 7, nums = [0,0,1,1,2,3,3,_,_]
 * Explanation: Your function should return k = 7, with the first seven elements
 * of nums being 0, 0, 1, 1, 2, 3 and 3 respectively.
 * It does not matter what you leave beyond the returned k (hence they are
 * underscores).
 * 
 * 
 * ! Constraints:
 * 
 * 1 <= nums.length <= 3 * 104
 * -104 <= nums[i] <= 104
 * nums is sorted in non-decreasing order.
 */
public class RemoveDuplicatesFromSortedArray2 {

    // ---------- Solution ----------
    int removeDuplicates(int[] nums) {
        int write = 0;
        for (int value : nums) {
            if (write < 2 || nums[write - 2] != value) {
                nums[write] = value;
                write++;
            }
        }
        return write;
    }

    // ---------- Tests ----------

    @Test
    void example1_tripleCollapsesToDouble() {
        int[] nums = { 1, 1, 1, 2, 2, 3 };
        int k = removeDuplicates(nums);
        assertEquals(5, k);
        assertArrayEquals(new int[] { 1, 1, 2, 2, 3 }, Arrays.copyOfRange(nums, 0, k));
    }

    @Test
    void example2_mixedRunLengths() {
        int[] nums = { 0, 0, 1, 1, 1, 1, 2, 3, 3 };
        int k = removeDuplicates(nums);
        assertEquals(7, k);
        assertArrayEquals(new int[] { 0, 0, 1, 1, 2, 3, 3 }, Arrays.copyOfRange(nums, 0, k));
    }

    @Test
    void singleElement_returnsOne() {
        int[] nums = { 7 };
        int k = removeDuplicates(nums);
        assertEquals(1, k);
        assertArrayEquals(new int[] { 7 }, Arrays.copyOfRange(nums, 0, k));
    }

    @Test
    void twoElements_bothKeptEvenIfEqual() {
        int[] nums = { 4, 4 };
        int k = removeDuplicates(nums);
        assertEquals(2, k);
        assertArrayEquals(new int[] { 4, 4 }, Arrays.copyOfRange(nums, 0, k));
    }

    @Test
    void allSameValue_collapsesToTwo() {
        int[] nums = { 5, 5, 5, 5, 5, 5 };
        int k = removeDuplicates(nums);
        assertEquals(2, k);
        assertArrayEquals(new int[] { 5, 5 }, Arrays.copyOfRange(nums, 0, k));
    }

    @Test
    void noDuplicates_arrayUnchanged() {
        int[] nums = { 1, 2, 3, 4 };
        int k = removeDuplicates(nums);
        assertEquals(4, k);
        assertArrayEquals(new int[] { 1, 2, 3, 4 }, Arrays.copyOfRange(nums, 0, k));
    }

    @Test
    void exactlyTwoOfEach_nothingRemoved() {
        int[] nums = { 1, 1, 2, 2, 3, 3 };
        int k = removeDuplicates(nums);
        assertEquals(6, k);
        assertArrayEquals(new int[] { 1, 1, 2, 2, 3, 3 }, Arrays.copyOfRange(nums, 0, k));
    }

    @Test
    void boundaryValues_constraintExtremes() {
        int[] nums = { -10_000, -10_000, -10_000, 0, 10_000, 10_000, 10_000 };
        int k = removeDuplicates(nums);
        assertEquals(5, k);
        assertArrayEquals(new int[] { -10_000, -10_000, 0, 10_000, 10_000 },
                Arrays.copyOfRange(nums, 0, k));
    }
}