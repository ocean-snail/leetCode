package topInterview150;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Given the head of a singly linked list and two integers left and right where
 * left <= right, reverse the nodes of the list from position left to position
 * right, and return the reversed list.
 * 
 * 
 * ? Example 1:
 * 
 * 
 * Input: head = [1,2,3,4,5], left = 2, right = 4
 * Output: [1,4,3,2,5]
 * 
 * ? Example 2:
 * 
 * Input: head = [5], left = 1, right = 1
 * Output: [5]
 * 
 * 
 * ! Constraints:
 * 
 * The number of nodes in the list is n.
 * 1 <= n <= 500
 * -500 <= Node.val <= 500
 * 1 <= left <= right <= n
 * 
 * 
 * Follow up: Could you do it in one pass?
 * 
 */
public class ReverseLinkedListII {

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
     * Reverses positions left through right by moving the node after segmentTail to
     * the front of
     * the segment once per iteration, which leaves every other link in the list
     * untouched.
     *
     * * Time: O(right) - the first loop runs left - 1 times and the second loop
     * runs
     * right - left
     * times, so the method performs right - 1 iterations of fixed work in total.
     * Best case
     * O(1) when right == 1, average case O(n) because the mean of right over all
     * valid
     * position pairs is (2n + 1) / 3, worst case O(n) when right == n.
     * * Space: O(1) - one dummy node plus the references beforeSegment, segmentTail
     * and moved are
     * allocated no matter how large n is, and no node value is copied.
     *
     * @param head  first node of the list
     * @param left  1-based position of the first node to reverse
     * @param right 1-based position of the last node to reverse, never smaller than
     *              left
     * @return head of the list after positions left through right have been
     *         reversed
     */
    public ListNode reverseBetween(ListNode head, int left, int right) {
        ListNode dummy = new ListNode(0, head);
        ListNode beforeSegment = dummy;
        for (int i = 1; i < left; i++) {
            beforeSegment = beforeSegment.next;
        }
        ListNode segmentTail = beforeSegment.next;
        for (int i = left; i < right; i++) {
            ListNode moved = segmentTail.next;
            segmentTail.next = moved.next;
            moved.next = beforeSegment.next;
            beforeSegment.next = moved;
        }
        return dummy.next;
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------

    private static ListNode buildList(int... values) {
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
        int[] result = new int[values.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = values.get(i);
        }
        return result;
    }

    @Test
    @DisplayName("Official example 1: positions 2 to 4 of [1,2,3,4,5] become [1,4,3,2,5]")
    void officialExampleOne_reversesTheMiddleThreeNodes() {
        assertArrayEquals(
                new int[] { 1, 4, 3, 2, 5 },
                toArray(reverseBetween(buildList(1, 2, 3, 4, 5), 2, 4)));
    }

    @Test
    @DisplayName("Official example 2: a one node list with left and right both 1 stays [5]")
    void officialExampleTwo_singleNodeListIsUnchanged() {
        assertArrayEquals(new int[] { 5 }, toArray(reverseBetween(buildList(5), 1, 1)));
    }

    @Test
    @DisplayName("A segment of length one leaves every value in its original position")
    void leftEqualsRight_listIsUnchanged() {
        assertArrayEquals(
                new int[] { 1, 2, 3, 4, 5 },
                toArray(reverseBetween(buildList(1, 2, 3, 4, 5), 3, 3)));
    }

    @Test
    @DisplayName("A segment starting at position 1 reverses the prefix and keeps the suffix")
    void segmentStartsAtHead_reversesThePrefixOnly() {
        assertArrayEquals(
                new int[] { 3, 2, 1, 4, 5 },
                toArray(reverseBetween(buildList(1, 2, 3, 4, 5), 1, 3)));
    }

    @Test
    @DisplayName("A segment ending at the last position reverses the suffix and keeps the prefix")
    void segmentEndsAtTail_reversesTheSuffixOnly() {
        assertArrayEquals(
                new int[] { 1, 2, 5, 4, 3 },
                toArray(reverseBetween(buildList(1, 2, 3, 4, 5), 3, 5)));
    }

    @Test
    @DisplayName("A segment covering every position reverses the whole list")
    void segmentCoversWholeList_everyNodeIsReversed() {
        assertArrayEquals(
                new int[] { 5, 4, 3, 2, 1 },
                toArray(reverseBetween(buildList(1, 2, 3, 4, 5), 1, 5)));
    }

    @Test
    @DisplayName("A two node list reversed end to end swaps the two values")
    void twoNodeList_swapsBothNodes() {
        assertArrayEquals(new int[] { 2, 1 }, toArray(reverseBetween(buildList(1, 2), 1, 2)));
    }

    @Test
    @DisplayName("Repeated values are reordered by position, not by value")
    void duplicateValues_areReorderedByPosition() {
        assertArrayEquals(
                new int[] { 7, 9, 8, 7, 7 },
                toArray(reverseBetween(buildList(7, 7, 8, 9, 7), 2, 4)));
    }

    @Test
    @DisplayName("Values at the constraint bounds of -500 and 500 survive the reversal unchanged")
    void extremeValues_arePreservedExactly() {
        assertArrayEquals(
                new int[] { 500, -500, 500, -500 },
                toArray(reverseBetween(buildList(-500, 500, -500, 500), 1, 4)));
    }

    @Test
    @DisplayName("Reversing a segment relinks the original node objects rather than new ones")
    void reversedSegment_containsTheOriginalNodeObjects() {
        ListNode head = buildList(1, 2, 3, 4, 5);
        ListNode[] before = new ListNode[5];
        ListNode node = head;
        for (int i = 0; i < before.length; i++) {
            before[i] = node;
            node = node.next;
        }
        ListNode result = reverseBetween(head, 2, 4);
        ListNode[] after = new ListNode[5];
        node = result;
        for (int i = 0; i < after.length; i++) {
            after[i] = node;
            node = node.next;
        }
        assertSame(before[0], after[0]);
        assertSame(before[3], after[1]);
        assertSame(before[2], after[2]);
        assertSame(before[1], after[3]);
        assertSame(before[4], after[4]);
    }

    @Test
    @DisplayName("The returned list reaches null after exactly the original number of nodes")
    void reversedList_terminatesAfterTheOriginalLength() {
        ListNode result = reverseBetween(buildList(1, 2, 3, 4, 5, 6), 2, 5);
        int visited = 0;
        ListNode node = result;
        while (node != null && visited <= 6) {
            visited++;
            node = node.next;
        }
        assertNull(node);
        assertEquals(6, visited);
    }

    @Test
    @DisplayName("When left is 1 the original head node ends at position right")
    void leftIsOne_originalHeadBecomesTheSegmentTail() {
        ListNode head = buildList(1, 2, 3, 4);
        ListNode originalHead = head;
        ListNode result = reverseBetween(head, 1, 3);
        assertSame(originalHead, result.next.next);
        assertEquals(4, originalHead.next.val);
    }

    @Test
    @DisplayName("Nodes outside the reversed segment keep both their position and their identity")
    void nodesOutsideSegment_keepPositionAndIdentity() {
        ListNode head = buildList(1, 2, 3, 4, 5, 6);
        ListNode first = head;
        ListNode second = head.next;
        ListNode last = head.next.next.next.next.next;
        ListNode result = reverseBetween(head, 3, 5);
        assertSame(first, result);
        assertSame(second, result.next);
        assertSame(last, result.next.next.next.next.next);
        assertArrayEquals(new int[] { 1, 2, 5, 4, 3, 6 }, toArray(result));
    }
}