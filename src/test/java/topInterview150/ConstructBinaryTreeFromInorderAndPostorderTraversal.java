package topInterview150;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Given two integer arrays inorder and postorder where inorder is the inorder
 * traversal of a binary tree and postorder is the postorder traversal of the
 * same tree, construct and return the binary tree.
 * 
 * 
 * ? Example 1:
 * 
 * 
 * Input: inorder = [9,3,15,20,7], postorder = [9,15,7,20,3]
 * Output: [3,9,20,null,null,15,7]
 * 
 * ? Example 2:
 * 
 * Input: inorder = [-1], postorder = [-1]
 * Output: [-1]
 * 
 * 
 * ! Constraints:
 * 
 * 1 <= inorder.length <= 3000
 * postorder.length == inorder.length
 * -3000 <= inorder[i], postorder[i] <= 3000
 * inorder and postorder consist of unique values.
 * Each value of postorder also appears in inorder.
 * inorder is guaranteed to be the inorder traversal of the tree.
 * postorder is guaranteed to be the postorder traversal of the tree.
 * ConstructBinaryTreeFromInorderAndPostorderTraversal
 */
public class ConstructBinaryTreeFromInorderAndPostorderTraversal {

    // ------------------------------------------------------------------
    // Solution
    // ------------------------------------------------------------------

    public static final class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    /**
     * Builds the unique tree whose inorder and postorder traversals equal the
     * inputs.
     *
     * * Time: O(n) in best, average and worst case - one map insertion per value,
     * then each
     * postorder element is consumed exactly once with an O(1) expected map lookup.
     * * Space: O(n) in every case - the HashMap holds n entries; the recursion
     * stack
     * adds
     * O(log n) for a balanced tree (best/average) and O(n) for a skewed chain
     * (worst),
     * which never exceeds the map. The returned tree (n nodes) is output, not
     * auxiliary.
     *
     * @param inorder   inorder traversal (left, root, right) with unique values
     * @param postorder postorder traversal (left, right, root) of the same tree
     * @return root of the reconstructed tree
     */
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        Map<Integer, Integer> inorderIndexOf = new HashMap<>(inorder.length * 2);
        for (int i = 0; i < inorder.length; i++) {
            inorderIndexOf.put(inorder[i], i);
        }
        int[] postCursor = { postorder.length - 1 };
        return buildRange(postorder, inorderIndexOf, postCursor, 0, inorder.length - 1);
    }

    /**
     * Builds the subtree whose inorder values occupy inorder[lo..hi].
     *
     * * Time: O(hi - lo + 1) - one node is created per inorder position in the
     * range.
     * * Space: O(recursion depth) - one stack frame per level of the subtree being
     * built.
     *
     * @param postorder      postorder traversal, consumed from the end
     * @param inorderIndexOf map from node value to its index in inorder
     * @param postCursor     single-cell holder of the next unconsumed postorder
     *                       index
     * @param lo             inclusive left bound of the inorder range
     * @param hi             inclusive right bound of the inorder range
     * @return root of the subtree, or null when the range is empty
     */
    private TreeNode buildRange(int[] postorder, Map<Integer, Integer> inorderIndexOf,
            int[] postCursor, int lo, int hi) {
        if (lo > hi) {
            return null;
        }
        int rootValue = postorder[postCursor[0]--];
        TreeNode root = new TreeNode(rootValue);
        int mid = inorderIndexOf.get(rootValue);
        root.right = buildRange(postorder, inorderIndexOf, postCursor, mid + 1, hi);
        root.left = buildRange(postorder, inorderIndexOf, postCursor, lo, mid - 1);
        return root;
    }

    // ------------------------------------------------------------------
    // Reference implementation (oracle)
    // ------------------------------------------------------------------

    /**
     * Copies explicit sub-arrays for every subtree and finds the root by linear
     * scan;
     * it shares no cursor, no map and no build order with the solution.
     *
     * * Time: best/average O(n log n) for a balanced tree - O(n) scan+copy work per
     * level, log n levels;
     * worst O(n^2) for a skewed chain - levels of size n, n-1, ..., 1 are scanned
     * and copied.
     * * Space: best/average O(n) - copies on one root-to-leaf path halve in size (n
     * +
     * n/2 + ...);
     * worst O(n^2) - every frame on a depth-n path keeps its own copy alive (n +
     * (n-1) + ...).
     *
     * @param inorder   inorder traversal with unique values (may be empty inside
     *                  recursion)
     * @param postorder postorder traversal of the same tree
     * @return root of the reconstructed tree, or null for empty input
     */
    public TreeNode buildTreeReference(int[] inorder, int[] postorder) {
        int n = inorder.length;
        if (n == 0) {
            return null;
        }
        int rootValue = postorder[n - 1];
        int k = 0;
        while (inorder[k] != rootValue) {
            k++;
        }
        TreeNode root = new TreeNode(rootValue);
        root.left = buildTreeReference(Arrays.copyOfRange(inorder, 0, k),
                Arrays.copyOfRange(postorder, 0, k));
        root.right = buildTreeReference(Arrays.copyOfRange(inorder, k + 1, n),
                Arrays.copyOfRange(postorder, k, n - 1));
        return root;
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------

    @Test
    @DisplayName("Example 1 builds [3,9,20,null,null,15,7]")
    void example1_returnsLevelOrderWithNulls() {
        TreeNode root = buildTree(new int[] { 9, 3, 15, 20, 7 }, new int[] { 9, 15, 7, 20, 3 });
        assertEquals(Arrays.asList(3, 9, 20, null, null, 15, 7), levelOrder(root));
    }

    @Test
    @DisplayName("Example 2: a single value becomes a single leaf node")
    void singleNode_returnsLeaf() {
        TreeNode root = buildTree(new int[] { -1 }, new int[] { -1 });
        assertEquals(Arrays.asList(-1), levelOrder(root));
    }

    @Test
    @DisplayName("Identical inorder and postorder produce a left-only chain")
    void leftSkewedChain_returnsLeftOnlyPath() {
        TreeNode root = buildTree(new int[] { 1, 2, 3 }, new int[] { 1, 2, 3 });
        assertEquals(Arrays.asList(3, 2, null, 1), levelOrder(root));
    }

    @Test
    @DisplayName("Reversed postorder produces a right-only chain")
    void rightSkewedChain_returnsRightOnlyPath() {
        TreeNode root = buildTree(new int[] { 1, 2, 3 }, new int[] { 3, 2, 1 });
        assertEquals(Arrays.asList(1, null, 2, null, 3), levelOrder(root));
    }

    @Test
    @DisplayName("Boundary values -3000, 0 and 3000 are placed by identity, not by ordering")
    void boundaryValues_returnsTreeIndependentOfValueOrder() {
        TreeNode root = buildTree(new int[] { 3000, -3000, 0 }, new int[] { 3000, 0, -3000 });
        assertEquals(Arrays.asList(-3000, 3000, 0), levelOrder(root));
    }

    @Test
    @DisplayName("Input arrays are left unchanged after building")
    void inputArrays_areNotMutated() {
        int[] inorder = { 9, 3, 15, 20, 7 };
        int[] postorder = { 9, 15, 7, 20, 3 };
        buildTree(inorder, postorder);
        assertArrayEquals(new int[] { 9, 3, 15, 20, 7 }, inorder);
        assertArrayEquals(new int[] { 9, 15, 7, 20, 3 }, postorder);
    }

    @Test
    @DisplayName("Every tree shape up to 5 nodes under every value labeling is rebuilt exactly")
    void allShapesAndLabelingsUpToFiveNodes_rebuildOriginalTree() {
        int checked = 0;
        for (int n = 1; n <= 5; n++) {
            for (TreeNode shape : allShapes(n)) {
                for (int[] labels : permutations(n)) {
                    TreeNode original = relabelInorder(shape, labels);
                    int[] inorder = inorderOf(original);
                    int[] postorder = postorderOf(original);
                    List<Integer> expected = levelOrder(original);
                    assertEquals(expected, levelOrder(buildTree(inorder, postorder)));
                    assertEquals(expected, levelOrder(buildTreeReference(inorder, postorder)));
                    checked++;
                }
            }
        }
        assertEquals(5411, checked);
    }

    @Test
    @DisplayName("Random trees up to 3000 nodes agree with the oracle and round-trip their traversals")
    void randomTrees_matchOracleAndRoundTrip() {
        Random random = new Random(106);
        for (int trial = 0; trial < 300; trial++) {
            int n = 1 + random.nextInt(trial < 250 ? 60 : 3000);
            TreeNode original = randomTree(n, random);
            int[] inorder = inorderOf(original);
            int[] postorder = postorderOf(original);
            TreeNode built = buildTree(inorder, postorder);
            assertEquals(levelOrder(original), levelOrder(built));
            assertEquals(levelOrder(buildTreeReference(inorder, postorder)), levelOrder(built));
            assertArrayEquals(inorder, inorderOf(built));
            assertArrayEquals(postorder, postorderOf(built));
        }
    }

    @Test
    @DisplayName("A 3000-node chain in either direction is built without overflowing the stack")
    void maxLengthSkewedChains_buildFullDepth() {
        int n = 3000;
        int[] ascending = new int[n];
        int[] descending = new int[n];
        for (int i = 0; i < n; i++) {
            ascending[i] = i - 1500;
            descending[i] = 1499 - i;
        }
        TreeNode left = buildTree(ascending, ascending);
        TreeNode right = buildTree(ascending, descending);
        assertEquals(n, depthOf(left));
        assertEquals(n, depthOf(right));
        assertArrayEquals(ascending, postorderOf(left));
        assertArrayEquals(descending, postorderOf(right));
    }

    private static List<Integer> levelOrder(TreeNode root) {
        List<Integer> out = new ArrayList<>();
        List<TreeNode> level = new ArrayList<>();
        level.add(root);
        while (!level.isEmpty()) {
            List<TreeNode> next = new ArrayList<>();
            for (TreeNode node : level) {
                if (node == null) {
                    out.add(null);
                } else {
                    out.add(node.val);
                    next.add(node.left);
                    next.add(node.right);
                }
            }
            level = next;
        }
        while (!out.isEmpty() && out.get(out.size() - 1) == null) {
            out.remove(out.size() - 1);
        }
        return out;
    }

    private static int[] inorderOf(TreeNode root) {
        List<Integer> out = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode node = root;
        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
            node = stack.pop();
            out.add(node.val);
            node = node.right;
        }
        return out.stream().mapToInt(Integer::intValue).toArray();
    }

    private static int[] postorderOf(TreeNode root) {
        List<Integer> reversed = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        if (root != null) {
            stack.push(root);
        }
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            reversed.add(node.val);
            if (node.left != null) {
                stack.push(node.left);
            }
            if (node.right != null) {
                stack.push(node.right);
            }
        }
        int[] out = new int[reversed.size()];
        for (int i = 0; i < out.length; i++) {
            out[i] = reversed.get(out.length - 1 - i);
        }
        return out;
    }

    private static int depthOf(TreeNode root) {
        int depth = 0;
        List<TreeNode> level = new ArrayList<>();
        if (root != null) {
            level.add(root);
        }
        while (!level.isEmpty()) {
            depth++;
            List<TreeNode> next = new ArrayList<>();
            for (TreeNode node : level) {
                if (node.left != null) {
                    next.add(node.left);
                }
                if (node.right != null) {
                    next.add(node.right);
                }
            }
            level = next;
        }
        return depth;
    }

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

    private static List<int[]> permutations(int n) {
        List<int[]> out = new ArrayList<>();
        permute(new int[n], new boolean[n], 0, out);
        return out;
    }

    private static void permute(int[] current, boolean[] used, int pos, List<int[]> out) {
        if (pos == current.length) {
            out.add(current.clone());
            return;
        }
        for (int v = 0; v < current.length; v++) {
            if (!used[v]) {
                used[v] = true;
                current[pos] = v * 1500 - 3000;
                permute(current, used, pos + 1, out);
                used[v] = false;
            }
        }
    }

    private static TreeNode relabelInorder(TreeNode shape, int[] labels) {
        int[] next = { 0 };
        return copyWithLabels(shape, labels, next);
    }

    private static TreeNode copyWithLabels(TreeNode shape, int[] labels, int[] next) {
        if (shape == null) {
            return null;
        }
        TreeNode left = copyWithLabels(shape.left, labels, next);
        TreeNode node = new TreeNode(labels[next[0]++]);
        node.left = left;
        node.right = copyWithLabels(shape.right, labels, next);
        return node;
    }

    private static TreeNode randomTree(int n, Random random) {
        List<Integer> pool = new ArrayList<>();
        for (int v = -3000; v <= 3000; v++) {
            pool.add(v);
        }
        Collections.shuffle(pool, random);
        int[] labels = new int[n];
        for (int i = 0; i < n; i++) {
            labels[i] = pool.get(i);
        }
        return relabelInorder(randomShape(n, random), labels);
    }

    private static TreeNode randomShape(int n, Random random) {
        if (n == 0) {
            return null;
        }
        int leftSize = switch (random.nextInt(4)) {
            case 0 -> 0;
            case 1 -> n - 1;
            default -> random.nextInt(n);
        };
        TreeNode root = new TreeNode(0);
        root.left = randomShape(leftSize, random);
        root.right = randomShape(n - 1 - leftSize, random);
        return root;
    }
}