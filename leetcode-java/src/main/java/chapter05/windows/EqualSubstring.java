package chapter05.windows;

/**
 * <h3>尽可能使字符串相等</h3>
 */
public class EqualSubstring {

    private static int equalSubstring(String s, String t, int maxCost) {
        // 1. 开销数组
        int[] costs = new int[s.length()];
        // 2. 初始化
        for(int idx = 0;idx < costs.length;idx++){
            costs[idx] = Math.abs(s.charAt(idx) - t.charAt(idx));
        }
        // 3. 滑动窗口
        int window = 0;
        int left = 0, right = 0;
        // 4. 移动窗口
        int maxLength = 0;
        while(right < costs.length){
            window += costs[right++];
            while(window > maxCost){
                window -= costs[left++];
            }
            maxLength = Math.max(maxLength, right - left);
        }
        return maxLength;
    }

}
