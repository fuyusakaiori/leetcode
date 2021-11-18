package chapter01.search;

import utils.RandomUtil;
import utils.TestUtil;

import java.util.logging.Level;

// 实现二分查找算法
public class BinarySearch
{
    public static void main(String[] args)
    {
        TestUtil.findTest(new RandomUtil(10, 20),
                BinarySearch::loopBinarySearch,  "二分查找");
    }

    private static int binarySearch(int target, int[] numbers){
        return search(numbers, 0,numbers.length - 1, target);
    }

    // 采用递归实现
    private static int search(int[] numbers, int left, int right, int target){

        int mid = left + ((right - left) >> 1);
        if (numbers[mid] == target)
            return mid;
        // 大于小于号不要写反
        else if (numbers[mid] < target && mid + 1 <= right)
            return search(numbers, mid + 1, right, target);
        else if (numbers[mid] > target && mid - 1 >= left)
            return search(numbers, left, mid - 1, target);

        return -1;
    }

    // 采用循环实现
    private static int loopBinarySearch(int target, int[] numbers){
        int left = 0;
        int right = numbers.length - 1;
        int mid = left + ((right - left) >> 1);

        while (mid >= left && mid <= right) {
            if (numbers[mid] == target)
                return mid;
            else if (numbers[mid] > target)
                right = mid - 1;
            else if (numbers[mid] < target)
                left = mid + 1;
            // 左边界和右边界记得更新
            mid = left + ((right - left) >> 1);
        }

        return -1;
    }
}
