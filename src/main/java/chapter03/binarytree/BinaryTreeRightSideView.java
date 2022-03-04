package chapter03.binarytree;

import utils.TreeNode;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * <h3>二叉树的右视图</h3>
 */
public class BinaryTreeRightSideView {


    /**
     * <h3>思路: 二叉树的左、右视图</h3>
     * <h3>1. 深度遍历: 我第一次想出来的就是这个做法</h3>
     * <h3>1.1 这个题核心就是算层数, 相同层数只加最右边的值</h3>
     * <h3>1.2 需要先遍历右子树再遍历左子树, 只要右子树中有结点添加了, 那么层数就会增加, 下次就不会添加了</h3>
     * <h3>2. 层序遍历</h3>
     * <h3>1.1 容器大小小于层数的时候才添加</h3>
     * <h3>1.2 每层结束层数增加</h3>
     * <h3>注: 淦, 第二次做还想不出来了</h3>
     */
    private static List<Integer> rightSideView(TreeNode root){
        int level = 0;
        TreeNode currentLevelEnd = root;
        TreeNode nextLevelEnd = null;
        List<Integer> view = new LinkedList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        if (root != null)
            queue.offer(root);
        while (!queue.isEmpty()){
            root = queue.poll();
            if (view.size() <= level)
                view.add(root.value);
            if (root.left != null){
                queue.offer(root.left);
                nextLevelEnd = root.left;
            }
            if (root.right != null){
                queue.offer(root.right);
                nextLevelEnd = root.right;
            }
            if (currentLevelEnd == root){
                currentLevelEnd = nextLevelEnd;
                level++;
            }
        }

        return view;
    }


    private static void dfs(TreeNode root, int level, List<Integer> view){
        if (root == null) return;
        if (view.size() <= level)
            view.add(root.value);
        dfs(root.right, level + 1, view);
        dfs(root.left, level + 1, view);

    }


}
