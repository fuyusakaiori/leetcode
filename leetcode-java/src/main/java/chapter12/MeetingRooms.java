package chapter12;

import java.util.Arrays;
import java.util.Comparator;

/**
 * <h2>会议室问题</h2>
 * <p>问题描述</p>
 * <p>1. 存在多个项目, 每个项目都具有开始和结束时间</p>
 * <p>2. 现在要求你去安排项目的宣讲顺序, 确保当天安排的项目数是最多的</p>
 * <p>解法</p>
 * <p>1. 暴力递归, 每个会议室都去尝试</p>
 * <p>2. 寻找贪心策略, 看有几个标准, 然后去尝试</p>
 */
public class MeetingRooms
{
    /**
     * 需要安排的项目类
     */
    private static class Program{
        // 开始时间
        private int start;
        // 结束时间
        private int end;

        public Program(int start, int end)
        {
            this.start = start;
            this.end = end;
        }
    }

    private static class ProgramComparator implements Comparator<Program>{
        @Override
        public int compare(Program first, Program second) {
            return first.end - second.end;
        }
    }


    public static void main(String[] args)
    {
        Program[] programs = {
                new Program(6, 9), new Program(6, 8), new Program(7, 12),
                                     new Program(10, 13), new Program(11, 12), new Program(13, 16)
        };
        System.out.println(best(programs));

    }

    private static int arrangeProgram(Program[] programs){
        return best(programs);
    }


    /**
     * 因为不知道怎么安排才是最好的, 所以就逐个去尝试
     */
    private static int recursive(Program[] programs, int index){

        return 0;
    }

    /**
     * 验证是否违规
     */
    private static boolean isValid(Program first, Program second){
        return first.end <= second.start;
    }

    /**
     * <p>采用贪心策略完成</p>
     * <p>贪心策略: 每次都安排最早结束的项目</p>
     */
    private static int best(Program[] programs){
        int lastEnd = 0;
        int result = 0;
        // 0. 按照会议结束最早的时间排序
        Arrays.sort(programs, new ProgramComparator());
        // 1. 每次挑选结束最早的会议
        for (int i = 0;i < programs.length;i++){
            if (lastEnd <= programs[i].start){
                result++;
                lastEnd = programs[i].end;
            }
        }

        return result;
    }



}
