package chapter09;

import java.util.LinkedList;
import java.util.List;

/**
 * <h2>复原 IP 地址</h2>
 */
public class RestoreIPAddress
{
    public static void main(String[] args)
    {
    }

    /**
     * <p>构建 IP 地址至少 4 位, 至多 12 位, 超过的都应该直接返回空</p>
     */
    private static List<String> restoreIPAddress(String str){
        List<String> ips = new LinkedList<>();
        if (str.length() < 4 || str.length() > 12) return ips;
        recursive(str, 0, 0, "", ips);
        return ips;
    }

    /**
     * <p>思路非常简单, 逐个尝试就行, 但是剪枝条件不太好写</p>
     */
    private static void recursive(String str, int index, int count, String ip, List<String> ips){
        // 0. count == 4 && index == str.length(): 认为成功构建 IP 地址
        if(count == 4){
            if (index == str.length()) ips.add(ip);
            return;
        }
        ip += ".";
        // 1. 前一个分段的长度不能够过长导致后面的分段无法形成
        // 举例: 0.0.0.0 => 第一个分段不能够尝试到 2, 因为后面是不可能组成三个分段的
        // 2. 每个分段最多三位, 最少一位, 还需要考虑的分段数量 * 1 就是至少还有流出来的长度
        // 3. 如果每个分段首位是 0, 那么这个分段长度只能够为 1
        int limit = str.charAt(index) == '0' ? index + 1 : Math.min(str.length() -  (3 - count), index + 3);
        for (int i = index;i < limit;i++){
            int segment = Integer.parseInt(str.substring(index, i + 1));
            // 3. 确保每个分段都是没有越界的
            if (segment < 256)
                recursive(str, i + 1, count + 1, ip + segment, ips);
        }
    }
}
