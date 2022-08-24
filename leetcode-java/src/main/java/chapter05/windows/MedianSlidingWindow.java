package chapter05.windows;

import java.util.*;

/**
 * <h3>滑动窗口中位数</h3>
 */
public class MedianSlidingWindow {

    /**
     * <h3>暴力解</h3>
     */
    private static double[] medianSlidingWindow1(int[] nums, int cnt){
        // 1. 中位数数组
        double[] medianValue = new double[nums.length - cnt + 1];
        // 2. 滑动窗口
        List<int[]> window = new ArrayList<>(cnt);
        // 3. 初始化
        for (int idx = 0;idx < cnt;idx++){
            window.add(new int[]{idx, nums[idx]});
        }
        // 4. 排序
        window.sort(Comparator.comparingInt(a -> a[1]));
        // 5. 移动窗口
        for (int idx = cnt;idx <= nums.length;idx++){
            // 6. 计算中位数: 如果不使用双精度就会溢出
            double first = window.get(cnt / 2)[1];
            double second = window.get((cnt - 1) / 2)[1];
            medianValue[idx - cnt] = (first + second) / 2;
            // 7. 移除左边界: 最后一次不要移除, 会越界
            if (idx < nums.length){
                int[] removed = null;
                // 8. 找到集合中应该被移除的元素
                for (int[] couple : window) {
                    if (couple[0] == idx - cnt)
                        removed = couple;
                }
                window.remove(removed);
                window.add(new int[]{idx, nums[idx]});
                window.sort(Comparator.comparingInt(a -> a[1]));
            }
        }
        return medianValue;
    }

    /**
     * <h3>双优先权队列 + 延迟删除</h3>
     */
    private static double[] medianSlidingWindow2(int[] nums, int cnt){
        // 1. 中位数数组
        double[] medianValue = new double[nums.length - cnt + 1];
        // 2. 滑动窗口
        DualHeap window = new DualHeap(cnt);
        // 3. 初始化
        for (int idx = 0; idx < cnt; idx++) {
            window.insert(nums[idx]);
        }
        // 4. 移动窗口
        medianValue[0] = window.getMedian();
        for (int idx = cnt; idx < nums.length; idx++) {
            window.insert(nums[idx]);
            window.remove(nums[idx - cnt]);
            medianValue[idx - cnt + 1] = window.getMedian();
        }
        return medianValue;
    }

    private static class DualHeap{
        // 1. 大顶堆优先权队列: 维护较小的一半 直接使用传统的 lambda 表达式会溢出
        private final PriorityQueue<Integer> small = new PriorityQueue<>(Collections.reverseOrder());
        // 2. 小顶堆优先权队列: 维护较大的一半
        private final PriorityQueue<Integer> large = new PriorityQueue<>();
        // 3. 延迟删除队列
        private final Map<Integer, Integer> delayed = new HashMap<>();
        // 4. 单独记录两个优先权队列的长度
        int smallSize, largeSize;
        // 5. 滑动窗口大小
        int windowSize;

        public DualHeap(int windowSize) {
            this.windowSize = windowSize;
        }

        /**
         * <h3>获取中位数: (较大的一半的最小值 + 较小的一半的最大值) / 2</h3>
         */
        public double getMedian(){
            return (windowSize & 1) == 1 ? small.peek() : ((double) small.peek() + large.peek()) / 2;
        }

        public void insert(int value){
            // 1. 如果大顶堆为空或者元素小于大顶堆的堆顶元素, 那么直接进入大顶堆
            if (small.isEmpty() || value <= small.peek()){
                small.offer(value);
                smallSize++;
            }else{
                // 2. 如果元素大于大顶堆的堆顶元素, 那么就进入小顶堆
                large.offer(value);
                largeSize++;
            }
            // 3. 调整平衡: 完全有可能某个队列中的元素过多, 另一个队列中的元素过少, 所以需要平衡
            makeBalance();
        }

        public void remove(int value){
            // 1. 延时删除
            delayed.put(value, delayed.getOrDefault(value, 0) + 1);
            // 2. 检查删除的元素属于大顶堆还是小顶堆
            if (value <= small.peek()){
                smallSize--;
                // 2.1 如果删除的恰好是堆顶元素, 那么就立刻删除
                if (value == small.peek())
                    prune(small);
            }else{
                largeSize--;
                if (value == large.peek())
                    prune(large);
            }
            makeBalance();
        }

        private void makeBalance() {
            // 1. 如果大顶堆的数量比小顶堆的数量多 2 个
            if (smallSize > largeSize + 1){
                // 1.1 小顶堆的堆顶元素进入大顶堆
                // 注: 堆顶元素是需要被及时处理的, 所以堆顶元素不存在过期的情况
                large.offer(small.poll());
                largeSize++;
                smallSize--;
                // 1.2 处理可能存在的需要延时删除的元素
                prune(small);
            }else if (largeSize > smallSize){
                // 2. 如果小顶堆的数量比大顶堆的数量多 1 个
                small.offer(large.poll());
                smallSize++;
                largeSize--;
                prune(large);
            }
        }

        private void prune(PriorityQueue<Integer> heap) {
            while (!heap.isEmpty()){
                int element = heap.peek();
                if (!delayed.containsKey(element))
                    break;
                delayed.put(element, delayed.get(element) - 1);
                if (delayed.get(element) == 0)
                    delayed.remove(element);
                heap.poll();
            }
        }
    }

}
