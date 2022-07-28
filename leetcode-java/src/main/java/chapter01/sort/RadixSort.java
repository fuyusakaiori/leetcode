package chapter01.sort;


import java.util.Arrays;

// 基数排序: 只能针对非负整数
public class RadixSort {

    private static int maxBits(int[] numbers){
        // 1. 先遍历寻找到的最大值
        int max = 0;
        for (int number : numbers) {
            max = Math.max(number, max);
        }
        // 2. 判断最大值的位数
        int res = 0;
        while (max != 0){
            max /= 10;
            res++;
        }
        return res;
    }

    private static int getDigit(int number, int digit){
        return (number / (int) Math.pow(10, digit - 1)) % 10;
    }

    private static void radixSort(int[] numbers, int left, int right, int bits){
        // 1. 准备词频数组, 辅助数组
        final int radix = 10;
        int[] bucket = new int[right - left + 1];
        // 2. 统计词频: 相当于入桶的操作
        // 有几位就会执行几次入桶出桶的操作
        for (int k = 1; k <= bits; k++) {
            // 注意: 每次词频数组都需要清空再添加, 否则就会越界
            int[] count = new int[radix];
            for (int i = left; i <= right; i++) {
                int digit = getDigit(numbers[i], k);
                count[digit]++;
            }
            // 3. 计算前缀和
            for (int i = 1; i < count.length; i++) {
                count[i] = count[i] + count[i - 1];
            }
            // 4. 从右向左遍历: 相当于出桶
            for (int i = right;i >= left;i--){
                int digit = getDigit(numbers[i], k);
                bucket[count[digit] - 1] = numbers[i];
                count[digit]--;
            }
            // 5. 更新原数组
            for (int i = 0, j = left; i <= right; i++, j++) {
                 numbers[i] = bucket[j];
            }
        }
    }
}
