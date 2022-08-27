package chapter09.dynamic.stock;

/**
 * <h3>买卖股票最佳时机</h3>
 */
public class BuyOneStock {

    /**
     * <h3>动态规划: 从右到左</h3>
     */
    private static int maxProfit3(int[] prices){
        int[][] dp = new int[prices.length + 1][2];
        // 1. 如果目前持有股票, 并且你可以在最后一天之后卖出
        dp[prices.length - 1][1] = prices[prices.length - 1];
        // 2. 如果目前没有持有股票, 那么在最后这天买股票, 肯定亏损
        dp[prices.length - 1][0] = 0;
        for (int index = prices.length - 1;index < dp.length;index++){
            dp[index][0] = Math.max(dp[index + 1][1] - prices[index], dp[index + 1][0]);
            dp[index][1] = Math.max(dp[index + 1][1], prices[index]);
        }
        // 理论上应该比较持有股票和不持有股票的时候的价格, 实际上则是不用的, 因为你卖出了股票肯定比你没有卖出利润高
        return dp[0][0];
    }

    /**
     * <h3>从左到右</h3>
     */
    public static int maxProfit2(int[] prices){
        // 1. 表结构: dp[i][j] 表示在股票价格为 prices[i] 的最佳利润
        int[][] dp = new int[prices.length][2];
        // 2. basecase
        dp[0][0] = 0;
        dp[0][1] = -prices[0];
        // 3. 填表
        for(int idx = 1;idx < dp.length;idx++){
            // 3.1 如果手中要没有持有股票, 依赖于 idx - 1 没有持有股票的状态或者 idx - 1 持有股票立刻卖出
            dp[idx][0] = Math.max(dp[idx - 1][0], dp[idx - 1][1] + prices[idx]);
            // 3.2 如果手中要持有股票, 依赖于 idx - 1 持有股票的状态或者立刻买一张股票
            dp[idx][1] = Math.max(dp[idx - 1][1], -prices[idx]);
        }
        return dp[prices.length - 1][0];
    }

    /**
     * <h3>1. 如果手上没持有任何股票, 那么就只能够买股票, 或者不买</h3>
     * <h3>2. 如果手上持有股票, 那么就只能够卖出, 且卖出之后, 不能够继续买卖, 或者不买</h3>
     * <h3>3. 所以买与不买的状态需要通过一个变量来进行区分, 否则是没有办法区分的</h3>
     * <h3>递归</h3>
     */
    private static int maxProfit3(int[] prices, int idx, int hasStock, Integer[][] dp){
        if (idx == prices.length) return 0;
        if (dp[idx][hasStock] != null)
            return dp[idx][hasStock];
        if(hasStock == 0)
            return dp[idx][hasStock] = Math.max(maxProfit3(prices, idx + 1, 1, dp) - prices[idx],
                    maxProfit3(prices, idx + 1, 0, dp));
        return dp[idx][hasStock] = Math.max(maxProfit3(prices, idx + 1, 1, dp), prices[idx]);
    }

}
