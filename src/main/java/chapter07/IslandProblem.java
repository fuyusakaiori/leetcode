package chapter07;

/**
 * <h2>岛问题</h2>
 * <p>问题描述: </p>
 * <p>1. 矩阵中仅存在两个值（1、0）</p>
 * <p>2. 连在一起的一片 1 就称为一座岛</p>
 * <p>3. 现在给定一个矩阵, 求矩阵中存在多少个岛</p>
 * <p>进阶问题: 如何设计一个并行算法解决这个问题？</p>
 * <p>注: 进阶问题不会让你使用代码实现, 只需要知道思路是什么就可以了</p>
 * <p>注: 采用并查集解决</p>
 * <h2>总结</h2>
 * <p>1. 岛屿问题本质上是图问题, 所以依然是采用深度优先或者宽度优先遍历</p>
 * <p>2. LeetCode 中存在四个类似的岛屿问题, 应该都是采用深度优先或者宽度优先的做法完成</p>
 * <p>3. 不过还可以采用并查集的方式来完成, 并查集采用哈希表实现</p>
 */
public class IslandProblem
{
    /**
     * <p>1. 最简单的思路, 双重循环遍历矩阵, 然后对每个点进行深度遍历</p>
     * <p>2. 时间复杂度依然只有矩阵规模的大小: O(MxN)</p>
     */
    public static void main(String[] args)
    {
        int[][] matrix = {
                {1, 1, 1, 1, 0},
                {1, 1, 0, 1, 0},
                {1, 1, 0, 0, 0},
                {0, 0, 0, 0, 0}
        };
        System.out.println(getIslandCount(matrix));

    }

    private static int getIslandCount(int[][] matrix){
        int count = 0;
        // 0. 遍历每个结点, 然后深度遍历感染
        for(int row = 0;row < matrix.length;row++){
            for (int column = 0;column < matrix[row].length;column++){
                if (matrix[row][column] == 1){
                    count++;
                    // 1. 从这个结点开始去感染它的上下左右结点
                    infected(matrix, row, column);
                }
            }
        }
        return count;
    }

    /**
     * <p>1. 从一个点开始, 探测自己的上下左右是否已经被感染</p>
     * <p>2. 如果没有被感染, 那么立刻感染, 然后深度遍历</p>
     * <p>3. 如果已经受到感染, 那么就直接回溯</p>
     * @param matrix 矩阵
     * @param row 当前行
     * @param column 当前列
     */
    private static void infected(int[][] matrix, int row, int column){
        if (isValid(matrix, row, column)) return;
        // 2. 如果没有被感染, 那么立刻感染
        matrix[row][column] = 2;
        // 3. 继续深度遍历
        infected(matrix, row - 1, column);
        infected(matrix, row + 1, column);
        infected(matrix, row, column - 1);
        infected(matrix, row, column + 1);
    }

    /**
     * 验证当前这个点是否越界, 或者当前这个点是否已经被感染过
     */
    private static boolean isValid(int[][] matrix, int row, int column){
        return row < 0 || row > matrix.length - 1
                       || column < 0 || column > matrix[row].length - 1
                       || matrix[row][column] != 1;
    }



}
