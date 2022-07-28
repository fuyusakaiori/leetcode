package chapter07;

/**
 * <h2>字符串匹配问题</h2>
 * <h3>1. 最小编辑距离</h3>
 * <h3>2. 最长公共子序列</h3>
 * <h3>注: 这里的问题都是涉及两个字符串的问题, 所以没有将子序列问题归类过去</h3>
 * <h3>注: 其本质还是因为这个子序列问题和此前碰到的问题完全不同</h3>
 */
public class MatchString
{
    public static void main(String[] args)
    {

    }

    /**
     * <h3>编辑距离</h3>
     * <h3>1. 这个题和最长公共子序列基本类似, 只不过可能不太容易看出来</h3>
     * <h3>2. 首先, 我们需要理解下题中涉及的三个行为都是什么, 如何将其转换为更好的表现形式</h3>
     * <h3>2.1 题中提到对字符串进行替换、新增、删除, 但实际上肯定是不可能直接对字符串修改的</h3>
     * <h3>2.2 主要原因在于, 字符串是不可变的, 每次都修改, 成本过高, 基于这点就可以排除每次直接修改字符串</h3>
     * <h3>2.3 实际上, 你替换字符串之后, 两个字符肯定相同, 那么两个指针肯定都向前移动, 所以替换行为就转换为 dp[i+1][j+1]</h3>
     * <h3>2.4 删除字符串实际上就是第一个字符串少了个字符, 那么就只有第一个字符串指针向前移动, 所以删除行为就转换为 dp[i+1][j]</h3>
     * <h3>2.5 新增字符串实际上就是第一个字符串多了个字符, 那么就相当于第一个字符串指针向后移动, 但是向后移动也可以等价于第二个字符串指针向前移动, 所以等价于 dp[i][j+1]</h3>
     * <h3>3. 把三个行为转换成可行的表达方式之后, 你发现实际上就很简单了, 因为尝试方式从一开始就告诉你了, 不需要你自己想</h3>
     * <h3>4. 最后的问题在于 Base Case 是什么?这个 Base Case 和以往的都以很大不同</h3>
     * <h3>4.1 考虑这种情况, 如果第二个字符串的指针已经走到末尾, 那么证明之前的内容都已经匹配上, 那么对于第一个字符串来说, 只需要删除剩下的字符就可以了, 所以返回的就是剩余字符串的长度</h3>
     * <h3>4.2 再考虑这种情况, 如果第一个字符串的指针已经走到末尾, 那么只能够说明内容不足以支撑匹配了, 那么只需要每次都新增对应的字符就可以了, 也就是只要返回第二个字符串剩余长度就可以了</h3>
     * @param word1 可以进行增、删、换操作的字符串
     * @param word2 不进行任何操作的字符串
     * @return 最少的操作次数
     */
    private static int minDistance(String word1, String word2){
        int firstLength = word1.length();
        int secondLength = word2.length();
        int[][] dp = new int[firstLength + 1][secondLength + 1];
        // Base Case 的初始化完全来自于递归
        for(int index = 0;index < secondLength;index++){
            dp[firstLength][index] = secondLength - index;
        }
        for(int index = 0;index < firstLength;index++){
            dp[index][secondLength] = firstLength - index;
        }
        for(int first = firstLength - 1;first >= 0;first--){
            for(int second = secondLength - 1;second >= 0;second--){
                if(word1.charAt(first) != word2.charAt(second)){
                    int replace = dp[first + 1][second + 1] + 1;
                    int delete = dp[first + 1][second] + 1;
                    int insert = dp[first][second + 1] + 1;
                    dp[first][second] = Math.min(replace, Math.min(insert, delete));
                }else{
                    dp[first][second] = dp[first + 1][second + 1];
                }
            }
        }

        return dp[0][0];
    }

    /**
     * <h3>暴力递归 + 记忆化搜索</h3>
     */
    private static int dfs(char[] word1, int first, char[] word2, int second, int[][] dp){
        // Base Case 非常特别
        if(second == word2.length)
            return word1.length - first;
        if(first == word1.length)
            return word2.length - second;

        if(dp[first][second] != 0) return dp[first][second];

        if(word1[first] != word2[second]){
            // 注: 无论两个字符串的长度如何都是允许增加和删除行为的
            int replace = dfs(word1, first + 1, word2, second + 1, dp) + 1;
            int delete = dfs(word1, first + 1, word2, second, dp) + 1;
            int insert = dfs(word1, first, word2, second + 1, dp) + 1;
            return dp[first][second] = Math.min(replace, Math.min(insert, delete));
        }
        return dp[first][second] = dfs(word1, first + 1, word2, second + 1, dp);
    }

    /**
     * <h3>2. 最长公共子序列</h3>
     * <h3>1. 这个题目, 显然就只有两个限制条件, 只需要 “最长” 和 "子序列" 就可以了, 相对没有那么难想</h3>
     * <h3>2. 对于两个字符串而言, 基本只有一种尝试方式就是挨着对比, 没有其他方法</h3>
     * <h3>3. 如果两个字符相同, 那么就两个字符串的指针都向前移动, 也就是依赖 dp[i+1][j+1]</h3>
     * <h3>4. 如果两个字符串不相同, 那么就需要去尝试了, 这里左神的套路就可以用了, 因为尝试的操作很明显</h3>
     * <h3>4.1 既然不同, 那么就可以考虑只让第一个字符串向前移动, 另一个不动, 也就是 dp[i+1][j]</h3>
     * <h3>4.2 要么就是考虑让第二个字符串移动, 第一个字符串不动, 也就是 dp[i][j+1]</h3>
     * <h3>5. 然后你就会发现, 递归的尝试思路就出现了, 这也就意味动态转移方程得到了</h3>
     * <h3>注: 这个题的尝试方法很明显, 如果不同, 要么第一个动, 要么第二个动, 所以转移方程好想</h3>
     */
    private static int longestCommonSubsequence(String text1, String text2){
        int firstLength = text1.length();
        int secondLength = text2.length();
        int[][] dp = new int[firstLength + 1][secondLength + 1];
        for(int first = firstLength - 1;first >= 0;first--){
            for(int second = secondLength - 1;second >= 0;second--){
                if(text1.charAt(first) == text2.charAt(second)){
                    dp[first][second] = dp[first + 1][second + 1] + 1;
                }else{
                    dp[first][second] = Math.max(dp[first + 1][second], dp[first][second + 1]);
                }
            }
        }
        return dp[0][0];
    }

    /**
     * <h3>暴力递归 + 记忆化搜索</h3>
     */
    private static int dfs(char[] text1, int first, char[] text2, int second, Integer[][] dp){
        if(first == text1.length || second == text2.length) return 0;
        if(dp[first][second] != null) return dp[first][second];
        if(text1[first] == text2[second]){
            return dp[first][second] = dfs(text1, first + 1, text2, second + 1, dp) + 1;
        }
        return dp[first][second] = Math.max(dfs(text1, first + 1, text2, second, dp),
                dfs(text1, first, text2, second + 1, dp));
    }
}
