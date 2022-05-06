package chapter04;

import java.util.Arrays;

/**
 * <h2>网络延迟时间</h2>
 * <h3>注: 考点在于迪杰斯特拉算法</h3>
 */
public class NetworkDelayTime {

    /**
     * <h3>思路: 邻接矩阵法</h3>
     * <h3>1. 获取单源到各个结点最短路径之后需要求最大值</h3>
     * <h3>2. 获取最大值所在路径的话还需要维护最近结点的数组</h3>
     */
    private static int networkDelayTime(int[][] times, int count, int start){
        final int INF = Integer.MAX_VALUE / 2;
        // 1. 构建图
        int[][] graph = getGraph(times, count);
        // 2. 最短路径数组
        int[] minCost = new int[count];
        // 3. 标记已经访问的结点
        boolean[] visited = new boolean[count];
        // 注: 初始化最短路径数组
        Arrays.fill(minCost, INF);
        minCost[start - 1] = 0;
        // 4. 开始执行迪杰斯特拉算法
        for (int index = 0;index < minCost.length;index++){
            // 5. 寻找距离最短且没有访问过的结点
            int minIndex = -1;
            for (int first = 0;first < minCost.length;first++){
                if (!visited[first] && (minIndex == -1 || minCost[first] < minCost[minIndex]))
                    minIndex = first;
            }
            // 6. 标记当前结点已经访问过
            visited[minIndex] = true;
            // 7. 更新最短距离数组
            for (int second = 0;second < minCost.length;second++){
                // 注: 已经锁定不再的更新结点肯定不能继续更新了
                if (!visited[second])
                    // 注: 将最短距离结点到这个结点的距离 + 上一个结点到自己的最短距离, 和上一个结点到这个结点的最短距离比较
                    minCost[second] = Math.min(
                            minCost[second], graph[minIndex][second] + minCost[minIndex]);
            }
        }
        // 8. 选择距离最大的作为返回值, 如果是不可到达的距离就返回 -1
        int maxCost = 0;
        for (int cost : minCost) {
            maxCost = Math.max(cost, maxCost);
        }
        return maxCost == INF ? -1: maxCost;
    }

    private static int[][] getGraph(int[][] times, int count){
        int[][] graph = new int[count][count];
        // 1. 图初始化, 默认所有结点不可达: 这里不要直接使用最大值, 因为和最大值相加会直接溢出
        for (int[] edges : graph) {
            Arrays.fill(edges, Integer.MAX_VALUE / 2);
        }
        // 2. 开始填充图
        for (int[] time : times){
            graph[time[0] - 1][time[1] - 1] = time[2];
        }
        return graph;
    }

}
