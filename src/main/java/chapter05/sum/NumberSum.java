package chapter05.sum;

import java.util.*;

/**
 * <h2>数字之和系列问题</h2>
 * <h3>1. 两数之和</h3>
 * <h3>2. 两数之和 II</h3>
 * <h3>3. 三数之和</h3>
 * <h3>4. 四数之和</h3>
 * <h3>5. 最接近的三数之和</h3>
 * <h3>6. 有效三角形个数</h3>
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

    private static int threeSumClosest(int[] numbers, int target){
        // 1. 排序
        Arrays.sort(numbers);
        // 2. 三指针
        int result = 0, minMinus = Integer.MAX_VALUE;

        for (int first = 0; first < numbers.length; first++){
            if(first > 0 && numbers[first] == numbers[first - 1])
                continue;
            int third = numbers.length - 1;
            int newTarget = target - numbers[first];
            for(int second = first + 1;second < numbers.length;second++){
                if(second > first + 1 && numbers[second] == numbers[second - 1])
                    continue;
                while(third > second && numbers[third] + numbers[second] > newTarget){
                    third--;
                }
                // 注: 核心部分
                int left = third != second ? numbers[third] + numbers[second] - newTarget:Integer.MAX_VALUE;
                int right = third != numbers.length - 1 ?numbers[third + 1] + numbers[second] - newTarget:Integer.MAX_VALUE;
                int minus = Math.abs(left) < Math.abs(right) ? left: right;
                if(Math.abs(minus) < minMinus){
                    minMinus = Math.abs(minus);
                    result = minus + target;
                }
            }
        }
        return result;
    }


    /**
     * <h3>思路: 有效三角形个数</h3>
     * <h3>1. 排序 + 暴力解</h3>
     * <h3>2. 排序 + 二分优化</h3>
     * <h3>3. 排序 + 三指针: 类似于三数之和</h3>
     * <h3>核心: 这个题有两种策略, 我的解法采用第二种方式, 会更好理解</h3>
     * <h3>1. 可以从小到大去确定, 也就是先确定最小的两条边, 然后从左向右移动第三个指针</h3>
     * <h3>2. 可以从大到小去确定, 也就先确定最大的两条边, 然后还是从左向右移动指针</h3>
     * <h3>3. 不能从从右向左移动的原因是这样的: </h3>
     * <h3>3.1 第一种, 将第三个指针从右向左移就是在减小最大值, 碰见符合条件的就终止, 然后将第二个指针右移</h3>
     * <h3>3.2 但是, 这样第三个指针必须重新从最右开始移动, 如果接着上次的位置移动就会产生遗漏, 最好自己试试</h3>
     * <h3>3.3 第二种, 和第一种道理是一样的, 但是会产生的问题是重复计算</h3>
     * <h3>注: 这个题就是三数之和的变种问题, 核心点在于双指针的初始位置和如何移动要想清楚</h3>
     */
    private static int triangleNumber1(int[] numbers){
        // 1. 排序
        Arrays.sort(numbers);
        // 2. 枚举
        int count = 0;
        for (int first = 0;first < numbers.length;first++){
            for (int second = first - 1;second >= 0;second--){
                for (int third = second - 1;third >= 0;third--){
                    if (numbers[second] + numbers[third] <= numbers[first])
                        break;
                    count++;
                }
            }
        }
        return count;
    }

    private static int triangleNumber2(int[] numbers){
        // 1. 排序
        Arrays.sort(numbers);
        // 2. 枚举
        int count = 0;
        for (int first = 0;first < numbers.length;first++){
            for (int second = first - 1;second >= 0;second--){
                // 二分优化: 第三个数可以采用二分来查找, 直到左右边界指针相等就找到分界点了
                int left = 0, right = second - 1;
                while (left < right){
                    int mid = (left + right) >> 1;
                    if (numbers[mid] + numbers[second] > numbers[first]){
                        // 注: 这里应该是将右指针移动到中点, 因为中点是符合条件的
                        right = mid;
                    }else{
                        // 注: 如果相等那么肯定是向右移动
                        left = mid + 1;
                    }
                }
                // 注: ① 确保左右指针相等, 避免出现右指针为负的情况 ② 再次检测分界点是否符合条件, 如果分界点不符合条件, 那么右侧肯定是和分界点一样大的的或者没有
                if (left == right && numbers[right] + numbers[second] > numbers[first])
                    count += second - right;
            }
        }

        return count;
    }

    private static int triangleNumber3(int[] numbers){
        // 1. 排序
        Arrays.sort(numbers);
        // 2. 枚举
        int count = 0;
        for (int first = 0; first < numbers.length; first++){
            for (int second = first - 1, third = 0;third < second;second--){
                // 注: 如果不满足条件, 那么就右移第三个指针让它满足条件
                while (third < second && numbers[second] + numbers[third] <= numbers[first])
                    third++;
                count += second - third;
            }
        }
        return count;
    }

}
