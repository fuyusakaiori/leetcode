package chapter04;

import java.util.*;

/**
 * <h2>图论算法: 拓扑排序</h2>
 * <h3>1. 课程表</h3>
 * <h3>2. 课程表 II</h3>
 * <h3>注: 有向图如果可以重复走到结点, 那么必然存在环</h3>
 */
public class CourseSchedule {

    //============================================== 邻接矩阵 ==============================================

    /**
     * <h3>拓扑排序 / 广度优先遍历</h3>
     */
    private static boolean canFinish1(int numCourses, int[][] prerequisites){
        // 1. 初始化邻接矩阵
        int[] indegrees = new int[numCourses];
        int[][] graph = new int[numCourses][numCourses];
        for (int[] prerequisite : prerequisites) {
            // 1.1 获取起点和终点
            int to = prerequisite[0], from = prerequisite[1];
            // 1.2 初始化路径
            graph[from][to] = 1;
            // 1.3 初始化入度
            indegrees[to]++;
        }
        // 2. 获取所有入度为零的结点
        Queue<Integer> zeroQueue = new LinkedList<>();
        for(int idx = 0;idx < indegrees.length;idx++){
            if (indegrees[idx] == 0)
                zeroQueue.offer(idx);
        }
        // 3. 遍历图: 有向图除非有环, 否则不可能重复走到同一个结点
        int visited = 0;
        while (!zeroQueue.isEmpty()){
            int idx = zeroQueue.poll();
            for (int next = 0;next < graph[idx].length;next++){
                if (graph[idx][next] != 0){
                    if (--indegrees[next] == 0){
                        zeroQueue.offer(next);
                    }
                }
            }
            visited++;
        }
        // 4. 检测是否存在环
        return visited == numCourses;
    }

    /**
     * <h3>深度优先遍历</h3>
     */
    private static boolean canFinish2(int numCourses,  int[][] prerequisites){
        // 1. 初始化图
        int[][] graph = new int[numCourses][numCourses];
        for (int[] prerequisite : prerequisites) {
            int to = prerequisite[0], from = prerequisite[1];
            graph[from][to] = 1;
        }
        // 2. 深度遍历: 只要不走到重复的, 那么就是没有环
        int[] visited = new int[numCourses];
        for (int idx = 0;idx < graph.length;idx++){
            if (!dfs(idx, visited, graph))
                return false;
        }
        return true;
    }

    private static boolean dfs(int start, int[] visited, int[][] graph){
        // 1. 如果是在访问值是 1 的期间再次走到, 那么就是其他路径造成的, 不是同一条路径造成的环
        if (visited[start] == 1)
            return true;
        // 2. 如果在访问值是 -1 的期间再次走到, 那么就认为造成环
        if (visited[start] == -1)
            return false;
        // 3. 标记结点第一次访问
        visited[start] = -1;
        for (int idx = 0;idx < graph.length;idx++){
            if (graph[start][idx] != 0){
                if (!dfs(idx, visited, graph))
                    return false;
            }
        }
        // 4. 标记结点访问结束
        visited[start] = 1;
        return true;
    }


    //============================================== 链式前向星 (邻接表) ==============================================

    private static int cnt = 0;
    /**
     * <h3>边的权重集合, 边的终点</h3>
     */
    private static int[] indegrees = new int[100010], tos = new int[5010];

    /**
     * <h3>边的终点, 边链表的下一条边</h3>
     */
    private static int[] heads = new int[100010], nexts = new int[5010];

    private static void addEdge(int idx, int to){
        tos[cnt] = to;
        nexts[cnt] = heads[idx];
        heads[idx] = cnt++;
    }

    private static boolean canFinish3(int numCourses,  int[][] prerequisites){
        // 1. 初始化头结点
        Arrays.fill(heads, -1);
        // 2. 初始化图
        for (int[] prerequisite : prerequisites) {
            int from = prerequisite[1], to = prerequisite[0];
            indegrees[to]++;
            addEdge(from, to);
        }
        // 3. 获取所有度为零的结点
        Queue<Integer> queue = new LinkedList<>();
        for (int idx = 0; idx < numCourses; idx++) {
            if (indegrees[idx] == 0)
                queue.offer(idx);
        }
        // 4. 出队
        int visited = 0;
        while(!queue.isEmpty()){
            int idx = queue.poll();
            for (int next = heads[idx];next != -1;next = nexts[next]){
                int to = tos[next];
                if (--indegrees[to] == 0){
                    queue.offer(to);
                }
            }
            visited++;
        }

        return visited == numCourses;
    }

    private static boolean canFinish4(int numCourses, int[][] prerequisites){
        // 1. 初始化头结点
        Arrays.fill(heads, -1);
        // 2. 初始化邻接矩阵
        for (int[] prerequisite : prerequisites) {
            addEdge(prerequisite[1], prerequisite[0]);
        }
        // 3. 深度遍历
        int[] visited = new int[numCourses];
        for (int idx = 0; idx < numCourses; idx++) {
            if (!dfs(idx, visited))
                return false;
        }
        return true;
    }

    private static boolean dfs(int start, int[] visited){
        if (visited[start] == 1)
            return true;
        if (visited[start] == -1)
            return false;
        visited[start] = -1;
        for (int idx = heads[start];idx != -1;idx = nexts[idx]){
            if (!dfs(tos[idx], visited))
                return false;
        }
        visited[start] = 1;
        return true;
    }


}
