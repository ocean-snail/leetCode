package topInterview150;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * You are given two non-empty linked lists representing two non-negative
 * integers. The digits are stored in reverse order, and each of their nodes
 * contains a single digit. Add the two numbers and return the sum as a linked
 * list.
 * 
 * You may assume the two numbers do not contain any leading zero, except the
 * number 0 itself.
 * 
 * 
 * ? Example 1:
 * 
 * 
 * Input: l1 = [2,4,3], l2 = [5,6,4]
 * Output: [7,0,8]
 * Explanation: 342 + 465 = 807.
 * 
 * ? Example 2:
 * 
 * Input: l1 = [0], l2 = [0]
 * Output: [0]
 * 
 * ? Example 3:
 * 
 * Input: l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
 * Output: [8,9,9,9,0,0,0,1]
 * 
 * 
 * ! Constraints:
 * 
 * The number of nodes in each linked list is in the range [1, 100].
 * 0 <= Node.val <= 9
 * It is guaranteed that the list represents a number that does not have leading
 * zeros.
 */
public class AddTwoNumbers {

    public static class ListNode {

        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    // ------------------------------------------------------------------
    // Solution
    // ------------------------------------------------------------------

    /**
     * Adds the two numbers one decimal column at a time, starting from the least
     * significant digit.
     *
     * * Time: O(max(m, n)) - the loop body runs once per result digit: max(m, n)
     * times, plus one
     * more time only when the most significant column produces a carry, and each
     * run does
     * a fixed amount of work. Best, average and worst case share this bound because
     * the
     * run count is always max(m, n) or max(m, n) + 1.
     * * Space: O(max(m, n)) - the returned list holds max(m, n) or max(m, n) + 1
     * new
     * nodes;
     * excluding that output, only the dummyHead node and the locals tail, node1,
     * node2,
     * carry and sum are used, which is O(1).
     *
     * @param l1 first number with m nodes, least significant digit at the head
     * @param l2 second number with n nodes, least significant digit at the head
     * @return new list holding the digits of the sum, least significant digit at
     *         the head
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummyHead = new ListNode(0);
        ListNode tail = dummyHead;
        ListNode node1 = l1;
        ListNode node2 = l2;
        int carry = 0;
        while (node1 != null || node2 != null || carry != 0) {
            int sum = carry;
            if (node1 != null) {
                sum += node1.val;
                node1 = node1.next;
            }
            if (node2 != null) {
                sum += node2.val;
                node2 = node2.next;
            }
            carry = sum / 10;
            tail.next = new ListNode(sum % 10);
            tail = tail.next;
        }
        return dummyHead.next;
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------

    /**
     * Builds a list whose head holds digits[0], matching LeetCode's array notation
     * for the inputs.
     *
     * Time: O(k) - one node allocation per array element.
     * Space: O(k) - k allocated nodes.
     *
     * @param digits node values from head to tail
     * @return head of the built list, or null when digits is empty
     */
    static ListNode buildList(int[] digits) {
        ListNode head = null;
        for (int i = digits.length - 1; i >= 0; i--) {
            head = new ListNode(digits[i], head);
        }
        return head;
    }

    /**
     * Reads a list into an array whose element 0 is the head's value.
     *
     * Time: O(k) - one pass to count the nodes and one pass to copy their values.
     * Space: O(k) - the returned array.
     *
     * @param head first node of the list, or null for a list with no node
     * @return node values from head to tail
     */
    static int[] toDigits(ListNode head) {
        int length = 0;
        for (ListNode node = head; node != null; node = node.next) {
            length++;
        }
        int[] digits = new int[length];
        int index = 0;
        for (ListNode node = head; node != null; node = node.next) {
            digits[index++] = node.val;
        }
        return digits;
    }

    /**
     * Produces an array holding one digit repeated, used for inputs at the 100-node
     * bound.
     *
     * Time: O(count) - one write per element.
     * Space: O(count) - the returned array.
     *
     * @param count number of elements
     * @param digit value written to every element
     * @return array holding count copies of digit
     */
    static int[] repeatedDigits(int count, int digit) {
        int[] digits = new int[count];
        Arrays.fill(digits, digit);
        return digits;
    }

    @Test
    @DisplayName("Example 1: [2,4,3] + [5,6,4] (342 + 465) returns [7,0,8]")
    void example1CarryIntoLastColumn_returns708() {
        ListNode sum = addTwoNumbers(
                buildList(new int[] { 2, 4, 3 }), buildList(new int[] { 5, 6, 4 }));
        assertArrayEquals(new int[] { 7, 0, 8 }, toDigits(sum));
    }

    @Test
    @DisplayName("Example 2: [0] + [0] returns the single node [0]")
    void example2BothZero_returnsSingleZero() {
        ListNode sum = addTwoNumbers(buildList(new int[] { 0 }), buildList(new int[] { 0 }));
        assertArrayEquals(new int[] { 0 }, toDigits(sum));
    }

    @Test
    @DisplayName("Example 3: [9,9,9,9,9,9,9] + [9,9,9,9] returns [8,9,9,9,0,0,0,1]")
    void example3LongerFirstListWithCarryChain_returnsEightNodes() {
        ListNode sum = addTwoNumbers(
                buildList(new int[] { 9, 9, 9, 9, 9, 9, 9 }), buildList(new int[] { 9, 9, 9, 9 }));
        assertArrayEquals(new int[] { 8, 9, 9, 9, 0, 0, 0, 1 }, toDigits(sum));
    }

    @Test
    @DisplayName("[5] + [5] writes 0 for the column sum 10 and appends a carry node: [0,1]")
    void singleDigitsSummingToTen_appendsCarryNode() {
        ListNode sum = addTwoNumbers(buildList(new int[] { 5 }), buildList(new int[] { 5 }));
        assertArrayEquals(new int[] { 0, 1 }, toDigits(sum));
    }

    @Test
    @DisplayName("[9,9] + [9,9] (99 + 99) writes 9 for the largest column sum 19: [8,9,1]")
    void largestColumnSumNineteen_returns891() {
        ListNode sum = addTwoNumbers(buildList(new int[] { 9, 9 }), buildList(new int[] { 9, 9 }));
        assertArrayEquals(new int[] { 8, 9, 1 }, toDigits(sum));
    }

    @Test
    @DisplayName("[5] + [1,2,3] (5 + 321) copies the second list's remaining digits: [6,2,3]")
    void firstListShorterWithoutCarry_returns623() {
        ListNode sum = addTwoNumbers(buildList(new int[] { 5 }), buildList(new int[] { 1, 2, 3 }));
        assertArrayEquals(new int[] { 6, 2, 3 }, toDigits(sum));
    }

    @Test
    @DisplayName("[1,2,3] + [5] (321 + 5) copies the first list's remaining digits: [6,2,3]")
    void secondListShorterWithoutCarry_returns623() {
        ListNode sum = addTwoNumbers(buildList(new int[] { 1, 2, 3 }), buildList(new int[] { 5 }));
        assertArrayEquals(new int[] { 6, 2, 3 }, toDigits(sum));
    }

    @Test
    @DisplayName("[1] + [9,9] (1 + 99) carries past the end of the shorter first list: [0,0,1]")
    void firstListShorterWithCarryChain_returns001() {
        ListNode sum = addTwoNumbers(buildList(new int[] { 1 }), buildList(new int[] { 9, 9 }));
        assertArrayEquals(new int[] { 0, 0, 1 }, toDigits(sum));
    }

    @Test
    @DisplayName("100 nines + 100 nines returns 8, then 99 nines, then 1 (101 nodes)")
    void maximumLengthAllNines_returns101Nodes() {
        int[] expected = repeatedDigits(101, 9);
        expected[0] = 8;
        expected[100] = 1;
        ListNode sum = addTwoNumbers(
                buildList(repeatedDigits(100, 9)), buildList(repeatedDigits(100, 9)));
        assertArrayEquals(expected, toDigits(sum));
    }

    @Test
    @DisplayName("100 nines + [1] carries through all 100 columns: 100 zeros, then 1")
    void maximumLengthNinesPlusOne_returnsHundredZerosThenOne() {
        int[] expected = new int[101];
        expected[100] = 1;
        ListNode sum = addTwoNumbers(buildList(repeatedDigits(100, 9)), buildList(new int[] { 1 }));
        assertArrayEquals(expected, toDigits(sum));
    }

    @Test
    @DisplayName("Both input lists keep their digits and their lengths after the call")
    void inputListsAfterAddition_remainUnchanged() {
        int[] firstDigits = new int[] { 9, 9, 9, 9, 9, 9, 9 };
        int[] secondDigits = new int[] { 9, 9, 9, 9 };
        ListNode l1 = buildList(firstDigits);
        ListNode l2 = buildList(secondDigits);
        addTwoNumbers(l1, l2);
        assertArrayEquals(firstDigits, toDigits(l1));
        assertArrayEquals(secondDigits, toDigits(l2));
    }
}