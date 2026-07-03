public class FixedSlidingWindowExample {

    // * Find the maximum sum of any subArray of size k.
    // * Time - O(n) | Space - O(1)
    public static int maxSumSubArrayOfSizeK(int[] nums, int k) {
        int windowSum = 0;
        int maxSum = Integer.MIN_VALUE;

        // * Time - O(n)
        for (int right = 0; right < nums.length; right++) {
            windowSum += nums[right];

            if (right >= k - 1) {
                maxSum = Math.max(maxSum, windowSum);

                int left = right - k + 1;
                windowSum -= nums[left];
            }
        }

        return maxSum;
    }

    public static void main(String[] args) {
        int[] nums = { 2, 1, 5, 1, 3, 2 };

        System.out.println(maxSumSubArrayOfSizeK(nums, 3)); // 9
    }

}
