public class PairSumInSortedArrayExample {

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

    public static void main(String[] args) {
        int[] nums = { 1, 2, 3, 4, 6 };

        int[] result = pairSumInSortedArray(nums, 6);

        System.out.println(result[0] + ", " + result[1]); // 1, 3

    }
}
