package chapter09.dynamic.basic;

/**
 * <h2>斐波那契数列问题</h2>
 * <h3>1. 斐波那契</h3>
 * <h3>2. 泰波那契</h3>
 * <h3>自定义问题: 母牛生崽（记得要去分析状态间的依赖关系）</h3>
 * <h3>自定义问题: 细胞分裂</h3>
 * <h2>状态转移方程</h2>
 * <h3>dp[i] = dp[i - 1] + dp[i - 2]</h3>
 * <h3>dp[i] = dp[i - 1] + dp[i - 2] + dp[i - 3]</h3>
 */
public class Fibonacci {

    /**
     * <h3>思路: 斐波那契数</h3>
     * <h3>1. 动态规划</h3>
     * <h3>2. 状态压缩</h3>
     * <h3>3. 矩阵快速幂</h3>
     */
    private static int fibonacci(int n){
        int first = 0, second = 1, third = 0;
        for(int index = 0;index <= n - 2;index++){
            third = first + second;
            first = second;
            second = third;
        }
        return third;
    }
}
