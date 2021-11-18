package chapter01;

public class MaxSubSum
{
    public static void main(String[] args)
    {
        int[] array = {-1, -2};
        System.out.println(maxSubSum1(array, 0, array.length - 1));
    }

    /* 采用递归的方式求最大子序列和
    1、首先明确最大子序列和可能在哪些地方出现, 序列和在左侧、序列和在右侧、序列和在中间
    2、如果能够计算得到左侧、中间、右侧三者的和, 那么显然直接比较就可以知道最大值了
    3、那么如何才能够得到这三者的和呢?
        3.1、仅有一个元素的数组的最大子序列和是最容易判断的, 就是自己本身
        3.2、多个元素的数组如何判读呢?选定中间值, 从中间开始加到左边, 得到左侧的值
        3.3、然后再从中间加到右边, 得到右侧的值
        3.4、那么中间的值如何计算呢?因为是从中间开始加的, 所以两侧之和相加就是中间值
    3、那么接下来就需要考虑如何
    4、
     */
    private static int maxSubSum1(int[] array, int left, int right){

        // 如果左指针等于右指针, 代表数组中仅有一个元素
        if (left == right)
            return array[left];

        // 如果左指针不等于右指针, 那么就需要继续二分分裂
        int center = (left + right) / 2;
        // TODO 左侧最大值
        int maxLeftSum = maxSubSum1(array, left, center);
        // TODO 右侧最大值
        int maxRightSum = maxSubSum1(array, center + 1, right);

        // TODO 中间最大值
        int maxLeftBorderSum = Integer.MIN_VALUE;
        int leftBorderSum = 0;
        for (int i = center; i >= left; i--) {
            leftBorderSum += array[i];
            maxLeftBorderSum = Math.max(leftBorderSum, maxLeftBorderSum);
        }

        int maxRightBorderSum = Integer.MIN_VALUE;
        int rightBorderSum = 0;
        for (int i = center + 1; i <= right; i++) {
            rightBorderSum += array[i];
            maxRightBorderSum = Math.max(rightBorderSum, maxRightBorderSum);
        }

        // 比较左侧最大和、右侧最大和、两侧边界之和的大小
        return Math.max(Math.max(maxLeftSum, maxRightSum), maxLeftBorderSum + maxRightBorderSum);
    }
}
