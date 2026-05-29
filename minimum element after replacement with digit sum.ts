/*
You are given an integer array nums.
You replace each element in nums with the sum of its digits.
Return the minimum element in nums after all replacements.

Example 1:
Input: nums = [10,12,13,14]
Output: 1
Explanation:
nums becomes [1, 3, 4, 5] after all replacements, with minimum element 1.
*/

function minElement(nums: number[]): number {
  const sumArray = [];
  for (let i = 0; i < nums.length; i++) {
    const numbers = nums[i].toString().split("");
    let sum = 0;
    for (let j = 0; j < numbers.length; j++) {
      sum += Number(numbers[j]);
    }
    sumArray.push(sum);
  }

  return Math.min(...sumArray);
}

let result = minElement([10, 132, 13, 14]); // 1
console.log("result : " + result);
