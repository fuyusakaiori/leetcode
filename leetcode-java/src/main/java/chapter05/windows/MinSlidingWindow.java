package chapter05.windows;

import java.util.HashMap;
import java.util.Map;

/**
 * <h3>最小覆盖子串</h3>
 */
public class MinSlidingWindow
{

    /**
     * <h3>思路: 最小覆盖子串</h3>
     * <h3>1. 首先需要两个哈希表: </h3>
     * <h3>1.1 cnt: 记录需要覆盖的子串的每种字符的数量</h3>
     * <h3>1.2 window: 滑动窗口 记录遍历过程中的子串符合要求的每种字符的数量</h3>
     * <h3>2. 开始遍历: </h3>
     * <h3>2.1 右指针不停滑动, 将所有满足条件的字符全部放入 window</h3>
     * <h3>2.2 如果满足条件的字符数量已经达到覆盖子串的字符数量, 那么就认为这个字符已经满足要求, 增加有效值</h3>
     * <h3>2.3 如果有效值已经等于 cnt 的长度, 那么就认为当前子串已经完成覆盖, 但不一定是最小的</h3>
     * <h3>2.4 开始移动左指针, 如果满足条件的字符数量小于覆盖子串的字符数量, 那么就认为没有满足条件, 有效值减少</h3>
     * <h3>3. 退出循环</h3>
     * <h3>本质 cnt 哈希表的作用就是左指针移动的条件</h3>
     */
    private static String minWindow(String first, String second){
        // 1. 如果第一个字符串长度小于第二个, 直接返回
        if (first.length() < second.length())
            return "";
        // 2. 字符串转换成字符数组
        char[] fArray = first.toCharArray();
        char[] sArray = second.toCharArray();
        // 3. 滑动窗口
        int left = 0, right = 0;
        Map<Character, Integer> window = new HashMap<>();
        // 4. 停止条件
        Map<Character, Integer> cnt = new HashMap<>();
        for (int idx = 0; idx < sArray.length; idx++) {
            cnt.put(sArray[idx], cnt.getOrDefault(sArray[idx], 0) + 1);
        }
        // 5. 遍历字符数组
        int valid = 0;
        String result = "";
        while (right < fArray.length){
            char rch = fArray[right++];
            // 6. 如果字符属于目标字符串, 那么就放入窗口中统计
            if (cnt.containsKey(rch)){
                // 7. 如果滑动窗口的字符已经达到目标字符串中的数量, 那么有效值增加
                window.put(rch, window.getOrDefault(rch, 0) + 1);
                if (window.get(rch).intValue() == cnt.get(rch).intValue())
                    valid++;
            }
            // 8. 如果有效值已经达到目标值, 那么就可以结算长度
            while (valid == cnt.size()){
                if ("".equals(result) || result.length() > right - left)
                    result = first.substring(left, right);
                // 9. 移除滑动窗口中的字符, 直到有效值和目标值不同: 缩小覆盖字符串的长度
                char lch = fArray[left++];
                if (cnt.containsKey(lch)){
                    if (window.get(lch).intValue() == cnt.get(lch).intValue())
                        valid--;
                    window.put(lch, window.get(lch) - 1);
                }
            }
        }
        return result;
    }

}
