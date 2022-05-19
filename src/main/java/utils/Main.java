package utils;


import java.util.*;

public class Main{

    public static void main(String[] args) {

        orangesRotting(new int[][]{
                {2, 1, 1},
                {1, 1, 0},
                {0, 1, 1}
        });
    }

    public static int orangesRotting(int[][] grid) {
        int minutes = 0;
        int rowLength = grid.length, colLength = grid[0].length;
        Queue<int[]> queue = new LinkedList<>();

        // 1. 初始化
        int[] nextEnd = new int[2];
        for(int row = 0;row < rowLength;row++){
            for(int col = 0;col < colLength;col++){
                if(grid[row][col] == 2){
                    nextEnd = new int[]{row, col};
                    queue.offer(nextEnd);
                }
            }
        }
        // 2. 开始遍历
        int[] curEnd = nextEnd;
        while(!queue.isEmpty()){
            int[] cur = queue.poll();
            int x = cur[0], y = cur[1];
            if(x + 1 < rowLength && grid[x + 1][y] == 1){
                grid[x + 1][y] = 2;
                nextEnd = new int[]{x + 1, y};
                queue.offer(nextEnd);
            }
            if(x - 1 >= 0 && grid[x - 1][y] == 1){
                grid[x - 1][y] = 2;
                nextEnd = new int[]{x - 1, y};
                queue.offer(nextEnd);
            }
            if(y + 1 < colLength && grid[x][y + 1] == 1){
                grid[x][y + 1] = 2;
                nextEnd = new int[]{x, y + 1};
                queue.offer(nextEnd);
            }
            if(y - 1 >= 0 && grid[x][y - 1] == 1){
                grid[x][y - 1] = 2;
                nextEnd = new int[]{x, y - 1};
                queue.offer(nextEnd);
            }
            if(x == curEnd[0] && y == curEnd[1]){
                curEnd = nextEnd;
                minutes++;
            }
        }
        return minutes == 0 ? -1 : minutes;
    }
}

