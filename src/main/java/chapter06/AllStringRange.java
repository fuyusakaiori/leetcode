package chapter06;

/**
 * <h2>打印字符串的全部排列组合</h2>
 * <p>问题描述: 就是说需要将字符串中的所有字符随机组合并打印</p>
 * <p>进阶问题: 不允许重复打印</p>
 */
public class AllStringRange
{
    /**
     * 分支限界, 提前结束递归, 也就是剪枝策略, 优化常数项
     */
    public static void main(String[] args)
    {
        findAll("abc");
    }

    /**
     * <p>核心思路</p>
     * <p>1. N个字符都可以作为第一个字符</p>
     * <p>2. 第一个字符确定之后, N-1 个字符都可以作为第二个字符</p>
     * <p>3. 第二个字符确定之后, N-2 个字符都可以作为第三个字符</p>
     * <p>依次类推...</p>
     */
    private static void findAll(String string){
        findAllRepeat(string.toCharArray(), 0);
    }

    private static void findAllRepeat(char[] chars, int index){
        //=======================base case========================
        if (index == chars.length){
            System.out.println(String.valueOf(chars));
            return;
        }
        boolean[] visited = new boolean[26];
        //======================递归规则===========================
        for (int i = index;i < chars.length;i++){
            if (!visited[chars[i] - 'a']){
                // 0. 如果这个字符没有试过, 那么就可以交换并尝试
                visited[chars[i] - 'a'] = true;
                // 1. 第一个字符确定, 然后确定剩下的字符
                swap(chars, i, index);
                findAllRepeat(chars, index + 1);
                // 2. 将字符串还原
                swap(chars, i, index);
            }
        }
    }

    private static void swap(char[] chars, int first , int second){
        char temp = chars[first];
        chars[first] = chars[second];
        chars[second] = temp;
    }
}
