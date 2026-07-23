package topInterview150;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 * Given an integer array nums, return an array answer such that answer[i] is
 * equal to the product of all the elements of nums except nums[i].
 * 
 * The product of any prefix or suffix of nums is guaranteed to fit in a 32-bit
 * integer.
 * 
 * You must write an algorithm that runs in O(n) time and without using the
 * division operation.
 * 
 * 
 * ? Example 1:
 * 
 * Input: nums = [1,2,3,4]
 * Output: [24,12,8,6]
 * 
 * ? Example 2:
 * 
 * Input: nums = [-1,1,0,-3,3]
 * Output: [0,0,9,0,0]
 * 
 * 
 * ! Constraints:
 * 
 * 2 <= nums.length <= 105
 * -30 <= nums[i] <= 30
 * The input is generated such that answer[i] is guaranteed to fit in a 32-bit
 * integer.
 * 
 * 
 * Follow up: Can you solve the problem in O(1) extra space complexity? (The
 * output array does not count as extra space for space complexity analysis.)
 */
public class ProductofArrayExceptSelf {

    // ------------------------------------------------------------------
    // Solution
    // ------------------------------------------------------------------

    // * Time O(n), Space O(1)
    int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] answer = new int[n];

        answer[0] = 1;
        for (int i = 1; i < n; i++) {
            answer[i] = answer[i - 1] * nums[i - 1];
        }

        int suffix = 1;
        for (int i = n - 1; i >= 0; i--) {
            answer[i] *= suffix;
            suffix *= nums[i];
        }

        return answer;
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------

    @Test
    @DisplayName("Example 1: all positive, no zero")
    void allPositive() {
        assertArrayEquals(new int[] { 24, 12, 8, 6 }, productExceptSelf(new int[] { 1, 2, 3, 4 }));
    }

    @Test
    @DisplayName("Example 2: exactly one zero present")
    void singleZero() {
        assertArrayEquals(new int[] { 0, 0, 9, 0, 0 }, productExceptSelf(new int[] { -1, 1, 0, -3, 3 }));
    }

    @Test
    @DisplayName("Minimum length n = 2")
    void minimumLength() {
        assertArrayEquals(new int[] { 7, 3 }, productExceptSelf(new int[] { 3, 7 }));
    }

    @Test
    @DisplayName("Two or more zeros collapse every entry to 0")
    void multipleZeros() {
        assertArrayEquals(new int[] { 0, 0 }, productExceptSelf(new int[] { 0, 0 }));
        assertArrayEquals(new int[] { 0, 0, 0 }, productExceptSelf(new int[] { 0, 5, 0 }));
    }

    @Test
    @DisplayName("Negative values: sign must be carried, not just magnitude")
    void negativeValues() {
        assertArrayEquals(new int[] { -12, -8, 6 }, productExceptSelf(new int[] { -2, -3, 4 }));
    }

    @Test
    @DisplayName("Value boundaries -30 and 30")
    void valueBoundaries() {
        assertArrayEquals(new int[] { -30, 30, -900 }, productExceptSelf(new int[] { 30, -30, 1 }));
    }

    @Test
    @DisplayName("Large input stays linear and correct")
    void largeInput() {
        int n = 100_000;
        int[] nums = new int[n];
        Arrays.fill(nums, 1);

        int[] expected = new int[n];
        Arrays.fill(expected, 1);

        assertArrayEquals(expected, productExceptSelf(nums));
    }
}