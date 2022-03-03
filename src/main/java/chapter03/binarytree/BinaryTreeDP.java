package chapter03.binarytree;

import utils.TreeNode;

/**
 * <h2>树形动态规划</h2>
 * <h3>1. 二叉树的直径</h3>
 * <h3>2. 二叉树的最大路径和</h3>
 */
@SuppressWarnings("unchcked")
public class BinaryTreeDP {

    private static int max = Integer.MIN_VALUE;

    private static int diameterOfBinaryTree(TreeNode root){
        diameterOfBinaryTreeDFS(root);
        return max;
    }

    private static int diameterOfBinaryTreeDFS(TreeNode root){
        if (root == null)
            return 0;
        int leftDepth = diameterOfBinaryTreeDFS(root.left);
        int rightDepth = diameterOfBinaryTreeDFS(root.right);
        max = Math.max(max, leftDepth + rightDepth);
        return Math.max(leftDepth, rightDepth) + 1;
    }


    /**
     * <h3>思路: 二叉树的最大路径和</h3>
     * <h3>注: 这个题有人提到说需要打印出最大和的路径...思考下怎么做</h3>
     * <h3>注: 大致思路</h3>
     * <h3>1. 准备两个链表, 一个用于保存最大和对应的路径, 另一个用于保存当前路径和对应的路径</h3>
     * <h3>2. 每次最大值更新的时候, 就更新对应的链表; 每次返回前就将当前结点加入对应的链表</h3>
     * TODO 明天尝试下
     */
    private static int maxPathSum(TreeNode root){
        dfs(root);
        return max;
    }


    /**
     * <h3>思路: 这个题的思路和最大连续子数组和几乎一致</h3>
     */
    private static int dfs(TreeNode root){
        if (root == null) return 0;
        // 如果左子树路径和小于 0, 那么显然不能够让当前结点更大, 所以就让当前结点和右子树组合
        int leftMax = Math.max(dfs(root.left), 0);
        // 如果右子树路径和小于 0, 那么显然不能够让当前结点更大, 所以就让当前结点和左子树组合
        int rightMax = dfs(root.right);

        max = Math.max(max, leftMax + rightMax + root.value);
        // 选择左右路径中最大的路径和返回即可
        return Math.max(leftMax, rightMax) + root.value;
    }
}
