package chapter03;

import utils.TreeNode;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <p>寻找二叉搜索树中的众数</p>
 * <p>如果存在多个众数, 那么还需要将多个众数一起返回</p>
 * <p>1. 采用空间复杂度为 O(N) 的算法解决</p>
 * <p>2. 采用空间复杂度为 O(1) 的算法解决（这种方式并不是特别好想）</p>
 */
public class BinarySearchTreeMode
{
    public static void main(String[] args)
    {

    }

    private static final Map<Integer, Integer> map = new HashMap<>();

    /**
     * 最暴力的解法, 面试写这种直接回家
     */
    private static List<Integer> findModeViolence(TreeNode root){
        int max = 0;
        recursive(root);
        List<Integer> counts = new LinkedList<>(map.values());
        List<Integer> modes = new LinkedList<>();
        for(int count : counts)
            max = Math.max(count, max);
        for(Map.Entry<Integer, Integer> entry : map.entrySet()){
            if(entry.getValue() == max)
                modes.add(entry.getKey());
        }
        return modes;
    }

    private static void recursive(TreeNode root){
        if(root == null)
            return;
        if(map.containsKey(root.value))
            map.put(root.value, map.get(root.value) + 1);
        else
            map.put(root.value, 1);
        recursive(root.left);
        recursive(root.right);
    }

    /**
     * 递归完成的, 在这个题中不算做额外空间
     */
    private static List<Integer> findMode(TreeNode root){

        return null;
    }

    /**
     * <p>树的题目中出现要求空间复杂度为 O(1) 的时候, 都是可以考虑 Morris 遍历的</p>
     * <p>真正做到空间复杂度为 O(1)</p>
     */
    private static List<Integer> findModeMorris(TreeNode root){

        return null;
    }
}
