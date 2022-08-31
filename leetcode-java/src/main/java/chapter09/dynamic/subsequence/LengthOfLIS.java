package chapter09.dynamic.subsequence;

/**
 * <h3>最长上升子序列</h3>
 */
public class LengthOfLIS {

    /**
     * <h3>动态规划</h3>
     */
    private static int lengthOfLIS1(int[] nums){
        int maxLength = 0;
        int[] dp = new int[nums.length + 1];
        for (int index = nums.length - 1;index >= 0;index--){
            dp[index] = 1;
            for (int start = index + 1;start < nums.length;start++){
                if (nums[index] < nums[start]){
                    dp[index] = Math.max(dp[index], dp[start] + 1);
                }
            }
            maxLength = Math.max(maxLength, dp[index]);
        }
        return maxLength;
    }

    /**
     * <h3>递归</h3>
     */
    private static int lengthOfLIS2(int[] nums){
        int maxLength = 0;
        int[] dp = new int[nums.length];
        for (int index = 0;index < nums.length;index++){
            maxLength = Math.max(maxLength, lengthOfLIS2(nums, index, dp));
        }
        return maxLength;
    }

    private static int lengthOfLIS2(int[] nums, int start, int[] dp){
        if (start == nums.length)
            return 0;
        if (dp[start] != 0)
            return dp[start];
        dp[start] = 1;
        for (int end = start + 1;end < nums.length;end++){
            // 2. 如果后面的值比当前值大, 那么就以下个值为起点继续判断
            if (nums[end] > nums[start]){
                // 3. 这里不要判断之后立刻跳出循环, 因为很有可能选择这个数会导致子序列变小
                dp[start] = Math.max(dp[start], lengthOfLIS2(nums, end, dp)) + 1;
            }
        }
        return dp[start];
    }

}
