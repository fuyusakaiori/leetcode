package utils;

import java.util.*;

/**
 * <h2>测试各种写法</h2>
 */
public class Test
{
    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        TreeNode node1 = new TreeNode(2);
        TreeNode node2 = new TreeNode(3);
        TreeNode node3 = new TreeNode(4);
        TreeNode node4 = new TreeNode(5);

        root.left = node1;
        root.right = node2;
        node1.left = node3;
        node1.right = node4;

        diameterOfBinaryTree(root);
    }

    private static int max = 0;

    public static int diameterOfBinaryTree(TreeNode root) {
        dfs(root);
        return max;
    }

    public static int dfs(TreeNode root){
        if(root == null)
            return 0;
        int leftDepth = diameterOfBinaryTree(root.left);
        int rightDepth = diameterOfBinaryTree(root.right);

        max = Math.max(max, leftDepth + rightDepth);
        return Math.max(leftDepth, rightDepth) + 1;
    }

}
