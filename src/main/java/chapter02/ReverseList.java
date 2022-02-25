package chapter02;

import utils.ListNode;

/**
 * <h2>反转链表</h2>
 * <h3>1.反转链表</h3>
 * <h3>2.反转链表 II</h3>
 * <h3>3.K个一组反转</h3>
 * <p>万能解法: 集合、数组、栈都是可以完成的</p>
 */
public class ReverseList
{
    public static void main(String[] args)
    {

    }


    private static ListNode reverseAll(ListNode head){
        if(head == null || head.next == null)
            return head;
        ListNode previous = null;
        ListNode current = head;
        ListNode next = head.next;
        while(current != null){
            current.next = previous;
            previous = current;
            current = next;
            next = next != null ? next.next: null;
        }
        return previous;
    }

    /**
     * 反转指定范围内的部分链表
     */
    public static ListNode reverseBetween(ListNode head, int left, int right) {
        int index = 1;
        ListNode dummy = new ListNode(0, head);
        ListNode current = head;
        ListNode previous = null;
        ListNode next = null;
        // 先到达需要反转的位置
        while(index <= left){
            dummy = index == left - 1 ? current: dummy;
            previous = current;
            current = current.next;
            index++;
        }
        // 开始反转
        while(index++ <= right){
            next = current.next;
            current.next = previous;
            previous = current;
            current = next;
        }
        dummy.next.next = current;
        dummy.next = previous;
        return dummy.value != 0 ? head: dummy.next;
    }

    public static ListNode reverseKGroup(ListNode head, int k) {
        ListNode dummy = new ListNode(0, head);
        ListNode tail = dummy;
        ListNode previous = dummy;
        ListNode current = head;
        ListNode next = null;
        while(current != null){
            // 找到需要反转的区域, 也兼具查看剩下的链表是否需要反转的功能
            for(int index = 0;index < k;index++){
                tail = tail.next;
                // 如果尾指针为空, 证明剩余部分不够
                if(tail == null) return dummy.next;
            }
            // 记录下一部分链表的头结点
            next = tail.next;
            // 反转链表
            ListNode[] nodes = reverse(current, tail);
            // 接回原链表
            current = nodes[0];
            tail = nodes[1];
            previous.next = current;
            tail.next = next;
            // 更新尾指针
            previous = tail;
            // 向后移动
            current = next;
        }
        return dummy.next;
    }
    // 反转链表, 并且返回头尾指针
    public static ListNode[] reverse(ListNode head, ListNode tail){
        ListNode previous = null;
        ListNode current = head;
        ListNode next = null;
        ListNode end = tail.next;
        while(current != end){
            next = current.next;
            current.next = previous;
            previous = current;
            current = next;
        }
        return new ListNode[]{tail, head};
    }
}
