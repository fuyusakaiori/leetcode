package chapter07;

/**
 * <h2>大写和小写字母转换</h2>
 * <p>注: 禁止使用语言自带的函数</p>
 * <p>优化技巧: 位运算</p>
 */
public class LowerCaseAndUpperCase
{
    /**
     * <p>1. 大写字母和小写字母的 ASCII 码差值是固定, 可以利用这个特点进行转换</p>
     * <p>2. 可以避免使用减法或者加法而是使用位运算来进行转换</p>
     */
    public static void main(String[] args)
    {

    }

    /**
     * <p>转换成小写字母: 字母和 32 进行或运算</p>
     */
    private static String toLowerCase(String str){
        char[] chars = str.toCharArray();
        for (int index = 0;index < chars.length;index++){
            if (chars[index] >= 'A' && chars[index] <= 'Z')
                chars[index] |= 32;
        }
        return new String(chars);
    }

    /**
     * <p>转换成大写字母: 字母和 32 进行异或运算</p>
     */
    private static String toUpperCase(String str){
        char[] chars = str.toCharArray();
        for (int index = 0; index < chars.length; index++) {
            if (chars[index] >= 'a' &&  chars[index] <= 'z')
                chars[index] ^= 32;
        }
        return new String(chars);
    }
}
