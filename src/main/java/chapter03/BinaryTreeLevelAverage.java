package chapter03;

import utils.TreeNode;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * <h2>求二叉树每层的平均值</h2>
 * <p>1. 最简单的做法: BFS 完成</p>
 * <p>2. 不好想的做法: DFS 完成</p>
 */
public class BinaryTreeLevelAverage
{
    public static void main(String[] args)
    {

    }

    /**
     * 层序遍历完成
     */
    public static List<Double> averageOfLevelsBFS(TreeNode root) {
        if(root == null)
            return null;
        // 记录每层的总和
        double result = 0;
        // 记录每层元素个数
        double numbers = 0;
        // 分层用的临时变量
        TreeNode currentEnd = root;
        TreeNode nextEnd = null;
        List<Double> averages = new LinkedList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while(!queue.isEmpty()){
            root = queue.poll();
            result += root.value;
            numbers++;
            if(root.left != null){
                queue.offer(root.left);
                nextEnd = root.left;
            }
            if(root.right != null){
                queue.offer(root.right);
                nextEnd = root.right;
            }
            if(root == currentEnd){
                averages.add(result / numbers);
                result = 0;
                numbers = 0;
                currentEnd = nextEnd;
            }
        }
        return averages;
    }

    /**
     * <p>深度遍历完成</p>
     * <p>递归或者说深度遍历, 存在一个问题, 只能够记住返回值, 也就是记住的内容有限</p>
     * <p>但是有时候, 需要我们记住多个值, 那么这时候通常考虑额外空间, 或者临时变量</p>
     * <p>但是如果递归还要采用额外空间, 那么效果就不太好</p>
     * <p>核心思路: 采用一个数组统计总和, 采用另一个数组统计频次, 哈希表不行</p>
     */
    public static List<Double> averageOfLevelsDFS(TreeNode root){
        List<Double> sums = new LinkedList<>();
        List<Integer> counts = new LinkedList<>();
        List<Double> averages = new LinkedList<>();
        dfs(root, 0, sums, counts);
        for (int i = 0; i < sums.size(); i++) {
            averages.add(sums.get(i) / counts.get(i));
        }

        return averages;
    }

    public static void dfs(TreeNode root, int level, List<Double> sums, List<Integer> counts){
        if (root == null)
            return;
        // 如果层次大于链表长度, 那么显然证明这是新的一层
        if (level > sums.size()){
            // 将这个新的结点放进去
            sums.add(1.0 * root.value);
            // 记录该层结点的个数
            counts.add(1);
        }else{
            sums.set(level, sums.get(level) + root.value);
            counts.set(level, counts.get(level) + 1);
        }
        dfs(root.left, level + 1, sums, counts);
        dfs(root.right, level + 1, sums, counts);

    }
}
