package chapter06;

import java.util.Arrays;
import java.util.List;

/**
 * <h2>路径问题</h3>
 * <h3>1. 不同路径 & 机器人到到达指定位置方法数/h3>
 * <h3>2. 不同路径 II</h3>
 * <h3>3. 最小路径和</h3>
 * <h3>4. 三角形最小路径和</h3>
 *
 * <p>注: 所有路径问题的尝试思路都是非常明确的, 并且递归退出条件非常好想</p>
 * <p></p>
 * <h2>状态转移方程</h2>
 * <h3>dp[i][j] = dp[i - 1][j] + dp[i][j - 1](根据题目需要自行扩展)</h3>
 * <h3>dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + value(根据题目需求自行扩展)</h3>
 */
public class PathProblem
{
    public static void main(String[] args)
    {

    }

    /**
     * <h3>1. 不同路径</h3>
     * <p>不同路径、机器人到达指定位置方法数本质都是一个题</p>
     * <p>(1) 二维表 + 机器人只能够向下和向右移动</p>
     * <p>(2) 一维表 + 机器人只能够左右移动</p>
     * <p>(3) 二维表 + 机器人可以上下左右移动</p>
     * <p>注: 影响的无非就是边界条件的多少, 动态转移方程完全一致</p>
     * <p>注: 递归是从终点向起点移动的, 动态规划是从起点向终点移动的</p>
     * <p></p>
     * <p>注: 这个题没有办法状态压缩</p>
     * @return 达到终点的方法数
     */
    private static int uniquePaths(int row, int column){
        int[][] dp = new int[row][column];
        Arrays.fill(dp[0], 1);
        for(int index = 0;index < dp.length;index++){
            dp[index][0] = 1;
        }
        for(int i = 1;i < dp.length;i++){
            for(int j = 1;j < dp[i].length;j++){
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }
        return dp[row - 1][column - 1];
    }

    /**
     * <p>暴力递归 + 记忆化搜索</p>
     * <p>注: 这里的题目是以第一个题目为准</p>
     */
    private static int dfs(int row, int column, int[][] dp){
        if (row == 0 && column == 0) return 1;
        if (row < 0 || column < 0) return 0;
        // 注: 如果某个点走不到终点, 那么结果就是 0
        if (dp[row][column] != -1) return dp[row][column];

        return dp[row][column] = dfs(row - 1, column, dp) + dfs(row, column - 1, dp);
    }

    /**
     * <h3>2. 不同路径 II</h3>
     * <p>这个题目和不同路径是相同的, 不写代码了</p>
     */
    private static int uniquePathsWithObstacles(int[][] obstacleGrid){
        return 0;
    }

    /**
     * <h3>3. 最小路径和</h3>
     */
    private static int minPathSum(int[][] grid){
        int row = grid.length;
        int column = grid[0].length;
        int[][] dp = new int[row][column];
        // 1. Base Case
        // 1.1 因为第一行或者第一列到达起点的走法只有一种, 就是走到尾
        // 1.2 如果在迭代的时候再填值, 就需要单独处理越界的问题, 所以不如提前填好
        for(int index = 0;index < dp[0].length;index++){
            dp[0][index] = index - 1 >= 0 ? dp[0][index - 1] + grid[0][index] : grid[0][index];
        }
        for(int index = 0;index < dp.length;index++){
            dp[index][0] = index - 1 >= 0 ? dp[index - 1][0] + grid[index][0] : grid[index][0];
        }
        // 2. 开始迭代
        for(int x = 1;x < dp.length;x++){
            for(int y = 1;y < dp[x].length;y++){
                dp[x][y] = Math.min(dp[x - 1][y], dp[x][y - 1]) + grid[x][y];
            }
        }
        return dp[row - 1][column - 1];
    }

    /**
     * 暴力递归 => 记忆化搜索
     */
    private static int dfs(int[][] grid, int x, int y, int[][] dp){
        // 0. 恰好走到起点的时候就认为这种走法是可行的
        if (x == 0 && y == 0) return grid[0][0];
        // 1. 如果出界就认为走法是不可行的
        if (x < 0 || y < 0) return Integer.MAX_VALUE;
        if (dp[x][y] != -1) return dp[x][y];
        // 2. 开始尝试
        return Math.min(dfs(grid, x - 1, y, dp), dfs(grid, x, y - 1, dp)) + grid[x][y];
    }

    /**
     * <h3>4. 三角形最小路径和</h3>
     * <p>动态规划 => 状态压缩</p>
     * <p>注: 第一行只会依赖第二行, 而不会依赖第三行, 所以不需要设置二维表结构</p>
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

    /**
     * <p>暴力递归 => 记忆化搜索</p>
     * <p>注: 在判断最大最小的之后还需要做加法, 容易导致溢出而判断出错, 所以最大最小的初始值要稍微考虑下</p>
     * <p>注: 如何判断该点已经被计算过, 不要使用计算过程中可能出现的结果作为标志, 最好使用包装类数组, 完全避免这个问题</p>
     */
    private static int dfs(List<List<Integer>> triangle, int x, int y, Integer[][] dp){
        if (x == triangle.size()) return 0;
        if (dp[x][y] != null) return dp[x][y];

        return Math.min(dfs(triangle, x + 1, y, dp), dfs(triangle, x + 1, y + 1, dp))
                       + triangle.get(x).get(y);
    }
}
