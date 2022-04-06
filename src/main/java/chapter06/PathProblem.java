package chapter06;

import java.util.Arrays;
import java.util.List;

/**
 * <h2>路径问题</h3>
 * <h3>1. 不同路径</h3>
 * <h3>2. 不同路径 II</h3>
 * <h3>3. 最小路径和</h3>
 * <h3>4. 三角形最小路径和</h3>
 * <h2>状态转移方程</h2>
 * <h3>dp[i][j] = dp[i - 1][j] + dp[i][j - 1](根据题目需要自行扩展)</h3>
 * <h3>dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + value(根据题目需求自行扩展)</h3>
 */
public class PathProblem {

    /**
     * <h3>思路: 不同路径</h3>
     * <h3>1. 动态规划: 可以从上向下遍历, 也可以从下向上遍历</h3>
     * <h3>2. 排列组合</h3>
     * @return 达到终点的方法数
     */
    private static int uniquePathsTopDown(int row, int column){
       int[][] dp = new int[row][column];
       // 1. 第一行和第一列的路径数量始终都是为 1 的, 所以可以直接填充
        for (int first = 0, second = 0;first < row || second < column;first++, second++){
            if (first < row) dp[first][0] = 1;
            if (second < column) dp[0][second] = 1;
        }
        // 2. 开始填表: 第一行和第一列都已经填过了
        for (int rowIndex = 1;rowIndex < row;rowIndex++){
            for (int columnIndex = 1;columnIndex < column;columnIndex++){
                dp[rowIndex][columnIndex] = dp[rowIndex - 1][columnIndex] + dp[rowIndex][columnIndex - 1];
            }
        }
        return dp[row - 1][column - 1];
    }

    private static int uniquePathsDownTop(int row, int column){
        int[][] dp = new int[row + 1][column + 1];
        dp[row - 1][column - 1] = 0;
        for (int rowIndex = row - 1;rowIndex >= 0;rowIndex--){
            for (int columnIndex = column - 1;columnIndex >= 0;columnIndex--){
                dp[rowIndex][columnIndex] += dp[rowIndex + 1][columnIndex] + dp[rowIndex][columnIndex + 1];
            }
        }
        return dp[0][0];
    }

    /**
     * <h3>暴力递归 + 记忆化搜索</h3>
     */
    private static int dfs(int row, int column, int[][] dp){
        if (row == 0 && column == 0) return 1;
        if (row < 0 || column < 0) return 0;
        // 注: 如果某个点走不到终点, 那么结果就是 0
        if (dp[row][column] != -1) return dp[row][column];

        return dp[row][column] = dfs(row - 1, column, dp) + dfs(row, column - 1, dp);
    }

    /**
     * <h3>排列组合</h3>
     */
    private static int arrangementCombination(int row, int column){
        long result = 1;
        for (int first = 1, second = column;second < row;first++, second++){
            // 这里可能出现溢出
            result = result * second / first;
        }
        return (int) result;
    }

    /**
     * <h3>思路: 不同路径 II</h3>
     * <h3>1. 最短的路径是多少? => 每走一步都加一, 遇见障碍物直接返回最大值</h3>
     * <h3>2. 最短路径有多少条? => 最短路径作为步数, 步数耗尽就证明不是最短</h3>
     */
    private static int uniquePathsWithObstacles(int[][] obstacleGrid){
        int row = obstacleGrid.length;
        int column = obstacleGrid[0].length;
        int[][] dp = new int[row + 1][column + 1];
        if(obstacleGrid[row - 1][column - 1] == 0)
            dp[row - 1][column - 1] = 1;
        for(int rowIndex = row - 1;rowIndex >= 0;rowIndex--){
            for(int columnIndex = column - 1;columnIndex >= 0;columnIndex--){
                if(obstacleGrid[rowIndex][columnIndex] == 0){
                    dp[rowIndex][columnIndex] += dp[rowIndex + 1][columnIndex] + dp[rowIndex][columnIndex + 1];
                }
            }
        }
        return dp[0][0];
    }



    /**
     * <h3>3. 最小路径和</h3>
     */
    private static int minPathSum(int[][] grid){
        int row = grid.length;
        int column = grid[0].length;
        int[][] dp = new int[row][column];
        for(int index = 0;index < dp[0].length;index++){
            dp[0][index] = index - 1 >= 0 ? dp[0][index - 1] + grid[0][index] : grid[0][index];
        }
        for(int index = 0;index < dp.length;index++){
            dp[index][0] = index - 1 >= 0 ? dp[index - 1][0] + grid[index][0] : grid[index][0];
        }
        for(int x = 1;x < dp.length;x++){
            for(int y = 1;y < dp[x].length;y++){
                dp[x][y] = Math.min(dp[x - 1][y], dp[x][y - 1]) + grid[x][y];
            }
        }
        return dp[row - 1][column - 1];
    }

    private static int dfs(int[][] grid, int x, int y, Integer[][] dp){
        if (x < 0 || y < 0 || x > grid.length - 1 || y > grid[0].length - 1)
            return -1;
        if (x == grid.length - 1 && y == grid[0].length - 1)
            return grid[x][y];
        if (dp[x][y] != null)
            return dp[x][y];
        int down = dfs(grid, x + 1, y, dp);
        int right = dfs(grid, x, y + 1, dp);
        if (down != -1 && right != -1)
            return Math.min(down, right) + grid[x][y];
        return dp[x][y] = (down == -1 ? right: down) + grid[x][y];
    }

    /**
     * <h3>4. 三角形最小路径和</h3>
     */
    private static int minimumTotal(List<List<Integer>> triangle){
        // 0. 直接开一个矩阵就行, 三角形不好表示, 空着的地方空着就行
        // 0. 改: 压缩为一维表结构
        int[] dp = new int[triangle.size() + 1];
        // 1. 开始迭代: 这里是自下而上开始填表的
        for (int row = triangle.size() - 1;row >= 0;row--){
            // 2. 从左向右和从右向左都是可以的
            for (int column = 0;column < triangle.get(row).size();column++){
                // 改: 基本不变
                dp[column] = Math.min(dp[column], dp[column + 1]) + triangle.get(row).get(column);
            }
        }
        return dp[0];
    }

    private static int dfs(List<List<Integer>> triangle, int x, int y, Integer[][] dp){
        if (x == triangle.size()) return 0;
        if (dp[x][y] != null) return dp[x][y];

        return Math.min(dfs(triangle, x + 1, y, dp), dfs(triangle, x + 1, y + 1, dp))
                       + triangle.get(x).get(y);
    }
}
