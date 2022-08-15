package chapter04;

import java.util.*;

/**
 * <h3>钥匙和房间</h3>
 */
public class KeyAndRoom {

    private static int cnt = 0;

    public static boolean canVisitAllRooms1(List<List<Integer>> rooms) {
        dfs(0, new boolean[rooms.size()], rooms);
        return cnt == rooms.size();
    }

    public static void dfs(int cur, boolean[] visited, List<List<Integer>> rooms){
        if(visited[cur])
            return;
        cnt++;
        visited[cur] = true;
        for(int key : rooms.get(cur)){
            dfs(key, visited, rooms);
        }
    }

    public static boolean canVisitAllRooms2(List<List<Integer>> rooms){
        int cnt = 0;
        boolean[] visited = new boolean[rooms.size()];
        Queue<Integer> queue = new LinkedList<>(
                Collections.singletonList(0));
        visited[0] = true;
        while (!queue.isEmpty()){
            int idx = queue.poll();
            cnt++;
            for (int key : rooms.get(idx)) {
                if (!visited[key]){
                    visited[key] = true;
                    queue.offer(key);
                }
            }
        }
        return cnt == rooms.size();
    }



}
