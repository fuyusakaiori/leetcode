package chapter09.dynamic.stock;

/**
 * <h3>买卖股票最佳时机含冷冻期</h3>
 */
public class BuyStockFrozen {

    public static int maxProfit1(int[] prices) {
        // 1. 表结构
        int[][] dp = new int[prices.length][2];
        // 2. basecase
        dp[0][0] = 0;
        dp[0][1] = -prices[0];
        // 3. 填表
        for(int idx = 1;idx < prices.length;idx++){
            dp[idx][0] = Math.max(dp[idx - 1][0], dp[idx - 1][1] + prices[idx]);
            dp[idx][1] = Math.max(dp[idx - 1][1], idx < 2 ? -prices[idx] : dp[idx - 2][0] - prices[idx]);
        }
        return dp[prices.length - 1][0];
    }

    /**
     * <h3>买卖股票的最佳时机含有冷冻期</h3>
     */
    private static int maxProfit2(int[] prices){
        int[][] dp = new int[prices.length + 1][2];
        for (int index = prices.length - 1;index >= 0;index--){
            dp[index][0] = Math.max(dp[index + 1][0], dp[index + 1][1] - prices[index]);
            dp[index][1] = Math.max(dp[index + 1][1], index + 2 < dp.length ?
                                                              dp[index + 2][0] + prices[index] : prices[index]);
        }
        return dp[0][0];
    }

    /**
     * <h3>暴力递归 + 记忆化搜索</h3>
     */
    private static int maxProfit3(int[] prices, int index, int hasStock, Integer[][] dp){
        if(index == prices.length) return 0;
        if(dp[index][hasStock] != null) return dp[index][hasStock];

        if (hasStock > 0){
            // 冷冻期
            return dp[index][hasStock] = Math.max(
                    maxProfit3(prices, index + 2, 0, dp) + prices[index],
                    maxProfit3(prices, index + 1, 1, dp));
        }

        return  dp[index][hasStock] = Math.max(
                maxProfit3(prices, index + 1, 1, dp) - prices[index],
                maxProfit3(prices, index + 1, 0, dp));
    }

}
