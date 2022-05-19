package chapter06;

import java.util.*;

/**
 * <h2>波兰表达式</h2>
 * <h3>1. 中缀表达式求值</h3>
 * <h3>2. 逆波兰表达式求值</h3>
 * <h3>3. 中缀表达式转逆波兰表达式</h3>
 * <h3>4. 中缀表达式转波兰表达式</h3>
 * <h3>注: 逆波兰表达式就是后缀表达式, 波兰表达式就是前缀表达式</h3>
 */
public class PolishNotation {

    /**
     * <h3>思路: 逆波兰表达式求值</h3>
     */
    private static int evalRPN(String[] tokens){
        LinkedList<Integer> stack = new LinkedList<>();
        Set<String> operations = new HashSet<>(Arrays.asList("+", "-", "/", "*"));
        for (String token : tokens) {
            stack.push(operations.contains(token)
                               ? calculate(stack.pop(), stack.pop(), token)
                               : Integer.parseInt(token));
        }
        return stack.pop();
    }

    private static int calculate(int first, int second, String operation){
        switch (operation){
            case "+": return first + second;
            case "-": return second - first;
            case "/": return second / first;
            case "*": return first * second;
        }
        return 0;
    }

    /**
     * <h3>思路: 中缀表达式转逆波兰表达式</h3>
     */
    private static String[] toRPN(String expression){
        List<String> rpn = new LinkedList<>();
        LinkedList<String> operationStack = new LinkedList<>();
        Set<String> operationSet = new HashSet<>(Arrays.asList("+", "-", "*", "/", "(", ")"));
        char[] chars = expression.toCharArray();
        for (int index = 0;index < chars.length;){
            String token = String.valueOf(chars[index]);
            // 1. 如果是运算符的话就需要进入相应的判断
            if (operationSet.contains(token)){
                getOperation(rpn, operationStack, token);
                index++;
            }else{
                // 2. 如果是数字那么进入相应的判断, 并且返回索引
                index = getNumber(expression, rpn, operationSet, chars, index);
            }
        }
        return rpn.toArray(new String[0]);
    }

    /**
     * <h3>向表达式中添加数字</h3>
     */
    private static int getNumber(String expression, List<String> rpn,
                                 Set<String> operationSet, char[] chars, int index) {
        int start = index;
        for (;index < chars.length;index++){
            // 1. 不断向后遍历直到遍历到运算符终止
            if (operationSet.contains(String.valueOf(chars[index])))
                break;
        }
        // 2. 将相应的字符串截取出来, 放入整数中就可以了, 这样做是因为可能出现多位数
        rpn.add(expression.substring(start, index));
        // 3. 将终止的索引返回, 避免下次重复判断
        return index;
    }

    /**
     * <h3>向表达式中添加运算符</h3>
     */
    private static void getOperation(List<String> rpn, LinkedList<String> operationStack, String token) {
        // 1. 如果是左括号就压入运算符栈中
        if ("(".equals(token)){
            operationStack.push(token);
        }else if (")".equals(token)){
            // 2. 如果是右括号就需要将运算符栈中的运算符出栈, 直到碰见左括号
            while (!"(".equals(operationStack.peek())){
                rpn.add(operationStack.pop());
            }
            // 注: 最后记得把左括号弹出
            operationStack.pop();
        }else{
            // 3. 如果是四则运算符就需要将栈顶元素和入栈元素优先级进行比较:
            // 3.1 如果入栈元素优先级小于等于栈顶元素就直接入栈
            // 3.2 如果入栈元素优先级大于栈顶元素, 那么就将栈顶元素出栈直到满足小于等于的条件后再入栈
            while (!operationStack.isEmpty() && compareOperation(token, operationStack)){
                rpn.add(operationStack.pop());
            }
            operationStack.push(token);
        }
    }

    /**
     * <h3>比较运算符优先级</h3>
     */
    private static boolean compareOperation(String token, LinkedList<String> operationStack){
        String top = operationStack.peek();
        int first = "+".equals(token) || "-".equals(token) ? 0: 1;
        int second = "+".equals(top) || "-".equals(top) ? 0: 1;
        return first > second;
    }

}
