package chapter03.travel;

import utils.TreeNode;

import java.util.Currency;
import java.util.LinkedList;
import java.util.Queue;

/**
 * <h2>总结: 二叉树的深度和宽度优先遍历</h2>
 * <p>1. 二叉树的递归套路都是固定的, 没有什么特别需要注意的, 相比动态规划中的递归</p>
 * <p>2. 几乎所有二叉树的题目都是在这两种方式的基础上删删改改, 没有什么大的变化</p>
 * <p>3. 迭代的终止条件几乎都是栈不为空</p>
 * <p>非常轻松, 不需要再复习了</p>
 */
public class Summary
{
    public static void main(String[] args)
    {
        TreeNode root = getInstance();
        preorderRecursive(root);
        System.out.println();
        preorderUnRecursive(root);
        System.out.println();
        preorderMorris(root);
        System.out.println();
        infixorderRecursive(root);
        System.out.println();
        infixorderUnRecursive(root);
        System.out.println();
        infixorderMorris(root);
        System.out.println();
        postorderRecursive(root);
        System.out.println();
        postorderUnRecursive(root);
        System.out.println();
        postorderUnRecursive(root, "单栈");
        System.out.println();
        postorderMorris(root);
    }

    /**
     * <p>前序遍历: 递归</p>
     */
    private static void preorderRecursive(TreeNode root){
        if (root == null)
            return;
        System.out.print(root.value + "\t");
        preorderRecursive(root.left);
        preorderRecursive(root.right);
    }

    /**
     * 前序遍历: 非递归
     */
    private static void preorderUnRecursive(TreeNode root){
        if (root == null)
            return;
        LinkedList<TreeNode> stack = new LinkedList<>();
        stack.push(root);
        while (!stack.isEmpty()){
            root = stack.pop();
            System.out.print(root.value + "\t");
            if (root.right != null)
                stack.push(root.right);
            if (root.left != null)
                stack.push(root.left);
        }
    }

    /**
     * 前序遍历: Morris 实现
     */
    private static void preorderMorris(TreeNode root){
        TreeNode mostRight = null;
        TreeNode current = root;
        while (current != null){
            if (current.left != null){

                mostRight = current.left;
                while (mostRight.right != null && mostRight.right != current)
                    mostRight = mostRight.right;
                if (mostRight.right == null){
                    System.out.print(current.value + "\t");
                    mostRight.right = current;
                    current = current.left;
                    continue;
                }else{
                    mostRight.right = null;
                }
            }else{
                System.out.print(current.value + "\t");
            }
            current = current.right;
        }
    }


    /**
     * <p>中序遍历: 递归</p>
     */
    private static void infixorderRecursive(TreeNode root){
        if (root == null)
            return;
        infixorderRecursive(root.left);
        System.out.print(root.value + "\t");
        infixorderRecursive(root.right);
    }

    /**
     * <p>中序遍历: 非递归</p>
     */
    private static void infixorderUnRecursive(TreeNode root){
        LinkedList<TreeNode> stack = new LinkedList<>();
        while (!stack.isEmpty() || root != null){
            if (root != null){
                stack.push(root);
                root = root.left;
            }else{
                root = stack.pop();
                System.out.print(root.value + "\t");
                root = root.right;
            }
        }
    }

    /**
     * 中序遍历: Morris
     */
    private static void infixorderMorris(TreeNode root){
        TreeNode mostRight = null;
        TreeNode current = root;
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
                    System.out.print(current.value + "\t");
                }
            }else{
                System.out.print(current.value + "\t");
            }
            current = current.right;
        }
    }

    /**
     * 后序遍历: 递归
     */
    private static void postorderRecursive(TreeNode root){
        if (root == null)
            return;
        postorderRecursive(root.left);
        postorderRecursive(root.right);
        System.out.print(root.value + "\t");
    }

    /**
     * 后序遍历: 非递归
     */
    private static void postorderUnRecursive(TreeNode root){
        if (root == null)
            return;
        LinkedList<TreeNode> stack = new LinkedList<>();
        LinkedList<TreeNode> collection = new LinkedList<>();
        stack.push(root);
        while (!stack.isEmpty()){
            root = stack.pop();
            collection.push(root);
            if (root.left != null)
                stack.push(root.left);
            if (root.right != null)
                stack.push(root.right);
        }
        while (!collection.isEmpty()){
            System.out.print(collection.pop().value + "\t");
        }
    }

    /**
     * 后序遍历: 非递归, 采用单个栈实现的
     */
    private static void postorderUnRecursive(TreeNode root, Object... args){
        if (root == null)
            return;
        TreeNode current = root;
        TreeNode last = root;
        LinkedList<TreeNode> stack = new LinkedList<>();
        stack.push(root);
        while (!stack.isEmpty()){
            current = stack.peek();
            // 上一次打印的不是左子结点证明, 左子树还没有遍历
            if (current.left != null && current.left != last && current.right != last){
                stack.push(current.left);
            }else if (current.right != null && current.right != last){
                stack.push(current.right);
            }else{
                // 遍历到底的时候, 就需要弹出了
                System.out.print((last = stack.pop()).value + "\t");
            }

        }
    }

    /**
     * 后序遍历: Morris
     */
    private static void postorderMorris(TreeNode root){
        TreeNode mostRight = null;
        TreeNode current = root;
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
                    printf(current.left);
                }
            }
            current = current.right;
        }
        printf(root);
    }

    /**
     * 反转最右侧的子树
     */
    private static TreeNode reverse(TreeNode root){
        TreeNode previous = null;
        TreeNode current = root;
        TreeNode next = current.right;
        while (current != null){
            current.right = previous;
            previous = current;
            current = next;
            next = next != null ? next.right: null;
        }
        return previous;
    }

    /**
     * 逆序打印最右侧的子树
     */
    private static void printf(TreeNode root){
        TreeNode tail = reverse(root);
        TreeNode current = tail;
        while (current != null){
            System.out.print(current.value + "\t");
            current = current.right;
        }
        // 记得还原
        reverse(tail);
    }

    /**
     * 层序遍历
     */
    private static void levelorder(TreeNode root){
        if (root == null)
            return;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()){
            root = queue.poll();
            System.out.print(root.value + "\t");
            if (root.left != null)
                queue.offer(root.left);
            if (root.right != null)
                queue.offer(root.right);
        }
    }

    /**
     * 测试用例
     */
    private static TreeNode getInstance(){
        TreeNode root = new TreeNode(1);
        TreeNode node1 = new TreeNode(2);
        TreeNode node2 = new TreeNode(3);
        TreeNode node3 = new TreeNode(4);
        TreeNode node4 = new TreeNode(5);

        root.left = node1;
        root.right = node2;

        node1.left = node3;
        node2.left = node4;

        return root;
    }
}
