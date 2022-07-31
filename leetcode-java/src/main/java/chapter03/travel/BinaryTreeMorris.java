package chapter03.travel;

import chapter03.TreeNode;

/**
 * <h2>Morris 算法</h2>
 */
public class BinaryTreeMorris {

    /**
     * <h3>产生 Morris 序的算法</h3>
     * @param root 根结点
     */
    private static void morris(TreeNode root){
        TreeNode current = root;
        TreeNode mostRight = null;

        while (current != null){
            // 如果有左子树就可以到达两次
            if (current.left != null){
                mostRight = current.left;
                while (mostRight.right != null && mostRight.right != current)
                    mostRight = mostRight.right;
                // 第一次到达
                if (mostRight.right == null){
                    mostRight.right = current;
                    current = current.left;
                    continue;
                }else{
                    // 第二次达到
                    mostRight.right = null;
                }
            }
            // 如果没有左子树就只能够到达一次
            current = current.right;
        }
    }

    /**
     * <h3> Morris 序产生前序遍历</h3>
     * @param root 根结点
     */
    private static void preorderMorris(TreeNode root){
        TreeNode current = root;
        TreeNode mostRight = null;

        while (current != null){
            if (current.left != null){
                mostRight = current.left;
                while (mostRight.right != null && mostRight.right != current)
                    mostRight = mostRight.right;
                if (mostRight.right == null){
                    // 第一次到达就直接输出
                    System.out.println(current.value);
                    mostRight.right = current;
                    current = current.left;
                    continue;
                }else{
                    mostRight.right = null;
                }
            }else{
                // 第一次达到输出
                System.out.println(current.value);
            }
            current = current.right;
        }
    }

    /**
     * <h3> Morris 序产生中序遍历</h3>
     * @param root 根结点
     */
    private static void infixorderMorris(TreeNode root){
        TreeNode current = root;
        TreeNode mostRight = null;

        while (current != null){
            if (current.left != null){
                mostRight = current.left;
                while (mostRight.right != null && mostRight.right != current)
                    mostRight = mostRight.right;
                if (mostRight.right == null){
                    // 第一次到达不做任何输出
                    mostRight.right = current;
                    current = current.left;
                    continue;
                }else{
                    mostRight.right = null;
                    System.out.println(current.value);
                }
            }else{
                // 仅达到一次的立刻输出
                System.out.println(current.value);
            }
            current = current.right;
        }
    }

    private static void postorderMorris(TreeNode root){
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
                    printTree(current.left);
                }
            }
            current = current.right;
        }
        printTree(root);
    }

    /**
     * <h3> 反序输出左子树 </h3>
     */
    private static TreeNode reverseTree(TreeNode root){
        TreeNode previous = null;
        TreeNode current = root;
        TreeNode next = null;
        while (current != null){
            next = current.right;
            current.right = previous;
            previous = current;
            current = next;
        }
        return previous;
    }

    /**
     * <h3>逆序输出</h3>
     */
    private static void printTree(TreeNode root){
        TreeNode tail = reverseTree(root);
        TreeNode current = tail;
        while (current != null){
            System.out.print(current.value + "->");
            current = current.right;
        }
        reverseTree(tail);
    }
}
