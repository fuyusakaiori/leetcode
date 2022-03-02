package chapter03.binarysearch;

import utils.TreeNode;

import java.util.LinkedList;

/**
 * <h2>二叉搜索树中的基本操作</h2>
 * <h3>1. 二叉搜索树中的查询</h3>
 * <h3>2. 二叉搜索树中第 K 小的元素</h3>
 * <h3>3. 二叉搜索树中的删除</h3>
 * <h3>注: 二叉搜索树的查询或者遍历使用迭代的方式, 不要用递归, 包括二叉树也一样</h3>
 */
public class BinarySearchBasic {


    /**
     * <h3>思路: </h3>
     * <h3>1. 深度遍历</h3>
     * <h3>2. 迭代</h3>
     * <h3>注: 面试题就不要写递归了...</h3>
     */
    private static TreeNode searchBST(TreeNode root, int target){

        while (root != null){
            if (root.value == target)
                return root;
            root = root.value > target ? root.left: root.right;
        }
        return null;
    }

    private static TreeNode searchDFS(TreeNode root, int target){
        if(root == null || root.value == target) return root;
        return root.value > target ? searchBST(root.left, target): searchBST(root.right, target);
    }

    /**
     * <h3>思路: 二叉搜索树中的第 K 小的元素 </h3>
     * <h3>1. 放入链表中后排序得到: 笔试题就可以这么做, 不用动脑子</h3>
     * <h3>2. 中序遍历: 递归或者迭代</h3>
     * <h3>3. 莫里斯遍历: 如果要求空间复杂度为 O(1) </h3>
     */
    private static int kthSmallest(TreeNode root, int k){
        int index = 0;
        LinkedList<TreeNode> stack = new LinkedList<>();
        while (!stack.isEmpty() || root != null){
            if(root != null){
                stack.push(root);
                root = root.left;
            }else{
                root = stack.pop();
                if(index++ == k)
                    return root.value;
                root = root.right;
            }
        }
        return -1;
    }

    private static int count;

    private static int dfs(TreeNode root, int k){
        if(root == null)
            return -1;

        int left = kthSmallest(root.left, k);
        if(++count == k)
            return root.value;
        int right = kthSmallest(root.right, k);

        return left != -1 ? left : right;
    }

    private static int morris(TreeNode root, int k){
        int index = 1;
        TreeNode current = root;
        TreeNode mostRight = null;
        while (current != null){
            if (current.left != null){
                mostRight = current.left;
                while (mostRight.right != null && mostRight.right != current)
                    mostRight = mostRight.right;
                if (mostRight.right == null){
                    mostRight.right = current;
                    current = current.left;
                    continue;
                }else{
                    mostRight.right = null;
                    if (index++ == k) return current.value;
                }
            }else{
                if(index++ == k) return current.value;
            }
            current = current.right;
        }
        return -1;
    }

    /**
     * <h3>思路: 删除二叉搜索树中的结点</h3>
     * <h3>1. 如果删除的是叶子结点, 那么直接删除就行</h3>
     * <h3>2. 如果删除的是非叶子结点, 那么需要分为两种情况</h3>
     * <h3>2.1 非叶子结点具有一个叶子结点, 直接让父结点指向孙子结点</h3>
     * <h3>2.2 非叶子结点具有两个叶子结点, 找左子树的最右结点或者右子树的最左结点替换删除的结点</h3>
     */
    private static TreeNode deleteBST(TreeNode root, int target){
        return null;
    }

    private static TreeNode insertBST(TreeNode root, int value){
        return null;
    }

}
