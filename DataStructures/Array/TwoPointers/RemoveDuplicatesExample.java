import java.util.Arrays;

public class RemoveDuplicatesExample {

    // * Time - O(n) | Space - O(1)
    // return length of the array
    public static int removeDuplicates(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }

        int left = 0;
        // * Time - O(n)
        for (int right = 1; right < nums.length; right++) {
            if (nums[left] != nums[right]) {
                left++;
                nums[left] = nums[right];

            }
        }
        return left + 1;

    }

    public static void main(String[] args) {
        int[] duplicateNums = { 1, 1, 2, 2, 3, 3, 5 };
        int newLength = removeDuplicates(duplicateNums);

        System.out.println("New length: " + newLength);
        System.out.println(Arrays.toString(Arrays.copyOf(duplicateNums, newLength)));
    }
}
