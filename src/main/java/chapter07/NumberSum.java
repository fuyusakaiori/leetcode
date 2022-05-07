package chapter07;

import java.util.*;

/**
 * <h2>数字之和系列问题</h2>
 * <h3>1. 两数之和</h3>
 * <h3>2. 两数之和 II</h3>
 * <h3>3. 三数之和</h3>
 * <h3>4. 四数之和</h3>
 */
public class NumberSum {

    /**
     * <h3>思路: 两数之和</h3>
     * <h3>1. 暴力解</h3>
     * <h3>2. 哈希表</h3>
     * <h3>注: 无序数组不能转换为有序数组处理, 因为排序之后索引就发生变化了</h3>
     */
    private static int[] twoSum(int[] numbers, int target){
        Map<Integer, Integer> map = new HashMap<>();
        for (int index = 0;index < numbers.length;index++){
            int newTarget = target - numbers[index];
            if (map.containsKey(newTarget))
                return new int[]{map.get(newTarget), index};
            map.put(numbers[index], index);
        }
        return null;
    }

    /**
     * <h3>思路: 两数之和 II</h3>
     */
    private static int[] twoSum(int target, int[] numbers){
        int first = 0, last = numbers.length - 1;
        while (first < last){
            int result = numbers[first] + numbers[last];
            if (result == target)
                return new int[]{first + 1, last + 1};
            if (result > target)
                last--;
            if (result < target)
                first++;
        }
        return null;
    }

    /**
     * <h3>思路: 三数之和</h3>
     * <h3>本质: 排序 + 双指针, 就是转换为两数之和来做</h3>
     */
    private static List<List<Integer>> threeSum(int[] numbers){
        List<List<Integer>> triples = new LinkedList<>();
        // 1. 排序
        Arrays.sort(numbers);
        // 2. 确定第一个数字
        for (int first = 0; first < numbers.length; first++){
            // 3. 确保不要和上次相同, 也就是避免重复
            if (first > 0 && numbers[first] == numbers[first - 1])
                continue;
            int target = -numbers[first];
            // 注: 每次不需要都从末尾开始, 只需要接着上次开始就行
            int third = numbers.length - 1;
            // 4. 确定第二个数: 因为不能像两数之和那样找到后就结束, 这里需要将所有符合条件的都找到, 所以需要继续遍历
            for (int second = first + 1; second < numbers.length; second++){
                 // 5. 确保不和上次相同
                if (second > first + 1 && numbers[second] == numbers[second - 1])
                    continue;
                // 6. 只要双指针之和加起来还比目标值大, 那么就继续移动右指针
                while (third > second && numbers[third] + numbers[second] > target){
                    third--;
                }
                // 7. 退出循环之后检查是否等于目标值
                if (third != second && numbers[third] + numbers[second] == target)
                    triples.add(Arrays.asList(numbers[first], numbers[second], numbers[third]));
            }
        }
        return triples;
    }

    /**
     * <h3>思路: 四数之和</h3>
     * <h3>本质和三数之和没有任何区别</h3>
     */
    private static List<List<Integer>> fourSum(int[] numbers, int target){
        List<List<Integer>> results = new LinkedList<>();
        Arrays.sort(numbers);
        for(int first = 0;first < numbers.length;first++){
            if(first > 0 && numbers[first] == numbers[first - 1])
                continue;
            int firstTarget = target - numbers[first];
            for(int second = first + 1;second < numbers.length;second++){
                if(second > first + 1 && numbers[second] == numbers[second - 1])
                    continue;
                int secondTarget = firstTarget - numbers[second];
                int fourth = numbers.length - 1;
                for(int third = second + 1;third < numbers.length - 1;third++){
                    if(third > second + 1 && numbers[third] == numbers[third - 1])
                        continue;
                    while(fourth > third && numbers[third] + numbers[fourth] > secondTarget){
                        fourth--;
                    }
                    if(third == fourth)
                        break;
                    if(numbers[third] + numbers[fourth] == secondTarget)
                        results.add(Arrays.asList(numbers[first], numbers[second], numbers[third], numbers[fourth]));
                }
            }
        }
        return results;
    }

}
