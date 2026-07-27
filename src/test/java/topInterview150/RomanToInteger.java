package topInterview150;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Roman numerals are represented by seven different symbols: I, V, X, L, C, D
 * and M.
 * 
 * Symbol Value
 * I 1
 * V 5
 * X 10
 * L 50
 * C 100
 * D 500
 * M 1000
 * 
 * For example, 2 is written as II in Roman numeral, just two ones added
 * together. 12 is written as XII, which is simply X + II. The number 27 is
 * written as XXVII, which is XX + V + II.
 * 
 * Roman numerals are usually written largest to smallest from left to right.
 * However, the numeral for four is not IIII. Instead, the number four is
 * written as IV. Because the one is before the five we subtract it making four.
 * The same principle applies to the number nine, which is written as IX. There
 * are six instances where subtraction is used:
 * 
 * I can be placed before V (5) and X (10) to make 4 and 9.
 * X can be placed before L (50) and C (100) to make 40 and 90.
 * C can be placed before D (500) and M (1000) to make 400 and 900.
 * Given a roman numeral, convert it to an integer.
 * 
 * 
 * ? Example 1:
 * 
 * Input: s = "III"
 * Output: 3
 * Explanation: III = 3.
 * 
 * ? Example 2:
 * 
 * Input: s = "LVIII"
 * Output: 58
 * Explanation: L = 50, V= 5, III = 3.
 * 
 * ? Example 3:
 * 
 * Input: s = "MCMXCIV"
 * Output: 1994
 * Explanation: M = 1000, CM = 900, XC = 90 and IV = 4.
 * 
 * 
 * ! Constraints:
 * 
 * 1 <= s.length <= 15
 * s contains only the characters ('I', 'V', 'X', 'L', 'C', 'D', 'M').
 * It is guaranteed that s is a valid roman numeral in the range [1, 3999].
 */

public class RomanToInteger {

    // ------------------------------------------------------------------
    // Solution
    // ------------------------------------------------------------------

    /**
     * Converts a Roman numeral to the integer it denotes.
     * Time: O(n) - exactly n iterations over the n characters, O(1) work per
     * iteration, no early exit.
     * Space: O(1) - two int locals; the switch table is compiled in, nothing is
     * allocated.
     *
     * @param s a valid Roman numeral in the range [1, 3999]
     * @return the integer value of {@code s}
     */
    public int romanToInt(String s) {
        int total = 0;
        for (int i = 0; i < s.length(); i++) {
            int current = valueOf(s.charAt(i));
            if (i + 1 < s.length() && current < valueOf(s.charAt(i + 1))) {
                total -= current;
            } else {
                total += current;
            }
        }
        return total;
    }

    /**
     * Maps one Roman symbol to its value.
     * Time: O(1) - a seven-way switch over a fixed alphabet, no hashing and no
     * boxing.
     * Space: O(1) - no allocation.
     *
     * @param symbol one of I, V, X, L, C, D, M
     * @return the value the symbol denotes
     */
    public int valueOf(char symbol) {
        return switch (symbol) {
            case 'I' -> 1;
            case 'V' -> 5;
            case 'X' -> 10;
            case 'L' -> 50;
            case 'C' -> 100;
            case 'D' -> 500;
            case 'M' -> 1000;
            default -> throw new IllegalArgumentException("Not a Roman symbol: " + symbol);
        };
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------
    @Test
    @DisplayName("single symbol at the minimum length returns that symbol's value")
    void singleSymbol_returnsOne() {
        assertEquals(1, romanToInt("I"));
    }

    @Test
    @DisplayName("purely additive numeral returns the sum of its symbols")
    void additiveOnly_returnsThree() {
        assertEquals(3, romanToInt("III"));
    }

    @Test
    @DisplayName("descending symbols with a repeated tail return their sum")
    void descendingWithRepeats_returnsFiftyEight() {
        assertEquals(58, romanToInt("LVIII"));
    }

    @Test
    @DisplayName("each of the six subtractive forms returns its encoded value")
    void allSubtractiveForms_returnEncodedValues() {
        assertEquals(4, romanToInt("IV"));
        assertEquals(9, romanToInt("IX"));
        assertEquals(40, romanToInt("XL"));
        assertEquals(90, romanToInt("XC"));
        assertEquals(400, romanToInt("CD"));
        assertEquals(900, romanToInt("CM"));
    }

    @Test
    @DisplayName("alternating subtractive and additive groups return the combined value")
    void mixedSubtractiveAndAdditive_returnsNineteenNinetyFour() {
        assertEquals(1994, romanToInt("MCMXCIV"));
    }

    @Test
    @DisplayName("largest value in range returns 3999")
    void maximumValue_returnsThreeThousandNineHundredNinetyNine() {
        assertEquals(3999, romanToInt("MMMCMXCIX"));
    }

    @Test
    @DisplayName("longest numeral in range, 15 characters, returns 3888")
    void longestNumeral_returnsThreeThousandEightHundredEightyEight() {
        assertEquals(3888, romanToInt("MMMDCCCLXXXVIII"));
    }
}