package chapter09.dynamic.stock;

/**
 * <h3>买卖股票的最佳时机含手续费</h3>
 */
public class BuyOneStockFee {
    /**
     * <h3>动态规划: 从左向右</h3>
     */
    public static int maxProfit1(int[] prices, int fee) {
        // 1. 表结构
        int[][] dp = new int[prices.length][2];
        // 2. basecase
        dp[0][0] = 0;
        dp[0][1] = -prices[0];
        // 3. 填表
        for(int idx = 1;idx < dp.length;idx++){
            dp[idx][0] = Math.max(dp[idx - 1][0], dp[idx - 1][1] + prices[idx] - fee);
            dp[idx][1] = Math.max(dp[idx - 1][1], dp[idx - 1][0] - prices[idx]);
        }
        return dp[prices.length - 1][0];
    }

    /**
     * <h3>买卖股票的最佳时机含手续费</h3>
     * @param fee 手续费
     */
    private static int maxProfit2(int[] prices, int fee){
        int[][] dp = new int[prices.length + 1][2];
        for (int index = prices.length - 1;index >= 0;index--){
            dp[index][0] = Math.max(dp[index + 1][0], dp[index + 1][1] - prices[index]);
            dp[index][1] = Math.max(dp[index + 1][1], dp[index + 1][0] + prices[index] - fee);
        }
        return dp[0][0];
    }

    private static int maxProfit3(int[] prices, int fee, int index, int hasStock, Integer[][] dp){
        if(index == prices.length) return 0;
        if(dp[index][hasStock] != null) return dp[index][hasStock];

        if (hasStock > 0){
            return dp[index][hasStock] = Math.max(
                    maxProfit3(prices, fee, index + 1, 0, dp) + prices[index] - fee,
                    maxProfit3(prices, fee, index + 1, 1, dp));
        }

        return  dp[index][hasStock] = Math.max(
                maxProfit3(prices, fee,index + 1, 1, dp) - prices[index],
                maxProfit3(prices, fee,index + 1, 0, dp));
    }
}
