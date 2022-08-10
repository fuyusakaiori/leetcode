package chapter11.dynamic;

import java.util.List;

/**
 * <h2>路径和</h2>
 * <h3>1. 不同路径</h3>
 * <h3>2. 不同路径 II</h3>
 * <h3>3. 最小路径和</h3>
 * <h3>4. 三角形最小路径和</h3>
 * <h3>5. 地下城游戏</h3>
 */
public class PathSum {

    /**
     * <h3>思路: 不同路径</h3>
     * <h3>1. 动态规划</h3>
     * <h3>1.1 从上向下填更加符合动态规划的思路</h3>
     * <h3>1.2 从下向上是从递归的思路考虑的</h3>
     * <h3>2. 排列组合</h3>
     */
    private static int uniquePaths1(int rowLength, int colLength){
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

    private static int uniquePaths2(int rowLength, int colLength){
        int[][] dp = new int[rowLength][colLength];
        // 1. 初始化第一行
        for (int index = 0;index < rowLength;index++){
            dp[index][0] = 1;
        }
        // 2. 初始化第一列
        for (int index = 0;index < colLength;index++){
            dp[0][index] = 1;
        }
        // 3. 填充剩余各自
        for (int row = 1;row < rowLength;row++){
            for (int col = 1;col < colLength;col++){
                dp[row][col] = dp[row - 1][col] + dp[row][col - 1];
            }
        }
        return dp[rowLength - 1][colLength - 1];
    }

    private static int uniquePaths3(int rowLength, int colLength){
        return dfs(rowLength, colLength, 0, 0, new int[rowLength][colLength]);
    }

    private static int dfs(int rowLength, int colLength, int row, int col, int[][] dp){
        if (row >= rowLength || col > colLength)
            return 0;
        if (row == rowLength - 1 && col == colLength - 1)
            return 1;
        if (dp[row][col] != 0)
            return dp[row][col];
        return dfs(rowLength, colLength, row + 1, col, dp)
                + dfs(rowLength, colLength, row, col + 1, dp);
    }

    /**
     * <h3>思路: 不同路径 II</h3>
     */
    private static int uniquePathsWithObstacles1(int[][] obstacleGrid) {
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
    private static int uniquePathsWithObstacles2(int[][] obstacleGrid){
        int rowLength = obstacleGrid.length, colLength = obstacleGrid[0].length;
        int[][] dp = new int[rowLength][colLength];
        // 1. 初始化第一行
        for (int index = 0;index < rowLength && obstacleGrid[index][0] == 0;index++){
            dp[index][0] = 1;
        }
        // 2, 初始化第一列
        for (int index = 0;index < colLength && obstacleGrid[0][index] == 0;index++){
            dp[0][index] = 1;
        }
        // 3. 填充剩余格子
        for (int row = 1;row < rowLength;row++){
            for (int col = 1;col < colLength;col++){
                if (obstacleGrid[row][col] != 1){
                    dp[row][col] = dp[row - 1][col] + dp[row][col - 1];
                }
            }
        }
        return dp[rowLength - 1][colLength - 1];
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

    /**
     * <h3>思路: 地下城游戏</h3>
     * <h3>1. 地下城游戏和前面的路径问题不同, 只能采用从下向上的方式填写</h3>
     * <h3>2. 建议每次还是先考虑递归的方式, 直接写动态规划还是存在比较高的难度</h3>
     */
    public static int calculateMinimumHP(int[][] dungeon){
        return dfs(0, 0, dungeon, new int[dungeon.length][dungeon[0].length]);
    }

    public static int dfs(int row, int col, int[][] dungeon, int[][] dp){
        // 1. 如果骑士越界, 那么就要返回最大值, 不要返回最小值
        if (row >= dungeon.length || col >= dungeon[0].length)
            return Integer.MAX_VALUE;
        // 2. 如果骑士到达目的地, 那么就返回各自的值:
        // 2.1 如果是负数, 那么就返回绝对值, 表示进入这个格子至少的生命值
        // 2.2 如果是正数, 那么直接返回默认生命值
        if (row == dungeon.length - 1 && col == dungeon[0].length - 1)
            return dungeon[row][col] > 0 ? 1: 1 - dungeon[row][col];
        if (dp[row][col] != 0)
            return dp[row][col];
        // 3. 获取进入右侧和下侧各自的最少生命值
        int right = dfs(row + 1, col, dungeon, dp);
        int down = dfs(row, col + 1, dungeon, dp);
        // 4. 比较哪个格子需要的生命值最少, 然后减去当前格子的生命值
        // 4.1 如果当前格子是负数, 那么相减的话就表示还需要这么多生命值才可以或者
        // 4.2 如果当前格子是正数, 那么就表示表示不需要这么多生命值, 不过要确保骑士是活着的
        return dp[row][col] = Math.max(1, Math.min(right, down) - dungeon[row][col]);
    }

}
