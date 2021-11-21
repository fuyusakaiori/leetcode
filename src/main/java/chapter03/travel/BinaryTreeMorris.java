package chapter03.travel;

import utils.TreeNode;

/**
 * <h2>Morris 算法遍历树结构</h2>
 * <p>时间复杂度: O(N)</p>
 * <p>空间复杂度: O(1)</p>
 */
public class BinaryTreeMorris
{
    public static void main(String[] args)
    {

    }

    /**
     * 产生 Morris 序的算法
     * @param root 根结点
     */
    private static void morris(TreeNode root){
        // 遍历树的临时变量
        TreeNode current = root;
        // 记录左子树的最右结点
        TreeNode mostRight = null;
        while (current != null){
            // 1. 如果结点的左子树不为空, 那么向左侧移动
            if (current.left != null){
                mostRight = current.left;
                // 3. 开始循环遍历, 找到左子树的最右结点
                while (mostRight.right != null && mostRight.right != current)
                    mostRight = mostRight.right;
                // 4. 判断最右结点的右指针是否指向空
                if (mostRight.right == null){
                    // 4.1 将最右结点的右指针指向当前结点
                    mostRight.right = current;
                    // 4.2 然后继续向左移动
                    current = current.left;
                    continue;
                }else{
                    // 5. 最右结点的右指针不指向空的话, 就将指针指向空
                    mostRight.right = null;
                }
            }
            // 2. 如果结点的左子树为空, 那么向右侧移动
            current = current.right;
        }
    }

    /**
     * 借助 Morris 算法产生前序
     * @param root 根结点
     */
    private static void premorris(TreeNode root){
        TreeNode current = root;
        TreeNode mostRight = null;

        while (current != null){
            if (current.left != null){
                mostRight = current.left;
                while (mostRight.right != null && mostRight.right != current)
                    mostRight = mostRight.right;
                if (mostRight.right == null){
                    // 第一次到达可以到达两次的结点时, 立刻输出
                    System.out.print(current.value + "->");
                    mostRight.right = current;
                    current = current.left;
                    continue;
                }else{
                    // 第二次就不需要继续输出
                    mostRight.right = null;
                }
            }else{
                // 只能够到达一次的结点就立刻输出
                System.out.print(current.value + "->");
            }
            current = current.right;
        }
    }

    /**
     * 借助 Morris 算法产生中序
     * @param root 根结点
     */
    private static void infixmorris(TreeNode root){
        TreeNode current = root;
        TreeNode mostRight = null;

        while (current != null){
            if (current.left != null){
                mostRight = current.left;
                while (mostRight.right != null && mostRight.right != current)
                    mostRight = mostRight.right;
                if (mostRight.right == null){
                    // 第一次到达可以到达两次的结点时, 不做任何输出
                    mostRight.right = current;
                    current = current.left;
                    continue;
                }else{
                    // 第二次到达的时候, 立刻输出
                    mostRight.right = null;
                    System.out.print(current.value + "->");
                }
            }else{
                // 只能够到达一次的结点就立刻输出
                System.out.print(current.value + "->");
            }
            current = current.right;
        }
    }

    /**
     * <p>借助 Morris 算法产生后序</p>
     * <p>注: Morris 产生后序是比较麻烦的</p>
     * @param root 根结点
     */
    private static void postmorrsi(TreeNode root){
        TreeNode current = root;
        TreeNode mostRight = null;

        while (current != null){
            if (current.left != null){
                mostRight = current.left;
                while (mostRight.right != null && mostRight.right != current)
                    mostRight = mostRight.right;
                if (mostRight.right == null){
                    // 第一次到达可以到达两次的结点时, 不做任何输出
                    mostRight.right = current;
                    current = current.left;
                    continue;
                }else{
                    // 第二次到达的时候, 立刻输出
                    mostRight.right = null;
                    printTree(current.left);
                }
            }
            current = current.right;
        }
        printTree(root);
    }

    /**
     * Morris 序生成后序时, 需要在第二次到达结点时, 反序输出, 其余时候不做任何处理
     */
    private static TreeNode reverseTree(TreeNode root){
        TreeNode previous = null;
        TreeNode current = root;
        TreeNode next = root.right;
        while (current != null){
            current.right = previous;
            previous = current;
            current = next;
            next = next != null ? next.right:null;
        }
        return previous;
    }

    /**
     * 逆序输出
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
