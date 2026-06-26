import java.util.HashMap;
import java.util.Map;

/*
? Need fast lookup
* Find a pair
* Find a complement
* Check if something was seen before
* -> Hash Map or Hash Set
*/
public class TwoSumExample {

    // Time - O(n), Space - O(n)
    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            int current = nums[i];
            int needed = target - current;

            if (map.containsKey(needed)) {
                return new int[] { map.get(needed), i };
            }

            map.put(current, i);
        }

        return new int[] { -1, -1 };
    }

    public static void main(String[] args) {
        int[] nums1 = { 2, 7, 11, 15 };
        int[] nums2 = { 1, 3, 5, 9, 17, 50 };
        int[] nums3 = { 0, 0 };

        int target1 = 9;
        int target2 = 14;
        int target3 = 1;

        int[] result1 = twoSum(nums1, target1);
        int[] result2 = twoSum(nums2, target2);
        int[] result3 = twoSum(nums3, target3);

        System.out.println(result1[0] + ", " + result1[1]); // 0, 1
        System.out.println(result2[0] + ", " + result2[1]); // 2, 3
        System.out.println(result3[0] + ", " + result3[1]); // -1, -1

    }
}