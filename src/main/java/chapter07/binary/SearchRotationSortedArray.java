package chapter07.binary;

/**
 * <h2>搜索旋转排序数组系列问题</h2>
 * <h3>1. 搜索旋转排序数组 </h3>
 * <h3>2. 搜索旋转排序数组 II</h3>
 * <h3>3. 寻找旋转排序数组中最小值</h3>
 * <h3>4. 寻找旋转排序数组中最小值 II</h3>
 */
public class SearchRotationSortedArray {

    /**
     * <h3>思路: 搜索旋转排序数组</h3>
     */
    private static int search(int[] numbers, int target){
        int left = 0, right = numbers.length - 1;
        while (left <= right){
            int mid = (left + right) >> 1;
            if (numbers[mid] == target)
                return mid;
            // 1. 左侧数组有序
            if (numbers[left] <= numbers[mid]){
                // 3. 是否在有序数组中
                if (numbers[left] <= target && target <= numbers[mid])
                    right = mid - 1;
                else
                    left = mid + 1;
            }else{
                // 2. 右侧数组有序
                if (numbers[mid] <= target && target <= numbers[right])
                    left = mid + 1;
                else
                    right = mid - 1;
            }
        }
        return -1;
    }

    /**
     * <h3>思路: 搜索旋转排序数组 II</h3>
     */
    private static boolean search(int target, int[] numbers){
        int left = 0, right = numbers.length - 1;
        while (left <= right){
            int mid = (left + right) >> 1;
            if (numbers[mid] == target)
                return true;
            // 注: 唯一的区别在于如果两侧的数字相同, 那么就没有办法比较大小知道那边有序了, 所以只要去掉就好了
            if (numbers[left] == numbers[mid] && numbers[right] == numbers[mid]){
                left++;right--;
            }else if (numbers[left] <= numbers[mid]){
                if (numbers[left] <= target && target <= numbers[mid])
                    right = mid - 1;
                else
                    left = mid + 1;
            }else if (numbers[mid] <= numbers[right]){
                if (numbers[mid] <= target && target <= numbers[right])
                    left = mid + 1;
                else
                    right = mid - 1;
            }
        }
        return false;
    }

    /**
     * <h3>本质思路和前两个题一致, 但是要知道到底怎么找最小值</h3>
     * <h3>1. 如果满足 nums[left] <= nums[mid], 也就是左侧有序</h3>
     * <h3>2. 那么接着就去右侧找最小值而不是左侧, 这是因为左侧的最小值一定大于右侧的最大值, 因为是旋转过去的</h3>
     * <h3>3. 如果满足 nums[mid] <= nums[right], 那么证明右侧有序</h3>
     * <h3>4. 那么接着就去左侧找最小值, 这是因为右侧的值一定比左侧的大, 因为升序排列的, 所以去左侧找就好</h3>
     */
    private static int findMin1(int[] numbers){
        int min = Integer.MAX_VALUE;
        int left = 0, right = numbers.length - 1;
        while (left <= right){
            int mid = (left + right) >> 1;
            // 注: 这里必须保证中间值比右边界大, 才能确保左侧有序, 右侧无序, 否则可能出现左右两侧都有序的情况
            // 注: 这里最好不要去加右侧的等号, 这是因为如果左侧有序, 右侧相等, 那么这个时候应该去左侧找而不应该去右侧找, 加了等号就会右侧找
            if (numbers[left] <= numbers[mid] && numbers[mid] > numbers[right]){
                left = mid + 1;
            }else{
                right = mid - 1;
            }
            min = Math.min(min, numbers[mid]);
        }

        return min;
    }

    private static int findMin2(int[] numbers){
        int min = Integer.MAX_VALUE;
        int left = 0, right = numbers.length - 1;
        while (left <= right){
            int mid = (left + right) >> 1;
            if(numbers[left] == numbers[mid] && numbers[mid] == numbers[right]){
                left++;right--;
            }else if (numbers[left] <= numbers[mid] && numbers[mid] > numbers[right]){
                left = mid + 1;
            }else{
                right = mid - 1;
            }
            min = Math.min(min, numbers[mid]);
        }
        return min;
    }
}
