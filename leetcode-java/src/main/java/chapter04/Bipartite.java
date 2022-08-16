package chapter04;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

/**
 * <h3>判断二分图</h3>
 */
public class Bipartite {

    /**
     * <h3>深度优先搜索: 染色法</h3>
     */
    private static boolean isBipartite1(int[][] graph){
        int[] visited = new int[graph.length];
        for (int idx = 0; idx < graph.length; idx++) {
            if (visited[idx] == 0 && !dfs(idx, 1, visited, graph))
                return false;
        }
        return true;
    }

    private static boolean dfs(int idx, int color, int[] visited, int[][] graph){
        // 1. 如果不为零, 那么就证明结点已经被访问过, 检查颜色是否相同
        if (visited[idx] != 0)
            // 2. 如果两者颜色不同, 那么就可以继续分, 如果颜色相同, 那么就无法二分了: color 表示的不是上个结点的颜色, 是将要涂的颜色
            return visited[idx] != -color;
        visited[idx] = color;
        for (int next : graph[idx]) {
            if (!dfs(next, -color, visited, graph))
                return false;
        }
        return true;
    }

    /**
     * <h3>广度优先搜索</h3>
     */
    private static boolean isBipartite2(int[][] graph){
        int[] visited = new int[graph.length];
        for (int idx = 0; idx < graph.length; idx++) {
            if (visited[idx] == 0){
                visited[idx] = 1;
                if (!bfs(idx, visited, graph))
                    return false;
            }
        }
        return true;
    }

    private static boolean bfs(int cur, int[] visited, int[][] graph){
        // 1. 初始化变量
        Queue<Integer> queue = new LinkedList<>(Collections.singletonList(cur));
        // 2. 遍历图
        while (!queue.isEmpty()){
            int idx = queue.poll();
            for (int next : graph[idx]) {
                if (visited[next] == visited[idx])
                    return false;
                if (visited[next] == 0){
                    queue.offer(next);
                    visited[next] = -visited[idx];
                }
            }
        }
        return true;
    }


    /**
     * <h3>并查集</h3>
     */
    private static boolean isBipartite3(int[][] graph){
        int[] unionSet = getUnionSet(graph.length);
        for (int start = 0; start < graph.length; start++) {
            for (int cur = 0; cur < graph[start].length; cur++) {
                // 1. 检查相邻结点和当前结点的父节点是否相同
                if (find(unionSet, start) == find(unionSet, graph[start][cur]))
                    return false;
                // 2. 如果不同, 那么就合并所有的相邻结点: 起始节点和相邻结点的颜色肯定不同
                union(unionSet, graph[start][0], graph[start][cur]);
            }
        }
        return true;
    }

    private static int[] getUnionSet(int length){
        int[] unionSet = new int[length];
        for (int idx = 0; idx < unionSet.length; idx++) {
            unionSet[idx] = idx;
        }
        return unionSet;
    }

    private static int find(int[] unionSet, int idx){
        if (unionSet[idx] == idx)
            return idx;
        return unionSet[idx] = find(unionSet, unionSet[idx]);
    }

    private static void union(int[] unionSet, int first, int second){
        unionSet[find(unionSet, second)] = unionSet[find(unionSet, first)];
    }



}
