package chapter06;

import java.util.LinkedList;
import java.util.List;

/**
 * <h2>字符串的全部子序列</h2>
 * <p>问题描述: 找到字符串的全部子序列</p>
 */
public class AllSubString
{
    /**
     * 这道题的解法接近树的递归方式, 是双向的而不是单向的
     */
    public static void main(String[] args)
    {
        findAll("abc");
    }

    /**
     * <p>核心思路</p>
     * <p>1. 每次确定是否选择一个字符</p>
     * <p>2. 如果要选择, 那么就考虑接下来的字符是否选择</p>
     * <p>3。如果不选择, 那么也要考虑接下来的字符是否选择</p>
     * @param string 字符串
     */
    private static void findAll(String string){
        // 注意: 这里需要将字符串转换为字符数组, 因为字符串是不可以修改的, 字符数组可以
        findAllWaste(string.toCharArray(), 0, new LinkedList<>());
    }

    /**
     * 不浪费空间的写法
     */
    private static void findAllNoWaste(char[] chars, int index){
        //==================base case===================
        if (index == chars.length){
            // 只要走到最后一个字符的时候, 显然就是可以将此前的选择的字符串输出
            System.out.println(String.valueOf(chars));
            return;
        }
        //==================递归规则=====================
        // 1. 选择当前的字符, 那么就直接向下传递就行
        findAllNoWaste(chars, index + 1);
        // 2. 不选择当前的字符, 那么就需要将当前的字符置为 0
        // 注意: 这里非常的巧妙, 将字符数组中的字符置为数字 0, 这样字符就消失了, 但是字符数组长度仍然不变
        // 好像是因为 ASCII 码的 0 值为 BLANK NULL
        char temp = chars[index];
        chars[index] = 0;
        findAllNoWaste(chars, index + 1);
        // 3. 记得还原回去
        chars[index] = temp;
    }

    private static void findAllWaste(char[] chars, int index, List<Character> string){
        if (index == chars.length){
            System.out.println(string);
            return;
        }
        // 1. 不选择当前字符的集合, 以及包含当前字符的集合
        // 注: 因为如果不新建集合, 那么在回溯的时候就会造成分支污染
        List<Character> stringNoInclude = new LinkedList<>(string);
        List<Character> stringInclude = new LinkedList<>(string);
        // 2. 选择当前字符
        stringInclude.add(chars[index]);
        findAllWaste(chars, index + 1, stringInclude);
        findAllWaste(chars, index + 1, stringNoInclude);
    }
}
