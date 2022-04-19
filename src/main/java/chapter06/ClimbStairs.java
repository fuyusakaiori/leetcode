package chapter06;


import java.util.ArrayList;
import java.util.List;

/**
 * <h2>爬楼梯问题</h2>
 * <h3>1. 爬楼梯基础问题</h3>
 * <h3>2. 爬楼梯最小花费问题</h3>
 * <h3>3. 母牛生崽</h3>
 * <h3>4. 细胞分裂</h3>
 * <h2>动态转移方程</h2>
 * <h3>dp[n] = dp[n - 1] + dp[n - 2](根据题目需要改变)</h3>
 * <h3>dp[n] = Math.min(dp[n - 1], dp[n - 2])</h3>
 */
public class ClimbStairs {

    public static void main(String[] args) {

    }

    /**
     * <h3>思路: 爬楼梯</h3>
     * <h3>1. 可以从尝试的角度思考, 也可以考虑从子问题的角度思考</h3>
     * <h3>2. 你要达到下一个楼梯, 要么跳一步, 要么就跳两步, 直到最后你没有步数可跳就结束了</h3>
     * <h3>3. 你只能够达到下一个台阶或者下两个台阶, 然后你要从下一个或者下两个楼梯到达中点</h3>
     * <h3>4. 也就意味着你是依赖这两个状态的, 所以状态转移方程就分析出来了</h3>
     */
    private static int climbStairs(int target){
        return 0;
    }

    /**
     * <h3>注: 爬楼梯的基本思路和斐波那契没有本质区别</h3>
     */
    private static int dp1(int target){
        if(target == 0) return 1;
        int[] dp = new int[target + 1];
        dp[0] = dp[1] = 1;
        for(int index = 2;index < dp.length;index++){
            dp[index] = dp[index - 1] + dp[index - 2];
        }
        return dp[target];
    }

    private static int dfs1(int target, int[] dp){
        if (target < 0) return 0;
        if (target == 0) return 1;
        if (dp[target] != -1) return dp[target];
        return dp[target] = dfs1(target - 1, dp) + dfs1(target - 2, dp);
    }

    /**
     * <h3>扩展题目: </h3>
     * <h3>1. 要求不能够爬到阶梯数为 7 或者 7 的倍数</h3>
     * <h3>思路: 直接加个判断条件就可以</h3>
     * <h3>2. 要求到达顶端的所有路径</h3>
     * <h3>思路: 只能够用递归来实现, 动态规划只能够知道方法数, 不知道过程的</h3>
     */
    private static int dp2(int target){
        if(target == 0) return 0;
        int[] dp = new int[target + 1];
        dp[0] = dp[1] = 1;
        for(int index = 2;index < dp.length;index++){
            dp[index] = index % 7 != 0 ? dp[index - 1] + dp[index + 2] : 0;
        }
        return dp[target];
    }

    /**
     * <h3>思路: 保存所有阶梯, 在到底顶端的时候全部输出即可</h3>
     */
    private static int dfs2(int target, List<Integer> path){
        if(target < 0) return 0;
        if(target == 0){
            System.out.println(path);
            return 1;
        }
        path.add(target - 1);
        int firstStep = dfs2(target - 1, path);
        path.remove(path.size() - 1);
        path.add(target - 2);
        int secondStep = dfs2(target - 2, path);
        path.remove(path.size() - 1);
        return firstStep + secondStep;
    }

    /**
     * <h3>思路: 爬楼梯的最小花费</h3>
     * <h3>1. 这个最好从右向左遍历, 更加符合递归的逻辑</h3>
     * <h3>2. 顶点到顶点自己肯定是 0, 最后一个阶梯到顶点的花费就是 cost[cost.length - 1]</h3>
     * <h3>3. 之前的所有阶梯到顶点的花费都是依赖于前面两个阶梯的, 这就是状态转移方程</h3>
     */
    private static int climbStairsMinCost(int[] cost){
        // 注: 条件中已经限制了数组的长度至少大于等于 2
        int[] dp = new int[cost.length + 1];
        // 注: 初始值可以根据递归来定
        dp[cost.length] = 0;
        dp[cost.length - 1] = cost[cost.length - 1];
        // 注:
        for (int index = cost.length - 2;index >= 0;index--){
            dp[index] = Math.min(dp[index - 1], dp[index - 2]) + cost[index];
        }
        return Math.min(dp[0], dp[1]);
    }

    private static int dfs3(int[] cost, int index, int[] dp){
        if(index >= cost.length) return 0;
        if(dp[index] != 0) return dp[index];
        // 注: 每次向上爬都是要加上当前楼梯到的开销的
        return dp[index] = cost[index] + Math.min(
                dfs3(cost, index + 1, dp), dfs3(cost, index + 2, dp));
    }



    /**
     * <h3>3. 母牛生崽</h3>
     * <p>动态规划</p>
     * @param year 在给定年份之后, 牛的数量
     */
    private static int cowGivingBirth(int year){
        int[] dp = new int[year + 1];
        for(int index = 1;index < dp.length;index++){
            dp[index] = index > 3 ? dp[index - 1] + dp[index - 3]: index;
        }
        return dp[year];
    }

    /**
     * <h3>1. 上一年的所有牛肯定都会存活, 所以今年的数量一定会依赖上一年的数量</h3>
     * <h3>2. 此外, 前三年的牛已经在上一年成熟, 并且会生一头新牛, 所以也会依赖前三年的数量</h3>
     * <p>暴力递归 + 记忆化搜索</p>
     */
    private static int dfs(int year, int[] dp){
        if(year == 1 || year == 2 || year == 3) return year;
        if (dp[year] != 0) return dp[year];
        return dp[year] = dfs(year - 1, dp) + dfs(year - 3, dp);
    }

    /**
     * <h3>4. 细胞分裂</h3>
     * <h4>1. 第一秒只有 1个细胞, 该细胞从第 2 秒开始分裂</h4>
     * <h4>2. 产生的新细胞会在经过 2 秒之后开始分裂</h4>
     * <h4>3. 分裂的过程中, k 个细胞会聚合成一组, 如果此时存在单独成组的细胞, 那么就会死亡</h4>
     * <h4>4. 问经过 n 秒之后, 会有多少个细胞</h4>
     * <p>注: 和之前的母牛生崽问题区别就在于, 细胞是会死亡的</p>
     * @param seconds 细胞分裂的时间
     * @param convergence 多少个细胞聚合成一组
     */
    private static int cellDivision(int seconds, int convergence){

        return 0;
    }

    private static int dfs(int seconds, int convergence, int[] dp){
        if (seconds == 1 || seconds == 2) return seconds;
        int base = dfs(seconds - 1, convergence, dp) + dfs(seconds - 2, convergence, dp);
        return dp[seconds] = base % convergence == 1 ? base - 1: base;
    }

}
