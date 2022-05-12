package chapter07.binary;

/**
 * <h2>寻找山峰</h2>
 */
public class FindPeekElement {

    /**
     * <h3>思路: O(n) 解法</h3>
     */
    private static int findPeekElement1(int[] numbers){
        final int LIMIT = Integer.MIN_VALUE;
        for (int index = 0;index < numbers.length;index++){
            int left = index - 1 >= 0 ? numbers[index - 1]: LIMIT;
            int right = index + 1 <= numbers.length - 1 ? numbers[index + 1]: LIMIT;
            if (numbers[index] > left && numbers[index] > right)
                return index;
        }
        return 0;
    }

    /**
     * <h3>思路: O(logn) 解法</h3>
     */
    private static int findPeekElement2(int[] numbers){
        final int LIMIT = Integer.MIN_VALUE;
        int left = 0, right = numbers.length - 1, mid = 0;
        while (left <= right){
            mid = (left + right) >> 1;
            // 注: 计算得左右两侧相邻的值, 同时注意避免越界的情况
            int leftMid = mid - 1 >= 0 ? numbers[mid - 1]: LIMIT;
            int rightMid = mid + 1 < numbers.length - 1 ? numbers[mid + 1]: LIMIT;
            // 注: 如果满足峰值的条件就直接返回结果
            if (numbers[mid] > leftMid && rightMid < numbers[mid])
                return mid;
            // 注: 如果是单调递增那么就去右侧查找
            if (leftMid <= numbers[mid] && numbers[mid] <= rightMid){
                left = mid + 1;
            }else{
                // 注: 如果是单调递减就那么就去左侧查找
                right = mid - 1;
            }
            // 注: 如果是波谷, 那么既可以选择左侧也可以选择右侧
        }
        return mid;
    }

}
