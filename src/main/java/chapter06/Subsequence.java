package chapter06;

import java.util.LinkedList;
import java.util.List;

/**
 * <h1>子序列问题</h1>
 * <h2>1. 字符串</h2>
 * <h3>1.1 最长回文子串</h3>
 * <h3>1.2 回文子串</h3>
 * <h3>1.3 分割回文子串</h3>
 * <h3>1.4 最长回文子序列</h3>
 * <h2>2. 数组</h2>
 * <h3>2.1 最大子数组和</h3>
 * <h3>2.2 乘积最大子数组</h3>
 * <h3>2.3 最长递增子序列</h3>
 *
 * <p>注: 这里包含字符串和数组中所有的子序列问题</p>
 * <p>注: 这里所有题目都采用动态规划完成</p>
 */
public class Subsequence
{
    public static void main(String[] args)
    {

    }

    /**
     * <h3>1. 最长回文子串</h3>
     * <h3>1. 在具体分析问题之前, 首先需要知道这个题有多少的限制条件</h3>
     * <h3>1.1 首先需要确保字符串是最长的, 这个是肯定可以使用递归直接解决的</h3>
     * <h3>1.2 其次需要确保这个字符串是子串而不是子序列, 这个递归也是可以完成的</h3>
     * <h3>1.3 但是回文字符串却不是递归可以直接解决的, 我们只能够每次单独判断这个字符串是否为回文</h3>
     * <h3>注: 子序列问题中和数组相关的问题, 基本都只会有两个限制条件, 或者说第三个条件很容易实现</h3>
     * <h3>2. 首先, 这个题并不太符合左神的那种尝试理论, 只能够老实分析状态依赖</h3>
     * <h3>2.1 s.charAt(i) != s.charAt(j), 也就是最外侧的两个字符不相等, 那么这个字符串肯定不是回文</h3>
     * <h3>2.2 s.charAt(i) == s.charAt(j), 就判断内部字符串是否是回文, 该字符串是否为回文就依赖内部子串</h3>
     * <h3>2.3 换句话说, 状态转移方程就是这个 dp[i][j] = dp[i + 1][j - 1]</h3>
     * <h3>3. 知道单个字符串如何判断之后, 接下来需要做的就是对每个字符串都应用这个判断方法</h3>
     * <h3>3.1 看起来非常像暴力解法, 事实上, 如果你先使用递归完成的话, 确实就是暴力解法, 时间复杂度为 O(n^3)</h3>
     * <h3>3.2 但是如果你将递归逐步改写为动态规划的时候, 时间复杂度就会降低到 O(n^2), 这就是所谓聪明的枚举</h3>
     * <h3>4. 事实上这里的递归基本就对应使用双指针的暴力解法, 动态规划就对应中心扩展的暴力解法, 本质不是很快</h3>
     * <h3>5. 只有 Manacher 算法才能够真正加速枚举的过程, 不过相对难写</h3>
     * <h3>注: 其余几个回文子串的动态规划基本和这个题完全相同</h3>
     * @return 最长回文子串
     */
    private static String longestPalindrome(String str){
        int begin = 0;
        int maxLength = 0;
        boolean[][] dp = new boolean[str.length()][str.length()];
        // 注: 这里填表的顺序需要画图分析才知道, 不要想当然去填表
        for (int start = str.length() - 1;start >= 0;start--){
            for (int end = str.length() - 1;end >= start;end--){
                if (str.charAt(start) == str.charAt(end)){
                    // 注: 发现没有?动态规划和递归几乎完全相同
                    dp[start][end] = start + 1 >= end - 1 || dp[start + 1][end - 1];
                    if (dp[start][end] && maxLength < end - start + 1){
                        maxLength = end - start + 1;
                        begin = start;
                    }
                }else{
                    dp[start][end] = false;
                }
            }
        }
        return str.substring(begin, begin + maxLength);
    }

    /**
     * <h3>暴力递归 + 记忆化搜索</h3>
     * <h3>注: 字符串的动态规划题目通常都是二维表, 因为需要记录起始和结尾</h3>
     */
    private static String dfs(String str){
        int begin = 0;
        int maxLength = 0;
        // 注: 这里最好使用对象数组, 否则没有办法不知道哪些是判断过, 哪些是没判断过的
        Boolean[][] dp = new Boolean[str.length()][str.length()];
        for(int start = 0;start < str.length();start++){
            for (int end = start;end < str.length();end++){
                if (dfs(str, start, end, dp) && maxLength < end - start + 1){
                    maxLength = end - start + 1;
                    begin = start;
                }
            }
        }
        return str.substring(begin, begin + maxLength);
    }

    /**
     * <h3>1. 这里的递归只能够判断单个字符串是否是回文字符串</h3>
     * <h3>2. 也就是说我们需要在递归的外面添加双层循环, 确保对每个子串都进行相应的判断</h3>
     */
    private static boolean dfs(String str, int left, int right, Boolean[][] dp){
        if (left >= right) return true;
        if (dp[left][right] != null) return dp[left][right];
        if (str.charAt(left) == str.charAt(right)){
            // 注: 这就是动态转移方程
            return dp[left][right] = dfs(str, left + 1, right + 1, dp);
        }
        return dp[left][right] = false;
    }

    /**
     * <h3>2. 回文子串</h3>
     * <h3>1. 这个题和最长回文子串基本类似, 都是先判断回文字符串, 然后计数就可以了</h3>
     * <h3>2. 实际上就是将之前求最大长度的过程替换成计算回文串的个数了, 其余代码完全不懂</h3>
     * <h3>注: 由于基本类似, 所以就只写动态规划了, 不再写暴力解</h3>
     * @return 回文子串的数量
     */
    private static int countSubstrings(String str){
        // 注: 直接计数就可以了
        int count = 0;
        char[] chars = str.toCharArray();
        boolean[][] dp = new boolean[str.length()][str.length()];
        for(int start = chars.length - 1;start >= 0;start--){
            for(int end = chars.length - 1;end >= start;end--){
                if(chars[start] == chars[end]){
                    dp[start][end] = start + 1 >= end - 1 || dp[start + 1][end - 1];
                    if(dp[start][end]){
                        count++;
                    }
                }else{
                    dp[start][end] = false;
                }
            }
        }
        return count;
    }

    /**
     * <h3>3. 分割回文子串</h3>
     * <h3>1. 这个和之前的两个题区别也不大, 你显然需要知道哪些是回文字符串才行</h3>
     * <h3>2. 因为你只要知道哪些是回文子串, 你就可以选择每个回文子串, 然后深度搜索去不断尝试</h3>
     * <h3>3. 看到这里, 其实发现这种三个限制条件的动态规划, 尤其是第三个条件实现相对困难的动态规划的特点</h3>
     * <h3>3.1 实际上, 我们没有对全局使用动态规划, 只是在判断回文字符串的过程中使用了动态规划从而节省时间</h3>
     * <h3>3.2 相当于说动态规划只是一个预处理的过程, 其余该枚举还是需要枚举, 并不像数组动态规划是全局使用的</h3>
     * <h3>注: 这里依然只写动态规划解, 暴力解和第一个没有太大区别</h3>
     * <h3>注: 实际上直接暴力解还是挺快的, 那个暴力解记录在 LeetCode 上</h3>
     * @return 回文子串的集合
     */
    private static List<List<String>> partition(String str){
        List<String> partition = new LinkedList<>();
        List<List<String>> combination = new LinkedList<>();
        // 动态规划处理回文字符串
        boolean[][] dp = new boolean[str.length()][str.length()];
        for(int start = str.length() - 1;start >= 0;start--){
            for(int end = str.length() - 1;end >= start;end--){
                if(str.charAt(start) == str.charAt(end)){
                    dp[start][end] = start + 1 >= end - 1 || dp[start + 1][end - 1];
                }else{
                    dp[start][end] = false;
                }
            }
        }
        // 没哟办法在判断回文的时候处理, 就只能够再判断结束之后处理
        dfs(str, 0, dp, partition, combination);
        return combination;
    }

    private static void dfs(String str, int start, boolean[][] dp, List<String> partition, List<List<String>> combination){
        if(start == str.length()){
            combination.add(new LinkedList<>(partition));
            return;
        }
        for(int end = start;end < str.length();end++){
            if(dp[start][end]){
                partition.add(str.substring(start, end + 1));
                dfs(str, end + 1, dp, partition, combination);
                partition.remove(partition.size() - 1);
            }
        }
    }



}
