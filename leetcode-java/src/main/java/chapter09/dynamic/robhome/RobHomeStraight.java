package chapter09.dynamic.robhome;

import java.util.Arrays;

/**
 * <h3>打家劫舍</h3>
 */
public class RobHomeStraight
{

    /**
     * <h3>打家劫舍 </h3>
     * <h3>递归</h3>
     */
    public static int rob1(int[] homes){
        int[] dp = new int[homes.length];
        Arrays.fill(dp, -1);
        return rob1(0, homes, dp);
    }

    public static int rob1(int idx, int[] nums, int[] dp){
        if(idx >= nums.length)
            return 0;
        if(dp[idx] != -1)
            return dp[idx];
        return dp[idx] = Math.max(
                rob1(idx + 1, nums, dp), rob1(idx + 2, nums, dp) + nums[idx]);
    }

    /**
     * <h3>打家劫舍</h3>
     * <h3>动态规划: 从左到右</h3>
     */
    public static int rob2(int[] homes){
        if(homes.length == 1)
            return homes[0];
        // 1. 表结构: dp[i] 表示从 0 偷到 i 的最大利润
        int[] dp = new int[homes.length];
        // 2. basecase
        dp[0] = homes[0];
        dp[1] = Math.max(homes[0], homes[1]);
        // 3. 填表
        for(int idx = 2;idx < homes.length;idx++){
            dp[idx] = Math.max(dp[idx - 1], homes[idx] + dp[idx - 2]);
        }
        return dp[homes.length - 1];
    }

    /**
     * <h3>打家劫舍</h3>
     * <h3>动态规划: 从右到左</h3>
     */
    public static int rob3(int[] homes){
        // 1. 表结构: dp[i] 表示从 i 偷到 nums.length - 1 的最大金额
        int[] dp = new int[homes.length + 1];
        // 2. basecase
        dp[homes.length - 1] = homes[homes.length - 1];
        // 3. 填表
        for(int index = homes.length - 2;index >= 0;index--){
            dp[index] = Math.max(dp[index + 2] + homes[index], dp[index + 1]);
        }
        return Math.max(dp[0], dp[1]);
    }

}
