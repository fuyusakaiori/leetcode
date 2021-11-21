package chapter03.travel;

import utils.TreeNode;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * <p>宽度优先遍历及其衍生问题</p>
 * <p>1. 最基本的宽度遍历</p>
 * <p>2. 自底向上的宽度遍历</p>
 * <p>3. 锯齿形遍历</p>
 */
public class BinaryTreeBreathFirstSearch
{
    public static void main(String[] args)
    {

    }

    /**
     * 宽度遍历及其所有衍生问题都是基于这个最基本的方法
     */
    private static void levelorder(TreeNode root){
        if (root == null)
            return;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()){
            root = queue.poll();
            // TODO 所有处理出队的结点的行为都在这里添加
            if (root.left != null)
                queue.offer(root.left);
            if (root.right != null)
                queue.offer(root.right);
        }
    }

    /**
     * <p>锯齿形遍历的意思就是, 先从左向右处理, 下一层从右向左处理</p>
     * <p>两种方式: 1. 采用栈实现 2. 采用双端队列实现</p>
     * <p>个人认为, 采用双端队列的逻辑稍微复杂点, 代码长点, 但是更加灵活</p>
     */
    private static void zigZagLevelOrderDeque(TreeNode root){
        if (root == null)return;
        Deque<TreeNode> deque = new LinkedList<>();
        // 标志是从左向右, 还是从右向左
        boolean flag = true;
        TreeNode currentEnd = root;
        TreeNode nextEnd = null;
        deque.offer(root);
        while (!deque.isEmpty()){
            // 1. 结点从队列尾部进入, 从头部出去
            if (flag){
                root = deque.pollFirst();
                if (root.left != null){
                    deque.offerLast(root.left);
                    nextEnd = nextEnd == null ? root.left: nextEnd;
                }
                if (root.right != null){
                    deque.offerLast(root.right);
                    nextEnd = nextEnd == null ? root.right : nextEnd;
                }
            }
            // 2. 结点从队列头部进入, 从尾部出去
            else{
                root = deque.pollLast();
                // 注: 这里需要先将右结点入队, 才能够确保是完全相反的, 否则只是部分相反
                if (root.right != null){
                    nextEnd = nextEnd == null ? root.right : nextEnd;
                    deque.offerFirst(root.right);
                }
                if (root.left != null){
                    nextEnd = nextEnd == null ? root.left: nextEnd;
                    deque.offerFirst(root.left);
                }
            }
            // 3. 如何知道换层?
            // 只要下层末尾被记录了, 就不要继续更新了, 因为你的头结点就是下次的尾结点
            if (currentEnd == root && !deque.isEmpty()){
                currentEnd = nextEnd;
                flag = !flag;
            }
        }
    }

    /**
     * 使用栈的话实际上是因为 LeetCode 题返回的是链表集合, 所以可以在不同的层调用不同方法
     */
    private static List<List<Integer>> zigXagLevelOrderStack(TreeNode root){
        boolean flag = true;
        TreeNode currentEnd = root;
        TreeNode nextEnd = root;
        List<List<Integer>> level = new LinkedList<>();
        LinkedList<Integer> nodes = new LinkedList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        if(root != null)
            queue.offer(root);
        while(!queue.isEmpty()){
            root = queue.poll();
            // 当前层采用正常添加元素的方式
            if(flag)
                nodes.add(root.value);
            // 下一层采用栈添加元素的方式, 这样顺序自然而然就是逆序的
            else
                nodes.push(root.value);
            if(root.left != null){
                queue.add(root.left);
                nextEnd = root.left;
            }
            if(root.right != null){
                queue.add(root.right);
                nextEnd = root.right;
            }
            if(root == currentEnd){
                level.add(nodes);
                nodes = new LinkedList<>();
                currentEnd = nextEnd;
                flag = !flag;
            }

        }
        return level;
    }

}
