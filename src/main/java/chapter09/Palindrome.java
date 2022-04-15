package chapter09;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <h2>回文串相关问题</h2>
 * <h3>1. 验证回文串</h3>
 * <h3>2. 验证回文子串 II</h3>
 * <h3>3. 最长回文串</h3>
 * <h3>4. 回文子串</h3>
 * <h3>5. 分割回文串</h3>
 * <h3>6. 分割回文子串 II</h3>
 * <h3>7. 最长回文序列</h3>
 * <h3>注: 这类题目解法基本一致: ① 中心扩展 ② {@code Manacher} 算法 ③ d动态规划</h3>
 */
public class Palindrome {

    /**
     * <h3>思路: 最长回文串</h3>
     * <h3>1. 中心扩展算法: 这个算法是一定要掌握的</h3>
     * <h3>2. {@code Manacher} 算法: 这个是拿来装逼的</h3>
     * <h3>3. 动态规划: 这个是最好要会的, 相对容易的优化解</h3>
     */
    private static String longestPalindrome1(String str){
        int maxLength = 0;
        String result = null;
        char[] chars = str.toCharArray();
        // 1. 这里最核心的地方在于存在两种情况: aba bba;前者很容易判断, 但是后者和前者判断就有些微差距
        // 2. 所以这里就有两种处理方式: 填充间隙, 这样依然可以按照第一种方式处理; 第二种就是单独处理
        for (int center = 0;center < chars.length * 2 - 1;center++){
            // 3. 这里的目的就是为了兼顾两种情况, 我把另外一种写法写出来, 应该会比较好理解
            int left = center / 2, right = left + center % 2;
            while (left >= 0 && right <= chars.length - 1){
                if (chars[left] != chars[right]) break;
                left--;
                right++;
            }
            String sub = str.substring(left + 1, right);
            if (sub.length() > maxLength){
                maxLength = Math.max(maxLength, sub.length());
                result = sub;
            }
        }
        return result;
    }

    private static String longestPalindrome2(String str){
        int start = 0, end = 0;
        for (int center = 0;center < str.length();center++){
            // 1. 中心点为当前点
            int firstLength = expand(str, center, center);
            // 2. 中心点在缝隙中
            int secondLength = expand(str, center, center + 1);
            // 3. 最大长度
            int maxLength = Math.max(firstLength, secondLength);
            if (maxLength > end - start + 1){
                // 4. 这里长度减一再除二的目的是为了确保中心点在缝隙中的情况是正确的
                start = center - (maxLength - 1) / 2;
                end = center + maxLength / 2;
            }
        }
        return str.substring(start, end + 1);
    }

    private static int expand(String str, int left, int right){
        while (left >= 0 && right <= str.length() - 1){
            if (str.charAt(left) != str.charAt(right))
                break;
            left--;
            right++;
        }
        return right - left - 1;
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
