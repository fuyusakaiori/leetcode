package chapter05.sort;

import java.util.Random;

/**
 * <h2>多数元素</h2>
 */
public class MajorityElement {

    /**
     * <h3>本质:</h3>
     * <h3>1. 数组中只会存在一个出现次数大于 n/2 的数, 不可能出现第二个</h3>
     * <h3>2. 所以只要整个数组是有序的, 那么数组的中间元素必然是出现次数大于 n/2 的数字</h3>
     * <h3>3. 如果最小的元素出现次数大于 n/2, 那么显然最后一个元素一定在中间</h3>
     * <h3>4. 如果最大的元素出现大于 n/2 次, 那么第一个元素一定在中间</h3>
     * <h3>思路:</h3>
     * <h3>1. 手写排序: 归并, 快排, 堆排</h3>
     * <h3>2. 摩尔投票法</h3>
     */
    private static int majorityElement1(int[] nums) {
        quickSort(nums, 0, nums.length - 1);
        return nums[nums.length / 2];
    }

    private static void quickSort(int[] nums, int left, int right){
        if (left > right)
            return;
        int index = new Random().nextInt(right - left + 1) + left;
        swap(nums, right, index);
        int[] partition = partition(nums, left, right, nums[right]);
        quickSort(nums, left, partition[0] - 1);
        quickSort(nums, partition[0] + 1, right);
    }

    private static int[] partition(int[] nums, int left, int right, int target){
        int index = left;
        int rightIndex = right, leftIndex = left;
        while (index < rightIndex + 1){
            if (nums[index] < target){
                swap(nums, leftIndex++, index++);
            }else if (nums[index] > target){
                swap(nums, rightIndex--, index);
            }else{
                index++;
            }
        }
        return new int[] {leftIndex, rightIndex};
    }

    private static void swap(int[] nums, int first, int second){
        int temp = nums[first];
        nums[first] = nums[second];
        nums[second] = temp;
    }

    /**
     * <h3>摩尔投票法: </h3>
     * <h3>1. 准备两个变量: major 记录众数, count 记录票数</h3>
     * <h3>2. 如果 count == 0, 那么直接将 major 置为当前这个元素</h3>
     * <h3>3. 如果 nums[index] != major, 那么 count - 1; 如果 nums[index] == major, 那么 count + 1</h3>
     * <h3>4. 遍历结束后, major 表示的就是众数, 这个摩尔投票法不是很好从数学角度证明, 不过可以大概去证明下</h3>
     * <h3>5. 因为数组中必然有出现次数大于 n/2 的数字, 所以可以这样来考虑</h3>
     * <h3>5.1 如果 major 不是真正的候选人, 那么其余候选人会一起反对, 由于正统候选人票最多, 所以会被换下去</h3>
     * <h3>5.2 如果 major 就是真正的后候选人, 那么其余候选人的票数最后是不足以将其反对下去的</h3>
     */
    private static int majorityElement2(int[] nums){
        int major = 0, count = 0;
        for (int num : nums) {
            major = count == 0 ? num: major;
            count += num == major ? 1 : -1;
        }
        return major;
    }

}
