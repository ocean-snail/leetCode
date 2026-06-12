/*
You are given a 0-indexed array of integers nums of length n. 
You are initially positioned at index 0.

Each element nums[i] represents the maximum length of a forward jump from index i. 
In other words, if you are at index i, you can jump to any index (i + j) where:

0 <= j <= nums[i] and
i + j < n
Return the minimum number of jumps to reach index n - 1. 
The test cases are generated such that you can reach index n - 1.

Constraints:
1 <= nums.length <= 104
0 <= nums[i] <= 1000
It's guaranteed that you can reach nums[n - 1].


Example 1:
Input: nums = [2,3,1,1,4]
Output: 2
Explanation: The minimum number of jumps to reach the last index is 2. Jump 1 step from index 0 to 1, then 3 steps to the last index.

count one more 
* Input: nums = [3,4,4,1,2,3,6,4,2,2,2,2] 
* Input: nums = [!,4,!,1,2,3,!,4,2,2,2,2]
1회에 이동가능 3칸
nums[i] 만큼 경우의 수 생김

nums[i] 3이어서 3개 
2회에 이동가능 1+4 (nums[0] + nums[1]) - 5
2회에 이동가능 2+4 (nums[0] + nums[2]) - 6
2회에 이동가능 3+1 (nums[0] + nums[3]) - 4

nums[5] 3개
3회에 이동가능 1+4+? (nums[0] + nums[1] + ?)

nums[6] 6개
3회에 이동가능 2+4+?

nums[4] 2개
3회에 이동가능 3+1+?

i 돌면서 최대이동거리 갱신 -> 최대 이동 거리 max 정의
횟수 확정 때마다(최대 이동거리(currentTarget) 도달 시) 갱신 -> 횟수 jumps 정의, currentTarget 정의
목표 도달 시 종료


*/

function jump(nums: number[]): number {
  let jumps = 0;
  let currentEnd = 0;
  let max = 0;

  for (let i = 0; i < nums.length - 1; i++) {
    max = Math.max(max, i + nums[i]);
    if (i === currentEnd) {
      jumps++;
      currentEnd = max;
      if (currentEnd >= nums.length - 1) break;
    }
  }

  return jumps;
}

function logResult(nums: number[]) {
  const result = jump(nums);
  console.log(`result : ${result}`);
}

// logResult([0]);
// logResult([1]);
logResult([3, 0, 1, 2, 0, 5, 2, 0, 2, 0, 0]);
// logResult([2, 3, 1, 1, 4]); // 2
// logResult([2, 3, 1, 1, 4]); // 2
// logResult([3, 4, 4, 1, 2, 3, 6, 4, 2, 2, 2, 2]); // 3
