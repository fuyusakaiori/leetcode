package chapter01.sort;

public class BubbleSort {

    // 冒泡排序算法
    private static void bubbleSort(int[] numbers){
        // 执行一次循环并不能够保证最终的顺序是递增的, 是需要执行两次的
        int length = numbers.length;
        boolean flag = true;
        for (int i = 0; i < length; i++) {
            // 每轮排序都会确定一个数的位置
            for (int j = 0; j < length - i - 1; j++) {
                if (numbers[j] > numbers[j + 1]){
                    flag = false;
                    swap(numbers, j, j+1);
                }
            }
            // 改良: 如果一轮排序中一次都没有交换, 那么就证明已经有序, 跳出循环即可
            if (flag) break;
            flag = true;
        }
    }

    // 交换两个数的位置
    private static void swap(int[] numbers, int first, int second){
        // 利用位运算交换, 而不是临时变量交换
        numbers[first] = numbers[first] ^ numbers[second];
        numbers[second] = numbers[first] ^ numbers[second];
        numbers[first] = numbers[first] ^ numbers[second];
    }
}
