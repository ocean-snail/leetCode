package topInterview150;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * There are n children standing in a line. Each child is assigned a rating
 * value given in the integer array ratings.
 * 
 * You are giving candies to these children subjected to the following
 * requirements:
 * 
 * Each child must have at least one candy.
 * Children with a higher rating get more candies than their neighbors.
 * Return the minimum number of candies you need to have to distribute the
 * candies to the children.
 * 
 * 
 * ? Example 1:
 * 
 * Input: ratings = [1,0,2]
 * Output: 5
 * Explanation: You can allocate to the first, second and third child with 2, 1,
 * 2 candies respectively.
 * 
 * ?Example 2:
 * 
 * Input: ratings = [1,2,2]
 * Output: 4
 * Explanation: You can allocate to the first, second and third child with 1, 2,
 * 1 candies respectively.
 * The third child gets 1 candy because it satisfies the above two conditions.
 * 
 * 
 * ! Constraints:
 * 
 * n == ratings.length
 * 1 <= n <= 2 * 104
 * 0 <= ratings[i] <= 2 * 104
 * 
 */
public class Candy {

    // ------------------------------------------------------------------
    // Solution
    // ------------------------------------------------------------------

    // * Time O(n), Space O(1)
    public int candy(int[] ratings) {
        int n = ratings.length;
        int total = 1;
        int up = 0;
        int down = 0;
        int peak = 0;

        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) {
                up++;
                down = 0;
                peak = up;
                total += 1 + up;
            } else if (ratings[i] == ratings[i - 1]) {
                up = 0;
                down = 0;
                peak = 0;
                total += 1;
            } else {
                up = 0;
                down++;
                total += 1 + down - (peak >= down ? 1 : 0);
            }
        }
        return total;
    }

    // =====================================================================
    // Tests
    // =====================================================================

    @Test
    @DisplayName("Example 1: valley in the middle")
    void example1() {
        assertEquals(5, candy(new int[] { 1, 0, 2 }));
    }

    @Test
    @DisplayName("Example 2: equal neighbours impose no constraint")
    void example2() {
        assertEquals(4, candy(new int[] { 1, 2, 2 }));
    }

    @Test
    @DisplayName("Single child gets exactly one candy")
    void singleChild() {
        assertEquals(1, candy(new int[] { 5 }));
    }

    @Test
    @DisplayName("All ratings equal -> one candy each")
    void allEqual() {
        assertEquals(3, candy(new int[] { 3, 3, 3 }));
    }

    @Test
    @DisplayName("Strictly increasing -> 1+2+3+4")
    void strictlyIncreasing() {
        assertEquals(10, candy(new int[] { 1, 2, 3, 4 }));
    }

    @Test
    @DisplayName("Strictly decreasing -> right pass carries the whole answer")
    void strictlyDecreasing() {
        assertEquals(10, candy(new int[] { 4, 3, 2, 1 }));
    }

    @Test
    @DisplayName("Plateau at the peak: the plateau breaks the run")
    void plateauAtPeak() {
        assertEquals(13, candy(new int[] { 1, 2, 87, 87, 87, 2, 1 }));
    }

    @Test
    @DisplayName("Descent longer than ascent: the peak must be raised")
    void descentLongerThanAscent() {
        assertEquals(13, candy(new int[] { 1, 2, 3, 2, 1, 0 }));
    }

    @Test
    @DisplayName("Plateau inside a valley")
    void plateauInsideValley() {
        assertEquals(7, candy(new int[] { 1, 3, 2, 2, 1 }));
    }
}