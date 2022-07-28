package chapter08;

import java.util.LinkedList;
import java.util.List;

/**
 * <h2>组合总和问题</h2>
 * <h3>1. 组合总和</h3>
 * <h3>2. 组合总和 II</h3>
 * <h3>3. 组合总和 III</h3>
 * <h3>4. 组合总和 IV</h3>
 * <h3>5. 组合</h3>
 */
public class CombinationSum {

    /**
     * <h3>思路: 组合总和</h3>
     * <h3>1. 回溯算法</h3>
     * <h3>2. 背包问题解法</h3>
     * <h3>注: 这两种方式本质都是回溯算法, 但是两者的思路有所区别</h3>
     */
    private static List<List<Integer>> combinationSum(int[] candidates, int target){
        List<Integer> combine = new LinkedList<>();
        List<List<Integer>> combines = new LinkedList<>();
        dfs(candidates, target, 0, combine, combines);
        return combines;
    }

    private static void dfs(int[] candidates, int target, int index, List<Integer> combine, List<List<Integer>> combines){
        if (target == 0){
            combines.add(new LinkedList<>(combine));
            return;
        }
        if (index == candidates.length || target < 0)
            return;
        // 1. 不选择当前这个数
        dfs(candidates, target, index + 1, combine, combines);
        // 2. 继续选择当前这个数
        combine.add(candidates[index]);
        dfs(candidates, target - candidates[index], index, combine, combines);
        combine.add(combine.size() - 1);
    }

    /**
     * <h3>思路: 组合总和 II</h3>
     */
    private static List<List<Integer>> combinationSum(int target, int[] candidates){
        List<Integer> combine = new LinkedList<>();
        List<List<Integer>> combines = new LinkedList<>();
        dfs(0, target, false, candidates, combine, combines);
        return combines;
    }

    private static void dfs(int index, int target, boolean choose, int[] candidates, List<Integer> combine, List<List<Integer>> combines){
        if (target == 0){
            combines.add(new LinkedList<>(combine));
            return;
        }
        if (index == candidates.length || target < 0)
            return;
        if (!choose && index > 0 && candidates[index] == candidates[index - 1])
            return;
        dfs(index + 1, target, false, candidates, combine, combines);
        combine.add(candidates[index]);
        dfs(index + 1, target - candidates[index], true, candidates, combine, combines);
        combine.remove(combine.size() - 1);
    }

    /**
     * <h3>思路: 组合总和 III</h3>
     */
    private static List<List<Integer>> combinationSum(int k, int n){
        List<Integer> combine = new LinkedList<>();
        List<List<Integer>> combines = new LinkedList<>();
        dfs(9, n, k, combine, combines);
        return combines;
    }

    private static void dfs(int number, int target, int count, List<Integer> combine, List<List<Integer>> combines){
        if (target == 0){
            if (count == 0)
                combines.add(new LinkedList<>(combine));
            return;
        }
        if (number == 0 || target < 0)
            return;
        dfs(number - 1, target, count, combine, combines);
        combine.add(number);
        dfs(number - 1, target - number, count - 1, combine, combines);
        combine.remove(combine.size() - 1);
    }

    /**
     * <h3>思路: 组合总和 IV</h3>
     */
    private static int combinationCount(int[] nums, int target){
        int[] dp = new int[target + 1];
        return dfs(nums, target, dp);
    }

    private static int dfs(int[] nums, int target, int[] dp){
        if (target == 0)
            return 1;
        if (target < 0)
            return 0;
        if (dp[target] != Integer.MIN_VALUE)
            return dp[target];
        int count = 0;
        for (int num : nums) {
            count += dfs(nums, target - num, dp);
        }
        return dp[target] = count;
    }

    /**
     * <h3>思路: 组合</h3>
     */
    public List<List<Integer>> combine(int n, int k) {
        List<Integer> c = new LinkedList<>();
        List<List<Integer>> cs = new LinkedList<>();
        dfs(n, k, c, cs);
        return cs;
    }

    public void dfs(int n, int k, List<Integer> c, List<List<Integer>> cs){
        if(n < k)
            return;
        if(k == 0){
            cs.add(new LinkedList<>(c));
            return;
        }
        if(n == 0)
            return;
        dfs(n - 1, k, c, cs);
        c.add(n);
        dfs(n - 1, k - 1, c, cs);
        c.remove(c.size() - 1);
    }

}
