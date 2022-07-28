package chapter05.matrix;

import java.util.ArrayList;
import java.util.List;

/**
 * <h2>数独问题</h2>
 * <h3>1. 有效数独</h3>
 * <h3>2. 解数独</h3>
 * <h3>3. 锯齿形数独</h3>
 */
public class Sudoku {

    /**
     * <h3>思路: 有效数独</h3>
     * <h3>1. 暴力解</h3>
     * <h3>2。暴力解: 优化</h3>
     * <h3>注: 这个题没有什么特别好的解法, 就是采用暴力解, 然后在暴力解上进行简单的优化</h3>
     * <h3>注: 这种模拟题本身并不是很复杂, 但是模拟的过程是比较消耗耐心的</h3>
     */
    private static boolean isValidSudoku1(char[][] board){
        // 注: 这种暴力解的方式显然存在很多的重复比较, 所以之后会采用相应的数据结构进行优化
        for (int row = 0;row < board.length;row++){
            for (int col = 0;col < board.length;col++){
                if (!checkNumber(board, row, col))
                    return false;
            }
        }
        return true;
    }

    private static boolean checkNumber(char[][] board, int row, int col){
        // 1. 检测行是否满足要求
        for (int index = 0;index < board.length;index++){
            if (board[row][index] == '.')
                continue;
            if (col != index && board[row][col] == board[row][index])
                return false;
        }
        // 2. 检测列是否满足要求
        for (int index = 0;index < board.length;index++){
            if (board[row][col] == '.')
                continue;
            if (row != index && board[row][col] == board[index][col])
                return false;
        }
        // 3. 检测所在宫是否满足要求: 最简单的方式就是将当前元素和宫中的所有元素进行比较, 现在问题在于如何确定所在宫
        int vertical = (row / 3) * 3, level = (col / 3) * 3;
        // 注: 矩阵转换成矩阵会比一维和二维数组相互转换更加麻烦, 尤其是这里还是矩阵缩小
        for (int rowIndex = vertical;rowIndex < vertical + 3;rowIndex++){
            for (int colIndex = level;colIndex < level + 3;colIndex++){
                if (board[rowIndex][colIndex] == '.')
                    continue;
                if (row != rowIndex && col != colIndex && board[rowIndex][colIndex] == board[row][col])
                    return false;
            }
        }
        return true;
    }

    private static boolean isValidSudoku2(char[][] board){
        // 1. 采用哈希表来记录每行已经出现过的数字, 不过这里可以将哈希表简化成数组
        int[][] rowSet = new int[9][9];
        int[][] colSet = new int[9][9];
        // 注: 这里每行代表一个宫, 也就是将二维的宫转换成的一维的, 对于这个优化策略来说是非常关键的
        int[][] palace = new int[9][9];

        for (int row = 0;row < board.length;row++){
            for (int col = 0;col < board.length;col++){
                if (board[row][col] == '.')
                    continue;
                int index = board[row][col] - '0' - 1, cur = (row / 3) * 3 + col / 3;
                if (rowSet[row][index] == index + 1)
                    return false;
                if (colSet[col][index] == index + 1)
                    return false;
                // 注: 这里的转换其实可以类比二维数组转一维数组 row/3 = rowIndex, col/3 = colIndex rowIndex * 3 + colIndex
                if (palace[cur][index] == index + 1)
                    return false;
                rowSet[row][index] = index + 1;
                colSet[col][index] = index + 1;
                palace[cur][index] = index + 1;
            }
        }
        return true;
    }

    /**
     * <h3>思路: 解数独</h3>
     * <h3>注: 做这个题之前最好先做有效数独, 然后这个题就可以迎刃而解了</h3>
     */
    private static void solveSudoku(char[][] board){
        int[][] rowSet = new int[9][9];
        int[][] colSet = new int[9][9];
        int[][] palace = new int[9][9];
        // 注: 记录需要填数字的位置
        List<Integer> empty = new ArrayList<>();
        // 1. 记录数独
        for(int row = 0;row < board.length;row++){
            for(int col = 0;col < board.length;col++){
                if(board[row][col] == '.'){
                    empty.add(row * 9 + col);
                    continue;
                }
                int index = board[row][col] - '0' - 1, cur = (row / 3) * 3 + col / 3;
                rowSet[row][index] = index + 1;
                colSet[col][index] = index + 1;
                palace[cur][index] = index + 1;
            }
        }
        // 2. 填数独
        fillBlank(board, empty, rowSet, colSet, palace, 0);
    }

    private static boolean fillBlank(char[][] board, List<Integer> empty,
                                     int[][] rowSet, int[][] colSet, int[][] palace, int index){
        if(index == empty.size())
            return true;
        int location = empty.get(index);
        int row = location / 9, col = location % 9, cur = (row / 3) * 3 + col / 3;
        for(int number = 1;number <= 9;number++){
            if(rowSet[row][number - 1] != 0) continue;
            if(colSet[col][number - 1] != 0) continue;
            if(palace[cur][number - 1] != 0) continue;

            char value = (char) (number + '0');
            board[row][col] = value;
            rowSet[row][number - 1] = value;colSet[col][number - 1] = value;
            palace[cur][number - 1] = value;
            if(fillBlank(board, empty, rowSet, colSet, palace, index + 1))
                return true;
            board[row][col] = '.';
            rowSet[row][number - 1] = 0;colSet[col][number - 1] = 0;
            palace[cur][number - 1] = 0;
        }
        return false;
    }


}
