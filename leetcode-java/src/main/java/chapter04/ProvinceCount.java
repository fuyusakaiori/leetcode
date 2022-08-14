package chapter04;

import java.util.LinkedList;

/**
 * <h3>省份数量</h3>
 */
public class ProvinceCount {

    private static int findCircleNum(int[][] isConnected) {
        int count = 0;
        boolean[] visited = new boolean[isConnected.length];
        for(int idx = 0;idx < isConnected.length;idx++){
            if(!visited[idx]){
                count++;
                dfs(idx, visited, isConnected);
                bfs(idx, visited, isConnected);
            }
        }
        return count;
    }

    private static void dfs(int cur, boolean[] visited, int[][] graph){
        if(visited[cur])
            return;
        visited[cur] = true;
        for(int idx = 0;idx < graph[cur].length;idx++){
            if(!visited[idx] && graph[cur][idx] != 0){
                dfs(idx, visited, graph);
            }
        }
    }

    public static void bfs(int cur, boolean[] visited, int[][] graph){
        LinkedList<Integer> queue = new LinkedList<>();
        queue.offer(cur);
        while(!queue.isEmpty()){
            cur = queue.poll();
            visited[cur] = true;
            for(int idx = 0;idx < graph[cur].length;idx++){
                if(!visited[idx] && graph[cur][idx] != 0){
                    queue.offer(idx);
                }
            }
        }
    }


}
