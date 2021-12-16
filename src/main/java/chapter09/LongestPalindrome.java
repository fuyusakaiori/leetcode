package chapter09;


/**
 * <h2>最长回文子串</h2>
 * <p>问题描述: </p>
 * <p>1. 给定一个字符串, 然后将这个字符串中的所有字符进行组合</p>
 * <p>2. 组合出来的最长的回文字符串的长度是多少</p>
 */
public class LongestPalindrome
{
    public static void main(String[] args)
    {
        convert("PAYPALISHIRING", 3);
    }

    public static String convert(String string, int numRows) {
        char[] chars = string.toCharArray();
        int steps = 2 * (numRows - 1);
        int step = steps;
        StringBuilder sb = new StringBuilder();

        for(int i = 0;i < numRows;i++){
            sb.append(recursive(chars, steps, i, step));
            step -= 2;
        }

        return sb.toString();
    }

    public static String recursive(char[] chars, int steps, int index, int step){
        if(index >= chars.length) return "";
        String result = "";
        result = result + chars[index];
        recursive(chars, steps, index + step,
                steps - step == 0 ? step: steps - step);
        return result;
    }


    /**
     * <p>核心思路: 回文字符串有个特点, 出现奇数次的字符只会存在一种, 其余的字符一定出现的都是偶数次</p>
     * <p>1. 所以我们只需要统计出现偶数次和出现奇数次的字符</p>
     * <p>2. 然后将偶数次的字符全部加起来, 奇数次的字符 - 1加起来, 最后就可以得到总的长度了</p>
     * <p>3. 注: 使用普通的数组比哈希表快不少, 可能是因为哈希表扩容的原因</p>
     * @param string 字符串
     * @return 最长回文长度
     */
    private static int longestPalindrome(String string){
        // 0. 存储字符次数的数组, 可能存在特殊字符和大写字母, 所以是整个 ASCII 范围
        int[] count = new int[128];
        // 1. 开始统计次数
        for (int index = 0;index < string.length();index++){
            count[string.charAt(index)]++;
        }
        // 2. 开始挑选字符
        int length = 0;
        for (int index = 0;index < count.length;index++){
            // 2.1 如果长度为偶数的时候, 是可以直接添加奇数次的字符的
            if (length % 2 == 0)
                length += count[index];
            // 2.2 如果长度为奇数的时候, 就只能添加偶数次的字符, 和奇数次字符 - 1
            else
                length += count[index] % 2 == 0 ? count[index] : count[index] - 1;
        }
        return length;
    }

}
