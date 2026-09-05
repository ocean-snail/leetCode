package topInterview150;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Given a string s containing just the characters '(', ')', '{', '}', '[' and
 * ']', determine if the input string is valid.
 * 
 * An input string is valid if:
 * 
 * 1. Open brackets must be closed by the same type of brackets.
 * 2. Open brackets must be closed in the correct order.
 * 3. Every close bracket has a corresponding open bracket of the same type.
 * 
 * 
 * ? Example 1:
 * 
 * Input: s = "()"
 * 
 * Output: true
 * 
 * ? Example 2:
 * 
 * Input: s = "()[]{}"
 * 
 * Output: true
 * 
 * ? Example 3:
 * 
 * Input: s = "(]"
 * 
 * Output: false
 * 
 * ? Example 4:
 * 
 * Input: s = "([])"
 * 
 * Output: true
 * 
 * ? Example 5:
 * 
 * Input: s = "([)]"
 * 
 * Output: false
 * 
 * 
 * ! Constraints:
 * 
 * 1 <= s.length <= 104
 * s consists of parentheses only '()[]{}'.
 * 
 * 
 */
public class ValidParentheses {

    // ------------------------------------------------------------------
    // Solution
    // ------------------------------------------------------------------

    /**
     * Reports whether every bracket in the string is closed by the same type in the
     * correct order.
     *
     * * Time: O(n) - one pass over the n characters, and each character performs a
     * constant number of
     * array writes, array reads and comparisons.
     * * Space: O(n) - one char array whose length equals the input length, which is
     * the largest stack
     * depth reachable when every character is an opening bracket.
     *
     * @param s a string built only from the six characters '(', ')', '[', ']', '{'
     *          and '}'
     * @return true when the string is a valid bracket sequence, false otherwise
     */
    public boolean isValid(String s) {
        int n = s.length();
        char[] expected = new char[n];
        int top = 0;
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            switch (c) {
                case '(' -> {
                    expected[top] = ')';
                    top++;
                }
                case '[' -> {
                    expected[top] = ']';
                    top++;
                }
                case '{' -> {
                    expected[top] = '}';
                    top++;
                }
                default -> {
                    if (top == 0) {
                        return false;
                    }
                    top--;
                    if (expected[top] != c) {
                        return false;
                    }
                }
            }
        }
        return top == 0;
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------

    @Test
    @DisplayName("A single matched pair is valid")
    void singlePair_returnsTrue() {
        assertTrue(isValid("()"));
    }

    @Test
    @DisplayName("Three different pair types placed side by side are valid")
    void threeAdjacentPairsOfDifferentTypes_returnsTrue() {
        assertTrue(isValid("()[]{}"));
    }

    @Test
    @DisplayName("A round opening bracket closed by a square bracket is invalid")
    void mismatchedTypesInOnePair_returnsFalse() {
        assertFalse(isValid("(]"));
    }

    @Test
    @DisplayName("A square pair nested inside a round pair is valid")
    void correctlyNestedDifferentTypes_returnsTrue() {
        assertTrue(isValid("([])"));
    }

    @Test
    @DisplayName("Two pairs that interleave instead of nesting are invalid")
    void interleavedPairs_returnsFalse() {
        assertFalse(isValid("([)]"));
    }

    @Test
    @DisplayName("A lone opening bracket is invalid because it is never closed")
    void singleOpeningBracket_returnsFalse() {
        assertFalse(isValid("("));
    }

    @Test
    @DisplayName("A lone closing bracket is invalid because the stack is empty when it is read")
    void singleClosingBracket_returnsFalse() {
        assertFalse(isValid(")"));
    }

    @Test
    @DisplayName("A closing bracket that appears after a complete pair is invalid")
    void extraClosingBracketAfterCompletePair_returnsFalse() {
        assertFalse(isValid("())"));
    }

    @Test
    @DisplayName("An opening bracket that follows a complete pair and is never closed is invalid")
    void extraOpeningBracketAfterCompletePair_returnsFalse() {
        assertFalse(isValid("()("));
    }

    @Test
    @DisplayName("Three pair types nested inside one another are valid")
    void threeTypesFullyNested_returnsTrue() {
        assertTrue(isValid("{[()]}"));
    }

    @Test
    @DisplayName("A curly closing bracket cannot close a square opening bracket")
    void squareOpenedAndCurlyClosed_returnsFalse() {
        assertFalse(isValid("[}"));
    }

    @Test
    @DisplayName("A square closing bracket cannot close a curly opening bracket")
    void curlyOpenedAndSquareClosed_returnsFalse() {
        assertFalse(isValid("{]"));
    }

    @Test
    @DisplayName("A valid prefix followed by an unmatched opening bracket is invalid")
    void validPrefixThenUnclosedOpening_returnsFalse() {
        assertFalse(isValid("{[()]}("));
    }

    @Test
    @DisplayName("Nesting five thousand round brackets stays valid at the constraint limit")
    void tenThousandCharactersFullyNested_returnsTrue() {
        assertTrue(isValid("(".repeat(5000) + ")".repeat(5000)));
    }

    @Test
    @DisplayName("Ten thousand opening brackets are invalid because none of them is closed")
    void tenThousandOpeningBrackets_returnsFalse() {
        assertFalse(isValid("(".repeat(10000)));
    }
}