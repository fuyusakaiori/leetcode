package chapter07.prefix;

/**
 * <h2>差分数组</h2>
 * <h3>1. 航班预订统计</h3>
 * <h3>2. K 连续位的最小反转次数</h3>
 */
public class DifferenceArray {

    /**
     * <h3>思路: 航班预订统计</h3>
     * <h3>差分数组 计算 前缀和 => 最终答案</h3>
     * <h3>注: 差分数组索引</h3>
     * <h3>nums[i] - nums[i - 1] => difference[i]</h3>
     */
    private static int[] corpFlightBookings(int[][] bookings, int count){
        // 1. 差分数组: 数组长度增加一的目的是因为可能在设置差分的时候会越界
        int[] difference = new int[count + 1];
        for (int index = 0;index < difference.length;index++){
            int increment = bookings[index][2];
            // 注: 这里区间不是按照索引给出的, 要转换下
            int left = bookings[index][0] - 1, right = bookings[index][1] - 1;
            // 注: 区间增加, 前半区的差值显然增加, 左边界恰好可以索引到前半区
            difference[left] += increment;
            // 注: 区间增加, 后半区的差值显然减少, 右边界只能索引到当前区域, 所以要加一之后可以索引到右半区域
            difference[right + 1] = increment;
        }
        // 2. 前缀和
        int[] total = new int[count];
        // 注: 差分数组第一个差值就是前缀和第一个值, 因为第一个值前面没有元素和它作差, 所以就是自己
        total[0] = difference[0];
        for (int index = 1; index < total.length; index++){
            total[index] = difference[index] + total[index - 1];
        }
        return total;
    }

}
