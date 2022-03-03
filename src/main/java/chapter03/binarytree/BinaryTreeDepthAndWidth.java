package chapter03.binarytree;

import utils.TreeNode;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * <h2>二叉树深度与宽度/h2>
 * <h3>1. 二叉树的最大深度</h3>
 * <h3>2. 二叉树的最小深度</h3>
 * <h3>3. 二叉树的最大宽度</h3>
 * <h3>4. 二叉树的最大宽度 II</h3>
 */
public class BinaryTreeDepthAndWidth
{
    public static void main(String[] args)
    {

    }

    /**
     * <h3>思路: </h3>
     * <h3>1. 深度遍历: 写起来容易</h3>
     * <h3>2. 层序遍历: 计算层数, 写起来麻烦</h3>
     */
    private static int maxDepth(TreeNode root){
        if (root == null) return 0;
        return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
    }

    /**
     * <h3>思路: </h3>
     * <h3>1. 深度遍历: 返回条件只有一个, 所以不需要封装</h3>
     * <h3>2: 宽度遍历: 将深度封装在结点中</h3>
     * <h3>2. Morris 遍历是不行的, 之前为什么没有批注?</h3>
     */
    private static int minDepthDFS(TreeNode root){
        if (root == null)
            return 0;
        int left = minDepthDFS(root.left);
        int right = minDepthDFS(root.right);
        if (left == 0 || right == 0)
            return left == 0 ? right + 1: left + 1;
        return Math.min(left, right) + 1;
    }

    private static int minDepthBFS(TreeNode root){
        class Node{
            private int depth;
            private TreeNode node;
            public Node(int depth, TreeNode node) {
                this.depth = depth;
                this.node = node;
            }
        }
        Queue<Node> queue = new LinkedList<>();
        if (root != null)
            queue.offer(new Node(1, root));
        while (!queue.isEmpty()){
            Node wrapper = queue.poll();
            TreeNode node = wrapper.node;
            // 如果遇见第一个叶子结点直接返回, 此时就是最小深度
            if (node.left == null && node.right == null)
                return wrapper.depth;
            // 如果不是叶子结点, 就需要接着层序遍历
            if (node.left != null)
                queue.offer(new Node(wrapper.depth + 1, node.left));
            if (node.right != null)
                queue.offer(new Node(wrapper.depth + 1, node.right));
        }
        return 0;
    }

    /**
     * <h3>思路: 直接层序遍历即可, 因为不需要计算空结点</h3>
     */
    private static int maxWidth(TreeNode root){
        return 0;
    }

    /**
     * <h3>思路: </h3>
     * <h3>1. 将结点、深度、索引封装成结点</h3>
     * <h3>2. 每层的起始结点和终止结点的索引相减就是每层的结点数, 包含空结点</h3>
     * <h3>注: 可以使用深度遍历或者宽度遍历, 都是基于编号的想法</h3>
     */
    private static int maxWidthNullBFS(TreeNode root){
        class Wrapper{
            // 用于计算索引
            private int depth;
            // 用于计算宽度
            private int index;
            // 包含的结点, 可以为空
            private TreeNode node;
            public Wrapper(int depth, int index, TreeNode node) {
                this.depth = depth;
                this.index = index;
                this.node = node;
            }
        }
        int maxWidth = 0;
        Wrapper start = new Wrapper(1, 0, root);
        Queue<Wrapper> queue = new LinkedList<>();
        if (root != null)
            queue.offer(new Wrapper(1, 0, root));
        while (!queue.isEmpty()){
            Wrapper wrapper = queue.poll();
            if (wrapper.node != null)
            {
                queue.offer(new Wrapper(wrapper.depth + 1,
                        2 * wrapper.index + 1, wrapper.node.left));
                queue.offer(new Wrapper(wrapper.depth + 1,
                        2 * wrapper.index + 2, wrapper.node.right));
                if (start.depth != wrapper.depth){
                    start = wrapper;
                }
                maxWidth = Math.max(maxWidth, wrapper.index - start.index + 1);
            }
        }

        return maxWidth;
    }

    /**
     * <h3>记录每层的起始结点的编号: depth - index</h3>
     */
    private static final Map<Integer, Integer> map = new HashMap<>();
    private static int maxWidth = 0;
    private static int maxWidthNullDFS(TreeNode root){
        dfs(root, 1, 0);
        return maxWidth;
    }

    private static void dfs(TreeNode root, int depth, int index){
        if (root == null) return;
        if (!map.containsKey(depth))
            map.put(depth, index);
        maxWidth = Math.max(maxWidth, index - map.get(depth) + 1);
        dfs(root.left, depth + 1, index * 2 + 1);
        dfs(root.right, depth + 1, index * 2 + 2);
    }


}
