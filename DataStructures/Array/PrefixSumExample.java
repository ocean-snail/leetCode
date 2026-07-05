// Precompute cumulative sums so range sums become fast.
public class PrefixSumExample {

    // * Time - O(n), Space - O(n)
    private static class PrefixSum {
        private int[] prefix;

        public PrefixSum(int[] nums) {
            // * Space - O(n)
            prefix = new int[nums.length + 1];

            // * Time - O(n)
            for (int i = 0; i < nums.length; i++) {
                prefix[i + 1] = prefix[i] + nums[i];
            }
        }

        // * Time - O(1)
        public int rangeSum(int left, int right) {
            return prefix[right + 1] - prefix[left];
        }

    }

    public static void main(String[] args) {
        System.out.println("PrefixSumExample");

        int[] nums = { 2, 4, 1, 3, 5 };

        PrefixSum ps = new PrefixSum(nums);

        System.out.println(ps.rangeSum(1, 3)); // 8
    }
}
