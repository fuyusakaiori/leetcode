package chapter05.windows;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <h3>串联所有单词的子串</h3>
 * <h3>注: 本质和字母异位词类似</h3>
 */
public class FindSubstring {

    /**
     * <h3>滑动窗口</h3>
     */
    public List<Integer> findSubstring(String s, String[] words) {
        // 1. 获取每个单词的长度和滑动窗口的长度
        int wordLength = words[0].length(), windowLength = words.length;
        // 2. 初始化
        Map<String, Integer> cnt = new HashMap<>();
        for (String word : words) {
            cnt.put(word, cnt.getOrDefault(word, 0) + 1);
        }
        // 3. 前 wordLength 都作为起点来移动窗口
        // 3.1 如果只从 0 开始移动窗口, 那么就会忽略有些合法的情况
        // 3.2 起点坐标对单词长度取模
        List<Integer> result = new LinkedList<>();
        for (int idx = 0; idx < wordLength; idx++) {
            // 4. 滑动窗口
            int valid = 0;
            int left = idx, right = idx;
            Map<String, Integer> window = new HashMap<>();
            // 5. 开始移动窗口
            while (right + wordLength <= s.length()){
                String word = s.substring(right, right + wordLength);
                right = right + wordLength;
                if (cnt.containsKey(word)){
                    window.put(word, window.getOrDefault(word, 0) + 1);
                    if (window.get(word).intValue() == cnt.get(word).intValue())
                        valid++;
                }
                if (right - left == wordLength * windowLength){
                    if (valid == cnt.size())
                        result.add(left);
                    String removed = s.substring(left, left + wordLength);
                    left = left + wordLength;
                    if (cnt.containsKey(removed)){
                        if (window.get(removed).intValue() == cnt.get(removed).intValue())
                            valid--;
                        window.put(removed, window.get(removed) - 1);
                    }
                }
            }
        }
        return result;
    }

}
