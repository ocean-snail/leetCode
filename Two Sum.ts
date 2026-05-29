/* Given an array of integers numbers and an integer target, return indices of the two numbers such that they add up to target.
You may assume that each input would have exactly one solution, and you may not use the same element twice.
You can return the answer in any order.

example)
Input: numbers = [2,7,11,15], target = 9
Output: [0,1]
Explanation: Because numbers[0] + numbers[1] == 9, we return [0, 1].


1. numbers in input can be negative or 0
*/

function twoSum(nums: number[], target: number): number[] {
  for (let i = 0; i < nums.length; i++) {
    for (let j = i + 1; j < nums.length; j++) {
      if (nums[j] === target - nums[i]) {
        return [i, j];
      }
    }
  }
  return [];
}

console.log(twoSum([2, 6, 1, 3, 5], 4));
console.log(twoSum([2, 11, 6, 13, 1, 3, 5, 12], 23));
console.log(twoSum([2, 11, 1, 2], 4));
console.log(twoSum([0, 4, 3, 0], 0));
console.log(twoSum([-3, 4, 3, 90], 0));
console.log(twoSum([-10, -1, -18, -19], -19));
console.log(
  twoSum([2, 7, 11, 15, 6, 45, 23, 6, 1, 0, 0, 0, 2, 6, 8, 2, 100, 100], 200),
);
