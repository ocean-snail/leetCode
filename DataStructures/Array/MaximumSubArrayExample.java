
/*
! Complexity
* Time - O(n) | Space - O(1)

* When solving optimization problems, keep the best result ending at the current position.
*/
public class MaximumSubArrayExample {

    public static int maximumSubArray(int[] nums) {
        int currentMax = nums[0];
        int totalMax = nums[0];

        for (int i = 0; i < nums.length; i++) {
            currentMax = Math.max(nums[i], currentMax + nums[i]);
            totalMax = Math.max(totalMax, currentMax);
        }

        return totalMax;
    }

    public static void main(String[] args) {
        int[] nums = { -2, 1, -3, 4, -1, 2, 1, -5, 4 };
        int result = maximumSubArray(nums);

        System.out.println(result);
    }
}
