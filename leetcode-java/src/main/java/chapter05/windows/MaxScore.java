package chapter05.windows;

/**
 * <h3>可获得的最大点数</h3>
 */
public class MaxScore {

    /**
     * <h3>左边的数字 "放到" 右边去形成连续的区间</h3>
     * <h3>注: 理论可以采用动态规划, 不过会爆内存溢出</h3>
     */
    private static int maxScore(int[] cardPoints, int cnt){
        int length = cardPoints.length;
        // 1. 滑动窗口
        int window = 0;
        // 2. 初始化
        for (int idx = length - 1; idx > length - cnt - 1; idx--) {
            window += cardPoints[idx];
        }
        int maxScore = window;
        for (int idx = length;idx < length + cnt;idx++){
            window = window + cardPoints[idx % length] - cardPoints[idx - cnt];
            maxScore = Math.max(maxScore, window);
        }
        return maxScore;
    }

    /**
     * <h3>子数组最大平均值</h3>
     * <h3>注: 两个题没有本质区别</h3>
     */
    private static double findMaxAverage(int[] nums, int cnt){
        // 1. 滑动窗口
        int window = 0;
        // 2. 初始化
        for (int idx = 0; idx < cnt; idx++) {
            window += nums[idx];
        }
        double maxSum = window;
        // 3. 移动窗口
        for (int idx = cnt;idx < nums.length;idx++){
            window = window - nums[idx - cnt] + nums[idx];
            maxSum = Math.max(maxSum, window);
        }
        return maxSum / cnt;
    }


}
