package chapter99.netease;

import java.util.*;

/**
 * <h3>两数最少操作次数</h3>
 * <h3>1. 暴力解</h3>
 * <h3>2. 动态规划: 暂时行不通</h3>
 */
public class TwoNumberMinCount {


    /**
     * <h3>题目描述: </h3>
     * <h3>1. 给两个整数</h3>
     * <h3>2. 每次都可以修改其中一个整数的一个数字</h3>
     * <h3>3. 问最少多少次修改可以让第一个数整除第二个数, 或者让第二个数整除第一个数</h3>
     */
    private static int twoNumberMinCount1(long first, long second){
        int minCnt = Integer.MAX_VALUE;
        // 1. 转换成字符串
        char[] fArray = String.valueOf(first).toCharArray();
        char[] sArray = String.valueOf(second).toCharArray();
        // 2. 获取所有子集
        Map<String, Integer> firstSet = new HashMap<>();
        Map<String, Integer> secondSet = new HashMap<>();
        dfs(0, 0 ,fArray, new StringBuilder(), firstSet);
        dfs(0, 0 ,sArray, new StringBuilder(), secondSet);
        // 3. 检查子集
        for (Map.Entry<String, Integer> firstEntry : firstSet.entrySet()) {
            for (Map.Entry<String, Integer> secondEntry : secondSet.entrySet()) {
                first = Long.parseLong(firstEntry.getKey());
                second = Long.parseLong(secondEntry.getKey());
                if (first % second == 0 || second % first == 0)
                    minCnt = Math.min(minCnt, firstEntry.getValue() + secondEntry.getValue());
            }
        }
        return minCnt == Integer.MAX_VALUE ? -1: minCnt;
     }

     private static void dfs(int idx, int cnt, char[] chars, StringBuilder number, Map<String, Integer> subset){
        if (idx == chars.length){
            if (!"".equals(number.toString()) && !"0".equals(number.toString()))
                subset.put(number.toString(), chars.length - cnt);
            return;
        }
        dfs(idx + 1, cnt, chars, number, subset);
        number.append(chars[idx]);
        dfs(idx + 1, cnt + 1, chars, number, subset);
        number.delete(number.length() - 1, number.length());
     }



    public static void main(String[] args) {
        Random random = new Random();
        for (int idx = 0; idx < 100000; idx++) {
            long first = Math.abs(random.nextLong()) % 100;
            long second = Math.abs(random.nextLong()) % 100;
            System.out.println("first = " + first + "\tsecond = " + second);
            int firstResult = twoNumberMinCount1(first, second);
        }
    }



}
