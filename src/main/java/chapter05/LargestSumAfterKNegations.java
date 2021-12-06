package chapter05;

import java.util.Arrays;

public class LargestSumAfterKNegations
{
    public static void main(String[] args)
    {
        largestSumAfterKNegations(new int[]{-4, -2, -3}, 4);
    }

    public static int largestSumAfterKNegations(int[] nums, int k) {
        Arrays.sort(nums);
        int index = 0;
        int count = 0;
        boolean flag = true;
        while(count < k){
            if(index == nums.length)
                index %= nums.length;
            if(flag && nums[index] < 0){
                nums[index] = -nums[index];
                index++;
                count++;
            }else{
                flag = false;
                if(index - 1 > 0 && Math.abs(nums[index - 1]) < Math.abs(nums[index]))
                    nums[index - 1] = -nums[index - 1];
                else
                    nums[index] = -nums[index];
                count++;
            }
        }
        int sum = 0;
        for(int i = 0;i < nums.length;i++){
            sum += nums[i];
        }
        return sum;
    }
}
