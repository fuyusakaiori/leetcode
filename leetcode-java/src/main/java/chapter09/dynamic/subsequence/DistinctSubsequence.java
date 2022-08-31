package chapter09.dynamic.subsequence;

/**
 * <h3>不同子序列</h3>
 */
public class DistinctSubsequence {


    /**
     * <h3>动态规划</h3>
     */
    private static int numDistinct1(String s, String t){
        char[] fArray = s.toCharArray();
        char[] sArray = t.toCharArray();
        int[][] dp = new int[fArray.length + 1][sArray.length + 1];
        for (int index = 0;index < dp.length;index++){
            dp[index][sArray.length] = 1;
        }
        for (int first = fArray.length - 1;first >= 0;first--){
            for (int second = sArray.length - 1;second >= 0;second--){
                if (fArray[first] == sArray[second]){
                    dp[first][second] += dp[first + 1][second + 1];
                }
                dp[first][second] += dp[first + 1][second];
            }
        }
        return dp[0][0];
    }

    /**
     * <h3>递归</h3>
     */
    public static int numDistinct2(String s, String t){
        char[] fArray = s.toCharArray();
        char[] sArray = t.toCharArray();
        Integer[][] dp = new Integer[fArray.length][s.length()];
        return numDistinct2(0, fArray, 0, sArray, dp);
    }

    public static int numDistinct2(int first, char[] fArray, int second, char[] sArray, Integer[][] dp){
        if(second == sArray.length)
            return 1;
        if(first == fArray.length)
            return 0;
        if(dp[first][second] != null)
            return dp[first][second];
        int result = 0;
        if(fArray[first] == sArray[second])
            result = numDistinct2(first + 1, fArray, second + 1, sArray, dp);
        result += numDistinct2(first + 1, fArray, second, sArray, dp);

        return dp[first][second] = result;
    }

}
