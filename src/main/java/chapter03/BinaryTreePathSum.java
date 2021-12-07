package chapter03;

import utils.TreeNode;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * <h2>二叉树的路径和问题</h2>
 * <p>1. 给定目标值, 判断是否存在一条路径的路径和等于目标值</p>
 * <p>2. 给定目标值, <strong>找到所有路径和等于目标值的路径</strong>, 所有路径都从根结点开始</p>
 * <p>3. 给定目标值, <strong>找到所有路径和等于目标值的路径数量</strong>, 但是可以不从根结点开始</p>
 * <p>4. 求取二叉树中最大的路径和</p>
 */
public class BinaryTreePathSum
{
    public static void main(String[] args)
    {
        Class<BinaryTreePathSum> binaryTreePathSumClass = BinaryTreePathSum.class;

    }

    /**
     * <p>仅判断树中是否存在路径和等于目标值的路径</p>
     * <p>两种做法</p>
     * <p>1. DFS</p>
     * <p>2. BFS</p>
     * @param root 根结点
     * @param target 目标值
     * @return 是否存在相应的路径
     */
    private static boolean hasPathSumDFS(TreeNode root, int target){
        if (root == null)
            return false;
        // 必须到达叶子结点才可以算作一条路径
        if (target == root.value && root.left == null && root.right == null)
            return true;
        // 只要左子树或者右子树中存在一条就可以
        return hasPathSumDFS(root.left, target - root.value) ||
                       hasPathSumDFS(root.right, target - root.value);
    }

    /**
     * <p>虽然层序遍历的做法相对较慢, 但是我觉得这种思路可以在以后的题目中被借鉴</p>
     * @param root 根结点
     * @param target 目标值
     * @return 是否存在相应的路径
     */
    private static boolean hasPathSumBFS(TreeNode root, int target){
        if (root == null)
            return false;
        // 双队列解决: 第一个队列遍历树, 第二个队列存储路径和
        Queue<TreeNode> queue = new LinkedList<>();
        Queue<Integer> pathSum = new LinkedList<>();
        // 记录路径和的临时变量
        int path = 0;
        queue.offer(root);
        pathSum.offer(root.value);
        while (!queue.isEmpty()){
            root = queue.poll();
            path = pathSum.poll();
            if (path == target && root.left == null && root.right == null)
                return true;
            if (root.left != null){
                queue.offer(root.left);
                pathSum.offer(path + root.left.value);
            }
            if (root.right != null){
                queue.offer(root.right);
                pathSum.offer(path + root.right.value);
            }
        }
        return false;
    }


    /**
     * <p>这个题目就要求找出所有符合条件的路径了, 相对要难一些</p>
     * <p>只采用深度优先遍历实现, 层次遍历实在是有点慢</p>
     * <p>主要问题, 在于递归的时候如何记录路径</p>
     * @return 所有符合要求的路径
     */
    private static List<List<Integer>> findPathSumFromRoot(TreeNode root, int target){
        List<Integer> path = new LinkedList<>();
        List<List<Integer>> paths = new LinkedList<>();
        recursive(root, target, path, paths);
        return paths;
    }

    private static void recursive(TreeNode root, int target, List<Integer> path, List<List<Integer>> paths){
        // 防止其中一个孩子是空的
        if (root == null)
            return;
        path.add(root.value);
        // 到达叶子结点后就开始判断, 路径是否符合要求
        if (root.left == null && root.right == null){
            if (target == root.value)
                // 这里必须新建集合后, 再添加进入路径集合, 否则之后的修改会反映到路径集合汇总
                paths.add(new LinkedList<>(path));
            // 因为集合是引用类型, 递归中对集合内容的修改, 是不会因为回溯而消失的, 所以需要手动移除
            path.remove(path.size() - 1);
            return;
        }
        recursive(root.left, target - root.value, path, paths);
        recursive(root.right, target - root.value, path, paths);
        // 回溯之前需要将当前结点移除
        path.remove(path.size() - 1);
    }

    /**
     * <p>这个题目更进一步, 要求路径可以不从根结点开始, 也可以不到叶子结点结束, 只要符合要求的路径就可以</p>
     * <p>这个题有两种做法, 实际都不太好想</p>
     * <p>1. 采用双重递归的方式</p>
     * <p>2. 采用前缀和的方式（还没看）</p>
     * <p>双重递归核心思路</p>
     * <p>1. 每个结点都可能成为路径的起始结点, 这就意味着我们需要对每个结点都进行递归, 求路径和</p>
     * <p>2. 求路径和需要进行递归, 以每个结点为起始结点也需要递归, 所以构成了双重递归</p>
     * @return 符合要求的路径数量
     */
    private static int findPathSum(TreeNode root, int target){
        if (root == null)
            return 0;

        return pathSum(root, target)
                       + findPathSum(root.left, target)
                       + findPathSum(root.right, target);
    }

    private static int pathSum(TreeNode root, int target){
        if (root == null)
            return 0;

        int res = 0;
        if (target == root.value && root.left == null && root.right == null)
            res += 1;

        return res + pathSum(root.left, target - root.value)
                       + pathSum(root.right, target - root.value);
    }
}
