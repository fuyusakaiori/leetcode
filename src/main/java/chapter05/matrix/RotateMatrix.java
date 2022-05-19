package chapter05.matrix;

/**
 * <h2>旋转矩阵</h2>
 */
public class RotateMatrix {


    /**
     * <h3>思路: 旋转矩阵</h3>
     * <h3>1. 额外矩阵</h3>
     * <h3>2. 原地旋转</h3>
     * <h3>3. 转置 + 翻转</h3>
     * <h3>扩展: 旋转 180、270 如何处理</h3>
     * <h3>1. 原地旋转扩展到 180、270 还是很好做的, 只要知道旋转公式, 也就是对应的位置就行</h3>
     * <h3>2. 翻转的话: 180 先上下翻转, 再左右翻转; 270 先转置, 再上下翻转</h3>
     */
    private static void rotate1(int[][] matrix){
        int length = matrix.length;
        int[][] rotate = new int[length][length];
        // 1. 移动到新矩阵中
        for(int row = 0;row < length;row++){
            for(int col = 0;col < length;col++){
                // 注: 第一行的数旋转后会移动到倒数第一列, 数字之间的相对顺序不会变
                rotate[col][length - row - 1] = matrix[row][col];
                // 注: 说实话第一次做的时候, 这个关系都没看出来
            }
        }
        // 2. 放回原矩阵中
        for(int row = 0;row < length;row++){
            System.arraycopy(rotate[row], 0, matrix[row], 0, length);
        }
    }

    private static void rotate2(int[][] matrix){
        int length = matrix.length;
        for (int row = 0;row < length / 2;row++){
            for (int col = 0;col < (length + 1) / 2;col++){
                // 注: 临时变量保存当前值
                int temp = matrix[row][col];
                matrix[row][col] = matrix[length - col - 1][row];
                matrix[length - col - 1][row] = matrix[length - row - 1][length - col - 1];
                matrix[length - row - 1][length - col - 1] = matrix[col][length - row - 1];
                matrix[col][length - row - 1] = temp;
            }
        }
    }

    private static void rotate3(int[][] matrix){
        int length = matrix.length;
        // 1. 转置
        for (int row = 0;row < length;row++){
            for (int col = row;col < length;col++){
                int temp = matrix[row][col];
                matrix[row][col] = matrix[col][row];
                matrix[col][row] = temp;
            }
        }
        // 2. 左右翻转
        for (int col = 0; col < length / 2; col++){
            for (int row = 0; row < length; row++){
                int temp = matrix[row][col];
                matrix[row][col] = matrix[row][length - 1 - col];
                matrix[row][length - 1 - col] = temp;
            }
        }
    }
}
