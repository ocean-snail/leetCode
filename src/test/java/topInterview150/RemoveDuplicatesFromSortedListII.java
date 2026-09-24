package topInterview150;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * You are given the head of a sorted linked list.
 * 
 * Delete all nodes that have duplicate numbers, leaving only distinct numbers
 * from the original list.
 * 
 * Return the linked list sorted as well.
 * 
 * 
 * ? Example 1:
 * 
 * 
 * Input: head = [1,2,3,3,4,4,5]
 * Output: [1,2,5]
 * 
 * ? Example 2:
 * 
 * 
 * Input: head = [1,1,1,2,3]
 * Output: [2,3]
 * 
 * 
 * ! Constraints:
 * 
 * The number of nodes in the list is in the range [0, 300].
 * -100 <= Node.val <= 100
 * The list is guaranteed to be sorted in ascending order.
 */
public class RemoveDuplicatesFromSortedListII {

    // ------------------------------------------------------------------
    // Solution
    // ------------------------------------------------------------------

    /**
     * Singly linked list node, matching the LeetCode definition.
     */
    public static final class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    /**
     * Removes every value that appears more than once, keeping only values that
     * appear exactly once.
     *
     * * Time: O(n) in best, average and worst case - current advances one node per
     * step in either branch and never moves backwards, so each of the n nodes is
     * read once.
     * * Space: O(1) - only the sentinel node and three references (tail, current,
     * duplicated) are allocated; kept nodes are relinked in place.
     *
     * @param head first node of a list sorted in ascending order, or null for an
     *             empty list
     * @return first node of the list that contains only the distinct values, or
     *         null if none remain
     */
    public ListNode deleteDuplicates(ListNode head) {
        ListNode sentinel = new ListNode(0, head);
        ListNode tail = sentinel;
        ListNode current = head;

        while (current != null) {
            if (current.next != null && current.val == current.next.val) {
                int duplicated = current.val;
                while (current != null && current.val == duplicated) {
                    current = current.next;
                }
                tail.next = current;
            } else {
                tail = current;
                current = current.next;
            }
        }
        return sentinel.next;
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------

    @Test
    @DisplayName("Example 1: [1,2,3,3,4,4,5] keeps only the values 1, 2 and 5")
    void exampleOne_returnsOneTwoFive() {
        assertArrayEquals(new int[] { 1, 2, 5 }, toArray(deleteDuplicates(build(1, 2, 3, 3, 4, 4, 5))));
    }

    @Test
    @DisplayName("Example 2: [1,1,1,2,3] removes the triple run at the head and returns [2,3]")
    void exampleTwo_returnsTwoThree() {
        assertArrayEquals(new int[] { 2, 3 }, toArray(deleteDuplicates(build(1, 1, 1, 2, 3))));
    }

    @Test
    @DisplayName("An empty list (0 nodes is allowed by the constraints) returns null")
    void emptyList_returnsNull() {
        assertNull(deleteDuplicates(null));
    }

    @Test
    @DisplayName("A single node has no neighbour to duplicate it and is returned unchanged")
    void singleNode_returnsSameNode() {
        ListNode head = build(7);
        assertSame(head, deleteDuplicates(head));
    }

    @Test
    @DisplayName("A list made only of duplicate runs returns null")
    void allValuesDuplicated_returnsNull() {
        assertNull(deleteDuplicates(build(1, 1, 2, 2, 2, 3, 3)));
    }

    @Test
    @DisplayName("A duplicate run at the tail is cut off so the last kept node points to null")
    void duplicateRunAtTail_endsAtLastDistinctNode() {
        assertArrayEquals(new int[] { 1 }, toArray(deleteDuplicates(build(1, 2, 2))));
    }

    @Test
    @DisplayName("Two duplicate runs next to each other are both removed")
    void adjacentDuplicateRuns_removesBothRuns() {
        assertArrayEquals(new int[] { 1, 4 }, toArray(deleteDuplicates(build(1, 2, 2, 3, 3, 4))));
    }

    @Test
    @DisplayName("Boundary values -100 and 100 are handled like any other value")
    void boundaryValues_keepsDistinctBoundaries() {
        assertArrayEquals(new int[] { 0, 100 }, toArray(deleteDuplicates(build(-100, -100, 0, 100))));
    }

    @Test
    @DisplayName("A list with no duplicates keeps the original node objects in the original order")
    void noDuplicates_reusesOriginalNodes() {
        ListNode head = build(-3, 0, 5);
        ListNode second = head.next;
        ListNode third = second.next;
        ListNode result = deleteDuplicates(head);
        assertSame(head, result);
        assertSame(second, result.next);
        assertSame(third, result.next.next);
        assertNull(third.next);
    }

    private static ListNode build(int... values) {
        ListNode head = null;
        for (int i = values.length - 1; i >= 0; i--) {
            head = new ListNode(values[i], head);
        }
        return head;
    }

    private static int[] toArray(ListNode head) {
        List<Integer> values = new ArrayList<>();
        for (ListNode node = head; node != null; node = node.next) {
            values.add(node.val);
        }
        return values.stream().mapToInt(Integer::intValue).toArray();
    }
}