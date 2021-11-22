package chapter03;

import utils.TreeNode;

/**
 * <h2>求取二叉树的深度系列问题</h2>
 * <p>1. 求二叉树的最大深度</p>
 * <p>2. 求二叉树的最小深度</p>
 * <p>3. 求二叉树的最小深度, 保证空间复杂度为 O(1)</p>
 */
public class BinaryTreeDepth
{
    public static void main(String[] args)
    {

    }

    /**
     * <p>获取树的最大深度: DFS、BFS</p>
     * <p>1. BFS 就是计算有多少层</p>
     * <p>2. 这里采用 DFS 实现</p>
     */
    private static int maxDepth(TreeNode root){
        if (root == null) return 0;
        return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
    }

    /**
     * <p>获取树的最小深度</p>
     * <p>1. 路径是从根结点到叶子结点的距离, 不能够是到空结点的距离</p>
     * <p>2. 这里采用递归的方式实现</p>
     */
    private static int minDepth(TreeNode root){
        if (root == null)
            return 0;
        int left = minDepth(root.left);
        int right = minDepth(root.right);
        // 如果其中左子树或者右子树返回的深度是 0, 那么深度为 0 的子树是不可以作为最小深度的
        if (left == 0)
            return right + 1;
        if (right == 0)
            return left + 1;
        return Math.min(left, right) + 1;
    }

    /**
     * 使用 Morris 算法实现空间复杂度为 O(1) 的解法
     */
    private static int minDepthMorris(TreeNode root){
        int depth = 1;
        int minDepth = Integer.MAX_VALUE;
        TreeNode current = root;
        TreeNode mostRight = null;
        while (current != null){
            if (current.left != null){
                mostRight = current.left;
                depth++;
                while (mostRight.right != null && mostRight.right != current){
                    mostRight = mostRight.right;
                    depth++;
                }
                if (mostRight.right == null){
                    minDepth = Math.min(minDepth, depth);
                    depth = 1;
                    mostRight.right = current;
                    current = current.left;
                    depth++;
                    continue;
                }else{
                    mostRight.right = null;
                }
            }else{
                if (current.right == null)
                    minDepth = Math.min(minDepth, depth);
            }
            current = current.right;
        }

        return minDepth;
    }
}
