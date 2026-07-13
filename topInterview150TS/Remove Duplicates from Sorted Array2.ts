/*
Given an integer array nums sorted in non-decreasing order, 
remove some duplicates in-place such that each unique element appears at most twice.
 The relative order of the elements should be kept the same.

Since it is impossible to change the length of the array in some languages, 
you must instead have the result be placed in the first part of the array nums. More formally, 
if there are k elements after removing the duplicates, then the first k elements of nums should hold the final result. 
It does not matter what you leave beyond the first k elements.

Return k after placing the final result in the first k slots of nums.

Do not allocate extra space for another array. You must do this by modifying the input array in-place with O(1) extra memory.

Constraints:

1 <= nums.length <= 3 * 104
-104 <= nums[i] <= 104
nums is sorted in non-decreasing order.

Example 1:

Input: nums = [1,1,1,2,2,3]
Output: 5, nums = [1,1,2,2,3,_]
Explanation: Your function should return k = 5, with the first five elements of nums being 1, 1, 2, 2 and 3 respectively.
It does not matter what you leave beyond the returned k (hence they are underscores).
*/

function removeDuplicates(nums: number[]): number {
  let count = 0;
  let pointer = 0;
  for (let i = 0; i < nums.length; i++) {
    console.log(`i : ${i}`);
    if (nums[i] === nums[i - 1]) {
      if (count > 1) {
        console.log("count > 1");
        continue;
      }
      count++;
    } else {
      console.log("count = 1");
      count = 1;
    }
    nums[pointer] = nums[i];
    pointer++;
    console.log(`pointer : ${pointer}`);
  }
  return pointer;
}
function logResult(input: number[]) {
  const result = removeDuplicates(input);
  console.log(`result : ${result}`);
}

logResult([1, 1, 1, 2, 2, 3]);
logResult([0, 0, 1, 1, 1, 1, 2, 3, 3]);

/*
 같은 숫자 2번 최대
 count = 0
 pointer = 0
 if(숫자가 같다){
    if(count < 2){ 2개 이하일 때
        count++
        pointer 위치에 숫자 넣고 pointer 이동
    }
 }else(숫자가 다르다){
    count = 1
    pointer 위치에 숫자 넣고 pointer 이동
    }
    
*/
