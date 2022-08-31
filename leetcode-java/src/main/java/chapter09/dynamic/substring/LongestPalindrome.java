package chapter09.dynamic.substring;

/**
 * <h3>最长回文子串</h3>
 */
public class LongestPalindrome {

    /**
     * <h3>中心扩展算法: 奇数长度和偶数长度分开算</h3>
     */
    public static String longestPalindrome1(String s) {
        char[] chars = s.toCharArray();
        // 1. 最长回文子串的边界
        int start = 0, end = 0;
        // 2. 每个字符为中心结点向两边扩展
        for(int idx = 0;idx < chars.length;idx++){
            // 3. 奇数长度的最长回文
            int firstLength = getMaxLength(idx, idx, chars);
            // 4. 偶数长度的最长回文
            int secondLength = getMaxLength(idx, idx + 1, chars);
            // 5. 判断最长回文
            int maxLength = Math.max(firstLength, secondLength);
            if(maxLength > end - start + 1){
                start = idx - (maxLength - 1) / 2;
                end = idx + maxLength / 2;
            }
        }
        return s.substring(start, end + 1);
    }


    /**
     * <h3>中心扩展算法: 奇数长度和偶数长度一起算</h3>
     */
    public static String longestPalindrome2(String s){
        char[] chars = s.toCharArray();
        // 1. 字符串的边界
        int start = 0, end = 0;
        // 2. 遍历字符以每个字符为中点向两侧扩展
        for (int idx = 0; idx < 2 * chars.length - 1; idx++) {
            // 3. 根据中心结点计算起始的左右结点
            int left = idx / 2, right = left + idx % 2;
            // 4. 执行中心扩展算法
            int maxLength = getMaxLength(left, right, chars);
            // 5. 判断最长回文
            if (maxLength > end - start + 1){
                start = left - (maxLength - 1) / 2;
                end = right + (maxLength - 1) / 2;
            }
        }
        return s.substring(start, end + 1);
    }

    public static int getMaxLength(int left, int right, char[] chars){
        while(left >= 0 && right <= chars.length - 1){
            if(chars[left] != chars[right])
                break;
            left--;
            right++;
        }
        return right - left - 1;
    }

    /**
     * <h3>动态规划: 从下向上填</h3>
     */
    public static String longestPalindrome3(String s){
        char[] chars = s.toCharArray();
        // 1. 表结构 dp[i][j] 表示 i...j 的子串是否是回文字符串
        boolean[][] dp = new boolean[chars.length][chars.length];
        // 2. 填表
        int start = 0, end = 0;
        // 注: 只能从下向上填: 上侧的值是依赖于下侧的值
        for(int left = chars.length - 1;left >= 0;left--){
            for(int right = left;right < chars.length;right++){
                if(chars[left] == chars[right]){
                    dp[left][right] = left + 1 >= right - 1 || dp[left + 1][right - 1];
                }
                if(dp[left][right] && right - left >= end - start){
                    start = left;
                    end = right;
                }
            }
        }
        return s.substring(start, end + 1);
    }

    /**
     * <h3>递归</h3>
     */
    public static String longestPalindrome4(String s) {
        char[] chars = s.toCharArray();
        // 1. 表结构
        Boolean[][] dp = new Boolean[chars.length][chars.length];
        // 2. 每次子字符串都采用双指针进行校验
        int start = 0, end = 0;
        for(int left = 0;left < chars.length;left++){
            for(int right = left;right < chars.length;right++){
                if(isPalindrome(left, right, chars, dp) && right - left > end - start){
                    start = left;
                    end = right;
                }
            }
        }
        return s.substring(start, end + 1);
    }

    public static boolean isPalindrome(int left, int right, char[] chars, Boolean[][] dp){
        if(left > right)
            return true;
        if(dp[left][right] != null)
            return dp[left][right];
        if(chars[left] == chars[right])
            return dp[left][right] = isPalindrome(left + 1, right - 1, chars, dp);
        return dp[left][right] = false;
    }

}
