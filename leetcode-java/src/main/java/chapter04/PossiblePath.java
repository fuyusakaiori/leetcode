package chapter04;

import java.util.*;

/**
 * <h3>所有可能路径</h3>
 */
public class PossiblePath {

    /**
     * <h3>深度优先遍历</h3>
     */
    private static List<List<Integer>> allPathsSourceTarget1(int[][] graph){
        List<Integer> path = new LinkedList<>();
        List<List<Integer>> paths = new LinkedList<>();
        dfs(0, graph, path, paths);
        return paths;
    }

    private static void dfs(int cur, int[][] graph, List<Integer> path, List<List<Integer>> paths){
        path.add(cur);
        if (cur == graph.length - 1)
            paths.add(new LinkedList<>(path));
        for (int idx = 0; idx < graph[cur].length; idx++) {
            dfs(graph[cur][idx], graph, path, paths);
        }
        path.remove(path.size() - 1);
    }

    /**
     * <h3>广度优先遍历: 类似于二叉树中的某个题目</h3>
     */
    private static List<List<Integer>> allPathsSourceTarget2(int[][] graph){
        // 0. 返回路径
        List<List<Integer>> paths = new LinkedList<>();
        // 1. 遍历图的队列
        Queue<Integer> nodeQueue = new LinkedList<>();
        // 2. 记录路径的队列
        LinkedList<List<Integer>> pathQueue = new LinkedList<>();
        // 3. 初始化
        if (graph.length != 0){
            nodeQueue.offer(0);
            pathQueue.add(Collections.singletonList(0));
        }
        // 4. 遍历图
        while(!nodeQueue.isEmpty()){
            // 4.1 获取遍历的结点
            int idx = nodeQueue.poll();
            // 4.2 获取结点对应的路径
            List<Integer> path = pathQueue.poll();
            // 4.3 广度遍历
            for (int next : graph[idx]) {
                // 4.4 结点加入路径
                LinkedList<Integer> newPath = new LinkedList<>(Objects.requireNonNull(path));
                newPath.add(next);
                // 4.5 结点路径入队
                if (next == graph.length - 1){
                    paths.add(newPath);
                }else{
                    nodeQueue.offer(next);
                    pathQueue.add(newPath);
                }
            }
        }
        return paths;
    }

}
