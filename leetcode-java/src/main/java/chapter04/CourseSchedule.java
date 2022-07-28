package chapter04;

import java.util.*;

/**
 * <h2>图论算法: 拓扑排序</h2>
 * <h3>主要用于判断图中是否存在环</h3>
 * <h3>1. 课程表</h3>
 * <h3>2. 课程表 II</h3>
 * <h3>注: 从这个题基本就能够看出来在没有模板的情况下, 建图的过程可能会非常繁琐, 笔试时间肯定不够</h3>
 * <h3>注: 图的题目基本就不要太追求效率, 能解出来就行</h3>
 */
public class CourseSchedule {

    /**
     * <h3>思路: 课程表 </h3>
     * <h3>1. 邻接矩阵: 适用于稠密图, 所以这里效率比较低</h3>
     * <h3>2. 邻接表: 适用于稀疏图, 这里效率比较高</h3>
     * <h3>3. 模板实现和邻接表效率较低, 但是好写</h3>
     * <h3>注: 只是存储图的方式不同, 具体的算法是一致的</h3>
     */
    private static boolean findOrder1(int[][] requires, int numCourses){
        Object[] params = getGraph(requires, numCourses);
        int[] indegree = (int[]) params[0];
        int[][] graph = (int[][]) params[1];
        return dfs(graph) || bfs(indegree, graph);
    }

    /**
     * <h3>邻接矩阵存储图</h3>
     */
    private static Object[] getGraph(int[][] requires, int numCourse){
        int[] indegree = new int[numCourse];
        int[][] graph = new int[numCourse][numCourse];
        for (int[] require : requires){
            graph[require[1]][require[0]] = 1;
            indegree[require[0]]++;
        }
        return new Object[]{indegree, graph};
    }

    private static boolean bfs(int[] indegree, int[][] graph){
        Queue<Integer> queue = new LinkedList<>();

        for (int index = 0;index < indegree.length;index++){
            if (indegree[index] == 0)
                queue.offer(index);
        }
        int visited = 0;
        while (!queue.isEmpty()){
            int start = queue.poll();
            for (int index = 0;index < graph[start].length;index++){
                if (graph[start][index] == 1){
                    if (--indegree[index] == 0)
                        queue.offer(index);
                }
            }
            visited++;
        }
        return visited == indegree.length;
    }

    private static boolean dfs(int[][] graph){
        int[] visited = new int[graph.length];
        for(int index = 0;index < graph.length;index++){
            if(!dfs(graph, visited, index))
                return false;
        }
        return true;
    }

    private static boolean dfs(int[][] graph, int[] visited, int start){
        if(visited[start] == 1)
            return true;
        if(visited[start] == -1)
            return false;
        visited[start] = -1;
        for(int index = 0;index < graph[start].length;index++){
            if(graph[start][index] == 1){
                if(!dfs(graph, visited, index))
                    return false;
            }
        }
        visited[start] = 1;
        return true;
    }


    private static class GraphNode {
        // 1. 结点的值
        private final int course;
        // 2. 结点的入度: 只有广度遍历会使用, 深度遍历是不会使用的
        private int indegree;
        // 3. 相邻结点
        private final List<GraphNode> neighbors;

        public GraphNode(int course){
            this.course = course;
            this.neighbors = new LinkedList<>();
        }
    }

    /**
     * <h3>模板存储图</h3>
     */
    private static Map<Integer, GraphNode> getGraph(int[][] requires){
        Map<Integer, GraphNode> graph = new HashMap<>();
        for (int[] require : requires) {
            GraphNode prevNode = graph.getOrDefault(require[1], new GraphNode(require[1]));
            GraphNode nextNode = graph.getOrDefault(require[0], new GraphNode(require[0]));
            prevNode.neighbors.add(nextNode);
            nextNode.indegree++;
            graph.put(require[1], prevNode);
            graph.put(require[0], nextNode);
        }
        return graph;
    }

    /**
     * <h3>深度优先搜索</h3>
     * <h3>注: 深度优先不需要使用入度</h3>
     */
    private static boolean dfs(int[][] requires, int numCourses){
        int[] visited = new int[numCourses];
        Map<Integer, GraphNode> graph = getGraph(requires);

        for (int course : graph.keySet()) {
            if (!dfs(graph, visited, course))
                return false;
        }
        return true;
    }

    private static boolean dfs(Map<Integer, GraphNode> graph, int[] visited, int course){
        // 1. 表示这和结点已经被其他结点深度搜索过, 不需要重复搜索
        if (visited[course] == 1)
            return true;
        // 2. 表示这个结点被重复搜索了, 也就是出现环了
        if (visited[course] == -1)
            return false;
        // 3. 为了防止在单次搜索中出现环, 需要将这个结点暂时置为 1
        visited[course] = 1;
        for (GraphNode neighbor : graph.get(course).neighbors){
            if(!dfs(graph, visited, neighbor.course))
                return false;
        }
        // 4. 在单个结点深度搜索完成之后, 发现没有环就可以结束了
        visited[course] = -1;
        return true;
    }

    /**
     * <h3>广度优先搜索</h3>
     */
    private static boolean bfs(int[][] requires){
        Queue<GraphNode> queue = new LinkedList<>();
        Map<Integer, GraphNode> graph = getGraph(requires);
        // 1. 获取所有入度为零的结点
        for (GraphNode node : graph.values()) {
            if (node.indegree == 0)
                queue.offer(node);
        }
        // 2. 开始遍历图
        int count = 0;
        while (!queue.isEmpty()){
            GraphNode node = queue.poll();
            for (GraphNode neighbor : node.neighbors) {
                if (--neighbor.indegree == 0)
                    queue.offer(neighbor);
            }
            // 3. 学习的课程数量增加
            count++;
        }
        return count == graph.size();
    }


    /**
     * <h3>思路: 课程表 II</h3>
     */
    private static int[] findOrder(int numCourses, int[][] prerequisites) {
        Map<Integer, GraphNode> graph = getGraph(prerequisites, new boolean[numCourses]);
        Queue<GraphNode> queue = new LinkedList<>();
        for (GraphNode node : graph.values())
        {
            if (node.indegree == 0) queue.offer(node);
        }
        int index = 0;
        int[] path = new int[numCourses];
        while (!queue.isEmpty())
        {
            GraphNode node = queue.poll();
            for (GraphNode next : node.neighbors)
            {
                if (--next.indegree == 0)
                    queue.offer(next);
            }
            path[index++] = node.course;
        }
        return index == path.length ? path : new int[]{};
    }

    private static Map<Integer, GraphNode> getGraph(int[][] prerequisites, boolean[] visited)
    {
        Map<Integer, GraphNode> graph = new HashMap<>();
        for (int[] prerequisite : prerequisites)
        {
            int first = prerequisite[0];
            int second = prerequisite[1];
            visited[first] = visited[second] = true;
            GraphNode firstNode = graph.getOrDefault(first, new GraphNode(first));
            GraphNode secondNode = graph.getOrDefault(second, new GraphNode(second));
            firstNode.indegree++;
            secondNode.neighbors.add(firstNode);
            graph.put(first, firstNode);
            graph.put(second, secondNode);
        }
        for (int index = 0; index < visited.length; index++)
        {
            if (!visited[index])
                graph.put(index, new GraphNode(index));
        }
        return graph;
    }

}
