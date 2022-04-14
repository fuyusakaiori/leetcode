package chapter04;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

/**
 * <h2>岛屿问题</h2>
 * <h3>1. 岛屿数量</h3>
 * <h3>2. 岛屿周长</h3>
 * <h3>注: 这两个题应该都是采用 DFS BFS 并查集这三种解法完成</h3>
 */
public class IslandProblem {

    /**
     * <h3>思路: 岛屿数量</h3>
     */
    private static int numIslands(char[][] grid){
        int count = 0;
        for (int row = 0;row < grid.length;row++){
            for (int column = 0;column < grid[row].length;column++){
                if (grid[row][column] == '1'){
                    count++;
                    dfs(grid, row, column);
                    bfs(grid, row, column);
                }
            }
        }
        return count;
    }

    private static void dfs(char[][] grid, int row, int column){
        if (row < 0 || column < 0 || row > grid.length - 1 || column > grid[row].length - 1)
            return;
        if (grid[row][column] == '0')
            return;
        grid[row][column] = '1';
        dfs(grid, row + 1, column);
        dfs(grid, row - 1, column);
        dfs(grid, row, column + 1);
        dfs(grid, row, column - 1);
    }

    private static void bfs(char[][] grid, int row, int column){
        Queue<int[]> queue = new LinkedList<>(Collections.singletonList(new int[]{row, column}));
        while (!queue.isEmpty()){
            int[] location = queue.poll();
            row = location[0];
            column = location[1];
            if (row < 0 || column < 0 || row > grid.length - 1 || column > grid[row].length - 1)
                continue;
            if (grid[row][column] == '0')
                continue;
            grid[row][column] = '0';
            queue.offer(new int[]{row + 1, column});
            queue.offer(new int[]{row - 1, column});
            queue.offer(new int[]{row, column + 1});
            queue.offer(new int[]{row, column - 1});
        }
    }

    /**
     * <h3>思路: 岛屿周长</h3>
     */
    private static int perimeterIsland(char[][] grid){
        for (int row = 0;row < grid.length;row++){
            for (int column = 0;column < grid[row].length;column++){
                if (grid[row][column] == '1'){
                    return dfs(row, column, grid);
                }
            }
        }
        return 0;
    }

    /**
     * <h3>注: 这种解法的好处在于可以扩展到计算多个岛屿的周长</h3>
     */
    private static int dfs(int row, int column, char[][] grid){
        // 1. 如果碰到整个范围的边界, 那么将这个临界边返回
        if (row < 0 || column < 0 || row > grid.length - 1 || column > grid[row].length - 1)
            return 1;
        // 2. 如果是水域相邻, 那么将这个临界边返回
        if (grid[row][column] == '0')
            return 1;
        // 3. 这时代表陆地是个环
        if (grid[row][column] == '2')
            return 0;
        grid[row][column] = '2';
        return dfs(row + 1, column, grid) + dfs(row - 1, column, grid)
                       + dfs(row, column + 1, grid) + dfs(row, column - 1, grid);
    }

    /**
     * <h3>思路: 岛屿面积</h3>
     */
    private static int maxAreaOfIsland(int[][] grid) {
        int max = 0;
        for(int row = 0;row < grid.length;row++){
            for(int column = 0;column < grid[row].length;column++){
                if(grid[row][column] == 1){
                    max = Math.max(max, dfs(grid, row, column));
                }
            }
        }
        return max;
    }

    private static int dfs(int[][] grid, int row, int column){
        if(row < 0 || column < 0 || row > grid.length - 1 || column > grid[row].length - 1)
            return 0;
        if(grid[row][column] == 0)
            return 0;
        grid[row][column] = 0;

        return 1 + dfs(grid, row + 1, column) + dfs(grid, row - 1, column)
                       + dfs(grid, row, column + 1) + dfs(grid, row, column - 1);
    }
}
