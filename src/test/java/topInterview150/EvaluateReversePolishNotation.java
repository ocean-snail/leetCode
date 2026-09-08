package topInterview150;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * You are given an array of strings tokens that represents an arithmetic
 * expression in a Reverse Polish Notation.
 * 
 * Evaluate the expression. Return an integer that represents the value of the
 * expression.
 * 
 * Note that:
 * 
 * The valid operators are '+', '-', '*', and '/'.
 * Each operand may be an integer or another expression.
 * The division between two integers always truncates toward zero.
 * There will not be any division by zero.
 * The input represents a valid arithmetic expression in a reverse polish
 * notation.
 * The answer and all the intermediate calculations can be represented in a
 * 32-bit integer.
 * 
 * 
 * ? Example 1:
 * 
 * Input: tokens = ["2","1","+","3","*"]
 * Output: 9
 * Explanation: ((2 + 1) * 3) = 9
 * 
 * ? Example 2:
 * 
 * Input: tokens = ["4","13","5","/","+"]
 * Output: 6
 * Explanation: (4 + (13 / 5)) = 6
 * 
 * ? Example 3:
 * 
 * Input: tokens = ["10","6","9","3","+","-11","*","/","*","17","+","5","+"]
 * Output: 22
 * Explanation: ((10 * (6 / ((9 + 3) * -11))) + 17) + 5
 * = ((10 * (6 / (12 * -11))) + 17) + 5
 * = ((10 * (6 / -132)) + 17) + 5
 * = ((10 * 0) + 17) + 5
 * = (0 + 17) + 5
 * = 17 + 5
 * = 22
 * 
 * 
 * ! Constraints:
 * 
 * 1 <= tokens.length <= 104
 * tokens[i] is either an operator: "+", "-", "*", or "/", or an integer in the
 * range [-200, 200].
 */
public class EvaluateReversePolishNotation {

    // ------------------------------------------------------------------
    // Solution
    // ------------------------------------------------------------------

    /**
     * Evaluates the Reverse Polish Notation expression held in {@code tokens}.
     *
     * * Time: O(n) - one pass over the n tokens, each token costing a push or a
     * pop-pop-push.
     * * Space: O(n) - one int array of (n + 1) / 2 slots, the number of operands an
     * n-token
     * expression can contain.
     *
     * @param tokens a valid RPN expression, each entry either "+", "-", "*", "/" or
     *               an integer
     * @return the value of the expression
     */
    public int evalRPN(String[] tokens) {
        int[] stack = new int[(tokens.length + 1) / 2];
        int top = 0;
        for (String token : tokens) {
            if (isOperator(token)) {
                int right = stack[--top];
                int left = stack[--top];
                stack[top++] = apply(token.charAt(0), left, right);
            } else {
                stack[top++] = Integer.parseInt(token);
            }
        }
        return stack[0];
    }

    /**
     * * Time: O(1) - a length test followed by at most four character comparisons.
     * * Space: O(1) - no allocation.
     *
     * @param token one entry of the expression
     * @return true when the token is one of the four operators
     */
    private boolean isOperator(String token) {
        if (token.length() != 1) {
            return false;
        }
        char symbol = token.charAt(0);
        return symbol == '+' || symbol == '-' || symbol == '*' || symbol == '/';
    }

    /**
     * * Time: O(1) - one switch over four operators and one arithmetic instruction.
     * * Space: O(1) - no allocation.
     *
     * @param operator one of '+', '-', '*', '/'
     * @param left     the operand that was pushed first
     * @param right    the operand that was pushed second
     * @return the result of applying the operator to the two operands in that order
     */
    private int apply(char operator, int left, int right) {
        return switch (operator) {
            case '+' -> left + right;
            case '-' -> left - right;
            case '*' -> left * right;
            default -> left / right;
        };
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------

    @Test
    @DisplayName("The first sample expression 2 1 + 3 * evaluates to 9")
    void sumMultipliedByAnOperand_returnsNine() {
        assertEquals(9, evalRPN(new String[] { "2", "1", "+", "3", "*" }));
    }

    @Test
    @DisplayName("The second sample expression 4 13 5 / + evaluates to 6")
    void quotientAddedToAnOperand_returnsSix() {
        assertEquals(6, evalRPN(new String[] { "4", "13", "5", "/", "+" }));
    }

    @Test
    @DisplayName("The third sample expression with a negative operand evaluates to 22")
    void nestedExpressionWithNegativeOperand_returnsTwentyTwo() {
        String[] tokens = { "10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+" };
        assertEquals(22, evalRPN(tokens));
    }

    @Test
    @DisplayName("A single operand token is returned unchanged")
    void singleOperandToken_returnsThatOperand() {
        assertEquals(42, evalRPN(new String[] { "42" }));
    }

    @Test
    @DisplayName("A single negative operand token is returned unchanged")
    void singleNegativeOperandToken_returnsThatOperand() {
        assertEquals(-200, evalRPN(new String[] { "-200" }));
    }

    @Test
    @DisplayName("Subtraction uses the earlier operand as the left side, so 2 3 - is -1")
    void subtraction_appliesTheEarlierOperandOnTheLeft() {
        assertEquals(-1, evalRPN(new String[] { "2", "3", "-" }));
    }

    @Test
    @DisplayName("Division of a negative dividend truncates toward zero, so -7 3 / is -2")
    void divisionOfNegativeDividend_truncatesTowardZero() {
        assertEquals(-2, evalRPN(new String[] { "-7", "3", "/" }));
    }

    @Test
    @DisplayName("Division by a negative divisor truncates toward zero, so 7 -3 / is -2")
    void divisionByNegativeDivisor_truncatesTowardZero() {
        assertEquals(-2, evalRPN(new String[] { "7", "-3", "/" }));
    }

    @Test
    @DisplayName("A dividend smaller than its divisor yields 0 rather than a fraction")
    void dividendSmallerThanDivisor_returnsZero() {
        assertEquals(0, evalRPN(new String[] { "1", "2", "/" }));
    }

    @Test
    @DisplayName("A negative operand that starts with the minus character is read as a number")
    void negativeOperandStartingWithMinus_isReadAsANumber() {
        assertEquals(-6, evalRPN(new String[] { "5", "-11", "+" }));
    }

    @Test
    @DisplayName("A product built from four operands of 200 stays inside the 32-bit range")
    void largestReachableProduct_staysWithinIntRange() {
        String[] tokens = { "200", "200", "*", "200", "*", "200", "*" };
        assertEquals(1_600_000_000, evalRPN(tokens));
    }

    @Test
    @DisplayName("An expression whose operands all precede its operators fills the operand buffer exactly")
    void maximumStackDepthExpression_fillsTheOperandBuffer() {
        int operandCount = 5000;
        String[] tokens = new String[2 * operandCount - 1];
        Arrays.fill(tokens, 0, operandCount, "1");
        Arrays.fill(tokens, operandCount, tokens.length, "+");
        assertEquals(operandCount, evalRPN(tokens));
    }
}