package chapter03.binarytree;

import chapter03.TreeNode;

import java.util.HashMap;
import java.util.Map;

/**
 * <h2>树形动态规划</h2>
 * <h3>1. 二叉树的直径</h3>
 * <h3>2. 二叉树的最大路径和</h3>
 * <h3>3. 打家劫舍 III</h3>
 * <h3>注: 这三个题本质都是动态规划的题目, 但是因为都是在树中的, 所以整合到一起</h3>
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


    /**
     * <h3>思路: 打家劫舍 III</h3>
     * <h3>1. 如果当前房子的钱被偷了, 那么左孩子和右孩子的钱就不能够偷</h3>
     * <h3>2. 只能够偷左孩子的两个孩子结点, 或者右孩子的两个孩子结点, 也就是要跳过一层去偷</h3>
     * <h3>3. 如果当前房子的钱没有被偷, 那么左孩子和右孩子的钱就可以一起偷, 而不是只偷一个</h3>
     * <h3>注: 这个套路是非常容易理解且简单的, 官方题解的答案要绕点弯子, 本身和我的逻辑没有太大区别</h3>
     */
    private static int rob(TreeNode root){
        Map<TreeNode, Integer> map = new HashMap<>();
        return dfsprefix(root, map);
    }


    private static int dfsprefix(TreeNode root, Map<TreeNode, Integer> map){
        if(root == null) return 0;
        if (map.containsKey(root))
            return map.get(root);
        int left = 0;
        int right = 0;
        if (root.left != null)
            left += dfsprefix(root.left.left, map) + dfsprefix(root.left.right, map);
        if (root.right != null)
            right += dfsprefix(root.right.left, map) + dfsprefix(root.right.right, map);
        int currentValue = root.value + left + right;
        int otherValue = dfsprefix(root.left, map) + dfsprefix(root.right, map);
        map.put(root, Math.max(currentValue, otherValue));
        return map.get(root);
    }
    // 如果选择当前结点
    private static final Map<TreeNode, Integer> select = new HashMap<>();
    // 如果不选择当前结点
    private static final Map<TreeNode, Integer> noSelect = new HashMap<>();

    /**
     * <h3>思路: 后序遍历</h3>
     */
    private static void dfspost(TreeNode root){
        if (root == null) return ;

        dfspost(root.left);
        dfspost(root.right);
        // 如果选择当前结点, 那么显然能够偷到的钱就是当前值加上左右两个子结点没有偷的钱
        select.put(root, root.value
                                 + noSelect.getOrDefault(root.left, 0)
                                 + noSelect.getOrDefault(root.right, 0));
        // 如果不选择当前结点, 那么就考虑要不要选左子结点或者右子结点
        noSelect.put(root, Math.max(
                select.getOrDefault(root.left, 0),
                noSelect.getOrDefault(root.left, 0))
                                   + Math.max(
                select.getOrDefault(root.right, 0),
                noSelect.getOrDefault(root.right, 0)));
    }
}
