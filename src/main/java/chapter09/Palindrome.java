package chapter09;

import java.util.LinkedList;
import java.util.List;

/**
 * <h2>回文串相关问题</h2>
 * <h3>常用的三种解法: 中心扩展 => Manacher => 动态规划</h3>
 * <p>1. 验证回文串</p>
 * <p>2. 验证回文子串 II</p>
 * <p>3. 最长回文串</p>
 * <p>4. 回文子串</p>
 * <p>5. 分割回文串</p>
 * <p>6. 分割回文子串 II</p>
 * <p>7. 最长回文序列</p>
 * <p>注: 这里不提供动态规划的做法, 动态规划的解法放在 chapter 06 中</p>
 */
public class Palindrome
{
    public static void main(String[] args)
    {
    }

    /**
     * <h3>3. 最长回文串</h3>
     * <p>中心扩展算法</p>
     * @param str 字符串
     * @return 最长的回文子串
     */
    private static String longestPalindrome(String str, String expandAroundCenter){
        int length = 0;
        int maxLength = 0;
        String result = null;
        for (int center = 0;center <= 2 * str.length() - 1;center++){
            int left = center / 2;
            int right = left + center % 2;
            StringBuilder sb = new StringBuilder();
            while (left >= 0 && right <= str.length() - 1 && str.charAt(left) == str.charAt(right)){
                if(left == right){
                    sb.append(str.charAt(left));
                    length++;
                }else{
                    sb.insert(0, str.charAt(left));
                    sb.insert(sb.length(), str.charAt(right));
                    length += 2;
                }
                left--;
                right++;
            }
            if(maxLength < length){
                maxLength = length;
                result = sb.toString();
            }
            length = 0;
        }
        return result;
    }

    /**
     * <p>Manacher 算法</p>
     * <p>1. 准备变量: 中心点、右边界、回文半径数组</p>
     * <p>2. 生成 Manacher 字符串</p>
     * <p>3. 判断当前索引和右边界以及左边界的关系, 从而确定当前索引的回文半径大小</p>
     */
    private static String longestPalindrome(String str, Integer manacherAlgorithm){
        // 0. 基本变量
        int center = -1;
        int rightBound = -1;
        int maxRadius = 0;
        String result = "";
        // 1. Manacher 字符串
        String manacher = getManacher(str);
        int[] radius = new int[manacher.length()];
        // 2. 开始中心扩展算法
        for (int index = 0;index < manacher.length();index++){
            // 3. 如果索引在右边界内, 分为三种情况
            StringBuilder palindrome = new StringBuilder();
            if (index < rightBound){
                // 4. 对称点的回文半径和到右边界的距离=比较 => 如果小于, 那么当前点的回文半径就是这个; 否则就是到右边界距离
                if (radius[center * 2 - index] < rightBound - index){
                    radius[index] = radius[center * 2 - index];
                }else{
                    radius[index] = rightBound - index;
                }
                palindrome.append(manacher, index - radius[index] + 1, index + radius[index]);
            }else{
                // 5. 如果当前点在右边界的外面, 那么回文半径就是 1
                radius[index] = 1;
                palindrome.append(manacher.charAt(index));
            }
            // 6. 继续扩展增加回文半径长度
            while (index - radius[index] >= 0 && index + radius[index] <= manacher.length() - 1){
                char left = manacher.charAt(index - radius[index]);
                char right = manacher.charAt(index + radius[index]);
                if (left == right){
                    radius[index]++;
                    palindrome.insert(0,left);
                    palindrome.insert(palindrome.length(), right);
                }else{
                    break;
                }
            }
            // 7. 如果当前点的回文半径超过了右边界就需要更新
            if (rightBound < index + radius[index]){
                rightBound = index + radius[index];
                center = index;
            }
            // 8. 更新最大的回文半径
            if (maxRadius < radius[index]){
                maxRadius = radius[index];
                result = palindrome.toString();
            }
        }
        // 9. 去掉最长回文子串中的分隔符
        return back(result);
    }

    private static String back(String str){
        String[] strings = str.split("#");
        StringBuilder sb = new StringBuilder();
        for(int i = 0;i < strings.length;i++){
            sb.append(strings[i]);
        }
        return sb.toString();
    }

    private static String getManacher(String str){
        StringBuilder sb = new StringBuilder();
        for (int index = 0;index < str.length();index++){
            sb.append("#").append(str.charAt(index));
        }
        return sb.append("#").toString();
    }


    /**
     * <h3>4. 回文子串</h3>
     * <p>核心: 中心扩展算法</p>
     * <p>问题: 偶数长度的子串没有办法使用中心扩展算法, 将中间的两个字符作为中心点进行扩展</p>
     * <p>中心点: 可以单个字符也可以两个字符, 所以总共需要进行 2*n - 1次的中心扩展</p>
     * @param str 字符串
     * @return 回文子串的长度
     */
    private static int countSubstrings(String str){
        int result = 0;
        // 1. 开始中心扩展算法
        for (int center = 0;center < str.length() * 2 - 1;center++){
            // 2. 对每个子串进行中心扩展 => 计算得到左指针和右指针
            int left = center / 2; // 这里左指针得到的是中心点偏左的位置 ( (center + 1) / 2 得到的是偏右的位置)
            int right = left + center % 2; // 这里右指针可能和左指针是重合的, 所以需要进行模运算
            // 3. 开始向外扩展, 只要符合要求, 那一定就是回文
            while (left >= 0 && right <= str.length() - 1 && str.charAt(left--) == str.charAt(right++)){
                result++;
            }
        }
        return result;
    }

    /**
     * <p>核心: Manacher 算法</p>
     * <p>注: 只要能够使用中性扩展算法, 那么必然可以使用 Manacher 算法加速</p>
     */
    private static int countSubstrings(String str, Object ...args){
        int center = -1;
        int rightBound = -1;
        int result = 0;
        String manacher = getManacher(str);
        int[] radius = new int[manacher.length()];
        for(int index = 0;index < manacher.length();index++){
            radius[index] = index < rightBound ? Math.min(radius[center * 2 - index], rightBound - index): 1;
            while(index - radius[index] >= 0 && index + radius[index] <= manacher.length() - 1){
                char left = manacher.charAt(index - radius[index]);
                char right = manacher.charAt(index + radius[index]);
                if(left == right)radius[index]++;
                else break;
            }
            if(rightBound < index + radius[index]){
                center = index;
                rightBound = index + radius[index];
            }
            // 注: 回文半径 ÷ 2 之后得到真正的回文半径
            result += radius[index] / 2;
        }
        return result;
    }

    /**
     * <p>核心: 基于双指针的暴力解法, 很慢</p>
     * <p>如果做到了这个题, 实在想不起来前面两种解法的时候, 就可以使用这个</p>
     */
    private static int countSubstrings(String str, Integer ...numbers){
        int result = str.length();
        for (int i = 2;i <= str.length();i++){
            for (int j = 0;j <= str.length() - i;j++){
                if (isValid(str.substring(j, j + i))) result++;
            }
        }
        return result;
    }

    private static boolean isValid(String str){
        int first = 0;
        int last = str.length() - 1;
        while (first < last){
            if (str.charAt(first++) != str.charAt(last--)) return false;
        }
        return true;
    }


}
