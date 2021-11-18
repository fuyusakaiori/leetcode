package chapter03;

import utils.TreeNode;

/**
 * <h2>Morris 遍历</h2>
 * <p>特点: 1.时间复杂度为 O(N) 2. 空间复杂度为 O(1)</p>
 * <p>细节: 此前递归和非递归的遍历的空间复杂度都是 O(1), 采用这种方式实现的遍历可以达到空间复杂度为 O(1)</p>
 */
public class Morris
{
    public static void main(String[] args)
    {
        TreeNode root = getInstance();
        morris(root);
        System.out.println();
        preMorris(root);
        System.out.println();
        infixMorris(root);
        System.out.println();
        postMorris(root);
    }

    /**
     * 产生 Morris 序
     * @param root 树的根结点
     */
    private static void morris(TreeNode root){
        TreeNode current = root;
        TreeNode mostRight = null;
        while (current != null){
            System.out.print(current.value + "\t");
            // 1. 如果左子树存在
            if (current.left != null){
                // 3. 找到左子树的最右结点
                mostRight = current.left;
                // 3.1 第一个条件是用于终止第一次到达可到达两次的结点
                // 3.2 第二个条件是用于终止第二次到达可到达两次的结点
                while (mostRight.right != null && mostRight.right != current)
                    mostRight = mostRight.right;
                // 4. 如果左子树的最右结点的右指针指向空, 那么就让其指向当前结点, 向左移动, 重复上述行为
                if (mostRight.right == null) {
                    mostRight.right = current;
                    current = current.left;
                    continue;
                }
                // 5. 如果左子树的最右结点的右指针指向当前结点. 那么就让其指向空, 然后向右移动
                else{
                    mostRight.right = null;
                }
            }

            // 2. 如果左子树不存在, 就直接向右移动
            current = current.right;

        }

    }

    /**
     * Morris 序生成前序遍历
     * @param root 树的根结点
     */
    private static void preMorris(TreeNode root){
        TreeNode current = root;
        TreeNode mostRight = null;
        while(current != null){
            if (current.left != null){
                mostRight = current.left;
                while(mostRight.right != null && mostRight.right != current)
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
     * Morris 序生成中序遍历
     * @param root 树的根结点
     */
    private static void infixMorris(TreeNode root){
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
                    System.out.print(current.value + "\t");
                    mostRight.right = null;
                }
            }else{
                System.out.print(current.value + "\t");
            }
            current = current.right;
        }

    }

    /**
     * Morris 序生成后序遍历
     * @param root 树的根结点
     */
    private static void postMorris(TreeNode root){
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

    private static void printTree(TreeNode root){
        TreeNode tail = reverseTree(root);
        TreeNode current = tail;
        while (current != null){
            System.out.print(current.value + "\t");
            current = current.right;
        }
        assert tail != null;
        reverseTree(tail);
    }

    private static TreeNode reverseTree(TreeNode root){
        TreeNode previous = null;
        TreeNode current = root;
        TreeNode next = root.right;
        while (current != null){
            current.right = previous;
            previous = current;
            current = next;
            next = next != null ? next.right : null;
        }

        return previous;
    }

    private static TreeNode getInstance(){
        TreeNode root = new TreeNode(4);
        TreeNode node1 = new TreeNode(2);
        TreeNode node2 = new TreeNode(6);
        TreeNode node3 = new TreeNode(1);
        TreeNode node4 = new TreeNode(3);
        TreeNode node5 = new TreeNode(5);
        TreeNode node6 = new TreeNode(7);

        root.left = node1;
        node1.left = node3;
        node1.right = node4;

        root.right = node2;
        node2.left = node5;
        node2.right = node6;

        return root;
    }
}
