package topInterview150;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

/**
 * Given an integer array nums and an integer val, remove all occurrences of val
 * in nums in-place. The order of the elements may be changed. Then return the
 * number of elements in nums which are not equal to val.
 * 
 * Consider the number of elements in nums which are not equal to val be k, to
 * get accepted, you need to do the following things:
 * 
 * Change the array nums such that the first k elements of nums contain the
 * elements which are not equal to val. The remaining elements of nums are not
 * important as well as the size of nums.
 * Return k.
 * Custom Judge:
 * 
 * The judge will test your solution with the following code:
 * 
 * int[] nums = [...]; // Input array
 * int val = ...; // Value to remove
 * int[] expectedNums = [...]; // The expected answer with correct length.
 * // It is sorted with no values equaling val.
 * 
 * int k = removeElement(nums, val); // Calls your implementation
 * 
 * assert k == expectedNums.length;
 * sort(nums, 0, k); // Sort the first k elements of nums
 * for (int i = 0; i < actualLength; i++) {
 * assert nums[i] == expectedNums[i];
 * }
 * If all assertions pass, then your solution will be accepted.
 * 
 * 
 * 
 * Example 1:
 * 
 * Input: nums = [3,2,2,3], val = 3
 * Output: 2, nums = [2,2,_,_]
 * Explanation: Your function should return k = 2, with the first two elements
 * of nums being 2.
 * It does not matter what you leave beyond the returned k (hence they are
 * underscores).
 * 
 * Example 2:
 * 
 * Input: nums = [0,1,2,2,3,0,4,2], val = 2
 * Output: 5, nums = [0,1,4,0,3,_,_,_]
 * Explanation: Your function should return k = 5, with the first five elements
 * of nums containing 0, 0, 1, 3, and 4.
 * Note that the five elements can be returned in any order.
 * It does not matter what you leave beyond the returned k (hence they are
 * underscores).
 * 
 * 
 * ! Constraints:
 * 
 * 0 <= nums.length <= 100
 * 0 <= nums[i] <= 50
 * 0 <= val <= 100
 */
public class RemoveElement {
    /**
     * Removes all occurrences of val in-place.
     * Two-pointer overwrite: k is the write index for kept elements.
     * * Time O(n), space O(1).
     */
    public int removeElement(int[] nums, int val) {
        int k = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != val) {
                nums[k] = nums[i];
                k++;
            }
        }
        return k;
    }

    // Mirrors the custom judge: check k, then sort the first k elements and
    // compare.
    private void assertJudge(int[] nums, int val, int[] expectedNums) {
        int k = removeElement(nums, val);
        assertEquals(expectedNums.length, k, "returned k is wrong");
        int[] firstK = Arrays.copyOfRange(nums, 0, k);
        Arrays.sort(firstK);
        assertArrayEquals(expectedNums, firstK, "first k elements are wrong");
    }

    @Test
    void example1() {
        assertJudge(new int[] { 3, 2, 2, 3 }, 3, new int[] { 2, 2 });
    }

    @Test
    void example2() {
        assertJudge(new int[] { 0, 1, 2, 2, 3, 0, 4, 2 }, 2, new int[] { 0, 0, 1, 3, 4 });
    }

    @Test
    void emptyArray() {
        assertJudge(new int[] {}, 0, new int[] {});
    }

    @Test
    void allElementsEqualVal() {
        assertJudge(new int[] { 7, 7, 7 }, 7, new int[] {});
    }

    @Test
    void noElementEqualsVal() {
        assertJudge(new int[] { 1, 2, 3 }, 9, new int[] { 1, 2, 3 });
    }

    @Test
    void singleElementEqualsVal() {
        assertJudge(new int[] { 5 }, 5, new int[] {});
    }

    @Test
    void singleElementNotEqualVal() {
        assertJudge(new int[] { 5 }, 3, new int[] { 5 });
    }
}
