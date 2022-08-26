package chapter09.dynamic.backpack;

/**
 * <h3>完全平方数</h3>
 */
public class NumSquare {


    /**
     * <h3>动态规划</h3>
     */
    public static int numSquares1(int number){
        // 1. 表结构: dp[i] 表示 i 需要的最少完全平方数数量
        int[] dp = new int[number + 1];
        // 2. 填表
        for(int cur = 1;cur <= number;cur++){
            // 3. 直接赋最大值
            dp[cur] = cur;
            for(int idx = 1; idx * idx <= cur;idx++){
                dp[cur] = Math.min(dp[cur], dp[cur - idx * idx] + 1);
            }
        }
        return dp[number];
    }

    /**
     * <h3>递归</h3>
     */
    public int numSquares2(int number, int[] dp){
        if(number == 0)
            return 0;
        if(dp[number] != 0)
            return dp[number];
        int minCnt = Integer.MAX_VALUE / 2;
        for(int idx = 1; idx * idx <= number;idx++){
            minCnt = Math.min(numSquares2(number - idx * idx, dp) + 1, minCnt);
        }
        return dp[number] = minCnt;
    }

}
