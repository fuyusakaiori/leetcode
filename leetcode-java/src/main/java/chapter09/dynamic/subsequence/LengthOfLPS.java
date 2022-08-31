package chapter09.dynamic.subsequence;

import static chapter09.dynamic.subsequence.LengthOfLCS.lengthOfLCS1;

/**
 * <h3>最长回文子序列</h3>
 */
public class LengthOfLPS {


    /**
     * <h3>镜像反转 + LCS</h3>
     */
    private static int lengthOfLPS1(String str){
        // 1. 反转字符串
        String reverse = new StringBuilder(str).reverse().toString();
        // 2. 寻找公共子序列
        return lengthOfLCS1(reverse, str);
    }

    /**
     * <h3>动态规划</h3>
     */
    private static int lengthOfLPS2(String str){
        char[] chars = str.toCharArray();
        int[][] dp = new int[chars.length][chars.length];
        for (int first = chars.length - 1;first >= 0;first--){
            // 注: 这里只能从左向右填, 依赖关系决定的
            for (int second = first;second <= chars.length - 1;second++){
                if (first == second){
                    dp[first][second] = 1;
                    continue;
                }
                if (chars[first] == chars[second]){
                    dp[first][second] = dp[first + 1][second - 1] + 2;
                }else{
                    dp[first][second] = Math.max(dp[first + 1][second], dp[first][second - 1]);
                }
            }
        }
        return dp[0][chars.length - 1];
    }

    /**
     * <h3>递归</h3>
     */
    private static int lengthOfLPS3(String str){
        int[][] dp = new int[str.length()][str.length()];
        return dfs(str.toCharArray(), 0, str.length() - 1, dp);
    }

    private static int dfs(char[] chars, int left, int right, int[][] dp){
        // 1. 左指针可能恰好和右指针相等 -> 单个字符肯定是回文字符串
        if (left == right)
            return 1;
        // 2. 左指针大于右指针时 -> 肯定就不是回文字符串
        if (left > right)
            return 0;
        // 3. 如果两个字符相同, 那么肯定要作为回文子序列的一部分
        if (chars[left] == chars[right])
            return dp[left][right] = dfs(chars, left + 1, right - 1, dp);
        // 4. 如果两个字符不等, 那么就尝试删除其中一个
        return dp[left][right] = Math.max(dfs(chars, left + 1, right, dp),
                dfs(chars, left, right + 1, dp));
    }

}
