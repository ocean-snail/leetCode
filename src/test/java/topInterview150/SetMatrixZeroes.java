package topInterview150;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Given an m x n integer matrix matrix, if an element is 0, set its entire row
 * and column to 0's.
 * 
 * You must do it in place.
 * 
 * 
 * ? Example 1:
 * 
 * 
 * Input: matrix = [[1,1,1],[1,0,1],[1,1,1]]
 * Output: [[1,0,1],[0,0,0],[1,0,1]]
 * 
 * ? Example 2:
 * 
 * 
 * Input: matrix = [[0,1,2,0],[3,4,5,2],[1,3,1,5]]
 * Output: [[0,0,0,0],[0,4,5,0],[0,3,1,0]]
 * 
 * 
 * ! Constraints:
 * 
 * m == matrix.length
 * n == matrix[0].length
 * 1 <= m, n <= 200
 * -231 <= matrix[i][j] <= 231 - 1
 * 
 * 
 * * Follow up:
 * 
 * A straightforward solution using O(mn) space is probably a bad idea.
 * A simple improvement uses O(m + n) space, but still not the best solution.
 * Could you devise a constant space solution?
 */

public class SetMatrixZeroes {

    // ------------------------------------------------------------------
    // Solution
    // ------------------------------------------------------------------

    /**
     * Sets every row and every column that contains a zero to all zeros, in place.
     *
     * * Time: O(m * n) - the border scan touches m + n cells, and the marking pass
     * and the applying
     * pass each visit every inner cell exactly once.
     * * Space: O(1) - only two boolean flags and loop indices are allocated; the
     * marker bits are
     * stored inside the matrix that the caller already owns.
     *
     * @param matrix the m x n matrix to modify in place
     */
    public void setZeroes(int[][] matrix) {
        int rowCount = matrix.length;
        int columnCount = matrix[0].length;

        boolean firstRowHasZero = false;
        for (int column = 0; column < columnCount; column++) {
            if (matrix[0][column] == 0) {
                firstRowHasZero = true;
                break;
            }
        }
        boolean firstColumnHasZero = false;
        for (int row = 0; row < rowCount; row++) {
            if (matrix[row][0] == 0) {
                firstColumnHasZero = true;
                break;
            }
        }
        for (int row = 1; row < rowCount; row++) {
            for (int column = 1; column < columnCount; column++) {
                if (matrix[row][column] == 0) {
                    matrix[row][0] = 0;
                    matrix[0][column] = 0;
                }
            }
        }

        for (int row = 1; row < rowCount; row++) {
            for (int column = 1; column < columnCount; column++) {
                if (matrix[row][0] == 0 || matrix[0][column] == 0) {
                    matrix[row][column] = 0;
                }
            }
        }

        if (firstRowHasZero) {
            for (int column = 0; column < columnCount; column++) {
                matrix[0][column] = 0;
            }
        }
        if (firstColumnHasZero) {
            for (int row = 0; row < rowCount; row++) {
                matrix[row][0] = 0;
            }
        }
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------

    @Test
    @DisplayName("LeetCode example 1: the single interior zero clears its row and its column")
    void leetCodeExampleOne_clearsCrossThroughTheZero() {
        int[][] matrix = { { 1, 1, 1 }, { 1, 0, 1 }, { 1, 1, 1 } };
        int[][] expected = { { 1, 0, 1 }, { 0, 0, 0 }, { 1, 0, 1 } };
        setZeroes(matrix);
        assertArrayEquals(expected, matrix);
    }

    @Test
    @DisplayName("LeetCode example 2: two zeros in row 0 clear that row and both of their columns")
    void leetCodeExampleTwo_clearsTwoColumnsAndTheFirstRow() {
        int[][] matrix = { { 0, 1, 2, 0 }, { 3, 4, 5, 2 }, { 1, 3, 1, 5 } };
        int[][] expected = { { 0, 0, 0, 0 }, { 0, 4, 5, 0 }, { 0, 3, 1, 0 } };
        setZeroes(matrix);
        assertArrayEquals(expected, matrix);
    }

    @Test
    @DisplayName("A 1x1 matrix holding zero stays zero")
    void singleCellHoldingZero_staysZero() {
        int[][] matrix = { { 0 } };
        int[][] expected = { { 0 } };
        setZeroes(matrix);
        assertArrayEquals(expected, matrix);
    }

    @Test
    @DisplayName("A 1x1 matrix holding a non-zero value is left untouched")
    void singleCellHoldingNonZero_staysUnchanged() {
        int[][] matrix = { { 7 } };
        int[][] expected = { { 7 } };
        setZeroes(matrix);
        assertArrayEquals(expected, matrix);
    }

    @Test
    @DisplayName("A single row containing one zero becomes all zeros")
    void singleRowWithOneZero_becomesAllZeros() {
        int[][] matrix = { { 4, 0, 9, 2 } };
        int[][] expected = { { 0, 0, 0, 0 } };
        setZeroes(matrix);
        assertArrayEquals(expected, matrix);
    }

    @Test
    @DisplayName("A single column containing one zero becomes all zeros")
    void singleColumnWithOneZero_becomesAllZeros() {
        int[][] matrix = { { 4 }, { 0 }, { 9 } };
        int[][] expected = { { 0 }, { 0 }, { 0 } };
        setZeroes(matrix);
        assertArrayEquals(expected, matrix);
    }

    @Test
    @DisplayName("A matrix without any zero is left untouched")
    void matrixWithoutZero_staysUnchanged() {
        int[][] matrix = { { 1, 2 }, { 3, 4 }, { 5, 6 } };
        int[][] expected = { { 1, 2 }, { 3, 4 }, { 5, 6 } };
        setZeroes(matrix);
        assertArrayEquals(expected, matrix);
    }

    @Test
    @DisplayName("A matrix that is already all zeros stays all zeros")
    void matrixOfAllZeros_staysAllZeros() {
        int[][] matrix = { { 0, 0 }, { 0, 0 } };
        int[][] expected = { { 0, 0 }, { 0, 0 } };
        setZeroes(matrix);
        assertArrayEquals(expected, matrix);
    }

    @Test
    @DisplayName("A zero at the top-left corner clears both the first row and the first column")
    void zeroAtTopLeftCorner_clearsFirstRowAndFirstColumn() {
        int[][] matrix = { { 0, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };
        int[][] expected = { { 0, 0, 0 }, { 0, 5, 6 }, { 0, 8, 9 } };
        setZeroes(matrix);
        assertArrayEquals(expected, matrix);
    }

    @Test
    @DisplayName("A zero inside the first row but not at the corner clears its column and the first row")
    void zeroInsideFirstRowOnly_clearsItsColumnAndTheFirstRow() {
        int[][] matrix = { { 1, 0, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };
        int[][] expected = { { 0, 0, 0 }, { 4, 0, 6 }, { 7, 0, 9 } };
        setZeroes(matrix);
        assertArrayEquals(expected, matrix);
    }

    @Test
    @DisplayName("A zero inside the first column but not at the corner clears its row and the first column")
    void zeroInsideFirstColumnOnly_clearsItsRowAndTheFirstColumn() {
        int[][] matrix = { { 1, 2, 3 }, { 0, 5, 6 }, { 7, 8, 9 } };
        int[][] expected = { { 0, 2, 3 }, { 0, 0, 0 }, { 0, 8, 9 } };
        setZeroes(matrix);
        assertArrayEquals(expected, matrix);
    }

    @Test
    @DisplayName("Integer.MIN_VALUE and Integer.MAX_VALUE survive when their row and column hold no zero")
    void extremeIntegerValues_arePreserved() {
        int[][] matrix = { { Integer.MIN_VALUE, Integer.MAX_VALUE, 3 }, { 4, 5, 6 }, { 7, 8, 0 } };
        int[][] expected = { { Integer.MIN_VALUE, Integer.MAX_VALUE, 0 }, { 4, 5, 0 }, { 0, 0, 0 } };
        setZeroes(matrix);
        assertArrayEquals(expected, matrix);
    }
}