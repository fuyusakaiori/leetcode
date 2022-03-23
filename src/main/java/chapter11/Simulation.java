package chapter11;

import utils.Important;

import java.util.ArrayList;


/**
 * <h2>模拟系列问题</h2>
 * <h3>====矩阵模拟====</h3>
 * <h3>1. 螺旋矩阵 & 螺旋矩阵 II</h3>
 * <h3>2. 重塑矩阵</h3>
 * <h3>3. 转置矩阵</h3>
 * <h3>4。矩阵置零</h3>
 * <h3>5. 生命游戏(细胞自动机)</h3>
 * <h3>====计算模拟====</h3>
 * <h3>6. 字符串相乘 & 字符串相加 & 二进制求和</h3>
 * <h3>====字符串模拟====</h3>
 * <h3>注: 所有字符串模拟的题目放在第九章中</h3>
 * <h3>====其他模拟====</h3>
 * <h3>7. 提莫攻击</h3>
 * <h3>8. 比较含退格的字符串</h3>
 * <h2>总结</h2>
 * <h3>1. 模拟就是按照题目的意思, 去模仿题目的行为</h3>
 * <h3>2. 如果模拟题和矩阵有关, 那么可能会使用到深度搜索或者广度搜索的技巧, 不过本质还是模拟</h3>
 * <h3>3. 因为绝大多数的矩阵题目都是双重循环套深度搜索, 没有太多优化技巧可言, 所以说本质还是模拟</h3>
 */
public class Simulation {

    /**
     * <h3>思路: 螺旋矩阵</h3>
     * <h3>核心: 使用四个边界指针, 不停向内收缩</h3>
     * <h3>注: 最好是在每个循环结束后就修改边界指针, 如果循环都结束后一起修改, 是没有办法处理长方形矩阵的</h3>
     * <h3>注: 两个螺旋矩阵题的思路是完全相同的</h3>
     */
    @Important
    private static int[][] generateMatrix(int n){
        int value = 0;
        int top = 0, left = 0;
        int bottom = n - 1, right = n - 1;
        int[][] matrix = new int[n][n];
        while (value < n * n){
            // 1.向右遍历生成数字
            for (int index = left;index <= right;index++){
                matrix[top][index] = ++value;
            }
            // 注: 修改上界指针, 否则刚才填的最后一个值会被覆盖
            top++;
            // 2. 向下遍历生成数字
            for (int index = top;index <= bottom;index++){
                matrix[index][right] = ++value;
            }
            right--;
            // 3. 向左遍历生成数字
            for (int index = right;index >= left;index--){
                matrix[bottom][index] = ++value;
            }
            bottom--;
            // 4. 向上遍历生成数字
            for (int index = bottom;index >= top;index--){
                matrix[left][index] = ++value;
            }
            left++;
        }
        return matrix;
    }

    /**
     * <h3>思路: 重塑矩阵</h3>
     * <h3>1. 二维矩阵可以利用坐标间的映射关系, 将元素存放到一维数组中</h3>
     * <h3>2. 反过来, 一维数组也可以利用坐标间映射关系, 将元素存放到二维数组中</h3>
     * <h3>3. 那么显然就可以直接实现二维数组到二维数组的转换</h3>
     * <h3>注: 这个题并不难, 但是这种映射的思想非常关键, 很有可能在其他题中用到</h3>
     */
    private static int[][] matrixReshape(int[][] matrix, int row, int column){
        if (matrix.length * matrix[0].length != row * column)
            return matrix;
        int limit = matrix[0].length;
        int[][] reshape = new int[row][column];
        for (int index = 0;index < matrix.length * matrix[0].length;index++){
            reshape[index / column][index % column] = matrix[index / limit][index / limit];
        }
        return reshape;
    }

    /**
     * <h3>思路: 二维数组转一维</h3>
     * <h3>1. 一维数组坐标 / 列数 = 行</h3>
     * <h3>2. 一位数组 % 列数 = 列</h3>
     */
    @Important
    private static int[] toOneDimension(int[][] matrix){
        int row = matrix.length;
        int column = matrix[0].length;
        int[] result = new int[row * column];
        for (int index = 0;index < result.length;index++){
            // 注: 和行数没有任何关系
            result[index] = matrix[index / column][index % column];
            // 注: 一维转二维就是这样, 那么二维转一维显然反过来就行
            matrix[index / column][index % column] = result[index];
            // 注: 所以二维转一维的方式就出来了
        }
        return result;
    }

    /**
     * <h3>思路: 转置矩阵</h3>
     * <h3>注: 在某些题中也是可能使用到的技巧, 要留心</h3>
     * <h3>注: 没有原地算法, 因为矩阵可能是长方形</h3>
     */
    private static int[][] transpose(int[][] matrix){
        int[][] T = new int[matrix[0].length][matrix.length];
        for(int row = 0;row < matrix.length;row++){
            for(int column = 0;column < matrix[row].length;column++){
                // 注: 直接交换列和行就行
                T[column][row] = matrix[row][column];
            }
        }
        return T;
    }

    /**
     * <h3>思路: 矩阵置零</h3>
     * <h3>1. 记录所有 0 出现的位置, 然后将对应的行和列都置为 0</h3>
     * <h3>1.1 将 0 的二维坐标转换成一维坐标存放, 然后再将一维坐标转换成二维坐标去更新对应的行和列</h3>
     * <h3>注: 这种做法的问题在于更新行和列会有重复计算, 常数级时间可能较高, 但是仅使用单个链表就可以完成</h3>
     * <h3>1.2 采用两个数组分别存储出现 0 的行和列, 而不是放在一个数组中存放</h3>
     * <h3>注: 不存在重复计算</h3>
     * <h3>2. 让矩阵的第一行和第一列充当刚才的数组, 使用双变量单独处理第一行和第一列</h3>
     * <h3>3. 仅使用单个变量</h3>
     */
    private static void setZero1(int[][] matrix){
        boolean[] rowFlag = new boolean[matrix.length];
        boolean[] columnFlag = new boolean[matrix[0].length];
        // 1. 记录出现零的行和列
        for (int row = 0;row < matrix.length;row++){
            for (int column = 0;column < matrix[row].length;column++){
                if (matrix[row][column] == 0)
                    rowFlag[row] = columnFlag[column] = true;
            }
        }
        // 2. 再次遍历, 只要元素所在的行或者列出现过零, 那么直接置为零, 不需要单独更新行和列
        for (int row = 0;row < matrix.length;row++){
            for (int column = 0;column < matrix[0].length;column++){
                if (rowFlag[row] || columnFlag[column])
                    matrix[row][column] = 0;
            }
        }
    }

    private static void setZero2(int[][] matrix){
        // 1. 使用矩阵的第一行和第一列作为标记数组, 先要记录第一行和第一列是否有零
        // 2. 避免原来没有零, 但是因为作为标记数组之后变为零的情况, 这种情况肯定是不去更新第一行和第一列的
        boolean rowFlag = false;
        boolean columnFlag = false;
        // 3. 可以合并在一起处理
        for (int index = 0;index < matrix.length || index < matrix[0].length;index++){
            if (index < matrix.length && matrix[index][0] == 0) columnFlag = true;
            if (index < matrix[0].length && matrix[0][index] == 0) rowFlag = true;
        }
        // 4. 记录出现 0 的位置
        for (int row = 1;row < matrix.length;row++){
            for (int column = 1;column < matrix[0].length;column++){
                if (matrix[row][column] == 0)
                    matrix[row][0] = matrix[0][column] = 0;
            }
        }
        // 5. 设置零
        for (int row = 1;row < matrix.length;row++){
            for (int column = 1;column < matrix[0].length;column++){
                if (matrix[row][0] == 0 || matrix[0][column] == 0)
                    matrix[row][column] = 0;
            }
        }
        // 6. 单独处理
        if (rowFlag){
            for (int index = 0;index < matrix[0].length;index++){
                matrix[0][index] = 0;
            }
        }
        if (columnFlag){
            for (int index = 0;index < matrix.length;index++){
                matrix[index][0] = 0;
            }
        }

    }

    /**
     * <h3>思路: 字符串相加 (大数相加)</h3>
     * <h3>注: 模拟就行, 没有其他做法, 不过可以简化代码</h3>
     */
    private static String addStrings(String first, String second){
        int carry = 0;
        int sum = 0;
        int firstIndex = first.length() - 1;
        int secondIndex = second.length() - 1;
        StringBuilder result = new StringBuilder();
        while (firstIndex >= 0 || secondIndex >= 0 || carry != 0){
            int firstNum = firstIndex >= 0 ? first.charAt(firstIndex--) - '0' : 0;
            int secondNum = secondIndex >= 0 ? second.charAt(secondIndex--) - '0' : 0;
            sum = firstNum + secondNum + carry;
            result.insert(0, sum % 10);
            carry = sum / 10;
        }
        return result.toString();
    }

    /**
     * <h3>思路: 字符串相乘 (大数相乘)</h3>
     * <h3>注: 可以优化</h3>
     */
    private static String multiplyString(String first, String second){
        String longStr = first.length() <= second.length() ? second: first;
        String shortStr = first.length() <= second.length() ? first: second;
        if("0".equals(shortStr))
            return "0";
        String result = "";
        for(int index = shortStr.length() - 1;index >= 0;index--){
            String add = multiplyStrings(longStr, shortStr.charAt(index));
            result = addStrings(result, setZero(add, shortStr.length() - 1 - index));
        }
        return result;
    }

    private static String setZero(String str, int index){
        StringBuilder sb = new StringBuilder(str);
        for(int count = 0;count < index;count++){
            sb.append("0");
        }
        return sb.toString();
    }

    private static String multiplyStrings(String first, char second){
        int carry = 0;
        int result = 0;
        int number = second - '0';
        StringBuilder sb = new StringBuilder();
        for(int index = first.length() - 1;index >= 0;index--){
            result = number * (first.charAt(index) - '0') + carry;
            sb.insert(0, result % 10);
            carry = result / 10;
        }
        if(carry != 0) sb.insert(0, carry);
        return sb.toString();
    }


}
