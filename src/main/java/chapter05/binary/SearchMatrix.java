package chapter05.binary;

/**
 * <h2>搜索二维矩阵问题</h2>
 * <h3>1. 搜索二维矩阵</h3>
 * <h3>2. 搜索二维矩阵 II</h3>
 * <h3>注: 只要数组有序的题目或者要求时间复杂度为 logn 的题目都要优先考虑二分查找</h3>
 */
public class SearchMatrix {

    /**
     * <h3>思路: 搜索二维矩阵</h3>
     * <h3>1. 两次二分</h3>
     * <h3>2. 转成一位数组</h3>
     * <h3>注: 这个题是可以用第二题的任何一个做法去做的, 但是时间复杂度会高一些, 这个题有更特殊的做法</h3>
     * <h3>注: 实际上我做的第二题的时候就想的是这个类似的做法, 但是第二题不能用第一题的做法</h3>
     */
    private static boolean searchMatrixI(int[][] matrix, int target){
        int rowIndex = searchMatrixColumn(matrix, target);
        if(rowIndex == -1)
            return false;
        return matrix[rowIndex][0] == target || searchMatrixRow(matrix, target, rowIndex);
    }

    /**
     * <h3>思路: 查找第一列中最后一个比目标值小的数</h3>
     * <h3>1. 只要找到第一列中最后一个比目标值小的数, 那么之前行的所有数肯定都是比目标值小的</h3>
     * <h3>2. 之后的行的第一个数肯定比目标值大, 所以目标值一定就在这行之中, 所以只需要再对这行进行二分搜素</h3>
     * <h3>注: 为什么第二题不可以这么做</h3>
     * <h3>1. 因为行与行之间没有明确的这种大小关系, 即使你找到第一列最后一个比目标值小的数</h3>
     * <h3>2. 前面的行中还有可能存在更大的数, 不能断定目标值就在这行里, 完全可能在前面的行中</h3>
     * <h3>注: 为什么不先对行进行二分?</h3>
     * <h3>1.道理和刚才一样, 列与列之间没有明确的大小关系, 前一列很有可能存在比目标值大的数, 更加接近目标值</h3>
     */
    private static int searchMatrixColumn(int[][] matrix, int target){
        int left = 0, right = matrix.length - 1;
        int result = -1;
        while (left <= right){
            int mid = (left + right) >> 1;
            if(matrix[mid][0] == target)
                return mid;
            if (matrix[mid][0] < target){
                left = mid + 1;
                result = mid;
            }else{
                right = mid - 1;
            }
        }
        return result;
    }

    private static boolean searchMatrixRow(int[][] matrix, int target, int rowIndex){
        int left = 0, right = matrix[rowIndex].length - 1;
        while(left <= right){
            int mid = (left + right) >> 1;
            if(matrix[rowIndex][mid] == target)
                return true;
            if(matrix[rowIndex][mid] < target)
                left = mid + 1;
            else
                right = mid - 1;
        }
        return false;
    }

    /**
     * <h3>二维矩阵拉成一维数组</h3>
     */
    private static boolean searchMatrixReflection(int[][] matrix, int target){
        int length = matrix[0].length;
        int left = 0, right = matrix.length * matrix[0].length - 1;
        while(left <= right){
            int mid = (left + right) >> 1;
            int row = mid / length;
            int column = mid % length;
            if (matrix[row][column] == target)
                return true;
            if (matrix[row][column] > target)
                right = mid - 1;
            if (matrix[row][column] < target)
                left = mid + 1;
        }

        return false;
    }


    /**
     * <h3>思路: 搜索二维矩阵 II</h3>
     * <h3>1. 直接暴力搜素: O(n^2)</h3>
     * <h3>2. 直接对每行使用二分查找: O(n * logn)</h3>
     * <h3>3. Z 字形查找: O(m + n)</h3>
     * <h3>注: 先对第一行二分, 然后对大于目标值的列进行二分</h3>
     * <h3>注: 这个方式是不行的, 小于目标值的列也是可能存在目标值的</h3>
     */
    private static boolean searchMatrixIIZSearch(int[][] matrix, int target){
        int row = 0, coloum = matrix[0].length - 1;
        while(row <= matrix.length - 1 && coloum >= 0){
            if(matrix[row][coloum] == target) return true;
            if(matrix[row][coloum] > target) coloum--;
            if (matrix[row][coloum] < target) row++;
        }
        return false;
    }

    private static boolean searchMatrixIIBinarySearch(int[][] matrix, int target){
        for (int[] ints : matrix) {
            if (binarySearch(ints, target)) return true;
        }
        return false;
    }

    private static boolean binarySearch(int[] nums, int target){
        int left = 0, right = nums.length - 1;
        while(left <= right){
            int mid = (left + right) >> 1;
            if(nums[mid] == target) return true;
            if(nums[mid] > target) right = mid - 1;
            if (nums[mid] < target) left = mid + 1;
        }
        return false;
    }


}
