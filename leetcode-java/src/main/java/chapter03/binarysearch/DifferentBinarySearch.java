package chapter03.binarysearch;

import chapter03.TreeNode;

import java.util.LinkedList;
import java.util.List;

/**
 * <h3>不同的二叉搜索树</h3>
 * <h3>1. 不同的二叉搜索树</h3>
 * <h3>2. 不同的二叉搜索树 II</h3>
 */
public class DifferentBinarySearch {

    /**
     * <h3>思路: 不同的二叉搜索树</h3>
     * <h3>1. 这个题其实就是很常规的排列组合问题</h3>
     * <h3>2. 每次都将 n 值分别作为根结点, 然后左边的数字就是左子树的结点, 右边的数字就是右子树的结点</h3>
     * <h3>3. 此时左子树的结点数一定小于 n, 小于 n 的二叉搜索树的数量此前已经算过, 所以可以直接得到</h3>
     * <h3>4. 右子树的种数也是同理的</h3>
     * <h3>5. 然后以当前值为根结点的二叉搜索树的种数就是 左子树的数量 * 右子树的数量</h3>
     * <h3>注: 不要搞成加法了...</h3>
     */
    private static int numTrees(int number){
        // 循环就可以实现
        int[] trees = new int[number + 1];
        // 初始化
        trees[0] = 1;
        trees[1] = 1;
        for (int index = 2;index <= number;index++){
            int result = 0;
            for (int left = 0,  right = number - 1;left <= number - 1 && right >= 0;left++, right--){
                result += trees[left] + trees[right];
            }
            trees[index] = result;
        }
        return trees[number];
    }

    /**
     * <h3>思路: 不同的二叉搜索树</h3>
     * <h3>1. 依然需要选择 1~n 中的每个数字作为作为根结点</h3>
     * <h3>2. 但是这个时候需要得到的是左边和右边的具体有哪些树, 不只是树的种类</h3>
     * <h3>3. 所以这个题就可以翻译成构建树的题目, 只不过需要构建出所有可能的组合</h3>
     * <h3>4. 所以最基本的想法就是要递归, 并且设置边界</h3>
     * <h3>5. 不过区别于构建一棵树, 仅返回一个结点就好, 这里需要构建很多棵树, 所以需要返回树的集合</h3>
     * <h3>注: 这个题比刚才的要麻烦, 但是其实思路也是很简单的</h3>
     */
    private static List<TreeNode> generateTrees(int number){
        return dfs(1, number);
    }

    private static List<TreeNode> dfs(int left, int right){
        List<TreeNode> trees = new LinkedList<>();
        if(left > right){
            // 防止之后集合为空, 增强循环抛出异常, 这里需要将空值加入集合
            trees.add(null);
            return trees;
        }
        for (int index = left;index <= right;index++){
            List<TreeNode> leftTrees = dfs(left, index - 1);
            List<TreeNode> rightTrees = dfs(index + 1, right);
            // 开始组合
            for (TreeNode leftTree : leftTrees) {
                for (TreeNode rightTree : rightTrees) {
                    TreeNode root = new TreeNode(index);
                    root.left = leftTree;
                    root.right = rightTree;
                    trees.add(root);
                }
            }
        }
        return trees;
    }

}
