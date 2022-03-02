package chapter03.binarysearch;

import utils.TreeNode;

import java.util.LinkedList;

/**
 * <h2>二叉搜索树和数学有关的题目</h2>
 * <h3>1. 二叉搜索树的最小绝对值</h3>
 * <h3>2. 二叉搜索树的范围和</h3>
 * <h3>3. 二叉搜索树的众数</h3>
 * <h3>注: 这些题目基本都不难, 考的频率也很低, 但是做的人很多, 所以也记录在这里</h3>
 */
public class BinarySearchMath {

    /**
     * <h3>思路: 二叉树的最小绝对值, 基于中序遍历实现</h3>
     * <h3>1. 递归</h3>
     * <h3>2. 迭代</h3>
     * <h3>注: 如果要求不能够遍历, 那么能够实现吗?</h3>
     */
    private static int getMinimumDifference(TreeNode root){
        int minValue = Integer.MAX_VALUE;
        TreeNode previous = null;
        LinkedList<TreeNode> stack = new LinkedList<>();
        while (!stack.isEmpty() || root != null){
            if(root != null){
                stack.push(root);
                root = root.left;
            }else{
                root = stack.pop();
                if(previous != null)
                    minValue = Math.min(minValue, root.value - previous.value);
                previous = root;
                root = root.right;
            }
        }
        return minValue;
    }

    private static int minValue = Integer.MAX_VALUE;
    private static TreeNode previous = null;
    private static void dfs(TreeNode root){
        if (root == null) return;
        dfs(root.left);
        if (previous != null)
            minValue = Math.min(minValue, root.value - previous.value);
        previous = root;
        dfs(root.right);
    }

    /**
     * <h3>思路: 二叉搜索树的范围和</h3>
     * <h3>1. 递归</h3>
     * <h3>2. 迭代</h3>
     */
    private static int rangeSumBST(TreeNode root, int low, int high){
        int targetSum = 0;
        TreeNode previous = null;
        LinkedList<TreeNode> stack = new LinkedList<>();
        while (!stack.isEmpty() || root != null){
            if(root != null){
                stack.push(root);
                root = root.left;
            }else{
                root = stack.pop();
                if(root.value <= high && root.value >= low)
                    targetSum += root.value;
                root = root.right;
            }
        }
        return minValue;
    }

    private static int targetSum = 0;
    private static void dfs(TreeNode root, int low, int high){
        if(root == null) return;

        dfs(root.left, low, high);
        if (root.value <= high && root.value >= low)
            targetSum += root.value;
        dfs(root.right, low, high);
    }

    /**
     * <h3>思路: 二叉搜索树中的众数</h3>
     */
    private static int[] findMode(TreeNode root){
        return null;
    }
}
