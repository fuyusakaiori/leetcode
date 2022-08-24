package chapter05.windows;

import java.util.*;

/**
 * <h3>滑动窗口最大值</h3>
 */
public class MaxSlidingWindow {

    /**
     * <h3>优先权队列</h3>
     */
    public static int[] maxSlidingWindow1(int[] nums, int cnt) {
        // 1. 最大值数组的长度
        int[] maxValue = new int[nums.length - cnt + 1];
        // 2. 滑动窗口: 优先权队列 记录数组的索引并依据元素大小排序 -> 记录索引的目的是判断最大值是否已经在滑动窗口之外
        PriorityQueue<int[]> window = new PriorityQueue<>((a, b) -> b[1] - a[1]);
        // 3. 初始化窗口大小: 窗口大小固定的最好先初始化, 不要合成单个循环
        for(int idx = 0;idx < cnt;idx++){
            window.offer(new int[]{idx, nums[idx]});
        }
        // 4. 获取最大值
        maxValue[0] = Objects.requireNonNull(window.peek())[1];
        // 5. 移动窗口
        for(int idx = cnt;idx < nums.length;){
            // 6. 先将新元素加入到优先权队列中
            window.offer(new int[]{idx, nums[idx++]});
            // 7. 更新优先权队列中的最大值
            while(!window.isEmpty() && window.peek()[0] < idx - cnt)
                window.poll();
            // 8. 更新最值
            maxValue[idx - cnt] = Objects.requireNonNull(window.peek())[1];
        }
        return maxValue;
    }

    /**
     * <h3>单调队列</h3>
     */
    public static int[] maxSlidingWindow2(int[] nums, int cnt){
        // 1. 最大值数组
        int[] maxValue = new int[nums.length - cnt + 1];
        // 2. 滑动窗口
        Deque<Integer> window = new LinkedList<>();
        // 3. 移动窗口
        for (int idx = 0; idx < nums.length;) {
            while (!window.isEmpty() && nums[window.peekLast()] < nums[idx])
                window.pollLast();
            window.offerLast(idx++);
            if (window.peekFirst() < idx - cnt)
                window.pollFirst();
            if (idx >= cnt)
                maxValue[idx - cnt] = nums[window.peekFirst()];
        }
        return maxValue;
    }
}
