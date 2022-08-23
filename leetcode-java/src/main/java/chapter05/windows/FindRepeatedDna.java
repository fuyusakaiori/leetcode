package chapter05.windows;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <h3>重复的 DNA 序列</h3>
 */
public class FindRepeatedDna {

    private static List<String> findRepeatedDnaSequences1(int cnt, String s){
        List<String> result = new LinkedList<>();
        Map<String, Integer> window = new HashMap<>();
        for (int idx = 0; idx + cnt <= s.length(); idx += cnt) {
            String dna = s.substring(idx, idx + cnt);
            int frequency = window.getOrDefault(dna, 0);
            if (frequency == 1) result.add(dna);
            window.put(dna, frequency + 1);
        }
        return result;
    }

    private static final int offsetConstant = 131313;

    private static List<String> findRepeatedDnaSequences2(int cnt, String s){
        List<String> result = new LinkedList<>();
        // 1. 前缀和
        int[] prefixSum = new int[s.length() + 1];
        int[] offset = new int[s.length() + 1];
        offset[0] = 1;
        // 2. 初始化
        for(int idx = 1;idx <= s.length();idx++){
            prefixSum[idx] = prefixSum[idx - 1] * offsetConstant + s.charAt(idx - 1);
            offset[idx] = offset[idx - 1] * offsetConstant;
        }
        // 3. 滑动窗口
        Map<Integer, Integer> window = new HashMap<>();
        // 4. 移动窗口
        for(int idx = 1;idx + cnt - 1 <= s.length();idx++){
            int hash = prefixSum[idx + cnt - 1] - prefixSum[idx - 1] * offset[cnt];
            int frequency = window.getOrDefault(hash, 0);
            if(frequency == 1)
                result.add(s.substring(idx - 1, idx + cnt - 1));
            window.put(hash, frequency + 1);
        }
        return result;
    }
}
