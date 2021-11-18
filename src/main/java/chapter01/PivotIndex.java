package chapter01;

public class PivotIndex
{
    public static void main(String[] args)
    {
        int[] array = {-1, -1, 0, 1, 1, 0};
        System.out.println(new PivotIndex().pivotIndex2(array));
    }

    // 最暴力的解法
    public int pivotIndex(int[] nums) {
        int index = 0;
        int[] pivot = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            int leftSum = getSum(0, i, nums);
            int rightSum = getSum(i+1, nums.length, nums);
            if (leftSum == rightSum){
                pivot[index] = i;
                index++;
            }
        }
        if (index == 0){
            return -1;
        }

        return pivot[0];
    }

    public int getSum(int left, int right, int[] nums){
        int sum = 0;
        for(int i = left;i < right;i++){
            sum += nums[i];
        }

        return sum;
    }

    // 核心: 考虑减少循环的次数 速度 100% 击败 内存占用有点高
    public int pivotIndex2(int[] nums){
        int sum = 0;
        int length = nums.length;
        // 获得数组所有元素之和
        for(int i = 0;i < length;i++){
            sum += nums[i];
        }
        int leftSum = 0;
        int rightSum = 0;
        // 从最左侧开始遍历
        for(int i = 0;i < length;i++){
            rightSum = sum - nums[i] - leftSum;
            if(leftSum == rightSum){
                return i;
            }
            leftSum += nums[i];
        }

        return -1;
    }
}
