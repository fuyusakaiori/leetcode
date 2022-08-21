package chapter05.windows;

import java.util.HashMap;
import java.util.Map;

/**
 * <h3>字符串排列</h3>
 */
public class StringArrangement {


    private static boolean checkInclusion(String s1, String s2) {
        if(s1.length() > s2.length())
            return false;
        // 1. 滑动窗口
        int left = 0, right = 0;
        Map<Character, Integer> window = new HashMap<>();
        Map<Character, Integer> cnt = new HashMap<>();
        // 2. 初始化
        for(int idx = 0;idx < s1.length();idx++){
            char ch = s1.charAt(idx);
            cnt.put(ch, cnt.getOrDefault(ch, 0) + 1);
        }
        // 3. 移动窗口
        int valid = 0;
        while(right < s2.length()){
            char rch = s2.charAt(right++);
            if(cnt.containsKey(rch)){
                window.put(rch, window.getOrDefault(rch, 0) + 1);
                if(window.get(rch).intValue() == cnt.get(rch).intValue())
                    valid++;
            }
            // 4. 滑动窗口的长度已经达到子串长度, 可以判断是否符合要求
            if(right - left == s1.length()){
                // 5. 如果有效值等于目标值, 那么直接返回
                if(valid == cnt.size())
                    return true;
                // 6. 移除左边界的元素
                char lch = s2.charAt(left++);
                if(cnt.containsKey(lch)){
                    if(window.get(lch).intValue() == cnt.get(lch).intValue())
                        valid--;
                    window.put(lch, window.get(lch) - 1);
                }
            }
        }
        return false;
    }

}
