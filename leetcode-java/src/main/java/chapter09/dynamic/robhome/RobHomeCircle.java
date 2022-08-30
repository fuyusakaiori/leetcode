package chapter09.dynamic.robhome;

import java.util.Arrays;

/**
 * <h3>打家劫舍 II</h3>
 */
public class RobHomeCircle {


    /**
     * <h3>动态规划: 从左到右</h3>
     */
    public static int rob1(int[] homes){
        if(homes.length == 1)
            return homes[0];
        // 1. 表结构: dp[i] 表示从 0 偷到 i 的最大金额
        int[] dp1 = new int[homes.length - 1];
        int[] dp2 = new int[homes.length - 1];
        // 2. basecase
        dp1[0] = homes[0];
        dp2[0] = homes[1];
        // 3. 填表
        for (int idx = 1; idx < homes.length - 1; idx++) {
            // 4. 从 0 开始遍历到 length - 1
            dp1[idx] = idx == 1 ? Math.max(homes[0], homes[1]) :
                               Math.max(dp1[idx - 1], homes[idx] + dp1[idx - 2]);
            // 5. 从 1 开始遍历到 length
            dp2[idx] = idx == 1 ? Math.max(homes[1], homes[2]) :
                               Math.max(dp2[idx - 1], homes[idx + 1] + dp2[idx - 2]);
        }
        return Math.max(dp1[dp1.length - 1], dp2[dp2.length - 1]);
    }

    /**
     * <h3>递归</h3>
     */
    public static int rob3(int[] homes){
        if(homes.length == 1)
            return homes[0];
        // 1. 两个备忘录: 两次记忆化搜索
        int[] dp1 = new int[homes.length];
        int[] dp2 = new int[homes.length];
        Arrays.fill(dp1, -1);
        Arrays.fill(dp2, -1);
        // 2. 两次记忆化不能使用相同的备忘录, 因为起点不同所以中间的值也会不同, 使用同一张表会导致结果出错
        return Math.max(rob3(0, homes.length - 1, homes, dp1),
                rob3(1, homes.length, homes, dp2));
    }

    public static int rob3(int start, int end, int[] homes, int[] dp){
        if(start >= end)
            return 0;
        if(dp[start] != -1)
            return dp[start];
        return dp[start] = Math.max(
                rob3(start + 1, end, homes, dp), rob3(start + 2, end, homes, dp) + homes[start]);
    }

}
