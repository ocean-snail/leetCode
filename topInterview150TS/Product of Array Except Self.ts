/*
Given an integer array nums, 
return an array answer such that answer[i] is equal to the product of all the elements of nums except nums[i].
The product of any prefix or suffix of nums is guaranteed to fit in a 32-bit integer.
You must write an algorithm that runs in O(n) time and without using the division operation.

Constraints:
2 <= nums.length <= 105
-30 <= nums[i] <= 30
The input is generated such that answer[i] is guaranteed to fit in a 32-bit integer.

Follow up: Can you solve the problem in O(1) extra space complexity? 
(The output array does not count as extra space for space complexity analysis.)

Example 1:
Input: nums = [1,2,3,4]
Output: [24,12,8,6]

TODO get the product of all elements
! if 0 in the elements
* 1. count of 0 is more than 1
* 2. count of 0 is 1
! size of the product, nums -> constrains


[1,2,3,4]
[1,1,1,1]

[1,1,2,6] left ->
[24,12,4,1] right <-
[24,12,8,6]
*/

function productExceptSelf(nums: number[]): number[] {
  const n = nums.length;
  const answer: number[] = new Array(n).fill(1);

  // Left pass: answer[i] = product of all elements to the left of i
  let prefix = 1;
  for (let i = 0; i < n; i++) {
    answer[i] = prefix;
    prefix *= nums[i];
  }

  // Right pass: multiply by product of all elements to the right of i
  let suffix = 1;
  for (let i = n - 1; i >= 0; i--) {
    answer[i] *= suffix;
    suffix *= nums[i];
  }

  return answer;
}

// function productExceptSelf(nums: number[]): number[] {
//   let product = 1;
//   let count = 0;
//   let result = [];
//   for (let i = 0; i < nums.length; i++) {
//     if (nums[i] !== 0) {
//       product *= nums[i];
//     } else {
//       if (++count > 1) break;
//     }
//   }

//   for (let i = 0; i < nums.length; i++) {
//     if (count > 1 || (count == 1 && nums[i] != 0)) {
//       result.push(0);
//     } else {
//       if (nums[i] == 0) {
//         result.push(product);
//       } else {
//         result.push(product / nums[i]);
//       }
//     }
//   }
//   return result;
// }

function logResult(nums: number[]) {
  const result = productExceptSelf(nums);
  console.log(`result : ${result}`);
}

logResult([1, 2, 3, 4]); // [24,12,8,6]
logResult([0, 0, 0, 1, 0]); // [0,0,0,0,0]
logResult([-1, -5, 0, 1, 2]); // 0,0,10,0,0]
logResult([-1, 1, 0, -3, 3]); // [0,0,9,0,0]
