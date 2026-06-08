/*
Given an integer array nums, rotate the array to the right by k steps, where k is non-negative.

Constraints:

1 <= nums.length <= 105
-231 <= nums[i] <= 231 - 1
0 <= k <= 105

Example 1:

Input: nums = [1,2,3,4,5,6,7], k = 3
Output: [5,6,7,1,2,3,4]
Explanation:
rotate 1 steps to the right: [7,1,2,3,4,5,6]
rotate 2 steps to the right: [6,7,1,2,3,4,5]
rotate 3 steps to the right: [5,6,7,1,2,3,4]

*/

// Time - O(n*k), Space - O(1)
function rotate1(nums: number[], k: number) {
  let temp;
  k = k % nums.length;
  for (let i = 0; i < k; i++) {
    temp = nums.pop();
    if (temp === undefined) break;
    nums.unshift(temp);
  }
}

function logResult(nums: number[], k: number) {
  rotate2(nums, k);
  console.log(`nums : ${nums}`);
}

logResult([1, 2, 3, 4, 5, 6, 7], 3); // [5,6,7,1,2,3,4]
// logResult([1, 2, 3, 4, 5], 11); // [5,1,2,3,4]

// Time - O(n), Space - O(1)
function rotate2(nums: number[], k: number) {
  const n = nums.length;
  k = k % n;
  if (k === 0) return;
  const reverse = (left: number, right: number) => {
    while (left < right) {
      [nums[left], nums[right]] = [nums[right], nums[left]];
      left++;
      right--;
    }
  };

  reverse(0, n - 1);
  reverse(0, k - 1);
  reverse(k, n - 1);
}
