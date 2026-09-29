package topInterview150;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayDeque;
import java.util.Queue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Given the roots of two binary trees p and q, write a function to check if
 * they are the same or not.
 * 
 * Two binary trees are considered the same if they are structurally identical,
 * and the nodes have the same value.
 * 
 * 
 * ? Example 1:
 * 
 * 
 * Input: p = [1,2,3], q = [1,2,3]
 * Output: true
 * 
 * ? Example 2:
 * 
 * 
 * Input: p = [1,2], q = [1,null,2]
 * Output: false
 * 
 * ? Example 3:
 * 
 * 
 * Input: p = [1,2,1], q = [1,1,2]
 * Output: false
 * 
 * 
 * ! Constraints:
 * 
 * The number of nodes in both trees is in the range [0, 100].
 * -104 <= Node.val <= 104
 */
public class SameTree {

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
     * Compares the two nodes at the same position, then both child positions.
     *
     * * Time: best O(1) - the roots already differ (one is null, or the values
     * differ), so no child call is made.
     * worst O(min(n, m)) - each call either stops or descends into a position
     * present in both trees,
     * so at most 2 * min(n, m) + 1 calls run (n, m = node counts of p and q).
     * * Space: best O(1) - no recursion beyond the root call.
     * average O(log n) - balanced trees give a call stack as deep as the tree
     * height.
     * worst O(min(n, m)) - skewed trees (every node has one child) make the height
     * equal the node count.
     *
     * @param p root of the first tree, may be null
     * @param q root of the second tree, may be null
     * @return true when both trees have identical shape and identical values at
     *         every position
     */
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null) {
            return false;
        }
        if (p.val != q.val) {
            return false;
        }
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------

    private static TreeNode fromLevelOrder(Integer... values) {
        if (values.length == 0 || values[0] == null) {
            return null;
        }
        TreeNode root = new TreeNode(values[0]);
        Queue<TreeNode> parents = new ArrayDeque<>();
        parents.add(root);
        int index = 1;
        while (index < values.length) {
            TreeNode parent = parents.remove();
            if (values[index] != null) {
                parent.left = new TreeNode(values[index]);
                parents.add(parent.left);
            }
            index++;
            if (index < values.length && values[index] != null) {
                parent.right = new TreeNode(values[index]);
                parents.add(parent.right);
            }
            index++;
        }
        return root;
    }

    @Test
    @DisplayName("Example 1: [1,2,3] and [1,2,3] are the same tree")
    void example1IdenticalTrees_returnsTrue() {
        assertEquals(true, isSameTree(fromLevelOrder(1, 2, 3), fromLevelOrder(1, 2, 3)));
    }

    @Test
    @DisplayName("Example 2: [1,2] has 2 as a left child, [1,null,2] has 2 as a right child")
    void example2LeftChildVersusRightChild_returnsFalse() {
        assertEquals(false, isSameTree(fromLevelOrder(1, 2), fromLevelOrder(1, null, 2)));
    }

    @Test
    @DisplayName("Example 3: [1,2,1] and [1,1,2] have the same shape but swapped child values")
    void example3SwappedChildValues_returnsFalse() {
        assertEquals(false, isSameTree(fromLevelOrder(1, 2, 1), fromLevelOrder(1, 1, 2)));
    }

    @Test
    @DisplayName("Two empty trees (both roots null) are the same tree")
    void bothTreesEmpty_returnsTrue() {
        assertEquals(true, isSameTree(null, null));
    }

    @Test
    @DisplayName("An empty tree and a one-node tree differ, in either argument order")
    void oneTreeEmpty_returnsFalse() {
        assertEquals(false, isSameTree(null, fromLevelOrder(0)));
        assertEquals(false, isSameTree(fromLevelOrder(0), null));
    }

    @Test
    @DisplayName("Identical shape and all-equal values, but one tree puts the child on the right")
    void equalValuesDifferentShape_returnsFalse() {
        assertEquals(false, isSameTree(fromLevelOrder(1, 1), fromLevelOrder(1, null, 1)));
    }

    @Test
    @DisplayName("A mismatch only in the left subtree is detected even though the right subtrees match")
    void mismatchOnlyInLeftSubtree_returnsFalse() {
        assertEquals(false, isSameTree(fromLevelOrder(1, 2, 3, 4), fromLevelOrder(1, 2, 3, 5)));
    }

    @Test
    @DisplayName("A mismatch only in the right subtree is detected even though the left subtrees match")
    void mismatchOnlyInRightSubtree_returnsFalse() {
        assertEquals(false, isSameTree(
                fromLevelOrder(1, 2, 3, null, null, 4),
                fromLevelOrder(1, 2, 3, null, null, 5)));
    }

    @Test
    @DisplayName("One tree has an extra leaf while every shared position holds equal values")
    void extraLeafInSecondTree_returnsFalse() {
        assertEquals(false, isSameTree(fromLevelOrder(1, 2, 3), fromLevelOrder(1, 2, 3, 4)));
    }

    @Test
    @DisplayName("Values at the constraint bounds -10^4 and 10^4 compare as equal when they match")
    void constraintBoundaryValues_returnsTrue() {
        assertEquals(true, isSameTree(
                fromLevelOrder(-10_000, 10_000, -10_000),
                fromLevelOrder(-10_000, 10_000, -10_000)));
    }

    @Test
    @DisplayName("The same root passed as both arguments is the same tree")
    void sameInstanceBothArguments_returnsTrue() {
        TreeNode root = fromLevelOrder(5, 3, 8, 1, null, 7, 9);
        assertEquals(true, isSameTree(root, root));
    }
}