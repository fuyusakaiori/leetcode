package chapter06;

import java.util.LinkedList;
import java.util.List;

/**
 * <h2>动态规划: 子串问题</h2>
 * <h3>1. 最长回文串</h3>
 * <h3>2. 回文子串</h3>
 * <h3>3. 分割回文串</h3>
 * <h3>4. 分割回文子串 II</h3>
 * <h3>5. 最长回文序列</h3>
 * <h3>注: 这类题目解法基本一致: ① 中心扩展 ② {@code Manacher} 算法 ③ d动态规划</h3>
 */
public class Substring {

    /**
     * <h3>思路: 最长回文串</h3>
     * <h3>1. 中心扩展算法: 这个算法是一定要掌握的</h3>
     * <h3>2. {@code Manacher} 算法: 这个是拿来装逼的</h3>
     * <h3>3. 动态规划: 这个是最好要会的, 相对容易的优化解</h3>
     */
    private static String longestPalindrome1(String str){
        int maxLength = 0;
        String result = null;
        char[] chars = str.toCharArray();
        // 1. 这里最核心的地方在于存在两种情况: aba bba;前者很容易判断, 但是后者和前者判断就有些微差距
        // 2. 所以这里就有两种处理方式: 填充间隙, 这样依然可以按照第一种方式处理; 第二种就是单独处理
        for (int center = 0;center < chars.length * 2 - 1;center++){
            // 3. 这里的目的就是为了兼顾两种情况, 我把另外一种写法写出来, 应该会比较好理解
            int left = center / 2, right = left + center % 2;
            while (left >= 0 && right <= chars.length - 1){
                if (chars[left] != chars[right]) break;
                left--;
                right++;
            }
            String sub = str.substring(left + 1, right);
            if (sub.length() > maxLength){
                maxLength = Math.max(maxLength, sub.length());
                result = sub;
            }
        }
        return result;
    }

    private static String longestPalindrome2(String str){
        int start = 0, end = 0;
        for (int center = 0;center < str.length();center++){
            // 1. 中心点为当前点
            int firstLength = expandAroundCenter(str, center, center);
            // 2. 中心点在缝隙中
            int secondLength = expandAroundCenter(str, center, center + 1);
            // 3. 最大长度
            int maxLength = Math.max(firstLength, secondLength);
            if (maxLength > end - start + 1){
                // 4. 这里长度减一再除二的目的是为了确保中心点在缝隙中的情况是正确的
                start = center - (maxLength - 1) / 2;
                end = center + maxLength / 2;
            }
        }
        return str.substring(start, end + 1);
    }

    private static int expandAroundCenter(String str, int left, int right){
        while (left >= 0 && right <= str.length() - 1){
            if (str.charAt(left) != str.charAt(right))
                break;
            left--;
            right++;
        }
        return right - left - 1;
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
     * <h3>注: 其余几个回文子串的动态规划基本和这个题完全相同</h3>
     * @return 最长回文子串
     */
    private static String longestPalindrome3(String str){
        int begin = 0, maxLength = 0;
        char[] chars = str.toCharArray();
        // 注: 如果不知道表有多大, 那么就先试试默认长度
        boolean[][] dp = new boolean[chars.length][chars.length];
        // 1. 这次尝试从上向下填表, 之前是从下向上填表, 其实只需要填一半的表
        for (int right = 0;right < dp.length;right++){
            for (int left = 0;left < right + 1;left++){
                // 2. 首先需要判断两个最外层的字符是否相等
                if (chars[left] == chars[right]){
                    // 3. 然后根据表可以知道内部的子串是否是回文子串; 还需要注意不要越界
                    dp[left][right] = left + 1 > right - 1 || dp[left + 1][right - 1];
                    // 4. 计算最大长度, 之后用于截取字符串
                    if (dp[left][right] && maxLength < right - left + 1){
                        begin = left;
                        maxLength = right - left + 1;
                    }
                }
            }
        }

        return str.substring(begin, begin + maxLength);
    }

    /**
     * <h3>暴力递归 + 记忆化搜索</h3>
     * <h3>注: 字符串的动态规划题目通常都是二维表, 因为需要记录起始和结尾</h3>
     */
    private static String longestPalindrome4(String str){
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
        // 1. 越界肯定代表这个是回文字符串
        if (left >= right) return true;
        // 2. 记忆化搜索
        if (dp[left][right] != null) return dp[left][right];
        if (str.charAt(left) == str.charAt(right)){
            return dp[left][right] = dfs(str, left + 1, right - 1, dp);
        }
        return dp[left][right] = false;
    }


    private static String longestPalindrome5(String str){
        // 0. 基本变量
        int center = -1;
        int rightBound = -1;
        int maxRadius = 0;
        String result = "";
        // 1. Manacher 字符串
        String manacher = getManacher(str);
        int[] radius = new int[manacher.length()];
        // 2. 开始中心扩展算法
        for (int index = 0;index < manacher.length();index++){
            // 3. 如果索引在右边界内, 分为三种情况
            StringBuilder palindrome = new StringBuilder();
            if (index < rightBound){
                // 4. 对称点的回文半径和到右边界的距离=比较 => 如果小于, 那么当前点的回文半径就是这个; 否则就是到右边界距离
                if (radius[center * 2 - index] < rightBound - index){
                    radius[index] = radius[center * 2 - index];
                }else{
                    radius[index] = rightBound - index;
                }
                palindrome.append(manacher, index - radius[index] + 1, index + radius[index]);
            }else{
                // 5. 如果当前点在右边界的外面, 那么回文半径就是 1
                radius[index] = 1;
                palindrome.append(manacher.charAt(index));
            }
            // 6. 继续扩展增加回文半径长度
            while (index - radius[index] >= 0 && index + radius[index] <= manacher.length() - 1){
                char left = manacher.charAt(index - radius[index]);
                char right = manacher.charAt(index + radius[index]);
                if (left == right){
                    radius[index]++;
                    palindrome.insert(0,left);
                    palindrome.insert(palindrome.length(), right);
                }else{
                    break;
                }
            }
            // 7. 如果当前点的回文半径超过了右边界就需要更新
            if (rightBound < index + radius[index]){
                rightBound = index + radius[index];
                center = index;
            }
            // 8. 更新最大的回文半径
            if (maxRadius < radius[index]){
                maxRadius = radius[index];
                result = palindrome.toString();
            }
        }
        // 9. 去掉最长回文子串中的分隔符
        return back(result);
    }

    private static String back(String str){
        String[] strings = str.split("#");
        StringBuilder sb = new StringBuilder();
        for(int i = 0;i < strings.length;i++){
            sb.append(strings[i]);
        }
        return sb.toString();
    }

    private static String getManacher(String str){
        StringBuilder sb = new StringBuilder();
        for (int index = 0;index < str.length();index++){
            sb.append("#").append(str.charAt(index));
        }
        return sb.append("#").toString();
    }


    /**
     * <h3>思路: 回文子串</h3>
     */
    private static int countSubstrings1(String str){
        int count = 0;
        char[] chars = str.toCharArray();
        for (int center = 0;center < chars.length * 2 - 1;center++){
            int left = center / 2, right = left + center % 2;
            while (left >= 0 && right <= chars.length - 1){
                if (chars[left] != chars[right]) break;
                left--;right++;
                // 注: 直接把每个子回文串加进去就行
                count++;
            }
        }
        return count;
    }

    private static int countSubstrings2(String str){
        int count = 0;
        char[] chars = str.toCharArray();
        boolean[][] dp = new boolean[chars.length][chars.length];
        for (int right = 0;right < dp.length;right++){
            for (int left = 0;left < right + 1;left++){
                if (chars[left] == chars[right]){
                    dp[left][right] = left + 1 > right - 1 || dp[left + 1][right - 1];
                    if (dp[left][right])
                        count++;
                }
            }
        }
        return count;
    }

    private static int countSubstrings3(String str){
        int center = -1;
        int rightBound = -1;
        int result = 0;
        String manacher = getManacher(str);
        int[] radius = new int[manacher.length()];
        for(int index = 0;index < manacher.length();index++){
            radius[index] = index < rightBound ? Math.min(radius[center * 2 - index], rightBound - index): 1;
            while(index - radius[index] >= 0 && index + radius[index] <= manacher.length() - 1){
                char left = manacher.charAt(index - radius[index]);
                char right = manacher.charAt(index + radius[index]);
                if(left == right)radius[index]++;
                else break;
            }
            if(rightBound < index + radius[index]){
                center = index;
                rightBound = index + radius[index];
            }
            // 注: 回文半径 ÷ 2 之后得到真正的回文半径
            result += radius[index] / 2;
        }
        return result;
    }

    /**
     * <h3>思路: 分割回文子串</h3>
     */
    private static List<List<String>> partitionSubstrings(String str){
        char[] chars = str.toCharArray();
        // 1. 动态规划可以知道哪些子串是回文子串
        boolean[][] dp = new boolean[chars.length][chars.length];
        for (int right = 0;right < dp.length;right++){
            for (int left = 0;left < right + 1;left++){
                dp[left][right] = left + 1 > right - 1 || dp[left + 1][right - 1];
            }
        }
        // 2. 回溯就可以借助动态规划直接判断子串
        List<String> result = new LinkedList<>();
        List<List<String>> results = new LinkedList<>();
        dfs(str, dp, 0, result, results);
        return results;
    }

    private static void dfs(String str, boolean[][] dp, int left, List<String> result, List<List<String>> results){
        if (left == str.length()){
            results.add(new LinkedList<>(result));
            return;
        }
        for (int right = left;right < str.length();right++){
            if (dp[left][right]){
                result.add(str.substring(left, right + 1));
                dfs(str, dp, right + 1, result, results);
                result.remove(result.size() - 1);
            }
        }
    }

}
