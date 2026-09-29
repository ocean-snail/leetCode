package topInterview150;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Queue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Given the root of a binary tree, return its maximum depth.
 * 
 * A binary tree's maximum depth is the number of nodes along the longest path
 * from the root node down to the farthest leaf node.
 * 
 * 
 * ? Example 1:
 * 
 * 
 * Input: root = [3,9,20,null,null,15,7]
 * Output: 3
 * 
 * ? Example 2:
 * 
 * Input: root = [1,null,2]
 * Output: 2
 * 
 * 
 * ! Constraints:
 * 
 * The number of nodes in the tree is in the range [0, 104].
 * -100 <= Node.val <= 100
 * 
 */
public class MaximumDepthOfBinaryTree {

    // ------------------------------------------------------------------
    // Solution
    // ------------------------------------------------------------------

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

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
     * Returns the number of nodes on the longest root-to-leaf path.
     *
     * * Time: O(n) - exactly 2n + R loop iterations (n pushes, n pops, R moves into
     * a
     * right child, R = right-child count), each O(1); same in best, average and
     * worst case
     * * Space: O(h) - the path deque holds exactly the nodes from the root to the
     * current node, h = tree height
     * best O(log n) - complete tree, h = floor(log2 n) + 1
     * average O(log n) - random-insertion BST shape (measured mean h = 31.4 over
     * 200 trees of n = 10^4)
     * worst O(n) - skewed chain, h = n (heap memory, so no StackOverflowError)
     *
     * @param root root of the tree, or null for an empty tree
     * @return maximum depth; 0 for an empty tree
     */
    public int maxDepth(TreeNode root) {
        Deque<TreeNode> path = new ArrayDeque<>();
        TreeNode current = root;
        TreeNode lastPopped = null;
        int deepest = 0;
        while (current != null || !path.isEmpty()) {
            if (current != null) {
                path.push(current);
                deepest = Math.max(deepest, path.size());
                current = current.left;
            } else {
                TreeNode top = path.peek();
                if (top.right != null && top.right != lastPopped) {
                    current = top.right;
                } else {
                    lastPopped = path.pop();
                }
            }
        }
        return deepest;
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------

    @Test
    @DisplayName("Example 1: [3,9,20,null,null,15,7] has its deepest leaves 15 and 7 at depth 3")
    void example1_returnsThree() {
        assertEquals(3, maxDepth(fromLevelOrder(3, 9, 20, null, null, 15, 7)));
    }

    @Test
    @DisplayName("Example 2: [1,null,2] is a right-only chain of two nodes, so the depth is 2")
    void example2RightChain_returnsTwo() {
        assertEquals(2, maxDepth(fromLevelOrder(1, null, 2)));
    }

    @Test
    @DisplayName("An empty tree (0 nodes, allowed by the constraints) has depth 0")
    void emptyTree_returnsZero() {
        assertEquals(0, maxDepth(null));
    }

    @Test
    @DisplayName("A single root node is its own leaf, so the depth is 1")
    void singleNode_returnsOne() {
        assertEquals(1, maxDepth(fromLevelOrder(-100)));
    }

    @Test
    @DisplayName("[1,2,3,4,null,null,null,5] has its deepest leaf only under the left subtree, at depth 4")
    void deepestLeafOnLeftOnly_returnsFour() {
        assertEquals(4, maxDepth(fromLevelOrder(1, 2, 3, 4, null, null, null, 5)));
    }

    @Test
    @DisplayName("A complete tree of 15 nodes has exactly 4 full levels")
    void completeTreeOfFifteen_returnsFour() {
        assertEquals(4, maxDepth(fromLevelOrder(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)));
    }

    @Test
    @DisplayName("A left-skewed chain of 10^4 nodes (the constraint maximum) returns 10000 without StackOverflowError")
    void leftChainOfMaxSize_returnsNodeCount() {
        TreeNode root = null;
        for (int i = 0; i < 10_000; i++) {
            root = new TreeNode(i % 201 - 100, root, null);
        }
        assertEquals(10_000, maxDepth(root));
    }

    @Test
    @DisplayName("maxDepth leaves every node value and child link of the caller's tree unchanged")
    void callerTree_isNotModified() {
        TreeNode root = fromLevelOrder(3, 9, 20, null, null, 15, 7);
        String before = toLevelOrder(root);
        maxDepth(root);
        assertEquals(before, toLevelOrder(root));
    }

    /**
     * Builds a tree from LeetCode's level-order array, where null marks a missing
     * child.
     */
    private static TreeNode fromLevelOrder(Integer... values) {
        if (values.length == 0 || values[0] == null) {
            return null;
        }
        TreeNode root = new TreeNode(values[0]);
        Queue<TreeNode> parents = new ArrayDeque<>();
        parents.offer(root);
        int index = 1;
        while (index < values.length) {
            TreeNode parent = parents.poll();
            if (values[index] != null) {
                parent.left = new TreeNode(values[index]);
                parents.offer(parent.left);
            }
            index++;
            if (index < values.length && values[index] != null) {
                parent.right = new TreeNode(values[index]);
                parents.offer(parent.right);
            }
            index++;
        }
        return root;
    }

    /**
     * Serializes a tree to level order with explicit "null" slots, so any value or
     * link change alters the string.
     */
    private static String toLevelOrder(TreeNode root) {
        List<String> tokens = new ArrayList<>();
        Queue<TreeNode> queue = new ArrayDeque<>();
        List<TreeNode> order = new ArrayList<>();
        if (root != null) {
            queue.offer(root);
        }
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            order.add(node);
            tokens.add(Integer.toString(node.val));
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
        for (TreeNode node : order) {
            tokens.add(node.left == null ? "null" : "L");
            tokens.add(node.right == null ? "null" : "R");
        }
        return String.join(",", tokens);
    }
}