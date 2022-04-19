package utils;


import java.util.*;

/**
 * <h2>测试各种写法</h2>
 */
public class Test
{

    public static void main(String[] args){
        combinationSum3(9, 45);
    }

    public static List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> results = new LinkedList<>();
        dfs(n, 1, k, results, new LinkedList<>());
        return results;
    }

    public static void dfs(int target, int number, int count, List<List<Integer>> results, List<Integer> result){
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
}
