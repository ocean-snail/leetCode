package topInterview150;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Given the root of a binary tree, invert the tree, and return its root.
 * 
 * 
 * Example 1:
 * 
 * 
 * Input: root = [4,2,7,1,3,6,9]
 * Output: [4,7,2,9,6,3,1]
 * Example 2:
 * 
 * 
 * Input: root = [2,1,3]
 * Output: [2,3,1]
 * Example 3:
 * 
 * Input: root = []
 * Output: []
 * 
 * 
 * Constraints:
 * 
 * The number of nodes in the tree is in the range [0, 100].
 * -100 <= Node.val <= 100
 */
public class InvertBinaryTree {

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

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * Inverts the tree recursively by swapping the two child pointers of every
     * node.
     *
     * * Time: O(n) in best, average and worst case - each of the n nodes is entered
     * exactly once
     * and does a constant-time pointer swap.
     * * Space: O(h) - one call frame per level of the current root-to-node path;
     * best O(log n) for a balanced tree, worst O(n) for a skewed chain.
     *
     * @param root root of the tree to invert, or null for an empty tree
     * @return the same root reference, whose subtree is now mirrored in place
     */
    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode invertedLeft = invertTree(root.left);
        TreeNode invertedRight = invertTree(root.right);
        root.left = invertedRight;
        root.right = invertedLeft;
        return root;
    }

    /**
     * Inverts the tree level by level with an explicit FIFO queue instead of the
     * call stack.
     *
     * * Time: O(n) in best, average and worst case - each non-null node is enqueued
     * once and
     * dequeued once, and each dequeue does a constant-time pointer swap.
     * * Space: O(w) - the queue holds at most one level plus part of the next, w =
     * maximum level width;
     * best O(1) for a skewed chain (width 1), worst O(n) for a complete tree (last
     * level about n/2).
     *
     * @param root root of the tree to invert, or null for an empty tree
     * @return the same root reference, whose subtree is now mirrored in place
     */
    public TreeNode invertTreeIterative(TreeNode root) {
        if (root == null) {
            return null;
        }
        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            TreeNode originalLeft = node.left;
            node.left = node.right;
            node.right = originalLeft;
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
        return root;
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------

    @Test
    @DisplayName("Example 1: [4,2,7,1,3,6,9] inverts to [4,7,2,9,6,3,1]")
    void example1_mirroredLevelOrder() {
        TreeNode root = build(4, 2, 7, 1, 3, 6, 9);
        assertEquals(Arrays.asList(4, 7, 2, 9, 6, 3, 1), serialize(invertTree(root)));
    }

    @Test
    @DisplayName("Example 2: [2,1,3] inverts to [2,3,1]")
    void example2_childrenSwapped() {
        TreeNode root = build(2, 1, 3);
        assertEquals(Arrays.asList(2, 3, 1), serialize(invertTree(root)));
    }

    @Test
    @DisplayName("Example 3: an empty tree inverts to an empty tree (null root)")
    void emptyTree_returnsNull() {
        assertEquals(null, invertTree(null));
        assertEquals(null, invertTreeIterative(null));
    }

    @Test
    @DisplayName("A single node has no children to swap and is returned unchanged")
    void singleNode_unchanged() {
        TreeNode root = build(-100);
        assertEquals(Arrays.asList(-100), serialize(invertTree(root)));
    }

    @Test
    @DisplayName("A lone left child moves to the right side: [1,2] inverts to [1,null,2]")
    void loneLeftChild_movesToRight() {
        TreeNode root = build(1, 2);
        assertEquals(Arrays.asList(1, null, 2), serialize(invertTree(root)));
    }

    @Test
    @DisplayName("Asymmetric depth: [1,2,3,4] inverts to [1,3,2,null,null,null,4]")
    void asymmetricDepth_grandchildMovesToFarRight() {
        TreeNode root = build(1, 2, 3, 4);
        assertEquals(Arrays.asList(1, 3, 2, null, null, null, 4), serialize(invertTree(root)));
    }

    @Test
    @DisplayName("A 100-node left-skewed chain becomes a 100-node right-skewed chain")
    void leftSkewedChainOf100_becomesRightSkewedChain() {
        TreeNode root = new TreeNode(1);
        TreeNode tail = root;
        List<Integer> expected = new ArrayList<>();
        expected.add(1);
        for (int value = 2; value <= 100; value++) {
            tail.left = new TreeNode(value);
            tail = tail.left;
            expected.add(null);
            expected.add(value);
        }
        assertEquals(expected, serialize(invertTree(root)));
    }

    @Test
    @DisplayName("The returned root is the same object as the input root, because inversion is in place")
    void inPlace_returnsSameRootReference() {
        TreeNode root = build(4, 2, 7);
        TreeNode leftBefore = root.left;
        TreeNode result = invertTree(root);
        assertTrue(result == root, "root reference must be preserved");
        assertTrue(result.right == leftBefore, "the original left node object must now be the right child");
    }

    @Test
    @DisplayName("Inverting twice restores the original level order")
    void invertTwice_restoresOriginal() {
        TreeNode root = build(5, 3, 8, 1, null, 7, 9, null, 2);
        List<Integer> original = serialize(root);
        assertEquals(original, serialize(invertTree(invertTree(root))));
    }

    @Test
    @DisplayName("The iterative version matches the given examples")
    void iterative_matchesExamples() {
        assertEquals(Arrays.asList(4, 7, 2, 9, 6, 3, 1), serialize(invertTreeIterative(build(4, 2, 7, 1, 3, 6, 9))));
        assertEquals(Arrays.asList(2, 3, 1), serialize(invertTreeIterative(build(2, 1, 3))));
    }

    private static TreeNode build(Integer... levelOrder) {
        if (levelOrder.length == 0 || levelOrder[0] == null) {
            return null;
        }
        TreeNode root = new TreeNode(levelOrder[0]);
        Deque<TreeNode> parents = new ArrayDeque<>();
        parents.offer(root);
        int index = 1;
        while (index < levelOrder.length) {
            TreeNode parent = parents.poll();
            if (levelOrder[index] != null) {
                parent.left = new TreeNode(levelOrder[index]);
                parents.offer(parent.left);
            }
            index++;
            if (index < levelOrder.length && levelOrder[index] != null) {
                parent.right = new TreeNode(levelOrder[index]);
                parents.offer(parent.right);
            }
            index++;
        }
        return root;
    }

    private static List<Integer> serialize(TreeNode root) {
        List<Integer> out = new ArrayList<>();
        List<TreeNode> level = new ArrayList<>();
        level.add(root);
        for (int i = 0; i < level.size(); i++) {
            TreeNode node = level.get(i);
            if (node == null) {
                out.add(null);
            } else {
                out.add(node.val);
                level.add(node.left);
                level.add(node.right);
            }
        }
        while (!out.isEmpty() && out.get(out.size() - 1) == null) {
            out.remove(out.size() - 1);
        }
        return out;
    }

}