package chapter08;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * <h2>全排列</h2>
 * <h3>1. 全排列</h3>
 * <h3>2. 全排列 II</h3>
 */
public class Permutation {

    /**
     * <h3>注: 不使用额外数组来标记已经选中的数字, 直接在原数组中修改</h3>
     */
    private static final int INF = Integer.MIN_VALUE;

    /**
     * <h3>思路: 全排列</h3>
     * <h3>注: 这里的回溯算法不是暴力解, 这个题用暴力解是写不出来的</h3>
     */
    private static List<List<Integer>> permute(int[] nums){
        List<List<Integer>> cs = new LinkedList<>();
        dfs(nums, new LinkedList<>(), cs);
        return cs;
    }

    private static void dfs(int[] nums, List<Integer> c, List<List<Integer>> cs){
        if(c.size() == nums.length){
            cs.add(new LinkedList<>(c));
            return;
        }
        for (int index = 0;index < nums.length;index++){
            if (nums[index] != INF){
                c.add(nums[index]);
                nums[index] = INF;
                dfs(nums, c, cs);
                nums[index] = c.remove(c.size() - 1);
            }
        }
    }


    /**
     * <h3>思路: 全排列 II</h3>
     * <h3>去重的有两个思路: </h3>
     * <h3>1. 避免重复选择相同元素, 也就是在循环中判断前后是否相同, 如果相同那么就跳过, 如果不同就可以选择</h3>
     * <h3>&nbsp&nbsp 但是这会导致深度遍历也无法选择相同的元素, 但是可以通过采用原地修改的方式避免这个问题</h3>
     * <h3>2. 调整相同元素直之间的顺序, 避免连续选择相同的元素, 这样就可以采用额外数组的方式来实现</h3>
     */
    private static List<List<Integer>> permuteUnique1(int[] nums){
        // 注: 只有在有序的情况下才可以使用这种去重的方式
        Arrays.sort(nums);
        List<Integer> c = new LinkedList<>();
        List<List<Integer>> cs = new LinkedList<>();
        dfs(c, cs, nums);
        return cs;
    }

    private static void dfs(List<Integer> c, List<List<Integer>> cs, int[] nums){
        if (c.size() == nums.length){
            cs.add(new LinkedList<>(c));
            return;
        }
        for (int index = 0; index < nums.length; index++) {
            if (nums[index] == INF || (index > 0 && nums[index] == nums[index - 1]))
                continue;
            c.add(nums[index]);
            nums[index] = INF;
            dfs(c, cs, nums);
            nums[index] = c.remove(c.size() - 1);
        }
    }

    private static List<List<Integer>> permuteUnique2(int[] nums){
        Arrays.sort(nums);
        List<Integer> c = new LinkedList<>();
        List<List<Integer>> cs = new LinkedList<>();
        dfs(nums, new boolean[nums.length], c, cs);
        return cs;
    }

    private static void dfs(int[] nums, boolean[] visited, List<Integer> c, List<List<Integer>> cs){
        if (c.size() == nums.length){
            cs.add(new LinkedList<>(c));
            return;
        }
        for (int index = 0; index < nums.length; index++) {
            // 注: 这里添加 !visited[index - 1] 的目的就是避免两个相同的元素被连续选择, 从而完成去重
            if (visited[index] ||
                        (index > 0 && nums[index] == nums[index - 1] && !visited[index - 1]))
                continue;
            c.add(nums[index]);
            visited[index] = true;
            dfs(nums, visited, c, cs);
            visited[index] = false;
            c.remove(c.size() - 1);
        }
    }

}
