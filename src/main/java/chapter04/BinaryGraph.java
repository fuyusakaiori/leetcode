package chapter04;

import java.util.LinkedList;
import java.util.Queue;

/**
 * <h2>二分图</h2>
 */
public class BinaryGraph {

    /**
     * <h3>二分图</h3>
     * <h3>注: 这个题默认就是采用邻接表来表示的, 没有必要转换成邻接矩阵表示</h3>
     */
    private static boolean isBipartite1(int[][] graph){
        // 1. 标记结点: 1 标记第一个集合, -1 标记第二个集合
        int[] visited = new int[graph.length];
        // 2. 直接使用邻接表: 这里循环的目的是为了遍历所有的连通图
        for (int index = 0;index < graph.length;index++){
            // 3. 如果已经被染色的结点就不要访问了
            if (visited[index] == 0 && dfs(graph, visited, 1, index))
                return false;
        }
        return true;
    }

    private static boolean dfs(int[][] graph, int[] visited, int color, int current){
        // 1. 如果当前这个结点已经被访问过, 那么这个结点即将要上的色和已经上的色是否相同
        if (visited[current] != 0)
            // 2. 如果颜色相同那么证明没有办法二分, 如果颜色不同那么就继续搜索其他结点
            return color != visited[current];
        // 3. 染色
        visited[current] = color;
        // 4. 搜索其他结点
        for (int neighbor : graph[current]) {
            if(dfs(graph, visited, -color, neighbor))
                return true;
        }
        return false;
    }

    private static boolean isBipartite2(int[][] graph){
        int[] visited = new int[graph.length];
        for (int index = 0;index < graph.length;index++){
            if (visited[index] == 0 && !bfs(graph, visited, index))
                return false;
        }
        return true;
    }

    private static boolean bfs(int[][] graph, int[] visited, int current){
        Queue<Integer> queue = new LinkedList<>();
        // 1. 提前处理首结点: 和树的遍历方式没有区别
        queue.offer(current);
        visited[current] = 1;
        // 2. 开始遍历剩下的结点
        while (!queue.isEmpty()){
            current = queue.poll();
            // 注: 其实也可以在这里处理, 不过根据情况而定, 这里还是在循环中处理会更好
            for (int neighbor : graph[current]) {
                // 3. 如果颜色相同直接返回
                if (visited[neighbor] == visited[current])
                    return false;
                // 4. 如果不等就需要入队, 但是如果已经上色就不需要入队了, 重复入队肯定有问题的
                if (visited[neighbor] == 0){
                    queue.offer(neighbor);
                    visited[neighbor] = -visited[current];
                }
            }
        }
        return true;
    }

    private static boolean isBipartite3(int[][] graph){
        return true;
    }


}
