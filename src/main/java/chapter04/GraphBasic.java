package chapter04;

import java.util.*;

/**
 * <h2>图论基础</h2>
 * <h3>1. 邻接矩阵: 深度搜索和广度搜索</h3>
 * <h3>2. 邻接链表: 深度搜索和广度搜索</h3>
 * <h3>注: 无论是什么样的题, 对于图论来说都需要掌握深度搜索和广度搜索, 然后才是进阶算法</h3>
 * <h3>注: 表示的图的方式有非常多种, 这里先将最常用的两种熟悉, 然后才是各种变种和套路</h3>
 * <h3>注: 这道题基于 LeetCode 547, 作为入门题目非常好使</h3>
 */
public class GraphBasic {

    /**
     * <h3>思路: 省份数量</h3>
     * <h3>注: 说白了就是寻找连通图的数量</h3>
     */
    private static int findCircleNum(int[][] isConnected){
        int count = 0;
        // 1. 无论采用广度还是深度优先都需要这个数组记录访问过的结点, 避免重复访问, 并查集除外
        boolean[] visited = new boolean[isConnected.length];
        // 2. 对每个结点开始深度或者广度优先搜索, 其实和树没有太大的区别
        for (int index = 0;index < isConnected.length;index++){
            // 3. 只要这个结点没有被访问过, 那么我们就可以访问它的邻居
            if (!visited[index]){
                dfs(isConnected, visited, index);
                bfs(isConnected, visited, index);
                count++;
            }
        }

        return count;
    }

    /**
     * <h3>思路: 深度优先搜索</h3>
     * <h3>注: 左神提供过迭代版的深度优先遍历, 最好注意下</h3>
     */
    private static void dfs(int[][] isConnected, boolean[] visited, int start){
        // 1. 将当前结点置为已访问
        visited[start] = true;
        // 2. 依次遍历邻居
        for (int index = 0;index < isConnected.length;index++){
            if (!visited[index] && isConnected[start][index] == 1){
                dfs(isConnected, visited, index);
            }
        }
    }

    /**
     * <h3>思路: 广度优先搜索</h3>
     */
    private static void bfs(int[][] isConnected, boolean[] visited, int start){
        Queue<Integer> queue = new LinkedList<>();
        visited[start] = true;
        queue.offer(start);
        while (!queue.isEmpty()){
            start = queue.poll();
            for (int index = 0;index < isConnected.length;index++){
                if (!visited[index] && isConnected[start][index] == 1){
                    queue.offer(index);
                    visited[index] = true;
                }
            }
        }
    }

    /**
     * <h3>核心模式下通常只会以邻接矩阵的形式给出图, 但是有时候这种形式可能会超时</h3>
     * <h3>所以可以尝试将邻接矩阵转换成邻接表的形式来完成, 这种转换图的格式的方法要学会</h3>
     */
    private static Map<Integer, List<Integer>> getGraph(int[][] isConnected){
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (int row = 0;row < isConnected.length;row++){
            List<Integer> next = new ArrayList<>();
            for (int column = 0;column < isConnected[row].length;column++){
                if (row != column && isConnected[row][column] == 1)
                    next.add(column);
            }
            graph.put(row, next);
        }
        return graph;
    }

    private static int findCircleNum(Map<Integer, List<Integer>> graph){
        int count = 0;
        boolean[] visited = new boolean[graph.size()];
        for (int start : graph.keySet()) {
            if (!visited[start]){
                dfs(graph, visited, start);
                count++;
            }
        }
        return count;
    }

    private static void dfs(Map<Integer, List<Integer>> graph, boolean[] visited, int start){
        visited[start] = true;
        List<Integer> neighbors = graph.get(start);
        for (int index = 0;index < neighbors.size();index++){
            int next = neighbors.get(index);
            if (!visited[next]) dfs(graph, visited, next);
        }
    }

    private static void bfs(Map<Integer, List<Integer>> graph, boolean[] visited, int start){
        visited[start] = true;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);

        while (!queue.isEmpty()){
            start = queue.poll();
            for (int next : graph.get(start)) {
                if (!visited[next]){
                    visited[next] = true;
                    queue.offer(next);
                }
            }
        }
    }

}
