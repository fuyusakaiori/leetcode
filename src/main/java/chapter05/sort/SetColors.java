package chapter05.sort;

/**
 * <h2>颜色分类: 荷兰国旗问题</h2>
 * <h3>注: 本质就是快速排序</h3>
 */
public class SetColors {

    private static void sortColors(int[] nums) {
        int index = 0;
        int left = 0, right = nums.length - 1;
        while(index < right + 1){
            if(nums[index] < 1){
                swap(nums, index++, left++);
            }else if(nums[index] > 1){
                swap(nums, index, right--);
            }else{
                index++;
            }
        }
    }

    private static void swap(int[] nums, int first, int second){
        int temp = nums[first];
        nums[first] = nums[second];
        nums[second] = temp;
    }

}
