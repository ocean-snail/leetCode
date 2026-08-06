package topInterview150;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 * Given a 1-indexed array of integers numbers that is already sorted in
 * non-decreasing order, find two numbers such that they add up to a specific
 * target number. Let these two numbers be numbers[index1] and numbers[index2]
 * where 1 <= index1 < index2 <= numbers.length.
 * 
 * Return the indices of the two numbers index1 and index2, each incremented by
 * one, as an integer array [index1, index2] of length 2.
 * 
 * The tests are generated such that there is exactly one solution. You may not
 * use the same element twice.
 * 
 * Your solution must use only constant extra space.
 * 
 * 
 * ? Example 1:
 * 
 * Input: numbers = [2,7,11,15], target = 9
 * Output: [1,2]
 * Explanation: The sum of 2 and 7 is 9. Therefore, index1 = 1, index2 = 2. We
 * return [1, 2].
 * 
 * ? Example 2:
 * 
 * Input: numbers = [2,3,4], target = 6
 * Output: [1,3]
 * Explanation: The sum of 2 and 4 is 6. Therefore index1 = 1, index2 = 3. We
 * return [1, 3].
 * 
 * ? Example 3:
 * 
 * Input: numbers = [-1,0], target = -1
 * Output: [1,2]
 * Explanation: The sum of -1 and 0 is -1. Therefore index1 = 1, index2 = 2. We
 * return [1, 2].
 * 
 * 
 * ! Constraints:
 * 
 * 2 <= numbers.length <= 3 * 104
 * -1000 <= numbers[i] <= 1000
 * numbers is sorted in non-decreasing order.
 * -1000 <= target <= 1000
 * The tests are generated such that there is exactly one solution.
 */
public class TwoSum2InputArrayIsSorted {

    // ------------------------------------------------------------------
    // Solution
    // ------------------------------------------------------------------

    /**
     * Returns the 1-based indices of the unique pair whose values add up to target.
     * * Time: O(n) - every iteration either increments left or decrements right,
     * and
     * the
     * gap right - left starts at n - 1 and never grows, so the loop body
     * runs at most n - 1 times.
     * * Space: O(1) - three int variables (left, right, sum) are allocated
     * regardless
     * of n.
     *
     * @param numbers array sorted in non-decreasing order, length at least 2
     * @param target  the required sum of the two selected elements
     * @return the two 1-based indices as {index1, index2} with index1 < index2
     */
    public int[] twoSum(int[] numbers, int target) {
        int left = 0;
        int right = numbers.length - 1;
        while (left < right) {
            int sum = numbers[left] + numbers[right];
            if (sum == target) {
                return new int[] { left + 1, right + 1 };
            }
            if (sum < target) {
                left++;
            } else {
                right--;
            }
        }
        return new int[] { -1, -1 };
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------

    @Test
    @DisplayName("example1_returnsFirstAndSecondIndices")
    void example1_returnsFirstAndSecondIndices() {
        assertArrayEquals(new int[] { 1, 2 }, twoSum(new int[] { 2, 7, 11, 15 }, 9));
    }

    @Test
    @DisplayName("example2_returnsFirstAndThirdIndices")
    void example2_returnsFirstAndThirdIndices() {
        assertArrayEquals(new int[] { 1, 3 }, twoSum(new int[] { 2, 3, 4 }, 6));
    }

    @Test
    @DisplayName("example3_minimumLengthWithNegativeValue_returnsBothIndices")
    void example3_minimumLengthWithNegativeValue_returnsBothIndices() {
        assertArrayEquals(new int[] { 1, 2 }, twoSum(new int[] { -1, 0 }, -1));
    }

    @Test
    @DisplayName("equalValues_returnsBothIndicesWithoutReusingOneElement")
    void equalValues_returnsBothIndicesWithoutReusingOneElement() {
        assertArrayEquals(new int[] { 1, 2 }, twoSum(new int[] { 1, 1 }, 2));
    }

    @Test
    @DisplayName("pairAtBothEnds_returnsFirstAndLastIndices")
    void pairAtBothEnds_returnsFirstAndLastIndices() {
        assertArrayEquals(new int[] { 1, 5 }, twoSum(new int[] { 0, 2, 3, 4, 10 }, 10));
    }

    @Test
    @DisplayName("pairInMiddle_returnsInnerIndices")
    void pairInMiddle_returnsInnerIndices() {
        assertArrayEquals(new int[] { 2, 3 }, twoSum(new int[] { 1, 5, 6, 20 }, 11));
    }

    @Test
    @DisplayName("allNegativeValues_returnsCorrectIndices")
    void allNegativeValues_returnsCorrectIndices() {
        assertArrayEquals(new int[] { 1, 2 }, twoSum(new int[] { -1000, -999, -998, 500 }, -1999));
    }

    @Test
    @DisplayName("maximumPointerTravel_returnsLastTwoIndices")
    void maximumPointerTravel_returnsLastTwoIndices() {
        int[] numbers = new int[2001];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = i - 1000;
        }
        assertArrayEquals(new int[] { 2000, 2001 }, twoSum(numbers, 1999));
    }

    @Test
    @DisplayName("maximumLengthArray_returnsFirstAndLastIndices")
    void maximumLengthArray_returnsFirstAndLastIndices() {
        int[] numbers = new int[30000];
        numbers[0] = -1000;
        Arrays.fill(numbers, 1, 29999, 500);
        numbers[29999] = 1000;
        assertArrayEquals(new int[] { 1, 30000 }, twoSum(numbers, 0));
    }
}