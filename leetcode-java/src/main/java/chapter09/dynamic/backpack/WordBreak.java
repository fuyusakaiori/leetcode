package chapter09.dynamic.backpack;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * <h3>单词拆分</h3>
 */
public class WordBreak {

    /**
     * <h3>单词拆分</h3>
     * <h3>递归</h3>
     */
    public static boolean wordBreak1(String target, List<String> wordDict){
        return dfs(0, target, new HashSet<>(wordDict), new Boolean[target.length()]);
    }

    public static boolean dfs(int start, String target, Set<String> wordDict, Boolean[] dp){
        if (start == target.length())
            return true;
        if (dp[start] != null)
            return dp[start];
        // 1. 不断截取子串
        for (int end = start + 1;end <= target.length();end++){
            // 2. 判断是否符合条件
            if (wordDict.contains(target.substring(start, end)))
                // 3. 如果剩余的子串也成功匹配, 那么整个字符串都可以匹配, 直接返回
                if (dfs(end, target, wordDict, dp))
                    return dp[start] = true;
        }
        // 4. 如果所有子串都无法匹配, 就需要返回
        return dp[start] = false;
    }

    /**
     * <h3>单词拆分</h3>
     * <h3>动态规划</h3>
     */
    public static boolean wordBreak2(String target, List<String> wordDict){
        int length = target.length();
        Set<String> dictionary = new HashSet<>(wordDict);
        // 1. 表结构: dp[i] 表示 (i, length - 1) 的子串是否可以成功匹配
        boolean[] dp = new boolean[length + 1];
        // 2. basecase
        dp[length] = true;
        // 3. 填表
        for(int start = length - 1;start >= 0;start--){
            for(int end = length;end >= start + 1;end--){
                dp[start] = dictionary.contains(target.substring(start, end)) && dp[end];
                if (dp[start]) break;
            }
        }
        return dp[0];
    }

    private static StringBuilder sentence = new StringBuilder();

    private static List<String> sentences = new LinkedList<>();

    /**
     * <h3>单词拆分 II</h3>
     * <h3>递归</h3>
     */
    public static List<String> wordBreak(List<String> wordDict, String target){
        dfs(0, target, new HashSet<>(wordDict));
        return sentences;
    }

    public static void dfs(int start, String target, Set<String> wordDict){
        if(start == target.length()){
            sentences.add(sentence.substring(0, sentence.length() - 1));
            return;
        }
        for(int end = start + 1;end <= target.length();end++){
            String word = target.substring(start, end);
            if(wordDict.contains(word)){
                sentence.append(word).append(" ");
                dfs(end, target, wordDict);
                sentence.delete(sentence.length() - word.length() - 1, sentence.length());
            }
        }
    }
}
