package chapter07;

import java.util.*;

/**
 * <h2>字符串转换系列问题</h2>
 * <h3>1. 罗马数字转换成整数</h3>
 * <h3>2. 整数转换成罗马数字</h3>
 * <h3>3. 字符串转换成整数</h3>
 * <h3>注: 这个系列所有问题都是模拟问题, 没有太多优化技巧, 照着做就行</h3>
 */
public class StringParse {

    private static final Map<Character, Integer> romanToIntMap = new HashMap<Character, Integer>(){{
        put('I', 1);
        put('V', 5);
        put('X', 10);
        put('L', 50);
        put('C', 100);
        put('D', 500);
        put('M', 1000);
    }};

    /**
     * <h3>思路: 罗马数字转整数</h3>
     * <h3>注: 比较简单, 遍历计算就可以</h3>
     */
    private static int romanToInt(String roman){
        int number = 0;
        int previous = 0;
        for (int index = roman.length() - 1;index >= 0;index--){
            int digit = romanToIntMap.get(roman.charAt(index));
            number -= previous != 0 && previous > digit ? digit: -digit;
            previous = digit;
        }
        return number;
    }

    private static final int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    private static final String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

    /**
     * <h3>思路: 整数转罗马数字</h3>
     */
    private static String intToRoman(int number) {
        StringBuilder sb = new StringBuilder();

        for (int index = 0;index < values.length && number > 0;index++){
            int value = values[index];
            while (number >= value){
                number -= value;
                sb.append(symbols[index]);
            }
        }
        return sb.toString();
    }

    /**
     * <h3>思路: 字符串转换成整数</h3>
     * <h3>注: 照着题目的思路模拟就行</h3>
     * <h3>注: 属于纯粹面向测试用例编程, 最纯粹的模拟题</h3>
     */
    private static int parseInt(String str){
        // 1. 去除前置空格
        str = str.trim();
        // 2. 去除不可行的情况
        if ("".equals(str) || ".".equals(str) || Character.isLetter(str.charAt(0)))
            return 0;
        // 3. 判断正负号
        boolean isPositive = true;
        if (!Character.isDigit(str.charAt(0))){
            // 3.1 判断是正号还是负号
            if (str.charAt(0) == '-')
                isPositive = false;
            // 3.2 去除正负号
            str = str.substring(1);
        }
        // 4. 开始遍历
        long number = 0;
        long next = 0;
        for (int index = 0;index < str.length();index++){
            if (Character.isDigit(str.charAt(index))){
                next = number * 10 + str.charAt(index) - '0';
                if (next < number || next > Integer.MAX_VALUE)
                    return isPositive ? Integer.MAX_VALUE: Integer.MIN_VALUE;
                number = next;
            }else{
                break;
            }
        }

        return isPositive ? (int) number: (int) -number;
    }

}
