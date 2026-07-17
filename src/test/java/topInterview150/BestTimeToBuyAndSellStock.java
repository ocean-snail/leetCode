package topInterview150;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * You are given an array prices where prices[i] is the price of a given stock
 * on the ith day.
 * 
 * You want to maximize your profit by choosing a single day to buy one stock
 * and choosing a different day in the future to sell that stock.
 * 
 * Return the maximum profit you can achieve from this transaction. If you
 * cannot achieve any profit, return 0.
 * 
 * 
 * ? Example 1:
 * 
 * Input: prices = [7,1,5,3,6,4]
 * Output: 5
 * Explanation: Buy on day 2 (price = 1) and sell on day 5 (price = 6), profit =
 * 6-1 = 5.
 * Note that buying on day 2 and selling on day 1 is not allowed because you
 * must buy before you sell.
 * 
 * ? Example 2:
 * 
 * Input: prices = [7,6,4,3,1]
 * Output: 0
 * Explanation: In this case, no transactions are done and the max profit = 0.
 * 
 * 
 * ! Constraints:
 * 
 * 1 <= prices.length <= 105
 * 0 <= prices[i] <= 104
 */

public class BestTimeToBuyAndSellStock {

    public int maxProfit(int[] prices) {
        int minPrice = prices[0];
        int maxProfit = 0;

        for (int i = 1; i < prices.length; i++) {
            // Best profit if we SELL today: today's price minus cheapest earlier buy.
            maxProfit = Math.max(maxProfit, prices[i] - minPrice);

            // Update the cheapest buy price for FUTURE days.
            minPrice = Math.min(minPrice, prices[i]);
        }

        return maxProfit;
    }

    // --- Tests ---

    @Test
    void example1_buyLow_sellHigherLater() {
        assertEquals(5, maxProfit(new int[] { 7, 1, 5, 3, 6, 4 }));
    }

    @Test
    void example2_strictlyDecreasing_returnsZero() {
        assertEquals(0, maxProfit(new int[] { 7, 6, 4, 3, 1 }));
    }

    @Test
    void singleDay_cannotSell_returnsZero() {
        assertEquals(0, maxProfit(new int[] { 5 }));
    }

    @Test
    void twoDays_increasing_simpleProfit() {
        assertEquals(3, maxProfit(new int[] { 1, 4 }));
    }

    @Test
    void allPricesEqual_returnsZero() {
        assertEquals(0, maxProfit(new int[] { 3, 3, 3, 3 }));
    }

    @Test
    void minimumAppearsLast_cannotBuyAfterEnd() {
        // Best profit uses min BEFORE the peak, not the global min at the end.
        assertEquals(4, maxProfit(new int[] { 2, 6, 1 }));
    }

    @Test
    void newMinimumMidway_profitUsesLaterMin() {
        // Global min (0) appears mid-array; best sale happens after it.
        assertEquals(8, maxProfit(new int[] { 5, 7, 0, 3, 8 }));
    }
}