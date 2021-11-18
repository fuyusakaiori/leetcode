package chapter03;

import utils.TreeNode;

import java.util.*;

// 判断各种树
public class TreeType
{
    public static void main(String[] args)
    {
        // 构造二叉搜索树
        TreeNode root = new TreeNode(1);
        TreeNode node1 = new TreeNode(2);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(4);
        TreeNode node5 = new TreeNode(4);
        TreeNode node6 = new TreeNode(3);
        TreeNode node7 = new TreeNode(10);
        TreeNode node8 = new TreeNode(20);

        root.left = node1;
        node1.left = node3;
        node1.right = node4;

        root.right = node2;
        node2.left = node5;
        node2.right = node6;

        System.out.println(isBSTRecursive(root));
        System.out.println(isBSTUnRecursive(root));
        System.out.println(isCBTWidth(root));
        System.out.println(isBBTByTrick(root).isBalanced);

        TreeNode node = new TreeNode(0);
        System.out.println(isBSTRecursive(node));

    }

    //=======================常规解法============================

    // 判断树是否是二叉搜索树
    // 二叉搜索树特征是, 左子树的所有结点小于父结点, 右子树的所有结点大于父结点

    // 1. 递归实现判断是否为二叉搜索树
    private static double preValue = Integer.MIN_VALUE;
    public static boolean isBSTRecursive(TreeNode root){
        // 1. 只要子树为空, 那么这个树就认为是二叉搜索树
        if (root == null)
            return true;
        // 2. 左子树是否是二叉搜索树
        boolean isLeftBst = isBSTRecursive(root.left);
        if (!isLeftBst)
            return false;
        // 3. 左子树是二叉搜索树的前提下, 检查父结点和左子树结点的关系
        if (preValue < root.value)
            preValue = root.value;
        else
            return false;
        // 4. 检查右子树是否是二叉搜索树
        return isBSTRecursive(root.right);
    }

    // 2. 循环实现判断是否为二叉搜索树
    private static boolean isBSTUnRecursive(TreeNode root){
        int preValue = Integer.MIN_VALUE;
        LinkedList<TreeNode> stack = new LinkedList<>();

        while (!stack.isEmpty() || root != null){
            if (root != null){
                stack.push(root);
                root = root.left;
            }else{
                root = stack.pop();
                if (preValue > root.value)
                    return false;
                else
                    preValue = root.value;
                root = root.right;
            }
        }

        return true;
    }

    // 判断是否是完全二叉树:
    // 完全二叉树的特征：树的最后一层结点必须是从左到右依次排列, 其余层的结点必须是满的
    private static boolean isCBTWidth(TreeNode root){
        boolean flag = false;
        TreeNode left = null;
        TreeNode right = null;
        // 利用层次遍历完成
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()){
            root = queue.poll();
            left = root.left;
            right = root.right;
            // 1. 如果左子结点不存在, 右子结点存在, 那么就会直接返回
            // 2. 如果左子结点存在, 右子结点不存在, 那么这种情况仅允许出现一次, 也就是说之后的所有结点都只能是叶子
            //    也就是在 flag = true 的情况下不再之后遍历的结点存在子结点
            if ((flag && (left != null || right != null)) || (left == null && right != null))
                return false;
            // 3. 如果右子结点已经为空, 那么就证明这种情况出现了, 之后遍历的结点只能够是叶子, 将 flag = true;
            if (right == null)
                flag = true;

            if (left != null)
                queue.add(left);
            if (right != null)
                queue.add(right);
        }
        return true;
    }

    // 判断是否是满二叉树
    private static boolean isFBT(TreeNode root){

        return true;
    }

    // 判断是否是平衡二叉树
    private static boolean isBBT(){

        return true;
    }

    //=======================套路解法===========================

    private static class Info{
        private final boolean isBinarySearch;
        private final int maxValue;
        private final int minValue;

        public Info(boolean isBinarySearch, int maxValue, int minValue)
        {
            this.isBinarySearch = isBinarySearch;
            this.maxValue = maxValue;
            this.minValue = minValue;
        }
    }

    private static Info isBSTByTrick(TreeNode root){
        if (root == null)
            // 这里之所以返回空的原因, 是因为最大值和最小值的默认值不太好确定
            return null;

        // 1. 向两棵子树索要信息
        Info left = isBSTByTrick(root.left);
        Info right = isBSTByTrick(root.right);

        // 2. 更新根结点的最大最小值
        int minValue = root.value;
        int maxValue = root.value;
        if (left != null){
            // 实际左子树只需要提供最大值, 但是为了所有过程一样, 也需要比较最小值
            maxValue = Math.max(maxValue, left.maxValue);
            minValue = Math.min(minValue, left.minValue);
        }

        if (right != null){
            minValue = Math.min(minValue, right.minValue);
            maxValue = Math.max(maxValue, right.maxValue);
        }

        // 3. 左子树的结点的最大值必须小于根结点, 右子树的最小值必选大于根结点
        boolean isBinarySearch = true;
        if (left != null && (!left.isBinarySearch || left.maxValue > root.value))
            isBinarySearch = false;
        if (right != null && (!right.isBinarySearch || right.minValue < root.value))
            isBinarySearch = false;

        return new Info(isBinarySearch, maxValue, minValue);
    }

    // TODO 不太好用技巧解
    private static void isCBTByTrick(TreeNode root){ }

    // 这里其实可以再添加一个布尔变量, 但是没有必要, 你可以等到最后在计算
    private static class ReturnInfo{
        private final int height;
        private final int nodes;

        public ReturnInfo(int height, int nodes)
        {
            this.height = height;
            this.nodes = nodes;
        }
    }

    private static ReturnInfo isFBTByTrick(TreeNode root){
        if (root == null)
            return new ReturnInfo(0, 0);

        // 1. 获取信息
        ReturnInfo left = isFBTByTrick(root.left);
        ReturnInfo right = isFBTByTrick(root.right);
        // 2. 拆黑盒
        int height = Math.max(left.height, right.height) + 1;
        int nodes = left.nodes + right.nodes + 1;

        return new ReturnInfo(height, nodes);
    }


    // 每个子树向父结点提供的信息
    private static class ReturnType{
        // 1. 自己是否是平衡的
        private final boolean isBalanced;
        // 2. 自己的高度
        private final int height;

        public ReturnType(boolean isBalanced, int height)
        {
            this.isBalanced = isBalanced;
            this.height = height;
        }
    }

    private static ReturnType isBBTByTrick(TreeNode root){
        // 结束条件
        if (root == null)
            return new ReturnType(true, 0);

        // 1. 获取左子树提供的信息
        ReturnType left = isBBTByTrick(root.left);
        // 2. 获取右子树提供的信息
        ReturnType right = isBBTByTrick(root.right);

        // 3. 判断左子树是否是平衡树、判断右子树是否是平衡树、判断两者高度差是否小于等于 1
        boolean isBalanced = left.isBalanced && right.isBalanced
                                     && Math.abs(left.height - right.height) <= 1;
        // 4. 如果的确是平衡的, 那么就继续更新高度
        int height = Math.max(left.height, right.height) + 1;


        return new ReturnType(isBalanced, height);
    }

}
