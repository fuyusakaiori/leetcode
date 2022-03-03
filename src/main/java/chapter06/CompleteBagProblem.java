package chapter06;

import java.util.Arrays;

/**
 * <h2>完全背包问题</h2>
 * <h3>1. 零钱兑换</h3>
 * <h3>2. 零钱兑换 II</h3>
 * <h3>3. 完全平方数</h3>
 * <h3>4. 经典背包问题</h3>
 * <h2>动态转移方程</h2>
 * <h3>dp[amount] = Math.min(dp[amount], dp[amount - coins[index]])</h3>
 * <h3>dp[amount] += dp[amount - coins[index]]</h3>
 * <h3>dp[index] = Math.min(min, dp[index - digit * digit] + 1)</h3>
 * <p></p>
 * <p>注: 完全背包问题在暴力递归的过程中一定含有循环过程</p>
 */
public class CompleteBagProblem
{
    public static void main(String[] args)
    {

    }


    /**
     * <h3>1. 零钱兑换</h3>
     * <p>动态规划 => 状态压缩</p>
     * <p>注意: 两次状态压缩</p>
     */
    private static int coinChange(int[] coins, int target){
        int[] dp = new int[target + 1];
        Arrays.fill(dp, -1);
        dp[0] = 0;
        for(int index = coins.length - 1;index >= 0;index--){
            for(int amount = 1;amount < dp.length;amount++){
                int yes = amount - coins[index] >= 0 ? dp[amount - coins[index]]: -1;
                if(yes != -1 && dp[amount] == -1)
                    dp[amount] = yes + 1;
                else if(yes != -1 && dp[amount] != -1)
                    dp[amount] = Math.min(dp[amount], yes + 1);
            }
        }
        return dp[target];
    }

    /**
     * <p>暴力递归 + 记忆化搜索</p>
     */
    private static int dfs(int[] coins, int amount, int index, int[][] dp){
        if(amount == 0) return 0;
        if(index == coins.length) return -1;
        if(dp[index][amount] != -2) return dp[index][amount];
        int min = -1;
        for(int count = 0;coins[index] * count <= amount ;count++){
            int result = dfs(coins, amount - coins[index] * count, index + 1, dp);
            if(result != -1) min = min != -1 ? Math.min(min, count + result) : count + result;
        }
        dp[index][amount] = min;
        return dp[index][amount];
    }

    /**
     * <h3>2. 零钱兑换 II</h3>
     * <p>动态规划 => 两次状态压缩</p>
     */
    private static int change(int[] coins, int target){
        // 2. 第二次状态压缩
        int[] dp = new int[target + 1];
        dp[0] = 1;
        for(int index = coins.length - 1;index >= 0;index--){
            for(int amount = 1;amount < dp.length;amount++){
                // 1. 第一次状态压缩
                if(amount - coins[index] >= 0)
                    dp[amount] += dp[amount - coins[index]];
            }
        }
        return dp[target];
    }

    /**
     * <p>暴力递归 => 记忆化搜索</p>
     */
    public static int dfs(int[] coins, int amount, int index, Integer[][] dp){
        if(amount == 0) return 1;
        if(index == coins.length) return 0;
        if(dp[index][amount] != null) return dp[index][amount];
        int result = 0;
        for(int count = 0;count * coins[index] <= amount;count++){
            result += dfs(coins, amount - count * coins[index], index + 1, dp);
        }
        dp[index][amount] = result;
        return dp[index][amount];
    }

    /**
     * <h3>3. 完全平方数</h3>
     */
    private static int numberSquares(int number){
        int[] dp = new int[number + 1];
        for(int index = 1;index < dp.length;index++){
            int min = index;
            for(int digit = 1;digit * digit <= index;digit++){
                min = Math.min(min, dp[index - digit * digit] + 1);
            }
            dp[index] = min;
        }
        return dp[number];
    }

    /**
     * <p>暴力递归 + 记忆化搜索</p>
     * <p>注: 每个平方数都去尝试就行</p>
     */
    private static int dfs(int number, Integer[] dp){
        // 0. 已经凑出来, 所以正常返回
        if (number == 0) return 0;
        if (dp[number] != null) return dp[number];
        // 1. 挨个尝试每个平方数: 不要使用开方的 API, 会因为负数产生 NaN
        int min = number;
        for(int index = 1;index * index <= number;index++){
            min = Math.min(min, dfs(number - index * index, dp) + 1);
        }
        dp[number] = min;
        return dp[number];
    }


}
