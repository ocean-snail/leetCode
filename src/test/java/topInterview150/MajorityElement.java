package topInterview150;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Given an array nums of size n, return the majority element.
 * The majority element is the element that appears more than ⌊n / 2⌋ times. You
 * may assume that the majority element always exists in the array.
 * 
 * ? Example 1:
 * 
 * Input: nums = [3,2,3]
 * Output: 3
 * 
 * ? Example 2:
 * 
 * Input: nums = [2,2,1,1,1,2,2]
 * Output: 2
 * 
 * 
 * ! Constraints:
 * 
 * n == nums.length
 * 1 <= n <= 5 * 104
 * -109 <= nums[i] <= 109
 * The input is generated such that a majority element will exist in the array.
 * 
 * 
 * Follow-up: Could you solve the problem in linear time and in O(1) space?
 */

public class MajorityElement {
    /**
     * Boyer-Moore Voting Algorithm.
     * Time: O(n) — single pass. Space: O(1) — two scalar variables.
     */
    public int majorityElement(int[] nums) {
        int candidate = 0;
        int count = 0;

        for (int num : nums) {
            if (count == 0) {
                candidate = num;
            }
            count += (num == candidate) ? 1 : -1;
        }

        return candidate;
    }

    // --- Tests ---

    @Test
    @DisplayName("Example 1: [3,2,3] -> 3")
    void example1() {
        assertEquals(3, this.majorityElement(new int[] { 3, 2, 3 }));
    }

    @Test
    @DisplayName("Example 2: [2,2,1,1,1,2,2] -> 2")
    void example2() {
        assertEquals(2, this.majorityElement(new int[] { 2, 2, 1, 1, 1, 2, 2 }));
    }

    @Test
    @DisplayName("Single element array -> that element")
    void singleElement() {
        assertEquals(7, this.majorityElement(new int[] { 7 }));
    }

    @Test
    @DisplayName("All elements identical")
    void allIdentical() {
        assertEquals(5, this.majorityElement(new int[] { 5, 5, 5, 5 }));
    }

    @Test
    @DisplayName("Negative majority element")
    void negativeMajority() {
        assertEquals(-1, this.majorityElement(new int[] { -1, -1, 2, -1, 3 }));
    }

    @Test
    @DisplayName("Majority wins by exactly one occurrence over the threshold")
    void tightestMargin() {
        // n = 5, floor(5/2) = 2, majority count = 3
        assertEquals(4, this.majorityElement(new int[] { 4, 9, 4, 9, 4 }));
    }

    @Test
    @DisplayName("Majority elements scattered non-contiguously")
    void scatteredMajority() {
        assertEquals(6, this.majorityElement(new int[] { 6, 1, 6, 2, 6, 3, 6 }));
    }
}