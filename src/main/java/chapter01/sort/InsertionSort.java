package chapter01.sort;

import utils.RandomUtil;
import utils.TestUtil;

public class InsertionSort
{
    public static void main(String[] args)
    {
        RandomUtil random = new RandomUtil(10, 20);
        TestUtil.sortTest(random, InsertionSort::insertionSort, "插入排序");
    }


    private static void insertionSort(int[] numbers){
        int length = numbers.length;
        for (int i = 0; i < length; i++) {
            for (int j = i; j > 0; j--) {
                // 不要使用快捷运算符, 会多减好几次
                if (numbers[j] >= numbers[j - 1]) {
                    break;
                }else{
                    swap(numbers, j, j - 1);
                }
            }
        }
    }

    private static void swap(int[] numbers, int first, int second){
        numbers[first] = numbers[first] ^ numbers[second];
        numbers[second] = numbers[first] ^ numbers[second];
        numbers[first] = numbers[first] ^ numbers[second];
    }
}
