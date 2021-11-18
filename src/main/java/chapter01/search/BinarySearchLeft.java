package chapter01.search;

import utils.RandomUtil;
import utils.TestUtil;

public class BinarySearchLeft
{
    public static void main(String[] args)
    {
//        TestUtil.findTest(new RandomUtil(20, 100),
//                BinarySearchLeft::binarySearchLeft, "查找左侧最大值");
        System.out.println(TestUtil.isRight(new RandomUtil(20, 100),
                BinarySearchLeft::binarySearchLeft, "查找左侧最大值"));
    }

    private static int binarySearchLeft(int target, int[] numbers){
        return searchLeft(numbers, target, 0, numbers.length - 1);
    }


    // 查询大于等于目标值最左边的位置
    private static int searchLeft(int[] numbers, int target, int left, int right){

        return -1;
    }

    private static int searchLeft(int target, int[] numbers){
        int left = 0;
        int right = numbers.length - 1;
        int mid = left + ((right - left) >> 1);
        int res = -1;

        while (left <= right){
            if (numbers[mid] < target){
                left = mid + 1;
            }
            else if (numbers[mid] >= target){
                // 记录当前这个符合条件的值
                res = numbers[mid];
                right = mid - 1;
            }

            mid = left + ((right - left) >> 1);
        }

        return res;
    }
}
