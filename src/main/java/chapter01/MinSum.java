package chapter01;

import utils.RandomUtil;

import java.util.Arrays;

public class MinSum
{
    /* 题目 最小和 & 逆序对
    1、将每个数左侧比自己小的数加起来
    2、求整个数组中的的所有数的最小和的和
    3、变换为: 每个数在右边有多少个数自己大
    4、为什么不能够省略排序过程?
    5、逆序对: 每个数在右边有多少个数比自己小
    6、都是求数量, 不会让你打印的, 打印始终都是需要循环遍历
     */
    public static void main(String[] args)
    {
        RandomUtil random = new RandomUtil(10, 20);
        int[] array = random.randomArrayNoReplica();
        System.out.println(Arrays.toString(array));
        System.out.println(fork(new int[]{1,3,4,2,5}, 0, 4));
    }

    // 拆分
    private static int fork(int[] numbers, int left, int right){

        if (left == right)
            return 0;

        int mid = left + ((right - left) >> 1);
        int leftMinSum = fork(numbers, left, mid);
        int rightMinSum = fork(numbers, mid + 1, right);
        return leftMinSum + rightMinSum + joinAndCompute(numbers, left, mid, right);
    }

    private static int joinAndCompute(int[] numbers, int left, int mid, int right){
        int[] helper = new int[right - left + 1];
        int index = 0;
        int minSum = 0;
        int leftIndex = left;
        int rightIndex = mid + 1;

        // 注意: 非常重要!排序的过程是不可以省略的!
        while (leftIndex <= mid && rightIndex <= right){
            // 如果两个元素相等, 必须优先拷贝右侧的
            if (numbers[leftIndex] < numbers[rightIndex]){
                // TODO 你几把是不是铸币??????? 数量都不会算了是吧
                minSum += numbers[leftIndex] * (right - rightIndex + 1);
                helper[index++] = numbers[leftIndex++];
            }else{
                helper[index++] = numbers[rightIndex++];
            }
        }
        while (leftIndex <= mid){
            helper[index++] = numbers[leftIndex++];
        }
        while (rightIndex <= right){
            helper[index++] = numbers[rightIndex++];
        }
        for (int i = left; i < helper.length; i++) {
            numbers[i] = helper[i];
        }

        return minSum;
    }
}
