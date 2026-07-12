package topInterview150;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
You are given two integer arrays nums1 and nums2, sorted in non-decreasing order, and two integers m and n,
 representing the number of elements in nums1 and nums2 respectively.

Merge nums1 and nums2 into a single array sorted in non-decreasing order.

The final sorted array should not be returned by the function, but instead be stored inside the array nums1. 
To accommodate this, nums1 has a length of m + n, where the first m elements denote the elements that should be merged, 
and the last n elements are set to 0 and should be ignored. nums2 has a length of n.

 
Example 1: 
Input: nums1 = [1,2,3,0,0, = 3, nums2 = [2,5,6], n = 3
Output: [1,2,2,3,5,6]
Explanation: The arrays we are merging are [1,2,3] and [2,5,6].
The result of the merge is [1,2,2,3,5,6] with the underlined elements coming from nums1.

Constraints:
nums1.length == m + n
nums2.length == n
0 <= m, n <= 200
1 <= m + n <= 200
-109 <= nums1[i], nums2[j] <= 109
*/

/**
 * LeetCode 88. Merge Sorted Array
 * https://leetcode.com/problems/merge-sorted-array/
 *
 * Approach: three-pointer backward merge.
 * Time O(m + n), Space O(1).
 */
class MergeSortedArray {

    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int LastRealInNum1 = m - 1;
        int LastInNum2 = n - 1;
        int LastPositionInAll = m + n - 1;

        while (LastInNum2 >= 0) {
            if (LastRealInNum1 >= 0 && nums1[LastRealInNum1] > nums2[LastInNum2]) {
                nums1[LastPositionInAll--] = nums1[LastRealInNum1--];
            } else {
                nums1[LastPositionInAll--] = nums2[LastInNum2--];
            }
        }
    }

    // ---------------------------------------------------------------
    // Tests
    // ---------------------------------------------------------------

    @Test
    void mergesInterleavedArrays() {
        int[] nums1 = { 1, 2, 3, 0, 0, 0 };
        int[] nums2 = { 2, 5, 6 };
        merge(nums1, 3, nums2, 3);
        assertArrayEquals(new int[] { 1, 2, 2, 3, 5, 6 }, nums1);
    }

    @Test
    void handlesEmptyNums1() {
        int[] nums1 = { 1 };
        int[] nums2 = {};
        merge(nums1, 1, nums2, 0);
        assertArrayEquals(new int[] { 1 }, nums1);
    }

    @Test
    void handlesEmptyNums2() {
        int[] nums1 = { 0 };
        int[] nums2 = { 1 };
        merge(nums1, 0, nums2, 1);
        assertArrayEquals(new int[] { 1 }, nums1);
    }

    @Test
    void allNums2ElementsSmallerThanNums1() {
        int[] nums1 = { 4, 5, 6, 0, 0, 0 };
        int[] nums2 = { 1, 2, 3 };
        merge(nums1, 3, nums2, 3);
        assertArrayEquals(new int[] { 1, 2, 3, 4, 5, 6 }, nums1);
    }

    @Test
    void handlesDuplicatesAndNegatives() {
        int[] nums1 = { -3, -1, -1, 0, 0 };
        int[] nums2 = { -1, 2 };
        merge(nums1, 3, nums2, 2);
        assertArrayEquals(new int[] { -3, -1, -1, -1, 2 }, nums1);
    }
}