package chapter09.dynamic.subarray;

/**
 * <h3>最大子数组和</h3>
 */
public class MaxSubArraySum
{

    /**
     * <h3>动态规划</h3>
     */
    public static int maxSubArray(int[] nums){
        // 1. 表结构: dp[i] 表示从 0 到 i 的子数组的最大和
        int[] dp = new int[nums.length];
        // 2. basecase
        dp[0] = nums[0];
        // 3. 填表
        int maxSum = dp[0];
        for (int idx = 1; idx < dp.length; idx++) {
            dp[idx] = Math.max(dp[idx - 1] + nums[idx], nums[idx]);
            maxSum = Math.max(maxSum, dp[idx]);
        }
        return maxSum;
    }

}
