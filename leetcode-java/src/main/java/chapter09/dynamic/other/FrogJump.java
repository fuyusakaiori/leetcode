package chapter09.dynamic.other;

import java.util.HashMap;
import java.util.Map;

/**
 * <h3>青蛙过河</h3>
 */
public class FrogJump {

    private static final Map<Integer, Integer> map = new HashMap<>();

    public static boolean canCross(int[] stones) {
        // 1. 记录所有石头的位置
        for(int idx = 0;idx < stones.length;idx++){
            map.put(stones[idx], idx);
        }
        // 2. 递归遍历
        return canCross(0, 0, stones, new HashMap<>());
    }

    public static boolean canCross(int idx, int distance, int[] stones, Map<String, Boolean> dp){
        if(idx == stones.length - 1)
            return true;
        String key = idx + "_" + distance;
        if(dp.containsKey(key))
            return dp.get(key);
        for(int offset = distance - 1;offset <= distance + 1;offset++){
            if(offset <= 0) continue;
            if(map.containsKey(stones[idx] + offset)){
                if(canCross(map.get(stones[idx] + offset), offset, stones, dp)){
                    dp.put(key, true);
                    return true;
                }
            }
        }
        dp.put(key, false);
        return false;
    }

}
