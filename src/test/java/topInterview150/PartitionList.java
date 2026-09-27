package topInterview150;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Given the head of a linked list and a value x, partition it such that all
 * nodes less than x come before nodes greater than or equal to x.
 * 
 * You should preserve the original relative order of the nodes in each of the
 * two partitions.
 * 
 * 
 * ? Example 1:
 * 
 * 
 * Input: head = [1,4,3,2,5,2], x = 3
 * Output: [1,2,2,4,3,5]
 * 
 * ? Example 2:
 * 
 * Input: head = [2,1], x = 2
 * Output: [1,2]
 * 
 * 
 * ! Constraints:
 * 
 * The number of nodes in the list is in the range [0, 200].
 * -100 <= Node.val <= 100
 * -200 <= x <= 200
 * 
 */
public class PartitionList {

    // ------------------------------------------------------------------
    // Solution
    // ------------------------------------------------------------------

    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }
    }

    /**
     * Stable partition by relinking the existing nodes; no node is created except
     * two dummies.
     *
     * * Time: O(n) - best, average and worst case are identical: the for loop
     * visits
     * each of the n nodes exactly once and does O(1) pointer writes per node.
     * * Space: O(1) - only two dummy nodes and four references are allocated
     * regardless of n; the input nodes are reused.
     *
     * @param head first node of the list, or null for an empty list
     * @param x    pivot value; nodes with val < x go first, nodes with val >= x go
     *             second
     * @return first node of the partitioned list, or null if the list is empty
     */
    public ListNode partition(ListNode head, int x) {
        ListNode lessDummy = new ListNode(0);
        ListNode greaterOrEqualDummy = new ListNode(0);
        ListNode lessTail = lessDummy;
        ListNode greaterOrEqualTail = greaterOrEqualDummy;

        for (ListNode node = head; node != null; node = node.next) {
            if (node.val < x) {
                lessTail.next = node;
                lessTail = node;
            } else {
                greaterOrEqualTail.next = node;
                greaterOrEqualTail = node;
            }
        }

        greaterOrEqualTail.next = null;
        lessTail.next = greaterOrEqualDummy.next;
        return lessDummy.next;
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------

    @Test
    @DisplayName("Example 1: [1,4,3,2,5,2] with x=3 becomes [1,2,2,4,3,5]")
    void example1_returnsLessThenGreaterOrEqual() {
        int[] input = { 1, 4, 3, 2, 5, 2 };
        assertArrayEquals(new int[] { 1, 2, 2, 4, 3, 5 }, toArray(partition(fromArray(input), 3), input.length));
    }

    @Test
    @DisplayName("Example 2: [2,1] with x=2 becomes [1,2] because 2 is not less than x")
    void example2_movesSmallerNodeToFront() {
        int[] input = { 2, 1 };
        assertArrayEquals(new int[] { 1, 2 }, toArray(partition(fromArray(input), 2), input.length));
    }

    @Test
    @DisplayName("An empty list (null head) returns null")
    void emptyList_returnsNull() {
        assertNull(partition(null, 0));
    }

    @Test
    @DisplayName("A single-node list returns the same node with next == null")
    void singleNode_returnsSameNode() {
        ListNode only = new ListNode(7);
        ListNode result = partition(only, 0);
        assertSame(only, result);
        assertNull(result.next);
    }

    @Test
    @DisplayName("When every value is less than x, the original order is unchanged")
    void allLessThanX_keepsOriginalOrder() {
        int[] input = { 3, 1, 2 };
        assertArrayEquals(new int[] { 3, 1, 2 }, toArray(partition(fromArray(input), 5), input.length));
    }

    @Test
    @DisplayName("When every value is greater than or equal to x, the original order is unchanged")
    void allGreaterOrEqual_keepsOriginalOrder() {
        int[] input = { 5, 7, 5 };
        assertArrayEquals(new int[] { 5, 7, 5 }, toArray(partition(fromArray(input), 5), input.length));
    }

    @Test
    @DisplayName("Values equal to x are placed in the second partition")
    void valuesEqualToX_goToSecondPartition() {
        int[] input = { 3, 3, 1, 3 };
        assertArrayEquals(new int[] { 1, 3, 3, 3 }, toArray(partition(fromArray(input), 3), input.length));
    }

    @Test
    @DisplayName("When the original last node is less than x, the second partition is terminated with null")
    void lastNodeLessThanX_terminatesWithoutCycle() {
        int[] input = { 5, 6, 1 };
        assertArrayEquals(new int[] { 1, 5, 6 }, toArray(partition(fromArray(input), 3), input.length));
    }

    @Test
    @DisplayName("Output reuses the original node objects in stable order within each partition")
    void duplicateValues_reuseNodesInStableOrder() {
        ListNode a = new ListNode(2);
        ListNode b = new ListNode(2);
        ListNode c = new ListNode(1);
        ListNode d = new ListNode(1);
        a.next = b;
        b.next = c;
        c.next = d;
        ListNode result = partition(a, 2);
        assertSame(c, result);
        assertSame(d, result.next);
        assertSame(a, result.next.next);
        assertSame(b, result.next.next.next);
        assertNull(b.next);
    }

    @Test
    @DisplayName("Constraint boundaries: x=-200 and x=200 leave the list unchanged, x=100 moves -100 before 100")
    void constraintBoundaries_matchExpectedOrder() {
        int[] input = { 100, -100, 0 };
        assertArrayEquals(new int[] { 100, -100, 0 }, toArray(partition(fromArray(input), -200), input.length));
        assertArrayEquals(new int[] { 100, -100, 0 }, toArray(partition(fromArray(input), 200), input.length));
        assertArrayEquals(new int[] { -100, 0, 100 }, toArray(partition(fromArray(input), 100), input.length));
    }

    private static ListNode fromArray(int[] values) {
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;
        for (int value : values) {
            tail.next = new ListNode(value);
            tail = tail.next;
        }
        return dummy.next;
    }

    private static int[] toArray(ListNode head, int expectedLength) {
        int[] values = new int[expectedLength];
        int count = 0;
        for (ListNode node = head; node != null; node = node.next) {
            assertTrue(count < expectedLength, "list has more than " + expectedLength + " nodes (cycle)");
            values[count++] = node.val;
        }
        assertEquals(expectedLength, count);
        return values;
    }
}