package chapter03.travel;

import chapter03.TreeNode;

import java.util.LinkedList;

/**
 * <h2>采用递归 + 非递归的方式遍历二叉树结构</h2>
 * <h3>1. 二叉树的前序遍历</h3>
 * <h3>2. 二叉树的中序遍历</h3>
 * <h3>3. 二叉树的后序遍历</h3>
 */
public class BinaryTreeDeepFirstSearch {

    /**
     * <h3>思路: 递归实现前序遍历</h3>
     * @param root 根结点
     */
    private static void preorder1(TreeNode root){
        if (root == null) return;
        // 这里可以执行任意操作
        System.out.println(root.value);
        preorder1(root.left);
        preorder1(root.right);
    }

    /**
     * <h3>思路: 循环实现前序遍历 => 类似于层序遍历</h3>
     * @param root 根结点
     */
    private static void preorder2(TreeNode root){
        LinkedList<TreeNode> stack = new LinkedList<>();
        if (root != null) stack.push(root);
        while (!stack.isEmpty()){
            root = stack.pop();
            // 这里可以做任意操作
            System.out.println(root.value);
            if (root.right != null)
                stack.push(root.right);
            if (root.left != null)
                stack.push(root.left);
        }
    }

    /**
     * <h3>思路: 递归实现中序遍历</h3>
     * @param root 根结点
     */
    private static void infixorder1(TreeNode root){
        if (root == null) return;
        infixorder1(root.left);
        // 这里可以做任何操作
        System.out.println(root.value);
        infixorder1(root.right);
    }

    /**
     * <h3>思路: 循环实现中序遍历</h3>
     * <h3>1. 中序遍历的结点访问顺序和处理顺序是不同的, 先访问父结点, 但是先处理左子结点</h3>
     * <h3>2. 也就是说父结点应该先被压入栈中, 但是左子结点也是相当于父结点, 所以不停入栈, 直到为空</h3>
     * <h3>3. 之后出栈相当于处理左子结点, 也相当于处理父结点, 所以出栈之后就会去遍历也就是处理右子结点</h3>
     * @param root 根结点
     */
    private static void infixorder2(TreeNode root){
        LinkedList<TreeNode> stack = new LinkedList<>();
        while (!stack.isEmpty() || root != null){
            if (root != null){
                stack.push(root);
                root = root.left;
            }else{
                root = stack.pop();
                System.out.println(root.value);
                root = root.right;
            }
        }
    }

    /**
     * <h3>思路: 递归实现后序遍历</h3>
     * @param root 根结点
     */
    private static void postorder1(TreeNode root){
        if (root == null) return;
        postorder1(root.left);
        postorder1(root.right);
        // 这里可以执行任意操作
        System.out.println(root.value);
    }

    /**
     * <h3>思路: 循环实现后序遍历</h3>
     * <h3>注: 有两种实现方式: 1.采用双栈实现 2. 采用单个栈实现</h3>
     * <h3>1. 采用双栈实现: 得到父->右->左的顺序, 然后逆序输出, 就是左->右->父</h3>
     * <h3>2. 采用单个栈实现: 出栈的时候借助临时变量判断是继续压栈, 还是接着出栈</h3>
     * @param root 根结点
     */
    private static void postorder2(TreeNode root){
        LinkedList<TreeNode> stack = new LinkedList<>();
        LinkedList<TreeNode> collection = new LinkedList<>();
        if (root != null) stack.push(root);
        while (!stack.isEmpty()){
            root = stack.pop();
            collection.push(root);
            if (root.left != null)
                stack.push(root.left);
            if (root.right != null)
                stack.push(root.right);
        }

        while (!collection.isEmpty()){
            System.out.println(collection.pop().value);
        }
    }

    private static void postorder3(TreeNode root){
        if (root == null)
            return;
        TreeNode current = null;
        TreeNode last = root;
        LinkedList<TreeNode> stack = new LinkedList<>();
        stack.push(root);
        while (!stack.isEmpty()){
            current = stack.peek();
            if (current.left != null && last != current.left)
                stack.push(current.left);
            else if (current.right != null && last != current.right)
                stack.push(current.right);
            else
                System.out.print((last = stack.pop()) + "->");
        }
    }

}
