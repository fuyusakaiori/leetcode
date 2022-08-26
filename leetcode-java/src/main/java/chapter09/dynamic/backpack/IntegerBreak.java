package chapter09.dynamic.backpack;

/**
 * <h3>整数拆分</h3>
 */
public class IntegerBreak {

    /**
     * <h3>动态规划</h3>
     */
    public static int integerBreak(int number, int[] dp){
        if(number == 1)
            return 1;
        if(dp[number] != 0)
            return dp[number];
        int maxValue = 0;
        for(int idx = 1;idx <= number / 2;idx++){
            maxValue = Math.max(idx * (number - idx),
                    Math.max(maxValue, idx * integerBreak(number - idx, dp)));
        }
        return dp[number] = maxValue;
    }

    public static int integerBreak(int number){
        // 1. 表结构: dp[i] 表示数字 i 的最大乘积
        int[] dp = new int[number + 1];
        // 2. basecase
        dp[1] = 1;
        // 3. 填表
        for (int cur = 2;cur <= number;cur++){
            for (int idx = 1;idx <= cur / 2;idx++){
                dp[cur] = Math.max((cur - idx) * idx, Math.max(idx * dp[cur - idx], dp[cur]));
            }
        }
        return dp[number];
    }

}
