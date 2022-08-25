package chapter09.dynamic.path;

/**
 * <h3>地下城游戏</h3>
 */
public class DungeonGame {

    /**
     * <h3>地下城游戏</h3>
     * <h3>1. 地下城游戏和前面的路径问题不同, 只能采用从下向上的方式填写</h3>
     * <h3>2. 建议每次还是先考虑递归的方式, 直接写动态规划还是存在比较高的难度</h3>
     */
    public static int calculateMinimumHP1(int[][] dungeon){
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

    /**
     * <h3>动态规划</h3>
     */
    public static int calculateMinimumHP2(int[][] dungeon){
        int rowLength = dungeon.length, colLength = dungeon[0].length;
        int[][] dp = new int[rowLength + 1][colLength + 1];
        // 1. 初始化
        for(int index = 0;index < dp.length;index++){
            dp[index][colLength] = Integer.MAX_VALUE;
        }
        for(int index = 0;index < dp[0].length;index++){
            dp[rowLength][index] = Integer.MAX_VALUE;
        }
        for(int row = rowLength - 1;row >= 0;row--){
            for(int col = colLength - 1;col >= 0;col--){
                if(row == rowLength - 1 && col == colLength - 1){
                    dp[row][col] = dungeon[row][col] > 0 ? 1: 1 - dungeon[row][col];
                    continue;
                }
                dp[row][col] = Math.max(1,
                        Math.min(dp[row + 1][col], dp[row][col + 1]) - dungeon[row][col]);
            }
        }
        return dp[0][0];
    }


}
