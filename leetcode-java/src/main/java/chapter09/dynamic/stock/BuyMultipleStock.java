package chapter09.dynamic.stock;

/**
 * <h3>买卖股票最佳时机 IV</h3>
 */
public class BuyMultipleStock {

    /**
     * <h3>动态规划: 从左到右</h3>
     */
    public static int maxProfit1(int cnt, int[] prices){
        if(prices.length == 0)
            return 0;
        // 1. 表结构: dp[i][j][k] 表示在第 i 天的情况下还有 k 笔交易可以进行的最大利润 (持有股票和不持有股票)
        int[][][] dp = new int[prices.length][cnt + 1][2];
        // 2. basecase
        for (int idx = 0;idx < prices.length;idx++){
            dp[idx][0][0] = 0;
            dp[idx][0][1] = Integer.MIN_VALUE;
        }
        for (int idx = 0;idx < cnt + 1;idx++){
            dp[0][idx][0] = 0;
            dp[0][idx][1] = -prices[0];
        }
        // 3. 填表
        for(int idx = 1;idx < prices.length;idx++){
            for(int kdx = 1;kdx < cnt + 1;kdx++){
                dp[idx][kdx][0] = Math.max(dp[idx - 1][kdx][0], dp[idx - 1][kdx][1] + prices[idx]);
                dp[idx][kdx][1] = Math.max(dp[idx - 1][kdx][1], dp[idx - 1][kdx - 1][0] - prices[idx]);
            }
        }
        return dp[prices.length - 1][cnt][0];
    }

    /**
     * <h3>动态规划: 从右到左</h3>
     */
    public static int maxProfit2(int cnt, int[] prices){
        if(cnt == 0) return 0;
        int[][][] dp = new int[prices.length + 1][2][cnt + 1];
        for(int index = prices.length - 1;index >= 0;index--){
            for(int limit = 0;limit <= cnt;limit++){
                // 注: 这里的 0 代表的是没有进行任何交易
                dp[index][0][limit] = Math.max(dp[index + 1][0][limit],
                        dp[index + 1][1][limit] - prices[index]);
                dp[index][1][limit] = Math.max(dp[index + 1][1][limit],
                        limit - 1 > 0 ? dp[index + 1][0][limit - 1] + prices[index] : prices[index]);
            }
        }
        return dp[0][0][cnt];
    }

    /**
     * <h3>递归</h3>
     */
    public static int maxProfit3(int cnt, int[] prices) {
        return maxProfit(0, 0, cnt, prices, new Integer[prices.length][2][cnt + 1]);
    }

    public static int maxProfit(int idx, int hasStock, int cnt, int[] prices, Integer[][][] dp){
        if(cnt == 0 || idx == prices.length)
            return 0;
        if(dp[idx][hasStock][cnt] != null)
            return dp[idx][hasStock][cnt];
        // 1. 如果手中没有持有股票, 那么就可以卖出股票或者保持原状态
        if(hasStock > 0)
            return dp[idx][hasStock][cnt] = Math.max(
                    maxProfit(idx + 1, hasStock, cnt, prices, dp),
                    maxProfit(idx + 1, 1 - hasStock, cnt - 1, prices, dp) + prices[idx]);
        // 2. 如果手中持有股票, 那么就可以购买股票或者保持原状态
        return dp[idx][hasStock][cnt] = Math.max(
                maxProfit(idx + 1, hasStock, cnt, prices, dp),
                maxProfit(idx + 1, 1 - hasStock, cnt, prices, dp) - prices[idx]);
    }

}
