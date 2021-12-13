package chapter08;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * <h2>单调栈</h2>
 * <p>结构含义: </p>
 * <p>1. 栈中的所有元素都是严格按照单调递减或者单调递增的顺序排列的</p>
 * <p>2. 如果有破坏单调性的元素出现, 那么就栈中元素弹出, 直到栈顶元素大于入栈的元素</p>
 * <p>细节: </p>
 * <p>1. 单调栈可以处理非常多的题目, 但是你不一定能够发现这个题是使用单调栈的</p>
 * <p>2. 单调栈可以支持重复元素, 实现上有一点区别</p>
 */
public class MonotoneStack
{
    public static void main(String[] args)
    {
        System.out.println(Arrays.toString(nextGreaterElement(new int[]{5, 4, 3, 4, 5, 3, 5, 6}, "")));
    }

    /**
     * <p>下一个更大的元素 I</p>
     * <p>1. 数组中的每个元素的右侧可能存在比自己大的元素</p>
     * <p>2. 如果存在比自己大的元素, 那么将第一个比自己大的元素的索引放在数组中</p>
     * <p>3. 如果不存在比自己大的元素, 那么就将索引置为 -1</p>
     * <p>注: 这里的数组是不包含重复值的</p>
     * <p>注: 这里实际上也可以同时记录左侧比自己大的</p>
     * @param numbers 数组
     * @return 右侧离自己最近的最大元素
     */
    private static int[] nextGreaterElement(int[] numbers){
        // 0. 准备返回的数组
        int[] result = new int[numbers.length];
        // 1. 准备单调栈: 单调栈中存放的是元素对应的索引而不是元素值, 并且这个单调栈的是从大到小的
        LinkedList<Integer> stack = new LinkedList<>();
        // 2. 开始遍历数组
        for (int index = 0;index < numbers.length;index++){
            // 栈顶元素比入栈元素小就要出栈
            while (!stack.isEmpty() && numbers[stack.peek()] < numbers[index]) {
                result[stack.pop()] = index;
            }
            // 当前元素无论如何都是需要入栈的
            stack.push(index);
        }
        // 6. 栈中还可能存在剩余的元素
        while (!stack.isEmpty()) result[stack.pop()] = -1;

        return result;
    }

    /**
     * <p>含有重复元素</p>
     */
    private static int[] nextGreaterElement(int[] numbers, String repeat){
        int[] result = new int[numbers.length];
        // 注: 相同元素存储同一个链表中
        LinkedList<LinkedList<Integer>> stack = new LinkedList<>();
        for (int index = 0;index < numbers.length;index++){
            while (!stack.isEmpty() && numbers[stack.peek().peek()] < numbers[index]){
                LinkedList<Integer> list = stack.pop();
                while (!list.isEmpty()) result[list.poll()] = index;
            }
            if (stack.isEmpty() || numbers[index] != numbers[stack.peek().peek()]) {
                LinkedList<Integer> list = new LinkedList<>();
                list.offer(index);
                stack.push(list);
            }
            else{
                stack.peek().offer(index);
            }
        }
        while (!stack.isEmpty()){
            LinkedList<Integer> list = stack.pop();
            while (!list.isEmpty()) result[list.poll()] = -1;
        }

        return result;
    }

    /**
     * <p>最大指标</p>
     * <p>1. 指标的含义是子数组的元素和乘上这个子数组中的最小值, 这个乘积就是指标</p>
     * <p>2. 子数组的元素和, 就是选定一个子数组, 让后将这个子数组中的所有元素相加</p>
     * @return
     */
    private static int maxIndicators(){


        return 0;
    }
}
