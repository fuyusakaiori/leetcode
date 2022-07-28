package chapter03.binarytree;

import utils.TreeNode;

import java.lang.annotation.Target;
import java.util.*;

/**
 * <h2>验证二叉树</h2>
 * <h3>1. 验证二叉搜索树</h3>
 * <h3>2. 验证平衡二叉树</h3>
 * <h3>3. 验证完全二叉树</h3>
 * <h3>4. 验证对称二叉树</h3>
 * <h3>5. 验证两棵树是否相同</h3>
 */
public class BinaryTreeValidate
{
    public static void main(String[] args) {
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

    private static class BBTWrapper{
        private final int height;
        private final boolean flag;
        public BBTWrapper(int height, boolean flag){
            this.height = height;
            this.flag = flag;
        }
    }

    /**
     * <h3>思路: 验证是否为有效的平衡二叉树</h3>
     * <h3>1. 自底向上: 套路解</h3>
     * <h3>2. 自顶向下: 双重递归</h3>
     */
    private static boolean isValidateBBT(TreeNode root){
        if (root == null) return true;
        return Math.abs(getHeight(root.left) - getHeight(root.right)) >= 1
                       && isValidateBST(root.left) && isValidateBST(root.right);
    }

    private static BBTWrapper dfsBBT(TreeNode root){
        if(root == null) return new BBTWrapper(0, true);

        BBTWrapper left = dfsBBT(root.left);
        BBTWrapper right = dfsBBT(root.right);

        int height = Math.max(left.height, right.height) + 1;
        boolean flag = true;
        if(!left.flag || !right.flag || Math.abs(left.height - right.height) > 1)
            flag = false;
        return new BBTWrapper(height, flag);
    }

    private static int getHeight(TreeNode root){
        if(root == null)
            return 0;
        return Math.max(getHeight(root.left) , getHeight(root.right)) + 1;
    }


    /**
     * <h3>思路: 验证是否为有效的二叉搜索树</h3>
     * <h3>1. 套路解法</h3>
     * <h3>1.1 父结点需要比较左子树返回的最大值和右子树返回的最小值, 然后确定是否有效</h3>
     * <h3>1.2 这里需要返回三个信息, 而函数只能够有一个返回值, 所以需要封装三个条件</h3>
     * <h3>2. 深度遍历: 前序遍历</h3>
     * <h3>2.1 自顶向下的方式, 不停地维护一个区间, 区间具有最大值和最小值</h3>
     * <h3>2.2 遍历到的结点的值必须在这个区间内, 否则就不满足二叉搜索树的条件</h3>
     * <h3>3. 中序遍历: 可以采用深度遍历或者迭代实现</h3>
     * <h3>注: 这个题验证方式其实非常简单, 二叉搜索树的中序遍历结果一定是有序的</h3>
     */
    private static boolean isValidateBST(TreeNode root){
        return dfs(root).flag;
    }

    private static class BSTWrapper{
        private final int maxValue;
        private final int minValue;
        private final boolean flag;
        public BSTWrapper(int maxValue, int minValue, boolean flag) {
            this.maxValue = maxValue;
            this.minValue = minValue;
            this.flag = flag;
        }
    }

    private static BSTWrapper dfs(TreeNode root){
        if (root == null) return null;

        // 获取左子树和右子树的信息
        BSTWrapper left = dfs(root.left);
        BSTWrapper right = dfs(root.right);
        int minValue = root.value;
        int maxValue = root.value;
        // 初始化返回条件: 更新最大值和最小值
        if (left != null){
            // 注: 这里其实只用更新最小值也是可以的, 因为右子树中如果出现更小的, 那么标志肯定直接为 false, 那这个时候就无所谓最小最大了
            minValue = Math.min(minValue, left.minValue);
            maxValue = Math.max(maxValue, left.maxValue);
        }

        if (right != null){
            maxValue = Math.max(maxValue, right.maxValue);
            minValue = Math.min(minValue, right.minValue);
        }

        boolean flag = true;
        // 不满足二叉搜索树的条件
        if (left != null && (!left.flag || left.maxValue >= root.value)) flag = false;
        if (right != null && (!right.flag || right.minValue <= root.value)) flag = false;

        return new BSTWrapper(maxValue, minValue, flag);
    }

    private static boolean dfs(TreeNode root, long min, long max){
        if(root == null) return true;

        if (root.value <= min || root.value >= max)
            return false;

        return dfs(root.left, min, root.value) && dfs(root.right, root.value, max);
    }

    private static boolean infix(TreeNode root){
        long previous = Long.MIN_VALUE;
        LinkedList<TreeNode> stack = new LinkedList<>();
        while (!stack.isEmpty() || root != null){
            if (root != null){
                stack.push(root);
                root = root.left;
            }else {
                root = stack.pop();
                if (previous >= root.value)
                    return false;
                previous = root.value;
                root = root.right;
            }
        }
        return true;
    }

    /**
     * <h3>思路: 验证完全二叉树</h3>
     * <h3>1. 层序遍历: 基本方式</h3>
     * <h3>1.1 如果没有左子结点, 那么右子结点显然不能够有</h3>
     * <h3>1.2 如果没有右子结点, 那么之后也不应该有任何结点</h3>
     * <h3>2. 层序遍历: 封装类</h3>
     * <h3>2.1 记录每个结点的编号, 然后存入链表中, 最后链表的大小应该和编号相等</h3>
     * <h3>注: 原来的解法看不懂了...</h3>
     */
    private static boolean isValidateCBTQueue(TreeNode root){
        boolean flag = false;
        Queue<TreeNode> queue = new LinkedList<>();
        if(root != null) queue.offer(root);
        while (!queue.isEmpty()){
            root = queue.poll();
            if (root.left != null){
                if (flag) return false;
                queue.offer(root.left);
            }else{
                flag = true;
            }
            if (root.right != null){
                if (flag) return false;
                queue.offer(root.right);
            }else{
                flag = true;
            }
        }
        return true;
    }

    private static boolean isValidateCBTList(TreeNode root){
        class Wrapper{
            private final int index;
            private final TreeNode node;
            public Wrapper(int index, TreeNode node)
            {
                this.index = index;
                this.node = node;
            }
        }
        int index = 0;
        Wrapper wrapper = null;
        List<Wrapper> list = new LinkedList<>();
        if (root != null)
            list.add(new Wrapper(1, root));
        while (index < list.size()){
            wrapper = list.get(index++);
            if (wrapper.node != null){
               list.add(new Wrapper(index * 2, wrapper.node.left));
               list.add(new Wrapper(index * 2 + 1, wrapper.node.right));
            }
        }

        return list.get(list.size() - 1).index == list.size();
    }

    /**
     * <h3>思路: 验证是否为堆成二叉树</h3>
     * <h3>1. 深度遍历</h3>
     * TODO <h3>2. 层序遍历: 同时弹出两个结点比较</h3>
     * <h3>2.1 先压入左子树的左子结点和有右子树的右子结点</h3>
     * <h3>2.2 再压入左子树的右子结点和右子树的左子结点</h3>
     */
    private static boolean isSymmetric(TreeNode root){
        return dfs(root, root);
    }

    private static boolean dfs(TreeNode firstRoot, TreeNode secondRoot){
        if (firstRoot == null && secondRoot == null)
            return true;
        if (firstRoot == null || secondRoot == null)
            return false;

        return firstRoot.value == secondRoot.value &&
                       dfs(firstRoot.left, secondRoot.right) &&
                       dfs(firstRoot.right, secondRoot.left);
    }

    private static boolean isSame(TreeNode firstRoot, TreeNode secondRoot){
        if (firstRoot == null && secondRoot == null) return true;

        if (firstRoot == null || secondRoot == null) return false;

        return firstRoot.value == secondRoot.value &&
                       isSame(firstRoot.left, secondRoot.left) &&
                       isSame(firstRoot.right, secondRoot.right);
    }



}
