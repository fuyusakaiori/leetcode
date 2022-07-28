package chapter08;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <h2>电话号码的字母组合</h2>
 */
public class LetterCombinations {

    private static final Map<Integer, String> map = new HashMap<Integer, String>(){{
        put(2, "abc");put(3, "def");put(4, "ghi");
        put(5, "jkl");put(6, "mno");put(7, "pqrs");
        put(8, "tuv");put(9, "wxyz");
    }};

    /**
     * <h3>思路: 回溯算法</h3>
     */
    private static List<String> letterCombinations1(String digits){
        List<String> combinations = new LinkedList<>();
        if(digits.length() == 0)
            return combinations;
        dfs(digits, 0, new StringBuilder(), combinations);
        return combinations;
    }

    public static void dfs(String digits, int index, StringBuilder combine, List<String> combinations){
        if(index == digits.length()){
            combinations.add(combine.toString());
            return;
        }
        String target = map.get(digits.charAt(index) - '0');
        for(char ch : target.toCharArray()){
            combine.append(ch);
            dfs(digits, index + 1, combine, combinations);
            combine.delete(combine.length() - 1, combine.length());
        }

    }

    /**
     * <h3>思路: 层序遍历</h3>
     * <h3>注: 这个思路和二叉树中某个题的思路类似</h3>
     */
    private static List<String> letterCombinations2(String digits){
        return null;
    }

}
