package topInterview150;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Given the head of a linked list, remove the nth node from the end of the list
 * and return its head.
 * 
 * 
 * ? Example 1:
 * 
 * 
 * Input: head = [1,2,3,4,5], n = 2
 * Output: [1,2,3,5]
 * 
 * ? Example 2:
 * 
 * Input: head = [1], n = 1
 * Output: []
 * 
 * ? Example 3:
 * 
 * Input: head = [1,2], n = 1
 * Output: [1]
 * 
 * 
 * ! Constraints:
 * 
 * The number of nodes in the list is sz.
 * 1 <= sz <= 30
 * 0 <= Node.val <= 100
 * 1 <= n <= sz
 * 
 * 
 * Follow up: Could you do this in one pass?
 */
public class RemoveNthNodeFromEndOfList {

    // ------------------------------------------------------------------
    // Solution
    // ------------------------------------------------------------------

    static final class ListNode {

        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }
    }

    /**
     * Deletes the node at distance n from the end during a single traversal.
     *
     * * Time: O(sz) - the first loop advances lead n times and the second loop
     * advances lead sz - n times, so the pointers together read each of the sz
     * nodes a constant number of times.
     * * Space: O(1) - one dummy node and two references are allocated, and none of
     * them grows with sz.
     *
     * @param head first node of the list
     * @param n    position of the node to delete, counted from the end starting at
     *             1
     * @return first node of the list after the deletion, or null when the list
     *         becomes empty
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode lead = dummy;
        for (int i = 0; i < n; i++) {
            lead = lead.next;
        }
        ListNode trail = dummy;
        while (lead.next != null) {
            lead = lead.next;
            trail = trail.next;
        }
        trail.next = trail.next.next;
        return dummy.next;
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------

    @Test
    @DisplayName("List [1,2,3,4,5] with n = 2 loses the value 4 and returns [1,2,3,5]")
    void secondNodeFromEndRemoved_returnsListWithoutThatNode() {
        ListNode result = removeNthFromEnd(buildList(1, 2, 3, 4, 5), 2);

        assertArrayEquals(new int[] { 1, 2, 3, 5 }, toArray(result));
    }

    @Test
    @DisplayName("Single node list with n = 1 becomes empty and returns null")
    void onlyNodeRemoved_returnsNull() {
        ListNode result = removeNthFromEnd(buildList(1), 1);

        assertNull(result);
    }

    @Test
    @DisplayName("List [1,2] with n = 1 drops the last node and returns [1]")
    void lastNodeOfTwoNodeListRemoved_returnsFirstNodeOnly() {
        ListNode result = removeNthFromEnd(buildList(1, 2), 1);

        assertArrayEquals(new int[] { 1 }, toArray(result));
    }

    @Test
    @DisplayName("n equal to the list size removes the head and returns [20,30]")
    void nEqualToSize_removesHeadNode() {
        ListNode result = removeNthFromEnd(buildList(10, 20, 30), 3);

        assertArrayEquals(new int[] { 20, 30 }, toArray(result));
    }

    @Test
    @DisplayName("n equal to 1 removes the tail and returns [10,20]")
    void nEqualToOne_removesTailNode() {
        ListNode result = removeNthFromEnd(buildList(10, 20, 30), 1);

        assertArrayEquals(new int[] { 10, 20 }, toArray(result));
    }

    @Test
    @DisplayName("Repeated values are removed by position, so [1,2,1,2] with n = 3 returns [1,1,2]")
    void repeatedValues_removesNodeAtPositionNotFirstMatchingValue() {
        ListNode result = removeNthFromEnd(buildList(1, 2, 1, 2), 3);

        assertArrayEquals(new int[] { 1, 1, 2 }, toArray(result));
    }

    @Test
    @DisplayName("List of 30 nodes, the constraint maximum, with n = 17 drops the value 13")
    void maximumSizeList_removesNodeAtRequestedPosition() {
        int[] values = new int[30];
        for (int i = 0; i < values.length; i++) {
            values[i] = i;
        }

        ListNode result = removeNthFromEnd(buildList(values), 17);

        assertArrayEquals(new int[] {
                0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 14, 15, 16,
                17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29 }, toArray(result));
    }

    @Test
    @DisplayName("When the head survives, the returned reference is the original head node rather than a copy")
    void headNotRemoved_returnsOriginalHeadInstance() {
        ListNode head = buildList(1, 2, 3);

        ListNode result = removeNthFromEnd(head, 1);

        assertSame(head, result);
    }

    /**
     * Builds a chain of nodes holding the given values in order.
     *
     * @param values node values, listed from the head to the tail
     * @return first node of the chain, or null when no value is given
     */
    static ListNode buildList(int... values) {
        ListNode head = null;
        ListNode tail = null;
        for (int value : values) {
            ListNode node = new ListNode(value);
            if (head == null) {
                head = node;
            } else {
                tail.next = node;
            }
            tail = node;
        }
        return head;
    }

    /**
     * Reads a chain of nodes into an array so that two lists can be compared by
     * value.
     *
     * @param head first node of the chain, or null for an empty chain
     * @return values from the head to the tail in order
     */
    static int[] toArray(ListNode head) {
        List<Integer> values = new ArrayList<>();
        for (ListNode node = head; node != null; node = node.next) {
            values.add(node.val);
        }
        int[] result = new int[values.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = values.get(i);
        }
        return result;
    }
}