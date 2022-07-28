package chapter05.binary;

/**
 * <h2>搜索二维矩阵问题</h2>
 * <h3>1. 搜索二维矩阵</h3>
 * <h3>2. 搜索二维矩阵 II</h3>
 */
public class SearchMatrix {

    /**
     * <h3>思路: 搜索二维矩阵</h3>
     * <h3>1. 两次二分法: 这种方式只能分成两个方法, 没有办法合成一个方法</h3>
     * <h3>2. 一次二分法: 这个本质上是转为一维数组来做, 可以物理上转换再做, 也可以仅在逻辑上转换</h3>
     * <h3>3. 每行二分: 这个做法也是可以的, 但是对于这个题没有必要</h3>
     */
    private static boolean searchMatrix1(int[][] matrix, int target){
        int index;
        // 1. 如果没有在第一列没有找到比自己小的数, 那么直接返回, 因为矩阵里不可能有等于自己的数了
        if((index = searchMatrixCol(matrix, target, 0)) == -1)
            return false;
        // 2. 在这个比自己小的数所在行进行二分, 那么就可以找到自己是否存在了
        return searchMatrixRow(matrix, target, index);
    }
    /**
     * <h3>本质: 查找第一列中最后一个比目标值小的数</h3>
     * <h3>&nbsp&nbsp&nbsp 1. 找到第一列中最后一个比目标值小的数, 前面的所有行中的数肯定都比目标值小, 所以就不再考虑</h3>
     * <h3>&nbsp&nbsp&nbsp 2. 后面的行肯定比目标值大, 所以目标值最有可能在这行中, 所以只需要再对这行进行二分</h3>
     * <h3>问题 1: 为什么第二题不可以这么做</h3>
     * <h3>&nbsp&nbsp&nbsp 1. 因为行与行之间没有明确的这种大小关系, 即使你找到第一列最后一个比目标值小的数</h3>
     * <h3>&nbsp&nbsp&nbsp 2. 前面的行中还有可能存在更大的数, 不能断定目标值就在这行里, 完全可能在前面的行中</h3>
     * <h3>问题 2: 为什么不先对行进行二分?</h3>
     * <h3>&nbsp&nbsp&nbsp 1.道理和刚才一样, 列与列间没有明确的大小关系, 前一列很可能存在比目标大的数, 更加接近目标值</h3>
     */
    private static int searchMatrixCol(int[][] matrix, int target, int col){
        int index = -1;
        int left = 0, right = matrix.length - 1;
        while(left <= right){
            int mid = left + ((right - left) >> 1);
            if (matrix[mid][col] == target)
                return mid;
            if (matrix[mid][col] > target)
                right = mid - 1;
            if (matrix[mid][col] < target){
                index = mid;
                left = mid + 1;
            }
        }
        return index;
    }
    private static boolean searchMatrixRow(int[][] matrix, int target, int row){
        int left = 0, right = matrix[0].length - 1;
        while(left <= right){
            int mid = left + ((right - left) >> 1);
            if (matrix[row][mid] == target)
                return true;
            if (matrix[row][mid] > target)
                right = mid - 1;
            if (matrix[row][mid] < target)
                left = mid + 1;
        }
        return false;
    }

    /**
     * <h3>本质: 转换成一维数组</h3>
     */
    private static boolean searchMatrix2(int[][] matrix, int target){
        int row = matrix.length, col = matrix[0].length;
        int left = 0, right = row * col - 1;
        while (left <= right){
            int mid = left + ((right - left) >> 1);
            int rowIndex = mid / col, colIndex = mid % col;
            if (matrix[rowIndex][colIndex] == target)
                return true;
            if (matrix[rowIndex][colIndex] > target)
                right = mid - 1;
            if (matrix[rowIndex][colIndex] < target)
                left = mid + 1;
        }
        return false;
    }


    /**
     * <h3>思路: 搜索二维矩阵 II</h3>
     * <h3>1. 暴力搜素: O(n^2)</h3>
     * <h3>2. 每行二分查找: O(n * logn): 每行都是有序的自然可以这么做</h3>
     * <h3>3. Z 字形查找: O(m + n)</h3>
     */
    private static boolean searchMatrix3(int[][] matrix, int target){
        int row = 0, coloum = matrix[0].length - 1;
        while(row <= matrix.length - 1 && coloum >= 0){
            if(matrix[row][coloum] == target){
                return true;
            }else if (matrix[row][coloum] > target){
                coloum--;
            }else{
                row++;
            }
        }
        return false;
    }

    private static boolean searchMatrix4(int[][] matrix, int target){
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
