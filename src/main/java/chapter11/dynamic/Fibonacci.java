package chapter11.dynamic;

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

    /**
     * <h3>思路: 母牛生崽</h3>
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
