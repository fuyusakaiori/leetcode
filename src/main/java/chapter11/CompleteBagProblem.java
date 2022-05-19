package chapter11;

import java.util.*;

/**
 * <h2>完全背包问题</h2>
 * <h3>1. 零钱兑换</h3>
 * <h3>2. 零钱兑换 II</h3>
 * <h3>3. 完全平方数</h3>
 * <h3>4. 经典背包问题</h3>
 * <h3>5. 单词拆分</h3>
 * <h3>6. 单词拆分 II</h3>
 * <h3>注: 完全背包问题在暴力递归的过程中一定含有循环过程</h3>
 */
public class CompleteBagProblem
{
    public static void main(String[] args)
    {

    }


    /**
     * <h3>1. 零钱兑换</h3>
     * <p>动态规划 => 状态压缩</p>
     * <p>注意: 两次状态压缩</p>
     */
    private static int coinChange(int[] coins, int target){
        int[] dp = new int[target + 1];
        Arrays.fill(dp, -1);
        dp[0] = 0;
        for(int index = coins.length - 1;index >= 0;index--){
            for(int amount = 1;amount < dp.length;amount++){
                int yes = amount - coins[index] >= 0 ? dp[amount - coins[index]]: -1;
                if(yes != -1 && dp[amount] == -1)
                    dp[amount] = yes + 1;
                else if(yes != -1 && dp[amount] != -1)
                    dp[amount] = Math.min(dp[amount], yes + 1);
            }
        }
        return dp[target];
    }

    /**
     * <p>暴力递归 + 记忆化搜索</p>
     */
    private static int dfs(int[] coins, int amount, int index, int[][] dp){
        if(amount == 0) return 0;
        if(index == coins.length) return -1;
        if(dp[index][amount] != -2) return dp[index][amount];
        int min = -1;
        for(int count = 0;coins[index] * count <= amount ;count++){
            int result = dfs(coins, amount - coins[index] * count, index + 1, dp);
            if(result != -1) min = min != -1 ? Math.min(min, count + result) : count + result;
        }
        dp[index][amount] = min;
        return dp[index][amount];
    }

    /**
     * <h3>2. 零钱兑换 II</h3>
     * <p>动态规划 => 两次状态压缩</p>
     */
    private static int change(int[] coins, int target){
        // 2. 第二次状态压缩
        int[] dp = new int[target + 1];
        dp[0] = 1;
        for(int index = coins.length - 1;index >= 0;index--){
            for(int amount = 1;amount < dp.length;amount++){
                // 1. 第一次状态压缩
                if(amount - coins[index] >= 0)
                    dp[amount] += dp[amount - coins[index]];
            }
        }
        return dp[target];
    }

    /**
     * <p>暴力递归 => 记忆化搜索</p>
     */
    public static int dfs(int[] coins, int amount, int index, Integer[][] dp){
        if(amount == 0) return 1;
        if(index == coins.length) return 0;
        if(dp[index][amount] != null) return dp[index][amount];
        int result = 0;
        for(int count = 0;count * coins[index] <= amount;count++){
            result += dfs(coins, amount - count * coins[index], index + 1, dp);
        }
        dp[index][amount] = result;
        return dp[index][amount];
    }

    /**
     * <h3>3. 完全平方数</h3>
     */
    private static int numberSquares(int number){
        int[] dp = new int[number + 1];
        for(int index = 1;index < dp.length;index++){
            int min = index;
            for(int digit = 1;digit * digit <= index;digit++){
                min = Math.min(min, dp[index - digit * digit] + 1);
            }
            dp[index] = min;
        }
        return dp[number];
    }

    /**
     * <p>暴力递归 + 记忆化搜索</p>
     * <p>注: 每个平方数都去尝试就行</p>
     */
    private static int dfs(int number, Integer[] dp){
        // 0. 已经凑出来, 所以正常返回
        if (number == 0) return 0;
        if (dp[number] != null) return dp[number];
        // 1. 挨个尝试每个平方数: 不要使用开方的 API, 会因为负数产生 NaN
        int min = number;
        for(int index = 1;index * index <= number;index++){
            min = Math.min(min, dfs(number - index * index, dp) + 1);
        }
        dp[number] = min;
        return dp[number];
    }

    /**
     * <h3>思路: 单词拆分</h3>
     */
    private static boolean wordBreak(String str, List<String> wordDict){
        boolean[] dp = new boolean[str.length() + 1];
        Set<String> dict = new HashSet<>(wordDict);
        dp[str.length()] = true;
        for (int end = str.length();end >= 0;end--){
            for (int start = end - 1;start >= 0;start--){
                if (dp[end] && dict.contains(str.substring(start, end))){
                    dp[start] = true;
                    break;
                }
            }
        }

        return dp[0];
    }

    private static int dfs(String str, int start, Set<String> dict, int[] dp){
        if (start == str.length()) return 1;
        if (dp[start] != 0) return dp[start];
        for (int end = start + 1;end <= str.length();end++){
            if (dict.contains(str.substring(start, end))){
                if (dfs(str, end, dict, dp) == 1) return dp[start] = 1;
            }
        }
        return dp[start] = -1;
    }

    /**
     * <h3>思路: 单词拆分</h3>
     */
    private final static List<String> sentences = new LinkedList<>();

    private static List<String> wordBreak(List<String> wordDict, String str) {
        dfs(str, new HashSet<>(wordDict), new StringBuilder(), 0);
        return sentences;
    }

    public static void dfs(String str, Set<String> dict, StringBuilder sb, int start){
        if(start == str.length()){
            sentences.add(sb.toString().substring(0, sb.length() - 1));
            return;
        }
        for(int end = start + 1;end <= str.length();end++){
            String sub = str.substring(start, end);
            if(dict.contains(sub)){
                sb.append(sub).append(" ");
                dfs(str, dict, sb, end);
                sb.delete(sb.length() - sub.length() - 1, sb.length());
            }
        }
    }


}
