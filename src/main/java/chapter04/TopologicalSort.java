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
public class TopologicalSort {

    /**
     * <h3>图的每个结点需要保存的内容</h3>
     */
    private static class GraphNode{
        private final int course;
        private int indegree;
        private final List<GraphNode> neighbors;

        public GraphNode(int course) {
            this.course = course;
            this.indegree = 0;
            this.neighbors = new LinkedList<>();
        }
    }

    /**
     * <h3>思路: 课程表</h3>
     * <h3>注: 这个参数在第二题才有用, 第一个题完全可以不用</h3>
     */
    private static boolean canFinish(int numCourses, int[][] prerequisites){
        Map<Integer, GraphNode> graph = getGraph(prerequisites);
        Queue<GraphNode> queue = new LinkedList<>();
        // 1. 获取所有入度为零的结点
        for (GraphNode node : graph.values()) {
            if (node.indegree == 0) queue.offer(node);
        }
        // 2. 开始拓扑排序
        int count = 0;
        while (!queue.isEmpty()){
            GraphNode node = queue.poll();
            // 3. 将所有邻居的入度都减少一, 然后继续判断是否有零度结点
            for (GraphNode next : node.neighbors) {
                if (--next.indegree == 0) queue.offer(next);
            }
            // 4. 学习课程数增加
            count++;
        }
        // 5. 只要学习的课程数和图中的结点数相同, 就证明没有环
        return count == graph.size();
    }

    /**
     * <h3>只有建图的过程是需要思考的, 算法就那么几种, 写熟就行</h3>
     */
    private static Map<Integer, GraphNode> getGraph(int[][] prerequisites){
        Map<Integer, GraphNode> graph = new HashMap<>();
        for (int[] prerequisite : prerequisites) {
            int first = prerequisite[0];
            int second = prerequisite[1];
            GraphNode firstNode = graph.getOrDefault(first, new GraphNode(first));
            GraphNode secondNode = graph.getOrDefault(second, new GraphNode(second));
            firstNode.indegree++;
            secondNode.neighbors.add(firstNode);
            graph.put(first, firstNode);
            graph.put(second, secondNode);
        }
        return graph;
    }

    /**
     * <h3>思路: 课程表 II</h3>
     */
    private static int[] findOrder(int numCourses, int[][] prerequisites){
        Map<Integer, GraphNode> graph = getGraph(prerequisites, new boolean[numCourses]);
        Queue<GraphNode> queue = new LinkedList<>();
        for (GraphNode node : graph.values()) {
            if (node.indegree == 0) queue.offer(node);
        }
        int index = 0;
        int[] path = new int[numCourses];
        while (!queue.isEmpty()){
            GraphNode node = queue.poll();
            for (GraphNode next : node.neighbors) {
                if (--next.indegree == 0)
                    queue.offer(next);
            }
            path[index++] = node.course;
        }
        return index == path.length ? path : new int[]{};
    }

    private static Map<Integer, GraphNode> getGraph(int[][] prerequisites, boolean[] visited){
        Map<Integer, GraphNode> graph = new HashMap<>();
        for (int[] prerequisite : prerequisites) {
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
        for(int index = 0;index < visited.length;index++){
            if(!visited[index])
                graph.put(index, new GraphNode(index));
        }
        return graph;
    }

}
