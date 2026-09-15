package topInterview150;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Given the head of a linked list, reverse the nodes of the list k at a time,
 * and return the modified list.
 * 
 * k is a positive integer and is less than or equal to the length of the linked
 * list. If the number of nodes is not a multiple of k then left-out nodes, in
 * the end, should remain as it is.
 * 
 * You may not alter the values in the list's nodes, only nodes themselves may
 * be changed.
 * 
 * 
 * ? Example 1:
 * 
 * 
 * Input: head = [1,2,3,4,5], k = 2
 * Output: [2,1,4,3,5]
 * 
 * ? Example 2:
 * 
 * 
 * Input: head = [1,2,3,4,5], k = 3
 * Output: [3,2,1,4,5]
 * 
 * 
 * ! Constraints:
 * 
 * The number of nodes in the list is n.
 * 1 <= k <= n <= 5000
 * 0 <= Node.val <= 1000
 * 
 * 
 * Follow-up: Can you solve the problem in O(1) extra memory space?
 */
public class ReverseNodesInKGroup {

    public static class ListNode {

        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }
    }

    // ------------------------------------------------------------------
    // Solution
    // ------------------------------------------------------------------

    /**
     * Reverses every complete block of k nodes and leaves a trailing block of fewer
     * than k nodes in
     * its original order.
     *
     * * Time: O(n) - the outer loop performs 2n - (n mod k) + 1 pointer moves in
     * total, because every
     * node in a complete block is read once by findKthNode and once by the
     * link-rewriting loop.
     * * Space: O(1) - one dummy node plus six reference variables, allocated once
     * regardless of n.
     *
     * @param head first node of the list, or null when the list is empty
     * @param k    number of nodes per block to reverse, 1 &lt;= k &lt;= n
     * @return first node of the modified list
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode groupPrev = dummy;
        while (true) {
            ListNode groupLast = findKthNode(groupPrev, k);
            if (groupLast == null) {
                break;
            }
            ListNode groupNext = groupLast.next;
            ListNode previous = groupNext;
            ListNode current = groupPrev.next;
            while (current != groupNext) {
                ListNode following = current.next;
                current.next = previous;
                previous = current;
                current = following;
            }
            ListNode newGroupPrev = groupPrev.next;
            groupPrev.next = groupLast;
            groupPrev = newGroupPrev;
        }
        return dummy.next;
    }

    /**
     * * Time: O(k) - advances the pointer k times, or stops earlier when the list
     * ends.
     * * Space: O(1) - one loop variable and one reference variable.
     *
     * @param start node immediately before the block being measured
     * @param k     number of nodes the block must contain
     * @return the k-th node after start, or null when fewer than k nodes follow
     *         start
     */
    private ListNode findKthNode(ListNode start, int k) {
        ListNode node = start;
        for (int step = 0; step < k && node != null; step++) {
            node = node.next;
        }
        return node;
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------

    @Test
    @DisplayName("Example 1: [1,2,3,4,5] with k=2 reverses both pairs and leaves node 5 in place")
    void example1_reversesEachPairAndKeepsFinalNode() {
        ListNode result = reverseKGroup(buildList(new int[] { 1, 2, 3, 4, 5 }), 2);

        assertArrayEquals(new int[] { 2, 1, 4, 3, 5 }, toArray(result));
    }

    @Test
    @DisplayName("Example 2: [1,2,3,4,5] with k=3 reverses the first three nodes and keeps [4,5]")
    void example2_reversesFirstBlockAndKeepsRemainder() {
        ListNode result = reverseKGroup(buildList(new int[] { 1, 2, 3, 4, 5 }), 3);

        assertArrayEquals(new int[] { 3, 2, 1, 4, 5 }, toArray(result));
    }

    @Test
    @DisplayName("k=1 makes every block one node long, so the sequence is unchanged")
    void kEqualsOne_returnsTheSameSequence() {
        ListNode result = reverseKGroup(buildList(new int[] { 1, 2, 3, 4, 5 }), 1);

        assertArrayEquals(new int[] { 1, 2, 3, 4, 5 }, toArray(result));
    }

    @Test
    @DisplayName("k equal to the list length reverses the whole list once")
    void kEqualsLength_reversesTheWholeList() {
        ListNode result = reverseKGroup(buildList(new int[] { 1, 2, 3, 4 }), 4);

        assertArrayEquals(new int[] { 4, 3, 2, 1 }, toArray(result));
    }

    @Test
    @DisplayName("A one-node list with k=1 returns that exact node object")
    void singleNodeWithKOne_returnsTheSameNodeObject() {
        ListNode only = buildList(new int[] { 7 });

        ListNode result = reverseKGroup(only, 1);

        assertSame(only, result);
        assertArrayEquals(new int[] { 7 }, toArray(result));
    }

    @Test
    @DisplayName("Length 7 with k=3 reverses two blocks and leaves the single remaining node as is")
    void lengthNotMultipleOfK_leavesRemainderInInputOrder() {
        ListNode result = reverseKGroup(buildList(new int[] { 1, 2, 3, 4, 5, 6, 7 }), 3);

        assertArrayEquals(new int[] { 3, 2, 1, 6, 5, 4, 7 }, toArray(result));
    }

    @Test
    @DisplayName("Repeated values are reordered by position, not by value")
    void duplicateValues_reverseByPositionNotByValue() {
        ListNode result = reverseKGroup(buildList(new int[] { 5, 5, 1, 5, 5, 1 }), 3);

        assertArrayEquals(new int[] { 1, 5, 5, 1, 5, 5 }, toArray(result));
    }

    @Test
    @DisplayName("Every output node is an input node object, so links move and values are untouched")
    void outputNodes_areTheInputNodeObjectsInPermutedOrder() {
        int[] values = { 10, 20, 30, 40, 50, 60, 70 };
        ListNode head = buildList(values);
        List<ListNode> inputNodes = collectNodes(head);
        int[] expectedPositions = { 2, 1, 0, 5, 4, 3, 6 };

        List<ListNode> outputNodes = collectNodes(reverseKGroup(head, 3));

        assertEquals(values.length, outputNodes.size());
        for (int index = 0; index < expectedPositions.length; index++) {
            assertSame(inputNodes.get(expectedPositions[index]), outputNodes.get(index));
        }
    }

    private static ListNode buildList(int[] values) {
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;
        for (int value : values) {
            tail.next = new ListNode(value);
            tail = tail.next;
        }
        return dummy.next;
    }

    private static int[] toArray(ListNode head) {
        List<Integer> values = new ArrayList<>();
        for (ListNode node = head; node != null; node = node.next) {
            values.add(node.val);
        }
        int[] result = new int[values.size()];
        for (int index = 0; index < result.length; index++) {
            result[index] = values.get(index);
        }
        return result;
    }

    private static List<ListNode> collectNodes(ListNode head) {
        List<ListNode> nodes = new ArrayList<>();
        for (ListNode node = head; node != null; node = node.next) {
            nodes.add(node);
        }
        return nodes;
    }
}