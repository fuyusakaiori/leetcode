package chapter03;

import utils.TreeNode;

import java.util.LinkedList;

/**
 * <p>恢复二叉搜索树</p>
 * <p>题目意思是: 正常的二叉搜索树中有两个结点被交换了, 导致这棵二叉树不符合二叉搜索树的规范</p>
 * <p>所以现在要求找到这两个错误交换的结点, 并且将其交换回去</p>
 * <p>1. 交换两个结点的值, 可以不用交换两个结点的位置</p>
 * <p>2. 交换两个结点的位置, 而不能够只交换两个结点的值</p>
 * <p>3. 能否采用时间复杂度为 O(N), 空间复杂度为 O(1) 的算法解决?</p>
 * <p>这里有好几个注意点需要了解</p>
 * <p><strong>1. 二叉树中通常不要求交换两个结点的位置, 可以直接交换两个结点值就行</strong></p>
 * <p><strong>2. 二叉搜索树的题目, 通常都需要借助它的性质来做题, 为数不多可以考虑放入数组或者链表解决的</strong></p>
 * <p><strong>3. 通常涉及到空间复杂度为 O(1) 的树的相关题目时, 基本就是采用 Morris 遍历实现的</strong></p>
 */
public class BinarySearchTreeRecover
{
    public static void main(String[] args)
    {
        TreeNode root = new TreeNode(1);
        TreeNode node1 = new TreeNode(3);
        TreeNode node2 = new TreeNode(2);
        root.left = node1;
        node1.right = node2;
        recoverTree(root);
    }

    /**
     * <p>核心思路</p>
     * <p>1. <strong>中序遍历</strong>二叉搜索树, 正常情况下得到的结果一定是完全升序的, 错误的情况就会出现降序</p>
     * <p>2. 两个错误的结点可能是相邻的, 也可能是不相邻的, 这就导致降序的次数是不同的</p>
     * <p>3. 如果两个错误结点相邻, 那么整个中序序列中只会出现一次降序; 如果两个错误结点不相邻, 那么就会出现两次降序</p>
     * <p>4. 如果只出现一次降序, 那么直接交换两个相邻结点就可以了</p>
     * <p>5. 如果出现两次降序, 那么就需要记录第一次降序时较大的那个数, 然后和第二次降序时较小的那个数交换</p>
     * <p>6. 总结得到就是, 只要出现降序, 那么大的那个结点被赋值后不再改变, 小的那个结点为最后一次降序的值</p>
     * <p>可以不用遍历之后放在数组里处理, 只需要借助临时变量就可以处理</p>
     */
    private static void recoverTree(TreeNode root){
        recoverTreeMorris(root);
    }

    /**
     * <p>采用递归方式实现</p>
     * <p>这里要保存的不是父结点, 而是前一个结点, 前一个结点不一定就是当前结点的父结点</p>
     * <p>所以只能够用成员变量保存</p>
     */
    private static TreeNode first;
    private static TreeNode second;
    private static TreeNode previous;
    private static void recoverTreeRecursive(TreeNode root){
        if(root == null)
            return;
        recoverTreeRecursive(root.left);
        // 前一个数如果大于后一个数, 那么肯定是发生了降序
        if (previous != null && previous.value > root.value){
            // 如果第一个结点已经被赋值, 那么之后不再进行更新
            first = first == null ? previous : first;
            second = root;
        }
        previous = root;
        recoverTreeRecursive(root.right);
    }

    /**
     * 采用非递归方式实现
     */
    private static void recoverTreeUnRecursive(TreeNode root){
        TreeNode previous = null;
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
    }

    /**
     * 采用 Morris 算法实现
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
        int temp = first.value;
        first.value = second.value;
        second.value = temp;
    }

    /**
     * <p>不能够只交换结点的值, 而是要交换结点的位置, 这种情况非常复杂</p>
     * <p>结构上交换需要考虑非常多的情况, 这道题总共存在 14 种情况, 只能够分类讨论</p>
     */
    private static void recoverTreeOnStructure(TreeNode root){

    }


}
