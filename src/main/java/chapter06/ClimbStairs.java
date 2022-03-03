package chapter06;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

/**
 * <h2>爬楼梯问题</h2>
 * <h3>1. 爬楼梯基础问题</h3>
 * <h3>2. 爬楼梯最小花费问题</h3>
 * <h3>3. 母牛生崽</h3>
 * <h3>4. 细胞分裂</h3>
 * <h2>动态转移方程</h2>
 * <h3>dp[n] = dp[n - 1] + dp[n - 2](根据题目需要改变)</h3>
 * <h3>dp[n] = Math.min(dp[n - 1], dp[n - 2])</h3>
 */
public class ClimbStairs
{
    public static void main(String[] args)
    {

    }

    /**
     * <h3>1. 爬楼梯基础问题</h3>
     * <h3>可以从尝试的角度思考, 也可以考虑从子问题的角度思考</h3>
     * <h3>你要达到下一个楼梯, 要么跳一步, 要么就跳两步, 直到最后你没有步数可跳就结束了</h3>
     * <h3>你只能够达到下一个台阶或者下两个台阶, 然后你要从下一个或者下两个楼梯到达中点</h3>
     * <h3>也就意味着你是依赖这两个状态的, 所以状态转移方程就分析出来了</h3>
     */
    private static int climbStairs(int target){
        int[] dp = new int[target + 1];
        dp[0] = dp[1] = 1;
        for(int index = 2;index < dp.length;index++){
            dp[index] = dp[index - 1] + dp[index - 2];
        }
        return dp[target];
    }

    private static int dfs_climb(int target, int[] dp){
        if (target < 0) return 0;
        if (target == 0) return 1;
        if (dp[target] != -1) return dp[target];
        return dp[target] = dfs_climb(target - 1, dp) + dfs_climb(target - 2, dp);
    }

    /**
     * <h3>2. 爬楼梯的最小花费</h3>
     * <h3>你依然是依赖下一个楼梯和下两个楼梯的状态, 但是求的是最小开销而不是方法数</h3>
     * <h3>所以需要对两个状态取最小就行, 而不是求和</h3>
     */
    private static int climbStairsMinCost(int[] cost){
        int[] dp = new int[cost.length + 1];
        dp[cost.length] = 0;
        dp[cost.length - 1] = cost[cost.length - 1];
        for(int index = cost.length - 2;index >= 0;index--){
            dp[index] = cost[index] + Math.min(dp[index + 1], dp[index + 2]);
        }

        return Math.min(dp[0], dp[1]);
    }

    private static int dfs(int[] cost, int index, int[] dp){
        if(index >= cost.length) return 0;
        if(dp[index] != 0) return dp[index];
        // 注: 每次向上爬都是要加上当前楼梯到的开销的
        return dp[index] = cost[index] + Math.min(dfs(cost, index + 1, dp), dfs(cost, index + 2, dp));
    }



    /**
     * <h3>3. 母牛生崽</h3>
     * <p>动态规划</p>
     * @param year 在给定年份之后, 牛的数量
     */
    private static int cowGivingBirth(int year){
        int[] dp = new int[year + 1];
        for(int index = 1;index < dp.length;index++){
            dp[index] = index > 3 ? dp[index - 1] + dp[index - 3]: index;
        }
        return dp[year];
    }

    /**
     * <h3>1. 上一年的所有牛肯定都会存活, 所以今年的数量一定会依赖上一年的数量</h3>
     * <h3>2. 此外, 前三年的牛已经在上一年成熟, 并且会生一头新牛, 所以也会依赖前三年的数量</h3>
     * <p>暴力递归 + 记忆化搜索</p>
     */
    private static int dfs(int year, int[] dp){
        if(year == 1 || year == 2 || year == 3) return year;
        if (dp[year] != 0) return dp[year];
        return dp[year] = dfs(year - 1, dp) + dfs(year - 3, dp);
    }

    /**
     * <h3>4. 细胞分裂</h3>
     * <h4>1. 第一秒只有 1个细胞, 该细胞从第 2 秒开始分裂</h4>
     * <h4>2. 产生的新细胞会在经过 2 秒之后开始分裂</h4>
     * <h4>3. 分裂的过程中, k 个细胞会聚合成一组, 如果此时存在单独成组的细胞, 那么就会死亡</h4>
     * <h4>4. 问经过 n 秒之后, 会有多少个细胞</h4>
     * <p>注: 和之前的母牛生崽问题区别就在于, 细胞是会死亡的</p>
     * @param seconds 细胞分裂的时间
     * @param convergence 多少个细胞聚合成一组
     */
    private static int cellDivision(int seconds, int convergence){

        return 0;
    }

    private static int dfs(int seconds, int convergence, int[] dp){
        if (seconds == 1 || seconds == 2) return seconds;
        int base = dfs(seconds - 1, convergence, dp) + dfs(seconds - 2, convergence, dp);
        return dp[seconds] = base % convergence == 1 ? base - 1: base;
    }

}
