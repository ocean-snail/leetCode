package topInterview150;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Determine if a 9 x 9 Sudoku board is valid. Only the filled cells need to be
 * validated according to the following rules:
 * 
 * Each row must contain the digits 1-9 without repetition.
 * Each column must contain the digits 1-9 without repetition.
 * Each of the nine 3 x 3 sub-boxes of the grid must contain the digits 1-9
 * without repetition.
 * Note:
 * 
 * A Sudoku board (partially filled) could be valid but is not necessarily
 * solvable.
 * Only the filled cells need to be validated according to the mentioned rules.
 * 
 * 
 * ? Example 1:
 * 
 * Input: board =
 * [["5","3",".",".","7",".",".",".","."]
 * ,["6",".",".","1","9","5",".",".","."]
 * ,[".","9","8",".",".",".",".","6","."]
 * ,["8",".",".",".","6",".",".",".","3"]
 * ,["4",".",".","8",".","3",".",".","1"]
 * ,["7",".",".",".","2",".",".",".","6"]
 * ,[".","6",".",".",".",".","2","8","."]
 * ,[".",".",".","4","1","9",".",".","5"]
 * ,[".",".",".",".","8",".",".","7","9"]]
 * Output: true
 * 
 * ? Example 2:
 * 
 * Input: board =
 * [["8","3",".",".","7",".",".",".","."]
 * ,["6",".",".","1","9","5",".",".","."]
 * ,[".","9","8",".",".",".",".","6","."]
 * ,["8",".",".",".","6",".",".",".","3"]
 * ,["4",".",".","8",".","3",".",".","1"]
 * ,["7",".",".",".","2",".",".",".","6"]
 * ,[".","6",".",".",".",".","2","8","."]
 * ,[".",".",".","4","1","9",".",".","5"]
 * ,[".",".",".",".","8",".",".","7","9"]]
 * Output: false
 * Explanation: Same as Example 1, except with the 5 in the top left corner
 * being modified to 8. Since there are two 8's in the top left 3x3 sub-box, it
 * is invalid.
 * 
 * 
 * ! Constraints:
 * 
 * board.length == 9
 * board[i].length == 9
 * board[i][j] is a digit 1-9 or '.'.
 */

public class ValidSudoku {

    // ------------------------------------------------------------------
    // Solution
    // ------------------------------------------------------------------

    /**
     * Reports whether every filled cell obeys the row, column and 3x3 box rules.
     *
     * * Time: O(n^2) worst case - reads each of the n*n cells once and performs a
     * fixed number
     * of bit operations per cell; O(1) best case - returns on the first repeated
     * digit.
     * * Space: O(n) - three int arrays of length n, one mask per row, column and
     * box.
     *
     * @param board the n x n grid (n = 9), each cell holding a digit '1'-'9' or the
     *              marker '.'
     * @return true when no digit occurs twice inside any row, any column or any 3x3
     *         box
     */
    public boolean isValidSudoku(char[][] board) {
        int[] rowMask = new int[9];
        int[] colMask = new int[9];
        int[] boxMask = new int[9];

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                char cell = board[row][col];
                if (cell == '.') {
                    continue;
                }
                int bit = 1 << (cell - '1');
                int box = (row / 3) * 3 + col / 3;
                if ((rowMask[row] & bit) != 0
                        || (colMask[col] & bit) != 0
                        || (boxMask[box] & bit) != 0) {
                    return false;
                }
                rowMask[row] |= bit;
                colMask[col] |= bit;
                boxMask[box] |= bit;
            }
        }
        return true;
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------

    /**
     * Turns nine 9-character strings into the char[][] shape the solution expects.
     *
     * Time: O(n^2) - copies every one of the n*n characters once.
     * Space: O(n^2) - allocates the n x n result grid.
     *
     * @param rows nine strings of length nine, each character a digit '1'-'9' or
     *             '.'
     * @return the equivalent 9 x 9 char grid
     */
    static char[][] grid(String... rows) {
        char[][] board = new char[9][9];
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                board[row][col] = rows[row].charAt(col);
            }
        }
        return board;
    }

    /**
     * Builds a board of '.' with the given cells filled in.
     *
     * Time: O(n^2) - fills the n*n grid with '.' before writing the requested
     * cells.
     * Space: O(n^2) - allocates the n x n result grid.
     *
     * @param cells triples of row, column and digit character code, laid out end to
     *              end
     * @return the 9 x 9 grid holding those digits and '.' everywhere else
     */
    static char[][] sparse(int... cells) {
        char[][] board = new char[9][9];
        for (char[] row : board) {
            Arrays.fill(row, '.');
        }
        for (int i = 0; i < cells.length; i += 3) {
            board[cells[i]][cells[i + 1]] = (char) cells[i + 2];
        }
        return board;
    }

    @Test
    @DisplayName("LeetCode example 1: a valid partially filled board returns true")
    void leetCodeExampleOne_returnsTrue() {
        char[][] board = grid(
                "53..7....",
                "6..195...",
                ".98....6.",
                "8...6...3",
                "4..8.3..1",
                "7...2...6",
                ".6....28.",
                "...419..5",
                "....8..79");
        assertTrue(isValidSudoku(board));
    }

    @Test
    @DisplayName("LeetCode example 2: two 8s in the top-left box returns false")
    void leetCodeExampleTwoDuplicateInBox_returnsFalse() {
        char[][] board = grid(
                "83..7....",
                "6..195...",
                ".98....6.",
                "8...6...3",
                "4..8.3..1",
                "7...2...6",
                ".6....28.",
                "...419..5",
                "....8..79");
        assertFalse(isValidSudoku(board));
    }

    @Test
    @DisplayName("A board of only '.' has no filled cell to violate a rule and returns true")
    void allEmptyCells_returnsTrue() {
        assertTrue(isValidSudoku(sparse()));
    }

    @Test
    @DisplayName("Two 5s in the same row but different columns and boxes returns false")
    void duplicateInRowOnly_returnsFalse() {
        assertFalse(isValidSudoku(sparse(0, 0, '5', 0, 4, '5')));
    }

    @Test
    @DisplayName("Two 5s in the same column but different rows and boxes returns false")
    void duplicateInColumnOnly_returnsFalse() {
        assertFalse(isValidSudoku(sparse(0, 0, '5', 4, 0, '5')));
    }

    @Test
    @DisplayName("Two 5s in the same 3x3 box but different rows and columns returns false")
    void duplicateInBoxOnly_returnsFalse() {
        assertFalse(isValidSudoku(sparse(0, 0, '5', 1, 1, '5')));
    }

    @Test
    @DisplayName("The same digit repeated once per box along the diagonal returns true")
    void sameDigitInEveryDiagonalBox_returnsTrue() {
        assertTrue(isValidSudoku(sparse(
                0, 0, '5', 1, 3, '5', 2, 6, '5',
                3, 1, '5', 4, 4, '5', 5, 7, '5',
                6, 2, '5', 7, 5, '5', 8, 8, '5')));
    }

    @Test
    @DisplayName("A fully solved Sudoku returns true")
    void completelyFilledValidBoard_returnsTrue() {
        char[][] board = grid(
                "534678912",
                "672195348",
                "198342567",
                "859761423",
                "426853791",
                "713924856",
                "961537284",
                "287419635",
                "345286179");
        assertTrue(isValidSudoku(board));
    }

    @Test
    @DisplayName("A conflict that only appears at the last cell still returns false")
    void duplicateAtFinalCell_returnsFalse() {
        assertFalse(isValidSudoku(sparse(8, 0, '7', 8, 8, '7')));
    }

    @Test
    @DisplayName("Digits '1' and '9' occupy the lowest and highest mask bits without overlap")
    void extremeDigitsInSameRow_returnsTrue() {
        assertTrue(isValidSudoku(sparse(0, 0, '1', 0, 1, '9')));
    }
}