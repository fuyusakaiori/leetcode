package chapter09.dynamic.subarray;

import java.util.Arrays;

/**
 * <h3>最长重复子数组</h3>
 */
public class MaxSubArrayLength {

    /**
     * <h3>动态规划: 从左到右</h3>
     */
    public static int findLength1(int[] nums1, int[] nums2){
        // 1. 表结构: dp[i][j] ?
        int[][] dp = new int[nums1.length + 1][nums2.length + 1];
        // 2. 填表
        int maxLength = 0;
        for(int first = 1;first <= nums1.length;first++){
            for(int second = 1;second <= nums2.length;second++){
                if (nums1[first] == nums2[second])
                    dp[first][second] =  dp[first - 1][second - 1] + 1;
                maxLength = Math.max(maxLength, dp[first][second]);
            }
        }
        return maxLength;
    }

    /**
     * <h3>递归</h3>
     */
    public static int findLength2(int[] nums1, int[] nums2) {
        // 1. 备忘录
        int[][] dp = new int[nums1.length][nums2.length];
        for(int[] array : dp){
            Arrays.fill(array, -1);
        }
        // 2. 依次从每个结点开始遍历
        int maxLength = 0;
        for(int first = 0;first < nums1.length;first++){
            for(int second = 0;second < nums2.length;second++){
                maxLength = Math.max(maxLength, findLength2(first, nums1, second, nums2, dp));
            }
        }
        return maxLength;
    }

    public static int findLength2(int first, int[] nums1, int second, int[] nums2, int[][] dp){
        if(first == nums1.length || second == nums2.length)
            return 0;
        if(dp[first][second] != -1)
            return dp[first][second];
        if(nums1[first] == nums2[second])
            return dp[first][second] = 1 + findLength2(first + 1, nums1, second + 1, nums2, dp);
        return dp[first][second] = 0;
    }
}
