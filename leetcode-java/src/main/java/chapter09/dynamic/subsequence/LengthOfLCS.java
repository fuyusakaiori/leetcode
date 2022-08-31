package chapter09.dynamic.subsequence;

/**
 * <h3>最长公共子序列</h3>
 */
public class LengthOfLCS {

    /**
     * <h3>动态规划</h3>
     */
    public static int lengthOfLCS1(String text1, String text2){
        char[] fArray = text1.toCharArray(), sArray = text2.toCharArray();
        int[][] dp = new int[fArray.length + 1][sArray.length + 1];
        for (int first = fArray.length - 1;first >= 0;first--){
            for (int second = sArray.length - 1;second >= 0;second--){
                if (fArray[first] == sArray[second]){
                    dp[first][second] = dp[first + 1][second + 1] + 1;
                }else{
                    dp[first][second] = Math.max(dp[first][second + 1], dp[first + 1][second]);
                }
            }
        }
        return dp[0][0];
    }

    /**
     * <h3>递归</h3>
     */
    public static int lengthOfLCS2(String text1, String text2){
        Integer[][] dp = new Integer[text1.length()][text2.length()];
        return lengthOfLCS2(text1.toCharArray(), 0, text2.toCharArray(), 0, dp);
    }

    public static int lengthOfLCS2(char[] fArray, int first, char[] sArray, int second, Integer[][] dp){
        if(first == fArray.length || second == sArray.length)
            return 0;
        if(dp[first][second] != null)
            return dp[first][second];
        if(fArray[first] == sArray[second])
            return dp[first][second] = lengthOfLCS2(fArray, first + 1, sArray, second + 1, dp) + 1;
        return dp[first][second] = Math.max(lengthOfLCS2(fArray, first + 1, sArray, second, dp),
                lengthOfLCS2(fArray, first , sArray, second + 1, dp));
    }

}
