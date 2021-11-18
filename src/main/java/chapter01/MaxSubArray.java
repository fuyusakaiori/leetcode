package chapter01;

public class MaxSubArray
{
    public static void main(String[] args)
    {
        System.out.println(maxSubArray(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}));
    }

    public static int maxSubArray(int[] nums)
    {
        int index = 0;
        int max = Integer.MIN_VALUE;
        int maxSum = Integer.MIN_VALUE;
        int length = nums.length;
        int[] sub = new int[length];

        if (length == 1)
        {
            return nums[0];
        }

        for (int i = 0; i < length; i++)
        {
            if (nums[i] + max > 0)
            {
                sub[index] = nums[i];
                if (max < nums[i])
                    max = nums[i];
                index++;
            }
            else if (nums[i] + max < 0)
            {
                index = 0;
                max = Integer.MIN_VALUE;
                int sum = getSum(sub);
                if (maxSum < sum)
                {
                    maxSum = sum;
                }
            }
        }

        if (sub[0] != 0)
        {
            int temp = getSum(sub);
            if (temp > maxSum)
            {
                maxSum = temp;
            }
        }

        return maxSum;
    }

    public static int getSum(int[] sub)
    {
        int sum = 0;
        for (int i = 0; i < sub.length; i++)
        {
            sum += sub[i];
        }
        return sum;
    }

}
