package chapter09;


/**
 * <h2>Manacher 算法</h2>
 * <p>应用场景: Manacher 算法有很多可以应用的题目, 比较常见的题目就是最长回文子串</p>
 * <p>算法内容: </p>
 * <p>1. 核心是基于中心扩展算法</p>
 * <p>1.1 如何判断一个字符串是否为回文字符串, 通常可以采用双指针的做法, 但是也可以采用中心扩展</p>
 * <p>1.2 找到中心字符, 然后向两边扩展, 也就是同时移动两个指针进行比对, 只要比对能够结束就可以证明是回文</p>
 * <p>举例: a b c k c b a </p>
 * <p>从 k 字符开始向两边扩展, 同时比对两个字符是否相同</p>
 * <p>找到最大回文子串的方法就是对每个字符串都使用中心扩展算法, 然后就可以确定最大值</p>
 * <p>但是这样是没有办法解决回文字符串是偶数的情况的, 所以通查都需要对原字符串进行处理</p>
 * <p>处理的字符串: # a # b # c # k # c # b # a #</p>
 * <p>这样得到的回文串长度 / 2 就是原始的回文串长度</p>
 * <p>注: 每个字符包括添加的字符都是需要执行中心扩展的, 这里添加的字符可以是任意的字符, 不一定要没有出现过的</p>
 * <p>2. 准备知识</p>
 * <p>2.1 回文半径和回文直径: a b c k c b a 这个回文字符串的回文半径就是 4, a 这个字符串的回文半径就是 1</p>
 * <p>2.2 右边界: 就是当前回文串能够扩展到的最右的边界, a b c k c b a 对 k 做中心扩展, 右边界就是 6, 这个起始值默认是 -1</p>
 * <p>2.3 中心点: 每个回文字符串的中心点, 比如 a b c k c b a 中心店就是 k</p>
 * <p>3. 算法过程</p>
 * <p>3.1 如果当前需要执行中心扩展算法的结点不在右边界内. 那么就只能够直接进行最原始的中心扩展</p>
 * <p>3.2 如果当前需要执行中心扩展算法的结点就在右边界内, 那么又分为三种情况</p>
 * <p>3.2.1 将当前点以中心点为对称轴做对称点, 然后检查这个对称点的回文串长度, 每个点的回文长度都是保存在数组中的</p>
 * <p>3.2.2 如果对称点的回文串长度就在左边界内, 那么当前点的回文串长度就是对称点的回文串长度</p>
 * <p>3.2.3 如果对称点的回文串长度超过了左边界, 那么当前点的回文串半径就是从自己到出发到右边界的距离</p>
 * <p>3.2.4 如果对称点的回文串长度刚好等于左边界, 那么当前点的回文串长度只能够得知一部分, 这一部分就是从自己到右边界, 然后从这个部分继续中心扩展算法判断到底多长</p>
 * <p>注: 大致内容就是这样, 整体比 KMP 算法简单点, 但是这里有个优化过程, 我会写两版</p>
 * <p>注: 实在记不得的时候建议再去看一遍左神的视频, 讲的非常清楚</p>
 */
public class Manacher
{
    /**
     * <p>以后采用 Manacher 算法完成的题目都记录在里面</p>
     */
    public static void main(String[] args)
    {

    }

    /**
     * <p>最长回文子串</p>
     * @param string 自付出那
     * @return 最长回文串的长度
     */
    private static int longestPalindrome(String string){

        return manacherAlgorithmOptimize(string);
    }

    /**
     * <p>完全采用中心扩展算法完成</p>
     * TODO 注: 这里是可以不使用处理字符串的, 一会儿会改一版
     * @return 最长回文字符串的长度
     */
    private static int expandAroundCenter(String string){
        int length = 1;
        int maxLength = 0;
        // 0. 获取处理之后的字符串
        String manacher = getManacherString(string);
        // 1. 开始对每个字符进行中心扩展算法
        for (int i = 0;i < manacher.length();i++){
            int left = i - 1;
            int right = i + 1;
            while (left >= 0 && right <= manacher.length() - 1 &&
                           manacher.charAt(left) == manacher.charAt(right)){
                left--;
                right++;
                length += 2;
            }
            maxLength = Math.max(length, maxLength);
            length = 1;
        }

        return maxLength / 2;
    }

    /**
     * <p>没有优化代码的 Manacher 算法</p>
     * <p>没有优化的那个版本在 LeetCode 上写了</p>
     */
    private static int manacherAlgorithm(String string){

        return 0;
    }

    /**
     * <p>最优化的版本, 代码比较短</p>
     */
    private static int manacherAlgorithmOptimize(String string){
        int maxLength = 0;
        // 0. 获取处理字符串
        String manacher = getManacherString(string);
        // 1.1 准备存储回文串长度的数组
        int[] palindromes = new int[manacher.length()];
        // 1.2 准备两个变量: 右边界和中心点
        int rightBound = -1;
        int center = -1;
        // 2. 开始遍历字符串
        for (int index = 0;index < manacher.length();index++){
            /*
             1. 这里是完全可以采用 if-else 的分支来写, 但是这样写代码就太长了, 所以使用一句话将四个分支全部综合
             2. 下面就来解释下为什么可以这么写
             2.1 第一种情况: index > rightBound（边界外）, 显然这种回文字符串最基本的半径就是 1, 然后后面通过中心扩展不断扩展半径大小
             2.2 第二种情况: index < rightBound（边界内）, index` 回文长度不超过 leftBound, 此时回文半径就是 palindromes[index`]
             2.3 第三种情况: index < rightBound, index` 回文长度超过 leftBound, 此时回文半径就是 rightBound - index
             2.4 第四种情况: index < rightBound, index` 回文长度刚好压在 leftBound, 此时基础的回文半径就是 rightBound - index 或者 palindromes[index`], 这两者是相等的
             2.5 为了将第二种和第三种情况合起来, 显然对 rightBound - index 和 palindromes[index`] 取最小就行, 因为第二种情况下, palindromes[index`] 肯定是小于 rightBound - index
                 第三种情况下, palindromes[index`] 肯定是大于 rightBound - index, 所以刚好取最小值就可以合并两个分支
             3. 对称点的求法: center - (index - center) => center * 2 - index
             */
            palindromes[index] = index < rightBound ?
                                         Math.min(palindromes[center * 2 - index], rightBound - index): 1;
            // 3. 开始中心扩展
            while (index - palindromes[index] >= 0 && index + palindromes[index] <= manacher.length() - 1){
                if (manacher.charAt(index - palindromes[index]) == manacher.charAt(index + palindromes[index]))
                    palindromes[index]++;
                // 注: 第二种和第三种情况进来就会直接退出
                else
                    break;
            }
            // 4. 如果新的回文字符串的右边界超过了原来的右边界那么就需要更新, 中心点也需要更新
            if (rightBound < index + palindromes[index]){
                rightBound = index + palindromes[index];
                center = index;
            }
            // 5. 记录最长的回文半径
            maxLength = Math.max(maxLength, palindromes[index]);
        }
        // 6. 由于使用的是处理之后的字符串, 所以回文半径并不代表真正的回文字符串的回文半径, 需要将其 -1, 就恰好是原来回文字符串的半径
        return maxLength - 1;
    }


    /**
     * <p>获取处理之后的字符串</p>
     * @return 处理之后的字符串
     */
    private static String getManacherString(String string){
        StringBuilder sb = new StringBuilder();
        for (int i = 0;i < string.length();i++){
            sb.append("#").append(string.charAt(i));
        }
        return sb.append("#").toString();
    }



}
