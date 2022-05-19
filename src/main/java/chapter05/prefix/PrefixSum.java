package chapter05.prefix;

import java.util.HashMap;
import java.util.Map;

/**
 * <h2>前缀和系列问题</h2>
 * <h3>1. 和为 K 的子数组</h3>
 * <h3>2. 长度最小的子数组</h3>
 * <h3>3. 一维区域和检索: 矩阵不可变</h3>
 * <h3>4. 二维区域和检索: 矩阵不可变</h3>
 * <h3>5. 除自身以外数组的乘积</h3>
 * <h3>注: 前缀和的思想其实很简单, 和动态规划或者记忆化搜索类似, 就是将之前的和保存下来</h3>
 */
public class PrefixSum {

    /**
     * <h3>思路: 和为 K 的子数组</h3>
     * <h3>1. 暴力解</h3>
     * <h3>2. 前缀和: 优化和没有优化的版本</h3>
     */
    private static int subArraySum1(int[] numbers, int target){
        int count = 0;
        for (int first = 0;first < numbers.length; first++){
            int sum = 0;
            for (int second = first; second < numbers.length; second++){
                if((sum += numbers[second]) == target)
                    count++;
            }
        }
        return count;
    }

    /**
     * <h3>1. 前缀和核心就是记录从起点到当前位置的和, 换成表达式就是下面这个</h3>
     * <h3>2. prefix[index] = prefix[index - 1] + numbers[index]</h3>
     * <h3>3. 利用前缀和题意就可以变成下面这样</h3>
     * <h3>4. prefix[right] - prefix[left - 1] = target</h3>
     * <h3>5. prefix[right]: 表示 [0, right] 的和; prefix[left - 1]: 表示 [0, left - 1] 的和</h3>
     */
    private static int subArraySum2(int[] numbers, int target){
        int[] prefix = new int[numbers.length + 1];
        for(int index = 0;index < numbers.length;index++){
            prefix[index + 1] = prefix[index] + numbers[index];
        }
        int count = 0;
        for (int left = 0; left < prefix.length; left++){
            for (int right = left + 1; right < prefix.length; right++){
                if (prefix[left] == prefix[right] - target)
                    count++;
            }
        }
        return count;
    }

    /**
     * <h3>前缀和的正确打开方式</h3>
     */
    private static int subArraySum3(int[] numbers, int target){
        int count = 0, preSum = 0;
        Map<Integer, Integer> prefix = new HashMap<>();
        prefix.put(0 ,1);
        for (int number : numbers) {
            preSum += number;
            // 1. 在哈希表中查找是否有符合条件的前缀和: 第一次查找显然是没有的, 这个也符合子数组的定义
            if (prefix.containsKey(preSum - target))
                count += prefix.get(preSum - target);
            // 2. 记得将前缀和存入
            prefix.put(preSum, prefix.getOrDefault(preSum, 0) + 1);
        }
        return count;
    }

    /**
     * <h3>思路: 一维区域和检索: 矩阵不可变</h3>
     */
    private static int sumRange(int[] nums, int left, int right) {
        int[] prefix = new int[nums.length + 1];
        for (int index = 0;index < nums.length;index++){
            prefix[index + 1] = prefix[index] + nums[index];
        }
        return prefix[right + 1] - prefix[left];
    }

    /**
     * <h3>思路: 二维区域和检索: 矩阵不可变</h3>
     * <h3>1. 一维前缀和</h3>
     * <h3>2. 二维前缀和</h3>
     * <h3>注: 可以直接使用二维数组存放前缀和, 不一定要使用一位数组</h3>
     */
    private static int sumRegion1(int[][] matrix, int row1, int col1, int row2, int col2){
        int[][] prefix = getPrefix1(matrix);
        row1++; col1++; row2++; col2++;
        return prefix[row2][col2] + prefix[row1 - 1][col1 - 1]
                       - prefix[row1 - 1][col2] - prefix[row2][col1 - 1];
    }

    private static int[][] getPrefix1(int[][] matrix){
        int rowLimit = matrix.length, columnLimit = matrix[0].length;
        // 注: 防止计算前缀和的时候越界
        int[][] prefix = new int[rowLimit + 1][columnLimit + 1];
        for (int row = 1;row <= rowLimit; row++) {
            for (int col = 1; col <= columnLimit; col++) {
                prefix[row][col] = prefix[row - 1][col] + prefix[row][col - 1]
                                           - prefix[row - 1][col - 1] + matrix[row - 1][col - 1];
            }
        }
        return prefix;
    }


    private static int sumRegion2(int[][] matrix, int row1, int col1, int row2, int col2){
        int result = 0;
        int[][] prefix = getPrefix2(matrix);
        for (int index = row1;index <= row2;index++){
            result += prefix[index][col2 + 1] - prefix[index][col1];
        }
        return result;
    }

    private static int[][] getPrefix2(int[][] matrix){
        int rowLimit = matrix.length, colLimit = matrix[0].length;
        int[][] prefix = new int[rowLimit][colLimit + 1];
        for (int row = 0; row < rowLimit; row++){
            for (int col = 0; col < colLimit; col++) {
                prefix[row][col + 1] = prefix[row][col] + matrix[row][col];
            }
        }
        return prefix;
    }
}
