package chapter07;

/**
 * <h2>验证回文字符串</h2>
 * <p>判断回文的经典方法: </p>
 * <p>1. 借助额外空间: 栈结构</p>
 * <p>2. 不借助额外空间: </p>
 * <p>2.1 双指针: 从两头开始判断</p>
 * <p>2.2 中心扩展: 从中心向两边开始判断</p>
 * <p>注: 双指针和中心扩展没有优劣之分, 根据不同的情况使用不同的做法</p>
 */
public class ValidPalindrome
{
    public static void main(String[] args)
    {

    }

    /**
     * <p>1. 这个不是简单的判断回文字符串, 字符串中包含非字母的字符</p>
     * <p>2. 这些非字母的字符都是忽略不计的, 也就是它们不影响回文字符串的判断</p>
     * <p>3. 大写和小写字母也可以认为是回文, Aa 认为是回文字符串</p>
     * <p>注: 这个题显然是不适合中心扩展的</p>
     */
    private static boolean isPalindrome(String str){

        return true;
    }

    /**
     * <p>1. 这个题也没有办法使用中心扩展算法, 因为删除某个字符之后中心轴可能发生变化</p>
     * <p>2. 这样就需要将这个字符从字符串中移除, 并且重新计算中心轴, 是不太好弄的</p>
     * <p>3. 这个题使用双指针相对合适点, 不过依然需要左右尝试</p>
     */
    private static boolean validPalindrome(String str){
        int start = 0;
        int last = str.length() - 1;
        char[] chars = str.toCharArray();
        while (start < last){
            // 如果发现字符不等, 那么就删除字符, 并检查是否是回文字符串
            if (chars[start] != chars[last])
                // 这里使用或运算的原因是因为存在, 删除左边可能不正确, 删除右边才正确的可能新
                return isValid(chars, start + 1, last) || isValid(chars, start, last - 1);
            start++;
            last--;
        }
        return true;
    }

    /**
     * <p>时间复杂度是 O(N): 是因为所有的字符实际上都只遍历了一次, 而不会重复遍历</p>
     */
    private static boolean isValid(char[] chars, int left, int right){
        while (left < right){
            if (chars[left++] != chars[right--]) return false;
        }
        return true;
    }

}
