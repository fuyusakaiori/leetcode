package chapter05.binary;

/**
 * <h2>在排序数组中查找元素的第一个值和最后一个值</h2>
 */
public class SearchRange {

    /**
     * <h3>本质: 分别查询目标值的最大值和最小值</h3>
     */
    private static int[] searchRange(int[] nums, int target){
        int first = binarySearch(nums, target, true);
        int second = binarySearch(nums, target, false) - 1;
        return first <= second ? new int[]{first, second} : new int[]{-1, -1};
    }

    /**
     * <h3>注: 查询最大值和最小值的代码会有稍微的区别, 所以需要多用一个参数</h3>
     */
    private static int binarySearch(int[] nums, int target, boolean flag){
        // 注: 初始化为长度, 否则找不到第二个数的时候结果会为 -2
        int result = nums.length;
        int left = 0, right = nums.length - 1;
        while (left <= right){
            int mid = left + ((right - left) >> 1);
            // 1. 如果当前元素大于目标值, 无论是查询最大值还是最小值, 都是向左移动
            if (nums[mid] > target || (flag && nums[mid] == target)){
                // 3. 如果查询的元素等于目标值, 那么查询最大值和最小值就有区别了, 最大值就要向右移动, 最小值就要向左移动
                result = mid;
                right = mid - 1;
            }else{
                // 2. 如果是当前元素小于目标值, 无论是查询最大值和最小值, 都是向右移动
                left = mid + 1;
            }
        }
        return result;
    }

}
