package chapter11.greedy;

/**
 * <h2>下一个排列</h2>
 * <h3>注: 一次性题目, 没有任何技巧可言, 非常难想</h3>
 */
public class NextPermutation
{

    /**
     * <h3>思路: 下一个排列</h3>
     * <h3>1. 下一个排列的定义其实是得到数字序列的字典序中下一个更大的排列</h3>
     * <h3>2. 用人话来说就是希望找到比当前数字序列大的序列, 但是这个序列还要尽可能小</h3>
     * <h3>3. 既然希望数字序列变大, 那么最直接的办法就是将后面的 [大数] 和前面的 [小数] 交换</h3>
     * <h3>4. 同时为了使得这个序列尽可能小, 这个 [大数] 要尽可能小, [小数] 要尽可能大</h3>
     * <h3>注: 现在问题在于如何去找这个 [大数] 和 [小数], 也就是算法的核心所在</h3>
     * <h3>注: 可以说这个题的思路能难死一大批人, 很难想到, 就算想到了实现也存在困难</h3>
     */
    private static void nextPermutation(int[] nums){
        // 1. 找到 [小数]: 从后向前查找, 直到出现降序那么就认为找到了 [小数]
        int first = nums.length - 2;
        while (first >= 0 && nums[first] >= nums[first + 1])
            first--;
        // 2. 找到 [大数]: 重新从后向前查找, 找到第一个比 [小数] 大的就是 [大数]
        int second = nums.length - 1;
        if (first >= 0){
            while (second >= first && nums[second] <= nums[first])
                second--;
            // 3. 交换 [大数] 和 [小数] 使得数字序列变大
            swap(nums, first, second);
        }
        // 4. 如果 [小数] 最后超过左边界, 那么这个数列肯定就是降序的, 直接反转就行
        reverse(nums, first + 1, nums.length - 1);
    }

    private static void reverse(int[] nums, int left, int right){
        while (left < right){
            swap(nums, left++, right--);
        }
    }

    private static void swap(int[] nums, int first, int second){
        int temp = nums[first];
        nums[first] = nums[second];
        nums[second] = temp;
    }


}
