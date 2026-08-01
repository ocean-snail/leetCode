package topInterview150;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * The string "PAYPALISHIRING" is written in a zigzag pattern on a given number
 * of rows like this: (you may want to display this pattern in a fixed font for
 * better legibility)
 * 
 * And then read line by line: "PAHNAPLSIIGYIR"
 * 
 * Write the code that will take a string and make this conversion given a
 * number of rows:
 * 
 * string convert(string s, int numRows);
 * 
 * 
 * ? Example 1:
 * 
 * Input: s = "PAYPALISHIRING", numRows = 3
 * Output: "PAHNAPLSIIGYIR"
 * 
 * ? Example 2:
 * 
 * Input: s = "PAYPALISHIRING", numRows = 4
 * Output: "PINALSIGYAHRPI"
 * 
 * ? Example 3:
 * 
 * Input: s = "A", numRows = 1
 * Output: "A"
 * 
 * 
 * ! Constraints:
 * 
 * 1 <= s.length <= 1000
 * s consists of English letters (lower-case and upper-case), ',' and '.'.
 * 1 <= numRows <= 1000
 */
public class ZigzagConversion {

    // ------------------------------------------------------------------
    // Solution
    // ------------------------------------------------------------------

    /**
     * Distributes the characters of s into numRows builders and concatenates them.
     * * Time: O(n) - the loop runs n times and appends one character per iteration,
     * and the final
     * concatenation copies each of those n characters exactly once.
     * * Space: O(n) - the numRows builders together store exactly n characters, and
     * the result
     * builder stores n more.
     *
     * @param s       the input string, 1 &lt;= s.length() &lt;= 1000
     * @param numRows the number of rows in the layout, 1 &lt;= numRows &lt;= 1000
     * @return the characters of the layout read row by row, top row first
     */
    public String convert(String s, int numRows) {
        if (numRows == 1 || numRows >= s.length()) {
            return s;
        }

        StringBuilder[] rows = new StringBuilder[numRows];
        for (int i = 0; i < numRows; i++) {
            rows[i] = new StringBuilder();
        }

        int row = 0;
        int step = 1;
        for (int i = 0; i < s.length(); i++) {
            rows[row].append(s.charAt(i));
            if (row == 0) {
                step = 1;
            } else if (row == numRows - 1) {
                step = -1;
            }
            row += step;
        }

        StringBuilder result = new StringBuilder(s.length());
        for (StringBuilder rowBuilder : rows) {
            result.append(rowBuilder);
        }
        return result.toString();
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------
    @Test
    @DisplayName("PAYPALISHIRING with 3 rows returns PAHNAPLSIIGYIR")
    void threeRowsExample_returnsRowsConcatenatedTopToBottom() {
        assertEquals("PAHNAPLSIIGYIR", convert("PAYPALISHIRING", 3));
    }

    @Test
    @DisplayName("PAYPALISHIRING with 4 rows returns PINALSIGYAHRPI")
    void fourRowsExample_returnsRowsConcatenatedTopToBottom() {
        assertEquals("PINALSIGYAHRPI", convert("PAYPALISHIRING", 4));
    }

    @Test
    @DisplayName("single character with 1 row returns that character")
    void singleCharacterOneRow_returnsSameString() {
        assertEquals("A", convert("A", 1));
    }

    @Test
    @DisplayName("1 row returns the input unchanged")
    void oneRow_returnsInputUnchanged() {
        assertEquals("PAYPALISHIRING", convert("PAYPALISHIRING", 1));
    }

    @Test
    @DisplayName("2 rows splits the input into even indices then odd indices")
    void twoRows_returnsEvenIndicesThenOddIndices() {
        assertEquals("PYAIHRNAPLSIIG", convert("PAYPALISHIRING", 2));
    }

    @Test
    @DisplayName("numRows equal to the input length returns the input unchanged")
    void numRowsEqualsInputLength_returnsInputUnchanged() {
        assertEquals("ABC", convert("ABC", 3));
    }

    @Test
    @DisplayName("numRows greater than the input length returns the input unchanged")
    void numRowsGreaterThanInputLength_returnsInputUnchanged() {
        assertEquals("ABC", convert("ABC", 10));
    }

    @Test
    @DisplayName("comma and period are placed like any other character")
    void punctuationCharacters_arePlacedLikeLetters() {
        assertEquals("ACE,.,.BD", convert("A,B.C,D.E", 3));
    }

    @Test
    @DisplayName("an input that ends mid-cycle omits the row positions that have no character")
    void lastCycleIncomplete_omitsMissingPositions() {
        assertEquals("ABCED", convert("ABCDE", 4));
    }
}