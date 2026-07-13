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


*/

// Time - O(n log n), Space - O(1)
function hIndex(citations: number[]): number {
  let h = 0;
  citations.sort((a, b) => b - a);

  while (h < citations.length && citations[h] > h) {
    h++;
  }

  return h;
}

function logResult(citations: number[]) {
  const result = hIndex(citations);
  console.log(`result : ${result}`);
}

// logResult([0, 1, 3, 5, 6]); //3
// logResult([3]); //1
// logResult([1]); //1
logResult([0, 0, 0, 3]); //1
// logResult([0, 0, 0]); //0
// logResult([100]); //1
// logResult([4, 100]); //2
// logResult([2, 5, 6, 7, 8]); //4
// logResult([2, 3, 6, 7, 8]); //3
