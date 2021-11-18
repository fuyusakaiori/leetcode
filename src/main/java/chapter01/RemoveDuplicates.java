package chapter01;

public class RemoveDuplicates
{
    public static void main(String[] args)
    {
        System.out.println(removeDuplicates(new int[]{0,0,1,1,1,2,2,3,3,4}));
    }

    /* 这个题的题意是这样的
    1、首先需要删除数组中的重复项, 但是像 Java 这种只有值传递的语言中显然是不可能在函数中移除元素的
    2、所以这个题内部就做了件事, 就是只要你返回一个长度, 它就能够自动按照这个长度截取数组
    3、也就意味着原数组为 [0,0,1,1,2] 的时候, 返回长度为 3, 截取的数组为 [0,0,1]
    4、从这里可以看出这个题内部肯定不会帮你处理重复的问题, 只是帮你截取, 你得把所有非重复的元素排到一起才行
     */
    public static int removeDuplicates(int[] nums) {
        int length = nums.length;
        int count = 0;
        int index = 0;
        for(int i = 0;i < nums.length;i = i + count + 1){
            count = 0;
            for(int j = i + 1;j < nums.length;j++){
                if(nums[i] == nums[j])
                    count++;
                else{
                    nums[++index] = nums[j];
                    break;
                }
            }
            length -= count;
        }

        return length;
    }
}
