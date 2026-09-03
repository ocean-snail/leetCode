package topInterview150;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MinimumNumberOfArrowsToBurstBalloons {

    // ------------------------------------------------------------------
    // Solution
    // ------------------------------------------------------------------

    /**
     * Returns the smallest number of vertical arrows that intersect every interval.
     *
     * * Time: O(n log n) average and worst case - Arrays.sort performs O(n log n)
     * comparisons on the n
     * row references, and the following single pass reads each row once, so the
     * sort dominates.
     * Best case O(n) - when the rows already arrive in non-decreasing order of
     * points[i][1],
     * TimSort detects one ascending run and performs n - 1 comparisons without
     * merging.
     * * Space: O(n) - one cloned array of n row references plus the temporary merge
     * buffer that
     * Arrays.sort allocates for object arrays, both proportional to n.
     *
     * @param points each points[i] is the closed interval [points[i][0],
     *               points[i][1]] of one balloon
     * @return the minimum number of arrow positions needed so that every interval
     *         contains at least one
     */
    public int findMinArrowShots(int[][] points) {
        int[][] sorted = points.clone();
        Arrays.sort(sorted, (a, b) -> Integer.compare(a[1], b[1]));
        int arrows = 1;
        int arrowX = sorted[0][1];
        for (int i = 1; i < sorted.length; i++) {
            if (sorted[i][0] > arrowX) {
                arrows++;
                arrowX = sorted[i][1];
            }
        }
        return arrows;
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------

    @Test
    @DisplayName("The four overlapping balloons of example 1 are burst by two arrows")
    void example1_returnsTwo() {
        assertEquals(2, findMinArrowShots(new int[][] { { 10, 16 }, { 2, 8 }, { 1, 6 }, { 7, 12 } }));
    }

    @Test
    @DisplayName("Four pairwise disjoint balloons need one arrow each")
    void example2_returnsFour() {
        assertEquals(4, findMinArrowShots(new int[][] { { 1, 2 }, { 3, 4 }, { 5, 6 }, { 7, 8 } }));
    }

    @Test
    @DisplayName("Balloons that only touch at shared endpoints are burst by two arrows")
    void example3_returnsTwo() {
        assertEquals(2, findMinArrowShots(new int[][] { { 1, 2 }, { 2, 3 }, { 3, 4 }, { 4, 5 } }));
    }

    @Test
    @DisplayName("A single balloon is burst by one arrow")
    void singleBalloon_returnsOne() {
        assertEquals(1, findMinArrowShots(new int[][] { { 7, 9 } }));
    }

    @Test
    @DisplayName("Repeated identical intervals share one arrow")
    void identicalIntervals_returnsOne() {
        assertEquals(1, findMinArrowShots(new int[][] { { 4, 5 }, { 4, 5 }, { 4, 5 } }));
    }

    @Test
    @DisplayName("A balloon starting exactly at the previous right endpoint shares that arrow")
    void startEqualToPreviousEnd_returnsOne() {
        assertEquals(1, findMinArrowShots(new int[][] { { 1, 2 }, { 2, 3 } }));
    }

    @Test
    @DisplayName("A balloon starting one unit after the previous right endpoint needs a second arrow")
    void startOneUnitAfterPreviousEnd_returnsTwo() {
        assertEquals(2, findMinArrowShots(new int[][] { { 1, 2 }, { 3, 4 } }));
    }

    @Test
    @DisplayName("Fully nested intervals are burst by one arrow at the innermost right endpoint")
    void nestedIntervals_returnsOne() {
        assertEquals(1, findMinArrowShots(new int[][] { { 1, 10 }, { 2, 9 }, { 3, 8 } }));
    }

    @Test
    @DisplayName("Two disjoint intervals at the opposite ends of the int range need two arrows")
    void disjointIntervalsAtIntBounds_returnsTwo() {
        assertEquals(2, findMinArrowShots(
                new int[][] { { 2147483646, 2147483647 }, { -2147483648, -2147483647 } }));
    }

    @Test
    @DisplayName("An interval spanning the whole int range shares an arrow with an interval inside it")
    void fullRangeIntervalWithNestedInterval_returnsOne() {
        assertEquals(1, findMinArrowShots(
                new int[][] { { -2147483648, 2147483647 }, { -2147483648, -2147483647 } }));
    }

    @Test
    @DisplayName("Solving does not reorder the caller's array")
    void solvingInput_leavesCallerArrayUnchanged() {
        int[][] input = { { 10, 16 }, { 2, 8 }, { 1, 6 }, { 7, 12 } };
        findMinArrowShots(input);
        assertArrayEquals(new int[][] { { 10, 16 }, { 2, 8 }, { 1, 6 }, { 7, 12 } }, input);
    }
}