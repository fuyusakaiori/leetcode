package chapter05.windows;

/**
 * <h3>替换后最长重复字符</h3>
 */
public class CharacterReplacement {

    /**
     * <h3>暴力解</h3>
     * <h3>本质就是转换成最大连续 1 的个数来求解</h3>
     */
    private static int characterReplacement1(String s, int k){
        int maxLength = 0;
        // 1. 获取所有可以替换的字符
        int[] map = new int[26];
        char[] chars = s.toCharArray();
        for (char ch : chars) {
            map[ch - 'A']++;
        }
        // 2. 每个出现的字符都作为替换字符进行滑动窗口
        for (int idx = 0; idx < map.length; idx++) {
            if (map[idx] == 0) continue;
            // 3. 滑动窗口
            int cnt = k;
            int left = 0, right = 0;
            while (right < chars.length) {
                if (chars[right++] - 'A' != idx) {
                    while (cnt == 0)
                        cnt += chars[left++] - 'A' != idx ? 1 : 0;
                    cnt--;
                }
                maxLength = Math.max(maxLength, right - left);
            }
        }
        return maxLength;
    }

    /**
     * <h3>优化解</h3>
     * <h3>停止条件和最大连续 1 的个数非常相似</h3>
     */
    private static int characterReplacement2(String s, int cnt){
        // 1. 滑动窗口
        int historyMaxCnt = 0;
        int left = 0, right = 0;
        int[] map = new int[26];
        // 2. 移动窗口
        while (right < s.length()){
            int idx = s.charAt(right++) - 'A';
            // 3. 字符对应的次数增加
            map[idx]++;
            // 4. 记录在窗口中重复出现次数最多的字符
            historyMaxCnt = Math.max(historyMaxCnt, map[idx]);
            // 5. 验证当前的字符串长度是否超过 重复字符出现的最大值 + 可以替换的次数
            if (right - left - historyMaxCnt > cnt)
                map[s.charAt(left++) - 'A']--;
        }
        return right - left;
    }
}
