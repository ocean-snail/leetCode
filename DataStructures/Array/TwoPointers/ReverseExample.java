import java.util.Arrays;

public class ReverseExample {

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

        reverse(nums);
        System.out.println(Arrays.toString(nums));

    }
}
