package chapter05.matrix;

/**
 * <h2>重塑矩阵</h2>
 */
public class ReshapeMatrix {

    /**
     * <h3>思路: 重塑矩阵</h3>
     * <h3>1. 直接从二维矩阵转到二维矩阵</h3>
     * <h3>2. 利用一维矩阵作为桥接</h3>
     * <h3>注: 这个题并不难, 但是这种映射的思想非常关键, 很有可能在其他题中用到</h3>
     */
    private static int[][] matrixReshape(int[][] matrix, int row, int col){
        // 注: 不符合条件的输出原矩阵
        if (matrix.length * matrix[0].length != row * col)
            return matrix;
        int length = matrix[0].length;
        int[][] reshape = new int[row][col];
        for (int index = 0;index < row * col;index++){
            reshape[index / col][index % col] = matrix[index / length][index % length];
        }
        return reshape;
    }

}
