/*
You are given an integer array nums. 
You are initially positioned at the array's first index, 
and each element in the array represents your maximum jump length at that position.
Return true if you can reach the last index, or false otherwise.

!Constraints:
1 <= nums.length <= 104
0 <= nums[i] <= 105


Example 1:

Input: nums = [2,3,1,1,4]
Output: true
Explanation: Jump 1 step from index 0 to 1, then 3 steps to the last index.

* 풀이순서
1. jumpDistance 0으로 정의
2. nums 순서대로 순회, i++, jumpDistance--
3. jumpDistance 배열 끝에 도달 또는 넘어갈 때 return true
4. 순회 중 nums[i] 와 jumpDistance 비교 후 nums[i] 가 크면 jumpDistance 갱신.


*/

function canJump(nums: number[]): boolean {
  let jumpDistance = 0;
  for (let i = 0; i < nums.length; i++) {
    if (i > jumpDistance) return false;
    if (jumpDistance < nums[i] + i) {
      jumpDistance = nums[i] + i;
    }
    if (jumpDistance >= nums.length - 1) return true;
  }

  return true;
}

function logResult(nums: number[]) {
  const result = canJump(nums);
  console.log(`result : ${result}`);
}

logResult([2, 3, 1, 1, 4]); // true
logResult([2]); // true
logResult([0]); // true
logResult([2, 0]); // true
logResult([0, 1]); // false
logResult([2, 0, 0]); // true
logResult([2, 5, 0, 0]); // true
logResult([3, 2, 1, 0, 4]); // false
logResult([3, 2, 1, 0, 4]); // false
