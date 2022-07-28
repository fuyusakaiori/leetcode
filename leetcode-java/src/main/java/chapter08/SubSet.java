package chapter08;

import java.util.LinkedList;
import java.util.List;

/**
 * <h2>子集问题</h2>
 * <h3>1. 子集</h3>
 * <h3>2. 子集 II</h3>
 */
public class SubSet {

    /**
     * <h3>思路: 子集</h3>
     */
    private static List<List<Integer>> subsets(int[] nums) {
        List<Integer> subset = new LinkedList<>();
        List<List<Integer>> subsets = new LinkedList<>();
        dfs(0, nums, subset, subsets);
        return subsets;
    }

    private static void dfs(int index, int[] nums, List<Integer> subset, List<List<Integer>> subsets){
        if(index == nums.length){
            subsets.add(new LinkedList<>(subset));
            return;
        }
        dfs(index + 1, nums, subset, subsets);
        subset.add(nums[index]);
        dfs(index + 1, nums, subset, subsets);
        subset.remove(subset.size() - 1);
    }

    /**
     * <h3>思路: 子集 II</h3>
     * <h3>去重的核心思路没有变化, 但是表现形式会有变化</h3>
     */
    private static List<List<Integer>> subsetsWithDup(int[] nums){
        List<Integer> subset = new LinkedList<>();
        List<List<Integer>> subsets = new LinkedList<>();
        dfs(0, nums, false, subset, subsets);
        return subsets;
    }

    /**
     * <h3>注: 不需要使用整个布尔数组, 只需要单个布尔变量</h3>
     */
    private static void dfs(int index, int[] nums, boolean choose, List<Integer> subset, List<List<Integer>> subsets){
        if (index == nums.length){
            subsets.add(new LinkedList<>(subset));
            return;
        }
        dfs(index + 1, nums, false, subset, subsets);
        // 注: 如果之前没有选择任何元素, 然后还发现上一个元素和自己相等, 那么证明以自己为起点的子集已经存在了, 所以直接返回
        if (!choose && index > 0 && nums[index] == nums[index - 1])
            return;
        dfs(index + 1, nums, true, subset, subsets);
    }

}
