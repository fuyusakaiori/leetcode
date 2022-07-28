package chapter05.matrix;

import java.util.ArrayList;
import java.util.List;

/**
 * <h2>螺旋矩阵</h2>
 * <h3>1. 螺旋矩阵</h3>
 * <h3>2. 螺旋矩阵 II</h3>
 * <h3>注: 这两个题的思路没有任何区别</h3>
 */
public class SpiralMatrix {


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

    private static List<Integer> spiralOrders(int[][] matrix){
        int row = matrix.length, col = matrix[0].length;
        int left = 0, right = col - 1;
        int top = 0, bottom = row - 1;
        int total = row * col;
        List<Integer> elements = new ArrayList<>(total);
        while(elements.size() < total){
            // 注: 循环条件必须添加 elements.size() < total 这个条件, 主要目的是为了避免之后的循环在集合元素已经够了的情况下继续添加
            for(int index = left;index <= right && elements.size() < total;index++){
                elements.add(matrix[top][index]);
            }
            top++;
            for(int index = top;index <= bottom && elements.size() < total;index++){
                elements.add(matrix[index][right]);
            }
            right--;
            for(int index = right;index >= left && elements.size() < total;index--){
                elements.add(matrix[bottom][index]);
            }
            bottom--;
            for(int index = bottom;index >= top && elements.size() < total;index--){
                elements.add(matrix[index][left]);
            }
            left++;
        }
        return elements;
    }

}
