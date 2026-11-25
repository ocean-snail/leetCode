package topInterview150;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * You are given the root of a binary tree containing digits from 0 to 9 only.
 * 
 * Each root-to-leaf path in the tree represents a number.
 * 
 * For example, the root-to-leaf path 1 -> 2 -> 3 represents the number 123.
 * Return the total sum of all root-to-leaf numbers. Test cases are generated so
 * that the answer will fit in a 32-bit integer.
 * 
 * A leaf node is a node with no children.
 * 
 * 
 * ? Example 1:
 * 
 * 
 * Input: root = [1,2,3]
 * Output: 25
 * Explanation:
 * The root-to-leaf path 1->2 represents the number 12.
 * The root-to-leaf path 1->3 represents the number 13.
 * Therefore, sum = 12 + 13 = 25.
 * 
 * ? Example 2:
 * 
 * 
 * Input: root = [4,9,0,5,1]
 * Output: 1026
 * Explanation:
 * The root-to-leaf path 4->9->5 represents the number 495.
 * The root-to-leaf path 4->9->1 represents the number 491.
 * The root-to-leaf path 4->0 represents the number 40.
 * Therefore, sum = 495 + 491 + 40 = 1026.
 * 
 * 
 * ! Constraints:
 * 
 * The number of nodes in the tree is in the range [1, 1000].
 * 0 <= Node.val <= 9
 * The depth of the tree will not exceed 10.
 */
public class SumRootToLeafNumbers {

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
     * Returns the sum of the numbers spelled by the digits on all root-to-leaf
     * paths.
     *
     * * Time: O(n) - sumFrom runs exactly once per node and does constant work
     * besides its calls.
     * * Space:
     * Best: O(log n) - a complete tree keeps at most floor(log2 n) + 1 sumFrom
     * frames active.
     * Average: O(h) - h is the tree height, the largest number of sumFrom frames
     * active at once.
     * Worst: O(n) - a chain keeps all n sumFrom frames active when its single leaf
     * is reached.
     *
     * @param root root of a tree with 1 to 1000 nodes, each holding a digit from 0
     *             to 9
     * @return sum of all root-to-leaf numbers
     */
    public int sumNumbers(TreeNode root) {
        return sumFrom(root, 0);
    }

    /**
     * Returns the sum of the root-to-leaf numbers whose leaves lie in the subtree
     * rooted at node.
     *
     * * Time: O(m) - m is the subtree size, and each node in the subtree runs
     * sumFrom
     * exactly once.
     * * Space: O(h) - h is the subtree height, the largest number of sumFrom frames
     * active at once.
     *
     * @param node   non-null root of the subtree
     * @param prefix number spelled by the digits from the tree root down to the
     *               parent of node
     * @return sum of the numbers that end at the leaves of this subtree
     */
    private int sumFrom(TreeNode node, int prefix) {
        int number = prefix * 10 + node.val;
        if (node.left == null && node.right == null) {
            return number;
        }
        int sum = 0;
        if (node.left != null) {
            sum += sumFrom(node.left, number);
        }
        if (node.right != null) {
            sum += sumFrom(node.right, number);
        }
        return sum;
    }

    // ------------------------------------------------------------------
    // Reference implementation (oracle)
    // ------------------------------------------------------------------

    /**
     * Rebuilds each number from its leaf to the root via BFS parent links, not by
     * passing a prefix.
     *
     * * Time:
     * Best: O(n) - a chain has one leaf, so BFS and the one parent-link loop each
     * read n nodes.
     * Average: O(n + L * h) - L leaves, height h; each leaf's loop reads at most h
     * parent links.
     * Worst: O(n^2) - a caterpillar (a leaf on each chain node): n/2 loops of up to
     * n/2 links.
     * * Space: O(n) - parentOf, visited and parents each hold up to n entries for
     * every tree shape.
     *
     * @param root root of a tree with 1 to 1000 nodes, each holding a digit from 0
     *             to 9
     * @return exact sum of all root-to-leaf numbers, computed in long
     */
    public long sumNumbersReference(TreeNode root) {
        Map<TreeNode, TreeNode> parentOf = new IdentityHashMap<>();
        List<TreeNode> visited = new ArrayList<>();
        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            visited.add(node);
            if (node.left != null) {
                parentOf.put(node.left, node);
                queue.add(node.left);
            }
            if (node.right != null) {
                parentOf.put(node.right, node);
                queue.add(node.right);
            }
        }
        Set<TreeNode> parents = Collections.newSetFromMap(new IdentityHashMap<>());
        parents.addAll(parentOf.values());
        long total = 0;
        for (TreeNode node : visited) {
            if (!parents.contains(node)) {
                total += pathValueEndingAt(node, parentOf);
            }
        }
        return total;
    }

    /**
     * Returns the number spelled by the digits on the path from the root down to
     * leaf.
     *
     * * Time: O(d) - d is the depth of leaf, and the loop reads one parent link per
     * level.
     * * Space: O(1) - only value, placeValue and the cursor node are stored.
     *
     * @param leaf     node at the end of the path
     * @param parentOf parent of every node except the root
     * @return value of the path read as a decimal number
     */
    private static long pathValueEndingAt(TreeNode leaf, Map<TreeNode, TreeNode> parentOf) {
        long value = 0;
        long placeValue = 1;
        for (TreeNode node = leaf; node != null; node = parentOf.get(node)) {
            value += node.val * placeValue;
            placeValue *= 10;
        }
        return value;
    }

    // ------------------------------------------------------------------
    // Tests
    // ------------------------------------------------------------------

    @Test
    @DisplayName("Example 1: [1,2,3] returns 25 because 12 + 13 = 25")
    void example1_returnsTwentyFive() {
        TreeNode root = buildTree(1, 2, 3);

        int result = sumNumbers(root);

        assertEquals(25, result);
    }

    @Test
    @DisplayName("Example 2: [4,9,0,5,1] returns 1026 because 495 + 491 + 40 = 1026")
    void example2_returnsOneThousandTwentySix() {
        TreeNode root = buildTree(4, 9, 0, 5, 1);

        int result = sumNumbers(root);

        assertEquals(1026, result);
    }

    @Test
    @DisplayName("Single node [7] returns its own digit 7")
    void singleNode_returnsItsDigit() {
        TreeNode root = buildTree(7);

        int result = sumNumbers(root);

        assertEquals(7, result);
    }

    @Test
    @DisplayName("Left child only [1,2] returns 12 because the root is not a leaf")
    void leftChildOnly_returnsTwelve() {
        TreeNode root = buildTree(1, 2);

        int result = sumNumbers(root);

        assertEquals(12, result);
    }

    @Test
    @DisplayName("Right child only [1,null,2] returns 12 because the root is not a leaf")
    void rightChildOnly_returnsTwelve() {
        TreeNode root = buildTree(1, null, 2);

        int result = sumNumbers(root);

        assertEquals(12, result);
    }

    @Test
    @DisplayName("Zero root [0,1,2] returns 3 because the paths 01 and 02 are 1 and 2")
    void zeroRootDigit_returnsThree() {
        TreeNode root = buildTree(0, 1, 2);

        int result = sumNumbers(root);

        assertEquals(3, result);
    }

    @Test
    @DisplayName("Ten-node chain spelling 2147483647 returns Integer.MAX_VALUE")
    void tenNodeChainAtIntLimit_returnsIntegerMaxValue() {
        TreeNode root = buildTree(2, 1, null, 4, null, 7, null, 4, null, 8, null, 3, null, 6, null,
                4, null, 7);

        int result = sumNumbers(root);

        assertEquals(Integer.MAX_VALUE, result);
    }

    @Test
    @DisplayName("Tree [1,2,3,4,null,null,5] keeps every value and child link after the call")
    void sumNumbersCall_leavesTreeUnchanged() {
        TreeNode root = buildTree(1, 2, 3, 4, null, null, 5);
        String before = serializeLevelOrder(root);

        sumNumbers(root);

        assertEquals(before, serializeLevelOrder(root));
    }

    @Test
    @DisplayName("500 seeded random trees of 1 to 1000 nodes and depth 10 or less match reference")
    void randomTrees_matchReference() {
        Random sizes = new Random(129);
        for (int trial = 0; trial < 500; trial++) {
            int n = 1 + sizes.nextInt(1000);
            long seed = sizes.nextLong();
            TreeNode root = randomTree(new Random(seed), n);
            long expected = sumNumbersReference(root);

            int actual = sumNumbers(root);

            assertEquals(expected, actual, "seed=" + seed + " n=" + n);
        }
    }

    /**
     * Builds a tree from LeetCode level-order notation, where null marks a missing
     * child.
     *
     * @param levelOrder node values in level order, with null for each missing
     *                   child
     * @return root of the built tree
     */
    static TreeNode buildTree(Integer... levelOrder) {
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
     * Serializes values and shape in level order, writing # for each missing child.
     *
     * @param root root of the tree
     * @return comma-separated values that identify both the digits and the child
     *         links
     */
    static String serializeLevelOrder(TreeNode root) {
        StringBuilder text = new StringBuilder().append(root.val);
        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node.left == null) {
                text.append(",#");
            } else {
                text.append(',').append(node.left.val);
                queue.add(node.left);
            }
            if (node.right == null) {
                text.append(",#");
            } else {
                text.append(',').append(node.right.val);
                queue.add(node.right);
            }
        }
        return text.toString();
    }

    /**
     * Builds a random tree of n nodes with depth 10 or less whose answer fits in an
     * int.
     *
     * @param random source of the shape and the digits
     * @param n      number of nodes, from 1 to 1000
     * @return root of the built tree
     */
    static TreeNode randomTree(Random random, int n) {
        TreeNode[] nodes = new TreeNode[n];
        int[] parentIndex = new int[n];
        int[] depth = new int[n];
        List<int[]> freeSlots = new ArrayList<>();
        nodes[0] = new TreeNode(0);
        parentIndex[0] = -1;
        depth[0] = 1;
        freeSlots.add(new int[] { 0, 0 });
        freeSlots.add(new int[] { 0, 1 });
        for (int i = 1; i < n; i++) {
            int[] slot = freeSlots.remove(random.nextInt(freeSlots.size()));
            nodes[i] = new TreeNode(0);
            parentIndex[i] = slot[0];
            depth[i] = depth[slot[0]] + 1;
            if (slot[1] == 0) {
                nodes[slot[0]].left = nodes[i];
            } else {
                nodes[slot[0]].right = nodes[i];
            }
            if (depth[i] < 10) {
                freeSlots.add(new int[] { i, 0 });
                freeSlots.add(new int[] { i, 1 });
            }
        }
        assignDigitsWithinIntRange(random, nodes, parentIndex);
        return nodes[0];
    }

    /**
     * Assigns random digits so that the exact root-to-leaf sum stays at most
     * Integer.MAX_VALUE.
     *
     * @param random      source of the digits
     * @param nodes       all nodes, with every parent placed before its children
     * @param parentIndex index of each node's parent in nodes, or -1 for the root
     */
    static void assignDigitsWithinIntRange(Random random, TreeNode[] nodes, int[] parentIndex) {
        long[] weight = new long[nodes.length];
        for (int i = nodes.length - 1; i >= 0; i--) {
            if (weight[i] == 0) {
                weight[i] = 1;
            }
            if (parentIndex[i] >= 0) {
                weight[parentIndex[i]] += 10 * weight[i];
            }
        }
        long budget = Integer.MAX_VALUE;
        for (int i = 0; i < nodes.length; i++) {
            int maxDigit = (int) Math.min(9, budget / weight[i]);
            nodes[i].val = random.nextInt(maxDigit + 1);
            budget -= nodes[i].val * weight[i];
        }
    }
}