package utils;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * <h2>练习处理输入输出</h2>
 * <h3>注: 这个过程必须非常熟悉, 你才能够知道之后如何处理输入数据, 而不是在笔试的时候尝试</h3>
 */
public class Demo {

    /**
     * <h3>1. 我知道问题在哪里了, 我不知道采用何种结构将数据保存起来</h3>
     * <h3>2. 由于不知道是如何将数据保存起来的, 就无法识别这个题究竟是什么类型</h3>
     * <h3>3. 没有识别出题的类型, 最终就出现了这种不知道如何下手的感觉</h3>
     * <h3>4. 不要将 next 系列方法和 nextLine 混用, 很容易出现这种读取换行符的现象</h3>
     * <h3>5. 所有数据无论长度是否固定, 都直接采用链表保存, 非常直接</h3>
     */
    public static void main(String[] args) {
        // 1. 声明输入对象
        Scanner in = new Scanner(System.in);
        // 2. 通常在笔试的时候都是按照字符串读取一行数据的, 所以需要掌握所有和字符串相关的读取
        String includeBlankSpace = in.nextLine();
        System.out.println("读取的内容可以有空格: " + includeBlankSpace);
        String excludeBlankSpace = in.next();
        System.out.println(in.next());
        System.out.println(in.next());
        System.out.println(in.next());
        // 只有 nextLine 会将换行吃掉, 其余所有 next 方法都会保留换行, 所以就出现了读取到空的现象
        in.nextLine();
        System.out.println("读取的内容不可以有空格: " + excludeBlankSpace);
        // 3. 连续处理多行数据: 通常第一行都是表示之后有多少行数据, 这里必须使用循环否则之后的测试用例是跑不起来的
        while (in.hasNextLine()){
            // 注: 可能单个测试用例就会有多行数据
            int count = Integer.parseInt(in.nextLine());
            // 4. 之后的每行很有可能是用字符串表示的整数, 那么首先只能够使用字符串的方式读取出来
            List<List<Integer>> input = resolve(in, count);
            // 5. 说白了就是我们需要自行定义数据如何保存, 如果这个结构设计的不好, 确实有可能影响做题
            System.out.println(input);
        }
    }

    /**
     * <h3>单个测试用例存在多行数据的</h3>
     */
    public static List<List<Integer>> resolve(Scanner in, int count){
        List<List<Integer>> input = new LinkedList<>();
        for (int index = 0;index < count;index++){
            input.add(solution1(in));
        }
        return input;
    }

    /**
     * <h3>如何将一行用字符串表示的整数分别处理成整数</h3>
     */
    public static List<Integer> solution1(Scanner in){
        String line = in.nextLine();
        // 这种处理方式能够很容易针对每行格式不同的输入, 我看了大伙的基本也都是这么处理的
        String[] strs = line.split(" ");
        // 解决方案: 只能单个单个转换
        List<Integer> list = new LinkedList<>();
        for (String str : strs) {
            list.add(Integer.parseInt(str));
        }
        return list;
    }

    public static int[][] process(Scanner in){
        int count = Integer.parseInt(in.nextLine());
        int[][] numbers = new int[count][2];
        for(int index = 0;index < count;index++){
            String[] line = in.nextLine().split(" ");
            numbers[index] = new int[]{Integer.parseInt(line[0]), Integer.parseInt(line[1])};
        }

        return numbers;
    }

    public static void add(int[][] numbers){
        for(int index = 0;index < numbers.length;index++){
            System.out.println(numbers[index][0] + numbers[index][1]);
        }
    }



}
