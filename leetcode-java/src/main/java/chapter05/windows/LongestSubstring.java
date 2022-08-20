package chapter05.windows;

import java.util.HashSet;
import java.util.Set;

/**
 * <h3>无重复字符最长子串</h3>
 * <h3>1. 哈希集合 + 滑动窗口: </h3>
 * <h3>1.1 双重循环</h3>
 * <h3>1.2 单次循环</h3>
 * <h3>3. 数组 + 滑动窗口</h3>
 * <h3>4. 动态规划</h3>
 */
public class LongestSubstring {

    /**
     * <h3>哈希表</h3>
     */
    private static int lengthOfLongestSubstring1(String str){
        // 1. 左右边界指针
        int left = 0, right = 0;
        // 2. 滑动窗口
        Set<Character> window = new HashSet<>();
        // 3. 遍历字符串
        int maxLength = 0;
        char[] sArray = str.toCharArray();
        while (right < sArray.length){
            char ch = sArray[right++];
            while (!window.add(ch))
                window.remove(sArray[left++]);
            maxLength = Math.max(maxLength, right - left);
        }
        return maxLength;
    }

    /**
     * <h3>数组</h3>
     */
    private static int lengthOfLongestSubstring2(String str){
        // 1. 左右指针
        int left = 0, right = 0;
        // 2. 滑动窗口
        boolean[] window = new boolean[128];
        // 3. 遍历字符串
        int maxLength = 0;
        char[] sArray = str.toCharArray();
        while (right < sArray.length){
            int idx = sArray[right++] - ' ';
            while (window[idx])
                window[sArray[left++] - ' '] = false;
            window[idx] = true;
            maxLength = Math.max(maxLength, right - left);
        }
        return maxLength;
    }


}
