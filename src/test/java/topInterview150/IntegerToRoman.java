package topInterview150;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Seven different symbols represent Roman numerals with the following values:
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
 * Roman numerals are formed by appending the conversions of decimal place
 * values from highest to lowest. Converting a decimal place value into a Roman
 * numeral has the following rules:
 * 
 * If the value does not start with 4 or 9, select the symbol of the maximal
 * value that can be subtracted from the input, append that symbol to the
 * result, subtract its value, and convert the remainder to a Roman numeral.
 * If the value starts with 4 or 9 use the subtractive form representing one
 * symbol subtracted from the following symbol, for example, 4 is 1 (I) less
 * than 5 (V): IV and 9 is 1 (I) less than 10 (X): IX. Only the following
 * subtractive forms are used: 4 (IV), 9 (IX), 40 (XL), 90 (XC), 400 (CD) and
 * 900 (CM).
 * Only powers of 10 (I, X, C, M) can be appended consecutively at most 3 times
 * to represent multiples of 10. You cannot append 5 (V), 50 (L), or 500 (D)
 * multiple times. If you need to append a symbol 4 times use the subtractive
 * form.
 * Given an integer, convert it to a Roman numeral.
 * 
 * 
 * ? Example 1:
 * 
 * Input: num = 3749
 * 
 * Output: "MMMDCCXLIX"
 * 
 * Explanation:
 * 
 * 3000 = MMM as 1000 (M) + 1000 (M) + 1000 (M)
 * 700 = DCC as 500 (D) + 100 (C) + 100 (C)
 * 40 = XL as 10 (X) less of 50 (L)
 * 9 = IX as 1 (I) less of 10 (X)
 * Note: 49 is not 1 (I) less of 50 (L) because the conversion is based on
 * decimal places
 * 
 * ? Example 2:
 * 
 * Input: num = 58
 * 
 * Output: "LVIII"
 * 
 * Explanation:
 * 
 * 50 = L
 * 8 = VIII
 * 
 * ? Example 3:
 * 
 * Input: num = 1994
 * 
 * Output: "MCMXCIV"
 * 
 * Explanation:
 * 
 * 1000 = M
 * 900 = CM
 * 90 = XC
 * 4 = IV
 * 
 * 
 * ! Constraints:
 * 
 * 1 <= num <= 3999
 */
public class IntegerToRoman {

    // ------------------------------------------------------------------
    // Solution
    // ------------------------------------------------------------------
    private static final int[] VALUES = { 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1 };

    private static final String[] SYMBOLS = { "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I" };

    /**
     * Converts an integer to its Roman numeral text.
     * * Time: O(1) - the table length is fixed at 13 and, because num <= 3999, at
     * most 15 characters are appended.
     * * Space: O(1) - one StringBuilder of at most 15 characters; the tables are
     * static, so they are shared, not rebuilt.
     *
     * @param num integer to convert, 1 <= num <= 3999
     * @return Roman numeral text for num
     */
    public String intToRoman(int num) {
        StringBuilder roman = new StringBuilder();
        for (int i = 0; i < VALUES.length; i++) {
            while (num >= VALUES[i]) {
                roman.append(SYMBOLS[i]);
                num -= VALUES[i];
            }
        }
        return roman.toString();
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------
    @Test
    @DisplayName("smallest legal input 1 becomes the single symbol I")
    void minimumInput_returnsI() {
        assertEquals("I", intToRoman(1));
    }

    @Test
    @DisplayName("3 uses the maximum legal run of one symbol")
    void threeUnits_returnsIII() {
        assertEquals("III", intToRoman(3));
    }

    @Test
    @DisplayName("4 uses the subtractive form IV, not IIII")
    void subtractiveFour_returnsIV() {
        assertEquals("IV", intToRoman(4));
    }

    @Test
    @DisplayName("9 uses the subtractive form IX, not VIIII")
    void subtractiveNine_returnsIX() {
        assertEquals("IX", intToRoman(9));
    }

    @Test
    @DisplayName("the four remaining subtractive forms are produced for 40, 90, 400 and 900")
    void subtractiveTensAndHundreds_returnXlXcCdCm() {
        assertEquals("XL", intToRoman(40));
        assertEquals("XC", intToRoman(90));
        assertEquals("CD", intToRoman(400));
        assertEquals("CM", intToRoman(900));
    }

    @Test
    @DisplayName("58 mixes a half-symbol L with an additive tail VIII")
    void fiftyEight_returnsLviii() {
        assertEquals("LVIII", intToRoman(58));
    }

    @Test
    @DisplayName("3749 combines a triple M with two different subtractive forms")
    void exampleThreeThousandSevenHundredFortyNine_returnsMmmdccxlix() {
        assertEquals("MMMDCCXLIX", intToRoman(3749));
    }

    @Test
    @DisplayName("1994 chains three subtractive forms in a row")
    void exampleNineteenNinetyFour_returnsMcmxciv() {
        assertEquals("MCMXCIV", intToRoman(1994));
    }

    @Test
    @DisplayName("largest legal input 3999 becomes MMMCMXCIX")
    void maximumInput_returnsMmmcmxcix() {
        assertEquals("MMMCMXCIX", intToRoman(3999));
    }

    @Test
    @DisplayName("3888 is the longest possible output and is 15 characters")
    void longestOutput_returnsFifteenCharacters() {
        String roman = intToRoman(3888);
        assertEquals("MMMDCCCLXXXVIII", roman);
        assertEquals(15, roman.length());
    }
}