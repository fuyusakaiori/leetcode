package chapter03.binarysearch;

import utils.TreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * <h2>二叉搜索树和数学有关的题目</h2>
 * <h3>1. 二叉搜索树的最小绝对值</h3>
 * <h3>2. 二叉搜索树的范围和</h3>
 * <h3>3. 二叉搜索树的众数</h3>
 * <h3>注: 这些题目基本都不难, 考的频率也很低, 但是做的人很多, 所以也记录在这里</h3>
 */
public class BinarySearchMath {

    public static void main(String[] args) {
    }

    /**
     * <h3>思路: 二叉树的最小绝对值, 基于中序遍历实现</h3>
     * <h3>1. 递归</h3>
     * <h3>2. 迭代</h3>
     * <h3>注: 如果要求不能够遍历, 那么能够实现吗?</h3>
     */
    private static int getMinimumDifference(TreeNode root){
        int minValue = Integer.MAX_VALUE;
        TreeNode previous = null;
        LinkedList<TreeNode> stack = new LinkedList<>();
        while (!stack.isEmpty() || root != null){
            if(root != null){
                stack.push(root);
                root = root.left;
            }else{
                root = stack.pop();
                if(previous != null)
                    minValue = Math.min(minValue, root.value - previous.value);
                previous = root;
                root = root.right;
            }
        }
        return minValue;
    }

    private static int minValue = Integer.MAX_VALUE;
    private static TreeNode previous = null;
    private static void dfs(TreeNode root){
        if (root == null) return;
        dfs(root.left);
        if (previous != null)
            minValue = Math.min(minValue, root.value - previous.value);
        previous = root;
        dfs(root.right);
    }

    /**
     * <h3>思路: 二叉搜索树的范围和</h3>
     * <h3>1. 递归</h3>
     * <h3>2. 迭代</h3>
     */
    private static int rangeSumBST(TreeNode root, int low, int high){
        int targetSum = 0;
        TreeNode previous = null;
        LinkedList<TreeNode> stack = new LinkedList<>();
        while (!stack.isEmpty() || root != null){
            if(root != null){
                stack.push(root);
                root = root.left;
            }else{
                root = stack.pop();
                if(root.value <= high && root.value >= low)
                    targetSum += root.value;
                root = root.right;
            }
        }
        return minValue;
    }

    private static int targetSum = 0;
    private static void dfs(TreeNode root, int low, int high){
        if(root == null) return;

        dfs(root.left, low, high);
        if (root.value <= high && root.value >= low)
            targetSum += root.value;
        dfs(root.right, low, high);
    }

    /**
     * <h3>思路: 二叉搜索树中的众数</h3>
     * <h3>1. 哈希表统计每个数字出现的次数, 最后统计所有出现次数最大的数字即可</h3>
     * <h3>2. 深度遍历: 中序遍历 迭代</h3>
     * <h3>3. 莫里斯遍历: 空间复杂度为 O(1)</h3>
     * <h3>注: 再次强调, 二叉搜索树一定要使用性质!</h3>
     * <h3>注: 只要在树的题目中要求空间复杂度为 O(1), 那么肯定使用莫里斯遍历</h3>
     */
    private static Integer[] findModeInfix(TreeNode root){
        // 1. 基准值, 也就是前一个结点的值
        int base = 0;
        // 2. 当前基准值的次数, 和最大次数
        int count = 0, maxCount = 0;
        List<Integer> modes = new LinkedList<>();
        LinkedList<TreeNode> stack = new LinkedList<>();
        while (!stack.isEmpty() || root != null){
            if (root != null){
                stack.push(root);
                root = root.left;
            }else{
                root = stack.pop();
                // 如果结点值和当前值相等那么直接增加次数
                if (root.value == base){
                    count++;
                }else{
                    // 不要在这里进行 "结算", 如果最后一个数的次数最多, 那么就进入不了集合了
                    base = root.value;
                    count = 1;
                }
                // 每次都去判断当前的次数和最大的次数的关系
                // 注: 此时的基准值的次数可能不是最终的结果, 但是之后次数增加比最大值大的时候, 清空后再加入基准值就行
                // 注: 这样可以避免此前的问题
                if (count == maxCount)
                    modes.add(base);
                if (count > maxCount){
                    maxCount = count;
                    modes.clear();
                    // 清除之后重新将此前的基准值加入进来
                    modes.add(base);
                }

                root = root.right;
            }
        }

        // 注: 如果返回值是基本类型的数组, 那么就只能够一个一个复制过去了
        return modes.toArray(new Integer[0]);
    }

    private static int base = 0;
    private static int count = 0, maxCount = 0;
    private static Integer[] findModeMorris(TreeNode root){
        List<Integer> modes = new LinkedList<>();
        TreeNode current = root;
        TreeNode mostRight = null;
        while (current != null){
            if (current.left != null){
                mostRight = current.left;
                while (mostRight.right != null && mostRight.right != current)
                    mostRight = mostRight.right;
                if (mostRight.right == null){
                    mostRight.right = current;
                    current = current.left;
                    continue;
                }else{
                    mostRight.right = null;
                    update(current, modes);
                }
            }else{
                update(current, modes);
            }
            current = current.right;
        }
        return modes.toArray(new Integer[0]);
    }

    private static void update(TreeNode root, List<Integer> modes){
        if (root.value == base) {
            count++;
        }else{
            base = root.value;
            count = 1;
        }
        if (count == maxCount)
            modes.add(base);
        if (count > maxCount){
            maxCount = count;
            modes.clear();
            modes.add(base);
        }
    }


}
