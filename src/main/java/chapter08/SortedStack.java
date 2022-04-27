package chapter08;

import java.util.LinkedList;

/**
 * <h2>栈排序</h2>
 */
public class SortedStack {

    private final LinkedList<Integer> stack;
    private final LinkedList<Integer> helper;

    public SortedStack() {
        this.stack = new LinkedList<>();
        this.helper = new LinkedList<>();
    }

    public void push(int value){
        while (!stack.isEmpty() && stack.peek() < value){
            helper.push(stack.pop());
        }
        stack.push(value);
        while (!helper.isEmpty()){
            stack.push(helper.pop());
        }
    }

    public int pop(){
        return stack.isEmpty() ? -1: stack.pop();
    }

    public int peek(){
        return stack.isEmpty() ? -1: stack.peek();
    }

    public boolean isEmpty(){
        return stack.isEmpty();
    }
}
