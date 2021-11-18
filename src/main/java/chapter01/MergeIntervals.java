package chapter01;

import java.util.*;

public class MergeIntervals
{
    public static void main(String[] args)
    {
        int[][] intervals = {
                {2,3}, {4,5},{6,7},{8,9},{1, 10}
        };

        sort(intervals);
        System.out.println(Arrays.deepToString(merge2(intervals)));
    }

    //问题: 能不能想办法把时间复杂度降低, 而不是 O(n^2)
    public static int[][] merge1(int[][] intervals){

        return null;
    }

    // 优化排序算法:
    public static void quicksort(){

    }


    // 合并: 这个方法并不是很好...
    public static int[][] merge2(int[][] intervals) {
        int length = intervals.length;
        // 标记数组
        boolean[] flags = new boolean[length];
        // 返回数组
        List<int[]> arrays = new ArrayList<>();

        // 先排序吧


        for(int i = 0;i < length;i++){
            if(flags[i])
                continue;

            for(int j = i + 1;j < length;j++){
                if(!(intervals[i][1] < intervals[j][0] ||
                             intervals[i][0] > intervals[j][1])) {
                    intervals[i][0] = Math.min(intervals[i][0], intervals[j][0]);
                    intervals[i][1] = Math.max(intervals[i][1], intervals[j][1]);
                    flags[j] = true;
                }
            }

            arrays.add(intervals[i]);
        }

        int index = 0;
        int[][] res = new int[arrays.size()][2];
        Iterator<int[]> iterator = arrays.iterator();
        while (iterator.hasNext()){
            res[index] = iterator.next();
            index++;
        }

        return res;
    }

    // 对二维数组进行排序
    public static void sort(int[][] intervals){
        for (int i = 0; i < intervals.length; i++) {
            for (int j = i + 1;j < intervals.length;j++){
                if (intervals[i][0] > intervals[j][0]){
                    int[] temp = intervals[i];
                    intervals[i] = intervals[j];
                    intervals[j] = temp;
                }
                else if (intervals[i][0] == intervals[j][0]){
                    if (intervals[i][1] > intervals[j][1]){
                        int[] temp = intervals[i];
                        intervals[i] = intervals[j];
                        intervals[j] = temp;
                    }
                }
            }
        }
    }

}
