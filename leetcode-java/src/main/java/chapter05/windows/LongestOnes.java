package chapter05.windows;

/**
 * <h3>最大连续 1 的个数</h3>
 */
public class LongestOnes {

    private static int longestOnes(int cnt, int[] nums){
        int maxLength = 0;
        // 1. 滑动窗口
        int left = 0, right = 0;
        // 2. 移动窗口
        while (right < nums.length){
            if (nums[right++] == 0){
                while (cnt == 0){
                    cnt += nums[left++] == 0 ? 1: 0;
                }
                cnt--;
            }
            maxLength = Math.max(maxLength, right - left);
        }
        return maxLength;
    }

}
