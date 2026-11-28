package topInterview150;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
 * A path in a binary tree is a sequence of nodes where each pair of adjacent
 * nodes in the sequence has an edge connecting them. A node can only appear in
 * the sequence at most once. Note that the path does not need to pass through
 * the root.
 * 
 * The path sum of a path is the sum of the node's values in the path.
 * 
 * Given the root of a binary tree, return the maximum path sum of any non-empty
 * path.
 * 
 * 
 * ? Example 1:
 * 
 * 
 * Input: root = [1,2,3]
 * Output: 6
 * Explanation: The optimal path is 2 -> 1 -> 3 with a path sum of 2 + 1 + 3 =
 * 6.
 * 
 * ? Example 2:
 * 
 * 
 * Input: root = [-10,9,20,null,null,15,7]
 * Output: 42
 * Explanation: The optimal path is 15 -> 20 -> 7 with a path sum of 15 + 20 + 7
 * = 42.
 * 
 * 
 * ! Constraints:
 * 
 * The number of nodes in the tree is in the range [1, 3 * 104].
 * -1000 <= Node.val <= 1000
 * 
 */
public class BinaryTreeMaximumPathSum {

    // ------------------------------------------------------------------
    // Solution
    // ------------------------------------------------------------------

    static final class TreeNode {

        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    /**
     * Returns the largest path sum, trying every node as the highest node of a
     * path.
     *
     * * Time: O(n) - each node is pushed and popped once, and its right child is
     * tested at most
     * twice before the pop.
     * * Space:
     * Best: O(log n) - a complete tree keeps at most floor(log2 n) + 1 entries on
     * each stack.
     * Average: O(h) - h is the tree height, and both stacks hold at most h + 1
     * entries.
     * Worst: O(n) - a chain, where every node has one child, keeps all n nodes on
     * the path stack.
     *
     * @param root root of a tree with 1 to 30000 nodes
     * @return maximum sum over all non-empty paths
     */
    public int maxPathSum(TreeNode root) {
        Deque<TreeNode> path = new ArrayDeque<>();
        Deque<Integer> gains = new ArrayDeque<>();
        int best = Integer.MIN_VALUE;
        TreeNode current = root;
        TreeNode lastFinished = null;
        while (current != null || !path.isEmpty()) {
            if (current != null) {
                path.push(current);
                current = current.left;
            } else {
                TreeNode top = path.peek();
                if (top.right != null && top.right != lastFinished) {
                    current = top.right;
                } else {
                    path.pop();
                    int rightGain = top.right == null ? 0 : gains.pop();
                    int leftGain = top.left == null ? 0 : gains.pop();
                    best = Math.max(best, top.val + leftGain + rightGain);
                    gains.push(Math.max(0, top.val + Math.max(leftGain, rightGain)));
                    lastFinished = top;
                }
            }
        }
        return best;
    }

    // ------------------------------------------------------------------
    // Reference implementation (oracle)
    // ------------------------------------------------------------------

    /**
     * Sums every path explicitly with one breadth-first search per start node, not
     * subtree gains.
     *
     * * Time: O(n^2) - n searches run, and each search visits all n nodes over the
     * n
     * - 1 edges.
     * * Space: O(n) - the node list, adjacency lists, and one search's arrays each
     * hold n entries.
     *
     * @param root root of a tree with 1 to 30000 nodes
     * @return maximum sum over all non-empty paths
     */
    public int maxPathSumReference(TreeNode root) {
        List<TreeNode> nodes = listNodes(root);
        List<List<Integer>> neighbors = buildNeighbors(nodes);
        int best = Integer.MIN_VALUE;
        for (int start = 0; start < nodes.size(); start++) {
            best = Math.max(best, maxPathSumFrom(start, nodes, neighbors));
        }
        return best;
    }

    /**
     * Returns all nodes of the tree in breadth-first order.
     *
     * * Time: O(n) - each node is appended once and read once by the index loop.
     * * Space: O(n) - the returned list holds all n nodes.
     *
     * @param root root of the tree
     * @return every node, root first
     */
    private static List<TreeNode> listNodes(TreeNode root) {
        List<TreeNode> nodes = new ArrayList<>();
        nodes.add(root);
        for (int i = 0; i < nodes.size(); i++) {
            TreeNode node = nodes.get(i);
            if (node.left != null) {
                nodes.add(node.left);
            }
            if (node.right != null) {
                nodes.add(node.right);
            }
        }
        return nodes;
    }

    /**
     * Returns undirected adjacency lists indexed by each node's position in the
     * node list.
     *
     * * Time: O(n) - one identity-map insertion per node and one lookup per
     * parent-child edge.
     * * Space: O(n) - the identity map holds n entries and the lists hold 2(n - 1)
     * indices.
     *
     * @param nodes every node of the tree
     * @return for each node index, the indices of its parent and children
     */
    private static List<List<Integer>> buildNeighbors(List<TreeNode> nodes) {
        Map<TreeNode, Integer> indexOf = new IdentityHashMap<>();
        List<List<Integer>> neighbors = new ArrayList<>();
        for (int i = 0; i < nodes.size(); i++) {
            indexOf.put(nodes.get(i), i);
            neighbors.add(new ArrayList<>());
        }
        for (int i = 0; i < nodes.size(); i++) {
            TreeNode node = nodes.get(i);
            for (TreeNode child : new TreeNode[] { node.left, node.right }) {
                if (child != null) {
                    int j = indexOf.get(child);
                    neighbors.get(i).add(j);
                    neighbors.get(j).add(i);
                }
            }
        }
        return neighbors;
    }

    /**
     * Returns the largest sum of a path that starts at the given node.
     *
     * * Time: O(n) - the breadth-first search visits each node once over the n - 1
     * edges.
     * * Space: O(n) - the visited array, the sum array, and the queue each hold up
     * to
     * n entries.
     *
     * @param start     index of the first node of every path considered
     * @param nodes     every node of the tree
     * @param neighbors undirected adjacency lists indexed like nodes
     * @return maximum sum over the paths from start to each node, including start
     *         alone
     */
    private static int maxPathSumFrom(int start, List<TreeNode> nodes,
            List<List<Integer>> neighbors) {
        boolean[] visited = new boolean[nodes.size()];
        int[] sum = new int[nodes.size()];
        Deque<Integer> queue = new ArrayDeque<>();
        visited[start] = true;
        sum[start] = nodes.get(start).val;
        queue.add(start);
        int best = sum[start];
        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (int v : neighbors.get(u)) {
                if (!visited[v]) {
                    visited[v] = true;
                    sum[v] = sum[u] + nodes.get(v).val;
                    best = Math.max(best, sum[v]);
                    queue.add(v);
                }
            }
        }
        return best;
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------

    @Test
    @DisplayName("Example 1: [1,2,3] returns 6 through the path 2 -> 1 -> 3")
    void example1_returnsSix() {
        TreeNode root = buildTree(1, 2, 3);

        int result = maxPathSum(root);

        assertEquals(6, result);
    }

    @Test
    @DisplayName("Example 2: [-10,9,20,null,null,15,7] returns 42 through the path 15 -> 20 -> 7")
    void example2_returnsFortyTwo() {
        TreeNode root = buildTree(-10, 9, 20, null, null, 15, 7);

        int result = maxPathSum(root);

        assertEquals(42, result);
    }

    @Test
    @DisplayName("Single node [-3] returns -3 because a path must contain at least one node")
    void singleNegativeNode_returnsItsValue() {
        TreeNode root = buildTree(-3);

        int result = maxPathSum(root);

        assertEquals(-3, result);
    }

    @Test
    @DisplayName("[2,-1] returns 2 because adding the negative child lowers the sum")
    void negativeChild_isLeftOut() {
        TreeNode root = buildTree(2, -1);

        int result = maxPathSum(root);

        assertEquals(2, result);
    }

    @Test
    @DisplayName("All-negative tree [-2,-1] returns -1, the value of the largest single node")
    void allNegativeValues_returnLargestSingleNode() {
        TreeNode root = buildTree(-2, -1);

        int result = maxPathSum(root);

        assertEquals(-1, result);
    }

    @Test
    @DisplayName("[5,4,8,11,null,13,4,7,2,null,null,null,1] returns 48 by turning at the root")
    void deepBranchesOnBothSides_returnFortyEight() {
        TreeNode root = buildTree(5, 4, 8, 11, null, 13, 4, 7, 2, null, null, null, 1);

        int result = maxPathSum(root);

        assertEquals(48, result);
    }

    @Test
    @DisplayName("Left chain of 30000 nodes valued 1000 returns 30000000 without stack overflow")
    void longestLeftChain_returnsSumOfAllNodes() {
        TreeNode root = buildChain(30_000, 1000, true);

        int result = maxPathSum(root);

        assertEquals(30_000_000, result);
    }

    @Test
    @DisplayName("Right chain of 30000 nodes valued -1000 returns -1000, the value of one node")
    void longestRightChainOfNegatives_returnsSingleNode() {
        TreeNode root = buildChain(30_000, -1000, false);

        int result = maxPathSum(root);

        assertEquals(-1000, result);
    }

    @Test
    @DisplayName("Computing [-10,9,20,null,null,15,7] leaves its values and shape unchanged")
    void exampleTwoTree_isLeftUnchanged() {
        TreeNode root = buildTree(-10, 9, 20, null, null, 15, 7);

        maxPathSum(root);

        assertEquals("-10,9,20,null,null,15,7", serializeLevelOrder(root));
    }

    @Test
    @DisplayName("Seeded random trees of 1 to 60 nodes match the reference implementation")
    void randomTrees_matchReference() {
        Random random = new Random(124);
        for (int trial = 0; trial < 1000; trial++) {
            int n = 1 + random.nextInt(60);
            TreeNode root = randomTree(random, n);
            int expected = maxPathSumReference(root);

            int actual = maxPathSum(root);

            assertEquals(expected, actual, "trial=" + trial + " n=" + n);
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
    static TreeNode buildTree(Integer... levelOrder) {
        if (levelOrder.length == 0 || levelOrder[0] == null) {
            return null;
        }
        TreeNode root = new TreeNode(levelOrder[0]);
        Deque<TreeNode> parents = new ArrayDeque<>();
        parents.add(root);
        int index = 1;
        while (index < levelOrder.length) {
            TreeNode parent = parents.poll();
            if (levelOrder[index] != null) {
                parent.left = new TreeNode(levelOrder[index]);
                parents.add(parent.left);
            }
            index++;
            if (index < levelOrder.length && levelOrder[index] != null) {
                parent.right = new TreeNode(levelOrder[index]);
                parents.add(parent.right);
            }
            index++;
        }
        return root;
    }

    /**
     * Builds a chain in which every node except the last has one child, always on
     * the same side.
     *
     * @param length   number of nodes in the chain
     * @param value    value stored in every node
     * @param leftward true to link each node as the left child of the previous node
     * @return root of the chain
     */
    static TreeNode buildChain(int length, int value, boolean leftward) {
        TreeNode root = new TreeNode(value);
        TreeNode tail = root;
        for (int i = 1; i < length; i++) {
            TreeNode next = new TreeNode(value);
            if (leftward) {
                tail.left = next;
            } else {
                tail.right = next;
            }
            tail = next;
        }
        return root;
    }

    /**
     * Serializes a tree to LeetCode level-order notation without trailing nulls.
     *
     * @param root root of the tree
     * @return comma-separated values, with null for each missing child
     */
    static String serializeLevelOrder(TreeNode root) {
        List<String> tokens = new ArrayList<>();
        List<TreeNode> queue = new ArrayList<>();
        queue.add(root);
        for (int i = 0; i < queue.size(); i++) {
            TreeNode node = queue.get(i);
            if (node == null) {
                tokens.add("null");
            } else {
                tokens.add(String.valueOf(node.val));
                queue.add(node.left);
                queue.add(node.right);
            }
        }
        while (!tokens.isEmpty() && tokens.get(tokens.size() - 1).equals("null")) {
            tokens.remove(tokens.size() - 1);
        }
        return String.join(",", tokens);
    }

    /**
     * Builds a random tree by splitting the remaining node count at a random point
     * at each node.
     *
     * @param random source of the split points and of values in [-1000, 1000]
     * @param size   number of nodes to create
     * @return root of the random tree, or null when size is 0
     */
    static TreeNode randomTree(Random random, int size) {
        if (size == 0) {
            return null;
        }
        TreeNode root = new TreeNode(random.nextInt(2001) - 1000);
        int leftSize = random.nextInt(size);
        root.left = randomTree(random, leftSize);
        root.right = randomTree(random, size - 1 - leftSize);
        return root;
    }
}
