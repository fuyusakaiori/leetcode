package chapter01;

/**
 * <h2>反转链表</h2>
 * <h3>1. 反转链表</h3>
 * <h3>2. 反转链表 II</h3>
 * <h3>3. K 个一组反转链表</h3>
 * <h3>4. 两两交换链表中的结点</h3>
 */
public class ReverseList
{
    public static void main(String[] args)
    {

    }

    /**
     * <h3>思路: 三指针反转链表</h3>
     */
    public static ListNode reverseList2(ListNode head) {
        ListNode previous = null;
        ListNode current = head;
        ListNode next = null;
        while(current != null){
            next = current.next;
            current.next = previous;
            previous = current;
            current = next;
        }
        return previous;
    }

    /**
     * <h3>思路: 找到需要反转起始位置, 然后开始反转</h3>
     */
    private static ListNode reverseListBetween(ListNode head, int left, int right){
        int index = 1;
        ListNode previous = null;
        ListNode current = head;
        ListNode next = null;
        ListNode dummy = new ListNode(0);
        // 先找到需要反转的位置
        // 注: 这里即使判断条件为 false, 也是会 ++ 的
        while (index <= left){
            dummy = index == left - 1 ? current: dummy;
            current = current.next;
            previous = current;
            index++;
        }
        // 开始反转
        while (index <= right){
            next = current.next;
            current.next = previous;
            previous = current;
            current = next;
            index++;
        }
        return dummy.val != 0 ? head: dummy;
    }

    /**
     * <h3>思路: </h3>
     * <h4>1. 要么先计算出需要反转的次数, 要么就是在反转的过程中去判断后续的长度是否需要反转</h4>
     * <h4>2. 每个区域反转之后, 就让反转链表的头指针接到前驱指针, 然后将尾指针指向后驱指针</h4>
     * <h4>注: 原先的写法比较笨拙, 但是可以用, 后面采用官方题解作为改进</h4>
     */
    private static ListNode reverseKGroup(ListNode head, int k){
        ListNode dummy = new ListNode(0, head);
        ListNode tail = dummy;
        ListNode next = null;
        ListNode previous = dummy;
        while (head != null){
            for(int index = 0;index <= 9;index++){
                tail = tail.next;
                if (tail == null) return dummy.next;
            }
            next = tail.next;
            ListNode[] nodes = reverse(head, tail);
            // 注: 必须更新尾指针, 否则会有问题
            head = nodes[0];
            tail = nodes[1];
            // 接上链表
            previous.next = head;
            tail.next = next;
            // 更新前驱结点
            previous = tail;
            // 向前移动
            head = next;
        }

        return dummy.next;
    }

    private static ListNode[] reverse(ListNode head, ListNode tail){
        ListNode previous = null;
        ListNode current = head;
        ListNode next = null;
        // 记录终止结点, 就是尾结点的下一个结点
        ListNode end = tail.next;
        // 注: 不能够使用空值作为循环结束的条件, 因为尾结点之后还有结点, 所以当前结点不可能为空
        while (current != end){
            next = current.next;
            current.next = previous;
            previous = current;
            current = next;
        }
        return new ListNode[]{tail, head};
    }

    /**
     * <h3>思路: </h3>
     * <h4>1. 直接仿照 K 个一组反转来实现就可以 (k = 2)</h4>
     * <h4>2. 直接利用两两反转的特性</h4>
     */
    private static ListNode swapParis(ListNode head){
        ListNode dummy = new ListNode(0);
        ListNode previous = dummy;
        ListNode current = head;
        ListNode next = null;
        while (current != null && current.next != null){
            next = current.next;
            current.next = current.next.next;
            next.next = current;
            previous.next = next;

            previous = current;
            current = current.next;
        }
        return dummy.next;
    }

}
