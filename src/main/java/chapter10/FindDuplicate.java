package chapter10;

/**
 * <h2>寻找重复数</h2>
 * <h3>1. 不允许修改数组</h3>
 * <h3>2. 空间复杂度保证为 O(1)</h3>
 * <h3>3. 时间复杂度保证为 O(n)</h3>
 */
public class FindDuplicate {

    /**
     * <h3>思路: 寻找重复数</h3>
     * <h3>1. 这个题的思路非常巧妙, 换句话说就是不好想</h3>
     * <h3>2. 二分查找</h3>
     * <h3>3. 快慢指针</h3>
     */
    private static int findDuplicate(int[] nums){
        return fastSlowPointer(nums);
    }

    /**
     * <h3>二分查找</h3>
     * <h3>1. 首先数组中的元素都是无序的, 所以肯定是不可能直接使用二分查找的, 所以考虑构建一个可以二分的东西</h3>
     * <h3>2. 我们就需要维护一个 cnt 数组, 记录数组中比 nums[i] 小的元素的个数 </h3>
     * <h3>例: {1, 3, 4, 2, 2}, cnt: <=1 1, <=2 3, <=3 4, <=4 5</h3>
     * <h3>3. 然后再仔细观察就会发现</h3>
     * <h3>3.1 如果 nums[i] < target, 那么 cnt <= target</h3>
     * <h3>3.2 如果 nums[i] >= target, 那么 cnt > target</h3>
     * <h3>注: 这个条件反过来也是成立的, 至于存在的细节, 就自己去力扣的文章中看了</h3>
     * <h3>4. 此时就可以使用二分法了</h3>
     * <h3>4.1 如果 cnt > target, 那么证明当前值可能大于等于目标值, 需要记录, 然后向左移动右指针</h3>
     * <h3>4.2 如果 cnt < target, 那么就证明当前值是小于目标值的, 不需要记录, 然后向右移动左指针</h3>
     * <h3>5. 但是依然不可能直接在原数组上二分, 这个是不现实的, 要求空间复杂度为 O(1), 在 cnt 上二分也不行</h3>
     * <h3>6. 仔细观察, 你会发现数组中的值和索引是有关系的 => n+1个数, 数组的值: 1 ~ n, 索引 0 ~ n</h3>
     * <h3>7. 也就是我们可以在索引上进行二分, 只要 cnt 大于等于自己, 就向左移, 左边的数一定比自己小</h3>
     * <h3>8. 也就相当于在 cnt 上向左移动</h3>
     * <h3>注: 有些细节这里没有提及, 但是可以直接被处理</h3>
     */
    private static int binarySearch(int[] nums){
        int result = -1;
        int left = 0, right = nums.length - 1;
        while (left <= right){
            int mid = (right + left) >> 1;
            int count = getCount(nums, mid);
            if (count > mid){
                result = mid;
                right = mid - 1;
            }
            if (count < mid)
                left = mid + 1;
        }
        return result;
    }

    private static int getCount(int[] nums, int mid){
        int count = 0;
        for(int index = 0;index < nums.length;index++){
            // 注: 这里使用的是索引替代数组中的值
            if(nums[index] <= mid) count++;
        }
        return count;
    }

    /**
     * <h3>思路: 快慢指针</h3>
     * <h3>1. 这个做法也是会结合索引来做, 非常巧妙</h3>
     * <h3>2. 数组中的映射关系是这样的</h3>
     * <h3>数组的值:  {1, 3, 4, 2, 2}</h3>
     * <h3>数组的索引: {0, 1, 2, 3, 4}</h3>
     * <h3>3. 那么可以这么考虑: 0 -> 1 -> 3 -> 2 -> 4 -> 2</h3>
     * <h3>4. 形成了环状链表, 所以可以使用快慢指针来找入环的结点, 也就是重复的结点</h3>
     */
    private static int fastSlowPointer(int[] nums){
        int slow = 0;
        int fast = 0;
        do {
            slow = nums[slow];
            fast = nums[nums[fast]];
        }while (slow != fast);

        fast = 0;
        while (fast != slow){
            fast = nums[fast];
            slow = nums[slow];
        }
        // 注: 这里返回的是指针, 而不是数组中的值
        return slow;
    }

}
