package chapter03;

import utils.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * <h2>填充每个结点的 Next 指针</h2>
 * <p>1. 层序遍历非常简单</p>
 * <p>2. 深度遍历需要使用已经建立好的 Next 指针</p>
 * <p>两个问题</p>
 * <p>1. 树为满二叉树的时候, 填充 Next 指针</p>
 * <p>2. 树为普通二叉树的时候, 填充 Next 指针</p>
 * <p>总结: 填充指针这种类型的题目, 通常都可以考虑在填充之后立刻使用这些指针</p>
 */
public class ConnectNext
{
    public static void main(String[] args)
    {

    }

    /**
     * 层序遍历: 时间复杂度为 O(N)，空间复杂度为 O(1)
     */
    private static TreeNode connectBFS(TreeNode root){
        if(root == null)
            return null;
        TreeNode previous = null;
        TreeNode current = root;
        TreeNode currentEnd = root;
        TreeNode nextEnd = null;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while(!queue.isEmpty()){
            current = queue.poll();
            if(current.left != null){
                queue.offer(current.left);
                nextEnd = current.left;
            }
            if(current.right != null){
                queue.offer(current.right);
                nextEnd = current.right;
            }
            if(previous != null)
                previous.next = current;
            previous = current;
            if(current == currentEnd){
                currentEnd = nextEnd;
                previous = null;
            }
        }

        return root;
    }


    // 这两个变量其实可以不用做成成员变量, 但是因为将部分代码抽取成了方法, 所以需要使用成员变量
    private static TreeNode nextStart;
    private static TreeNode tail;
    /**
     * <p><strong>不是深度优先遍历, 已经和树的常规遍历方式没有关系了</strong></p>
     */
    private static TreeNode connect(TreeNode root){
        TreeNode current = root;
        TreeNode head;
        while (current != null){
            nextStart = null;
            tail = null;
            head = current;
            while (head != null){
                if (head.left != null)
                    handler(head.left);
                if (head.right != null)
                    handler(head.right);
                head = head.next;
            }
            // 开始下一层的处理
            current = nextStart;
        }
        return root;
    }

    private static void handler(TreeNode node){
        // 如果此时下一个开始结点为空, 那么证明当前传入的结点就是下一个起始结点
        nextStart = nextStart == null ? node : nextStart;
        // 如果此时链表的尾部结点为空, 那么就证明还没有开始连接链表, 所以只需要把练笔尾部更新就行
        // 也就是把当前结点作为链表的头结点, 这里其实和链表的操作很像
        if (tail != null)
            tail.next = node;
        tail = node;
    }

    /**
     * 满二叉树其实有另外一个做法, 比较取巧
     */
    private static TreeNode connectFBT(TreeNode root){

        return null;
    }

}
