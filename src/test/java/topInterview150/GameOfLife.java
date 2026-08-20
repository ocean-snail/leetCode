package topInterview150;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * According to Wikipedia's article: "The Game of Life, also known simply as
 * Life, is a cellular automaton devised by the British mathematician John
 * Horton Conway in 1970."
 * 
 * The board is made up of an m x n grid of cells, where each cell has an
 * initial state: live (represented by a 1) or dead (represented by a 0). Each
 * cell interacts with its eight neighbors (horizontal, vertical, diagonal)
 * using the following four rules (taken from the above Wikipedia article):
 * 
 * Any live cell with fewer than two live neighbors dies as if caused by
 * under-population.
 * Any live cell with two or three live neighbors lives on to the next
 * generation.
 * Any live cell with more than three live neighbors dies, as if by
 * over-population.
 * Any dead cell with exactly three live neighbors becomes a live cell, as if by
 * reproduction.
 * The next state of the board is determined by applying the above rules
 * simultaneously to every cell in the current state of the m x n grid board. In
 * this process, births and deaths occur simultaneously.
 * 
 * Given the current state of the board, update the board to reflect its next
 * state.
 * 
 * Note that you do not need to return anything.
 * 
 * 
 * ? Example 1:
 * 
 * 
 * Input: board = [[0,1,0],[0,0,1],[1,1,1],[0,0,0]]
 * Output: [[0,0,0],[1,0,1],[0,1,1],[0,1,0]]
 * Example 2:
 * 
 * 
 * Input: board = [[1,1],[1,0]]
 * Output: [[1,1],[1,1]]
 * 
 * 
 * ! Constraints:
 * 
 * m == board.length
 * n == board[i].length
 * 1 <= m, n <= 25
 * board[i][j] is 0 or 1.
 * 
 * 
 * * Follow up:
 * 
 * Could you solve it in-place? Remember that the board needs to be updated
 * simultaneously: You cannot update some cells first and then use their updated
 * values to update other cells.
 * In this question, we represent the board using a 2D array. In principle, the
 * board is infinite, which would cause problems when the active area encroaches
 * upon the border of the array (i.e., live cells reach the border). How would
 * you address these problems?
 * 
 */
public class GameOfLife {

    // ------------------------------------------------------------------
    // Solution
    // ------------------------------------------------------------------

    /**
     * Advances the board by exactly one generation, in place.
     *
     * * Time: O(m * n) - two passes over m * n cells, each cell inspecting at most
     * 8
     * fixed offsets.
     * * Space: O(1) - the next state is stored in bit 1 of the existing cells, no
     * auxiliary grid.
     *
     * @param board the m x n grid whose cells hold 0 (dead) or 1 (live); mutated in
     *              place
     */
    public void gameOfLife(int[][] board) {
        int rows = board.length;
        int cols = board[0].length;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int liveNeighbors = countLiveNeighbors(board, row, col);
                int currentlyLive = board[row][col] & 1;
                boolean nextLive = liveNeighbors == 3 || (currentlyLive == 1 && liveNeighbors == 2);
                if (nextLive) {
                    board[row][col] |= 2;
                }
            }
        }
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                board[row][col] >>= 1;
            }
        }
    }

    /**
     * Counts the live cells among the eight neighbors of one cell, reading only bit
     * 0.
     *
     * * Time: O(1) - nine offsets are examined and one of them is skipped.
     * * Space: O(1) - four int locals.
     *
     * @param board the grid being scanned, possibly already carrying next-state
     *              bits
     * @param row   the row index of the cell whose neighbors are counted
     * @param col   the column index of the cell whose neighbors are counted
     * @return the number of neighbors that were live in the current generation
     */
    private int countLiveNeighbors(int[][] board, int row, int col) {
        int rows = board.length;
        int cols = board[0].length;
        int count = 0;
        for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
            for (int colOffset = -1; colOffset <= 1; colOffset++) {
                if (rowOffset == 0 && colOffset == 0) {
                    continue;
                }
                int neighborRow = row + rowOffset;
                int neighborCol = col + colOffset;
                if (neighborRow < 0 || neighborRow >= rows || neighborCol < 0 || neighborCol >= cols) {
                    continue;
                }
                count += board[neighborRow][neighborCol] & 1;
            }
        }
        return count;
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------

    @Test
    @DisplayName("Example 1: the 4x3 board advances to the documented next state")
    void example1_matchesExpectedNextState() {
        int[][] board = { { 0, 1, 0 }, { 0, 0, 1 }, { 1, 1, 1 }, { 0, 0, 0 } };
        int[][] expected = { { 0, 0, 0 }, { 1, 0, 1 }, { 0, 1, 1 }, { 0, 1, 0 } };
        gameOfLife(board);
        assertArrayEquals(expected, board);
    }

    @Test
    @DisplayName("Example 2: the 2x2 board becomes fully live")
    void example2_matchesExpectedNextState() {
        int[][] board = { { 1, 1 }, { 1, 0 } };
        int[][] expected = { { 1, 1 }, { 1, 1 } };
        gameOfLife(board);
        assertArrayEquals(expected, board);
    }

    @Test
    @DisplayName("A single live cell on a 1x1 board dies of under-population")
    void singleLiveCell_diesFromUnderPopulation() {
        int[][] board = { { 1 } };
        int[][] expected = { { 0 } };
        gameOfLife(board);
        assertArrayEquals(expected, board);
    }

    @Test
    @DisplayName("The 2x2 block still life is unchanged after one generation")
    void blockPattern_remainsUnchanged() {
        int[][] board = { { 0, 0, 0, 0 }, { 0, 1, 1, 0 }, { 0, 1, 1, 0 }, { 0, 0, 0, 0 } };
        int[][] expected = { { 0, 0, 0, 0 }, { 0, 1, 1, 0 }, { 0, 1, 1, 0 }, { 0, 0, 0, 0 } };
        gameOfLife(board);
        assertArrayEquals(expected, board);
    }

    @Test
    @DisplayName("The blinker rotates after one generation and returns after two")
    void blinkerPattern_returnsToStartAfterTwoGenerations() {
        int[][] board = { { 0, 0, 0 }, { 1, 1, 1 }, { 0, 0, 0 } };
        int[][] afterOne = { { 0, 1, 0 }, { 0, 1, 0 }, { 0, 1, 0 } };
        int[][] afterTwo = { { 0, 0, 0 }, { 1, 1, 1 }, { 0, 0, 0 } };
        gameOfLife(board);
        assertArrayEquals(afterOne, board);
        gameOfLife(board);
        assertArrayEquals(afterTwo, board);
    }

    @Test
    @DisplayName("A board with no live cells stays empty")
    void allDeadBoard_staysAllDead() {
        int[][] board = { { 0, 0, 0 }, { 0, 0, 0 }, { 0, 0, 0 } };
        int[][] expected = { { 0, 0, 0 }, { 0, 0, 0 }, { 0, 0, 0 } };
        gameOfLife(board);
        assertArrayEquals(expected, board);
    }

    @Test
    @DisplayName("On a fully live 3x3 board only the four corners survive")
    void fullyLiveBoard_onlyCornersSurvive() {
        int[][] board = { { 1, 1, 1 }, { 1, 1, 1 }, { 1, 1, 1 } };
        int[][] expected = { { 1, 0, 1 }, { 0, 0, 0 }, { 1, 0, 1 } };
        gameOfLife(board);
        assertArrayEquals(expected, board);
    }

    @Test
    @DisplayName("The caller's array object and its row arrays are reused, not replaced")
    void inputArrayIsMutatedInPlace_sameReferencesRetained() {
        int[][] board = { { 0, 1, 0 }, { 0, 0, 1 }, { 1, 1, 1 }, { 0, 0, 0 } };
        int[][] outerBefore = board;
        int[] firstRowBefore = board[0];
        gameOfLife(board);
        assertSame(outerBefore, board, "the outer array must not be replaced");
        assertSame(firstRowBefore, board[0], "row arrays must not be replaced");
        for (int[] row : board) {
            for (int value : row) {
                assertTrue(value == 0 || value == 1, "cell values must be 0 or 1, saw " + value);
            }
        }
    }
}