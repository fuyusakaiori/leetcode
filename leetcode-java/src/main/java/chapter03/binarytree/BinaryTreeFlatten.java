package chapter03.binarytree;

import chapter03.TreeNode;

import java.util.LinkedList;
import java.util.List;

/**
 * 二叉树展开为链表
 * <p>1. 采用空间复杂度为 O(N) 的算法完成</p>
 * <p>2. 采用空间复杂度为 O(1) 的算法完成</p>
 */
public class BinaryTreeFlatten
{
    public static void main(String[] args)
    {

    }

    /**
     * 采用集合完成的
     */
    private static TreeNode flattenByCollection(TreeNode root){
        List<TreeNode> nodes = new LinkedList<>();
        infixorder(root, nodes);
        for(int i = 0;i < nodes.size() - 1;i++){
            nodes.get(i).right = nodes.get(i + 1);
            nodes.get(i).left = null;
        }
        return root;
    }

    public static void infixorder(TreeNode root, List<TreeNode> nodes){
        if(root == null)
            return;
        nodes.add(root);
        infixorder(root.left, nodes);
        infixorder(root.right, nodes);
    }

    private static TreeNode previous;
    /**
     * 原地展开, 仅借用唯一的临时变量
     */
    private static void flatten(TreeNode root){
        if(root == null)
            return;

        TreeNode right = root.right;
        TreeNode left = root.left;
        if(previous != null)
            previous.right = root;
        else
            root.right = left;
        root.left = null;
        previous = root;
        flatten(left);
        flatten(right);
    }
}
