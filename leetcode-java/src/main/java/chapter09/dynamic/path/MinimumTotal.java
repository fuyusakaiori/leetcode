package chapter09.dynamic.path;

import jdk.internal.org.objectweb.asm.tree.analysis.Value;

import java.util.List;

/**
 * <h3>三角形最小路径和</h3>
 */
public class MinimumTotal {

    /**
     * <h3>递归</h3>
     */
    public static int minimumTotal1(List<List<Integer>> triangle) {
        Integer[][] dp = new Integer[triangle.size()][triangle.size()];
        return dfs(0, 0, triangle, dp);
    }

    public static int dfs(int row, int col, List<List<Integer>> triangle, Integer[][] dp){
        if(row == triangle.size())
            return 0;
        if(dp[row][col] != null)
            return dp[row][col];
        int value = triangle.get(row).get(col);

        return dp[row][col] = Math.min(
                dfs(row + 1, col, triangle, dp),
                dfs(row + 1, col + 1, triangle, dp)) + value;
    }

    /**
     * <h3>动态规划: 普通方式</h3>
     */
    public static int minimumTotal2(List<List<Integer>> triangle){
         int length = triangle.size();
         // 1. 表结构: dp[i][j] 表示到终点的最小路径和
         int[][] dp = new int[length + 1][length + 1];
         // 2. 填表
         for (int row = length - 1;row >= 0;row--){
             for (int col = row;col >= 0;col--){
                 dp[row][col] = triangle.get(row).get(col)
                                        + Math.min(dp[row + 1][col], dp[row + 1][col + 1]);
             }
         }
        return dp[0][0];
    }

    /**
     * <h3>动态规划: 最优化</h3>
     */
    public static int minimumTotal3(List<List<Integer>> triangle){
        int length = triangle.size();
        // 1. 直接使用原二维表记录达到终点的最小路径
        for (int row = length - 2;row >= 2;row--){
            for (int col = row;col >= 0;col--){
                int minValue = Math.min(triangle.get(row + 1).get(col), triangle.get(row + 1).get(col + 1));
                triangle.get(row).set(col, minValue + triangle.get(row).get(col));
            }
        }
        return triangle.get(0).get(0);
    }

}
