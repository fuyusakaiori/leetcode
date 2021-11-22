package chapter03;

import java.util.HashMap;
import java.util.Map;

/**
 * 不同的二叉搜索树
 */
public class BinarySearchNumbers
{
    public static void main(String[] args)
    {

    }

    public static int numTrees(int n) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 0);
        map.put(1, 1);

        return map.get(n);
    }

    public static void numberOfTrees(int n, Map<Integer, Integer> map){
        if(!map.containsKey(n - 1))
            numberOfTrees(n - 1, map);

        int numbers = 0;
        for(int i = 1;i <= n;i++){
            numbers += map.get(n - i) + map.get(i - 1);
        }
        map.put(n, numbers);
    }
}
