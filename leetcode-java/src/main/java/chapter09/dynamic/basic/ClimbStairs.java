package chapter09.dynamic.basic;


import java.util.List;

/**
 * <h2>爬楼梯问题</h2>
 * <h3>1. 爬楼梯基础问题</h3>
 * <h3>2. 爬楼梯最小花费问题</h3>
 */
public class ClimbStairs {

    /**
     * <h3>爬楼梯</h3>
     * <h3>1. 动态规划</h3>
     * <h3>2. 状态压缩</h3>
     * <h3>扩展题目: </h3>
     * <h3>1. 要求不能够爬到阶梯数为 7 或者 7 的倍数: 添加判断条件</h3>
     * <h3>2. 要求到达顶端的所有路径: 采用递归实现</h3>
     */
    private static int climbStairs(int n){
        // 1. 表结构:
        int[] dp = new int[n + 1];
        // 2. basecase
        dp[0] = dp[1] = 1;
        // 3. 填表
        for (int idx = 2; idx <= n; idx++) {
            dp[idx] = dp[idx - 1] + dp[idx - 2];
        }
        return dp[n];
    }

    /**
     * <h3>爬楼梯的最小花费</h3>
     * <h3>注: 从左到右填和从右到左填的含义是不同的并且状态转移方程也是不同的</h3>
     */
    private static int minCostClimbingStairs1(int[] cost){
        // 1. 表结构: dp[i] 表示当前台阶到达顶部的最少花费
        int[] dp = new int[cost.length + 1];
        // 2. basecase
        dp[cost.length] = 0;
        dp[cost.length - 1] = cost[cost.length - 1];
        // 3. 填表
        for(int idx = cost.length - 2;idx >= 0;idx--){
            dp[idx] = Math.min(dp[idx + 1], dp[idx + 2]) + cost[idx];
        }

        return Math.min(dp[0], dp[1]);
    }

    /**
     * <h3>从左向右填</h3>
     */
    private static int minCostClimbingStairs2(int[] cost){
        // 1. 表结构: dp[i] 表示到达第 i 个台阶的最小花费
        int[] dp = new int[cost.length + 1];
        // 2. basecase
        dp[0] = dp[1] = 0;
        // 3. 填表
        for(int idx = 2;idx < dp.length;idx++){
            dp[idx] = Math.min(dp[idx - 1] + cost[idx - 1], dp[idx - 2] + cost[idx - 2]);
        }
        return dp[cost.length];
    }

    /**
     * <h3>递归</h3>
     */
    private static int minCostClimbingStairs3(int[] cost){
        int[] dp = new int[cost.length];
        return Math.min(dfs(0, cost, dp), dfs(1, cost, dp));
    }

    private static int dfs(int idx, int[] cost, int[] dp){
        if (idx >= cost.length)
            return 0;
        if (dp[idx] != 0)
            return dp[idx];
        return dp[idx] = Math.min(dfs(idx + 1, cost, dp), dfs(idx + 2, cost, dp)) + cost[idx];
    }
}
