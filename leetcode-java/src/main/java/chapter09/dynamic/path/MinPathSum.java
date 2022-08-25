package chapter09.dynamic.path;

public class MinPathSum {

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

}
