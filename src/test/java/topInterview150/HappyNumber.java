package topInterview150;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Write an algorithm to determine if a number n is happy.
 * 
 * A happy number is a number defined by the following process:
 * 
 * Starting with any positive integer, replace the number by the sum of the
 * squares of its digits.
 * Repeat the process until the number equals 1 (where it will stay), or it
 * loops endlessly in a cycle which does not include 1.
 * Those numbers for which this process ends in 1 are happy.
 * Return true if n is a happy number, and false if not.
 * 
 * 
 * ? Example 1:
 * 
 * Input: n = 19
 * Output: true
 * Explanation:
 * 12 + 92 = 82
 * 82 + 22 = 68
 * 62 + 82 = 100
 * 12 + 02 + 02 = 1
 * 
 * ? Example 2:
 * 
 * Input: n = 2
 * Output: false
 * 
 * 
 * ! Constraints:
 * 
 * 1 <= n <= 231 - 1
 * 
 */
public class HappyNumber {

    // ------------------------------------------------------------------
    // Solution
    // ------------------------------------------------------------------

    /**
     * Reports whether repeatedly replacing n by the sum of the squares of its
     * digits reaches 1.
     *
     * * Time: O(log n) - the first squareDigitSum call reads at most the 10 digits
     * of
     * n; the
     * value it returns is at most 730 (reached at n = 1999999999), and every value
     * below 1000 reaches 1 or enters the single cycle 4,16,37,58,89,145,42,20
     * within
     * a bounded number of steps, so the loop itself costs a constant amount of work
     * * Space: O(1) - only the two int variables slow and fast are allocated
     *
     * @param n the starting positive integer, 1 &lt;= n &lt;= 2147483647
     * @return true when the sequence reaches 1, false when it enters a cycle that
     *         excludes 1
     */
    public boolean isHappy(int n) {
        int slow = n;
        int fast = squareDigitSum(n);
        while (fast != 1 && slow != fast) {
            slow = squareDigitSum(slow);
            fast = squareDigitSum(squareDigitSum(fast));
        }
        return fast == 1;
    }

    /**
     * Adds the square of every decimal digit of value.
     *
     * Time: O(log value) - one iteration per decimal digit, at most 10 for an int
     * Space: O(1) - two int accumulators
     *
     * @param value a non-negative integer
     * @return the sum of the squares of the decimal digits of value
     */
    public int squareDigitSum(int value) {
        int sum = 0;
        int rest = value;
        while (rest > 0) {
            int digit = rest % 10;
            sum += digit * digit;
            rest /= 10;
        }
        return sum;
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------

    @Test
    @DisplayName("The chain 19 -> 82 -> 68 -> 100 -> 1 reaches one, so 19 is happy")
    void nineteen_returnsTrue() {
        assertTrue(isHappy(19));
    }

    @Test
    @DisplayName("The chain from 2 enters the cycle that excludes one, so 2 is not happy")
    void two_returnsFalse() {
        assertFalse(isHappy(2));
    }

    @Test
    @DisplayName("The smallest allowed input 1 is already one, so no step is needed")
    void smallestAllowedInput_returnsTrue() {
        assertTrue(isHappy(1));
    }

    @Test
    @DisplayName("Every member of the cycle 4,16,37,58,89,145,42,20 is not happy")
    void everyCycleMember_returnsFalse() {
        int[] cycle = { 4, 16, 37, 58, 89, 145, 42, 20 };
        for (int member : cycle) {
            assertFalse(isHappy(member), "expected " + member + " to be unhappy");
        }
    }

    @Test
    @DisplayName("The first ten happy numbers 1,7,10,13,19,23,28,31,32,44 all return true")
    void firstTenHappyNumbers_returnTrue() {
        int[] happy = { 1, 7, 10, 13, 19, 23, 28, 31, 32, 44 };
        for (int value : happy) {
            assertTrue(isHappy(value), "expected " + value + " to be happy");
        }
    }

    @Test
    @DisplayName("Trailing zero digits contribute nothing, so 100 and 10 both reach one")
    void valuesWithTrailingZeroDigits_returnTrue() {
        assertTrue(isHappy(100));
        assertTrue(isHappy(10));
    }

    @Test
    @DisplayName("The constraint ceiling 2147483647 is not happy and causes no overflow")
    void constraintCeiling_returnsFalse() {
        assertEquals(260, squareDigitSum(2147483647));
        assertFalse(isHappy(2147483647));
    }

    @Test
    @DisplayName("squareDigitSum adds squares digit by digit for one, two and three digit values")
    void squareDigitSum_addsSquaresOfEveryDigit() {
        assertEquals(1, squareDigitSum(1));
        assertEquals(82, squareDigitSum(19));
        assertEquals(1, squareDigitSum(100));
        assertEquals(243, squareDigitSum(999));
    }
}