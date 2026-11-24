package topInterview150;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Given the root of a binary tree and an integer targetSum, return true if the
 * tree has a root-to-leaf path such that adding up all the values along the
 * path equals targetSum.
 * 
 * A leaf is a node with no children.
 * 
 * 
 * ? Example 1:
 * 
 * 
 * Input: root = [5,4,8,11,null,13,4,7,2,null,null,null,1], targetSum = 22
 * Output: true
 * Explanation: The root-to-leaf path with the target sum is shown.
 * 
 * ? Example 2:
 * 
 * 
 * Input: root = [1,2,3], targetSum = 5
 * Output: false
 * Explanation: There are two root-to-leaf paths in the tree:
 * (1 --> 2): The sum is 3.
 * (1 --> 3): The sum is 4.
 * There is no root-to-leaf path with sum = 5.
 * Example 3:
 * 
 * Input: root = [], targetSum = 0
 * Output: false
 * Explanation: Since the tree is empty, there are no root-to-leaf paths.
 * 
 * 
 * ! Constraints:
 * 
 * The number of nodes in the tree is in the range [0, 5000].
 * -1000 <= Node.val <= 1000
 * -1000 <= targetSum <= 1000
 * 
 */
public class PathSum {

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
     * Returns whether some root-to-leaf path has node values that add up to
     * targetSum.
     *
     * * Time: O(n) worst and average, O(d) best - each call does O(1) work. A query
     * with no matching path
     * enters all n nodes plus one missing child per one-child node (at most 2n - 1
     * calls in total);
     * when the first leaf reached in left-first order matches, || returns after d +
     * 1 calls, where
     * d is that leaf's depth (O(1) when the root's left child is that leaf).
     * * Space: O(log n) best, O(n) worst - the call stack holds one frame per node
     * on
     * the current path,
     * so it peaks at h + 1 frames for tree height h: floor(log2 n) for a complete
     * tree and
     * n - 1 for a chain of n nodes.
     *
     * @param root      root of the tree, or null for an empty tree
     * @param targetSum the sum that a root-to-leaf path must reach
     * @return true if at least one root-to-leaf path sums to targetSum
     */
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }
        int remaining = targetSum - root.val;
        if (root.left == null && root.right == null) {
            return remaining == 0;
        }

        return hasPathSum(root.left, remaining) || hasPathSum(root.right, remaining);
    }

    // ------------------------------------------------------------------
    // Reference implementation (oracle)
    // ------------------------------------------------------------------

    /**
     * Visits every node in level order with an explicit queue, carries the sum from
     * the root downward
     * by addition, collects the sums found at leaves into a set and checks
     * membership at the end.
     * Unlike the solution it has no recursion, no subtraction and no early exit.
     *
     * * Time: O(n) in every case - each node is added to and removed from the queue
     * exactly once, and
     * each HashSet add or contains call takes O(1) expected time.
     * * Space: O(1) best, O(n) worst - for a chain the queue and the set each hold
     * at
     * most one entry;
     * for a perfect tree the queue holds the (n + 1) / 2 nodes of the last level
     * and the set
     * holds up to (n + 1) / 2 leaf sums.
     *
     * @param root      root of the tree, or null for an empty tree
     * @param targetSum the sum that a root-to-leaf path must reach
     * @return true if at least one root-to-leaf path sums to targetSum
     */
    public boolean hasPathSumReference(TreeNode root, int targetSum) {
        Set<Integer> leafSums = new HashSet<>();
        Deque<TreeNode> nodes = new ArrayDeque<>();
        Deque<Integer> sumsFromRoot = new ArrayDeque<>();
        if (root != null) {
            nodes.offerLast(root);
            sumsFromRoot.offerLast(root.val);
        }
        while (!nodes.isEmpty()) {
            TreeNode node = nodes.pollFirst();
            int sum = sumsFromRoot.pollFirst();
            if (node.left == null && node.right == null) {
                leafSums.add(sum);
            }
            if (node.left != null) {
                nodes.offerLast(node.left);
                sumsFromRoot.offerLast(sum + node.left.val);
            }
            if (node.right != null) {
                nodes.offerLast(node.right);
                sumsFromRoot.offerLast(sum + node.right.val);
            }
        }
        return leafSums.contains(targetSum);
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------

    private static final int MAX_NODES = 5000;

    private static final int MIN_TARGET = -1000;

    private static final int MAX_TARGET = 1000;

    @Test
    @DisplayName("Example 1: path 5 -> 4 -> 11 -> 2 sums to 22, so the answer is true")
    void example1_returnsTrue() {
        TreeNode root = buildTree(5, 4, 8, 11, null, 13, 4, 7, 2, null, null, null, 1);
        assertTrue(hasPathSum(root, 22));
    }

    @Test
    @DisplayName("Example 2: the leaf sums of [1,2,3] are 3 and 4, so target 5 gives false")
    void example2_returnsFalse() {
        assertFalse(hasPathSum(buildTree(1, 2, 3), 5));
    }

    @Test
    @DisplayName("Example 3: an empty tree has no root-to-leaf path, so even target 0 gives false")
    void emptyTreeWithTargetZero_returnsFalse() {
        assertFalse(hasPathSum(buildTree(), 0));
    }

    @Test
    @DisplayName("A single node is itself a leaf: [-1000] with target -1000 gives true")
    void singleNodeEqualToTarget_returnsTrue() {
        assertTrue(hasPathSum(buildTree(-1000), -1000));
    }

    @Test
    @DisplayName("A single node [1000] with target -1000 gives false")
    void singleNodeDifferentFromTarget_returnsFalse() {
        assertFalse(hasPathSum(buildTree(1000), -1000));
    }

    @Test
    @DisplayName("[1,2] with target 1: the root reaches 1 but has a left child, so it is not a leaf and the answer is false")
    void targetReachedOnlyAtInternalNode_returnsFalse() {
        assertFalse(hasPathSum(buildTree(1, 2), 1));
    }

    @Test
    @DisplayName("[1,2] with target 3: the only path passes through a node with one child and sums to 3")
    void pathThroughOneChildNode_returnsTrue() {
        assertTrue(hasPathSum(buildTree(1, 2), 3));
    }

    @Test
    @DisplayName("[1,2,3] with target 4: only the right path 1 -> 3 matches")
    void matchOnlyInRightSubtree_returnsTrue() {
        assertTrue(hasPathSum(buildTree(1, 2, 3), 4));
    }

    @Test
    @DisplayName("[-2,null,-3] with target -5: the partial sum -2 is above the target, yet adding -3 reaches it")
    void negativeValues_returnsTrue() {
        assertTrue(hasPathSum(buildTree(-2, null, -3), -5));
    }

    @Test
    @DisplayName("[1,-1] with target 0: a non-empty path can sum to 0")
    void pathCancellingToZero_returnsTrue() {
        assertTrue(hasPathSum(buildTree(1, -1), 0));
    }

    @Test
    @DisplayName("A 5000-node left chain of +1/-1 has leaf sum 0; target 1 appears only at internal nodes")
    void deepChainAtMaxSize_matchesOnlyTheLeafSum() {
        TreeNode root = new TreeNode(1);
        TreeNode tail = root;
        for (int depth = 1; depth < MAX_NODES; depth++) {
            tail.left = new TreeNode(depth % 2 == 0 ? 1 : -1);
            tail = tail.left;
        }
        assertTrue(hasPathSum(root, 0));
        assertFalse(hasPathSum(root, 1));
    }

    @Test
    @DisplayName("Neither the solution nor the oracle changes any value or link of the input tree")
    void solutionAndOracleCalls_leaveTreeUnchanged() {
        TreeNode root = buildTree(5, 4, 8, 11, null, 13, 4, 7, 2, null, null, null, 1);
        List<Integer> before = toLevelOrder(root);
        hasPathSum(root, 22);
        hasPathSum(root, 0);
        hasPathSumReference(root, 22);
        hasPathSumReference(root, 0);
        assertEquals(before, toLevelOrder(root));
    }

    @Test
    @DisplayName("All 197 shapes with 0 to 6 nodes, every labeling from {-1, 0, 1} and targets -7..7 match the oracle")
    void allLabeledShapesUpToSixNodes_matchOracle() {
        int shapes = 0;
        int cases = 0;
        int trueCases = 0;
        int mismatches = 0;
        String firstMismatch = "none";
        for (int n = 0; n <= 6; n++) {
            int labelings = 1;
            for (int i = 0; i < n; i++) {
                labelings *= 3;
            }
            for (TreeNode shape : allShapes(n)) {
                shapes++;
                List<TreeNode> nodes = preorderNodes(shape);
                for (int code = 0; code < labelings; code++) {
                    int digits = code;
                    for (TreeNode node : nodes) {
                        node.val = digits % 3 - 1;
                        digits /= 3;
                    }
                    for (int target = -7; target <= 7; target++) {
                        boolean expected = hasPathSumReference(shape, target);
                        cases++;
                        if (expected) {
                            trueCases++;
                        }
                        if (hasPathSum(shape, target) != expected) {
                            if (mismatches == 0) {
                                firstMismatch = toLevelOrder(shape) + " target " + target;
                            }
                            mismatches++;
                        }
                    }
                }
            }
        }
        assertEquals(0, mismatches, "first mismatch: " + firstMismatch);
        assertEquals(197, shapes);
        assertEquals(1_615_875, cases);
        assertEquals(176_082, trueCases);
    }

    @Test
    @DisplayName("300 seeded random trees of 0 to 5000 nodes, bushy to path-like, match the oracle")
    void randomTreesUpToMaxSize_matchOracle() {
        Random random = new Random(112L);
        double[] pathBias = { 0.0, 0.5, 0.9, 1.0 };
        int[] valueBound = { 1000, 10, 1 };
        int trueCases = 0;
        int mismatches = 0;
        String firstMismatch = "none";
        for (int trial = 0; trial < 300; trial++) {
            int n = random.nextInt(MAX_NODES + 1);
            TreeNode root = randomTree(n, pathBias[trial % 4], valueBound[(trial / 4) % 3], random);
            int target = MIN_TARGET + random.nextInt(MAX_TARGET - MIN_TARGET + 1);
            if (trial % 2 == 1 && root != null) {
                int walkSum = randomLeafPathSum(root, random);
                if (walkSum >= MIN_TARGET && walkSum <= MAX_TARGET) {
                    target = walkSum;
                }
            }
            boolean expected = hasPathSumReference(root, target);
            if (expected) {
                trueCases++;
            }
            if (hasPathSum(root, target) != expected) {
                if (mismatches == 0) {
                    firstMismatch = "trial " + trial + " n " + n + " target " + target;
                }
                mismatches++;
            }
        }
        assertEquals(0, mismatches, "first mismatch: " + firstMismatch);
        assertEquals(117, trueCases);
    }

    /**
     * Builds a tree from LeetCode level-order notation, where null marks a missing
     * child.
     *
     * Time: O(k) - each of the k array entries is read once
     * Space: O(k) - one node per non-null entry plus the queue of parents waiting
     * for children
     *
     * @param levelOrder values in LeetCode level-order notation; empty or a leading
     *                   null gives null
     * @return root of the built tree, or null for an empty tree
     */
    private static TreeNode buildTree(Integer... levelOrder) {
        if (levelOrder.length == 0 || levelOrder[0] == null) {
            return null;
        }
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
     * Serializes a tree to LeetCode level-order notation with trailing nulls
     * removed.
     *
     * Time: O(n) - each node is read once and adds at most two entries
     * Space: O(n) - the output list doubles as the queue and holds at most 2n + 1
     * entries
     *
     * @param root root of the tree, or null
     * @return level-order values, with null for each missing child before the last
     *         node
     */
    private static List<Integer> toLevelOrder(TreeNode root) {
        List<TreeNode> queue = new ArrayList<>();
        List<Integer> result = new ArrayList<>();
        queue.add(root);
        // A list with a read index is used instead of ArrayDeque because missing
        // children (null) are
        // recorded in order and ArrayDeque rejects null elements.
        for (int read = 0; read < queue.size(); read++) {
            TreeNode node = queue.get(read);
            if (node == null) {
                result.add(null);
                continue;
            }
            result.add(node.val);
            queue.add(node.left);
            queue.add(node.right);
        }
        while (!result.isEmpty() && result.get(result.size() - 1) == null) {
            result.remove(result.size() - 1);
        }
        return result;
    }

    /**
     * Enumerates every binary tree shape with n nodes; every shape is built from
     * fresh nodes so that
     * relabeling one shape never changes another, and all values start at 0.
     *
     * Time: O(n * C(n)) - C(n) is the n-th Catalan number of shapes, and each shape
     * copies its n nodes
     * Space: O(n * C(n)) - all shapes are returned together
     *
     * @param n number of nodes, 0 or more
     * @return all shapes; for n = 0 a single entry null (the empty tree)
     */
    private static List<TreeNode> allShapes(int n) {
        List<TreeNode> result = new ArrayList<>();
        if (n == 0) {
            result.add(null);
            return result;
        }
        for (int leftSize = 0; leftSize < n; leftSize++) {
            List<TreeNode> lefts = allShapes(leftSize);
            List<TreeNode> rights = allShapes(n - 1 - leftSize);
            for (TreeNode left : lefts) {
                for (TreeNode right : rights) {
                    TreeNode root = new TreeNode(0);
                    root.left = copy(left);
                    root.right = copy(right);
                    result.add(root);
                }
            }
        }
        return result;
    }

    /**
     * Returns a structural copy of a tree made of new nodes.
     *
     * Time: O(n) - one new node per original node
     * Space: O(h) - one stack frame per level; only used for the small trees of
     * allShapes
     *
     * @param node root of the tree to copy, or null
     * @return root of the copy, or null
     */
    private static TreeNode copy(TreeNode node) {
        if (node == null) {
            return null;
        }
        TreeNode result = new TreeNode(node.val);
        result.left = copy(node.left);
        result.right = copy(node.right);
        return result;
    }

    /**
     * Lists the nodes of a tree in pre-order (node, left subtree, right subtree).
     *
     * Time: O(n) - each node is pushed and popped once
     * Space: O(n) - the output list holds n nodes and the stack holds at most n
     *
     * @param root root of the tree, or null
     * @return the nodes in pre-order; empty for a null root
     */
    private static List<TreeNode> preorderNodes(TreeNode root) {
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
        return order;
    }

    /**
     * Builds a random tree of n nodes. With probability pathBias the new node
     * becomes a child of the
     * node added just before it, which extends the current path; otherwise it
     * becomes a child of a
     * uniformly chosen node that still has a free child slot. pathBias 1.0 gives a
     * path of depth n - 1.
     *
     * Time: O(n) - each insertion picks a parent and updates the open list in O(1)
     * Space: O(n) - the n nodes plus the list of nodes with a free slot
     *
     * @param n          number of nodes, 0 or more
     * @param pathBias   probability of attaching to the most recently added node
     * @param valueBound node values are drawn uniformly from [-valueBound,
     *                   valueBound]
     * @param random     seeded source of randomness
     * @return root of the tree, or null when n is 0
     */
    private static TreeNode randomTree(int n, double pathBias, int valueBound, Random random) {
        if (n == 0) {
            return null;
        }
        TreeNode root = new TreeNode(random.nextInt(2 * valueBound + 1) - valueBound);
        List<TreeNode> open = new ArrayList<>(Arrays.asList(root));
        for (int added = 1; added < n; added++) {
            int parentIndex = random.nextDouble() < pathBias ? open.size() - 1 : random.nextInt(open.size());
            TreeNode parent = open.get(parentIndex);
            TreeNode child = new TreeNode(random.nextInt(2 * valueBound + 1) - valueBound);
            boolean bothFree = parent.left == null && parent.right == null;
            if (bothFree ? random.nextBoolean() : parent.left == null) {
                parent.left = child;
            } else {
                parent.right = child;
            }
            if (parent.left != null && parent.right != null) {
                open.set(parentIndex, open.get(open.size() - 1));
                open.remove(open.size() - 1);
            }
            open.add(child);
        }
        return root;
    }

    /**
     * Follows randomly chosen existing children from the root down to a leaf and
     * sums the values.
     *
     * Time: O(h) - one step per level
     * Space: O(1) - one reference and one running sum
     *
     * @param root   a non-null root
     * @param random seeded source of randomness
     * @return the sum of one root-to-leaf path
     */
    private static int randomLeafPathSum(TreeNode root, Random random) {
        TreeNode node = root;
        int sum = 0;
        while (true) {
            sum += node.val;
            if (node.left == null && node.right == null) {
                return sum;
            }
            if (node.left == null) {
                node = node.right;
            } else if (node.right == null) {
                node = node.left;
            } else {
                node = random.nextBoolean() ? node.left : node.right;
            }
        }
    }
}