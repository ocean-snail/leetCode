package topInterview150;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * You are given an array of non-overlapping intervals intervals where
 * intervals[i] = [starti, endi] represent the start and the end of the ith
 * interval and intervals is sorted in ascending order by starti. You are also
 * given an interval newInterval = [start, end] that represents the start and
 * end of another interval.
 * 
 * Two intervals are considered overlapping if they share at least one point.
 * 
 * Insert newInterval into intervals such that intervals is still sorted in
 * ascending order by starti and intervals still does not have any overlapping
 * intervals (merge overlapping intervals if necessary).
 * 
 * Return intervals after the insertion.
 * 
 * Note that you don't need to modify intervals in-place. You can make a new
 * array and return it.
 * 
 * 
 * ? Example 1:
 * 
 * Input: intervals = [[1,3],[6,9]], newInterval = [2,5]
 * Output: [[1,5],[6,9]]
 * 
 * ? Example 2:
 * 
 * Input: intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]], newInterval = [4,8]
 * Output: [[1,2],[3,10],[12,16]]
 * Explanation: Because the new interval [4,8] overlaps with [3,5],[6,7],[8,10].
 * 
 * 
 * ! Constraints:
 * 
 * 0 <= intervals.length <= 104
 * intervals[i].length == 2
 * 0 <= starti <= endi <= 105
 * intervals is sorted by starti in ascending order.
 * newInterval.length == 2
 * 0 <= start <= end <= 105
 * 
 */
public class InsertInterval {

    // ------------------------------------------------------------------
    // Solution
    // ------------------------------------------------------------------

    /**
     * Inserts newInterval into a sorted, non-overlapping interval list and merges
     * any overlap.
     *
     * * Time: O(n) - the index i never decreases across the three while loops, so
     * each of the n
     * rows of intervals is read exactly once.
     * * Space: O(n) - one int[n + 1][] row buffer plus the trimmed copy returned to
     * the caller;
     * no recursion and no sorting workspace.
     *
     * @param intervals   non-overlapping intervals sorted in ascending order by
     *                    start
     * @param newInterval the interval to insert, given as a two-element array
     *                    {start, end}
     * @return a new array holding the merged intervals, still sorted by start
     */
    public int[][] insert(int[][] intervals, int[] newInterval) {
        int n = intervals.length;
        int[][] merged = new int[n + 1][];
        int size = 0;
        int i = 0;
        while (i < n && intervals[i][1] < newInterval[0]) {
            merged[size++] = new int[] { intervals[i][0], intervals[i][1] };
            i++;
        }
        int start = newInterval[0];
        int end = newInterval[1];
        while (i < n && intervals[i][0] <= end) {
            start = Math.min(start, intervals[i][0]);
            end = Math.max(end, intervals[i][1]);
            i++;
        }
        merged[size++] = new int[] { start, end };
        while (i < n) {
            merged[size++] = new int[] { intervals[i][0], intervals[i][1] };
            i++;
        }
        return Arrays.copyOf(merged, size);
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------

    @Test
    @DisplayName("LeetCode example 1: [[1,3],[6,9]] with [2,5] merges the first interval only")
    void newIntervalOverlapsFirstInterval_mergesFirstAndKeepsRest() {
        int[][] intervals = { { 1, 3 }, { 6, 9 } };
        assertArrayEquals(new int[][] { { 1, 5 }, { 6, 9 } }, insert(intervals, new int[] { 2, 5 }));
    }

    @Test
    @DisplayName("LeetCode example 2: [4,8] absorbs [3,5], [6,7] and [8,10] into [3,10]")
    void newIntervalSpansThreeIntervals_collapsesThemIntoOne() {
        int[][] intervals = { { 1, 2 }, { 3, 5 }, { 6, 7 }, { 8, 10 }, { 12, 16 } };
        assertArrayEquals(new int[][] { { 1, 2 }, { 3, 10 }, { 12, 16 } }, insert(intervals, new int[] { 4, 8 }));
    }

    @Test
    @DisplayName("An empty interval list returns the new interval as the only element")
    void emptyIntervals_returnsNewIntervalAlone() {
        assertArrayEquals(new int[][] { { 4, 8 } }, insert(new int[0][2], new int[] { 4, 8 }));
    }

    @Test
    @DisplayName("Touching at a single point counts as overlapping and triggers a merge")
    void newIntervalTouchesBothNeighboursAtOnePoint_mergesAllThree() {
        int[][] intervals = { { 1, 2 }, { 5, 6 } };
        assertArrayEquals(new int[][] { { 1, 6 } }, insert(intervals, new int[] { 2, 5 }));
    }

    @Test
    @DisplayName("A new interval ending one unit before the first start is inserted, not merged")
    void newIntervalEndsJustBeforeFirstStart_insertsAsSeparateInterval() {
        int[][] intervals = { { 3, 5 }, { 7, 9 } };
        assertArrayEquals(new int[][] { { 0, 2 }, { 3, 5 }, { 7, 9 } }, insert(intervals, new int[] { 0, 2 }));
    }

    @Test
    @DisplayName("A new interval beyond every end is appended at the tail")
    void newIntervalStartsAfterLastEnd_appendsAtTail() {
        int[][] intervals = { { 1, 2 }, { 4, 6 } };
        assertArrayEquals(new int[][] { { 1, 2 }, { 4, 6 }, { 8, 10 } }, insert(intervals, new int[] { 8, 10 }));
    }

    @Test
    @DisplayName("A new interval contained in an existing one leaves the list unchanged")
    void newIntervalInsideExistingInterval_returnsSameIntervals() {
        int[][] intervals = { { 1, 10 } };
        assertArrayEquals(new int[][] { { 1, 10 } }, insert(intervals, new int[] { 3, 4 }));
    }

    @Test
    @DisplayName("A new interval covering every existing interval returns one spanning interval")
    void newIntervalCoversEveryInterval_returnsSingleSpan() {
        int[][] intervals = { { 2, 3 }, { 5, 7 } };
        assertArrayEquals(new int[][] { { 1, 9 } }, insert(intervals, new int[] { 1, 9 }));
    }

    @Test
    @DisplayName("A zero-width new interval landing in a gap is inserted between the neighbours")
    void zeroWidthNewIntervalInGap_insertedBetweenNeighbours() {
        int[][] intervals = { { 1, 2 }, { 5, 6 } };
        assertArrayEquals(new int[][] { { 1, 2 }, { 4, 4 }, { 5, 6 } }, insert(intervals, new int[] { 4, 4 }));
    }

    @Test
    @DisplayName("The input array is not modified by the insertion")
    void insertion_leavesInputArrayUnmodified() {
        int[][] intervals = { { 1, 3 }, { 6, 9 } };
        insert(intervals, new int[] { 2, 5 });
        assertArrayEquals(new int[][] { { 1, 3 }, { 6, 9 } }, intervals);
    }

    @Test
    @DisplayName("Returned rows are fresh copies, so writing to the result never reaches the input")
    void writingToReturnedRow_leavesInputArrayUnchanged() {
        int[][] intervals = { { 1, 2 }, { 5, 6 } };
        int[][] result = insert(intervals, new int[] { 9, 9 });
        result[0][0] = 99;
        assertArrayEquals(new int[][] { { 1, 2 }, { 5, 6 } }, intervals);
    }
}