package topInterview150;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 
 * Given a binary tree
 * 
 * struct Node {
 * int val;
 * Node *left;
 * Node *right;
 * Node *next;
 * }
 * Populate each next pointer to point to its next right node. If there is no
 * next right node, the next pointer should be set to NULL.
 * 
 * Initially, all next pointers are set to NULL.
 * 
 * 
 * ? Example 1:
 * 
 * 
 * Input: root = [1,2,3,4,5,null,7]
 * Output: [1,#,2,3,#,4,5,7,#]
 * Explanation: Given the above binary tree (Figure A), your function should
 * populate each next pointer to point to its next right node, just like in
 * Figure B. The serialized output is in level order as connected by the next
 * pointers, with '#' signifying the end of each level.
 * 
 * ? Example 2:
 * 
 * Input: root = []
 * Output: []
 * 
 * 
 * ! Constraints:
 * 
 * The number of nodes in the tree is in the range [0, 6000].
 * -100 <= Node.val <= 100
 * 
 * 
 * * Follow-up:
 * 
 * You may only use constant extra space.
 * The recursive approach is fine. You may assume implicit stack space does not
 * count as extra space for this problem.
 * 
 */
public class PopulatingNextRightPointersInEachNodeII {

    // ------------------------------------------------------------------
    // Solution
    // ------------------------------------------------------------------

    static final class Node {

        int val;
        Node left;
        Node right;
        Node next;

        Node(int val) {
            this.val = val;
        }
    }

    /**
     * Assigns each node's next to the node on its right in the same level, or
     * leaves it null.
     *
     * * Time: O(n) - each node is read once as current, and each child is assigned
     * to
     * tail.next
     * at most once.
     * * Space: O(1) - only sentinel, tail, current and levelHead are allocated,
     * whatever n and
     * the tree shape are.
     *
     * @param root root of the tree, or null for an empty tree
     * @return the same root reference, with every next pointer populated
     */
    public Node connect(Node root) {
        Node sentinel = new Node(0);
        Node levelHead = root;
        while (levelHead != null) {
            sentinel.next = null;
            Node tail = sentinel;
            for (Node current = levelHead; current != null; current = current.next) {
                if (current.left != null) {
                    tail.next = current.left;
                    tail = current.left;
                }
                if (current.right != null) {
                    tail.next = current.right;
                    tail = current.right;
                }
            }
            levelHead = sentinel.next;
        }
        return root;
    }

    // ------------------------------------------------------------------
    // Reference implementation (oracle)
    // ------------------------------------------------------------------

    /**
     * Runs a queue-based breadth-first search that reads only left and right, never
     * next.
     *
     * * Time: O(n) - each node is offered to the queue once and polled once.
     * * Space:
     * Best: O(1) - a chain keeps at most one node in the queue at any time.
     * Average: O(w) - w is the widest level, because the queue holds at most two
     * levels.
     * Worst: O(n) - a complete tree's last level holds about (n + 1) / 2 nodes.
     *
     * @param root root of the tree, or null for an empty tree
     * @return the same root reference, with every next pointer populated
     */
    public Node connectReference(Node root) {
        if (root == null) {
            return null;
        }
        Deque<Node> queue = new ArrayDeque<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            Node previous = null;
            for (int i = 0; i < levelSize; i++) {
                Node node = queue.poll();
                if (previous != null) {
                    previous.next = node;
                }
                previous = node;
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }
        return root;
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------

    @Test
    @DisplayName("Example 1: [1,2,3,4,5,null,7] serializes to 1,#,2,3,#,4,5,7,#")
    void example1_linksEachLevelLeftToRight() {
        Node root = buildTree(1, 2, 3, 4, 5, null, 7);

        Node result = connect(root);

        assertEquals("1,#,2,3,#,4,5,7,#", serializeByNext(result));
    }

    @Test
    @DisplayName("Example 2: an empty tree returns null")
    void example2_returnsNull() {
        Node result = connect(null);

        assertNull(result);
    }

    @Test
    @DisplayName("A single node forms one level and keeps next null")
    void singleNode_levelEndsWithNull() {
        Node root = buildTree(1);

        Node result = connect(root);

        assertEquals("1,#", serializeByNext(result));
    }

    @Test
    @DisplayName("A zigzag chain holds one node per level, so every next pointer stays null")
    void zigzagChain_eachLevelHoldsOneNode() {
        Node root = buildTree(1, 2, null, null, 3, 4);

        Node result = connect(root);

        assertEquals("1,#,2,#,3,#,4,#", serializeByNext(result));
    }

    @Test
    @DisplayName("Node 2 has no left child, so level 3 starts at 4 and level 4 is still linked")
    void missingLeftmostLeftChild_deeperLevelStillLinked() {
        Node root = buildTree(1, 2, 3, null, 4, 5, null, 6, null, null, 7);

        Node result = connect(root);

        assertEquals("1,#,2,3,#,4,5,#,6,7,#", serializeByNext(result));
    }

    @Test
    @DisplayName("Childless middle node 5 is skipped, so 7.next is 8 across the gap")
    void childlessMiddleNode_linksAcrossGap() {
        Node root = buildTree(1, 2, 3, 4, 5, null, 6, 7, null, null, null, null, 8);

        Node result = connect(root);

        assertEquals("1,#,2,3,#,4,5,6,#,7,8,#", serializeByNext(result));
    }

    @Test
    @DisplayName("connect returns the same root reference it received")
    void connect_returnsSameRootReference() {
        Node root = buildTree(1, 2, 3);

        Node result = connect(root);

        assertSame(root, result);
    }

    @Test
    @DisplayName("connect changes only next, so values and left/right links stay unchanged")
    void connect_keepsValuesAndChildLinksUnchanged() {
        Node root = buildTree(1, 2, 3, 4, 5, null, 6, 7, null, null, null, null, 8);
        String before = shapeSignature(root);

        connect(root);

        assertEquals(before, shapeSignature(root));
    }

    @Test
    @DisplayName("On 500 seeded random trees, every next targets the same node as the oracle")
    void randomTrees_matchOracleNextTargets() {
        Random sizes = new Random(117);
        for (int trial = 0; trial < 500; trial++) {
            int n = sizes.nextInt(61);
            long seed = sizes.nextLong();
            int[] expected = nextSignature(connectReference(randomTree(new Random(seed), n)));

            int[] actual = nextSignature(connect(randomTree(new Random(seed), n)));

            assertArrayEquals(expected, actual, "seed=" + seed + " n=" + n);
        }
    }

    /**
     * Builds a tree from LeetCode level-order notation, where null marks a missing
     * child.
     *
     * @param levelOrder node values in level order, with null for each missing
     *                   child
     * @return root of the built tree, or null when the first value is missing
     */
    static Node buildTree(Integer... levelOrder) {
        if (levelOrder.length == 0 || levelOrder[0] == null) {
            return null;
        }
        Node root = new Node(levelOrder[0]);
        Deque<Node> parents = new ArrayDeque<>();
        parents.offer(root);
        int i = 1;
        while (i < levelOrder.length) {
            Node parent = parents.poll();
            if (levelOrder[i] != null) {
                parent.left = new Node(levelOrder[i]);
                parents.offer(parent.left);
            }
            i++;
            if (i < levelOrder.length && levelOrder[i] != null) {
                parent.right = new Node(levelOrder[i]);
                parents.offer(parent.right);
            }
            i++;
        }
        return root;
    }

    /**
     * Serializes the tree in LeetCode output format by following next from each
     * level's head.
     *
     * @param root root of the tree, or null for an empty tree
     * @return values per level joined by commas, with # after each level, or "" for
     *         no tree
     */
    static String serializeByNext(Node root) {
        List<List<Node>> levels = levelsByChildLinks(root);
        int total = 0;
        for (List<Node> level : levels) {
            total += level.size();
        }
        StringBuilder out = new StringBuilder();
        for (List<Node> level : levels) {
            Node node = level.get(0);
            for (int steps = 0; node != null && steps <= total; steps++) {
                out.append(node.val).append(',');
                node = node.next;
            }
            out.append("#,");
        }
        return out.isEmpty() ? "" : out.substring(0, out.length() - 1);
    }

    /**
     * Groups nodes into levels through left and right only, so the grouping ignores
     * next.
     *
     * @param root root of the tree, or null for an empty tree
     * @return one list per level, each ordered from left to right
     */
    static List<List<Node>> levelsByChildLinks(Node root) {
        List<List<Node>> levels = new ArrayList<>();
        List<Node> level = new ArrayList<>();
        if (root != null) {
            level.add(root);
        }
        while (!level.isEmpty()) {
            levels.add(level);
            List<Node> below = new ArrayList<>();
            for (Node node : level) {
                if (node.left != null) {
                    below.add(node.left);
                }
                if (node.right != null) {
                    below.add(node.right);
                }
            }
            level = below;
        }
        return levels;
    }

    /**
     * Writes values and child links in pre-order, to confirm that connect edits
     * only next.
     *
     * @param node root of the subtree, or null
     * @return nested "(val left right)" text, with "()" for a missing node
     */
    static String shapeSignature(Node node) {
        if (node == null) {
            return "()";
        }
        return "(" + node.val + " " + shapeSignature(node.left) + " "
                + shapeSignature(node.right) + ")";
    }

    /**
     * Inserts n nodes by random descent from the root, so identical seeds give
     * identical trees.
     *
     * @param random source of child directions and values
     * @param n      number of nodes to create
     * @return root of the built tree, or null when n is 0
     */
    static Node randomTree(Random random, int n) {
        if (n == 0) {
            return null;
        }
        Node root = new Node(random.nextInt(201) - 100);
        for (int k = 1; k < n; k++) {
            Node node = root;
            Node child = new Node(random.nextInt(201) - 100);
            while (true) {
                if (random.nextBoolean()) {
                    if (node.left == null) {
                        node.left = child;
                        break;
                    }
                    node = node.left;
                } else {
                    if (node.right == null) {
                        node.right = child;
                        break;
                    }
                    node = node.right;
                }
            }
        }
        return root;
    }

    /**
     * Maps each node's next target to a breadth-first index, so results compare by
     * identity.
     *
     * @param root root of the tree, or null for an empty tree
     * @return entry i is the index of node i's next, -1 for null, -2 for a node
     *         outside the tree
     */
    static int[] nextSignature(Node root) {
        List<Node> order = new ArrayList<>();
        for (List<Node> level : levelsByChildLinks(root)) {
            order.addAll(level);
        }
        Map<Node, Integer> index = new IdentityHashMap<>();
        for (int i = 0; i < order.size(); i++) {
            index.put(order.get(i), i);
        }
        int[] signature = new int[order.size()];
        for (int i = 0; i < order.size(); i++) {
            Node next = order.get(i).next;
            signature[i] = next == null ? -1 : index.getOrDefault(next, -2);
        }
        return signature;
    }
}