package chapter05.windows;

import java.util.HashMap;
import java.util.Map;

/**
 * <h3>水果成篮</h3>
 */
public class TotalFruit {


    private static int totalFruit(int[] fruits){
        int trees = 0;
        // 1. 滑动窗口
        int left = 0, right = 0;
        Map<Integer, Integer> window = new HashMap<>();
        // 2. 移动窗口
        while (right < fruits.length){
            // 3. 添加元素
            window.put(fruits[right], window.getOrDefault(fruits[right++], 0) + 1);
            // 4. 是否超过篮子数量
            while (window.size() > 2){
                window.put(fruits[left], window.get(fruits[left] - 1));
                if (window.get(fruits[left]) == 0)
                    window.remove(fruits[left]);
                left++;
            }
            // 5. 随时更新
            trees = Math.max(trees, right - left);
        }
        return trees;
    }

}
