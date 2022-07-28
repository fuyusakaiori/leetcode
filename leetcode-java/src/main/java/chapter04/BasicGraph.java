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
     * <h3>思路: 省份数量</h3>
     * <h3>本质: 这个题本质就是找图中有多少个连通图</h3>
     * <h3>注: 这个题给的就是邻接矩阵, 显然不需要再转换格式</h3>
     */
    private static int findCircleNum1(int[][] isConnected){
        int count = 0;
        boolean[] visited = new boolean[isConnected.length];
        for (int index = 0;index < isConnected.length;index++){
            if (!visited[index]){
                dfs(isConnected, visited, index);
                count++;
            }
        }
        return count;
    }

    private static void dfs(int[][] isConnected, boolean[] visited, int current){
        if (visited[current])
            return;
        visited[current] = true;
        for (int index = 0;index < isConnected[current].length;index++){
            if (isConnected[current][index] == 1)
                dfs(isConnected, visited, current);
        }
    }


    private static int findCircleNum2(int[][] isConnected){
        int count = 0;
        boolean[] visited = new boolean[isConnected.length];
        for (int index = 0;index < isConnected.length;index++){
            if (!visited[index]){
                bfs(isConnected, visited, index);
                count++;
            }
        }
        return count;
    }

    private static void bfs(int[][] isConnected, boolean[] visited, int current){
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(current);
        visited[current] = true;
        while (!queue.isEmpty()){
            current = queue.poll();
            for (int index = 0;index < isConnected[current].length;index++){
                if (isConnected[current][index] == 1 && !visited[index]){
                    queue.offer(index);
                    visited[index] = true;
                }
            }
        }
    }

    /**
     * <h3>思路: 所有可能路径</h3>
     * <h3>注: 这个题给的是邻接表, 也是不需要转换格式的</h3>
     * <h3>注: 这个题是有向图且无环, 所以是不可能重复走到同一个结点的</h3>
     */
    private static List<List<Integer>> allPathSourceTarget1(int[][] graph){
        List<List<Integer>> paths = new LinkedList<>();
        dfs(graph, new LinkedList<>(), paths, 0);
        return paths;
    }

    private static void dfs(int[][] graph, List<Integer> path, List<List<Integer>> paths, int current){
        // 0. 当前结点进入路径
        path.add(current);
        // 1. 如果走到中点就直接返回
        if (current == graph.length - 1)
            paths.add(new LinkedList<>(path));
        // 2. 从每个相邻结点深度遍历
        for (int index = 0;index < graph[current].length;index++){
            dfs(graph, path, paths, graph[current][index]);
        }
        // 3. 回溯前记得将这个结点从路径中移除
        path.remove(path.size() - 1);
    }


    private static List<List<Integer>> allPathSourceTarget2(int[][] graph){
        List<List<Integer>> paths = new LinkedList<>();
        bfs(graph, paths);
        return paths;
    }

    /**
     * <h3>注: 这个题和二叉树中某个题比较类似, 需要使用两个队列完成</h3>
     */
    private static void bfs(int[][] graph, List<List<Integer>> paths){
        // 1. 第一个队列用来记录遍历的结点
        Queue<Integer> queue = new LinkedList<>();
        // 2. 第二个队列记录当前路径
        LinkedList<List<Integer>> pathQueue = new LinkedList<>();
        // 3. 把起始结点放入两个队列中
        queue.offer(0);
        pathQueue.add(Collections.singletonList(0));
        while (!queue.isEmpty()){
            int current = queue.poll();
            // 4. 获取路径
            List<Integer> path = pathQueue.poll();
            for (int index = 0;index < graph[current].length;index++){
                List<Integer> newPath = new LinkedList<>(Objects.requireNonNull(path));
                // 5. 将当前结点添加进路径中
                newPath.add(graph[current][index]);
                // 6. 如果这个结点已经是终点, 那么就没有必要再进入队列中, 直接将路径添加到结果集中就行
                if (graph[current][index] == graph.length - 1){
                    paths.add(newPath);
                }else{
                    pathQueue.offer(newPath);
                    // 7. 如果不是终点, 要记得把结点继续放入队列中
                    queue.offer(graph[current][index]);
                }
            }
        }
    }


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
     * <h3>思路: 克隆图</h3>
     */
    private static class GraphNode{
        private final int value;
        private final List<GraphNode> neighbors;

        public GraphNode(int value) {
            this.value = value;
            this.neighbors = new LinkedList<>();
        }
    }

    private static GraphNode cloneGraph1(GraphNode node){
        return dfs(node, new HashMap<>());
    }
    private static GraphNode dfs(GraphNode node, Map<Integer, GraphNode> visited){
        if (visited.containsKey(node.value))
            return visited.get(node.value);
        GraphNode clone = new GraphNode(node.value);
        visited.put(clone.value, clone);
        for (GraphNode neighbor : node.neighbors) {
            clone.neighbors.add(dfs(neighbor, visited));
        }
        return clone;
    }

    private static GraphNode cloneGraph2(GraphNode node){
        if (node == null)
            return null;
        Queue<GraphNode> queue = new LinkedList<>();
        Map<Integer, GraphNode> visited = new HashMap<>();
        queue.offer(node);
        visited.put(node.value, new GraphNode(node.value));
        while (!queue.isEmpty()){
            GraphNode current = queue.poll(), clone = visited.get(current.value);
            for (GraphNode neighbor : current.neighbors) {
                if (!visited.containsKey(neighbor.value)){
                    visited.put(neighbor.value, new GraphNode(neighbor.value));
                    queue.offer(neighbor);
                }
                clone.neighbors.add(visited.get(neighbor.value));
            }
        }
        return visited.get(node.value);
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
