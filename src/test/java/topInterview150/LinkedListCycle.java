package topInterview150;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Given head, the head of a linked list, determine if the linked list has a
 * cycle in it.
 * 
 * There is a cycle in a linked list if there is some node in the list that can
 * be reached again by continuously following the next pointer. Internally, pos
 * is used to denote the index of the node that tail's next pointer is connected
 * to. Note that pos is not passed as a parameter.
 * 
 * Return true if there is a cycle in the linked list. Otherwise, return false.
 * 
 * 
 * ? Example 1:
 * 
 * 
 * Input: head = [3,2,0,-4], pos = 1
 * Output: true
 * Explanation: There is a cycle in the linked list, where the tail connects to
 * the 1st node (0-indexed).
 * 
 * ? Example 2:
 * 
 * 
 * Input: head = [1,2], pos = 0
 * Output: true
 * Explanation: There is a cycle in the linked list, where the tail connects to
 * the 0th node.
 * 
 * ? Example 3:
 * 
 * 
 * Input: head = [1], pos = -1
 * Output: false
 * Explanation: There is no cycle in the linked list.
 * 
 * 
 * ! Constraints:
 * 
 * The number of the nodes in the list is in the range [0, 104].
 * -105 <= Node.val <= 105
 * pos is -1 or a valid index in the linked-list.
 * 
 * 
 * Follow up: Can you solve it using O(1) (i.e. constant) memory?
 */
public class LinkedListCycle {

    public static class ListNode {

        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
            this.next = null;
        }
    }

    // ------------------------------------------------------------------
    // Solution
    // ------------------------------------------------------------------

    /**
     * Detects a cycle with two references that advance at different rates and no
     * extra storage.
     *
     * * Time: best O(1) - head is null or head.next is null, so the loop body never
     * executes.
     * * worst O(n) - an acyclic list of n nodes ends after floor(n / 2) iterations
     * because
     * fast advances two nodes each time; a cyclic list whose tail holds mu nodes
     * and whose
     * cycle holds L nodes (mu + L = n) ends after at most mu + L iterations, since
     * after
     * slow reaches the cycle entry the gap between fast and slow shrinks by one per
     * iteration and starts below L.
     * * Space: O(1) - only the two references slow and fast are allocated, whatever
     * n
     * is.
     *
     * @param head first node of the list, or null when the list holds no node
     * @return true when some node is reachable again by following next repeatedly
     */
    public boolean hasCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                return true;
            }
        }
        return false;
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------

    /**
     * Builds a chain of nodes from values and sets the last node's next to the node
     * at index pos.
     *
     * Time: O(n) - one pass to allocate and link, one indexed walk to reach pos.
     * Space: O(n) - n allocated nodes.
     *
     * @param values value of each node in order from head to tail
     * @param pos    index the tail's next must point to, or -1 to leave the tail's
     *               next as null
     * @return head of the built list, or null when values is empty
     */
    static ListNode buildList(int[] values, int pos) {
        if (values.length == 0) {
            return null;
        }
        ListNode head = new ListNode(values[0]);
        ListNode tail = head;
        for (int i = 1; i < values.length; i++) {
            tail.next = new ListNode(values[i]);
            tail = tail.next;
        }
        if (pos >= 0) {
            ListNode entry = head;
            for (int i = 0; i < pos; i++) {
                entry = entry.next;
            }
            tail.next = entry;
        }
        return head;
    }

    /**
     * Produces the value array 0, 1, ..., n - 1, used where node values are
     * irrelevant.
     *
     * Time: O(n) - one write per element.
     * Space: O(n) - the returned array.
     *
     * @param n number of values to produce
     * @return array holding 0 through n - 1 in ascending order
     */
    static int[] sequentialValues(int n) {
        int[] values = new int[n];
        for (int i = 0; i < n; i++) {
            values[i] = i;
        }
        return values;
    }

    @Test
    @DisplayName("An empty list has no node to revisit, so the result is false")
    void emptyList_returnsFalse() {
        assertFalse(hasCycle(null));
    }

    @Test
    @DisplayName("Example 1: [3,2,0,-4] with the tail linked to index 1 reports a cycle")
    void example1CycleAtIndexOne_returnsTrue() {
        assertTrue(hasCycle(buildList(new int[] { 3, 2, 0, -4 }, 1)));
    }

    @Test
    @DisplayName("Example 2: [1,2] with the tail linked to index 0 reports a cycle")
    void example2TwoNodeCycleAtHead_returnsTrue() {
        assertTrue(hasCycle(buildList(new int[] { 1, 2 }, 0)));
    }

    @Test
    @DisplayName("Example 3: the single node [1] with a null next reports no cycle")
    void example3SingleNodeWithoutCycle_returnsFalse() {
        assertFalse(hasCycle(buildList(new int[] { 1 }, -1)));
    }

    @Test
    @DisplayName("A single node whose next points at itself reports a cycle")
    void singleNodeSelfLoop_returnsTrue() {
        assertTrue(hasCycle(buildList(new int[] { 1 }, 0)));
    }

    @Test
    @DisplayName("Two nodes ending in null make fast null after one iteration, so the result is false")
    void twoNodesWithoutCycle_returnsFalse() {
        assertFalse(hasCycle(buildList(new int[] { 1, 2 }, -1)));
    }

    @Test
    @DisplayName("Three nodes ending in null make fast.next null after one iteration, so the result is false")
    void threeNodesWithoutCycle_returnsFalse() {
        assertFalse(hasCycle(buildList(new int[] { 1, 2, 3 }, -1)));
    }

    @Test
    @DisplayName("Four nodes ending in null exit the loop with fast null, so the result is false")
    void fourNodesWithoutCycle_returnsFalse() {
        assertFalse(hasCycle(buildList(new int[] { 1, 2, 3, 4 }, -1)));
    }

    @Test
    @DisplayName("Three nodes holding the same value and ending in null still report no cycle")
    void identicalValuesWithoutCycle_returnsFalse() {
        assertFalse(hasCycle(buildList(new int[] { 7, 7, 7 }, -1)));
    }

    @Test
    @DisplayName("Four nodes holding the same value with the tail linked to index 2 report a cycle")
    void identicalValuesWithCycle_returnsTrue() {
        assertTrue(hasCycle(buildList(new int[] { 7, 7, 7, 7 }, 2)));
    }

    @Test
    @DisplayName("A one-node tail followed by a six-node cycle reports a cycle")
    void evenCycleAfterOddTail_returnsTrue() {
        assertTrue(hasCycle(buildList(sequentialValues(7), 1)));
    }

    @Test
    @DisplayName("A two-node tail followed by a five-node cycle reports a cycle")
    void oddCycleAfterEvenTail_returnsTrue() {
        assertTrue(hasCycle(buildList(sequentialValues(7), 2)));
    }

    @Test
    @DisplayName("At the constraint bound of 10000 nodes, a self-loop on the last node reports a cycle")
    void selfLoopAtTailOfLargestList_returnsTrue() {
        assertTrue(hasCycle(buildList(sequentialValues(10000), 9999)));
    }

    @Test
    @DisplayName("At the constraint bound of 10000 nodes, a cycle spanning every node reports a cycle")
    void cycleSpanningLargestList_returnsTrue() {
        assertTrue(hasCycle(buildList(sequentialValues(10000), 0)));
    }

    @Test
    @DisplayName("At the constraint bound of 10000 nodes, a chain ending in null reports no cycle")
    void largestListWithoutCycle_returnsFalse() {
        assertFalse(hasCycle(buildList(sequentialValues(10000), -1)));
    }

    @Test
    @DisplayName("Values at the -100000 and 100000 bounds do not change the result")
    void extremeValuesWithoutCycle_returnsFalse() {
        assertFalse(hasCycle(buildList(new int[] { -100000, 100000, 0, -100000 }, -1)));
    }
}