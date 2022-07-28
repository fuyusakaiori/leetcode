package chapter03.binarysearch;

import utils.TreeNode;

import java.util.LinkedList;

/**
 * <h2>恢复二叉搜索树</h2>
 * <h3>注: 这个题的测试用例不好想, 很难说能够直接写出来</h3>
 */
public class BinarySearchTreeRecover {

    /**
     * <h3>思路: 恢复二叉搜索树</h3>
     * <h3>1. 中序遍历二叉搜索树, 正常情况下得到的结果一定是完全升序的, 错误的情况就会出现降序</h3>
     * <h3>2. 两个错误的结点可能是相邻的, 也可能是不相邻的, 这就导致降序的次数是不同的</h3>
     * <h3>2.1 如果两个错误结点相邻, 那么整个中序序列中只会出现一次降序</h3>
     * <h3>2.2 那么直接交换两个相邻结点就可以了</h3>
     * <h3>2.3 如果两个错误结点不相邻, 那么就会出现两次降序</h3>
     * <h3>2.4 那么就需要记录第一次降序时较大的那个数, 然后和第二次降序时较小的那个数交换</p>
     * <h3>3. 总结得到就是, 只要出现降序, 那么大的那个结点被赋值后不再改变, 小的那个结点为最后一次降序的值</h3>
     */
    private static void recoverTree(TreeNode root){
        TreeNode previous = null, first = null, second = null;
        LinkedList<TreeNode> stack = new LinkedList<>();
        while (!stack.isEmpty() || root != null){
            if (root != null){
                stack.push(root);
                root = root.left;
            }else{
                root = stack.pop();
                if (previous != null && previous.value > root.value){
                    first = first == null ? previous: first;
                    second = root;
                }
                previous = root;
                root = root.right;
            }
        }
        assert first != null;
        swap(first, second);
    }

    private static TreeNode first;
    private static TreeNode second;
    private static TreeNode previous;
    private static void dfs(TreeNode root){
        if(root == null)
            return;
        dfs(root.left);
        // 前一个数如果大于后一个数, 那么肯定是发生了降序
        if (previous != null && previous.value > root.value){
            // 如果第一个结点已经被赋值, 那么之后不再进行更新
            first = first == null ? previous : first;
            second = root;
        }
        previous = root;
        dfs(root.right);
    }

    private static void swap(TreeNode first, TreeNode second){
        int temp = first.value;
        first.value = second.value;
        second.value = temp;
    }


    /**
     * <h3>莫里斯遍历</h3>
     */
    private static void recoverTreeMorris(TreeNode root){
        TreeNode current = root;
        TreeNode mostRight = null;
        TreeNode first = null;
        TreeNode second = null;
        TreeNode previous = null;
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
                    if (previous != null && previous.value > current.value){
                        first = first == null ? previous: first;
                        second = current;
                    }
                }
            }else{
                if (previous != null && previous.value > current.value){
                    first = first == null ? previous: first;
                    second = current;
                }
            }
            previous = current;
            current = current.right;
        }
        assert first != null;
        swap(first, second);
    }


}
