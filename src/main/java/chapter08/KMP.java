package chapter08;

/**
 * <h2>KMP 算法</h2>
 * <h3>解决问题: </h3>
 * <p>1. 查询一个字符串是否是另一个字符串的子串</p>
 * <p>2. 如果是子串那么就返回这个子串第一次出现的索引, 如果不是那么就返回 -1</p>
 * <h3>算法内容: </h3>
 * <p>1. 解释什么是前缀和后缀字符串</p>
 * <p>1.1 前缀和后缀字符串是针对模式字符串而言的</p>
 * <p>1.2 每个字符之前的字符串可以拆分为前缀和后缀字符串</p>
 * <p>1.3 示例: a b b t z c a b b | k</p>
 * <p>1.4 a b b, a b b t...就可以称为前缀字符串, a b b, c a b b... 就可以称为后缀字符串</p>
 * <p>2. 解释下什么是 next 数组</p>
 * <p>2.1 next 数组中也是针对模式字符串而言的</p>
 * <p>2.2 next 数组中保存每个字符的前缀字符串和后缀字符串相等的最大长度</p>
 * <p>2.3 示例: a b b t a b b || z c || a b b t a b b | f</p>
 * <p>2.4 f 的最长的匹配长度就是 7, next 数组中会记录模式字符串中每个字符的最长匹配长度的信息</p>
 * <p>2.5 前两个字符的最长匹配长度人为规定为 -1、0</p>
 * <p>3. 算法的执行过程</p>
 * <p>3.1 原字符串从 i 和模式字符串进行匹配直到 k 位置发现字符不同, 前面的字符都是相同的</p>
 * <p>3.2 此时原字符串的索引不动, 模式字符串的索引移动到的位置, 就是当前这个字符在 next 数组中对应的最长匹配</p>
 * <p>原字符串: a b b t a b b || z c || a b b t a b b | e</p>
 * <p>模式字符串 a b b t a b b || z c || a b b t a b b | f</p>
 * <p>此时发现 f 和 e 不同, 模式字符串的索引从当前的 16 移动到 7（最长匹配长度）</p>
 * <p>然后让 z 开始和 e 比较, 但是发现两者还是不相同, 则继续向前推</p>
 * <p>模式字符串从当前的 7 移动到 3（最长匹配长度）</p>
 * <p>最后发现还是不同, 就移动到 a, 此时发现最长匹配长度为 -1, 那么就需要让原字符串向前移动, 因为已经推不动了</p>
 * <h3>算法实质: </h3>
 * <p>1. 就是将模式字符串向右移动 next[i] 对应的长度, 或者说将原字符串向左移动 next[i] 对应的长度</p>
 * <p>2. 你会发现原字符串中有些字符被跳过匹配了, 那些字符都是不可能匹配成功的, 所以跳过了</p>
 * <p>注: 第二条的证明是采用反证法证明的, 还是详见左神的视频</p>
 * <p>注: Java indexOf 采用的是改进的 KMP 算法</p>
 */
public class KMP
{
    public static void main(String[] args)
    {
        String string = "Hello World!";
        /*
         1. Java indexOf 采用就是普通暴力实现而不是 KMP 算法（左神这里应该是说错了）
         2. KMP 在常见的应用场景下并不会比暴力实现快, 因为存在一个初始化的时间
         3. 而在大字符串的情况下, 使用正则表达式会更好
         */
        string.indexOf("or");
        System.out.println(indexOf(string, "World"));
    }

    /**
     * <p>查询模式字符串是否为传入的字符串的子串</p>
     * @param string 字符串
     * @param pattern 模式字符串
     * @return 子串第一次出现的索引
     */
    private static int indexOf(String string, String pattern){
        // 0. 获取 next 数组
        int[] next = getNextArray(string);
        // 1. 开始遍历字符串
        int first = 0;
        int second = 0;
        while (first != string.length() && second != pattern.length()){
            // 2. 如果两个字符相等, 那么显然直接两个指针都是一起向右移动
            if (string.charAt(first) == pattern.charAt(second)){
                first++;
                second++;
            }else if (second == 0){
              // 4. 如果发现模式字符串的索引已经推动到头了, 那么原字符串就该向右移动了
                first++;
            }else{
                // 3. 如果发现两者不等, 那么就要检查当前的 next 数组, 决定模式字符串索引回到哪个位置
                second = next[second];
            }
        }
        // 5. 如果第二个指针到达模式字符串的末尾, 那么就认为找到了匹配的子串
        return second == pattern.length() ? first - second : -1;
    }

    /**
     * <p>获取 next 数组</p>
     * @param string 传入的字符串
     * @return 返回 next 数组
     */
    private static int[] getNextArray(String string){
        // 0. 每个字符都有对应的最长匹配长度信息
        int[] next = new int[string.length()];
        // 1. 人为规定的信息
        next[0] = -1;
        next[1] = 0;
        // 2. 开始索引是 2
        int index = 2;
        // 3. 前缀字符串的右边界
        int rightBound = 0;
        while (index < next.length){
            // 如果前一个字符和前缀字符串的右边界相等
            if (string.charAt(index - 1) == string.charAt(rightBound)){
                // 前缀字符串的右边界也是需要向右移动的
                next[index++] = ++rightBound;
            }else if (rightBound > 0){
                rightBound = next[rightBound];
            }else{
                // 如果最后都跳到索引为 0 的位置了, 那么显然就是没有匹配的前缀和后缀了
                next[index++] = rightBound;
            }
        }
        return next;
    }
}
