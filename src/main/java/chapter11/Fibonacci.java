package chapter11;

/**
 * <h2>斐波那契数列问题</h2>
 * <h3>1. 斐波那契基础问题</h3>
 * <h3>2. 泰波那契基础问题</h3>
 * <h3>自定义问题: 母牛生崽（记得要去分析状态间的依赖关系）</h3>
 * <h3>自定义问题: 细胞分裂</h3>
 * <h2>状态转移方程</h2>
 * <h3>dp[i] = dp[i - 1] + dp[i - 2]</h3>
 * <h3>dp[i] = dp[i - 1] + dp[i - 2] + dp[i - 3]</h3>
 */
public class Fibonacci {

    private static int fibonacci(int number){
        return dp(number);
    }

    /**
     * <h3>暴力递归 => 记忆化搜索</h3>
     * <h3>时间复杂度: O(2^N) => 满二叉树的结点个数</h3>
     */
    private static int dfs(int number, int[] dp){
        if (number == 0) return 0;
        if (number == 1) return 1;
        if (dp[number] != 0) return dp[number];
        return dp[number] = dfs(number - 1, dp) + dfs(number - 2, dp);
    }

    /**
     * <h3>动态规划 => 状态压缩</h3>
     * <h3>注: 状态压缩就是一种优化策略, 实际是通过观察得出的</h3>
     * <h3>注: 发现 dp 数组中的每个值实际上只依赖前两个值, 前两个值之前的都和当前值无关</h3>
     * <h3>时间复杂度: O(N)</h3>
     */
    private static int dp(int number){
        // 1. Base Case + 前两个的值的状态
        int first = 0;
        int second = 1;
        int result = 0;
        // 2. 迭代: 从 Base Case 之后开始 => 3. 状态压缩
        for (int index = 2;index <= number;index++){
            // 状态转移方程
            result = first + second;
            // 前两个值状态更新
            first = second;
            second = result;
        }
        return result;
    }

    /**
     * <p>矩阵快速幂</p>
     * <p>1. n 阶递推数列都是可以转换成矩阵乘法来表示的</p>
     * <p>1.1 [F(n), F(n - 1)] = [F(n - 1), F(n - 2)] * matrix</p>
     * <p>1.2 采用矩阵表示递推数列就是猜出来的, 没有直接推导的过程</p>
     * <p>1.3 1阶常数 matrix 矩阵通过带入前面几项就可以求出</p>
     * <p>[1, 1]</p>
     * <p>[1, 0]</p>
     * <p>2. 问题转换成 [[F(n), F(n - 1)] = [F(n - 1), F(n - 2)] * [[1, 1], [1, 0]]^n</p>
     * <p>3. 问题再度变成如何快速求解矩阵的幂运算</p>
     * <p>4. 整数的快速幂运算和矩阵的快速幂运算理论是完全相同的, 细节不同而已</p>
     * <p>4.1 确定幂的二进制位数是多少, 那么就需要进行多少次乘法运算</p>
     * <p>4.2 然后每次整数都和自己相乘, 如果对应的二进制位数是 1, 那么就将这个结果保留, 如果是 0 就不保留</p>
     * <p>4.3 10^75 = 10^64 + 10^8 + 10^2 + 10^1 75 = 1001011</p>
     * <p>4.4 (1) 10^1 * 10^1 (2) 10^2 * 10^2 (3) 10^4 * 10^4...(每次都和自己相乘, 得到的结果用于下次)</p>
     */
    private static int[][] matrix(int[][] matrix, int pow){
        int[][] result = new int[matrix.length][matrix.length];
        // 1. 初始化为单位矩阵
        for (int index = 0;index < result.length;index++){
            result[index][index] = 1;
        }
        // 2. 开始快速幂解法
        while (pow != 0){
            // 3. 提取最右侧的 1: 如果不等于0, 那么就和之前的结果相乘, 如果等于0, 就不相乘
            if ( ((~pow + 1) & pow) != 0 ){
                result = multiply(result, matrix);
            }
            //4. 更新矩阵
            matrix = multiply(matrix, matrix);
            pow >>= 1;
        }
        return result;
    }

    /**
     * 矩阵乘法
     */
    private static int[][] multiply(int[][] first, int[][] second){
        int[][] result = new int[first.length][second[0].length];
        for (int row = 0;row < result.length;row++){
            for (int column = 0;column < result[row].length;column++){
                for (int index = 0;index < first[0].length;index++){
                    result[row][column] += first[row][index] * second[index][column];
                }
            }
        }
        return result;
    }
}
