package chapter11.dynamic;

import java.util.List;

/**
 * <h2>路径和</h2>
 * <h3>1. 不同路径</h3>
 * <h3>2. 不同路径 II</h3>
 * <h3>3. 最小路径和</h3>
 * <h3>4. 三角形最小路径和</h3>
 */
public class PathSum {

    /**
     * <h3>思路: 不同路径</h3>
     * <h3>1. 动态规划</h3>
     * <h3>2. 排列组合</h3>
     * <h3>注: 这已经是第三次写这个题了, 就直接动态规划了</h3>
     */
    private static int uniquePaths(int rowLength, int colLength){
        int[][] dp = new int[rowLength + 1][colLength + 1];
        dp[rowLength - 1][colLength - 1] = 1;
        // 注: 没有必要提前初始化第一行或者第一列, 都是可以直接在双重循环中填充好的
        for(int row = rowLength - 1;row >= 0;row--){
            for(int col = colLength - 1;col >= 0;col--){
                dp[row][col] += dp[row + 1][col] + dp[row][col + 1];
            }
        }
        return dp[0][0];
    }

    /**
     * <h3>思路: 不同路径 II</h3>
     */
    private static int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int rowLength = obstacleGrid.length, colLength = obstacleGrid[0].length;
        // 注: 如果终点直接被障碍阻挡, 那么直接返回, 因为肯定不存在到达终点的路径
        if(obstacleGrid[rowLength - 1][colLength - 1] == 1)
            return 0;
        int[][] dp = new int[rowLength + 1][colLength + 1];
        dp[rowLength - 1][colLength - 1] = 1;
        for(int row = rowLength - 1;row >= 0;row--){
            for(int col = colLength - 1;col >= 0;col--){
                if(obstacleGrid[row][col] != 1){
                    dp[row][col] += dp[row + 1][col] + dp[row][col + 1];
                }
            }
        }
        return dp[0][0];
    }

    /**
     * <h3>思路: 最小路径和</h3>
     */
    private static int minPathSum(int[][] grid){
        int rowLength = grid.length, colLength = grid[0].length;
        int[][] dp = new int[rowLength][colLength];
        // 注: 这里需要提前填充
        for(int index = 0;index < rowLength;index++){
            dp[index][0] = index > 0 ? dp[index - 1][0] + grid[index][0] : grid[index][0];
        }
        for(int index = 0;index < colLength;index++){
            dp[0][index] = index > 0 ? dp[0][index - 1] + grid[0][index] : grid[0][index];
        }
        for(int row = 1;row < dp.length;row++){
            for(int col = 1;col < dp[0].length;col++){
                dp[row][col] = Math.min(dp[row - 1][col], dp[row][col - 1]) + grid[row][col];
            }
        }

        return dp[rowLength - 1][colLength - 1];
    }

    /**
     * <h3>思路: 三角形路径和</h3>
     */
    private static int minimumTotal(List<List<Integer>> triangle) {
        int rowLength = triangle.size(), colLength = triangle.get(rowLength - 1).size();
        int[][] dp = new int[rowLength + 1][colLength + 1];

        for(int row = triangle.size() - 1;row >= 0;row--){
            for(int col = triangle.get(row).size() - 1;col >= 0;col--){
                int value = triangle.get(row).get(col);
                dp[row][col] = Math.min(dp[row + 1][col], dp[row + 1][col + 1]) + value;
            }
        }

        return dp[0][0];
    }

}
