package chapter06.greedy;

/**
 * <h2>最大交换</h2>
 */
public class MaximumSwap {

    /**
     * <h3>暴力解</h3>
     * <h3>注: 无论采用暴力解还是贪心算法, 都要将整数转为字符串形式, 否则不好提取数字和交换</h3>
     */
    private static int maximumSwap1(int number){
        // 注: 最大值初始化应该是原来的值, 因为可能远数字只有一位而无法进行交换
        int maxNumber = number;
        char[] chars = String.valueOf(number).toCharArray();
        for (int first = 0;first < chars.length;first++){
            for (int second = first + 1; second < chars.length; second++){
                swap(chars, first, second);
                maxNumber = Math.max(maxNumber, Integer.parseInt(new String(chars)));
                // 注: 记得交换回去, 否则就交换了多次
                swap(chars, first, second);
            }
        }
        return maxNumber;
    }

    /**
     * <h3>贪心算法</h3>
     * <h3>1. 数字最大的情况肯定是从左到右依次递减的, 那么最优的交换方式肯定就是让高位的小数和低位的大数交换</h3>
     * <h3>2. 现在的问题是大数和小数应该怎么确定, 也就是这个贪心算法到底怎么实现</h3>
     * <h3>3. 判断一个数是否是小数, 那么就只能向后遍历看是否有比自己大的数, 如果有那么自己就是小数</h3>
     * <h3>4. 判读小数的同时也就会找到相应的大数, 所以大数才是好找的那个数字</h3>
     * <h3>5. 如果每次都去遍历那么对于本身就是最大的数来说, 时间复杂度就是 O(n), 所以我们需要提前记录下来</h3>
     * <h3>注: 这里我会写两个版本: 分别是优化过的和没有优化的</h3>
     */
    private static int maximumSwap2(int number){
        char[] chars = String.valueOf(number).toCharArray();
        for (int first = 0;first < chars.length;first++){
            int index = first;
            // 注: 这里只能够倒着找最大的数, 不要顺着找, 有些例子是过不了的
            for (int second = chars.length - 1;second > first;second--){
                index = chars[second] - '0' > chars[index] - '0' ? second: index;
            }
            if (index != first){
                swap(chars, first, index);
                return Integer.parseInt(new String(chars));
            }
        }
        return number;
    }

    private static void swap(char[] chars, int first, int second){
        char temp = chars[first];
        chars[first] = chars[second];
        chars[second] = temp;
    }

}
