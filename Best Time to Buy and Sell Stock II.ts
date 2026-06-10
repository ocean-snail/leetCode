/*
You are given an integer array prices where prices[i] is the price of a given stock on the ith day.
On each day, you may decide to buy and/or sell the stock. You can only hold at most one share of the stock at any time. 
However, you can sell and buy the stock multiple times on the same day, ensuring you never hold more than one share of the stock.
Find and return the maximum profit you can achieve.

Constraints:

1 <= prices.length <= 3 * 104
0 <= prices[i] <= 104

Example 1:

Input: prices = [7,1,5,3,6,4]
Output: 7
Explanation: Buy on day 2 (price = 1) and sell on day 3 (price = 5), profit = 5-1 = 4.
Then buy on day 4 (price = 3) and sell on day 5 (price = 6), profit = 6-3 = 3.
Total profit is 4 + 3 = 7.

* 다른 각도에서 보기

1. 주식 하나만 보유 가능
2. 같은 날 팔고 사기 가능 -> 의미 없음
3. 최대 이익을 남겨서 출력

주식 정의(시작값, 이익)
쌀 때 사고 비쌀 때 팔기 - 언제 싼가? 언제 비싼가?
    싼 경우 - 숫자가 이전 값보다 작으면  -> 비교 후 시작값에 저장
    비싼 경우 - 숫자가 크면  -> 비교 후 이익에 저장
이익을 기록하고 있어야 함.

경우의 수
1. 숫자가 이전 값과 같거나 크다
    1.1 이익 저장
2. 숫자가 이전 값보다 작다
    2.1 시작값에 입력
    2.2 저장한 이익값 따로 뺌
-> 총 이익값 합계 후 출력


이익 
for(){
    if(현재값 > 이전값){
        이익 += 현재값 - 이전값        
    } 
}


*/

function maxProfit(prices: number[]): number {
  let profit = 0;
  for (let i = 1; i < prices.length; i++) {
    if (prices[i] > prices[i - 1]) {
      profit += prices[i] - prices[i - 1];
    }
  }
  return profit;
}

function logResult(prices: number[]) {
  const result = maxProfit(prices);
  console.log(`result : ${result}`);
}

logResult([7, 1, 5, 3, 6, 4]); // 7
logResult([1, 2, 3, 4, 5]); //4
logResult([7, 6, 4, 3, 1]); //0

// function maxProfit2(prices: number[]): number {
//   if (prices.length <= 1) return 0;
//   let profit = 0;
//   let totalProfit = 0;

//   for (let i = 1; i < prices.length; i++) {
//     if (prices.at(i - 1)! > prices[i]) {
//       totalProfit += profit;
//       profit = 0;
//     } else {
//       profit += prices[i] - prices.at(i - 1)!;
//       if (i === prices.length - 1) {
//         totalProfit += profit;
//       }
//     }
//   }
//   return totalProfit;
// }
