package chapter05.interval;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * <h2>区间问题</h2>
 * <h3>1. 合并区间</h3>
 * <h3>2. 插入区间</h3>
 * <h3>3. 汇总区间</h3>
 * <h3>4. 删除区间</h3>
 */
public class Interval {

    /**
     * <h3>思路: 合并区间</h3>
     */
    private static int[][] merge(int[][] intervals){
        // 1. 按照区间的左端点进行排序
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
        // 2. 合并区间 新的区间数组最大增加 1
        int current = -1;
        int[][] merge = new int[intervals.length + 1][2];
        for (int[] interval : intervals) {
            // 3. 如果前一个区间的右端点小于后一个区间的左端点, 那么说明两个区间不可以合并
            if (current == -1 || merge[current][1] < interval[0]) {
                merge[++current] = interval;
            }else {
                // 4. 如果可以合并, 那么只需要更新区间的右端点就行
                merge[current][1] = Math.max(merge[current][1], interval[1]);
            }
        }
        return Arrays.copyOf(merge, current + 1);
    }

    /**
     * <h3>思路: 插入区间</h3>
     */
    private static int[][] insert(int[][] intervals, int[] newInterval){
        int index = 0;
        int[][] newIntervals = new int[intervals.length + 1][2];
        // 1. 先将左侧无法合并的区间放入新的区间数组中
        while (index < intervals.length && intervals[index][1] < newInterval[0]){
            newIntervals[index] = intervals[index];
            index++;
        }
        // 2. 再将中间可以合并的区间进行合并然后放入区间数组中
        int current = index;
        while (index < intervals.length && newInterval[1] >= intervals[index][0]){
            newInterval[0] = Math.min(newInterval[0], intervals[index][0]);
            newInterval[1] = Math.max(newInterval[1], intervals[index][1]);
            index++;
        }
        newIntervals[current] = newInterval;
        // 3. 最后将右侧无法合并的区间再次放入区间数组中
        while (index < intervals.length){
            newIntervals[++current] = intervals[index++];
        }
        return Arrays.copyOf(newIntervals, current + 1);
    }

    /**
     * <h3>思路: 汇总区间</h3>
     */
    private static List<String> summary(int[] numbers){
        if (numbers.length == 0)
            return new LinkedList<>();
        int start = 0;
        List<String> intervals = new LinkedList<>();
        for (int index = 0;index <= numbers.length;index++){
            if (index == numbers.length ||
                        numbers[index] - numbers[start] != index - start){
                intervals.add(start == index - 1
                                      ? String.valueOf(numbers[start])
                                      : numbers[start] + "->" + numbers[index - 1]);
            }
        }
        return intervals;
    }

    /**
     * <h3>思路: 删除区间</h3>
     */
    private static int remove(int[][] intervals){
        // 1. 排序
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
        // 2. 合并区间
        int count = 0;
        int start = -1, end = -1;
        for (int[] interval : intervals){
            // 注: 此前已经按照左端点进行排序, 所以只要左边界相等, 那么必然会进行合并
            if (start == interval[0]
                        || (start < interval[0] && interval[1] <= end)){
                // 注: 右边界是没有排序的, 所以是需要将右边界更新为最大的那个
                end = Math.max(end, interval[1]);
            }else{
                // 注: 只要无法合并就将区间数增加, 然后重新更新左边界和右边界
                count++;
                start = interval[0];end = interval[1];
            }
        }
        return count;
    }
}
