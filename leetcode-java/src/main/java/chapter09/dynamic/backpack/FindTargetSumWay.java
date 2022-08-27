package chapter09.dynamic.backpack;

import java.util.Arrays;
import java.util.Map;

/**
 * <h3>目标和</h3>
 */
public class FindTargetSumWay {

    /**
     * <h3>递归</h3>
     * <h3>1. 暴力递归不会超时但是效率很低</h3>
     * <h3>2. 暴力递归不考虑表结构的问题, 所以非常好写</h3>
     */
    private static int findTargetSumWays1(int[] nums, int target){
        return findTargetSumWays1(0, target, nums);
    }

    private static int findTargetSumWays1(int idx, int target, int[] nums){
        if (idx == nums.length)
            return target == 0 ? 1: 0;
        return findTargetSumWays1(idx + 1, target - nums[idx], nums)
                       + findTargetSumWays1(idx + 1, target + nums[idx], nums);
    }

    private static final int offset = 2000;

    /**
     * <h3>记忆化搜索</h3>
     * <h3>1. 数组和最大不会超过 offset</h3>
     * <h3>2. 目标和 target + offset >= 0</h3>
     */
    public static int findTargetSumWays2(int[] nums, int target){
        // 1. 初始化表结构: 避免 dp[idx][target] 造成越界, 需要 2 倍左右的长度
        int[][] dp = new int[nums.length][target + 2 * offset];
        for (int[] array : dp) {
            Arrays.fill(array, Integer.MAX_VALUE);
        }
        // 2. 递归: 如果不添加偏移量, 那么就会在使用记忆化搜索时越界
        return findTargetSumWays2(0, offset, target + offset, nums, dp);
    }

    /**
     * <h3>静态数组 + 偏移量规避负数索引</h3>
     */
    public static int findTargetSumWays2(int idx, int cur, int target, int[] nums, int[][] dp){
        if (idx == nums.length)
            return cur == target ? 1: 0;
        if (dp[idx][cur] != Integer.MAX_VALUE)
            return dp[idx][cur];
        return dp[idx][cur] = findTargetSumWays2(idx + 1, cur - nums[idx], target, nums, dp) +
                                      findTargetSumWays2(idx + 1, cur + nums[idx], target, nums, dp);
    }

    /**
     * <h3>哈希表规避负数索引</h3>
     */
    public static int findTargetSumWays2(int idx, int target, int[] nums, Map<String, Integer> dp){
        if (idx == nums.length)
            return target == 0 ? 1: 0;
        // 1. 两个可变化参数合并成一个
        String key = idx + "_" + target;
        if (dp.containsKey(key))
            return dp.get(key);
        // 2. 递归
        int first = findTargetSumWays2(idx + 1, target - nums[idx], nums, dp);
        int second = findTargetSumWays2(idx + 1, target + nums[idx], nums, dp);
        dp.put(key, first + second);
        return first + second;
    }

    /**
     * <h3>动态规划: 存在问题</h3>
     */
    public static int findTargetSumWays3(int[] nums, int target){
        // 1. 表结构: dp[i][j] 表示选用 nums[i] 元素从当前 cur 组成 target 的方案数
        int[][] dp = new int[nums.length + 1][target + 2 * offset + 1];
        // 2. basecase
        dp[nums.length][target + offset] = 1;
        // 3. 填表
        for (int idx = nums.length - 1;idx >= 0;idx--){
            for (int cur = offset + target; cur < 2 * offset + target; cur++) {
                if(cur + nums[idx] < 2 * offset + target)
                    dp[idx][cur] += dp[idx + 1][cur + nums[idx]];
                dp[idx][cur] += dp[idx + 1][cur - nums[idx]];
            }
        }
        return dp[0][offset];
    }

}
