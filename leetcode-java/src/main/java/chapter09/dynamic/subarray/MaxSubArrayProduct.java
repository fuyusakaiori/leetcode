package chapter09.dynamic.subarray;

/**
 * <h3>乘积最大子数组</h3>
 */
public class MaxSubArrayProduct {

    public static int maxProduct(int[] nums) {
        // 1. 表结构 dp[i][0] 表示从 0 到 i 的最大值, dp[i][1] 表示从 0 到 i 的最小值
        int[][] dp = new int[nums.length][2];
        // 2. basecase
        dp[0][0] = dp[0][1] = nums[0];
        // 3. 填表
        int maxValue = dp[0][0];
        for(int idx = 1;idx < nums.length;idx++){
            dp[idx][0] = Math.max(nums[idx],
                    Math.max(nums[idx] * dp[idx - 1][0], nums[idx] * dp[idx - 1][1]));
            dp[idx][1] = Math.min(nums[idx],
                    Math.min(nums[idx] * dp[idx - 1][0], nums[idx] * dp[idx - 1][1]));
            maxValue = Math.max(maxValue, dp[idx][0]);
        }
        return maxValue;
    }

}
