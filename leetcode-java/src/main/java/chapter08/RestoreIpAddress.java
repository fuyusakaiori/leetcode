package chapter08;

import java.util.LinkedList;
import java.util.List;

/**
 * <h2>复原 IP 地址</h2>
 */
public class RestoreIpAddress {

    private static final int DEFAULT_SEGLENGTH = 3;
    private static final int MIM_SEGLENGTH = 1;

    private static final int SEGMENT_COUNT = 4;

    private static List<String> restoreIpAddresses(String source){
        List<String> ips = new LinkedList<>();
        dfs(0, 0, source, new StringBuilder(), ips);
        return ips;
    }

    private static void dfs(int start, int count, String source, StringBuilder ip, List<String> ips){
        // 注: 如果已经有三段了, 那么最后一段只能够是剩下的全部
        if (count == SEGMENT_COUNT - 1 && start != source.length()){
            if (source.length() - start > 3)
                return;
            String substring = source.substring(start);
            if (substring.length() > 1 && substring.charAt(0) == '0')
                return;
            int segment = Integer.parseInt(substring);
            if (segment >= 0 && segment <= 255)
                ips.add(ip.toString() + substring);
            return;
        }
        // 注: 如果在没有三段之前就已经走到末尾, 那么就直接返回
        if (start == source.length())
            return;
        // 1. 确定每段的长度, 默认长度肯定是 3
        int segLength = DEFAULT_SEGLENGTH;
        // 2. 如果剩余的长度不足, 那么就需要取小的那个
        segLength = Math.min(source.length() - start, segLength);
        // 3. 如果有前导零, 那么分段的长度只能是 1
        segLength = source.charAt(start) == '0' ? MIM_SEGLENGTH: segLength;
        // 4. 开始分段处理
        for (int index = start;index < start + segLength;index++){
            String substring = source.substring(start, index + 1);
            int segment = Integer.parseInt(substring);
            if (segment >= 0 && segment <= 255){
                ip.append(segment).append(".");
                dfs(index + 1, count + 1, source, ip, ips);
                // 注: 记得删除
                ip.delete(ip.length() - substring.length() - 1, ip.length());
            }
        }
    }

}
