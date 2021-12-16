package chapter08;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * <h2>滑动窗口</h2>
 * <p>结构内容: </p>
 * <p>1. 核心: 采用双端队列维护可能成为最大值的所有元素, 双端队列中元素是单调递减的</p>
 * <p>2. 滑动窗口采用左指针和右指针维护窗口的大小</p>
 * <p>3.1 每次右指针向右移动的时候, 新进入窗口中的元素就会进入双端队列</p>
 * <p>3.2 每次左指针向右移动的时候, 双端队列的头元素就会出队</p>
 * <p>4. 进入双端队列的元素如果比队尾的元素大, 那么就需要将队尾的元素弹出, 直到队尾元素大于入队元素</p>
 * <p>注: 滑动窗口可以处理很多问题, 这里是针对某个题来描述的</p>
 * <p>注: 实际做题的时候不需要完整的写这个结构, 本质就是个双端队列嘛, 直接用就可以</p>
 */
public class SlidingWindow
{
    // 1. 维护滑动窗口的左右边界
    private int left;
    private int right;
    // 2. 存放传入的数组
    private int[] numbers;
    // 3. 双端队列
    private Deque<Integer> deque;

    public SlidingWindow(int[] numbers) {
        // 初始化数组
        this.numbers = numbers;
        // 初始化双端队列
        this.deque = new ArrayDeque<>();

    }

    /**
     * <p>扩大窗口, 也就是将数组中的元素纳入窗口中</p>
     */
    public void expand(){
        if (right == numbers.length) return;
        // 0. 弹出所有比入队元素小的元素
        while (!deque.isEmpty() && numbers[deque.peekLast()] <= numbers[right]){
            deque.pollLast();
        }
        // 1. 入队元素
        deque.offer(right);
        // 2. 移动右边界
        right++;
    }

    /**
     * <p>缩小窗口, 也就是将窗口中左侧的元素从窗口移出去</p>
     */
    public void shrink(){
        // 0. 如果左右边界都相等了, 那么肯定证明窗口中是没有元素的
        if (left == right) return;
        // 1. 然后检查左边界的索引是否和队列头的索引相同, 如果相同那么就直接弹出, 如果不相同就不弹出
        if (!deque.isEmpty() && left == deque.peekFirst())
            deque.pollFirst();
        left++;
    }

    /**
     * <p>获取窗口中的最大值</p>
     * @return 最大值
     */
    public int getWindowMax(){
        // 0. 如果队列不为空, 或者说窗口的左右边界不相等, 那么就是有最大值的
        if (!deque.isEmpty()) return numbers[deque.peekFirst()];
        return -1;
    }

    public static void main(String[] args)
    {
        int[] numbers = {6, 5, 4, 3, 5, 7};
        SlidingWindow window = new SlidingWindow(numbers);
        // 初始值
        System.out.println(window.getWindowMax());
        // 扩大窗口
        window.expand();
        window.expand();
        window.expand();
        window.expand();
        System.out.println(window.getWindowMax());
        // 缩小窗口
        window.shrink();
        window.shrink();
        window.shrink();
        System.out.println(window.getWindowMax());
        window.expand();
        window.expand();
        System.out.println(window.getWindowMax());
    }
}
