package chapter05.windows;

/**
 * <h3>最大连续 1 的个数</h3>
 */
public class LongestOnes {

    private static int longestOnes1(int cnt, int[] nums){
        int maxLength = 0;
        // 1. 滑动窗口
        int left = 0, right = 0;
        // 2. 移动窗口
        while (right < nums.length){
            if (nums[right++] == 0){
                while (cnt == 0)
                    cnt += 1 - nums[left++];
                cnt--;
            }
            maxLength = Math.max(maxLength, right - left);
        }
        return maxLength;
    }

    private static int longestOnes2(int cnt, int[] nums){
        int maxLength = 0;
        // 1. 滑动窗口
        int window = 0;
        int left = 0, right = 0;
        // 2. 移动窗口
        while (right < nums.length){
            // 3. 如果是 0, 那么就代表变为 1
            window += 1 - nums[right++];
            // 4. 停止移动条件: 如果反转的和超过限制, 那么就移动左指针
            while (window > cnt)
                window -= 1 - nums[left++];
            maxLength = Math.max(maxLength, right - left);
        }
        return maxLength;
    }

}
