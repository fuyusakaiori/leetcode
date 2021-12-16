package chapter06;

import java.util.Arrays;

/**
 * <h2>机器人运动</h2>
 * <p>1. 存在 N 个位置, 机器人在这 N 个位置上进行移动</p>
 * <p>2. 机器人从给定的起始位置开始移动, 最后到达给定的终点</p>
 * <p>3. 机器人在到达终点之前必须移动给定的步数 K</p>
 * <p>4. 也就是说如果在没有走到给定的步数之前, 即使已经到达终点也是不算数的</p>
 * <p>5. 每个位置之间是可以反复移动的</p>
 * <p>6. 最后问从起点到终点的走法共有多少种</p>
 */
public class RobotWalk
{
    /**
     * <p>提供三种解法</p>
     * <p>1. 暴力递归尝试</p>
     * <p>2. 记忆化搜索</p>
     * <p>3. 动态规划</p>
     */
    public static void main(String[] args)
    {
        System.out.println(robotWalkWay(5, 2, 4, 4));
    }

    /**
     * <p>采用动态规划</p>
     * @param locations 总共有多少个位置
     * @param start 机器人的起点
     * @param end 机器人的终点
     * @param steps 机器人总共需要走的步数
     * @return 多少种走法
     */
    private static int robotWalkWay(int locations, int start, int end, int steps){
        // 0. 准备表结构: 明确横轴和纵轴表示的是什么（通常都是两个可变参数）
        int[][] dp = new int[steps + 1][locations + 1];
        // 1. 初始化表结构: 第 0 列是没有任何意义, 所以不需要管; 第 0 行是可以提前知道的
        dp[0][end] = 1;
        // 2. 根据递归分析位置依赖
        for (int row = 1;row < dp.length;row++){
            for (int column = 1;column < dp[row].length;column++){
                // 2.1 边界值都是单向依赖
                // 2.2 中间值都是双向依赖
                if (column == 1)
                    dp[row][column] = dp[row - 1][column + 1];
                else if (column == locations)
                    dp[row][column] = dp[row - 1][column - 1];
                else
                    dp[row][column] = dp[row - 1][column - 1] + dp[row - 1][column + 1];
            }
        }
        return dp[steps][start];
    }

    /**
     * <p>暴力递归</p>
     * <p>核心思路: 每个位置都可以选择向前走还是向后走, 只要最后能够在步数耗尽的同时达到终点就行</p>
     */
    private static int bruteForce(int locations, int current, int end, int steps){
        // 0。Base Case: 决定递归什么时候退出
        // 0.1 如果步数已经耗尽了, 且此时正处于终点, 那么这种走法就是可行的
        if (steps == 0) return current == end ? 1: 0;
        // 0.2 如果步数没有走完, 而且走到了左边界, 那么需要往回走
        if (current == 1) return bruteForce(locations, current + 1, end, steps - 1);
        // 0.3 如果步数没有走完, 而且走到了右边界, 那么也需要往回走
        if (current == locations) return bruteForce(locations, current - 1, end, steps - 1);

        // 1. 其余情况就说明正在途中: 可以向左走也可以向右走
        return bruteForce(locations, current - 1, end, steps - 1)
                       + bruteForce(locations, current + 1, end, steps -1);
    }

    /**
     * <p>记忆化搜索</p>
     * @param dp 二维表结构的横轴和纵轴就是两个可变参数, 分别表示当前位置和所剩的步数
     */
    private static int rememberSearch(int locations, int current, int end, int steps, int[][] dp){
        // 0. 如果表结构中已经保存了相应的值那么显然是可以直接返回的
        if (dp[steps][current] != -1) return dp[steps][current];
        // 1. 如果没有那么就需要继续递归, 并且将结果填充表中
        if (steps == 0)
            dp[steps][current] = current == end ? 1: 0;
        else if (current == 1)
            dp[steps][current] = rememberSearch(locations, current + 1, end, steps - 1, dp);
        else if (current == locations)
            dp[steps][current] = rememberSearch(locations, current - 1, end, steps - 1, dp);
        else
            dp[steps][current] = rememberSearch(locations, current - 1, end, steps - 1, dp)
                                        + rememberSearch(locations, current + 1, end, steps - 1, dp);
        return dp[steps][current];
    }

}
