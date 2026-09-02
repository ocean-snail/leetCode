package topInterview150;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Arrays;
import java.util.Comparator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Given an array of intervals where intervals[i] = [starti, endi], merge all
 * overlapping intervals, and return an array of the non-overlapping intervals
 * that cover all the intervals in the input.
 * 
 * 
 * ? Example 1:
 * 
 * Input: intervals = [[1,3],[2,6],[8,10],[15,18]]
 * Output: [[1,6],[8,10],[15,18]]
 * Explanation: Since intervals [1,3] and [2,6] overlap, merge them into [1,6].
 * 
 * ? Example 2:
 * 
 * Input: intervals = [[1,4],[4,5]]
 * Output: [[1,5]]
 * Explanation: Intervals [1,4] and [4,5] are considered overlapping.
 * 
 * ? Example 3:
 * 
 * Input: intervals = [[4,7],[1,4]]
 * Output: [[1,7]]
 * Explanation: Intervals [1,4] and [4,7] are considered overlapping.
 * 
 * 
 * ! Constraints:
 * 
 * 1 <= intervals.length <= 104
 * intervals[i].length == 2
 * 0 <= starti <= endi <= 104
 * 
 */
public class MergeIntervals {

    // ------------------------------------------------------------------
    // Solution
    // ------------------------------------------------------------------

    /**
     * Returns the merged intervals in ascending order of start value.
     *
     * * Time: O(n log n) - Arrays.sort on n rows dominates; the scan below reads
     * each
     * row once.
     * * Best O(n) - TimSort walks an already ascending input with n - 1 comparisons
     * and no merge passes.
     * * Average O(n log n) - TimSort merges O(log n) sorted runs whose total length
     * is n.
     * * Worst O(n log n) - the same sort bound; the scan adds O(n).
     * * Space: O(n) - one shallow clone of the outer array (n references), one
     * output
     * array of at
     * most n rows, plus the O(n) temporary buffer TimSort allocates for object
     * arrays.
     *
     * @param intervals n rows of length 2, each row holding a start value and an
     *                  end value
     * @return the non-overlapping intervals covering exactly the same values as the
     *         input
     */
    public int[][] merge(int[][] intervals) {
        int[][] sorted = intervals.clone();
        Arrays.sort(sorted, Comparator.comparingInt(row -> row[0]));

        int[][] merged = new int[sorted.length][];
        int size = 0;
        for (int[] interval : sorted) {
            if (size > 0 && interval[0] <= merged[size - 1][1]) {
                merged[size - 1][1] = Math.max(merged[size - 1][1], interval[1]);
            } else {
                merged[size] = new int[] { interval[0], interval[1] };
                size++;
            }
        }
        return Arrays.copyOf(merged, size);
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------

    @Test
    @DisplayName("Example 1: overlapping [1,3] and [2,6] merge while the two later intervals stay separate")
    void overlappingPairAmongSeparateIntervals_mergesOnlyThatPair() {
        assertArrayEquals(
                new int[][] { { 1, 6 }, { 8, 10 }, { 15, 18 } },
                merge(new int[][] { { 1, 3 }, { 2, 6 }, { 8, 10 }, { 15, 18 } }));
    }

    @Test
    @DisplayName("Example 2: intervals sharing a single endpoint merge into one interval")
    void intervalsSharingOneEndpoint_mergeIntoSingleInterval() {
        assertArrayEquals(new int[][] { { 1, 5 } }, merge(new int[][] { { 1, 4 }, { 4, 5 } }));
    }

    @Test
    @DisplayName("Example 3: unsorted input merges after the rows are ordered by start value")
    void unsortedTouchingIntervals_mergeIntoSingleInterval() {
        assertArrayEquals(new int[][] { { 1, 7 } }, merge(new int[][] { { 4, 7 }, { 1, 4 } }));
    }

    @Test
    @DisplayName("A single degenerate interval is returned unchanged")
    void singleDegenerateInterval_returnsThatInterval() {
        assertArrayEquals(new int[][] { { 5, 5 } }, merge(new int[][] { { 5, 5 } }));
    }

    @Test
    @DisplayName("An interval nested inside the previous one leaves the larger end in place")
    void nestedInterval_keepsTheLargerEnd() {
        assertArrayEquals(new int[][] { { 1, 10 } }, merge(new int[][] { { 1, 10 }, { 2, 3 }, { 4, 5 } }));
    }

    @Test
    @DisplayName("Intervals separated by one uncovered value are reported separately")
    void adjacentButDisjointIntervals_staySeparate() {
        assertArrayEquals(new int[][] { { 1, 2 }, { 3, 4 } }, merge(new int[][] { { 1, 2 }, { 3, 4 } }));
    }

    @Test
    @DisplayName("Repeated identical intervals collapse into one interval")
    void repeatedIdenticalIntervals_collapseIntoOne() {
        assertArrayEquals(new int[][] { { 2, 3 } }, merge(new int[][] { { 2, 3 }, { 2, 3 }, { 2, 3 } }));
    }

    @Test
    @DisplayName("Intervals sharing a start value merge and keep the largest end value")
    void sharedStartWithDifferentEnds_keepsLargestEnd() {
        assertArrayEquals(new int[][] { { 1, 9 } }, merge(new int[][] { { 1, 2 }, { 1, 9 }, { 1, 5 } }));
    }

    @Test
    @DisplayName("Input given in descending start order is returned in ascending start order")
    void descendingStartOrder_returnsAscendingOrder() {
        assertArrayEquals(
                new int[][] { { 1, 3 }, { 5, 6 }, { 8, 10 } },
                merge(new int[][] { { 8, 10 }, { 5, 6 }, { 1, 3 } }));
    }

    @Test
    @DisplayName("Intervals at both constraint boundaries merge into the full coordinate range")
    void constraintBoundaryIntervals_mergeIntoFullRange() {
        assertArrayEquals(
                new int[][] { { 0, 10_000 } },
                merge(new int[][] { { 0, 0 }, { 0, 10_000 }, { 10_000, 10_000 } }));
    }

    @Test
    @DisplayName("The caller's array keeps its original row order after the call returns")
    void callerArray_isNotReordered() {
        int[][] input = { { 8, 10 }, { 1, 3 }, { 2, 6 } };
        merge(input);
        assertArrayEquals(new int[][] { { 8, 10 }, { 1, 3 }, { 2, 6 } }, input);
    }
}