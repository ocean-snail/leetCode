package topInterview150;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Given an m x n matrix, return all elements of the matrix in spiral order.
 * 
 * 
 * ?Example 1:
 * 
 * 
 * Input: matrix = [[1,2,3],[4,5,6],[7,8,9]]
 * Output: [1,2,3,6,9,8,7,4,5]
 * 
 * ? Example 2:
 * 
 * 
 * Input: matrix = [[1,2,3,4],[5,6,7,8],[9,10,11,12]]
 * Output: [1,2,3,4,8,12,11,10,9,5,6,7]
 * 
 * 
 * ! Constraints:
 * 
 * m == matrix.length
 * n == matrix[i].length
 * 1 <= m, n <= 10
 * -100 <= matrix[i][j] <= 100
 */
public class SpiralMatrix {

    // ------------------------------------------------------------------
    // Solution
    // ------------------------------------------------------------------

    /**
     * Reads the matrix edge by edge while shrinking four boundary indices inward.
     * * Time: O(m * n) - each of the m * n cells is read by exactly one of the four
     * inner loops, and
     * every loop iteration reads a cell, so the total iteration count equals the
     * cell count.
     * * Space: O(1) - only five int variables (top, bottom, left, right, and a loop
     * index) are
     * allocated; the returned list is the required output and is not counted as
     * auxiliary space.
     *
     * @param matrix a non-empty rectangular matrix with m rows and n columns
     * @return the m * n elements in clockwise spiral order, starting at
     *         matrix[0][0]
     */
    public List<Integer> spiralOrder(int[][] matrix) {
        int top = 0;
        int bottom = matrix.length - 1;
        int left = 0;
        int right = matrix[0].length - 1;
        List<Integer> order = new ArrayList<>(matrix.length * matrix[0].length);
        while (top <= bottom && left <= right) {
            for (int col = left; col <= right; col++) {
                order.add(matrix[top][col]);
            }
            top++;
            for (int row = top; row <= bottom; row++) {
                order.add(matrix[row][right]);
            }
            right--;
            if (top <= bottom) {
                for (int col = right; col >= left; col--) {
                    order.add(matrix[bottom][col]);
                }
                bottom--;
            }
            if (left <= right) {
                for (int row = bottom; row >= top; row--) {
                    order.add(matrix[row][left]);
                }
                left++;
            }
        }
        return order;
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------

    @Test
    @DisplayName("LeetCode example 1: a 3x3 matrix spirals inward to its centre")
    void threeByThreeExample_returnsClockwiseSpiral() {
        int[][] matrix = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };
        assertEquals(List.of(1, 2, 3, 6, 9, 8, 7, 4, 5), spiralOrder(matrix));
    }

    @Test
    @DisplayName("LeetCode example 2: a 3x4 matrix ends on the inner row it entered")
    void threeByFourExample_returnsClockwiseSpiral() {
        int[][] matrix = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 } };
        assertEquals(List.of(1, 2, 3, 4, 8, 12, 11, 10, 9, 5, 6, 7), spiralOrder(matrix));
    }

    @Test
    @DisplayName("A 1x1 matrix returns its only element")
    void singleCell_returnsThatCell() {
        int[][] matrix = { { 42 } };
        assertEquals(List.of(42), spiralOrder(matrix));
    }

    @Test
    @DisplayName("A single row is returned left to right with no element repeated")
    void singleRow_returnsRowLeftToRight() {
        int[][] matrix = { { 1, 2, 3, 4, 5 } };
        assertEquals(List.of(1, 2, 3, 4, 5), spiralOrder(matrix));
    }

    @Test
    @DisplayName("A single column is returned top to bottom with no element repeated")
    void singleColumn_returnsColumnTopToBottom() {
        int[][] matrix = { { 1 }, { 2 }, { 3 }, { 4 }, { 5 } };
        assertEquals(List.of(1, 2, 3, 4, 5), spiralOrder(matrix));
    }

    @Test
    @DisplayName("A 2x2 matrix produces one complete clockwise cycle")
    void twoByTwo_returnsClockwiseCycle() {
        int[][] matrix = { { 1, 2 }, { 3, 4 } };
        assertEquals(List.of(1, 2, 4, 3), spiralOrder(matrix));
    }

    @Test
    @DisplayName("The constraint bounds -100 and 100 are returned unchanged and in order")
    void extremeValues_arePreservedInOrder() {
        int[][] matrix = { { -100, 100 }, { 0, -100 } };
        assertEquals(List.of(-100, 100, -100, 0), spiralOrder(matrix));
    }
}