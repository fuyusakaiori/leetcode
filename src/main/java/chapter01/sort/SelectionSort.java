package chapter01.sort;

import utils.RandomUtil;
import utils.TestUtil;

public class SelectionSort
{
    public static void main(String[] args)
    {
        RandomUtil random = new RandomUtil(10, 20);
        TestUtil.sortTest(random, SelectionSort::selectionSort, "选择排序");
    }

    private static void selectionSort(int[] numbers){
        // 选中第一个数, 然后向后看是否存在比自己更小的数, 如果存在就记录下来, 遍历结束后交换
        int length = numbers.length;
        for (int i = 0; i < length; i++) {
            int minIndex = i;
            // 每轮排序确定的都是前面的位置
            for (int j = i; j < length; j++) {
                // 每次都需要和之前记录的最小值比, 而不是和选中的元素比
                if (numbers[minIndex] > numbers[j]){
                    minIndex = j;
                }
            }
            if (i != minIndex){
                swap(numbers, i, minIndex);
            }
        }
    }

    // 选择排序不能够使用位运算交换:
    // 因为如果一轮排序中没有比自己小的, 那么元素就会和自己交换, 交换结果就会为 0
    // 当然完全可以采用标志位避免这个事情
    private static void swap(int[] numbers, int first, int second){
        numbers[first] = numbers[first] ^ numbers[second];
        numbers[second] = numbers[first] ^ numbers[second];
        numbers[first] = numbers[first] ^ numbers[second];
    }
}
