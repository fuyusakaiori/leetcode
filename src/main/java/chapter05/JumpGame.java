package chapter05;

public class JumpGame
{
    public static void main(String[] args)
    {
        String str1 = "34";
        String str2 = "9";
        System.out.println(str1.compareTo(str2));
        new StringBuilder();
    }

    public static boolean canJump(int[] nums) {
        int steps = 0;
        int index = 0;
        while(nums.length - 1 - index > nums[index]){
            steps = nums[index];
            if(steps == 0)
                return false;
            if(nums[index + 1] > steps)
                index++;
            else
                index = steps + 1;
        }
        return true;
    }
}
