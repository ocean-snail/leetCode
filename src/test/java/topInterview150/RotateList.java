package topInterview150;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Given the head of a linked list, rotate the list to the right by k places.
 * 
 * 
 * ? Example 1:
 * 
 * 
 * Input: head = [1,2,3,4,5], k = 2
 * Output: [4,5,1,2,3]
 * 
 * ? Example 2:
 * 
 * 
 * Input: head = [0,1,2], k = 4
 * Output: [2,0,1]
 * 
 * 
 * ! Constraints:
 * 
 * The number of nodes in the list is in the range [0, 500].
 * -100 <= Node.val <= 100
 * 0 <= k <= 2 * 109
 */
public class RotateList {

    // ------------------------------------------------------------------
    // Solution
    // ------------------------------------------------------------------

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

    /**
     * Rotates the list to the right by k places by relinking existing nodes.
     *
     * * Time: O(n) - one pass of n - 1 steps finds the tail and the length, then at
     * most
     * n - 2 more steps reach the new tail; best, average and worst case are all
     * O(n)
     * because the length is unknown until the full first pass ends (O(1) only when
     * n = 0)
     * * Space: O(1) - only the references tail, newTail, newHead and two int
     * counters;
     * no node is allocated
     *
     * @param head first node of the list, or null for an empty list
     * @param k    number of places to rotate right, 0 <= k <= 2 * 10^9
     * @return first node of the rotated list, or null for an empty list
     */
    public ListNode rotateRight(ListNode head, int k) {
        if (head == null) {
            return null;
        }

        int length = 1;
        ListNode tail = head;
        while (tail.next != null) {
            tail = tail.next;
            length++;
        }

        int shift = k % length;
        if (shift == 0) {
            return head;
        }

        ListNode newTail = head;
        for (int step = 0; step < length - shift - 1; step++) {
            newTail = newTail.next;
        }

        ListNode newHead = newTail.next;
        newTail.next = null;
        tail.next = head;
        return newHead;
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------

    private static final int MAX_NODES = 500;

    private static ListNode toList(int[] values) {
        ListNode dummy = new ListNode();
        ListNode last = dummy;
        for (int value : values) {
            last.next = new ListNode(value);
            last = last.next;
        }
        return dummy.next;
    }

    private static int[] toArray(ListNode head) {
        int[] buffer = new int[MAX_NODES];
        int count = 0;
        for (ListNode node = head; node != null; node = node.next) {
            if (count == MAX_NODES) {
                fail("list has more than " + MAX_NODES + " nodes; it contains a cycle");
            }
            buffer[count++] = node.val;
        }
        return Arrays.copyOf(buffer, count);
    }

    @Test
    @DisplayName("Example 1: [1,2,3,4,5] rotated by 2 becomes [4,5,1,2,3]")
    void example1_lastTwoMoveToFront() {
        assertArrayEquals(new int[] { 4, 5, 1, 2, 3 }, toArray(rotateRight(toList(new int[] { 1, 2, 3, 4, 5 }), 2)));
    }

    @Test
    @DisplayName("Example 2: [0,1,2] rotated by 4 equals rotating by 4 % 3 = 1, giving [2,0,1]")
    void example2_kLargerThanLength_rotatesByRemainder() {
        assertArrayEquals(new int[] { 2, 0, 1 }, toArray(rotateRight(toList(new int[] { 0, 1, 2 }), 4)));
    }

    @Test
    @DisplayName("An empty list returns null for any k")
    void emptyList_returnsNull() {
        assertNull(rotateRight(null, 3));
    }

    @Test
    @DisplayName("A single node stays unchanged even for the maximum k")
    void singleNode_maxK_unchanged() {
        assertArrayEquals(new int[] { 7 }, toArray(rotateRight(toList(new int[] { 7 }), 2_000_000_000)));
    }

    @Test
    @DisplayName("k = 0 leaves the list unchanged")
    void zeroK_unchanged() {
        assertArrayEquals(new int[] { 1, 2, 3 }, toArray(rotateRight(toList(new int[] { 1, 2, 3 }), 0)));
    }

    @Test
    @DisplayName("k equal to the length leaves the list unchanged")
    void kEqualsLength_unchanged() {
        assertArrayEquals(new int[] { 1, 2, 3, 4, 5 }, toArray(rotateRight(toList(new int[] { 1, 2, 3, 4, 5 }), 5)));
    }

    @Test
    @DisplayName("k = length - 1 moves every node except the head to the front")
    void kIsLengthMinusOne_headMovesToEnd() {
        assertArrayEquals(new int[] { 2, 3, 4, 5, 1 }, toArray(rotateRight(toList(new int[] { 1, 2, 3, 4, 5 }), 4)));
    }

    @Test
    @DisplayName("k = 1 moves only the tail to the front")
    void kIsOne_tailMovesToFront() {
        assertArrayEquals(new int[] { 5, 1, 2, 3, 4 }, toArray(rotateRight(toList(new int[] { 1, 2, 3, 4, 5 }), 1)));
    }

    @Test
    @DisplayName("Two nodes rotated by 1 swap order")
    void twoNodes_kOne_swapped() {
        assertArrayEquals(new int[] { 2, 1 }, toArray(rotateRight(toList(new int[] { 1, 2 }), 1)));
    }

    @Test
    @DisplayName("Maximum k on three nodes rotates by 2_000_000_000 % 3 = 2")
    void maxK_threeNodes_rotatesByRemainder() {
        assertArrayEquals(new int[] { 2, 3, 1 }, toArray(rotateRight(toList(new int[] { 1, 2, 3 }), 2_000_000_000)));
    }

    @Test
    @DisplayName("Negative and duplicate values are moved as whole nodes")
    void negativeAndDuplicateValues_rotatedByPosition() {
        assertArrayEquals(new int[] { 0, -100, 100, -100, 0 },
                toArray(rotateRight(toList(new int[] { -100, 0, 0, -100, 100 }), 3)));
    }
}