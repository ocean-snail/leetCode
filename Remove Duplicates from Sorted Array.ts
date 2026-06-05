/*
Given an integer array nums sorted in non-decreasing order, remove the duplicates in-place such that each unique element appears only once. 
The relative order of the elements should be kept the same.

Consider the number of unique elements in nums to be k​​​​​​​​​​​​​​. After removing duplicates, return the number of unique elements k.

The first k elements of nums should contain the unique numbers in sorted order. 
The remaining elements beyond index k - 1 can be ignored.

Example 1:

Input: nums = [1,1,2]
Output: 2, nums = [1,2,_]
Explanation: Your function should return k = 2, with the first two elements of nums being 1 and 2 respectively.
It does not matter what you leave beyond the returned k (hence they are underscores).
*/

function removeDuplicates(nums: number[]): number {
  let count = 0;

  while (count !== nums.length) {
    if (nums[count] === nums[count + 1]) {
      nums.splice(count + 1, 1);
    } else {
      count++;
    }
    console.log("count : " + count);
    console.log("nums : " + nums);
  }

  return count;
}

function removeDuplicates2(nums: number[]): number {
  let pointer = 0;

  for (let i = 0; i < nums.length; i++) {
    if (nums[i] !== nums[i - 1]) {
      nums[pointer] = nums[i];
      pointer++;
    }
  }
  return pointer;
}

let result = removeDuplicates([1, 1, 2, 2, 2, 3, 3, 4, 5, 5, 5, 5]);
console.log("result : " + result);
