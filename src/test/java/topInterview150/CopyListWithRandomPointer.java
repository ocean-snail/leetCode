package topInterview150;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.IdentityHashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 
 * A linked list of length n is given such that each node contains an additional
 * random pointer, which could point to any node in the list, or null.
 * 
 * Construct a deep copy of the list. The deep copy should consist of exactly n
 * brand new nodes, where each new node has its value set to the value of its
 * corresponding original node. Both the next and random pointer of the new
 * nodes should point to new nodes in the copied list such that the pointers in
 * the original list and copied list represent the same list state. None of the
 * pointers in the new list should point to nodes in the original list.
 * 
 * For example, if there are two nodes X and Y in the original list, where
 * X.random --> Y, then for the corresponding two nodes x and y in the copied
 * list, x.random --> y.
 * 
 * Return the head of the copied linked list.
 * 
 * The linked list is represented in the input/output as a list of n nodes. Each
 * node is represented as a pair of [val, random_index] where:
 * 
 * val: an integer representing Node.val
 * random_index: the index of the node (range from 0 to n-1) that the random
 * pointer points to, or null if it does not point to any node.
 * Your code will only be given the head of the original linked list.
 * 
 * 
 * ? Example 1:
 * 
 * 
 * Input: head = [[7,null],[13,0],[11,4],[10,2],[1,0]]
 * Output: [[7,null],[13,0],[11,4],[10,2],[1,0]]
 * 
 * ? Example 2:
 * 
 * 
 * Input: head = [[1,1],[2,1]]
 * Output: [[1,1],[2,1]]
 * 
 * ? Example 3:
 * 
 * 
 * 
 * Input: head = [[3,null],[3,0],[3,null]]
 * Output: [[3,null],[3,0],[3,null]]
 * 
 * 
 * ! Constraints:
 * 
 * 0 <= n <= 1000
 * -104 <= Node.val <= 104
 * Node.random is null or is pointing to some node in the linked list.
 * 
 */
public class CopyListWithRandomPointer {

    public static final class Node {

        public int val;
        public Node next;
        public Node random;

        public Node(int val) {
            this.val = val;
        }
    }

    // ------------------------------------------------------------------
    // Solution
    // ------------------------------------------------------------------

    /**
     * Copies the list in three passes, using the list itself as the lookup
     * structure.
     *
     * * Time: O(n) - three sequential passes over n nodes, each pass performing a
     * fixed number of
     * reference assignments per node; best, average and worst case are identical
     * because no
     * branch changes the number of visited nodes.
     * * Space: O(1) - two local references are live per pass; the n allocated nodes
     * are the required
     * output, not working memory.
     *
     * @param head first node of the original list, or null when the list is empty
     * @return first node of the copied list, or null when head is null
     */
    public Node copyRandomList(Node head) {
        if (head == null) {
            return null;
        }
        for (Node original = head; original != null; original = original.next.next) {
            Node copy = new Node(original.val);
            copy.next = original.next;
            original.next = copy;
        }
        for (Node original = head; original != null; original = original.next.next) {
            if (original.random != null) {
                original.next.random = original.random.next;
            }
        }
        Node copiedHead = head.next;
        for (Node original = head; original != null; original = original.next) {
            Node copy = original.next;
            original.next = copy.next;
            copy.next = (original.next == null) ? null : original.next.next;
        }
        return copiedHead;
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------

    private static final int NULL_RANDOM = -1;

    private static final int FOREIGN_RANDOM = -2;

    @Test
    @DisplayName("Example 1 [[7,null],[13,0],[11,4],[10,2],[1,0]] is reproduced with identical random indices")
    void example1_reproducesValuesAndRandomIndices() {
        int[][] spec = { { 7, NULL_RANDOM }, { 13, 0 }, { 11, 4 }, { 10, 2 }, { 1, 0 } };
        assertArrayEquals(spec, serialize(copyRandomList(buildList(spec))));
    }

    @Test
    @DisplayName("Example 2 [[1,1],[2,1]] is reproduced with identical random indices")
    void example2_reproducesValuesAndRandomIndices() {
        int[][] spec = { { 1, 1 }, { 2, 1 } };
        assertArrayEquals(spec, serialize(copyRandomList(buildList(spec))));
    }

    @Test
    @DisplayName("Example 3 [[3,null],[3,0],[3,null]] with duplicate values is reproduced by identity, not by value")
    void example3_reproducesValuesAndRandomIndices() {
        int[][] spec = { { 3, NULL_RANDOM }, { 3, 0 }, { 3, NULL_RANDOM } };
        assertArrayEquals(spec, serialize(copyRandomList(buildList(spec))));
    }

    @Test
    @DisplayName("A null head returns null")
    void nullHead_returnsNull() {
        assertNull(copyRandomList(null));
    }

    @Test
    @DisplayName("A single node with a null random link is copied as an isolated node")
    void singleNodeWithNullRandom_returnsIsolatedNode() {
        Node copiedHead = copyRandomList(buildList(new int[][] { { -10000, NULL_RANDOM } }));
        assertEquals(-10000, copiedHead.val);
        assertNull(copiedHead.next);
        assertNull(copiedHead.random);
    }

    @Test
    @DisplayName("A single node whose random targets itself is copied so the copy's random targets the copy")
    void singleNodeWithSelfRandom_copyPointsToItself() {
        Node head = buildList(new int[][] { { 10000, 0 } });
        Node copiedHead = copyRandomList(head);
        assertNotSame(head, copiedHead);
        assertSame(copiedHead, copiedHead.random);
        assertNull(copiedHead.next);
    }

    @Test
    @DisplayName("A list whose random links are all null is copied as a plain forward chain")
    void allRandomLinksNull_reproducesPlainChain() {
        int[][] spec = { { 1, NULL_RANDOM }, { 2, NULL_RANDOM }, { 3, NULL_RANDOM } };
        assertArrayEquals(spec, serialize(copyRandomList(buildList(spec))));
    }

    @Test
    @DisplayName("A list whose random links all target the head is copied with every random index 0")
    void everyRandomTargetsHead_reproducesZeroIndices() {
        int[][] spec = { { 1, 0 }, { 2, 0 }, { 3, 0 }, { 4, 0 } };
        assertArrayEquals(spec, serialize(copyRandomList(buildList(spec))));
    }

    @Test
    @DisplayName("No node reachable in the copied list is an instance taken from the original list")
    void copiedList_sharesNoNodeWithOriginal() {
        int[][] spec = { { 7, NULL_RANDOM }, { 13, 0 }, { 11, 4 }, { 10, 2 }, { 1, 0 } };
        Node head = buildList(spec);
        Node copiedHead = copyRandomList(head);
        assertNotSame(head, copiedHead);
        assertNoSharedNodes(head, copiedHead);
    }

    @Test
    @DisplayName("The original list holds its input shape after the copy returns")
    void afterCopy_originalListIsUnchanged() {
        int[][] spec = { { 7, NULL_RANDOM }, { 13, 0 }, { 11, 4 }, { 10, 2 }, { 1, 0 } };
        Node head = buildList(spec);
        copyRandomList(head);
        assertArrayEquals(spec, serialize(head));
    }

    /**
     * Time: O(n) - one pass allocates the n nodes, a second pass writes both links
     * per node.
     * Space: O(n) - an array of the n created nodes, used to resolve random
     * indices.
     *
     * @param spec one {value, randomIndex} pair per node, NULL_RANDOM meaning a
     *             null random link
     * @return head of the built list, or null when spec is empty
     */
    private static Node buildList(int[][] spec) {
        if (spec.length == 0) {
            return null;
        }
        Node[] nodes = new Node[spec.length];
        for (int i = 0; i < spec.length; i++) {
            nodes[i] = new Node(spec[i][0]);
        }
        for (int i = 0; i < spec.length; i++) {
            nodes[i].next = (i + 1 < spec.length) ? nodes[i + 1] : null;
            nodes[i].random = (spec[i][1] == NULL_RANDOM) ? null : nodes[spec[i][1]];
        }
        return nodes[0];
    }

    /**
     * Time: O(n) - one pass records the index of each node, a second pass emits the
     * pairs.
     * Space: O(n) - an identity map from node to index plus the produced pair
     * array.
     *
     * @param head first node of the list to encode, or null
     * @return one {value, randomIndex} pair per node in list order, where
     *         NULL_RANDOM marks a null
     *         random link and FOREIGN_RANDOM marks a random link leaving this list
     */
    private static int[][] serialize(Node head) {
        Map<Node, Integer> indexOf = new IdentityHashMap<>();
        int length = 0;
        for (Node node = head; node != null; node = node.next) {
            indexOf.put(node, length);
            length++;
        }
        int[][] spec = new int[length][2];
        int position = 0;
        for (Node node = head; node != null; node = node.next) {
            spec[position][0] = node.val;
            if (node.random == null) {
                spec[position][1] = NULL_RANDOM;
            } else {
                Integer target = indexOf.get(node.random);
                spec[position][1] = (target == null) ? FOREIGN_RANDOM : target;
            }
            position++;
        }
        return spec;
    }

    /**
     * Time: O(n) - one pass collects the original nodes, one pass checks both links
     * of each copy.
     * Space: O(n) - an identity map holding the n original node references.
     *
     * @param original first node of the original list, or null
     * @param copied   first node of the copied list, or null
     */
    static void assertNoSharedNodes(Node original, Node copied) {
        Map<Node, Boolean> originalNodes = new IdentityHashMap<>();
        for (Node node = original; node != null; node = node.next) {
            originalNodes.put(node, Boolean.TRUE);
        }
        for (Node node = copied; node != null; node = node.next) {
            assertFalse(originalNodes.containsKey(node), "the copied chain reaches an original node");
            assertFalse(node.random != null && originalNodes.containsKey(node.random),
                    "a random link of a copied node targets an original node");
        }
    }
}