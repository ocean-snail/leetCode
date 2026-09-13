package topInterview150;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * You are given the heads of two sorted linked lists list1 and list2.
 * 
 * Merge the two lists into one sorted list. The list should be made by splicing
 * together the nodes of the first two lists.
 * 
 * Return the head of the merged linked list.
 * 
 * 
 * ? Example 1:
 * 
 * 
 * Input: list1 = [1,2,4], list2 = [1,3,4]
 * Output: [1,1,2,3,4,4]
 * 
 * ? Example 2:
 * 
 * Input: list1 = [], list2 = []
 * Output: []
 * 
 * ? Example 3:
 * 
 * Input: list1 = [], list2 = [0]
 * Output: [0]
 * 
 * 
 * ! Constraints:
 * 
 * The number of nodes in both lists is in the range [0, 50].
 * -100 <= Node.val <= 100
 * Both list1 and list2 are sorted in non-decreasing order.
 * 
 */
public class MergeTwoSortedLists {

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
     * Relinks the nodes of both lists into one non-decreasing list without creating
     * result nodes.
     *
     * * Time: best O(1) - list1 or list2 is null, so the loop body never executes
     * and
     * one
     * assignment links the other list; with both lists non-empty the loop still
     * runs at
     * least min(n, m) times, reached when every node of the shorter list is linked
     * before
     * any node of the longer list.
     * * average O(n + m) - over all C(n + m, n) equally likely orders of distinct
     * values the
     * loop runs n + m - n / (m + 1) - m / (n + 1) times on average.
     * * worst O(n + m) - the loop runs n + m - 1 times when the last two nodes of
     * the
     * merged
     * order come from different lists, e.g. list1 = [1, 3, 5] and list2 = [2, 4,
     * 6].
     * * Space: O(1) - one dummy node and the references tail, list1 and list2,
     * whatever n and m are.
     *
     * @param list1 head of the first non-decreasing list, or null when it holds no
     *              node
     * @param list2 head of the second non-decreasing list, or null when it holds no
     *              node
     * @return head of the non-decreasing list made of the original nodes, with
     *         list1's nodes ahead
     *         of list2's nodes among equal values, or null when both lists are
     *         empty
     */
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode dummy = new ListNode();
        ListNode tail = dummy;
        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                tail.next = list1;
                list1 = list1.next;
            } else {
                tail.next = list2;
                list2 = list2.next;
            }
            tail = tail.next;
        }
        tail.next = list1 != null ? list1 : list2;
        return dummy.next;
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------

    /**
     * Builds a list of new nodes holding the given values from head to tail.
     *
     * Time: O(k) - one node creation per value.
     * Space: O(k) - k created nodes.
     *
     * @param values value of each node from head to tail
     * @return head of the built list, or null when values is empty
     */
    static ListNode buildList(int... values) {
        ListNode head = null;
        for (int i = values.length - 1; i >= 0; i--) {
            head = new ListNode(values[i], head);
        }
        return head;
    }

    /**
     * Collects the values of a list from head to tail.
     *
     * Time: O(k) - one visit per node.
     * Space: O(k) - the node array and the returned array.
     *
     * @param head first node, or null for an empty list
     * @return the values in list order
     */
    static int[] valuesOf(ListNode head) {
        ListNode[] nodes = nodesOf(head);
        int[] values = new int[nodes.length];
        for (int i = 0; i < nodes.length; i++) {
            values[i] = nodes[i].val;
        }
        return values;
    }

    /**
     * Collects the node references of a list from head to tail.
     *
     * Time: O(k) - one visit per node.
     * Space: O(k) - the returned array.
     *
     * @param head first node, or null for an empty list
     * @return the node references in list order
     */
    static ListNode[] nodesOf(ListNode head) {
        List<ListNode> nodes = new ArrayList<>();
        for (ListNode node = head; node != null; node = node.next) {
            nodes.add(node);
        }
        return nodes.toArray(new ListNode[0]);
    }

    @Test
    @DisplayName("Example 1: [1,2,4] and [1,3,4] merge into [1,1,2,3,4,4]")
    void example1_returnsMergedValues() {
        assertArrayEquals(new int[] { 1, 1, 2, 3, 4, 4 },
                valuesOf(mergeTwoLists(buildList(1, 2, 4), buildList(1, 3, 4))));
    }

    @Test
    @DisplayName("Example 2: two empty lists merge into an empty list")
    void example2BothListsEmpty_returnsNull() {
        assertNull(mergeTwoLists(null, null));
    }

    @Test
    @DisplayName("Example 3: an empty first list and [0] merge into [0]")
    void example3FirstListEmpty_returnsSecondListValues() {
        assertArrayEquals(new int[] { 0 }, valuesOf(mergeTwoLists(null, buildList(0))));
    }

    @Test
    @DisplayName("An empty second list makes the result the first list's own head node with its values unchanged")
    void secondListEmpty_returnsFirstListHeadNode() {
        ListNode list1 = buildList(3, 8);
        ListNode merged = mergeTwoLists(list1, null);
        assertSame(list1, merged);
        assertArrayEquals(new int[] { 3, 8 }, valuesOf(merged));
    }

    @Test
    @DisplayName("When every value of the first list is smaller, the whole second list follows the first list")
    void firstListEntirelySmaller_returnsFirstThenSecondValues() {
        assertArrayEquals(new int[] { 1, 2, 3, 4, 5, 6 },
                valuesOf(mergeTwoLists(buildList(1, 2, 3), buildList(4, 5, 6))));
    }

    @Test
    @DisplayName("When every value of the second list is smaller, the whole first list follows the second list")
    void secondListEntirelySmaller_returnsSecondThenFirstValues() {
        assertArrayEquals(new int[] { 1, 2, 3, 4, 5, 6 },
                valuesOf(mergeTwoLists(buildList(4, 5, 6), buildList(1, 2, 3))));
    }

    @Test
    @DisplayName("When the second list runs out first, the remaining nodes of the first list end the result")
    void secondListRunsOutFirst_appendsRestOfFirstList() {
        assertArrayEquals(new int[] { 1, 2, 3, 5, 6, 7 },
                valuesOf(mergeTwoLists(buildList(1, 5, 6, 7), buildList(2, 3))));
    }

    @Test
    @DisplayName("Single nodes [2] and [1] merge into [1,2]")
    void singleNodesWithSmallerSecondHead_returnsSecondThenFirstValue() {
        assertArrayEquals(new int[] { 1, 2 }, valuesOf(mergeTwoLists(buildList(2), buildList(1))));
    }

    @Test
    @DisplayName("Values at the -100 and 100 bounds, repeated across both lists, merge in order")
    void boundaryValuesRepeatedAcrossLists_returnsOrderedValues() {
        assertArrayEquals(new int[] { -100, -100, 0, 100, 100 },
                valuesOf(mergeTwoLists(buildList(-100, 0, 100), buildList(-100, 100))));
    }

    @Test
    @DisplayName("At the constraint bound of 50 nodes per list, alternating values merge into 100 ordered values")
    void fiftyNodesEachAlternating_returnsHundredOrderedValues() {
        int[] values1 = new int[50];
        int[] values2 = new int[50];
        for (int i = 0; i < 50; i++) {
            values1[i] = -100 + 4 * i;
            values2[i] = -98 + 4 * i;
        }
        int[] expected = new int[100];
        for (int i = 0; i < 100; i++) {
            expected[i] = -100 + 2 * i;
        }
        assertArrayEquals(expected,
                valuesOf(mergeTwoLists(buildList(values1), buildList(values2))));
    }

    @Test
    @DisplayName("Distinct values [1,4,6] and [2,3,5] return the original nodes in the order a0,b0,b1,a1,b2,a2")
    void distinctValues_relinksOriginalNodesInValueOrder() {
        ListNode list1 = buildList(1, 4, 6);
        ListNode list2 = buildList(2, 3, 5);
        ListNode[] a = nodesOf(list1);
        ListNode[] b = nodesOf(list2);
        // ListNode does not override equals, so the element comparison is reference
        // identity.
        assertArrayEquals(new ListNode[] { a[0], b[0], b[1], a[1], b[2], a[2] },
                nodesOf(mergeTwoLists(list1, list2)));
    }

    @Test
    @DisplayName("Equal values keep first-list nodes ahead: [2,2,3] and [1,2,2] give the nodes b0,a0,a1,b1,b2,a2")
    void equalValues_keepFirstListNodesAheadOfSecondListNodes() {
        ListNode list1 = buildList(2, 2, 3);
        ListNode list2 = buildList(1, 2, 2);
        ListNode[] a = nodesOf(list1);
        ListNode[] b = nodesOf(list2);
        assertArrayEquals(new ListNode[] { b[0], a[0], a[1], b[1], b[2], a[2] },
                nodesOf(mergeTwoLists(list1, list2)));
    }
}