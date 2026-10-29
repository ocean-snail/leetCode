package topInterview150;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Given the root of a binary tree, flatten the tree into a "linked list":
 * 
 * The "linked list" should use the same TreeNode class where the right child
 * pointer points to the next node in the list and the left child pointer is
 * always null.
 * The "linked list" should be in the same order as a pre-order traversal of the
 * binary tree.
 * 
 * 
 * ? Example 1:
 * 
 * 
 * Input: root = [1,2,5,3,4,null,6]
 * Output: [1,null,2,null,3,null,4,null,5,null,6]
 * 
 * ? Example 2:
 * 
 * Input: root = []
 * Output: []
 * 
 * ? Example 3:
 * 
 * Input: root = [0]
 * Output: [0]
 * 
 * 
 * ! Constraints:
 * 
 * The number of nodes in the tree is in the range [0, 2000].
 * -100 <= Node.val <= 100
 * 
 * 
 * Follow up: Can you flatten the tree in-place (with O(1) extra space)?
 * 
 */

public class FlattenBinaryTreeToLinkedList {

    public static final class TreeNode {

        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int val) {
            this.val = val;
        }
    }

    // ------------------------------------------------------------------
    // Solution
    // ------------------------------------------------------------------

    /**
     * Rewires the tree in place into a right-only chain that lists the nodes in
     * pre-order.
     *
     * * Time: O(n) in the best, average and worst case - the outer loop takes each
     * of
     * the n nodes as
     * current exactly once; the inner loop reads only the right spine of a left
     * subtree that no
     * earlier step has modified, and each node lies on such a spine for at most one
     * ancestor, so
     * the inner loop advances fewer than n times in total over the whole run.
     * * Space: O(1) - two local references (current, rightmost); no stack, queue,
     * list or recursion.
     *
     * @param root root of the tree, or null for an empty tree; the same node
     *             objects are relinked
     */
    public void flatten(TreeNode root) {
        TreeNode current = root;
        while (current != null) {
            if (current.left != null) {
                TreeNode rightmost = current.left;
                while (rightmost.right != null) {
                    rightmost = rightmost.right;
                }
                rightmost.right = current.right;
                current.right = current.left;
                current.left = null;
            }
            current = current.right;
        }
    }

    // ------------------------------------------------------------------
    // Reference implementation (oracle)
    // ------------------------------------------------------------------

    /**
     * Records the full pre-order sequence with an explicit stack before changing
     * any link, then relinks
     * the recorded nodes in a second pass. It never searches a right spine and
     * never moves a subtree.
     *
     * * Time: O(n) - each node is pushed once, popped once, appended to the list
     * once
     * and relinked once.
     * * Space: O(n) - the list holds all n node references; the stack holds at most
     * h
     * + 1 references,
     * where h is the tree height in edges (O(log n) for a balanced tree, O(n) for a
     * skewed one).
     *
     * @param root root of the tree, or null for an empty tree; the same node
     *             objects are relinked
     */
    public void flattenReference(TreeNode root) {
        List<TreeNode> order = new ArrayList<>();
        Deque<TreeNode> pending = new ArrayDeque<>();
        if (root != null) {
            pending.push(root);
        }
        while (!pending.isEmpty()) {
            TreeNode node = pending.pop();
            order.add(node);
            if (node.right != null) {
                pending.push(node.right);
            }
            if (node.left != null) {
                pending.push(node.left);
            }
        }
        for (int i = 0; i < order.size(); i++) {
            TreeNode node = order.get(i);
            node.left = null;
            node.right = i + 1 < order.size() ? order.get(i + 1) : null;
        }
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------

    private static final int MAX_NODES = 2000;

    private static final int MIN_VALUE = -100;

    private static final int MAX_VALUE = 100;

    @Test
    @DisplayName("Example 1 [1,2,5,3,4,null,6] becomes [1,null,2,null,3,null,4,null,5,null,6]")
    void example1_rightChainInPreorder() {
        TreeNode root = buildTree(1, 2, 5, 3, 4, null, 6);
        flatten(root);
        assertEquals(Arrays.asList(1, null, 2, null, 3, null, 4, null, 5, null, 6), toLevelOrder(root));
    }

    @Test
    @DisplayName("Example 2: an empty tree (null root) is accepted and stays empty")
    void emptyTree_staysNull() {
        TreeNode root = buildTree();
        flatten(root);
        assertEquals(List.of(), toLevelOrder(root));
    }

    @Test
    @DisplayName("Example 3: a single node [0] is returned unchanged as [0]")
    void singleNode_unchanged() {
        TreeNode root = buildTree(0);
        flatten(root);
        assertEquals(List.of(0), toLevelOrder(root));
    }

    @Test
    @DisplayName("A left-only chain [1,2,null,3,null,4] becomes the right-only chain [1,null,2,null,3,null,4]")
    void leftOnlyChain_becomesRightChainInSameOrder() {
        TreeNode root = buildTree(1, 2, null, 3, null, 4);
        flatten(root);
        assertEquals(Arrays.asList(1, null, 2, null, 3, null, 4), toLevelOrder(root));
    }

    @Test
    @DisplayName("A right-only chain [1,null,2,null,3] is already flat and stays unchanged")
    void rightOnlyChain_unchanged() {
        TreeNode root = buildTree(1, null, 2, null, 3);
        flatten(root);
        assertEquals(Arrays.asList(1, null, 2, null, 3), toLevelOrder(root));
    }

    @Test
    @DisplayName("[1,2,5,3]: the right spine of node 2 ends at 2 itself, yet 5 still follows 3 in the result")
    void rightSpineEndsBeforePreorderLast_rightSubtreeStillFollowsDeepestLeftNode() {
        TreeNode root = buildTree(1, 2, 5, 3);
        flatten(root);
        assertEquals(Arrays.asList(1, null, 2, null, 3, null, 5), toLevelOrder(root));
    }

    @Test
    @DisplayName("[1,2,5,null,3,null,null,null,4]: node 5 follows 4, the third node of the right spine 2 -> 3 -> 4")
    void longRightSpineInLeftSubtree_rightSubtreeFollowsLastSpineNode() {
        TreeNode root = buildTree(1, 2, 5, null, 3, null, null, null, 4);
        flatten(root);
        assertEquals(Arrays.asList(1, null, 2, null, 3, null, 4, null, 5), toLevelOrder(root));
    }

    @Test
    @DisplayName("Values at the bounds -100 and 100 with duplicates are ordered by tree position, not by value")
    void duplicateBoundaryValues_orderFollowsStructureNotValue() {
        TreeNode root = buildTree(0, -100, 100, 100, -100, 0, -100);
        flatten(root);
        assertEquals(Arrays.asList(0, null, -100, null, 100, null, -100, null, 100, null, 0, null, -100),
                toLevelOrder(root));
    }

    @Test
    @DisplayName("Flattening relinks the original node objects in pre-order instead of allocating new nodes")
    void originalNodes_relinkedInPreorderWithoutNewNodes() {
        TreeNode root = buildTree(1, 2, 5, 3, 4, null, 6);
        List<TreeNode> expected = preorderNodes(root);
        flatten(root);
        TreeNode node = root;
        for (TreeNode original : expected) {
            assertSame(original, node);
            node = node.right;
        }
        assertNull(node);
    }

    @Test
    @DisplayName("A 2000-node left-only chain (maximum size, maximum height) becomes a right-only chain")
    void maxSizeLeftChain_becomesRightChainOfSameNodes() {
        TreeNode[] nodes = new TreeNode[MAX_NODES];
        for (int i = 0; i < MAX_NODES; i++) {
            nodes[i] = new TreeNode(i % (MAX_VALUE - MIN_VALUE + 1) + MIN_VALUE);
            if (i > 0) {
                nodes[i - 1].left = nodes[i];
            }
        }
        flatten(nodes[0]);
        TreeNode node = nodes[0];
        for (TreeNode original : nodes) {
            assertSame(original, node);
            assertNull(node.left);
            node = node.right;
        }
        assertNull(node);
    }

    @Test
    @DisplayName("Every tree shape with 0 to 7 nodes (626 shapes) gives the same result as the oracle")
    void allShapesUpToSevenNodes_matchOracle() {
        int shapes = 0;
        for (int n = 0; n <= 7; n++) {
            for (TreeNode shape : allShapes(n)) {
                Integer[] spec = labelInLevelOrder(toLevelOrder(shape));
                TreeNode actual = buildTree(spec);
                TreeNode expected = buildTree(spec);
                flatten(actual);
                flattenReference(expected);
                assertEquals(toLevelOrder(expected), toLevelOrder(actual), "shape " + Arrays.toString(spec));
                shapes++;
            }
        }
        assertEquals(626, shapes);
    }

    @Test
    @DisplayName("300 seeded random trees of 0 to 2000 nodes, bushy to path-like, give the oracle's chain")
    void randomTreesUpToMaxSize_matchOracle() {
        Random random = new Random(114L);
        double[] pathBias = { 0.0, 0.5, 0.9, 1.0 };
        for (int trial = 0; trial < 300; trial++) {
            int n = random.nextInt(MAX_NODES + 1);
            Integer[] spec = toLevelOrder(randomTree(n, pathBias[trial % pathBias.length], random))
                    .toArray(new Integer[0]);
            TreeNode actual = buildTree(spec);
            TreeNode expected = buildTree(spec);
            flatten(actual);
            flattenReference(expected);
            assertEquals(readRightChain(expected, n), readRightChain(actual, n), "trial " + trial);
        }
    }

    /**
     * Builds a tree from LeetCode level-order notation, where null marks a missing
     * child.
     */
    private static TreeNode buildTree(Integer... levelOrder) {
        if (levelOrder.length == 0 || levelOrder[0] == null) {
            return null;
        }
        TreeNode root = new TreeNode(levelOrder[0]);
        Deque<TreeNode> parents = new ArrayDeque<>();
        parents.add(root);
        int i = 1;
        while (i < levelOrder.length) {
            TreeNode parent = parents.poll();
            if (levelOrder[i] != null) {
                parent.left = new TreeNode(levelOrder[i]);
                parents.add(parent.left);
            }
            i++;
            if (i < levelOrder.length && levelOrder[i] != null) {
                parent.right = new TreeNode(levelOrder[i]);
                parents.add(parent.right);
            }
            i++;
        }
        return root;
    }

    /**
     * Serializes a tree to LeetCode level-order notation with trailing nulls
     * removed.
     */
    private static List<Integer> toLevelOrder(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        // ArrayDeque rejects null, so missing children are recorded directly instead of
        // being queued.
        Deque<TreeNode> queue = new ArrayDeque<>();
        if (root != null) {
            queue.add(root);
            result.add(root.val);
        }
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            for (TreeNode child : new TreeNode[] { node.left, node.right }) {
                result.add(child == null ? null : child.val);
                if (child != null) {
                    queue.add(child);
                }
            }
        }
        while (!result.isEmpty() && result.get(result.size() - 1) == null) {
            result.remove(result.size() - 1);
        }
        return result;
    }

    /**
     * Reads values along right links, failing on a non-null left link or on more
     * than maxNodes nodes,
     * so a cycle introduced by a faulty rewiring fails instead of looping forever.
     */
    private static List<Integer> readRightChain(TreeNode head, int maxNodes) {
        List<Integer> values = new ArrayList<>();
        for (TreeNode node = head; node != null; node = node.right) {
            assertTrue(values.size() < maxNodes, "chain has more than " + maxNodes + " nodes");
            assertTrue(node.left == null, "left link of chain position " + values.size() + " is not null");
            values.add(node.val);
        }
        return values;
    }

    /**
     * Collects node objects in pre-order by recursion, independent of both
     * implementations under test.
     */
    private static List<TreeNode> preorderNodes(TreeNode root) {
        List<TreeNode> nodes = new ArrayList<>();
        collectPreorder(root, nodes);
        return nodes;
    }

    private static void collectPreorder(TreeNode node, List<TreeNode> nodes) {
        if (node == null) {
            return;
        }
        nodes.add(node);
        collectPreorder(node.left, nodes);
        collectPreorder(node.right, nodes);
    }

    /**
     * Returns every distinct binary tree shape with n nodes (Catalan(n) shapes);
     * subtrees are shared
     * between shapes, so the result is only serialized, never flattened.
     */
    private static List<TreeNode> allShapes(int n) {
        List<TreeNode> shapes = new ArrayList<>();
        if (n == 0) {
            shapes.add(null);
            return shapes;
        }
        for (int leftSize = 0; leftSize < n; leftSize++) {
            for (TreeNode left : allShapes(leftSize)) {
                for (TreeNode right : allShapes(n - 1 - leftSize)) {
                    TreeNode root = new TreeNode(0);
                    root.left = left;
                    root.right = right;
                    shapes.add(root);
                }
            }
        }
        return shapes;
    }

    /**
     * Replaces the values of a level-order shape by 1, 2, 3, ... so every node has
     * a distinct value.
     */
    private static Integer[] labelInLevelOrder(List<Integer> shape) {
        Integer[] spec = shape.toArray(new Integer[0]);
        int label = 1;
        for (int i = 0; i < spec.length; i++) {
            if (spec[i] != null) {
                spec[i] = label++;
            }
        }
        return spec;
    }

    /**
     * Builds a random tree by adding one node at a time. With probability pathBias
     * the new node goes
     * into a free child link of the previously added node (long paths); otherwise
     * into a uniformly
     * chosen free child link of any node (bushy trees).
     */
    private static TreeNode randomTree(int n, double pathBias, Random random) {
        if (n == 0) {
            return null;
        }
        TreeNode root = new TreeNode(randomValue(random));
        List<TreeNode> withFreeSide = new ArrayList<>(List.of(root));
        for (int i = 1; i < n; i++) {
            int parentIndex = random.nextDouble() < pathBias
                    ? withFreeSide.size() - 1
                    : random.nextInt(withFreeSide.size());
            TreeNode parent = withFreeSide.get(parentIndex);
            TreeNode child = new TreeNode(randomValue(random));
            boolean useLeft = parent.left == null && (parent.right != null || random.nextBoolean());
            if (useLeft) {
                parent.left = child;
            } else {
                parent.right = child;
            }
            if (parent.left != null && parent.right != null) {
                withFreeSide.set(parentIndex, withFreeSide.get(withFreeSide.size() - 1));
                withFreeSide.remove(withFreeSide.size() - 1);
            }
            withFreeSide.add(child);
        }
        return root;
    }

    private static int randomValue(Random random) {
        return MIN_VALUE + random.nextInt(MAX_VALUE - MIN_VALUE + 1);
    }
}