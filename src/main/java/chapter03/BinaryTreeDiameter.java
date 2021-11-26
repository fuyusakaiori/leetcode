package chapter03;

import utils.TreeNode;

/**
 * <h2>二叉树的直径</h2>
 * <p>问题描述: 两个结点之间边的数量称为路径长度, 而最大的路径长度就称为二叉树的直径</p>
 * <p>1. 两个结点之间边的数量 + 1 = 路径经过的结点数量</p>
 * <p>2. 这个关系是必要的, 可能看起来没什么用</p>
 * <p>总结: 树的题目基本都是 DFS、BFS 完成, DFS 既需要考虑使用前序、中序、还是后序</p>
 */
public class BinaryTreeDiameter
{
    public static void main(String[] args)
    {

    }

    /**
     * <p>问题反思</p>
     * <p>1.我们只需要知道左右子树的最大深度, 那么我们就可以将左右子树最大深度相加, 就可以得到直径了不是?</p>
     * <p>2.实际递归的时候并没有这么直接, 你在根结点处需要将左右子树相加, 而在子结点处又需要判断最大深度</p>
     * <p>3.这里的两个行为显然是不同, 而递归是一定要求逻辑相同才能够写出来的</p>
     * <p>4.此时你可能考虑, 那我递归从左右子树开始不行吗?回到根结点的时候, 相加不就行了?</p>
     * <p>5.很不凑巧, 直径并不一定需要经过树的根结点, 所以这种做法是没有办法通过所有用例的</p>
     * <p>6.所以最暴力的办法, 就是将每个结点都作为路径的起始结点来尝试, 这就需要双重递归</p>
     */
    private static int diameterOfBinaryTreeViolence(TreeNode root){
        if (root == null)
            return 0;
        // 获取根结点的左右子树的最大深度
        int leftPath = recursive(root.left);
        int rightPath = recursive(root.right);
        // 获取以子结点为根结点的直径
        int leftDiameter = diameterOfBinaryTreeViolence(root.left);
        int rightDiameter = diameterOfBinaryTreeViolence(root.right);
        // 比较左右子树的直径和根结点的直径
        return Math.max(leftPath + rightPath, Math.max(leftDiameter, rightDiameter));
    }

    /**
     * 获取最大深度
     */
    private static int recursive(TreeNode root){
        if (root == null)
            return 0;
        return Math.max(recursive(root.left), recursive(root.right)) + 1;
    }

    // 记录左子树+右子树的最大结点数
    private static int maxNodes = 1;
    /**
     * <p>核心思路: 后序遍历完成的</p>
     * <p>1. 左子树的最大深度 + 右子树的最大深度 + 1 就是直径经过的最大结点数, 而最大结点数 - 1 刚好就是树的最大直径</p>
     * <p>2. 有了这层关系之后, 显然在返回左右子树的深度之后, 我们可以立即知道以该结点为根结点的话, 直接是多少</p>
     * <p>3. 然后向上回溯, 依次比较</p>
     */
    private static int diameterOfBinaryTree(TreeNode root){
        // 这里的返回值是最大深度, 没有什么用
        deepFirstSearch(root);
        // 最大结点数 - 1 = 直径
        return maxNodes - 1;
    }

    /**
     * 深度优先遍历找最大结点数, 或者是最大深度
     */
    private static int deepFirstSearch(TreeNode root){
        if(root == null)
            return 0;

        int leftMax = deepFirstSearch(root.left);
        int rightMax = deepFirstSearch(root.right);
        maxNodes = Math.max(maxNodes, leftMax + rightMax + 1);
        // 继续向上返回最大深度, 或者最大结点数
        return Math.max(leftMax, rightMax) + 1;
    }
}
