package topInterview150;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Given an array of integers citations where citations[i] is the number of
 * citations a researcher received for their ith paper, return the researcher's
 * h-index.
 * 
 * According to the definition of h-index on Wikipedia: The h-index is defined
 * as the maximum value of h such that the given researcher has published at
 * least h papers that have each been cited at least h times.
 * 
 * 
 * 
 * ? Example 1:
 * 
 * Input: citations = [3,0,6,1,5]
 * Output: 3
 * Explanation: [3,0,6,1,5] means the researcher has 5 papers in total and each
 * of them had received 3, 0, 6, 1, 5 citations respectively.
 * Since the researcher has 3 papers with at least 3 citations each and the
 * remaining two with no more than 3 citations each, their h-index is 3.
 * 
 * ? Example 2:
 * 
 * Input: citations = [1,3,1]
 * Output: 1
 * 
 * ! Constraints:
 * 
 * n == citations.length
 * 1 <= n <= 5000
 * 0 <= citations[i] <= 1000
 */

public class HIndex {

    // * Time: O(n), Space: O(n)
    public int hIndex(int[] citations) {
        int n = citations.length;
        int[] buckets = new int[n + 1];

        for (int c : citations) {
            buckets[Math.min(c, n)]++; // cap at n: papers >= n land in bucket n
        }

        int papersWithAtLeastI = 0;
        for (int i = n; i >= 0; i--) {
            papersWithAtLeastI += buckets[i]; // papers with citations >= i
            if (papersWithAtLeastI >= i) {
                return i; // first i satisfying the definition
            }
        }
        return 0; // unreachable: i=0 always satisfies count >= 0
    }

    // ---------------- JUnit 5 tests ----------------
    // Constraint 1 <= n <= 5000 guarantees a non-empty array, so NO empty-array
    // test exists by design. In an interview, state this constraint aloud.

    @Test
    void example1_mixedValues() {
        assertEquals(3, hIndex(new int[] { 3, 0, 6, 1, 5 }));
    }

    @Test
    void example2_smallArray() {
        assertEquals(1, hIndex(new int[] { 1, 3, 1 }));
    }

    @Test
    void allZeros_returnsZero() {
        assertEquals(0, hIndex(new int[] { 0, 0, 0 }));
    }

    @Test
    void singlePaper_nonZero_returnsOne() {
        assertEquals(1, hIndex(new int[] { 100 }));
    }

    @Test
    void singlePaper_zero_returnsZero() {
        assertEquals(0, hIndex(new int[] { 0 }));
    }

    @Test
    void allEqualAndHigh_returnsN() {
        assertEquals(4, hIndex(new int[] { 4, 4, 4, 4 }));
    }

    @Test
    void citationsExceedN_cappedCorrectly() {
        assertEquals(2, hIndex(new int[] { 1000, 1000 }));
    }

    @Test
    void increasingRange_hLessThanN() {
        assertEquals(3, hIndex(new int[] { 0, 1, 2, 3, 4, 5, 6 }));
    }
}