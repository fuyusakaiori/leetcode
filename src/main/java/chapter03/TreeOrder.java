package chapter03;

import utils.ListNode;
import utils.TreeNode;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/** 遍历二叉树
 *  1. 递归遍历: 非常简单
 *  2. 非递归遍历: 相对麻烦, 需要借助栈结构手动压栈
 */
public class TreeOrder
{
    public static void main(String[] args)
    {
        TreeNode root = treeInstance();
        preOrder(root);
        System.out.println();
        preOrderNoRecursive(root);
        System.out.println();
        infixOrder(root);
        System.out.println();
        infixOrderNoRecursive(root);
        System.out.println();
        postOrder(root);
        System.out.println();
        postOrderNoRecursive(root);
        System.out.println();
        widthOrder(root);
    }

    // 深度遍历
    //=============================递归遍历==============================
    // 先序遍历: 父 -> 左 -> 右
    private static void preOrder(TreeNode root){
        if (root == null)
            return;
        System.out.print("结点: " + root.value + "\t");
        preOrder(root.left);
        preOrder(root.right);
    }
    // 中序遍历: 左 -> 父 -> 右
    private static void infixOrder(TreeNode root){
        if (root == null)
            return;
        infixOrder(root.left);
        System.out.print("结点: " + root.value + "\t");
        infixOrder(root.right);
    }
    // 后序遍历: 左 -> 右 -> 父
    private static void postOrder(TreeNode root){
        if (root == null)
            return;
        postOrder(root.left);
        postOrder(root.right);
        System.out.print("结点: " + root.value + "\t");
    }
    //=============================非递归遍历=============================
    // 前序遍历: 实现比较简单
    private static void preOrderNoRecursive(TreeNode root){
        if (root == null)
            return;
        // 1. 栈
        LinkedList<TreeNode> stack = new LinkedList<>();
        // 2. 开始遍历压栈
        stack.push(root);
        while (!stack.isEmpty()){
            TreeNode node = stack.pop();
            System.out.print("结点: " + node.value + "\t");
            if (node.right != null)
                stack.push(node.right);
            if (node.left != null)
                stack.push(node.left);
        }
    }

    // 中序遍历: 和前序遍历存在很大的不同
    // 核心: 中序遍历没有对右子树的操作, 右子树实际也变成了判断左子树和父结点的过程, 所以你会发现这个过程是不对称的
    private static void infixOrderNoRecursive(TreeNode root){
        // 1. 准备栈空间
        LinkedList<TreeNode> stack = new LinkedList<>();
        // 2. 判断条件: ① 栈为空的时候就证明所有结点已经被遍历结束 ② 此时根结点变量也会被更新为空 => 结束遍历
        while (!stack.isEmpty() || root != null){
            // 3. 只要自己不为空, 就把自己压入栈
            if (root != null){
                stack.push(root);
                // 4. 然后更新自己为左子结点
                root = root.left;
                // 5. 重复上述的循环 => 直到左子结点为空的时候
            }else{
                // 6. 左子结点为空的时候, 就会弹出左子结点的父结点
                root = stack.pop();
                System.out.print("结点: " + root.value + "\t");
                // 7. 打印左子结点之后压入右子结点
                root = root.right;
                // 8. 如果右子结点也不存在就会弹出祖父结点
            }
        }

    }

    // 后序遍历: 需要借助两个栈
    // 核心思路：先将元素按照头->左->右的顺序压入第一个栈, 然后会按照头->右->左的顺序进入第二个栈, 最后倒出第二个栈就是左->右->头
    // 注意: 单个栈不太好写, 但是应该是可行的, 就是代码肯定比较丑
    private static void postOrderNoRecursive(TreeNode root){
        // 1. 所有结点第一次进入的栈空间
        LinkedList<TreeNode> stack = new LinkedList<>();
        // 2. 父结点进入的栈
        LinkedList<TreeNode> collection = new LinkedList<>();
        // 3. 先将根结点压入第一个栈中
        stack.push(root);
        while (!stack.isEmpty()){
            // 4. 将第一个栈中的元素出栈
            root = stack.pop();
            // 5. 然后将这个元素压入收集栈
            collection.push(root);
            // 6. 然后先压入这个元素的左节点
            if (root.left != null)
                stack.push(root.left);
            // 7. 再压入这个元素的右结点
            if (root.right != null)
                stack.push(root.right);
        }
        // 8. 最后依次弹出第二个栈的元素
        while (!collection.isEmpty()){
            System.out.print("结点: " + collection.pop().value + "\t");
        }
    }

    // 宽度遍历（层次遍历）
    // 核心思路: 借助队列
    private static void widthOrder(TreeNode root){
        if (root == null)
            return;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()){
            root = queue.poll();
            System.out.print("结点: " + root.value + "\t");
            if (root.left != null)
                queue.offer(root.left);
            if (root.right != null)
                queue.offer(root.right);
        }
    }


    //================================构建树===================================
    private static TreeNode treeInstance(){
        int[] values = new int[10];
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            values[i] = random.nextInt(30);
        }
        System.out.println(Arrays.toString(values));
        TreeNode root = new TreeNode(values[0]);
        root.left = new TreeNode(values[1]);
        root.right = new TreeNode(values[2]);
        root.left.left = new TreeNode(values[3]);
        root.left.right = new TreeNode(values[4]);
        root.left.left.left = new TreeNode(values[5]);
        root.left.left.right = new TreeNode(values[6]);
        root.right.left = new TreeNode(values[7]);
        root.left.left.right.left = new TreeNode(values[8]);
        root.left.left.right.right = new TreeNode(values[9]);

        return root;
    }
}
