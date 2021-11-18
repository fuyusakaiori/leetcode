package chapter01;

import utils.RandomUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PartMinimum
{
    public static void main(String[] args)
    {
        RandomUtil random = new RandomUtil(20, 50);
        int[] array = random.randomArrayNoReplica();
        System.out.println(Arrays.toString(array));
        System.out.println(findAllMinimum(array));
        System.out.println(findPartMinimum(array, 0, array.length - 1));
    }

    // 对数器
    private static List<Integer> findAllMinimum(int[] nums)
    {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < nums.length; i++)
        {
            if ((i == 0 && nums[i] < nums[i + 1]) ||
                        (i == nums.length - 1 && nums[i - 1] > nums[i]))
            {
                list.add(nums[i]);
            }else if (i != nums.length - 1 && i != 0 &&
                                          nums[i] < nums[i - 1] && nums[i + 1] > nums[i]){
                list.add(nums[i]);
            }
        }

        return list;
    }

    // 二分查找
    private static int findPartMinimum(int[] nums, int left, int right){
        int length = nums.length;
        // 判断两侧是否为局部最小
        if (nums[0] < nums[1]){
            return nums[0];
        }else if (nums[length - 1] < nums[length - 2]){
            return nums[length - 1];
        }
        // 判断中心值是否为局部最小
        int center = (left + right) / 2;
        if (nums[center] < nums[center - 1] && nums[center] < nums[center + 1]){
            return nums[center];
        }

        int minimum = -1;
        if (nums[center] > nums[center - 1]){
            minimum = findPartMinimum(nums, left, center);
        }

        if (nums[center] > nums[center + 1]){
            minimum = findPartMinimum(nums, center, right);
        }

        return minimum;
    }
}
