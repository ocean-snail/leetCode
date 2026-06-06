/*
Given an array nums of size n, return the majority element.
The majority element is the element that appears more than ⌊n / 2⌋ times. 
You may assume that the majority element always exists in the array.

Example 1:

Input: nums = [3,2,3]
Output: 3
*/

// function majorityElement(nums: number[]): number {
//   let countMap = new Map();

//   for (let i = 0; i < nums.length; i++) {
//     let mapHasNum = countMap.has(nums[i]);
//     if (!mapHasNum) {
//       countMap.set(nums[i], 1);
//     } else {
//       let count = countMap.get(nums[i]);
//       console.log(`count : ${count}`);
//       if (++count > nums.length / 2) return nums[i];
//       countMap.set(nums[i], count);
//     }
//     console.log(`mapHasNum : ${mapHasNum}`);
//   }
//   return nums[0];
// }

function majorityElement(nums: number[]): number {
  if (nums.length === 0) return 0;
  let candidate = nums[0];
  let count = 1;
  for (let i = 1; i < nums.length; i++) {
    if (count === 0) {
      candidate = nums[i];
      count = 1;
    } else if (nums[i] === candidate) {
      count++;
    } else {
      count--;
    }

    // console.log(`i : ${i}`);
    // console.log(`candidate : ${candidate}`);
    // console.log(`count : ${count}`);
  }
  return candidate;
}

function logResult(numbers: number[]) {
  let result = majorityElement(numbers);
  console.log(`result : ${result}`);
}

logResult([3, 2, 3]);
logResult([2, 2, 1, 1, 1, 2, 2]);
logResult([
  1, 1, 1, 1, 1, 2, 5, 2, 5, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 5, 5, 5, 2,
]);
