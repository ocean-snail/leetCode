/*
You are given an array prices where prices[i] is the price of a given stock on the ith day.
You want to maximize your profit by choosing a single day to buy one stock and choosing a different day in the future to sell that stock.
Return the maximum profit you can achieve from this transaction. 
If you cannot achieve any profit, return 0.

Constraints:

1 <= prices.length <= 105
0 <= prices[i] <= 104

Example 1:

Input: prices = [7,1,5,3,6,4]
Output: 5
Explanation: Buy on day 2 (price = 1) and sell on day 5 (price = 6), profit = 6-1 = 5.
Note that buying on day 2 and selling on day 1 is not allowed because you must buy before you sell.
*/
function maxProfit1(prices: number[]): number {
  let minPrice = Infinity;
  let maxProfit = 0;

  for (const price of prices) {
    if (price < minPrice) {
      minPrice = price;
    } else if (price - minPrice > maxProfit) {
      maxProfit = price - minPrice;
    }
  }

  return maxProfit;
}

function maxProfit2(prices: number[]): number {
  if (prices.length === 1) return 0;

  let profit = 0;
  let max = prices[0];
  let min = prices[0];
  if (min === undefined) return 0;

  for (let i = 0; i < prices.length; i++) {
    if (min > prices[i]) {
      min = prices[i];
      max = prices[i];
    } else if (max < prices[i]) {
      max = prices[i];
    } else {
      continue;
    }
    if (profit < max - min) {
      profit = max - min;
    }
  }
  return profit;
}

function logResult(prices: number[]) {
  const result = maxProfit1(prices);
  console.log(`result :${result}`);
}

logResult([7, 1, 5, 3, 6, 4]); //5
logResult([2, 4, 1]); //2
