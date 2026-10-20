package topInterview150;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayDeque;
import java.util.Deque;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SymmetricTree {

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

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * Recursive version.
     *
     * * Time: O(n) worst - isMirror visits each node pair at most once; O(1) best
     * when the root's two children already differ
     * * Space: O(h) - one call frame per level of the pair recursion; O(log n) for
     * a
     * balanced tree, O(n) for two mirrored chains
     *
     * @param root root of the tree, never null (1 <= n)
     * @return true if the tree is a mirror image of itself
     */
    public boolean isSymmetric(TreeNode root) {
        return isMirror(root.left, root.right);
    }

    /**
     * Checks whether subtree a is the mirror image of subtree b.
     *
     * * Time: O(min(|a|, |b|)) - each call reads one node from each side and stops
     * at
     * the first mismatch
     * * Space: O(h) - recursion depth equals the depth of the shallower matching
     * path
     *
     * @param a node on the left side of the mirror
     * @param b node on the right side of the mirror
     * @return true if a and b are mirror images
     */
    private static boolean isMirror(TreeNode a, TreeNode b) {
        if (a == null && b == null) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        return a.val == b.val
                && isMirror(a.left, b.right)
                && isMirror(a.right, b.left);
    }

    /**
     * Iterative version using a FIFO queue of node pairs.
     *
     * * Time: O(n) worst - every node is enqueued and dequeued at most once; O(1)
     * best when the root's children differ
     * * Space: O(w) - the queue holds at most about two levels of pairs; O(1) for
     * two
     * mirrored chains, O(n) for a complete tree
     *
     * @param root root of the tree, never null (1 <= n)
     * @return true if the tree is a mirror image of itself
     */
    public boolean isSymmetricIterative(TreeNode root) {
        Deque<TreeNode> queue = new ArrayDeque<>();
        if (!offerPair(queue, root.left, root.right)) {
            return false;
        }
        while (!queue.isEmpty()) {
            TreeNode a = queue.pollFirst();
            TreeNode b = queue.pollFirst();
            if (!offerPair(queue, a.left, b.right) || !offerPair(queue, a.right, b.left)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Validates one mirror pair and enqueues it only if both nodes exist.
     *
     * Time: O(1) - two null checks, one value comparison, at most two offers
     * Space: O(1) - adds at most two references to the existing queue
     *
     * @param queue pending pairs, stored as consecutive elements
     * @param a     node on the left side of the mirror
     * @param b     node on the right side of the mirror
     * @return false if the pair already breaks symmetry
     */
    private static boolean offerPair(Deque<TreeNode> queue, TreeNode a, TreeNode b) {
        if (a == null && b == null) {
            return true;
        }
        if (a == null || b == null || a.val != b.val) {
            return false;
        }
        queue.offerLast(a);
        queue.offerLast(b);
        return true;
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------

    @Test
    @DisplayName("Example 1 [1,2,2,3,4,4,3] is symmetric")
    void example1_returnsTrue() {
        TreeNode root = tree(1, 2, 2, 3, 4, 4, 3);
        assertTrue(isSymmetric(root));
        assertTrue(isSymmetricIterative(root));
    }

    @Test
    @DisplayName("Example 2 [1,2,2,null,3,null,3] has matching values but mirrored positions differ")
    void example2_returnsFalse() {
        TreeNode root = tree(1, 2, 2, null, 3, null, 3);
        assertFalse(isSymmetric(root));
        assertFalse(isSymmetricIterative(root));
    }

    @Test
    @DisplayName("A single node is symmetric because both children are null")
    void singleNode_returnsTrue() {
        TreeNode root = tree(7);
        assertTrue(isSymmetric(root));
        assertTrue(isSymmetricIterative(root));
    }

    @Test
    @DisplayName("A root with only a left child is not symmetric")
    void onlyLeftChild_returnsFalse() {
        TreeNode root = tree(1, 2);
        assertFalse(isSymmetric(root));
        assertFalse(isSymmetricIterative(root));
    }

    @Test
    @DisplayName("Same shape with different mirrored values [1,2,3] is not symmetric")
    void sameShapeDifferentValues_returnsFalse() {
        TreeNode root = tree(1, 2, 3);
        assertFalse(isSymmetric(root));
        assertFalse(isSymmetricIterative(root));
    }

    @Test
    @DisplayName("Identical (not mirrored) subtrees [1,2,2,3,4,3,4] are not symmetric")
    void identicalSubtrees_returnsFalse() {
        TreeNode root = tree(1, 2, 2, 3, 4, 3, 4);
        assertFalse(isSymmetric(root));
        assertFalse(isSymmetricIterative(root));
    }

    @Test
    @DisplayName("Inner pair mismatch deep in the tree [1,2,2,3,4,5,3] is detected")
    void innerPairMismatch_returnsFalse() {
        TreeNode root = tree(1, 2, 2, 3, 4, 5, 3);
        assertFalse(isSymmetric(root));
        assertFalse(isSymmetricIterative(root));
    }

    @Test
    @DisplayName("Negative values at the constraint bounds mirror correctly")
    void negativeBoundaryValues_returnsTrue() {
        TreeNode root = tree(-100, 100, 100, -100, null, null, -100);
        assertTrue(isSymmetric(root));
        assertTrue(isSymmetricIterative(root));
    }

    @Test
    @DisplayName("Two mirrored chains with 999 nodes in total are symmetric")
    void deepMirroredChains_returnsTrue() {
        TreeNode root = mirroredChains(499);
        assertTrue(isSymmetric(root));
        assertTrue(isSymmetricIterative(root));
    }

    @Test
    @DisplayName("Two mirrored chains differing only at the deepest right node are not symmetric")
    void deepChainsLastNodeDiffers_returnsFalse() {
        TreeNode root = mirroredChains(499);
        TreeNode node = root.right;
        while (node.right != null) {
            node = node.right;
        }
        node.val = node.val + 1;
        assertFalse(isSymmetric(root));
        assertFalse(isSymmetricIterative(root));
    }

    /**
     * Builds a tree from LeetCode level-order notation, where null marks a missing
     * child.
     *
     * Time: O(k) - each array entry is read once
     * Space: O(k) - one node per non-null entry plus the parent queue
     *
     * @param levelOrder values in LeetCode level-order notation; the first entry is
     *                   non-null
     * @return root of the built tree
     */
    private static TreeNode tree(Integer... levelOrder) {
        TreeNode root = new TreeNode(levelOrder[0]);
        Deque<TreeNode> parents = new ArrayDeque<>();
        parents.offerLast(root);
        int i = 1;
        while (i < levelOrder.length) {
            TreeNode parent = parents.pollFirst();
            if (levelOrder[i] != null) {
                parent.left = new TreeNode(levelOrder[i]);
                parents.offerLast(parent.left);
            }
            i++;
            if (i < levelOrder.length && levelOrder[i] != null) {
                parent.right = new TreeNode(levelOrder[i]);
                parents.offerLast(parent.right);
            }
            i++;
        }
        return root;
    }

    /**
     * Builds a root whose left subtree is a left-going chain and whose right
     * subtree is a right-going chain.
     *
     * Time: O(depth) - two nodes are created per level
     * Space: O(depth) - 2 * depth + 1 nodes
     *
     * @param depth number of nodes in each chain
     * @return root of a symmetric tree of height depth
     */
    private static TreeNode mirroredChains(int depth) {
        TreeNode root = new TreeNode(0);
        TreeNode left = root;
        TreeNode right = root;
        for (int level = 1; level <= depth; level++) {
            left.left = new TreeNode(level % 201 - 100);
            right.right = new TreeNode(level % 201 - 100);
            left = left.left;
            right = right.right;
        }
        return root;
    }
}