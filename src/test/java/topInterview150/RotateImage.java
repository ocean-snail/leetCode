package topInterview150;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * You are given an n x n 2D matrix representing an image, rotate the image by
 * 90 degrees (clockwise).
 * 
 * You have to rotate the image in-place, which means you have to modify the
 * input 2D matrix directly. DO NOT allocate another 2D matrix and do the
 * rotation.
 * 
 * 
 * ? Example 1:
 * 
 * 
 * Input: matrix = [[1,2,3],[4,5,6],[7,8,9]]
 * Output: [[7,4,1],[8,5,2],[9,6,3]]
 * 
 * ? Example 2:
 * 
 * 
 * Input: matrix = [[5,1,9,11],[2,4,8,10],[13,3,6,7],[15,14,12,16]]
 * Output: [[15,13,2,5],[14,3,4,1],[12,6,8,9],[16,7,10,11]]
 * 
 * 
 * ! Constraints:
 * 
 * n == matrix.length == matrix[i].length
 * 1 <= n <= 20
 * -1000 <= matrix[i][j] <= 1000
 */
public class RotateImage {

    // ------------------------------------------------------------------
    // Solution
    // ------------------------------------------------------------------

    /**
     * Rotates the matrix 90 degrees clockwise in place.
     *
     * * Time: O(n^2) - the transpose pass performs n*(n-1)/2 swaps, one per cell
     * strictly
     * above the diagonal, and the reversal pass performs n*(n/2) swaps, one per
     * pair
     * of mirrored cells in each of the n rows; together that is proportional to
     * n^2.
     * * Space: O(1) - the only storage introduced is the loop counters row, col,
     * left, right
     * and a single int named temp holding one element during each swap; no array is
     * allocated, so the extra memory does not grow with n.
     *
     * @param matrix the n x n matrix to rotate; its contents are overwritten in
     *               place
     */
    public void rotate(int[][] matrix) {
        int n = matrix.length;
        for (int row = 0; row < n; row++) {
            for (int col = row + 1; col < n; col++) {
                int temp = matrix[row][col];
                matrix[row][col] = matrix[col][row];
                matrix[col][row] = temp;
            }
        }
        for (int row = 0; row < n; row++) {
            int left = 0;
            int right = n - 1;
            while (left < right) {
                int temp = matrix[row][left];
                matrix[row][left] = matrix[row][right];
                matrix[row][right] = temp;
                left++;
                right--;
            }
        }
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------

    @Test
    @DisplayName("LeetCode example 1: 3x3 matrix of 1..9 rotates clockwise")
    void threeByThreeExample_rotatedClockwise() {
        int[][] matrix = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };
        int[][] expected = { { 7, 4, 1 }, { 8, 5, 2 }, { 9, 6, 3 } };
        rotate(matrix);
        assertArrayEquals(expected, matrix);
    }

    @Test
    @DisplayName("LeetCode example 2: 4x4 matrix rotates clockwise")
    void fourByFourExample_rotatedClockwise() {
        int[][] matrix = { { 5, 1, 9, 11 }, { 2, 4, 8, 10 }, { 13, 3, 6, 7 }, { 15, 14, 12, 16 } };
        int[][] expected = { { 15, 13, 2, 5 }, { 14, 3, 4, 1 }, { 12, 6, 8, 9 }, { 16, 7, 10, 11 } };
        rotate(matrix);
        assertArrayEquals(expected, matrix);
    }

    @Test
    @DisplayName("Smallest allowed matrix (n = 1) is left unchanged")
    void singleCell_unchanged() {
        int[][] matrix = { { 42 } };
        int[][] expected = { { 42 } };
        rotate(matrix);
        assertArrayEquals(expected, matrix);
    }

    @Test
    @DisplayName("2x2 matrix rotates clockwise")
    void twoByTwo_rotatedClockwise() {
        int[][] matrix = { { 1, 2 }, { 3, 4 } };
        int[][] expected = { { 3, 1 }, { 4, 2 } };
        rotate(matrix);
        assertArrayEquals(expected, matrix);
    }

    @Test
    @DisplayName("Extreme values -1000 and 1000 are moved like any other value")
    void boundaryValues_rotatedClockwise() {
        int[][] matrix = { { -1000, 0, 1000 }, { 5, -5, 7 }, { -3, 2, -1 } };
        int[][] expected = { { -3, 5, -1000 }, { 2, -5, 0 }, { -1, 7, 1000 } };
        rotate(matrix);
        assertArrayEquals(expected, matrix);
    }

    @Test
    @DisplayName("The same row arrays are reused, proving the rotation happens in place")
    void rowArrays_reusedInPlace() {
        int[][] matrix = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };
        int[] firstRow = matrix[0];
        int[] lastRow = matrix[2];
        rotate(matrix);
        assertSame(firstRow, matrix[0], "row 0 was replaced by a newly allocated array");
        assertSame(lastRow, matrix[2], "row 2 was replaced by a newly allocated array");
    }
}