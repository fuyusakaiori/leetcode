package chapter01.sort;

import utils.RandomUtil;
import utils.TestUtil;

public class MergeSort
{
    /*
    1、要学会肉眼 Debug, 笔试的时候很有可能不给你提供本地 IDE 或者调试功能
    2、两个错误点: ① 位运算的优先级非常低 ② 更新原来的数组的时候不是从 0 开始的
    3、归并排序能够记住部分有序的信息, 冒泡、插入、选择排序都是无法记住有序的信息
     */
    public static void main(String[] args)
    {
        RandomUtil random = new RandomUtil(10, 20);
        TestUtil.sortTest(random, MergeSort::mergeSort, "归并排序");
    }

    private static void mergeSort(int[] numbers){
        fork(numbers, 0, numbers.length - 1);
    }

    // 拆分数组
    private static void fork(int[] numbers, int left, int right){
        // 持续拆分数组, 直到数组中元素只剩一个就开始合并
        if (left == right)
            return;
        // 位运算的优先级比四则运算低
        int mid = left + ((right - left) >> 1);
        fork(numbers, left, mid);
        fork(numbers, mid + 1, right);

        // 开始合并数组
        join(numbers, left, mid, right);
    }

    // 合并有序数组
    private static void join(int[] numbers, int left, int mid, int right){
        // 辅助存储的数组
        int[] helper = new int[right - left + 1];
        int index = 0;
        int leftIndex = left;
        int rightIndex = mid + 1;
        // 开始比较遍历
        while (leftIndex <= mid && rightIndex <= right){
            helper[index++] = numbers[leftIndex] < numbers[rightIndex] ?
                                       numbers[leftIndex++] : numbers[rightIndex++];
        }
        // 将剩余的数放入辅助数组
        while (leftIndex <= mid){
            helper[index++] = numbers[leftIndex++];
        }
        while (rightIndex <= right){
            helper[index++] = numbers[rightIndex++];
        }
        // 更新数组
        // 注意①: 不要直接将原数组指向辅助数组, Java 中都是值传递, 你是没有办法修改引用的
        // 注意②: 左指针不一定是 0, 千万不要搞错
        for (int i = 0; i < helper.length; i++) {
            numbers[i + left] = helper[i];
        }
    }
}
