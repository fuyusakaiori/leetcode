package chapter11.dynamic;


import java.util.List;

/**
 * <h2>爬楼梯问题</h2>
 * <h3>1. 爬楼梯基础问题</h3>
 * <h3>2. 爬楼梯最小花费问题</h3>
 */
public class ClimbStairs {

    /**
     * <h3>思路: 爬楼梯</h3>
     * <h3>1. 动态规划</h3>
     * <h3>2. 状态压缩</h3>
     */
    private static int climbStairs1(int n){
        if(n == 1 || n == 2)
            return n;
        int result = 0;
        int first = 1, second = 2;
        for(int index = 0;index < n - 2;index++){
            result = first + second;
            first = second;
            second = result;
        }
        return result;
    }

    /**
     * <h3>扩展题目: </h3>
     * <h3>1. 要求不能够爬到阶梯数为 7 或者 7 的倍数</h3>
     * <h3>思路: 直接加个判断条件就可以</h3>
     * <h3>2. 要求到达顶端的所有路径</h3>
     * <h3>思路: 只能够用递归来实现, 动态规划只能够知道方法数, 不知道过程的</h3>
     */
    private static int climbStairs2(int n){
        if(n == 0) return 0;
        int[] dp = new int[n + 1];
        dp[0] = dp[1] = 1;
        for(int index = 2;index < dp.length;index++){
            dp[index] = index % 7 != 0 ? dp[index - 1] + dp[index + 2] : 0;
        }
        return dp[n];
    }

    /**
     * <h3>思路: 保存所有阶梯, 在到底顶端的时候全部输出即可</h3>
     */
    private static int dfs(int target, List<Integer> path){
        if(target < 0) return 0;
        if(target == 0){
            System.out.println(path);
            return 1;
        }
        path.add(target - 1);
        int firstStep = dfs(target - 1, path);
        path.remove(path.size() - 1);
        path.add(target - 2);
        int secondStep = dfs(target - 2, path);
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

}
