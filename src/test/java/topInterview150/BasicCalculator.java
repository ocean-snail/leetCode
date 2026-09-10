package topInterview150;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayDeque;
import java.util.Deque;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Given a string s representing a valid expression, implement a basic
 * calculator to evaluate it, and return the result of the evaluation.
 * 
 * Note: You are not allowed to use any built-in function which evaluates
 * strings as mathematical expressions, such as eval().
 * 
 * 
 * ? Example 1:
 * 
 * Input: s = "1 + 1"
 * Output: 2
 * 
 * ? Example 2:
 * 
 * Input: s = " 2-1 + 2 "
 * Output: 3
 * 
 * ? Example 3:
 * 
 * Input: s = "(1+(4+5+2)-3)+(6+8)"
 * Output: 23
 * 
 * 
 * ! Constraints:
 * 
 * 1 <= s.length <= 3 * 105
 * s consists of digits, '+', '-', '(', ')', and ' '.
 * s represents a valid expression.
 * '+' is not used as a unary operation (i.e., "+1" and "+(2 + 3)" is invalid).
 * '-' could be used as a unary operation (i.e., "-1" and "-(2 + 3)" is valid).
 * There will be no two consecutive operators in the input.
 * Every number and running calculation will fit in a signed 32-bit integer.
 * 
 */
public class BasicCalculator {

    // ------------------------------------------------------------------
    // Solution
    // ------------------------------------------------------------------

    /**
     * Evaluates the expression in one left-to-right pass without building a token
     * list.
     *
     * * Time: O(n) - each character is read once, either by the outer loop or by
     * the
     * inner digit
     * loop; every '(' performs two pushes and every ')' performs two pops, all
     * O(1).
     * * Space: O(d) - the stack holds two int values per '(' that is still open, so
     * it reaches
     * 2 * d entries where d is the maximum nesting depth and d &lt;= n / 2.
     *
     * @param s a valid expression built from digits, '+', '-', '(', ')' and ' '
     * @return the value of the expression
     */
    public int calculate(String s) {
        Deque<Integer> stack = new ArrayDeque<>();
        int result = 0;
        int sign = 1;
        int index = 0;
        while (index < s.length()) {
            char c = s.charAt(index);
            if (c >= '0' && c <= '9') {
                int value = 0;
                while (index < s.length() && s.charAt(index) >= '0' && s.charAt(index) <= '9') {
                    value = value * 10 + (s.charAt(index) - '0');
                    index++;
                }
                result += sign * value;
                continue;
            }
            if (c == '+') {
                sign = 1;
            } else if (c == '-') {
                sign = -1;
            } else if (c == '(') {
                stack.push(result);
                stack.push(sign);
                result = 0;
                sign = 1;
            } else if (c == ')') {
                int outerSign = stack.pop();
                int outerResult = stack.pop();
                result = outerResult + outerSign * result;
            }
            index++;
        }
        return result;
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------

    @Test
    @DisplayName("Two single digits joined by '+' evaluate to their sum")
    void singleDigitAddition_returnsSum() {
        assertEquals(2, calculate("1 + 1"));
    }

    @Test
    @DisplayName("Leading and trailing spaces around mixed '+' and '-' do not change the value")
    void surroundingSpacesWithMixedOperators_returnsThree() {
        assertEquals(3, calculate(" 2-1 + 2 "));
    }

    @Test
    @DisplayName("Nested groups from the problem statement evaluate to 23")
    void nestedGroupsFromStatement_returnsTwentyThree() {
        assertEquals(23, calculate("(1+(4+5+2)-3)+(6+8)"));
    }

    @Test
    @DisplayName("A space between every token gives the same value as the compact form")
    void spacesAroundEveryToken_returnsSameValueAsCompactForm() {
        assertEquals(23, calculate(" ( 1 + ( 4 + 5 + 2 ) - 3 ) + ( 6 + 8 ) "));
    }

    @Test
    @DisplayName("An expression that is a single number returns that number")
    void singleNumberWithoutOperator_returnsThatNumber() {
        assertEquals(42, calculate("42"));
    }

    @Test
    @DisplayName("A two-digit left operand keeps its place value instead of splitting into two terms")
    void multiDigitLeftOperand_returnsSumWithPlaceValuePreserved() {
        assertEquals(15, calculate("12+3"));
    }

    @Test
    @DisplayName("Leading zeros inside a literal do not change its value")
    void leadingZerosInLiteral_returnsValueWithoutTheZeros() {
        assertEquals(12, calculate("000012+0"));
    }

    @Test
    @DisplayName("A '-' at the start of the expression negates the number that follows it")
    void unaryMinusBeforeNumber_returnsNegatedNumber() {
        assertEquals(-1, calculate("-1"));
    }

    @Test
    @DisplayName("A '-' in front of a group negates the whole value of that group")
    void unaryMinusBeforeGroup_returnsNegatedGroupValue() {
        assertEquals(-5, calculate("-(2 + 3)"));
    }

    @Test
    @DisplayName("A '-' right after '(' negates only the first term inside that group")
    void unaryMinusAfterOpeningParenthesis_returnsNegativeInnerTerm() {
        assertEquals(2, calculate("(-3+5)"));
    }

    @Test
    @DisplayName("Two nested unary minus signs restore the positive value of the inner group")
    void unaryMinusNestedTwice_returnsPositiveValue() {
        assertEquals(3, calculate("-(-(1+2))"));
    }

    @Test
    @DisplayName("A binary '-' followed by a group that starts with a unary '-' adds the inner value")
    void binaryMinusFollowedByUnaryMinusGroup_returnsSum() {
        assertEquals(3, calculate("1-(-2)"));
    }

    @Test
    @DisplayName("A group subtracted from a number returns the difference")
    void groupSubtractedFromNumber_returnsDifference() {
        assertEquals(5, calculate("10-(3+2)"));
    }

    @Test
    @DisplayName("A '-' in front of a group flips the subtraction that is inside it")
    void minusInFrontOfGroupFlipsInnerSubtraction_returnsThree() {
        assertEquals(3, calculate("2-(5-6)"));
    }

    @Test
    @DisplayName("A group subtracted from another group returns a negative difference")
    void groupSubtractedFromGroup_returnsNegativeDifference() {
        assertEquals(-4, calculate("(1+2)-(3+4)"));
    }

    @Test
    @DisplayName("Redundant nested parentheses return the number they enclose")
    void redundantNestedParentheses_returnsInnerNumber() {
        assertEquals(5, calculate("((((5))))"));
    }

    @Test
    @DisplayName("Operands at the signed 32-bit maximum subtract without overflow")
    void valuesAtSignedIntegerLimit_returnsZero() {
        assertEquals(0, calculate("2147483647-2147483647"));
    }
}