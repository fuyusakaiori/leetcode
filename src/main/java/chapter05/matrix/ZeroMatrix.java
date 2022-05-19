package chapter05.matrix;

import java.util.Arrays;

/**
 * <h2>矩阵置零</h2>
 * <h3>注: 这个题的思路和数独非常类似</h3>
 */
public class ZeroMatrix {

    /**
     * <h3>O(m + n)</h3>
     */
    private static void setZeros1(int[][] matrix){
        // 1. 记录有零所在的行和列
        boolean[] rowSet = new boolean[matrix.length];
        boolean[] colSet = new boolean[matrix[0].length];
        int rowLength = matrix.length, colLength = matrix[0].length;
        for (int row = 0; row < rowLength; row++){
            for (int col = 0; col < colLength; col++){
                if(matrix[row][col] == 0){
                    rowSet[row] = true;
                    colSet[col] = true;
                }
            }
        }
        // 2. 如果该位置所在的行或列有零就直接更新
        for (int row = 0; row < rowLength; row++){
            for (int col = 0; col < colLength; col++){
                if (rowSet[row] || colSet[col])
                    matrix[row][col] = 0;
            }
        }
    }

    /**
     * <h3>O(1)</h3>
     */
    private static void setZeros2(int[][] matrix){
        boolean rowFlag = false, colFlag = false;
        int rowLength = matrix.length, colLength = matrix[0].length;
        // 1. 检查第一行是否有零
        for (int index = 0;index < colLength;index++){
            if (matrix[0][index] == 0){
                rowFlag = true;
                break;
            }
        }
        // 2. 检查第一列是否有零
        for (int index = 0;index < rowLength;index++){
            if (matrix[index][0] == 0){
                colFlag = true;
                break;
            }
        }
        // 3. 标记
        for (int row = 0; row < rowLength; row++){
            for (int col = 0; col < colLength; col++){
                if(matrix[row][col] == 0){
                    matrix[row][0] = 0;
                    matrix[0][col] = 0;
                }
            }
        }
        // 4. 检查
        for (int row = 0; row < rowLength; row++){
            for (int col = 0; col < colLength; col++){
                if(matrix[row][0] == 0 || matrix[0][col] == 0)
                    matrix[row][col] = 0;
            }
        }
        if (rowFlag)
            Arrays.fill(matrix[0], 0);
        if (colFlag){
            for (int index = 0;index < rowLength;index++){
                matrix[index][0] = 0;
            }
        }
    }

}
