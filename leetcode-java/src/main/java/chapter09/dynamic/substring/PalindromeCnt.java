package chapter09.dynamic.substring;

/**
 * <h3>回文字符串</h3>
 */
public class PalindromeCnt {

    /**
     * <h3>中心扩展算法</h3>
     */
    public static int countSubstrings1(String s) {
        int cnt = 0;
        char[] chars = s.toCharArray();
        // 1. 每个字符为中心结点向两侧遍历
        for(int idx = 0;idx < chars.length;idx++){
            // 2. 奇数长度的回文字符串
            int firstCnt = getPalindromeCnt(idx, idx, chars);
            // 3. 偶数长度的回文字符串
            int secondCnt = getPalindromeCnt(idx, idx + 1, chars);
            // 4. 叠加到总数量上
            cnt += firstCnt + secondCnt;
        }
        return cnt;
    }

    public static int getPalindromeCnt(int left, int right, char[] chars){
        int cnt = 0;
        while(left >= 0 && right <= chars.length - 1){
            if(chars[left--] != chars[right++])
                return cnt;
            cnt++;
        }
        return cnt;
    }

    /**
     * <h3>动态规划</h3>
     */
    public static int countSubstring2(String s){
        char[] chars = s.toCharArray();
        // 1. 表结构
        boolean[][] dp = new boolean[chars.length][chars.length];
        // 2. 填表
        int cnt = 0;
        for (int left = chars.length - 1;left >= 0;left--){
            for (int right = left;right < chars.length;right++){
                if (chars[left] == chars[right])
                    dp [left][right] = left + 1 >= right - 1 || dp[left + 1][right - 1];
                if (dp[left][right])
                    cnt++;
            }
        }
        return cnt;
    }
}
