package chapter11;

import java.util.LinkedList;

/**
 * <h2>逆序栈</h2>
 * <p>不借助任何额外空间, 将栈逆序, 递归不算额外空间</p>
 */
public class ReverseStack
{
    public static void main(String[] args)
    {
        LinkedList<Integer> stack = new LinkedList<>();
        for (int i = 1;i <= 10;i++){
            stack.push(i);
        }
        reverseStack(stack);
        while (!stack.isEmpty()){
            System.out.print(stack.pop() + "\t");
        }
    }

    /**
     * <p>这个思路也非常的刁钻, 反正用递归实现挺几把离谱的</p>
     */
    private static void reverseStack(LinkedList<Integer> stack){
        if (stack.isEmpty())
            return;
        // 4. 获取到栈底的元素
        int bottom = reverse(stack);
        // 5. 不要急着压栈, 因为现在压进去, 其他元素不是逆序的, 递归调用, 不断地拿到栈底元素
        reverseStack(stack);
        // 6. 栈为空的, 就可把所有栈底元素分别压进去
        stack.push(bottom);
    }

    private static int reverse(LinkedList<Integer> stack){
        // 1.保留栈顶的元素
        int top = stack.pop();
        if (stack.isEmpty())
            return top;
        // 2.保留栈底的元素
        int bottom = reverse(stack);
        // 3.递归回溯的时候, 就先将栈底的元素压入栈, 然后再将栈顶的元素返回
        stack.push(top);
        // 4. 这样栈底的元素就回到栈顶上了
        return bottom;
    }
}
