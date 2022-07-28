package chapter05;

import java.util.HashSet;
import java.util.Set;

/**
 * <h2>最长连续子序列</h2>
 */
public class LongestConsecutive {

    /**
     * <h3>思路: 最长连续子序列</h3>
     * <h3>1、这个题和动态规划关系不大, 看着像是最优解问题, 但是时间复杂度限死之后就没有什么关系了</h3>
     * <h3>2、借助哈希集合或者哈希表来去重, 重复的数字没有存在的必要, 后者好像在使用的时候效率比较低</h3>
     */
    private static int longestConsecutive1(int[] nums){
        // 1. 记录目前出现过的数字
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }
        // 2. 开始循环检查
        int maxLength = 0;
        // 注: 不要去循环数组, 数组中有很多重复元素, 这些元素是没有必要再走的, 会浪费很多时间
        for (int num : set) {
            // 3. 如果这个数不是子序列的起点的话就不要进行遍历, 避免重复遍历
            int length = 1, next = num;
            if (!set.contains(num - 1)) {
                while (set.contains(next + 1)) {
                    length++;
                    next++;
                }
            }
            maxLength = Math.max(maxLength, length);
        }
        return maxLength;
    }

    /**
     * <h3>注: 这个题还可以借助并查集来做</h3>
     */
    private static int longestConsecutive2(int[] nums){
        return 0;
    }

}
