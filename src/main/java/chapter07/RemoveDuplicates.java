package chapter07;

import utils.Important;

/**
 * <h2>删除数组中的重复元素</h2>
 * <h3>1. 删除数组中的重复元素</h3>
 * <h3>2. 删除数组中的重复元素 II</h3>
 * <h3>注: 这个数组中存在多个重复元素</h3>
 */
public class RemoveDuplicates {

    /**
     * <h3>思路: 删除数组中的重复元素</h3>
     * <h3>1. 双指针: 这个思路很容易想到, 但是在具体实现上出了点偏差</h3>
     * <h3>2. 这个题是每个数字仅保留 1 位, 考虑保留 k 位应该怎么做?</h3>
     */
    private static int removeDuplicates(int[] nums){
        int previous = 0;
        for(int next = 0;next < nums.length;next++){
            if(nums[previous] != nums[next]){
                nums[++previous] = nums[next];
            }
        }
        return previous + 1;
    }

    /**
     * <h3>思路: 删除数组中的重复元素 II</h3>
     * <h3>注: 这个解法其实有点不好想, 也不是那么好理解, 建议多看几次</h3>
     */
    @Important
    private static int removeDuplicates(int[] nums, int k){
        int previous = 0;
        for(int next = 0;next < nums.length;next++){
            // 注: 前面 k 位直接保留, k 位之后再判断
            if (previous < k || nums[previous - k] != nums[next])
                nums[previous++] = nums[next];
        }
        return previous;
    }

    /**
     * <h3>1. 第二种解法可以采用快排分区的思想</h3>
     * <h3>2. 将大于目标次数的放在左边, 将小于目标次数的放在右边</h3>
     */
    private static int removeDuplicates(int k, int[] nums){
        int count = 1;
        int leftIndex = 0, currentIndex = 1;
        int previous = nums[0];
        while (currentIndex < nums.length){
            int current = nums[currentIndex];
            if (current != previous){
                nums[++leftIndex] = nums[currentIndex++];
                previous = current;
                count = 1;
            }else if (count < 2){
                nums[++leftIndex] = nums[currentIndex++];
                count++;
            }else{
                currentIndex++;
            }
        }

        return leftIndex + 1;
    }
}
