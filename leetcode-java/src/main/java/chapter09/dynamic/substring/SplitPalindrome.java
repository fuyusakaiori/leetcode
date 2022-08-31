package chapter09.dynamic.substring;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * <h3>分割回文字符串</h3>
 */
public class SplitPalindrome {

    private static final List<String> palindromes = new LinkedList<>();

    private static final List<List<String>> palindromesSet = new LinkedList<>();

    /**
     * <h3>分割回文字符串</h3>
     * <h3>动态规划 + 回溯</h3>
     */
    public static List<List<String>> partition(String s) {
        char[] chars = s.toCharArray();
        // 1. 表结构
        boolean[][] dp = new boolean[chars.length][chars.length];
        // 2. 动态规划判断哪些字符串是回文
        for(int left = chars.length - 1;left >= 0;left--){
            for(int right = left;right < chars.length;right++){
                if(chars[left] == chars[right])
                    dp[left][right] = left + 1 >= right - 1 || dp[left + 1][right - 1];
            }
        }
        // 3. 回溯分割
        partition(0, s, dp);
        return palindromesSet;
    }

    public static void partition(int start, String s, boolean[][] dp){
        if(start == s.length()){
            palindromesSet.add(new LinkedList<>(palindromes));
            return;
        }
        for(int end = start;end < s.length();end++){
            if(dp[start][end]){
                palindromes.add(s.substring(start, end + 1));
                partition(end + 1, s, dp);
                palindromes.remove(palindromes.size() - 1);
            }
        }
    }

    /**
     * <h3>分割回文字符串 II</h3>
     * <h3>动态规划 + 回溯</h3>
     */
    public int minCut(String s) {
        char[] chars = s.toCharArray();
        boolean[][] dp1 = new boolean[chars.length][chars.length];
        for(int left = chars.length - 1;left >= 0;left--){
            for(int right = 0;right < chars.length;right++){
                if(chars[left] == chars[right])
                    dp1[left][right] = left + 1 >= right - 1 || dp1[left + 1][right - 1];
            }
        }
        // 回溯切割
        int[] dp2 = new int[chars.length];
        Arrays.fill(dp2, -1);
        return minCut(0, dp1, dp2) - 1;
    }

    public int minCut(int start, boolean[][] dp1, int[] dp2){
        if(start == dp1.length)
            return 0;
        if(dp2[start] != -1)
            return dp2[start];
        int minCnt = Integer.MAX_VALUE;
        for(int end = start;end < dp1.length;end++){
            if(dp1[start][end])
                minCnt = Math.min(minCnt, minCut(end + 1, dp1, dp2) + 1);
        }
        return dp2[start] = minCnt;
    }

}
