import java.util.HashMap;
import java.util.Map;

// Given an integer array nums and an integer k, count how many contiguous subArrays sum to exactly k.
public class SubArraySumEqualsKExample {

    // * Time - O(n), Space - O(n)
    public static int subArraySum(int[] nums, int k) {
        // * Space - O(n)
        Map<Integer, Integer> prefixCount = new HashMap<>();

        prefixCount.put(0, 1);

        int currentPrefix = 0;
        int count = 0;

        // * Time - O(n)
        for (int num : nums) {
            currentPrefix += num;

            int neededPrefix = currentPrefix - k;

            // * Time - O(1)
            if (prefixCount.containsKey(neededPrefix)) {
                count += prefixCount.get(neededPrefix);
            }

            prefixCount.put(
                    currentPrefix,
                    prefixCount.getOrDefault(currentPrefix, 0) + 1);
        }

        return count;
    }

    public static void main(String[] args) {
        System.out.println("SubArraySumEqualsKExample");
        int[] nums = { 1, 2, 3 };
        int k = 3;
        int[] nums2 = { 7 };
        int k2 = 7;

        System.out.println(subArraySum(nums, k)); // 2
        System.out.println(subArraySum(nums2, k2)); // 1
    }

}
