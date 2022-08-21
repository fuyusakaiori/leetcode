package chapter05.windows;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * <h3>存在重复元素</h3>
 * <h3>存在重复元素 II</h3>
 * <h3>存在重复元素 III</h3>
 */
public class ContainsDuplicate {

    /**
     * <h3>存在重复元素 II</h3>
     * <h3>固定窗口大小的可以只使用右指针</h3>
     */
    public static boolean containsNearbyDuplicate(int[] nums, int k) {
        // 1. 滑动窗口
        Set<Integer> window = new HashSet<>();
        // 2. 移动窗口
        for(int idx = 0;idx < nums.length;idx++){
            // 3. 移除元素
            if(idx > k)
                window.remove(nums[idx - k - 1]);
            // 4. 新增元素
            if(!window.add(nums[idx]))
                return true;
        }
        return false;
    }

    /**
     * <h3>存在重复元素 III</h3>
     */
    public static boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        // 1. 滑动窗口
        TreeSet<Long> window = new TreeSet<>();
        // 2. 移动窗口
        for(int idx = 0;idx < nums.length;idx++){
            // 3. 移除元素
            if(idx > k)
                window.remove((long)nums[idx - k -1]);
            // 4. 获取和当前元素相差最小的两个元素
            long number = nums[idx];
            Long floor = window.floor(number);
            Long ceil = window.ceiling(number);
            // 5. 是否在范围内
            if(floor != null && number - floor <= t)
                return true;
            if(ceil != null && ceil - number <= t)
                return true;
            window.add(number);
        }
        return false;
    }

}
