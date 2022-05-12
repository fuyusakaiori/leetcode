package chapter07;

/**
 * <h2>双指针系列题目</h2>
 * <h3>1 合并两个有序数组</h3>
 * <h3>2. 移动零</h3>
 * <h3>注: 这里的题目主要做法就是采用双指针, 并不是不可以采用其他做法</h3>
 */
public class DoublePointer {

    /**
     * <h3>思路: 合并两个有序数组</h3>
     * <h3>1. 传统双指针: 这种做法会占据额外空间</h3>
     * <h3>2. 逆向双指针: 这种做法不会使用额外空间</h3>
     * <h3>扩展: 去除重复元素; 合并三个有序数组</h3>
     */
    private static void merge1(int[] nums1, int len1, int[] nums2, int len2){
        int first = 0, second = 0, index = 0;
        int[] merge = new int[len1 + len2];
        while(first < len1 && second < len2){
            merge[index++] = nums1[first] < nums2[second] ? nums1[first++]: nums2[second++];
        }
        if(first < len1)
            System.arraycopy(nums1, first, merge, index, len1 - first);
        if(second < len2)
            System.arraycopy(nums2, second, merge, index, len2 - second);

        System.arraycopy(merge, 0, nums1, 0, len1 + len2);
    }

    private static void merge2(int[] nums1, int len1, int[] nums2, int len2){
        int first = len1 - 1, second = len2 - 1, index = nums1.length - 1;
        // 注: 这样写的目的仅仅是为了代码好看, 和传统双指针的解法没有区别
        while (first >= 0 || second >= 0){
            if (first < 0){
                nums1[index--] = nums2[second--];
            }else if (second < 0){
                nums1[index--] = nums1[first--];
            }else if (nums1[first] < nums2[second]){
                nums1[index--] = nums2[second--];
            }else{
                nums1[index--] = nums1[first--];
            }
        }
    }

    /**
     * <h3>思路: 移动零</h3>
     */
    private static void moveZeroes(int[] nums) {
        int cur = 0;
        for(int index = 0;index < nums.length;index++){
            if(nums[index] != 0)
                swap(nums, cur++, index);
        }
    }

    private static void swap(int[] numbers, int first, int second){
        int temp = numbers[first];
        numbers[first] = numbers[second];
        numbers[second] = temp;
    }

}
