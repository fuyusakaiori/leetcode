package chapter05.binary;

/**
 * <h2>寻找重复数</h2>
 * <h3>1. 不允许修改数组</h3>
 * <h3>2. 空间复杂度保证为 O(1)</h3>
 * <h3>3. 时间复杂度保证为 O(n)</h3>
 */
public class FindDuplicate {

    /**
     * <h3>二分查找</h3>
     * <h3>1. 在没有任何提示的情况下这个题基本很难想到二分查找的方式, 这是因为原数组本身就是无序的</h3>
     * <h3>2. 如果使用排序让数组有序, 二分查找也就失去了意义, 所以想要在无序数组中使用二分就要构造有序数组</h3>
     * <h3>3. 所以这个构造有序数组的过程就是非常玄学的事情, 有点靠灵感或者猜测的意思</h3>
     * <h3>4. 这个题提到数组中所有的元素都是在 [1,n] 范围内的, 也就是说数组中的数字不是混乱的, 是可以预测的</h3>
     * <h3>5. 根据这个特性就可以统计小于等于 nums[i] 的数有多少个, 并且暂时保存在 cnt 数组中</h3>
     * <h3>5.1 {1, 3, 4, 2, 2}, cnt: <=1 1, <=2 3, <=3 4, <=4 5</h3>
     * <h3>5.2 通过统计小于等于 nums[i] 的数得到的 cnt 数组就具有单调性了, 这样就可以使用二分查找</h3>
     * <h3>6. 二分查找的规则也是非常不好想到的, 这里假设重复的数是 target</h3>
     * <h3>6.1 存在这样的关系: 如果没有重复的数字, 显然 cnt[i] == nums[i], 因为每个数字都只出现一次</h3>
     * <h3>6.2 如果 nums[i] < target, 那么对应 cnt[i] <= nums[i], 因为其余的数要么出现一次要么没有出现</h3>
     * <h3>6.3 如果 nums[i] > target, 那么 cnt[i] >= nums[i], 这是因为包含目标值在内, 所以次数肯定大</h3>
     * <h3>7. 在题目中还要求空间复杂度为 O(1), 所以肯定不能用额外数组保存, 所以只能够每次都去统计</h3>
     * <h3>8. 索引恰好可以表达数组中的数字, 所以就可以直接对索引进行统计, 而不是对数组元素进行统计</h3>
     */
    private static int findDuplicate1(int[] nums){
        int duplicate = 0;
        int left = 0, right = nums.length - 1;
        while (left <= right){
            // 1. 获取需要进行统计的数字
            int mid = (left + right) >> 1;
            // 2. 获取小于等于这个数字的个数
            int cnt = getCount(nums, mid);
            // 3. 判断个数和数字的大小关系:
            if (cnt > mid){
                // 4. 如果个数比数字大, 那么现在肯定偏向右侧, 向左侧移动, 但是这里需要记录结果
                duplicate = mid;
                right = mid - 1;
            }else{
                // 5. 如果个数比较小, 那么现在肯定偏向左侧, 向右侧移动
                left = mid + 1;
            }
        }
        return duplicate;
    }

    private static int getCount(int[] nums, int mid){
        int cnt = 0;
        for (int num : nums){
            cnt += num <= mid ? 1: 0;
        }
        return cnt;
    }


    /**
     * <h3>思路: 快慢指针</h3>
     * <h3>1. 这个做法也是会结合索引来做, 非常巧妙</h3>
     * <h3>2. 数组中的映射关系是这样的</h3>
     * <h3>数组的值:  {1, 3, 4, 2, 2}</h3>
     * <h3>数组的索引: {0, 1, 2, 3, 4}</h3>
     * <h3>3. 那么可以这么考虑: 0 -> 1 -> 3 -> 2 -> 4 -> 2</h3>
     * <h3>4. 形成了环状链表, 所以可以使用快慢指针来找入环的结点, 也就是重复的结点</h3>
     * <h3>注: 为什么入环结点恰好就是重复的结点?</h3>
     */
    private static int findDuplicate2(int[] nums){
        // 1. 快慢指针
        int fast = 0, slow = 0;
        // 2. 第一次相遇
        do{
            fast = nums[nums[fast]];
            slow = nums[slow];
        }while (fast != slow);
        // 3. 第二次相遇
        fast = 0;
        while (fast != slow){
            fast = nums[fast];
            slow = nums[slow];
        }
        return slow;
    }

}
