package chapter07;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <h2>括号相关的题目</h2>
 * <h3>1. 有效的括号</h3>
 * <h3>2. 括号的生成</h3>
 * <h3>3. 最长有效括号</h3>
 */
public class Brackets
{
    public static void main(String[] args)
    {

    }

    /**
     * <h3>有效的括号</h3>
     * <p>最常规的解法: 栈 + 哈希表</p>
     * <p>注: 不使用哈希表也是可以完成的, 但是那样需要多写几个分支, 但是效率比较高</p>
     * <p>注: 有其余几个比较巧妙的解法, 但是没有太多的可参考性</p>
     */
    private static boolean isValidMap(String str){
        // 0. 栈
        LinkedList<Character> stack = new LinkedList<>();
        // 1. 匿名内部类
        Map<Character, Character> map = new HashMap<Character, Character>()
        {{put('(', ')');put('[', ']');put('{', '}');}};
        for (int index = 0;index < str.length();index++){
            if (map.containsKey(str.charAt(index))) {
                stack.push(str.charAt(index));
            }
            else {
                if(stack.isEmpty() || map.get(stack.pop()) != str.charAt(index)) return false;
            }
        }
        return stack.isEmpty();
    }

    /**
     * <p>1. 如果括号只有一种, 是不需要使用栈结构的</p>
     * <p>2. 只需要判断左右括号数量是否相等就可以</p>
     */
    private static boolean isValid(String str){
        LinkedList<Character> stack = new LinkedList<>();
        for (int index = 0;index < str.length();index++){
            char bracket = str.charAt(index);
            if (bracket == '(' || bracket == '[' || bracket == '{'){
                stack.push(bracket);
            }else{
                if (stack.isEmpty()) return false;
                char left = stack.pop();
                if (left == '(' && bracket != ')') return false;
                if (left == '[' && bracket != ']') return false;
                if (left == '{' && bracket != '}') return false;
            }
        }
        return stack.isEmpty();
    }


    /**
     * <h3>有效括号生成</h3>
     * <p>1. 这个题可以从两种角度去考虑</p>
     * <p>1.1 递归的过程中每次都添加半个括号: 也就说要么添加 (, 要么添加 )</p>
     * <p>1.2 递归的过程中每次都添加一个完整的括号: 也就说 (1)2, 新的括号可以添加在 1或者 2的位置</p>
     * <p>2. 核心: 递归</p>
     * <p>注: 我没有想到这个题要怎样去改动态规划, 我觉得是不行的</p>
     * @param n 括号数量
     * @return 形成的有效括号
     */
    private static List<String> generateParenthesis(int n){
        List<String> brackets = new LinkedList<>();
        // dfs(brackets, n, n, "");
        dfs(brackets, n, new StringBuilder());
        return brackets;
    }

    /**
     * <p>1. 每次可以选择添加左括号或者右括号</p>
     * <p>2. 但是为了直接保证最后形成的括号是有效的, 必须对添加的过程进行限制, 也就是剪枝</p>
     * <p>2.1. 左右括号数量相同的时候, 只能够添加左括号, 要是先添加右括号肯定是无效的</p>
     * <p>2.2  左括号数量少于右括号的时候, 可以添加左括号, 也可以添加右括号</p>
     * <p>注: 使用 String 拼接的好处在于不用手动删除, 但是效率可能相对较低</p>
     * <p>注: 同类型的括号都是可以考虑以半个括号为单位进行操作</p>
     * @param brackets 有效的括号集合
     * @param left 剩余的左括号数量
     * @param right 剩余的右括号数量
     * @param bracket 有效括号
     */
    private static void dfs(List<String> brackets, int left, int right, String bracket){
        // 0. 如果括号数量已经使用完, 就直接将整个有效的括号添加进入集合中
        if (left == 0 && right == 0) brackets.add(bracket);
        // 1. 如果括号数量没有使用完, 那么继续添加
        if (left == right){
            // 2. 如果左右括号数量相等的时候, 添加左括号
            dfs(brackets, left - 1, right, bracket + "(");
        }else{
            // 3. 如果左括号数量还没有使用完, 可以优先添加左括号
            if (left > 0) dfs(brackets, left - 1, right, bracket + "(");
            if (right > 0) dfs(brackets, left, right - 1, bracket + ")");
        }
    }

    private static void dfs(List<String> brackets, int n, StringBuilder bracket){
        // 0. 完整的括号使用完毕, 就直接将整个括号全部添加到集合中
        if (n == 0){
            brackets.add(bracket.toString());
            // 注: 记得返回
            return;
        }
        // 1. 如果有效括号长度为 0, 那么直接压入一个括号
        if (bracket.length() == 0){
            dfs(brackets, n - 1, bracket.append("()"));
        }else{
            // 2. 如果长度不为 0, 就需要考虑括号放置的位置
            // 2.1 每次都在右括号的外面添加
            int index;
            for (index = bracket.length() - 1;bracket.charAt(index) != '(';index--){
                dfs(brackets, n - 1, bracket.insert(index + 1, "()"));
                bracket.delete(index + 1, index + 3);
            }
            // 2.2 最后需要在右括号的里面添加
            dfs(brackets, n - 1, bracket.insert(index + 1, "()"));
            bracket.delete(index + 1, index + 3);
        }
    }

    /**
     * <h3>最长有效括号</h3>
     * <p>核心: 栈 + 遇到左括号直接入栈 + 遇到右括号出栈</p>
     * <p>问题: 如何判断几个有效括号是否是相连的</p>
     * <p>1. 遇到左括号的时候将左括号的索引入栈, 遇到右括号的时候将右括号的索引减去左括号的索引</p>
     * <p>2. 索引相减的方式就可以避免去判断有效括号是否相连的问题, 也不需要循环判断</p>
     * <p>2.1 左括号的索引可以直接区分是否相连: ()((()(), 出栈的过程肯定不会涉及到最前面的括号</p>
     * <p>2.2 右括号由于无法入栈, 所以没有办法分割有效括号, 所以我们需要将最后一个没有匹配的右括号的索引入栈</p>
     * <p>3. 这个最后一个没有匹配的右括号索引实际上就相当于我之前记录的有效括号开始的位置</p>
     * <p>4. 开始的时候需要将提前压入 -1 索引作为没有匹配的右括号索引</p>
     * <h3>两种做法: </h3>
     * <p>1. 栈结构</p>
     * <p>2. 左右括号的数量</p>
     * <p>这两种基础解法都是可以解决这个题的</p>
     * @param str 括号
     * @return 有效括号的最长长度
     */
    private static int longestValidBracket(String str){
        // 0. 准备栈结构
        LinkedList<Integer> stack = new LinkedList<>();
        // 1. 提前压入没有匹配的右括号索引
        stack.push(-1);
        // 2. 开始遍历括号, 计算有效的括号长度
        int maxLength = 0;
        for (int index = 0;index < str.length();index++){
            // 3. 遇见左括号直接入栈, 压入左括号的索引
            if (str.charAt(index) == '('){
                stack.push(index);
            }else{
                // 4. 遇到右括号就开始出栈
                stack.pop();
                // 5. 然后用右括号索引减去栈顶元素的索引, 就可以刚好得到这个有效括号的长度
                if (!stack.isEmpty()){
                    maxLength = Math.max(maxLength, index - stack.peek());
                }else{
                    // 6. 如果出栈之后发现栈为空, 这就意味最后一个没有匹配的右括号需要更新, 直接入栈
                    stack.push(index);
                }
            }
        }
        return maxLength;
    }

    /**
     * <p>1. 括号的数量通常会经历这个过程: 左括号 > 右括号 => 左括号 = 右括号 => 左括号 < 右括号</p>
     * <p>2. 左括号 = 右括号的时候, 计算有效长度; 左括号 < 右括号, 证明有多余的无效的右括号, 将左右括号全部重新置为0</p>
     * <p>3. 上述是没有左括号 > 右括号的情况的, 因为从左到右遍历是判断不出来的, 所以反过来遍历</p>
     */
    private static int longestValidBracket(String str, Object ...args){
        int maxLength = 0;
        // 0. 记录左右括号的数量
        int left = 0, right = 0;
        // 1. 从左向右遍历
        for (int index = 0;index < str.length();index++){
            // 2. 统计左右括号的数量
            left += str.charAt(index) == '(' ? 1: 0;
            right += str.charAt(index) == ')' ? 1: 0;
            // 3. 判断左右括号数量是否相等
            if (left == right){
                maxLength = Math.max(maxLength, right + left);
            }else if (left < right){
                left = right = 0;
            }
        }
        left = right = 0;
        // 4. 从右向左遍历
        for (int index = str.length() - 1;index >= 0;index--){
            left += str.charAt(index) == '(' ? 1: 0;
            right += str.charAt(index) == ')' ? 1: 0;
            if (left == right){
                maxLength = Math.max(maxLength, right + left);
            }else if (left > right){
                left = right = 0;
            }
        }
        return maxLength;
    }
}
