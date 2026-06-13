/*
Given an array of integers citations where citations[i] is the number of citations a researcher received for their ith paper, 
return the researcher's h-index.

According to the definition of h-index on Wikipedia: 
The h-index is defined as the maximum value of h such that the given researcher has published at least h papers that have each been cited at least h times.

 Constraints:
n == citations.length
1 <= n <= 5000
0 <= citations[i] <= 1000

Example 1:
Input: citations = [3,0,6,1,5]
Output: 3
Explanation: [3,0,6,1,5] means the researcher has 5 papers in total and each of them had received 3, 0, 6, 1, 5 citations respectively.
Since the researcher has 3 papers with at least 3 citations each and the remaining two with no more than 3 citations each, their h-index is 3.


0,1,4,5,6
자신보다 크거나 같은 수의 갯수가 숫자보다 크거나 같아야 함. -> 최대값

*/

// function hIndex(citations: number[]): number {
//   const n = citations.length;
//   citations.sort((a, b) => a - b);

//   console.log(`citations : ${citations}`);
//   for (let i = n - 1; i >= 0; i--) {
//     if (citations[i] <= n - i) {
//       return citations[i];
//     }
//     console.log(`i : ${i}`);
//   }

//   return Math.min(n, citations[0]);
// }

function hIndex(citations: number[]): number {
  const n = citations.length;
  citations.sort((a, b) => a - b);
  let MaxNumber = Math.min(n, citations[0]); // 1 or 0 또는 길이가 1임

  for (let i = 0; i < n - 1; i++) {
    MaxNumber = Math.max(MaxNumber, citations[i]);
    // 2,5,6,7,8
    // 5,6,7,8
    if (citations[i] <= n - i) {
      MaxNumber = citations[i];
    } else {
    }
  }

  return 0;
}

function logResult(citations: number[]) {
  const result = hIndex(citations);
  console.log(`result : ${result}`);
}

// logResult([3, 0, 6, 1, 5]); //3
// logResult([3]); //1
// logResult([1]); //1
logResult([0, 0, 3]); //1
logResult([0, 0, 0]); //0
// logResult([100]); //1
// logResult([7, 8, 5, 2, 6]); //4
// 개수와 숫자 중 최솟값
