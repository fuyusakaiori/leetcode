package chapter05.pointer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * <h2>两个数组交集</h2>
 * <h3>1. 两个数组交集</h3>
 * <h3>2. 两个数组交集 II</h3>
 * <h3>注: 这两个题并不难, 但是面试官可能会深入提问</h3>
 * <h3>1. 两个数组如何作交集?</h3>
 * <h3>分别将两个数组放入集合中, 然后遍历其中一个集合, 如果在另一个集合中重复就放进结果集</h3>
 * <h3>2. 如果两个数组已经排好序或者已经按照非递减的顺序排序, 那么应该怎么处理?</h3>
 * <h3>这就可以采用双指针的方式来做, 如果两个元素相等就添加, 如果不等, 就移动较小的元素的指针</h3>
 * <h3>3. 这两种方式的时间复杂度是多少?</h3>
 * <h3>直接使用集合的时间复杂度为 O(n), 使用排序的方式时间复杂度为 O(n*logn)</h3>
 * <h3>4. 如果内存有限, 没有办法将数组所有元素都读取到内存中应该怎么办?</h3>
 * <h3>将数组拆分为多个小数组, 然后使用归并排序, 最后对将每个小数组和第一个数组都进行双指针排序</h3>
 */
public class IntersectionSet {


    /**
     * <h3>集合</h3>
     * <h3>注: 这里以第二个为准, 不是按照第一个题写的解法</h3>
     */
    private static int[] intersection1(int[] nums1, int[] nums2){
        Map<Integer, Integer> map = new HashMap<>();
        for(int num : nums1){
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        int index = 0;
        int[] intersection = new int[nums1.length + nums2.length];
        for(int num : nums2){
            if(map.containsKey(num) && map.get(num) != 0){
                intersection[index++] = num;
                map.put(num, map.get(num) - 1);
            }
        }
        return Arrays.copyOf(intersection, index);
    }

    /**
     * <h3>排序 + 双指针</h3>
     */
    private static int[] intersection2(int[] nums1, int[] nums2){
        // 1. 排序
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        // 2. 交集
        int first = 0, second = 0, index = 0;
        int[] intersectionSet = new int[nums1.length + nums2.length];
        while(first < nums1.length && second < nums2.length){
            if(nums1[first] == nums2[second]){
                intersectionSet[index++] = nums1[first];
                first++;second++;
            }else if(nums1[first] < nums2[second]){
                first++;
            }else{
                second++;
            }
        }
        // 3. 截取
        return Arrays.copyOf(intersectionSet, index);
    }

}
