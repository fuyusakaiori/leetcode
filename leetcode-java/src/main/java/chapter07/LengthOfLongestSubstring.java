package chapter07;

import java.util.HashSet;
import java.util.Set;

/**
 * <h2>无重复字符的最长子串</h2>
 * <p>1. 给定一个字符串, 然后寻找一个最长的子串</p>
 * <p>2. 要求这个子串中的字符不可以重复出现</p>
 * <p>3. 问这个子串的最长的长度是多少</p>
 */
public class LengthOfLongestSubstring
{

    public static void main(String[] args)
    {

    }

    /**
     * <p>字符串和数组经典技巧: 滑动窗口</p>
     * <p>1. 滑动窗口的本质实际上是使用两个指针 + 某种数据结构</p>
     * <p>1.1 也就是左右边界指针, 维护一个类似窗口的结构</p>
     * <p>1.2 某种数据结构就是记录窗口中的内容</p>
     * <p>2. 只要左指针向右移动, 窗口内容就会减少, 只要右指针向右移动, 窗口内容就会增多</p>
     * <p>3. 存储窗口内容的数据结构可以是各种各样的, 比如双端队列、哈希表、数组等等, 根据题目具体情况选择</p>
     * <p>核心: 滑动窗口的实质是一种贪心策略, 其目的就是希望窗口中的内容能够是最优解, 如果不是就缩小窗口</p>
     * @return 最长子串的长度
     */
    private static int lengthOfLongestSubstring(String str){
        int maxLength = 0;
        // 0. 维护窗口的两个指针
        int left = 0;
        int right = 0;
        // 1. 数据结构: 这里采用哈希表而不是双端队列
        // 1.1 因为需要检查新来的字符是否已经出现, 所以使用哈希表查重的速度远快于双端队列
        // 1.2 只要检查到新来的字符重复, 那么我们就一直弹出哈希表中的内容, 直到重复的内容被弹出, 再加入新的
        // 1.3 不过在弹出之前需要记录下当前字符串的长度
        Set<Character> set = new HashSet<>();
        // 2. 开始移动窗口
        for (right = 0;right < str.length();right++){
            // 2.1 如果不包含, 那么就直接加入集合
            if (!set.contains(str.charAt(right))){
                set.add(str.charAt(right));
            }
            else{
                // 2.2 记录当前子串的长度
                maxLength = Math.max(maxLength, set.size());
                // 2.3 开始弹出哈希表中的内容, 直到出现相似的
                while (str.charAt(right) != str.charAt(left)){ set.remove(str.charAt(left++)); }
                // 2.4 此时左指针和右指针指向的是相同内容, 所以左指针还必须向右移动一次
                left++;
            }
        }

        return Math.max(maxLength, set.size());
    }

    /**
     * <p>1. 评论区提供了一种没有使用哈希表的方式而是使用的数组</p>
     * <p>2. 这个 ASCII 数组也是常用的技巧</p>
     */
    private static int lengthOfLongestSubstring(String str, String method){
        int maxLength = 0;
        // 0. ASCII 码最大范围
        int[] last = new int[128];
        // 1. 窗口大小
        int left = 0;
        int right = 0;
        // 2. 开始遍历
        for (right = 0;right < str.length();right++){
            // 2.1 暂时保存字符
            char ch = str.charAt(right);
            // 2.2 检查这个字符上次出现的位置: 如果上次已经出现, 那么显然左边界就需要移动当前的位置来
            left = Math.max(last[ch], left);
            // 2.3 右边界实际上就是 index 的位置, 记录子串的长度
            maxLength = Math.max(right, right - left + 1);
            // 2.4 记录当前这个字符的出现的位置
            last[ch] = right + 1;
        }
        return maxLength;
    }


}
