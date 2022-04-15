package chapter07;

import utils.Important;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * <h2>三数之和</h2>
 */
public class ThreeSum {

    /**
     * <h3>思路: 三数之和</h3>
     * <h3>注: 将三数之和转换成两数之和</h3>
     */
    @Important
    private static List<List<Integer>> threeSum(int[] nums){
        List<List<Integer>> triples = new LinkedList<>();
        // 1. 排序
        Arrays.sort(nums);
        // 2. 固定第一个指针, 然后移动另外两个指针
        for(int first = 0;first < nums.length;first++){
            // 注: 去重操作
            if (first > 0 && nums[first] == nums[first - 1]) continue;
            // 3. 剩下两个值必须要和这个值相等, 才是三元组
            int target = -nums[first];

            // 4. 第三个指针只能够是在这里定义 => 不要在循环中定义, 这会导致每次双指针的时候都从尾部开始, 实际上是不需要的
            int third = nums.length - 1;

            for (int second = first + 1;second < nums.length;second++){
                // 注: 去重操作, 避免和上次选择的数相同
                if (second > first + 1 && nums[second] == nums[second - 1]) continue;
                // 5. 如果两者之和始终比目标值大, 那么就继续移动右指针
                while (third > second && nums[second] + nums[third] > target){
                    third--;
                }
                // 6. 判断两个指针是否因为相等而终止循环
                if (third == second) break;
                // 7. 判断两者是否因为和相等而终止
                if (nums[second] + nums[third] == target){
                    triples.add(Arrays.asList(nums[first], nums[second], nums[third]));
                }
            }
        }
        return triples;
    }


}
