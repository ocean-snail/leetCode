package topInterview150;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Given two integer arrays preorder and inorder where preorder is the preorder
 * traversal of a binary tree and inorder is the inorder traversal of the same
 * tree, construct and return the binary tree.
 * 
 * 
 * ? Example 1:
 * 
 * 
 * Input: preorder = [3,9,20,15,7], inorder = [9,3,15,20,7]
 * Output: [3,9,20,null,null,15,7]
 * 
 * ? Example 2:
 * 
 * Input: preorder = [-1], inorder = [-1]
 * Output: [-1]
 * 
 * 
 * ! Constraints:
 * 
 * 1 <= preorder.length <= 3000
 * inorder.length == preorder.length
 * -3000 <= preorder[i], inorder[i] <= 3000
 * preorder and inorder consist of unique values.
 * Each value of inorder also appears in preorder.
 * preorder is guaranteed to be the preorder traversal of the tree.
 * inorder is guaranteed to be the inorder traversal of the tree.
 */
public class ConstructBinaryTreeFromPreorderAndInorderTraversal {

    // ------------------------------------------------------------------
    // Solution
    // ------------------------------------------------------------------

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    /**
     * Builds the unique tree whose preorder and inorder traversals are the given
     * arrays.
     *
     * * Time: O(n) - one map insertion per inorder element, then one node creation
     * and one O(1) map lookup per preorder element
     * * Space: O(n) - the value-to-index HashMap holds n entries; the recursion
     * stack
     * adds O(h),
     * which is O(log n) for a balanced tree (best case) and O(n) for a skewed chain
     * (worst case)
     *
     * @param preorder root-left-right visiting order of the tree, length n >= 1,
     *                 unique values
     * @param inorder  left-root-right visiting order of the same tree
     * @return root of the reconstructed tree
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        Map<Integer, Integer> inorderIndex = new HashMap<>(inorder.length * 2);
        for (int i = 0; i < inorder.length; i++) {
            inorderIndex.put(inorder[i], i);
        }
        int[] nextPreorder = { 0 };
        return buildRange(preorder, inorderIndex, nextPreorder, 0, inorder.length - 1);
    }

    /**
     * Builds the subtree whose nodes occupy inorder[lo..hi].
     *
     * * Time: O(hi - lo + 1) - each call creates exactly one node, plus one empty
     * call per missing child
     * * Space: O(h) - one stack frame per level of the subtree being built
     *
     * @param preorder     the full preorder array
     * @param inorderIndex maps each value to its position in the inorder array
     * @param nextPreorder nextPreorder[0] is the index of the next unread preorder
     *                     element
     * @param lo           first inorder index of this subtree (inclusive)
     * @param hi           last inorder index of this subtree (inclusive)
     * @return root of the subtree, or null when the range is empty
     */
    private TreeNode buildRange(int[] preorder, Map<Integer, Integer> inorderIndex, int[] nextPreorder, int lo,
            int hi) {
        if (lo > hi) {
            return null;
        }
        int rootValue = preorder[nextPreorder[0]++];
        TreeNode root = new TreeNode(rootValue);
        int mid = inorderIndex.get(rootValue);
        root.left = buildRange(preorder, inorderIndex, nextPreorder, lo, mid - 1);
        root.right = buildRange(preorder, inorderIndex, nextPreorder, mid + 1, hi);
        return root;
    }

    // ------------------------------------------------------------------
    // Reference implementation (oracle)
    // ------------------------------------------------------------------

    /**
     * Iterative stack construction: no recursion, no index map, no inorder range
     * bounds.
     * Each new preorder value becomes the left child of the stack top until the
     * stack top equals
     * inorder[in]; then finished nodes are popped and the value becomes the right
     * child of the last popped node.
     *
     * * Time: O(n) - each node is pushed once and popped at most once
     * * Space: O(h) - the stack holds at most one root-to-leaf path; best case O(1)
     * for a right-skewed chain,
     * O(log n) for a balanced tree, worst case O(n) for a left-skewed chain
     *
     * @param preorder root-left-right visiting order of the tree
     * @param inorder  left-root-right visiting order of the same tree
     * @return root of the reconstructed tree
     */
    public TreeNode buildTreeReference(int[] preorder, int[] inorder) {
        TreeNode root = new TreeNode(preorder[0]);
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);
        int in = 0;
        for (int p = 1; p < preorder.length; p++) {
            TreeNode top = stack.peek();
            if (top.val != inorder[in]) {
                top.left = new TreeNode(preorder[p]);
                stack.push(top.left);
            } else {
                TreeNode parent = null;
                while (!stack.isEmpty() && stack.peek().val == inorder[in]) {
                    parent = stack.pop();
                    in++;
                }
                parent.right = new TreeNode(preorder[p]);
                stack.push(parent.right);
            }
        }
        return root;
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------

    @Test
    @DisplayName("Example 1 produces LeetCode level order [3,9,20,null,null,15,7]")
    void example1_returnsLeetCodeLevelOrder() {
        TreeNode root = buildTree(new int[] { 3, 9, 20, 15, 7 }, new int[] { 9, 3, 15, 20, 7 });
        assertEquals(Arrays.asList(3, 9, 20, null, null, 15, 7), levelOrder(root));
    }

    @Test
    @DisplayName("Example 2: a single node -1 produces [-1]")
    void singleNode_returnsSingleNodeTree() {
        TreeNode root = buildTree(new int[] { -1 }, new int[] { -1 });
        assertEquals(Arrays.asList(-1), levelOrder(root));
    }

    @Test
    @DisplayName("Inorder equal to reversed preorder builds a left-only chain")
    void leftSkewedChain_buildsLeftOnlyLinks() {
        TreeNode root = buildTree(new int[] { 1, 2, 3, 4 }, new int[] { 4, 3, 2, 1 });
        assertEquals(Arrays.asList(1, 2, null, 3, null, 4), levelOrder(root));
    }

    @Test
    @DisplayName("Inorder equal to preorder builds a right-only chain")
    void rightSkewedChain_buildsRightOnlyLinks() {
        TreeNode root = buildTree(new int[] { 1, 2, 3, 4 }, new int[] { 1, 2, 3, 4 });
        assertEquals(Arrays.asList(1, null, 2, null, 3, null, 4), levelOrder(root));
    }

    @Test
    @DisplayName("Left-right-left path: 1 has left 2, 2 has right 3, 3 has left 4")
    void zigzagPath_alternatesChildSides() {
        TreeNode root = buildTree(new int[] { 1, 2, 3, 4 }, new int[] { 2, 4, 3, 1 });
        assertEquals(Arrays.asList(1, 2, null, null, 3, 4), levelOrder(root));
    }

    @Test
    @DisplayName("Sorted inorder 1..7 with preorder 4,2,1,3,6,5,7 builds a perfect tree of height 3")
    void perfectTreeOfSeven_buildsBalancedTree() {
        TreeNode root = buildTree(new int[] { 4, 2, 1, 3, 6, 5, 7 }, new int[] { 1, 2, 3, 4, 5, 6, 7 });
        assertEquals(Arrays.asList(4, 2, 6, 1, 3, 5, 7), levelOrder(root));
    }

    @Test
    @DisplayName("Boundary values -3000 and 3000 are placed as left and right children of 0")
    void extremeValues_placedAsChildren() {
        TreeNode root = buildTree(new int[] { 0, -3000, 3000 }, new int[] { -3000, 0, 3000 });
        assertEquals(Arrays.asList(0, -3000, 3000), levelOrder(root));
    }

    @Test
    @DisplayName("The caller's preorder and inorder arrays are left unchanged")
    void inputArrays_areNotModified() {
        int[] preorder = { 3, 9, 20, 15, 7 };
        int[] inorder = { 9, 3, 15, 20, 7 };
        buildTree(preorder, inorder);
        assertArrayEquals(new int[] { 3, 9, 20, 15, 7 }, preorder);
        assertArrayEquals(new int[] { 9, 3, 15, 20, 7 }, inorder);
    }

    @Test
    @DisplayName("Left- and right-skewed chains of 3000 nodes build without StackOverflowError and round-trip")
    void maxLengthChains_buildAndRoundTrip() {
        int n = 3000;
        int[] ascending = new int[n];
        int[] descending = new int[n];
        for (int i = 0; i < n; i++) {
            ascending[i] = i - 1500;
            descending[i] = 1499 - i;
        }
        assertTraversalsMatch(ascending, descending, buildTree(ascending, descending));
        assertTraversalsMatch(ascending, ascending, buildTree(ascending, ascending));
    }

    @Test
    @DisplayName("Every tree shape with 1 to 7 nodes (625 shapes) matches the reference and round-trips")
    void allShapesUpToSevenNodes_matchReference() {
        Random random = new Random(105);
        int checked = 0;
        for (int n = 1; n <= 7; n++) {
            for (TreeNode shape : allShapes(n)) {
                TreeNode labeled = labelInPreorder(shape, distinctValues(n, random));
                int[] preorder = preorderOf(labeled);
                int[] inorder = inorderOf(labeled);
                TreeNode built = buildTree(preorder, inorder);
                assertEquals(levelOrder(labeled), levelOrder(built));
                assertEquals(levelOrder(buildTreeReference(preorder, inorder)), levelOrder(built));
                checked++;
            }
        }
        assertEquals(625, checked);
    }

    @Test
    @DisplayName("300 random trees of up to 3000 nodes match the reference and round-trip")
    void randomTrees_matchReferenceAndRoundTrip() {
        Random random = new Random(20260928);
        for (int trial = 0; trial < 300; trial++) {
            int n = 1 + random.nextInt(3000);
            TreeNode labeled = labelInPreorder(randomShape(n, random), distinctValues(n, random));
            int[] preorder = preorderOf(labeled);
            int[] inorder = inorderOf(labeled);
            TreeNode built = buildTree(preorder, inorder);
            assertTraversalsMatch(preorder, inorder, built);
            assertEquals(levelOrder(buildTreeReference(preorder, inorder)), levelOrder(built));
        }
    }

    /**
     * Asserts that the tree's own traversals reproduce the input arrays; with
     * unique values this pins the tree.
     *
     * Time: O(n) - two full traversals
     * Space: O(n) - two traversal arrays plus O(h) recursion
     *
     * @param preorder expected preorder
     * @param inorder  expected inorder
     * @param root     tree under test
     */
    private static void assertTraversalsMatch(int[] preorder, int[] inorder, TreeNode root) {
        assertArrayEquals(preorder, preorderOf(root));
        assertArrayEquals(inorder, inorderOf(root));
    }

    /**
     * LeetCode serialization: breadth-first values with null for missing children,
     * trailing nulls removed.
     *
     * Time: O(n) - each node and each null child is enqueued once
     * Space: O(n) - the queue and the output list
     *
     * @param root tree to serialize
     * @return level-order list with null placeholders
     */
    private static List<Integer> levelOrder(TreeNode root) {
        List<Integer> out = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node == null) {
                out.add(null);
                continue;
            }
            out.add(node.val);
            queue.add(node.left);
            queue.add(node.right);
        }
        while (!out.isEmpty() && out.get(out.size() - 1) == null) {
            out.remove(out.size() - 1);
        }
        return out;
    }

    /**
     * Preorder traversal of the tree.
     *
     * Time: O(n) - each node visited once
     * Space: O(n) - output list plus O(h) recursion
     *
     * @param root tree to traverse
     * @return values in root-left-right order
     */
    private static int[] preorderOf(TreeNode root) {
        List<Integer> out = new ArrayList<>();
        collectPreorder(root, out);
        return out.stream().mapToInt(Integer::intValue).toArray();
    }

    /**
     * Appends the subtree's values in preorder.
     *
     * Time: O(subtree size) - each node visited once
     * Space: O(h) - recursion depth
     *
     * @param node subtree root, may be null
     * @param out  destination list
     */
    private static void collectPreorder(TreeNode node, List<Integer> out) {
        if (node == null) {
            return;
        }
        out.add(node.val);
        collectPreorder(node.left, out);
        collectPreorder(node.right, out);
    }

    /**
     * Inorder traversal of the tree.
     *
     * Time: O(n) - each node visited once
     * Space: O(n) - output list plus O(h) recursion
     *
     * @param root tree to traverse
     * @return values in left-root-right order
     */
    private static int[] inorderOf(TreeNode root) {
        List<Integer> out = new ArrayList<>();
        collectInorder(root, out);
        return out.stream().mapToInt(Integer::intValue).toArray();
    }

    /**
     * Appends the subtree's values in inorder.
     *
     * Time: O(subtree size) - each node visited once
     * Space: O(h) - recursion depth
     *
     * @param node subtree root, may be null
     * @param out  destination list
     */
    private static void collectInorder(TreeNode node, List<Integer> out) {
        if (node == null) {
            return;
        }
        collectInorder(node.left, out);
        out.add(node.val);
        collectInorder(node.right, out);
    }

    /**
     * Enumerates every binary tree shape with exactly n nodes (Catalan(n) shapes);
     * node values are 0.
     *
     * Time: O(Catalan(n) * n) - each shape is assembled from previously built
     * subtrees
     * Space: O(Catalan(n)) - the returned list; subtrees are shared between shapes
     *
     * @param n number of nodes, n >= 0
     * @return all shapes; for n = 0 a list holding only null
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
     * Random shape: the left subtree size is drawn uniformly from 0..n-1 at every
     * node.
     *
     * Time: O(n) - one node per call
     * Space: O(h) - recursion depth
     *
     * @param n      number of nodes
     * @param random seeded source
     * @return root of the shape, or null when n is 0
     */
    private static TreeNode randomShape(int n, Random random) {
        if (n == 0) {
            return null;
        }
        int leftSize = random.nextInt(n);
        TreeNode root = new TreeNode(0);
        root.left = randomShape(leftSize, random);
        root.right = randomShape(n - 1 - leftSize, random);
        return root;
    }

    /**
     * Copies the shape into new nodes, assigning values[k] to the k-th node visited
     * in preorder.
     *
     * Time: O(n) - one copy per node
     * Space: O(n) - the new tree plus O(h) recursion
     *
     * @param shape  tree whose structure is copied (may share subtrees)
     * @param values distinct values, one per node
     * @return a freshly allocated labeled tree
     */
    private static TreeNode labelInPreorder(TreeNode shape, int[] values) {
        return copyLabeled(shape, values, new int[] { 0 });
    }

    /**
     * Recursive worker for labelInPreorder.
     *
     * Time: O(subtree size) - one copy per node
     * Space: O(h) - recursion depth
     *
     * @param shape  subtree to copy
     * @param values distinct values
     * @param next   next[0] is the index of the next unused value
     * @return copied subtree
     */
    private static TreeNode copyLabeled(TreeNode shape, int[] values, int[] next) {
        if (shape == null) {
            return null;
        }
        TreeNode copy = new TreeNode(values[next[0]++]);
        copy.left = copyLabeled(shape.left, values, next);
        copy.right = copyLabeled(shape.right, values, next);
        return copy;
    }

    /**
     * Draws n distinct values from [-3000, 3000] in random order (partial
     * Fisher-Yates shuffle).
     *
     * Time: O(6001) - the candidate pool is fixed by the value constraint
     * Space: O(6001) - the candidate pool
     *
     * @param n      how many values, n <= 6001
     * @param random seeded source
     * @return n distinct values
     */
    private static int[] distinctValues(int n, Random random) {
        int[] pool = new int[6001];
        for (int i = 0; i < pool.length; i++) {
            pool[i] = i - 3000;
        }
        for (int i = 0; i < n; i++) {
            int j = i + random.nextInt(pool.length - i);
            int tmp = pool[i];
            pool[i] = pool[j];
            pool[j] = tmp;
        }
        return Arrays.copyOf(pool, n);
    }
}