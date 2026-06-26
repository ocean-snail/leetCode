/*
? Common Signals
* Pair search
* Reverse
* Sorted array
* Palindrome
* Remove duplicates
* Linked list cycle
*/

import java.util.Arrays;

public class TwoPointersExample {

    // * Time - O(n) | Space - O(1)
    public static int[] pairSumInSortedArray(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            int sum = nums[left] + nums[right];

            if (sum == target) {
                return new int[] { left, right };
            }

            if (sum < target) {
                left++;
            } else {
                right--;
            }
        }

        return new int[] { -1, -1 };
    }

    // * Time - O(n) | Space - O(1)
    public static void reverse(int[] nums) {
        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;

            left++;
            right--;
        }
    }

    public static void main(String[] args) {
        int[] nums = { 1, 2, 3, 4, 6 };

        int[] result = pairSumInSortedArray(nums, 6);

        System.out.println(result[0] + ", " + result[1]); // 1, 3

        reverse(nums);
        System.out.println(Arrays.toString(nums));

    }
}