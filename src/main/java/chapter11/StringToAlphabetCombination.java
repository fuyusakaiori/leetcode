package chapter11;

/**
 * <h2>数字字符串转换为字符组合的种数</h2>
 * <p>问题描述</p>
 * <p>1. 给定一个由数字组成的字符串</p>
 * <p>2. 每个数字都可以转换为对应的字母, 1-A,2-B,3-C...26-Z</p>
 * <p>3. 数字之间可以自由组合, 也就是说 1123, 可以将 1 认为是 A, 也可以将 11 认为是 K</p>
 * <p>4. 问有多少种组合方法</p>
 */
public class StringToAlphabetCombination
{
    public static void main(String[] args)
    {
        System.out.println(combination("01"));
    }

    private static int combination(String number){
        return recursive(number, 0);
    }

    private static int recursive(String number, int index){
        if (index == number.length())
            return 1;
        if (number.charAt(index) == '0')
            return 0;
        int res = 0;
        if (number.charAt(index) == '1'){
            res += recursive(number, ++index);
            if (index < number.length())
                res += recursive(number, ++index);
        }else if (number.charAt(index) == '2'){
            res += recursive(number, ++index);
            if (index < number.length() && number.charAt(index) <= '6')
                res += recursive(number, ++index);
        }else{
            res += recursive(number, ++index);
        }
        return res;
    }


}
