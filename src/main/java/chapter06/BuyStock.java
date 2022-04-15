package chapter06;

import java.util.Arrays;

/**
 * <h2>股票问题</h2>
 * <h3>1. 买卖股票最佳时机</h3>
 * <h3>2. 买卖股票最佳时机 II</h3>
 * <h3>3. 买卖股票最佳时机 III</h3>
 * <h3>4. 买卖股票最佳时机 IV</h3>
 * <h3>5. 买卖股票含冷冻期</h3>
 * <h3>6. 买卖股票含手续费</h3>
 * <h2>动态转移方程</h2>
 * <h3>1. 没有交易限制的动态规划方程</h3>
 * <h3>1.1 dp[index][0] = Math.max(dp[index + 1][1] - prices[index], dp[index + 1][0])</h3>
 * <h3>1.2 dp[index][1] = Math.max(dp[index + 1][1], prices[index])</h3>
 * <h3>2. 限制交易数量</h3>
 * <h2>注意事项</h2>
 * <h3>注: 这类题的尝试方式其实很直接, 就是要么买股票, 要么不买, 要么保持状态</h3>
 * <h3>注: 但是核心点在于, 你必须认为你的手上是有钱的, 也就是买入股票的时候扣钱, 卖出股票的时候加钱</h3>
 * <h3>而不是直接让卖出时候股票的价格减去购入股票时候的价格</h3>
 * <h3>注: 这个题的递归版本是在评论区找到的, 因为没有人这么写, 但是这么写很好理解</h3>
 */
public class BuyStock {

    /**
     * <h3>思路: 股票问题 I</h3>
     * <h3>1. 如果是笔试, 就直接照着递归的思路走就可以了, 不需要去考虑太多</h3>
     * <h3>2. 如果是面试, 那最好想想怎么解释这个动态规划问题</h3>
     * <h3>3. 后面几个题目基本完全一致, 只要写出递归, 一切都好说</h3>
     * <h3>注: 动态规划</h3>
     */
    private static int maxProfit_I(int[] prices){
        int[][] dp = new int[prices.length + 1][2];
        // 1. 如果目前持有股票, 并且你可以在最后一天之后卖出
        dp[prices.length - 1][1] = prices[prices.length - 1];
        // 2. 如果目前没有持有股票, 那么在最后这天买股票, 肯定亏损
        dp[prices.length - 1][0] = 0;
        // TODO 实际上是可以倒着填的, 我这次就是倒着填出来的
        for (int index = prices.length - 1;index < dp.length;index++){
            dp[index][0] = Math.max(dp[index + 1][1] - prices[index], dp[index + 1][0]);
            dp[index][1] = Math.max(dp[index + 1][1], prices[index]);
        }
        // 理论上应该比较持有股票和不持有股票的时候的价格, 实际上则是不用的, 因为你卖出了股票肯定比你没有卖出利润高
        return dp[0][0];
    }

    /**
     * <h3>思路: 股票问题 I</h3>
     * <h3>1. 如果手上没持有任何股票, 那么就只能够买股票, 或者不买</h3>
     * <h3>2. 如果手上持有股票, 那么就只能够卖出, 且卖出之后, 不能够继续买卖, 或者不买</h3>
     * <h3>3. 所以买与不买的状态需要通过一个变量来进行区分, 否则是没有办法区分的</h3>
     * <h3>注: 暴力递归 + 记忆化搜索</h3>
     */
    private static int dfs_I(int[] prices, int index, int hasStock, Integer[][] dp){
        if (index == prices.length) return 0;

        if(hasStock == 0)
            return dp[index][hasStock] = Math.max(dfs_I(prices, index + 1, 1, dp) - prices[index],
                    dfs_I(prices, index + 1, 0, dp) );
        return dp[index][hasStock] = Math.max(dfs_I(prices, index + 1, 1, dp), prices[index]);
    }

    /**
     * <h3>2. 买卖股票的最佳时机 II</h3>
     */
    private static int maxProfit_II(int[] prices){
        int[][] dp = new int[prices.length][2];
        // Base Case
        dp[0][1] = -prices[0];
        for (int index = 1;index < dp.length;index++){
            dp[index][0] = Math.max(dp[index - 1][1] + prices[index], dp[index - 1][0]);
            dp[index][1] = Math.max(dp[index - 1][0] - prices[index], dp[index - 1][1]);
        }
        return dp[prices.length - 1][0];
    }

    /**
     * <p>暴力递归 + 记忆化搜索</p>
     */
    private static int dfs_II(int[] prices, int index, int hasStock, Integer[][] dp){
        if(index == prices.length) return 0;
        if(dp[index][hasStock] != null) return dp[index][hasStock];

        if (hasStock > 0){
            return dp[index][hasStock] = Math.max(
                    dfs_II(prices, index + 1, 0, dp) + prices[index],
                    dfs_II(prices, index + 1, 1, dp));
        }

        return  dp[index][hasStock] = Math.max(
                dfs_II(prices, index + 1, 1, dp) - prices[index],
                dfs_II(prices, index + 1, 0, dp));
    }

    /**
     * <h3>3. 买卖股票的最佳时机 III</h3>
     */
    private static int maxProfit_III(int[] prices){
        return maxProfit_IV(prices, 2);
    }

    private static int dfs_III(int[] prices, int index, int hasStock, Integer[][][] dp){
        return dfs_IV(prices, 2, 0, 0, dp);
    }

    /**
     * <h3>4. 买卖股票的最佳时机 IV</h3>
     * <h3>注: 三维表如何填?</h3>
     * @param limit 限制的交易数
     */
    private static int maxProfit_IV(int[] prices, int limit){

        return 0;
    }

    private static int dfs_IV(int[] prices, int limit, int index, int hasStock, Integer[][][] dp){
        if(limit == 0 || index == prices.length) return 0;
        if(dp[index][limit][hasStock] != null) return dp[index][limit][hasStock];

        if(hasStock > 0){
            dp[index][limit][hasStock] = Math.max(dfs_IV(prices, index + 1, limit - 1, 0, dp) + prices[index],
                    dfs_IV(prices, index + 1, limit, 1, dp));
            return dp[index][limit][hasStock];
        }
        // 注: 不能够在这里直接就将交易数量 - 1, 如果在这里就减少会导致递归进入方法的时候, 还没有卖出就停止了
        dp[index][limit][hasStock] = Math.max(dfs_IV(prices, index + 1, limit, 1, dp) - prices[index],
                dfs_IV(prices, index + 1, limit, 0, dp));

        return dp[index][limit][hasStock];
    }

    /**
     * <h3>买卖股票的最佳时机含手续费</h3>
     * @param fee 手续费
     */
    private static int maxProfitFee(int[] prices, int fee){
        int[][] dp = new int[prices.length + 1][2];
        for (int index = prices.length - 1;index >= 0;index--){
            dp[index][0] = Math.max(dp[index + 1][0], dp[index + 1][1] - prices[index]);
            dp[index][1] = Math.max(dp[index + 1][1], dp[index + 1][0] + prices[index] - fee);
        }
        return dp[0][0];
    }

    private static int dfsFee(int[] prices, int fee, int index, int hasStock, Integer[][] dp){
        if(index == prices.length) return 0;
        if(dp[index][hasStock] != null) return dp[index][hasStock];

        if (hasStock > 0){
            return dp[index][hasStock] = Math.max(
                    dfsFee(prices, fee, index + 1, 0, dp) + prices[index] - fee,
                    dfsFee(prices, fee, index + 1, 1, dp));
        }

        return  dp[index][hasStock] = Math.max(
                dfsFee(prices, fee,index + 1, 1, dp) - prices[index],
                dfsFee(prices, fee,index + 1, 0, dp));
    }

    /**
     * <h3>买卖股票的最佳时机含有冷冻期</h3>
     */
    private static int maxProfitFrozen(int[] prices){
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
    private static int dfsFrozen(int[] prices, int index, int hasStock, Integer[][] dp){
        if(index == prices.length) return 0;
        if(dp[index][hasStock] != null) return dp[index][hasStock];

        if (hasStock > 0){
            // 冷冻期
            return dp[index][hasStock] = Math.max(
                    dfsFrozen(prices, index + 2, 0, dp) + prices[index],
                    dfsFrozen(prices, index + 1, 1, dp));
        }

        return  dp[index][hasStock] = Math.max(
                dfsFrozen(prices, index + 1, 1, dp) - prices[index],
                dfsFrozen(prices, index + 1, 0, dp));
    }
}
