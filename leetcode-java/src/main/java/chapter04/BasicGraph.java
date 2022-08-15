package chapter04;

import java.util.*;

/**
 * <h2>基础图论题</h2>
 * <h3>1. 省份数量</h3>
 * <h3>2. 所有可能路径</h3>
 * <h3>3. 判断二分图</h3>
 * <h3>4. 钥匙和房间</h3>
 * <h3>5. 克隆图</h3>
 * <h3>6. 除法求值</h3>
 * <h3>注: 可以直接使用深度搜索和广度搜索完成的题目都相对比较简单</h3>
 */
public class BasicGraph {

    /**
     * <h3>思路: 钥匙和房间</h3>
     * <h3>注: 这个题也是采用邻接表形式表示的, 所以也是不需要进行转换的</h3>
     */
    private static boolean canVisitAllRooms1(List<List<Integer>> rooms){
        boolean[] visited = new boolean[rooms.size()];
        return dfs(rooms, visited, 0) == rooms.size();
    }

    private static int dfs(List<List<Integer>> rooms, boolean[] visited, int current){
        visited[current] = true;
        int count = 1;
        for(int neighbors : rooms.get(current)){
            if(!visited[neighbors])
                count += dfs(rooms, visited, neighbors);
        }
        return count;
    }

    public boolean canVisitAllRooms2(List<List<Integer>> rooms) {
        boolean[] visited = new boolean[rooms.size()];
        Queue<Integer> queue = new LinkedList<>();
        // 1. 只有 0 号房间可以进入
        queue.offer(0);
        visited[0] = true;
        // 2. 继续进入其余房间
        int count = 0;
        while(!queue.isEmpty()){
            int index = queue.poll();
            for(int neighbor : rooms.get(index)){
                if(!visited[neighbor]){
                    queue.offer(neighbor);
                    visited[neighbor] = true;
                }
            }
            count++;
        }
        return count == rooms.size();
    }


    /**
     * <h3>思路: 除法求值</h3>
     * <h3>注: 这个题就必须要转换成邻接表或者邻接矩阵来做了, 原本的形式根本不好使用</h3>
     */
    private static double[] calcEquation1(List<List<String>> equations, double[] values, List<List<String>> queries){
        double[] results = new double[queries.size()];
        Map<String, Integer> conversion = convert(equations);
        double[][] graph = getGraph(conversion, equations, values);
        int index = 0;
        for(List<String> query : queries){
            Integer first = conversion.get(query.get(0)), second = conversion.get(query.get(1));
            if(first == null || second == null){
                results[index++] = -1.0;
            }else if(first.intValue() == second.intValue()){
                results[index++] = 1.0;
            }else{
                results[index++] = dfs(graph, new boolean[conversion.size()], first, second);
            }
        }
        return results;
    }


    /**
     * <h3>字符串类型是没有办法在邻接表或者邻接矩阵中使用的, 必须转换成整数才可以进行索引</h3>
     */
    private static Map<String, Integer> convert(List<List<String>> equations){
        int index = 0;
        Map<String, Integer> conversion = new HashMap<>();
        for (List<String> equation : equations) {
            String first = equation.get(0), second = equation.get(1);
            if (!conversion.containsKey(first))
                conversion.put(first, index++);
            if (!conversion.containsKey(second))
                conversion.put(second, index++);
        }
        return conversion;
    }

    private static double[][] getGraph(Map<String, Integer> conversion, List<List<String>> equations, double[] values){
        int index = 0;
        double[][] graph = new double[conversion.size()][conversion.size()];
        for (List<String> equation : equations) {
            int first = conversion.get(equation.get(0)), second = conversion.get(equation.get(1));
            graph[first][second] = values[index];
            graph[second][first] = 1 / values[index];
            index++;
        }
        return graph;
    }

    private static double dfs(double[][] graph, boolean[] visited, int start, int end){
        if (start == end)
            return 1.0d;
        visited[start] = true;

        for (int index = 0;index < graph[start].length;index++){
            if (graph[start][index] == 1 && !visited[index]){
                double result = dfs(graph, visited, index, end);
                if (result != -1)
                    return graph[start][index] * result;
            }
        }

        return -1.0d;
    }
}
