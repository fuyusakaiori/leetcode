package chapter06;

/**
 * <h2>硬币游戏</h2>
 * <p>1. 给定一组硬币, 硬币的面值都是正数</p>
 * <p>2. 然后给定一个目标值</p>
 * <p>3. 现在要求你使用给出的硬币组合出目标值来, 并且要求使用的硬币数最少</p>
 */
public class CoinGame
{
    public static void main(String[] args)
    {
        System.out.println(minCoins(new int[]{2, 7, 3, 5, 3}, 10));
    }

    /**
     * <p>动态规划</p>
     * @param coins 可以使用的硬币
     * @param target 给定的目标值
     * @return 最少硬币数
     */
    private static int minCoins(int[] coins, int target){
        int[][] dp = new int[coins.length + 1][target + 1];
        for (int row = 0;row < dp.length;row++) dp[row][0] = 0;
        for (int column = 1;column < dp[5].length;column++) dp[5][column] = -1;
        for (int row = coins.length - 1;row >= 0;row--){
            for (int column = 1;column <= target;column++){
                int yes = column - coins[row] >= 0 ? dp[row + 1][column - coins[row]] : -1;
                int no = dp[row + 1][column];
                if (yes == -1 && no == -1) dp[row][column] = -1;
                else if (yes == -1) dp[row][column] = no;
                else if (no == -1) dp[row][column] = yes + 1;
                else dp[row][column] = Math.min(no, yes + 1);
            }
        }
        return dp[0][target];
    }

    /**
     * <p>这里的两个可变值是目标值和索引</p>
     * @param target 只要目标值减少到零, 那么就是完成组合
     * @param index 挑选数组中的硬币使用
     * @return 硬币数
     */
    private static int bruteForce(int[] coins, int target, int index){
        // 0. Base Case
        // 0.1 如果目标值减少到负数, 那么这种组合方式显然是不可行的
        if (target < 0) return -1;
        // 0.2 如果目标值恰好减少到 0, 那么显然就证明这种组合是可行的
        if (target == 0) return 0;
        // 0.3 如果到了数组末尾还是没有让目标值恰好减少到零, 这种组合肯定也是不可行的
        if (index == coins.length) return -1;
        // 注: 这里也不能够使用最大值, 会导致溢出, 还是使用负数比较好
        // 1. 其余情况, 我们可以挑选当前这个硬币, 也可以不挑选
        // // 1.1 后者就是挑选当前的硬币
        int yes =  bruteForce(coins, target - coins[index], index + 1);
        // 1.2 前者就是不挑选当前的硬币
        int no = bruteForce(coins, target, index + 1);
        if (yes == -1 && no == -1)
            return -1;
        else if (yes == -1)
            return no;
        else if (no == -1)
            return yes + 1;

        return Math.min(no, yes + 1);
    }

    /**
     * <p>记忆化搜索</p>
     */
    private static int rememberSearch(int[] coins, int target, int index, int[][] dp){
        if (target < 0) return -1;
        // 目标值可能是负数, 所以不能够直接做索引
        if (dp[index][target] != -2) return dp[index][target];
        if (target == 0) return dp[index][target] = 0;
        if (index == coins.length) return dp[index][target] = -1;
        int yes =  rememberSearch(coins, target - coins[index], index + 1, dp);
        // 1.2 前者就是不挑选当前的硬币
        int no = rememberSearch(coins, target, index + 1, dp);
        if (yes == -1 && no == -1)
            dp[index][target] = -1;
        else if (yes == -1)
            dp[index][target] = no;
        else if (no == -1)
            dp[index][target] = yes + 1;
        else
            dp[index][target] = Math.min(yes + 1, no);
        return dp[index][target];
    }

}
