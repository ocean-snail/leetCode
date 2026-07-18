package topInterview150;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * You are given an integer array prices where prices[i] is the price of a given
 * stock on the ith day.
 * 
 * On each day, you may decide to buy and/or sell the stock. You can only hold
 * at most one share of the stock at any time. However, you can sell and buy the
 * stock multiple times on the same day, ensuring you never hold more than one
 * share of the stock.
 * 
 * Find and return the maximum profit you can achieve.
 * 
 * 
 * ? Example 1:
 * 
 * Input: prices = [7,1,5,3,6,4]
 * Output: 7
 * Explanation: Buy on day 2 (price = 1) and sell on day 3 (price = 5), profit =
 * 5-1 = 4.
 * Then buy on day 4 (price = 3) and sell on day 5 (price = 6), profit = 6-3 =
 * 3.
 * Total profit is 4 + 3 = 7.
 * 
 * ? Example 2:
 * 
 * Input: prices = [1,2,3,4,5]
 * Output: 4
 * Explanation: Buy on day 1 (price = 1) and sell on day 5 (price = 5), profit =
 * 5-1 = 4.
 * Total profit is 4.
 * 
 * ? Example 3:
 * 
 * Input: prices = [7,6,4,3,1]
 * Output: 0
 * Explanation: There is no way to make a positive profit, so we never buy the
 * stock to achieve the maximum profit of 0.
 * 
 * 
 * ! Constraints:
 * 
 * 1 <= prices.length <= 3 * 104
 * 0 <= prices[i] <= 104
 */

public class BestTimeToBuyAndSellStock2 {

    // Time: O(n), Space: O(1)
    public int maxProfit(int[] prices) {
        int profit = 0;
        for (int i = 1; i < prices.length; i++) {
            int diff = prices[i] - prices[i - 1];
            if (diff > 0) {
                profit += diff;
            }
        }
        return profit;
    }

    @Test
    void example1_mixedUpsAndDowns() {
        assertEquals(7, maxProfit(new int[] { 7, 1, 5, 3, 6, 4 }));
    }

    @Test
    void example2_strictlyIncreasing() {
        assertEquals(4, maxProfit(new int[] { 1, 2, 3, 4, 5 }));
    }

    @Test
    void example3_strictlyDecreasing() {
        assertEquals(0, maxProfit(new int[] { 7, 6, 4, 3, 1 }));
    }

    @Test
    void singleDay_noTransactionPossible() {
        assertEquals(0, maxProfit(new int[] { 5 }));
    }

    @Test
    void allEqualPrices_noProfit() {
        assertEquals(0, maxProfit(new int[] { 3, 3, 3, 3 }));
    }

    @Test
    void twoDays_singleGain() {
        assertEquals(6, maxProfit(new int[] { 1, 7 }));
    }

    @Test
    void alternatingZigZag_capturesEveryRise() {
        // Rises: (1->5)=4, (2->8)=6, (3->9)=6 => 16
        assertEquals(16, maxProfit(new int[] { 1, 5, 2, 8, 3, 9 }));
    }

    @Test
    void zeroPrices_boundaryValues() {
        // prices[i] can be 0 per constraints: (0->4)=4, (0->2)=2 => 6
        assertEquals(6, maxProfit(new int[] { 0, 4, 0, 2 }));
    }
}