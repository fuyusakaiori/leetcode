package chapter05.sort;

import java.util.*;

/**
 * <h2>Top K 问题</h2>
 * <h3>1. 第 K 个最大元素</h3>
 * <h3>2. 前 K 个高频元素</h3>
 * <h3>3. 大数据情况下的 Top K 问题</h3>
 */
public class TopKFrequent {


    /**
     * <h3>思路: 优先权队列</h3>
     * <h3>注: 这个思路非常简单, 但是效率比较低</h3>
     */
    private static int[] topKFrequent(int[] nums, int k){
        // 1. 统计词频
        Map<Integer, Integer> frequent = new HashMap<>();
        for (int num : nums) {
            frequent.put(num, frequent.getOrDefault(num, 0) + 1);
        }
        // 2. 优先权队列排序
        PriorityQueue<int[]> queue = new PriorityQueue<>((a, b)-> b[1] - a[1]);
        for (Map.Entry<Integer, Integer> entry : frequent.entrySet()) {
            queue.offer(new int[]{entry.getKey(), entry.getValue()});
        }
        // 3. 取出优先权队列中的前 K 个元素
        int[] result = new int[k];
        for (int index = 0;index < k && !queue.isEmpty();index++){
            result[index] = queue.poll()[0];
        }
        return result;
    }

    /**
     * <h3>思路: 快速排序</h3>
     * <h3>注: 还需要进一步优化, 有点慢</h3>
     */
    private static int[] topKFrequent(int k, int[] nums){
        // 1. 统计词频
        Map<Integer, Integer> frequent = new HashMap<>();
        for (int num : nums) {
            frequent.put(num, frequent.getOrDefault(num, 0) + 1);
        }
        // 2. 放入链表中准备排序
        List<int[]> couples = new LinkedList<>();
        for (Map.Entry<Integer, Integer> entry : frequent.entrySet()) {
            couples.add(new int[]{entry.getKey(), entry.getValue()});
        }
        // 3. 开始排序: 这里比较麻烦, 主要是我之前写的快速排序存在差异
        findTopK(couples, k, 0, couples.size() - 1);
        int[] result = new int[k];
        for (int index = 0;index < k;index++){
            result[index] = couples.get(index)[0];
        }
        return result;
    }

    private static void findTopK(List<int[]> couples, int k, int left, int right){
        if (left >= right)
            return;
        int random = left + new Random().nextInt(right - left + 1);
        Collections.swap(couples, random, right);
        int[] bounds = partition(couples, left, right, couples.get(right)[1]);
        if (bounds[0] - left == k)
            return;
        if (bounds[0] - left > k)
            findTopK(couples, k, left, bounds[0] - 1);
        if (bounds[0] - left < k)
            findTopK(couples, k, bounds[1] + 1, right);
    }

    private static int[] partition(List<int[]> couples, int left, int right, int target){
        int leftIndex = left, rightIndex = right, index = left;
        while (index < rightIndex + 1){
            if (couples.get(index)[1] > target){
                Collections.swap(couples, index++, leftIndex++);
            }else if (couples.get(index)[1] < target){
                Collections.swap(couples, index, rightIndex--);
            }else{
                index++;
            }
        }
        return new int[]{leftIndex, rightIndex};
    }


}
