package chapter08;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

/**
 * <h2>栈和队列</h2>
 * <h3>1. 双栈实现队列</h3>
 * <h3>2. 队列实现栈</h3>
 * <h3>注: 栈和队列的设计题偏多, 单调栈和双端队列在其他类型的题目中有较多使用</h3>
 */
public class StackAndQueue {

    /**
     * <h3>思路: 双栈实现队列</h3>
     * <h3>1. 入队的时候就直接进入第一个栈就行</h3>
     * <h3>2. 出队的时候: </h3>
     * <h3>2.1 如果第二个栈中的元素不为空, 就直接出栈</h3>
     * <h3>2.2 如果为空, 那么将第一个栈中元素放入第二栈再出栈</h3>
     */
    private static class MyQueue<T>{
        private final LinkedList<T> firstStack;
        private final LinkedList<T> secondStack;

        public MyQueue() {
            this.firstStack = new LinkedList<>();
            this.secondStack = new LinkedList<>();
        }

        public void offer(T element){
            this.firstStack.push(element);
        }

        public T poll(){
            if (this.secondStack.isEmpty())
                this.transfer();
            return this.secondStack.pop();
        }

        public T peek(){
            if (secondStack.isEmpty())
                transfer();
            return this.secondStack.peek();
        }

        public boolean isEmpty(){
            return this.firstStack.isEmpty()
                           && this.secondStack.isEmpty();
        }

        private void transfer(){
            while (!this.firstStack.isEmpty()){
                this.secondStack.push(this.firstStack.pop());
            }
        }
    }

    /**
     * <h3>思路: 队列实现栈</h3>
     * <h3>1. 双队列可以实现栈</h3>
     * <h3>2. 单队列也是可以实现栈的</h3>
     */
    private static class MyStack1<T>{
        private Queue<T> firstQueue;
        private Queue<T> secondQueue;

        /**
         * <h3>注: 理论上来讲官方题解确实更好, 自己写的那个代码太重复</h3>
         */
        public MyStack1() {
            this.firstQueue = new LinkedList<>();
            this.secondQueue = new LinkedList<>();
        }


        public void push(T element){
            // 1. 先将元素压入第二个队列
            this.secondQueue.offer(element);
            // 2. 然后将第一个队列中的元素重新放入第二个队列, 这就逆序了
            while (!this.firstQueue.isEmpty()){
                this.secondQueue.offer(this.firstQueue.poll());
            }
            // 3. 重新更新第一个队列中的内容, 否则就会出现写成两个分支的情况
            Queue<T> queue = this.firstQueue;
            this.firstQueue = this.secondQueue;
            this.secondQueue = queue;
        }

        public T pop(){
            return this.firstQueue.poll();
        }

        public T top(){
            return this.firstQueue.peek();
        }

        public boolean isEmpty(){
            return this.firstQueue.isEmpty();
        }
    }

    private static class MyStack2<T>{
        private final Queue<T> queue;

        public MyStack2() {
            this.queue = new LinkedList<>();
        }

        public void push(T element){
            this.queue.offer(element);
            while (this.queue.peek() != element){
                this.queue.offer(this.queue.poll());
            }
        }

        public T pop(){
            return this.queue.poll();
        }

        public T top(){
            return this.queue.peek();
        }

        public boolean isEmpty(){
            return this.queue.isEmpty();
        }
    }

}
