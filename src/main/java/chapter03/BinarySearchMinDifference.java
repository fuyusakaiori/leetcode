package chapter03;

import utils.TreeNode;

/**
 * <h2>二叉搜索树的最小绝对差</h2>
 * <p>这个题非常简单, 放上来的目的只是为了说明二叉搜索树的常规解法</p>
 */
public class BinarySearchMinDifference
{
    public static void main(String[] args)
    {

    }

    private TreeNode previous;
    private int min = Integer.MAX_VALUE;

    public int getMinimumDifference(TreeNode root) {
        minAbsolute(root);
        return min;
    }

    public void minAbsolute(TreeNode root){
        if(root == null)
            return;
        minAbsolute(root.left);
        if(previous != null)
            min = Math.min(root.value - previous.value, min);
        previous = root;
        minAbsolute(root.right);
    }
}
