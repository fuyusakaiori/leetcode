package chapter09.dynamic.backpack;

/**
 * <h3>零钱兑换</h3>
 */
public class CoinChange {

    /**
     * <h3>零钱兑换</h3>
     * <h3>递归</h3>
     */
    public static int coinChange(int amount, int[] coins){
        // 1. 表结构: dp[i] 表示 i 元至少需要的硬币数
        int[] dp = new int[amount + 1];
        // 2. 填表
        for (int money = 1;money <= amount;money++){
            dp[money] = -1;
            for (int coin : coins) {
                if (money - coin >= 0){
                    int curCnt = dp[money - coin];
                    if (curCnt != -1)
                        dp[money] = dp[money] == -1 ? curCnt + 1: Math.min(curCnt + 1, dp[money]);
                }
            }
        }
        return dp[amount];
    }

    /**
     * <h3>零钱兑换</h3>
     * <h3>递归</h3>
     */
    public static int coinChange(int amount, int[] coins, int[] dp){
        if(amount == 0)
            return 0;
        if(amount < 0)
            return -1;
        if(dp[amount] != 0)
            return dp[amount];
        // 每个硬币都可以挑选
        int minCnt = -1;
        for (int coin : coins) {
            int curCnt = coinChange(amount - coin, coins, dp);
            if (curCnt != -1)
                minCnt = minCnt == -1 ? curCnt + 1 : Math.min(curCnt + 1, minCnt);
        }
        return dp[amount] = minCnt;
    }


    /**
     * <h3>零钱兑换 II</h3>
     * <h3>动态规划</h3>
     */
    public static int change(int amount, int[] coins){
        return 0;
    }

    /**
     * <h3>零钱兑换 II</h3>
     * <h3>递归</h3>
     */
    public static int change(int idx, int amount, int[] coins, Integer[][] dp){
        if (amount == 0)
            return 1;
        if (idx == coins.length)
            return 0;
        if (dp[idx][amount] != null)
            return dp[idx][amount];
        int total = 0;
        // 注: 不可能存在重复的方案
        for (int cnt = 0;coins[idx] * cnt <= amount;cnt++){
            total += change(idx + 1, amount - coins[idx] * cnt, coins, dp);
        }
        return dp[idx][amount] = total;
    }

}
