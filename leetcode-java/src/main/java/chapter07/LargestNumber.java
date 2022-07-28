package chapter07;

import java.util.Arrays;

/**
 * <h2>最大数</h2>
 * <p>核心: 贪心策略</p>
 */
public class LargestNumber
{
    public static void main(String[] args)
    {

    }

    /**
     * <p>贪心策略: </p>
     * <p>1. 考虑数组中只有两个数的情况, 那么可以将两个数以字符串的形式相加</p>
     * <p>2. 前者 + 后者 > 后者 + 前者 的话, 就证明当前这两个数的顺序是合理的, 否则就交换</p>
     * <p>3. 从两个数的最大值推导到 N 个数组合的最大值, 也就是只要我们能够确保两两拼接最大, 那么就可以确保 N 个拼接最大</p>
     * @param numbers 数组
     * @return 最大值
     */
    private static String largestNumber(int[] numbers){
        // 0. 先处理成字符串
        String[] strings = new String[numbers.length];
        for(int i = 0;i < strings.length;i++){
            strings[i] = "" + numbers[i];
        }
        // 1. 前者 + 后者 和 后者 + 前者进行比较
        // 2. 如果后者 + 前者 > 前者 + 后者, second 就会被排序到 first 的前面
        // 3. 这样我们就可以再次遍历字符串数组, 两两拼接就行
        Arrays.sort(strings, (first, second) ->{
            String a = first + second;
            String b = second + first;
            return b.compareTo(a);
        });
        StringBuilder sb = new StringBuilder();
        for(String str : strings){
            sb.append(str);
        }
        int index = 0;
        // 4. 整个数组都是 0 的话, 我们最后显然是只需要一个 0 的, 而不是多个, 所以需要单独处理
        // 举例: [0, 0, 0...]
        while(index < sb.length() - 1 && sb.charAt(index) == '0')
            index++;
        return sb.toString().substring(index);
    }
}
