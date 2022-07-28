package chapter05.random;

import java.util.Random;

/**
 * <h2>打乱数组</h2>
 */
public class RandomArray {

    private final int[] nums;

    private final Random random;

    /**
     * <h3>思路: 打乱数组</h3>
     * <h3>1. 全排列之后随机选择排列: 这个时间复杂度过高</h3>
     * <h3>2. 两两随机交换: 这种方式不具有公平性</h3>
     * <h3>3. Knuth 算法: 这个既能够保证时间复杂度, 也能够保证公平性</h3>
     */
    public RandomArray(int[] nums){
        this.nums = nums;
        this.random = new Random();
    }

    public int[] reset(){
        // 注: 直接返回原数组
        return this.nums;
    }

    public int[] shuffle(){
        // 注: 洗牌不要在原数组中进行, 否则没有办法重置
        int[] copy = nums.clone();
        // 注: 至于为什么这种算法可以保证公平性, 可以看掘金上的一篇文章, 写的挺好的
        for (int index = 0;index < copy.length;index++){
            // 注: 每次当前元素都和自己后面的元素进行交换就行
            swap(nums, index, index + random.nextInt(copy.length - index));
        }
        return copy;
    }

    private void swap(int[] nums, int first, int second){
        int temp = nums[first];
        nums[first] = nums[second];
        nums[second] = temp;
    }

}
