import java.util.Arrays;

public class SortedSquaresExample {

    // * Time - O(n) | Space - O(n)
    public static int[] sortedSquares(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        int position = nums.length - 1;

        // * Space - O(n)
        int[] result = new int[nums.length];

        // * Time - O(n)
        while (left <= right) {
            int leftSquare = nums[left] * nums[left];
            int rightSquare = nums[right] * nums[right];

            if (leftSquare > rightSquare) {
                result[position] = leftSquare;
                left++;
            } else {
                result[position] = rightSquare;
                right--;
            }

            position--;
        }

        return result;
    }

    public static void main(String[] args) {
        int[] nums = { -4, -1, 0, 3, 10 };

        System.out.println(Arrays.toString(sortedSquares(nums)));
        // [0, 1, 9, 16, 100]
    }
}