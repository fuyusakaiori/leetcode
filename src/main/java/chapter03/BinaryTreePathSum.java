package chapter03;

import utils.TreeNode;

import java.util.*;

/**
 * <h2>二叉树路径和问题</h2>
 * <h3>1. 路径总和 </h3>
 * <h3>2. 路径总和 II</h3>
 * <h3>3. 路径总和 III</h3>
 * <h3>4. 二叉树中最大路径和</h3>
 */
public class BinaryTreePathSum
{
    public static void main(String[] args)
    {
        Class<BinaryTreePathSum> binaryTreePathSumClass = BinaryTreePathSum.class;

    }

    /**
     * <h3>思路: </h3>
     * <h3>1. 深度遍历: 目标值不断减去结点值</h3>
     * <h3>1.1: 如果在非叶子结点变为 0, 那么到达叶子结点就会为负数, 返回 false</h3>
     * <h3>1.2: 如果到达叶子结点恰好为 0, 那么这条路径就是有效的</h3>
     * <p>2. 宽度遍历: 借助两个队列完成</p>
     * @param root 根结点
     * @param target 目标值
     * @return 是否存在相应的路径
     */
    private static boolean hasPathSumDFS(TreeNode root, int target){
        if (root == null) return false;
        // 必须到达叶子结点才可以算作一条路径
        if (root.left == null && root.right == null)
            return root.value == target;
        // 只要左子树或者右子树中存在一条就可以
        return hasPathSumDFS(root.left, target - root.value) ||
                       hasPathSumDFS(root.right, target - root.value);
    }

    /**
     * <h3>思路: 队列记录路径和</h3>
     * @param root 根结点
     * @return 是否存在相应的路径
     */
    private static boolean hasPathSumBFS(TreeNode root, int targetSum){
        Queue<TreeNode> queue = new LinkedList<>();
        Queue<Integer> paths = new LinkedList<>();

        if(root != null){
            queue.offer(root);
            paths.offer(root.value);
        }
        int path = 0;
        while(!queue.isEmpty()){
            root = queue.poll();
            path = paths.poll();
            if(root.left == null && root.right == null && path == targetSum)
                return true;
            if(root.left != null){
                queue.offer(root.left);
                paths.offer(path + root.left.value);
            }
            if(root.right != null){
                queue.offer(root.right);
                paths.offer(path + root.right.value);
            }
        }
        return false;
    }


    /**
     * <h3>思路: 深度遍历</h3>
     * <h3>1. 深度遍历直到叶子结点, 每经过一个结点都将其添加到路径中</h3>
     * <h3>2. 到达叶子结点之后, 就判断当前目标和是否等于结点的值, 如果相等就添加到集合中去</h3>
     * @return 所有符合要求的路径
     */
    private static List<List<Integer>> findPathSumFromRoot(TreeNode root, int target){
        List<Integer> path = new LinkedList<>();
        List<List<Integer>> paths = new LinkedList<>();
        dfs(root, target, path, paths);
        return paths;
    }

    private static void dfs(TreeNode root, int target, List<Integer> path, List<List<Integer>> paths){
        // 防止其中一个孩子是空的
        if (root == null) return;
        path.add(root.value);
        // 到达叶子结点后就开始判断, 路径是否符合要求
        if (root.left == null && root.right == null && target == root.value){
            // 防止分支污染
            paths.add(new LinkedList<>(path));
        }
        dfs(root.left, target - root.value, path, paths);
        dfs(root.right, target - root.value, path, paths);
        // 回溯之前需要将当前结点移除
        path.remove(path.size() - 1);
    }

    /**
     * <h3>思路: 深度遍历</h3>
     * <h3>1. 依然借助路径总和问题的想法, 采用双队列来分别记录遍历的情况和路径和的情况</h3>
     * <h3>2. 但是问题在于如何在遍历到叶子结点的时候将相应的路径添加进去</h3>
     * <h3>3. 因为遍历的时候是添加一层的结点进去, 并且没有办法在此时得知该路径是否有效, 所以只用链表是不够的</h3>
     * <h3>4. 原先的想法就是使用哈希表保存路径和路径和的映射关系, 但是可以使用结点和结点的映射关系</h3>
     * <h3>5. 前者从逻辑上来说比较直观, 但是效率很差, 因为需要频繁创建链表对象</h3>
     * <h3>6. 后者虽然在计算和的时候需要遍历, 但是不需要创建大量的对象</h3>
     */
    private static List<List<Integer>> findPathSumFromRootBFS(TreeNode root, int target){
        Map<TreeNode, TreeNode> map = new HashMap<>();
        List<List<Integer>> paths = new LinkedList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        Queue<Integer> pathSum = new LinkedList<>();
        if (root != null){
            queue.offer(root);
            pathSum.offer(root.value);
        }
        int value = 0;
        while (!queue.isEmpty()){
            root = queue.poll();
            assert !pathSum.isEmpty();
            value = pathSum.poll();
            if (root.left == null && root.right == null && value == target){
                paths.add(getPathSum(root, map));
            }
            if (root.left != null){
                queue.offer(root.left);
                pathSum.offer(root.left.value + value);
                map.put(root.left, root);
            }
            if (root.right != null){
                queue.offer(root.right);
                pathSum.offer(root.right.value + value);
                map.put(root.right, root);
            }
        }

        return paths;
    }

    /**
     * <h3>从叶子结点开始向上遍历到根结点, 算出路径</h3>
     * @return 路径和
     */
    private static List<Integer> getPathSum(TreeNode node, Map<TreeNode, TreeNode> map){
        LinkedList<Integer> path = new LinkedList<>();
        while (node != null){
            path.push(node.value);
            // 获取该结点的父结点, 不断向上移动
            node = map.get(node);
        }
        return path;
    }

    /**
     * <h3>思路: </h3>
     * <h3>1. 深度遍历: 双重递归</h3>
     * <h3>2. 前缀和: 不是字典树</h3>
     * <h3>双重递归: </h3>
     * <h3>1. 每个结点都可以作为起始结点, 每个结点也可以作为结束结点</h3>
     * <h3>2. 所以需要将所有的结点都作为起始结点来进行深度遍历</h3>
     * <h3>3. 需要某个负责深度遍历函数, 用来判断是否符合要求</h3>
     * <h3>4. 还需要某个负责遍历所有结点, 并将其作为起始结点的函数</h3>
     * @return 符合要求的路径数量
     */
    private static int findPathSumDFS(TreeNode root, int target){
        if (root == null)
            return 0;
        // 注: 这个函数里面不进行条件判断
        // dfs 代表从当前结点出发向下遍历, 只要符合条件就会将结果 +1
        // findPathSum 就是将左右孩子结点分别作为起始结点去深度遍历
        return dfs(root, target) + findPathSumDFS(root.left, target) + findPathSumDFS(root.right, target);
    }

    private static int dfs(TreeNode root, int target){
        if (root == null) return 0;

        int paths = 0;
        // 注: 现在结束的位置不一定就是叶子结点, 所以不能在满足条件之后立刻返回, 需要继续判断
        // 注: 不需要判断叶子结点了, 自然也就不需要相应的条件判断了
        if (target == root.value) paths++;

        return paths + dfs(root.left, target - root.value)
                       + dfs(root.right, target - root.value);
    }

    /**
     * <h3>思路: 前缀树的思路主要是这样的</h3>
     * <h3>1. 路径都是自顶向下的: root -> node-i -> node-i+1 -> ... -> node-j</h3>
     * <h3>2. 采用哈希表保存根结点到前面所有结点的路径和, root->node-i、root->node-i+1...的路径和都保存</h3>
     * <h3>3. 现在记 root-> node-j 的路径和为 current, 如果前面存在 node-i+1->node-j 的路径和满足条件</h3>
     * <h3>4. 那么必然存在 root->node-i 的路径和为 current - target</h3>
     * <h3>5. 所以每次只需要验证哈希表存放的前缀和中是否存在相应的 current - target 就可以</h3>
     * <h3>6. 如果哈希表中保存的前缀和中存在 current- target, 那么就证明存在一个或者多个结点到当前结点满足条件</h3>
     * <h3>7. 如果没有相应的前缀和, 那么就证明前面没有任何结点到当前结点是满足条件的</h3>
     */
    private static int findPathSumPrefix(TreeNode root, int target){
        Map<Integer, Integer> prefix = new HashMap<>();
        if (root == null) return 0;
        prefix.put(0, 1);
        return prefixDFS(root, 0, target, prefix);
    }

    private static int prefixDFS(TreeNode root, int current, int target, Map<Integer, Integer> prefix){
        if (root == null) return 0;
        // 1. 更新路径和
        current += root.value;
        // 2. 检查前缀和中是否有满足 current - target 的键值对
        int paths = 0;
        paths += prefix.getOrDefault(current - target, 0);
        // 3. 无论是否存在满足条件的前缀和, 都需要将当前的路径和存放到哈希表中
        // 注: 可能当前的路径和 和 此前的前缀和是相等的, 所以需要叠加, 到时候满足条件就意味着可能存在多条符合条件的路径
        prefix.put(current, prefix.getOrDefault(current, 0) + 1);
        // 4. 继续向左和向右深度遍历
        paths += prefixDFS(root.left, current, target, prefix);
        paths += prefixDFS(root.right, current, target, prefix);
        // 5. 记得回溯前将当前的路径和从前缀和中移除
        prefix.put(current, prefix.get(current) - 1);

        return paths;
    }
}
