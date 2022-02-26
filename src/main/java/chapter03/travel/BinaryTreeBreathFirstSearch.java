package chapter03.travel;

import utils.TreeNode;

import java.util.*;

/**
 * <h2>二叉树宽度遍历</h2>
 * <h3>1. 二叉树层序遍历</h3>
 * <h3>2. 二叉树层序遍历 II</h3>
 * <h3>3. 锯齿形遍历</h3>
 */
public class BinaryTreeBreathFirstSearch
{
    public static void main(String[] args)
    {

    }

    /**
     * <h3>思路: 队列</h3>
     * <h3>注: 层序遍历所有衍生问题都是基于队列的思路</h3>
     * <h3>注: 力扣上的题目带有返回值, 也就是我们需要区分每一层 => 记录当前层尾结点和下层尾结点即可</h3>
     */
    private static List<List<Integer>> levelorderTop(TreeNode root){
        TreeNode currentLevelEnd = root;
        TreeNode nextLevelEnd = null;
        Queue<TreeNode> queue = new LinkedList<>();
        List<Integer> level = new LinkedList<>();
        List<List<Integer>> levels = new LinkedList<>();

        if (root != null) queue.offer(root);
        while (!queue.isEmpty()){
            root = queue.poll();
            level.add(root.value);
            if (root.left != null){
                queue.offer(root.left);
                nextLevelEnd = root.left;
            }
            if (root.right != null){
                queue.offer(root.right);
                nextLevelEnd = root.right;
            }
            if (currentLevelEnd == root){
                levels.add(new LinkedList<>(level));
                level.clear();
                currentLevelEnd = nextLevelEnd;
            }
        }
        return levels;
    }

    /**
     * <h3>思路: 自底向上遍历</h3>
     * <h3>1. 可以使用栈, 但是比较繁琐</h3>
     * <h3>2. 每次指定在链表首位添加就可以</h3>
     */
    private static List<List<Integer>> levelorderDown(TreeNode root){
        TreeNode currenLevelEnd = root;
        TreeNode nextLevelEnd = null;
        List<Integer> level = new LinkedList<>();
        List<List<Integer>> levels = new LinkedList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        if(root != null)
            queue.offer(root);
        while(!queue.isEmpty()){
            root = queue.poll();
            level.add(root.value);
            if(root.left != null){
                queue.offer(root.left);
                nextLevelEnd = root.left;
            }
            if(root.right != null){
                queue.offer(root.right);
                nextLevelEnd = root.right;
            }
            if(currenLevelEnd == root){
                levels.add(0, new LinkedList<>(level));
                level.clear();
                currenLevelEnd = nextLevelEnd;
            }
        }
        return levels;
    }

    /**
     * <h3>思路: </h3>
     * <h3>1. 栈实现</h3>
     * <h3>2. 双端队列实现: 指的是每层双端队列实现, 不是将整体换成双端队列</h3>
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
     * <h3>思路: </h3>
     * <h3>1. 奇数层是正序, 偶数层是反序 => 最直观的想法就是使用栈</h3>
     * <h3>2. 奇数层的时候当链表使用, 偶数层的时候当栈使用, 也就是在结点进入链表的时候区分</h3>
     * <h3>3. 这样就不用考虑下一层尾结点的更新问题</h3>
     */
    private static List<List<Integer>> zigXagLevelOrderStack(TreeNode root){
        boolean flag = true;
        TreeNode currentEnd = root;
        TreeNode nextEnd = root;
        List<List<Integer>> levels = new LinkedList<>();
        LinkedList<Integer> level = new LinkedList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        if(root != null)
            queue.offer(root);
        while(!queue.isEmpty()){
            root = queue.poll();
            // 当前层采用正常添加元素的方式
            if(flag) level.add(root.value);
            // 下一层采用栈添加元素的方式, 这样顺序自然而然就是逆序的
            else level.push(root.value);
            if(root.left != null){
                queue.add(root.left);
                nextEnd = root.left;
            }
            if(root.right != null){
                queue.add(root.right);
                nextEnd = root.right;
            }
            if(root == currentEnd){
                levels.add(level);
                level = new LinkedList<>();
                currentEnd = nextEnd;
                flag = !flag;
            }

        }
        return levels;
    }

}
