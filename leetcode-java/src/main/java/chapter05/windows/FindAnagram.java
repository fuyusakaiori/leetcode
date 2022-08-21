package chapter05.windows;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <h3>找到字符串中所有字母异位词</h3>
 */
public class FindAnagram {

    public static List<Integer> findAnagrams(String first, String second) {
        if (first.length() < second.length())
            return new LinkedList<>();
        List<Integer> anagrams = new LinkedList<>();
        char[] fArray = first.toCharArray();
        char[] sArray = second.toCharArray();
        // 1. 滑动窗口
        int left = 0, right = 0;
        Map<Character, Integer> window = new HashMap<>();
        Map<Character, Integer> cnt = new HashMap<>();
        // 2. 初始化
        for (int idx = 0; idx < sArray.length; idx++) {
            cnt.put(sArray[idx], cnt.getOrDefault(sArray[idx], 0) + 1);
        }
        // 3. 移动窗口
        int valid = 0;
        while (right < fArray.length){
            char rch = fArray[right++];
            if (cnt.containsKey(rch)){
                window.put(rch, window.getOrDefault(rch, 0) + 1);
                if (window.get(rch).intValue() == cnt.get(rch).intValue())
                    valid++;
            }
            if (right - left == sArray.length){
                if (valid == cnt.size())
                    anagrams.add(left);
                char lch = fArray[left++];
                if (cnt.containsKey(lch)){
                    if (window.get(lch).intValue() == cnt.get(lch).intValue())
                        valid--;
                    window.put(lch, window.get(lch) - 1);
                }
            }
        }
        return anagrams;
    }

}
