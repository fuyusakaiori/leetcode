package chapter03;

import utils.TreeNode;

import java.util.*;

/**
 * <h2>二叉树的宽度系列问题</h2>
 * <p>1. 求二叉树的最大宽度, 不包含空节点</p>
 * <p>1. 求二叉树的最大宽度, 包含空节点</p>
 */
public class MaxWidth
{
    public static void main(String[] args)
    {
        TreeNode root = instance();
        System.out.println(getMaxWidthMap(root));
        System.out.println(getMaxWidth(root));

        LinkedList<List<Integer>> stack = new LinkedList<>();
    }

    // 解法一:
    private static int getMaxWidthMap(TreeNode root){
        if (root == null)
            return 0;
        int maxNodes = Integer.MIN_VALUE;
        int currentNodes = 0;
        int currentLevel = 1;
        // 1. 辅助遍历树的队列
        Queue<TreeNode> queue = new LinkedList<>();
        // 2. 记录每个结点对应的层数
        Map<TreeNode, Integer> map = new HashMap<>();
        // 3. 默认根结点是第一层
        map.put(root, 1);
        queue.offer(root);
        while (!queue.isEmpty()){
            // 4. 弹出队列中的结点
            root = queue.poll();
            // 5. 判断当前的结点应该在层数是否和当前层数匹配
            if (map.get(root) == currentLevel){
                // 6. 如果层数匹配, 那么就会给当前结点 +1
                currentNodes++;
            }else{
                // 7. 如果不匹配, 那么说明当前结点已经是下一层的了
                // 7.1 需要比较之前记录的最大结点数和当前层的最大结点数
                maxNodes = Math.max(maxNodes, currentNodes);
                // 7.2 递增到下一层
                currentLevel++;
                // 7.3 结点数重置为 1, 不是 0
                currentNodes = 1;
            }
            if (root.left != null){
                queue.offer(root.left);
                // 8. 记录结点所在的层数
                map.put(root.left, currentLevel + 1);
            }
            if (root.right != null){
                queue.offer(root.right);
                map.put(root.right, currentLevel + 1);
            }
        }

        // 9. 最后还需要额外比较一次, 因为最后一层会因为队列为空无法和最大值比较
        return Math.max(maxNodes, currentNodes);
    }

    // 解法二: 比较麻烦, 不借助哈希表
    // 核心思路: 需要记住下层的尾结点
    private static int getMaxWidth(TreeNode root){
        int maxNodes = 0;
        int currentNodes = 1;
        TreeNode currentEnd = root;
        TreeNode nextEnd = null;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()){
            root = queue.poll();
            // 1. 先将结点入队
            if (root.left != null){
                queue.offer(root.left);
                // 2. 将当前入队的结点置为下层的尾结点
                nextEnd = root.left;
            }
            // 3. 重复
            if (root.right != null){
                queue.offer(root.right);
                nextEnd = root.right;
            }
            // 4. 判断当前出队的结点是否是当前层的尾结点
            if (root == currentEnd){
                // 5. 如果是尾结点, 比较最大结点数和当前层结点数
                maxNodes = Math.max(currentNodes + 1, maxNodes);
                currentNodes = 0;
                currentEnd = nextEnd;
            }else{
                // 6. 如果不是, 那么当前层结点数 +1
                currentNodes++;
            }
        }
        return maxNodes;
    }

    private static TreeNode instance(){
        int[] values = new int[12];
        Random random = new Random();
        for (int i = 0; i < 12; i++) {
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
        root.left.left.left.left = new TreeNode(values[10]);
        root.left.left.left.right = new TreeNode(values[11]);
        root.left.left.right.left = new TreeNode(values[8]);
        root.left.left.right.right = new TreeNode(values[9]);

        return root;
    }
}
