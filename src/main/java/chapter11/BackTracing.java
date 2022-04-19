package chapter11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * <h2>回溯系列题目</h2>
 * <h3>1. 全排列</h3>
 * <h3>2. 全排列 II</h3>
 * <h3>3. 组合总和</h3>
 * <h3>4. 组合总和 II</h3>
 * <h3>5. 组合总和 III</h3>
 * <h3>6. 组合总和 IV</h3>
 * <h3>7. 子集</h3>
 * <h3>8. 子集 II</h3>
 * <h3>注: 动态规划都是可以使用回溯算法实现的, 但是不是所有回溯题目都可以采用动态规划</h3>
 * <h3>注: 结果去重通常会有两种方式, 前者就是采用哈希表, 后者就是在有序的情况下前后比较避免选中相同的值</h3>
 */
public class BackTracing {

    /**
     * <h3>思路: 全排列</h3>
     */
    private static List<List<Integer>> permute(int[] nums){
        List<List<Integer>> results = new LinkedList<>();
        dfs(nums, results, new ArrayList<>(nums.length));
        return results;
    }

    private static void dfs(int[] nums, List<List<Integer>> results, List<Integer> result){
        // 1. 如果集合中已经满了, 说明完成组合
        if (result.size() == nums.length){
            results.add(new LinkedList<>(result));
            return;
        }
        // 2. 如果集合中没有满, 那么就继续组合, 每次都要从零开始, 不过已经组合过的就不要再用了
        for (int index = 0;index < nums.length;index++){
            // 3. 避免重复选择
            if (nums[index] == Integer.MAX_VALUE)
                continue;
            result.add(nums[index]);
            // 4. 修改该元素的值, 从而避免之后再选中
            nums[index] = Integer.MAX_VALUE;
            // 5. 深度遍历
            dfs(nums, results, result);
            // 6. 回溯行为
            nums[index] = result.remove(result.size() - 1);
        }
    }

    /**
     * <h3>思路: 全排列 II</h3>
     */
    private static List<List<Integer>> permuteUnique(int[] nums){
        // 注: 这里必须要排序, 否则没有办法去重
        Arrays.sort(nums);
        List<List<Integer>> results = new LinkedList<>();
        dfs(new ArrayList<>(nums.length), results, nums);
        return results;
    }

    private static void dfs(List<Integer> result, List<List<Integer>> results, int[] nums){
        if (result.size() == nums.length){
            results.add(new ArrayList<>(result));
            return;
        }
        for (int index = 0;index < nums.length;index++){
            if (nums[index] == Integer.MAX_VALUE || nums[index] == nums[index - 1])
                continue;
            result.add(nums[index]);
            nums[index] = Integer.MAX_VALUE;
            dfs(result, results, nums);
            nums[index] = result.remove(result.size() - 1);
        }
    }

    /**
     * <h3>思路: 组合总和</h3>
     * <h3>1. 可以按照背包问题的思路去解, 但是时间复杂度是 O(n^2)</h3>
     * <h3>2. 采用回溯去解, 可以避免递归过程中出现循环</h3>
     */
    private static List<List<Integer>> combinationSum1(int[] candidates, int target){
        List<List<Integer>> results = new LinkedList<>();
        dfs(candidates, results, new LinkedList<>(), target, 0);
        return results;
    }

    private static void dfs(int[] candidates, List<List<Integer>> results, List<Integer> result, int target, int index){
        // 1. target == 0 的时候结束递归
        if (target <= 0 || index == candidates.length){
            if (target == 0)
                results.add(new LinkedList<>(result));
            return;
        }
        // 2. 可以不选择当前这个数字而是去选择接下来的数字
        dfs(candidates, results, result, target, index + 1);
        // 3. 重复选择当前的数字
        result.add(candidates[index]);
        dfs(candidates, results, result, target - candidates[index], index);
        result.remove(result.size() - 1);
    }

    /**
     * <h3>1. 如果用背包问题的思路来解, 那么就没有循环, 这也就导致第二种去重方式无法实现, 只能用哈希表去重</h3>
     * <h3>2. 所以就不要采用背包问题的思路来解, 就使用最常见的暴力解, 这样会更加容易去重</h3>
     */
    private static List<List<Integer>> combinationSum2(int[] candidates, int target){
        Arrays.sort(candidates);
        List<List<Integer>> results = new LinkedList<>();
        dfs(target, 0, candidates, new LinkedList<>(), results);
        return results;
    }

    private static void dfs(int target, int start, int[] candidates, List<Integer> result, List<List<Integer>> results){
        if (start == candidates.length || target == 0){
            if (target == 0)
                results.add(new LinkedList<>(result));
            return;
        }
        for (int index = start;index < candidates.length;index++){
            if (index > start && candidates[index - 1] == candidates[index])
                continue;
            if (target - candidates[index] < 0)
                break;
            result.add(candidates[index]);
            dfs(target - candidates[index], index, candidates, result, results);
            result.remove(result.size() - 1);
        }
    }

    /**
     * <h3>思路: 组合总和 III</h3>
     */
    private static List<List<Integer>> combinationSum3(int count, int target){
        List<List<Integer>> results = new LinkedList<>();
        dfs(target, 1, count, results, new LinkedList<>());
        return results;
    }

    private static void dfs(int target, int number, int count, List<List<Integer>> results, List<Integer> result){
        if(target == 0 && count == 0){
            results.add(new LinkedList<>(result));
            return;
        }
        if(number > 9)
            return;
        dfs(target, number + 1, count, results, result);
        result.add(number);
        dfs(target - number, number + 1, count - 1, results, result);
        result.remove(result.size() - 1);
    }

    /**
     * <h3>思路: 组合总和 IV</h3>
     * <h3>1. 从这个题就可以看出来回溯和动态规划的关系</h3>
     * <h3>2. 回溯说穿了也是暴力解的一种, 通过记忆化搜索可以变为动态规划</h3>
     * <h3>3. 这里使用回溯就会直接超时, 所以需要使用记忆化搜索减少时间</h3>
     */
    private static int combinationSum4(int[] nums, int target){
        Integer[] dp = new Integer[target + 1];
        return dfs(nums, target, dp);
    }

    private static int dfs(int[] nums, int target, Integer[] dp){
        if(target == 0)
            return 1;
        if(dp[target] != null)
            return dp[target];
        int count = 0;
        for (int num : nums) {
            if (target - num < 0)
                continue;
            count += dfs(nums, target - num, dp);
        }
        return dp[target] = count;
    }

    /**
     * <h3>思路: 子集</h3>
     */
    private static List<List<Integer>> subsets(int[] nums){
        return null;
    }

    /**
     * <h3>思路: 子集</h3>
     */
    private static List<List<Integer>> subsetsWithDup(int[] nums){
        return null;
    }

}
