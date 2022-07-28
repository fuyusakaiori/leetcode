package chapter05.hash;

import java.util.LinkedList;
import java.util.List;

/**
 * <h2>原地哈希</h2>
 * <h3>1. 找到所有数组中消失的数字</h3>
 * <h3>2. 数组中重复的数据</h3>
 * <h3>3. 缺失的第一个正数</h3>
 */
public class OriginalHash {

    /**
     * <h3>思路: 找到所有数组中消失的数字</h3>
     * <h3>注: 这里采用正负号的方式标记</h3>
     */
    private static List<Integer> findDisappearedNumbers(int[] nums){
        List<Integer> disappear = new LinkedList<>();
        // 1. 标记
        for(int index = 0;index < nums.length;index++){
            int location = Math.abs(nums[index]) - 1;
            if(nums[location] > 0)
                nums[location] = -nums[location];
        }
        // 2. 寻找正数
        for(int index = 0;index < nums.length;index++){
            if(nums[index] > 0)
                disappear.add(index + 1);
        }
        return disappear;
    }

    /**
     * <h3>思路: 数组中重复的数据</h3>
     * <h3>1. 原地哈希</h3>
     * <h3>2. 置换</h3>
     * <h3>注: 这个题没有办法使用寻找重复数的解法, 而是使用原地哈希来解的</h3>
     * <h3>注: 这个题最开始我是采用长度标记的, 但是代码没有采用正负号标记优雅</h3>
     */
    private static List<Integer> findDuplicates1(int[] nums){
        List<Integer> duplicates = new LinkedList<>();
        for (int index = 0;index < nums.length;index++){
            int location = Math.abs(nums[index]) - 1;
            if (nums[location] > 0){
                nums[location] = -nums[location];
            }else{
                duplicates.add(location + 1);
            }
        }
        return duplicates;
    }

    private static List<Integer> findDuplicates2(int[] nums){
        int index = 0;
        List<Integer> duplicates = new LinkedList<>();
        // 1. 将每个数都放在对应的位置上
        while (index < nums.length){
            while (nums[index] != nums[nums[index] - 1]){
                swap(nums, index, nums[index] - 1);
            }
            index++;
        }
        // 2. 如果该位置上的数和索引不对应, 那么证明这个数就是重复的
        for (index = 0;index < nums.length;index++){
            if (nums[index] != index + 1)
                duplicates.add(nums[index]);
        }
        return duplicates;
    }
    private static void swap(int[] nums, int first, int second){
        int temp = nums[first];
        nums[first] = nums[second];
        nums[second] = temp;
    }

    /**
     * <h3>思路: 缺失的第一个正数</h3>
     * <h3>1. 原地哈希</h3>
     * <h3>2. 置换</h3>
     * <h3>注: 这个题会相对难一些, 除了原地哈希外还有部分其他预处理操作</h3>
     */
    private static int firstMissingPositive1(int[] nums){
        // 1. 排除所有非正整数
        for(int index = 0;index < nums.length;index++){
            if(nums[index] <= 0)
                nums[index] = nums.length + 1;
        }
        // 2. 原地哈希
        for(int index = 0;index < nums.length;index++){
            int real = Math.abs(nums[index]);
            if(real - 1 < nums.length && nums[real - 1] > 0){
                nums[real - 1] = -nums[real - 1];
            }
        }
        // 3. 检查非负数
        for(int index = 0;index < nums.length;index++){
            if(nums[index] > 0)
                return index + 1;
        }
        return nums.length + 1;
    }

    private static int firstMissingPositive2(int[] nums){
        for(int index = 0;index < nums.length;index++){
            while(nums[index] > 0 && nums[index] < nums.length
                          && nums[nums[index] - 1] != nums[index]){
                int temp = nums[index];
                nums[index] = nums[temp - 1];
                nums[temp - 1] = temp;
            }
        }
        for(int index = 0;index < nums.length;index++){
            if(nums[index] != index + 1)
                return index + 1;
        }
        return nums.length + 1;
    }

    /**
     * <h3>丢失的数字</h3>
     * <h3>1. 位运算</h3>
     * <h3>2. 原地哈希</h3>
     * <h3>3. 数学公式</h3>
     * <h3>注: 如果数组中有零存在就不要用正负号标记数字, 没有办法区分, 只能用长度标记</h3>
     * <h3>注: 如果使用长度标记, 要注意不要直接使用原长度, 要在原长度上加一</h3>
     * <h3>注: 这题有很多做法, 这里仅列出最优的几个</h3>
     */
    private static int missingNumber(int[] nums){
        int mark = nums.length + 1;
        // 1. 标记
        for(int index = 0;index < nums.length;index++){
            if(nums[index] % mark < nums.length)
                nums[nums[index] % mark] += mark;
        }
        // 2. 选择
        for(int index = 0;index < nums.length;index++){
            if(nums[index] <= nums.length)
                return index;
        }
        return nums.length;
    }
}
