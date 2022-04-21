package chapter08;

import java.util.LinkedList;

/**
 * <h2>最小栈</h2>
 * <h3>这个题的核心思路在于栈中既要按照原有顺序保存元素, 又要记录最小值</h3>
 * <h3>1. 所以最简单的方式就是使用两个栈去解决, 一个记录当前值, 另一个记录最小值, 不过要注意协调内容</h3>
 * <h3>2. 另外一种方式就是在同一个栈中保存数组, 从而同时记录当前元素和最小值</h3>
 * <h3>3. 第三种方式就是利用链表实现, 从而避免占用任何额外空间</h3>
 */
public class MinStack{

    /**
     * <h3>思路: 双栈实现</h3>
     */
    private static class MinStack1<T extends Comparable<T>>{
        private final LinkedList<T> stack;
        private final LinkedList<T> helper;

        public MinStack1() {
            stack = new LinkedList<>();
            helper = new LinkedList<>();
        }

        public void push(T element){
            // 1. 辅助栈维护所有可能成为最小值的元素
            if (helper.isEmpty()){
                helper.push(element);
            }else{
                if (helper.peek().compareTo(element) >= 0)
                    helper.push(element);
            }
            // 2. 无论如何都会将新元素放入栈中
            stack.push(element);
        }

        public T pop(){
            // 1. 无论如何都是要将原栈中的数据出掉的
            T element = stack.pop();
            // 2. 判断弹出的元素是否为最小值
            if (helper.peek() == element)
                helper.pop();
            return element;
        }

        public T peek(){
            return stack.peek();
        }

        public T getMin(){
            return helper.peek();
        }
    }

    /**
     * <h3>思路: 单栈实现</h3>
     */
    @SuppressWarnings("unchecked")
    private static class MinStack2<T extends Comparable<T>>{
        private final LinkedList<T[]> stack;

        public MinStack2() {
            this.stack = new LinkedList<>();
        }

        public void push(T element){
            T minElement = element;
            if (!stack.isEmpty() && stack.peek()[1].compareTo(element) <= 0)
                minElement = stack.peek()[1];
            stack.push((T[])new Object[]{element, minElement});
        }

        public T pop(){
            return !stack.isEmpty() ? stack.pop()[0] : null;
        }

        public T top(){
            return !stack.isEmpty() ? stack.peek()[0] : null;
        }

        public T getMin(){
            return !stack.isEmpty() ? stack.peek()[1] : null;
        }
    }

    /**
     * <h3>思路: 不使用额外空间实现</h3>
     * <h3>本质就是单栈实现的思路, 不过采用链表实现就没有直接借助额外空间</h3>
     */
    private static class MinStack3<T extends Comparable<T>>{

        private Node<T> head;

        public void push(T element){
            if (head == null) {
                head = new Node<>(element, element);
            }else{
                head = new Node<>(element,
                        head.minElement.compareTo(element) < 0
                                ? head.minElement: element, head);
            }
        }

        public T pop(){
            Node<T> node = head;
            head = head.next;
            return node.element;
        }

        public T peek(){
            return head.element;
        }

        public T getMin(){
            return head.minElement;
        }

        /**
         * <h3>链表结点</h3>
         */
        private static class Node<T>{
            private final T element;
            private final T minElement;
            private Node<T> next;

            public Node(T element, T minElement) {
                this.element = element;
                this.minElement = minElement;
            }

            public Node(T element, T minElement, Node<T> next)
            {
                this.element = element;
                this.minElement = minElement;
                this.next = next;
            }
        }
    }

}
