package chapter05.windows;

/**
 * <h3>最小长度子数组</h3>
 */
public class MinSubArrayLength {

    private static int minSubArrayLen(int target, int[] nums) {
        int currentSum = 0, minLength = Integer.MAX_VALUE;
        // 1. 滑动窗口
        int left = 0, right = 0;
        // 2. 移动窗口
        while (right < nums.length) {
            currentSum += nums[right++];
            while (currentSum >= target) {
                minLength = Math.min(minLength, right - left);
                currentSum -= nums[left++];
            }
        }
        return minLength == Integer.MAX_VALUE ? 0 : minLength;
    }

}
