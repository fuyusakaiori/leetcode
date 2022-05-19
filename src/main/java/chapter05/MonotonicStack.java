package chapter05;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * <h2>单调栈系列题目</h2>
 * <h3>1. 接雨水</h3>
 * <h3>2. 柱状图中最大矩形</h3>
 * <h3>3. 最大矩形</h3>
 * <h3>4. 每日温度</h3>
 * <h3>5. 下一个更大元素</h3>
 * <h3>6. 下一个更大元素 II</h3>
 */
public class MonotonicStack {


    /**
     * <h3>思路: 柱状图中最大矩形</h3>
     * <h3>1. 如果能想到使用单调栈这个方法, 那么难度就会降低, 但是依然存在问题</h3>
     * <h3>2. 在单调栈的基础上还需要使用哨兵元素, 从而确保结果正确, 这个是非常不容易想到的</h3>
     */
    private static int largestRectangleArea(int[] heights){
        int max = 0;
        LinkedList<Integer> stack = new LinkedList<>();
        // 1. 哨兵结点: 此前弹出的元素都会栈顶元素大, 所以栈顶元素出栈的时候不仅可以右侧的, 还可以使用左侧的, 没有哨兵就会漏算左侧的
        stack.push(0);
        // 2. 还可以在数组末尾添加哨兵, 从而不用额外添加循环, 但是需要修改数组
        int[] newHeights = new int[heights.length + 2];
        System.arraycopy(heights, 0, newHeights, 1, heights.length);
        // 3. 单调栈
        for (int index = 1;index < newHeights.length;index++) {
            while (newHeights[stack.peek()] > newHeights[index]) {
                // 注: 这里不要使用 index - stack.pop(), 会导致右侧面积计算不到
                int height = newHeights[stack.pop()];
                int width = index - stack.peek() - 1;
                max = Math.max(max, height * width);
            }
            stack.push(index);
        }
        return max;
    }

    /**
     * <h3>思路: 每日温度</h3>
     */
    public int[] dailyTemperatures(int[] temperatures) {
        int[] result = new int[temperatures.length];
        LinkedList<Integer> stack = new LinkedList<>();
        for(int index = 0;index < temperatures.length;index++){
            while(!stack.isEmpty() && temperatures[stack.peek()] < temperatures[index]){
                int cur = stack.pop();
                result[cur] = index - cur;
            }
            stack.push(index);
        }
        return result;
    }


    /**
     * <h3>思路: 下一个更大元素</h3>
     */
    private static int[] nextGreaterElement(int[] nums1, int[] nums2){
        Map<Integer, Integer> map = new HashMap<>();
        for(int index = 0;index < nums1.length;index++){
            map.put(nums1[index], index);
        }
        int[] nextMax = new int[nums1.length];
        Arrays.fill(nextMax, -1);
        LinkedList<Integer> stack = new LinkedList<>();
        for (int num : nums2) {
            while (!stack.isEmpty() && stack.peek() < num) {
                int cur = stack.pop();
                if (map.containsKey(cur))
                    nextMax[map.get(cur)] = num;
            }
            stack.push(num);
        }
        return nextMax;
    }

    /**
     * <h3>思路: 下一个更大元素</h3>
     * <h3>注: 循环数组确实需要取模, 不过直接对索引取模就会导致循环最后难以终止</h3>
     * <h3>注: 所以可以考虑使用两倍的长度, 因为这里最多只会走两圈, 所以用两倍的长度来做终止条件是可以的</h3>
     */
    private static int[] nextGreaterElement(int[] nums){
        int length = nums.length;
        int[] nextMax = new int[length];
        LinkedList<Integer> stack = new LinkedList<>();
        Arrays.fill(nextMax, -1);
        // 注: 两倍长度会导致重复入栈, 不过没有关系并不会影响结果
        for (int index = 0;index < length * 2 - 1;index++){
            while (!stack.isEmpty() && nums[stack.peek()] < nums[index % length]){
                nextMax[stack.pop()] = nums[index % length];
            }
            stack.push(index % length);
        }
        return nextMax;
    }


}
