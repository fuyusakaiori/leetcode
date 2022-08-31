package chapter09.dynamic.subarray;

/**
 * <h3>最长上升子数组</h3>
 */
public class MaxSubArrayLCS {


    /**
     * <h3>动态规划: 从左到右</h3>
     */
    public static int findLengthOfLCIS1(int[] nums){
        // 1. 表结构: dp[i] 表示以 nums[i] 结尾的最大升序子数组
        int[] dp = new int[nums.length];
        // 2. basecase
        dp[0] = 1;
        // 3. 填表
        int maxLength = dp[0];
        for(int idx = 1;idx < nums.length;idx++){
            dp[idx] = nums[idx] <= nums[idx - 1] ? 1: dp[idx - 1] + 1;
            maxLength = Math.max(maxLength, dp[idx]);
        }
        return maxLength;
    }

    public int findLengthOfLCIS2(int[] nums) {
        int maxLength = 0;
        for(int idx = 0;idx < nums.length;idx++){
            maxLength = Math.max(maxLength, findLengthOfLCIS2(idx, nums));
        }
        return maxLength;
    }

    /**
     * <h3>递归</h3>
     */
    public int findLengthOfLCIS2(int idx, int[] nums){
        if(idx == nums.length)
            return 1;
        if(idx > 0 && nums[idx - 1] < nums[idx])
            return findLengthOfLCIS2(idx + 1, nums) + 1;
        return 1;
    }

    /**
     * <h3>贪心算法</h3>
     */
    private static int findLengthOfLCIS3(int[] nums){
        int maxLength = 0, start = 0;
        for (int index = 0;index < nums.length;index++){
            if (index != 0 && nums[index - 1] >= nums[index])
                start = index;
            maxLength = Math.max(maxLength, index - start + 1);
        }
        return maxLength;
    }

}
