package chapter09.greedy.jump;

import java.util.*;

/**
 * <h3>跳跃游戏</h3>
 * <h3>注: 动态规划是通解, 贪心算法是特殊解</h3>
 */
public class JumpGame {

    /**
     * <h3>贪心算法</h3>
     */
    public static boolean canJump1(int[] nums)
    {
        // 1. 跳跃的最远距离
        int distance = 0;
        for (int idx = 0; idx < nums.length; idx++)
        {
            // 2. 如果跳跃的最大距离无法达到当前位置, 那么就认为无法到达终点
            if (idx > distance)
                return false;
            // 3. 更新可以跳跃的最大距离
            distance = Math.max(distance, idx + nums[idx]);
        }
        return true;
    }

    /**
     * <h3>跳跃游戏 II</h3>
     */
    public static int canJump2(int[] nums)
    {
        // 1. 最小跳跃次数
        int minCnt = 0;
        // 2. 记录每次可以跳跃的最远区间
        int distance = 0;
        int start = 0, end = 0;
        // 3. 开始跳跃
        while (end < nums.length - 1)
        {
            // 4. 根据现在可以跳跃的区间计算最远可以跳跃的距离
            for (int idx = start; idx <= end; idx++)
            {
                distance = Math.max(distance, idx + nums[idx]);
            }
            // 5. 每次都跳跃到最远可以跳跃的距离
            start = end;
            end = distance;
            // 6. 记录跳跃的次数
            minCnt++;
        }
        return minCnt;
    }

    /**
     * <h3>跳跃游戏 III</h3>
     * <h3>深度优先</h3>
     */
    public static boolean canReach1(int[] arr, int start)
    {
        return canReach1(start, arr, new boolean[arr.length]);
    }

    public static boolean canReach1(int idx, int[] arr, boolean[] visited)
    {
        if (idx < 0 || idx > arr.length - 1)
            return false;
        if (visited[idx])
            return false;
        if (arr[idx] == 0)
            return true;
        visited[idx] = true;
        return canReach1(idx + arr[idx], arr, visited) || canReach1(idx - arr[idx], arr, visited);
    }

    /**
     * <h3>广度优先</h3>
     */
    public static boolean canReach2(int[] arr, int start)
    {
        // 0. 记录结点是否已经遍历过
        boolean[] visited = new boolean[arr.length];
        // 1. 队列
        Queue<Integer> queue = new LinkedList<>();
        // 2. 元素入队
        queue.offer(start);
        visited[start] = true;
        // 3. 开始遍历
        while (!queue.isEmpty())
        {
            int cur = queue.poll();
            // 4. 如果元素的值就是 0, 直接返回
            if (arr[cur] == 0)
                return true;
            // 5. 尝试跳跃到左侧
            if (cur - arr[cur] >= 0 && !visited[cur - arr[cur]])
            {
                queue.offer(cur - arr[cur]);
                visited[cur - arr[cur]] = true;
            }
            // 6. 尝试跳跃到右侧
            if (cur + arr[cur] <= arr.length - 1 && !visited[cur + arr[cur]])
            {
                queue.offer(cur + arr[cur]);
                visited[cur + arr[cur]] = true;
            }
        }
        return false;
    }


    private static final Map<Integer, List<Integer>> map = new HashMap<>();

    /**
     * <h3>跳跃游戏 IV</h3>
     * <h3>广度优先</h3>
     */
    public static int minJumps(int[] arr) {
        // 1. 获取所有相同的元素索引
        for (int idx = 0; idx < arr.length; idx++) {
            List<Integer> locations = map.getOrDefault(arr[idx], new LinkedList<>());
            locations.add(idx);
            map.put(arr[idx], locations);
        }
        // 2. 遍历队列
        boolean[] visited = new boolean[arr.length];
        Queue<int[]> queue = new LinkedList<>();
        // 3. 元素入队
        queue.offer(new int[]{0, 0});
        visited[0] = true;
        // 4. 开始遍历队列
        while (!queue.isEmpty()) {
            int[] couple = queue.poll();
            int cur = couple[0], cnt = couple[1];
            // 5. 如果到达终点
            if (cur == arr.length - 1)
                return cnt;
            cnt++;
            // 6. 尝试跳跃到左侧
            if (cur - 1 >= 0 && !visited[cur - 1]) {
                queue.offer(new int[]{cur - 1, cnt});
                visited[cur - 1] = true;
            }
            // 7. 尝试跳跃到右侧
            if (cur + 1 <= arr.length - 1 && !visited[cur + 1]) {
                queue.offer(new int[]{cur + 1, cnt});
                visited[cur + 1] = true;
            }
            // 8. 尝试跳跃到相同的位置
            if (map.containsKey(arr[cur])) {
                // 注: 将所有等值的元素入队后就移除子图, 否则就会再次遍历即使不会影响结果, 但是会超时
                for (int idx : map.get(arr[cur])) {
                    if (idx != cur && !visited[idx]) {
                        queue.offer(new int[]{idx, cnt});
                        visited[idx] = true;
                    }
                }
                map.remove(arr[cur]);
            }
        }

        return -1;
    }

    /**
     * <h3>跳跃游戏 V</h3>
     * <h3>递归</h3>
     */
    private static int maxJumps(int distance, int[] arr){
        int cnt = 0;
        int[] dp = new int[arr.length];
        Arrays.fill(dp, -1);
        for(int idx = 0;idx < arr.length;idx++){
            cnt = Math.max(cnt, maxJumps(idx, distance, arr, dp));
        }
        return cnt;
    }

    public static int maxJumps(int idx, int distance, int[] arr, int[] dp){
        if(dp[idx] != -1)
            return dp[idx];
        int cnt = 1;
        for(int offset = 1;offset <= distance;offset++){
            if(idx + offset > arr.length - 1 || arr[idx] <= arr[idx + offset])
                break;
            cnt = Math.max(cnt, maxJumps(idx + offset, distance, arr, dp) + 1);
        }
        for(int offset = 1;offset <= distance;offset++){
            if(idx - offset < 0 || arr[idx] <= arr[idx - offset])
                break;
            cnt = Math.max(cnt, maxJumps(idx - offset, distance, arr, dp) + 1);
        }
        return dp[idx] = cnt;
    }

    /**
     * <h3>跳跃游戏 VI</h3>
     * <h3>动态规划 + 单调队列优化</h3>
     * <h3>记忆化搜索和原生动态规划都会超时</h3>
     */
    public static int maxResult(int offset, int[] nums){
        // 1. 表结构: dp[i] 表示 0...i 的最大数字和
        int[] dp = new int[nums.length];
        Arrays.fill(dp, Integer.MIN_VALUE);
        Deque<Integer> deque = new LinkedList<>();
        // 2. basecase
        dp[0] = nums[0];
        deque.offerLast(0);
        // 3. 填表
        for(int idx = 1;idx < nums.length;idx++){
            // 3.1 移除超过移动范围的最大值
            while(!deque.isEmpty() && idx - offset > deque.peekFirst())
                deque.poll();
            // 3.2 更新当前的最大值
            dp[idx] = dp[deque.peekFirst()] + nums[idx];
            // 3.3 更新队列中的最大值
            while(!deque.isEmpty() && dp[idx] > dp[deque.peekLast()]){
                deque.pollLast();
            }
            deque.offerLast(idx);
        }
        return dp[nums.length - 1];
    }

}
