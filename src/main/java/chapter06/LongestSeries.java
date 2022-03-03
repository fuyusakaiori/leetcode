package chapter06;

import utils.TreeNode;

/**
 * <h2>最长数列问题</h2>
 * <h3>1. 最大子数组和</h3>
 * <h3>2. 乘积最大子数组</h3>
 * <h3>3. 二叉树中的最大路径和</h3>
 * <h2>动态转移方程</h2>
 * <h3>dp[index] = Math.max(dp[index - 1] + array[index], array[index])</h3>
 * <h3>dp1[index] = Math.max(dp1[index - 1] * array[index], dp2[index - 1] * array[index], array[index])</h3>
 * <h3>dp2[index] = Math.min(dp1[index - 1] * array[index], dp2[index - 1] * array[index], array[index])</h3>
 * <p>注: 这三个题完全类似, 状态转移方程都基本相同</p>
 */
public class LongestSeries
{
    public static void main(String[] args)
    {

    }

    /**
     * <h3>1. 最大子数组和</h3>
     * <p>动态规划: 可以状态压缩</p>
     */
    private static int maxSubArray(int[] numbers){
        int max = numbers[0];
        int[] dp = new int[numbers.length];
        dp[0] = numbers[0];
        for(int index = 1;index < dp.length;index++){
            dp[index] = Math.max(dp[index - 1] + numbers[index], numbers[index]);
            max = Math.max(max, dp[index]);
        }
        return max;
    }

    // 注: 记录递归过程中的最大值
    private static int max = Integer.MIN_VALUE;

    /**
     * <p>暴力递归: 这个题没有记忆化搜索的版本, 因为没有多次回溯的过程</p>
     * <p>注: 记得最后返回的是 max 不是 dfs 的返回值</p>
     */
    private static int dfs(int[] numbers, int index){
        if(index == numbers.length) return 0;
        // 0. 判断返回值是否是正数, 如果是正数, 我才选择要这个子数组, 否则不要
        int sum = Math.max(numbers[index], dfs(numbers, index + 1) + numbers[index]);
        // 1. 判断是否更新当前最大的子数组和
        max = Math.max(sum , max);
        return sum;
    }

    /**
     * <h3>2. 乘积最大子数组</h3>
     * <p>动态规划</p>
     * <p></p>
     * <p>注: 返回值为数组的动态规划如何改</p>
     */
    private static int maxProduct(int[] numbers){
        // 0. 记录最大值的表和记录最小值的表
        int max = 0;
        int[] dp1 = new int[numbers.length];
        int[] dp2 = new int[numbers.length];
        max =  dp1[0] = dp2[0] = numbers[0];
        for (int index = 1;index < numbers.length;index++){
            dp1[index] = Math.max(dp1[index - 1] * numbers[index],
                    Math.max(dp2[index - 1] * numbers[index], numbers[index]));
            dp2[index] = Math.min(dp1[index - 1] * numbers[index],
                    Math.min(dp2[index - 1] * numbers[index], numbers[index]));
            max = Math.max(max, dp1[index]);
        }
        return max;
    }

    private static int result = Integer.MIN_VALUE;

    /**
     * <p>严格来说已经不算暴力递归了, 因为没有尝试的过程</p>
     * <p></p>
     * <p>注: 依然没有记忆化搜索</p>
     */
    private static int[] dfs(int[] numbers, int index, Object ...args){
        if(index == numbers.length) return new int[]{1, 1};
        int[] values = dfs(numbers, index + 1, 0);
        int max = Math.max(values[1] * numbers[index], Math.max(values[0] * numbers[index], numbers[index]));
        int min = Math.min(values[1] * numbers[index], Math.min(values[0] * numbers[index], numbers[index]));
        values[0] = max;
        values[1] = min;
        result = Math.max(max, result);
        return values;
    }

    private static int maxPathSum = Integer.MIN_VALUE;

    /**
     * <h3>3. 二叉树最大路径和</h3>
     * <p></p>
     * <p>注: 只有递归形式</p>
     */
    private static int maxPathSum(TreeNode root){
        dfs(root);
        return maxPathSum ;
    }

    private static int dfs(TreeNode root){
        if(root == null) return 0;
        // 默认当前结点就是要选的
        // 看左子树和右子树是否能够让自己变得更大
        int leftMax = Math.max(dfs(root.left), 0);
        int rightMax = Math.max(dfs(root.right), 0);
        maxPathSum = Math.max(leftMax + rightMax + root.value, maxPathSum);
        return root.value + Math.max(leftMax, rightMax);
    }
}
