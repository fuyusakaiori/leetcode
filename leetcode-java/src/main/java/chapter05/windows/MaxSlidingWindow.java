package chapter05.windows;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.PriorityQueue;

/**
 * <h3>滑动窗口最大值</h3>
 */
public class MaxSlidingWindow {

    /**
     * <h3>优先权队列</h3>
     */
    public static int[] maxSlidingWindow1(int[] nums, int k) {
        int[] maxValue = new int[nums.length - k + 1];
        // 1. 滑动窗口
        PriorityQueue<int[]> queue = new PriorityQueue<>((a, b) -> b[1] - a[1]);
        // 2. 初始化
        for (int idx = 0;idx < k;idx++){
            queue.offer(new int[]{idx, nums[idx]});
        }
        // 3. 移动窗口
        maxValue[0] = queue.peek() != null ? queue.peek()[1] : 0;
        for(int idx = k;idx < nums.length;idx++){
            queue.offer(new int[]{idx, nums[idx]});
            while(!queue.isEmpty() && queue.peek()[0] <= idx - k)
                queue.poll();
            maxValue[idx - k + 1] = queue.peek() != null ? queue.peek()[1] : 0;
        }
        return maxValue;
    }

    /**
     * <h3>单调队列</h3>
     */
    public static int[] maxSlidingWindow2(int[] nums, int k){
        // 1。 滑动窗口必然需要准备的变量
        int left = 0, right = 0;
        int[] maxArray = new int[nums.length - k + 1];
        // 2. 大多数的滑动窗口都需要相应的数据结构
        Deque<Integer> window = new ArrayDeque<>();
        // 3. 开始移动滑动窗口: 这里的滑动窗口大小是固定的, 之前有些题窗口大小不是固定的
        while (right < nums.length){
            // 4. 确保队列是单调递减的, 相当于单调栈
            while (!window.isEmpty() && nums[window.peekLast()] < nums[right])
                window.pollLast();
            // 5. 确保单调性之后再将当前元素入队
            window.offerFirst(right++);
            // 6. 确保当前的最大值还在窗口中
            if (window.peekFirst() < left)
                window.pollFirst();
            // 7. 窗口大小满足之后就可以开始移动窗口, 填充最大值了
            if (right >= k)
                maxArray[left] = nums[window.pollFirst()];
        }
        return maxArray;
    }
}
