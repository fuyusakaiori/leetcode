package chapter02;

import utils.ListNode;

/**
 * <h2>反转链表</h2>
 * <p>1. 反转整个链表</p>
 * <p>2. 指定范围反转</p>
 * <p>3. K个一组反转</p>
 * <p>万能解法: 集合、数组、栈都是可以完成的</p>
 */
public class ReverseList
{
    public static void main(String[] args)
    {

    }


    /**
     * 反转整个链表
     * <p>1. 可以使用递归的形式完成, 不过比较麻烦, 而且占用额外空间（系统栈）</p>
     * <p>2. 可以使用头插法或者栈完成, 不过也是需要使用额外空间的</p>
     * <p>3. 真正的不借助额外空间的做法实际上是原地反转</p>
     */
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
    private static ListNode reversePartition(ListNode head, int left, int right){
        // 0. 空链表和元素唯一的链表是没有必要反转的
        if(head == null || head.next == null)
            return head;
        // 1. 左右两个边界都是从一开始计数的, 不是从零开始的
        int index = 1;
        // 2. 反转链表常用的常量
        ListNode previous = null;
        ListNode current = head;
        ListNode next = head.next;
        // 3. 使用哑元避免之后的越界问题
        // 3.1 主要是有可能反转的起始结点就是头结点, 而头结点之前没有任何结点
        // 3.2 如果使用空间点之后就有各种各样的空指针异常, 所以使用哑元避免这种问题
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode leftNode = dummy;
        ListNode rightNode = null;
        while(index <= right){
            // 4。记录左边界的前一个结点
            leftNode = index == left - 1 ? current: leftNode;
            // 5. 记录右边界
            rightNode = index == right ? current: rightNode;
            // 6. 到达左边界之后才开始反转
            if(index > left)
                current.next = previous;
            previous = current;
            current = next;
            next = next != null ? next.next : null;
            index++;
        }
        leftNode.next.next = current;
        leftNode.next = rightNode;
        return dummy.next;
    }

    /**
     * <p>K 个一组反转链表</p>
     * <p>1. 没有任何优化的思路比较慢, 但是核心肯定是这样的</p>
     */
    private static ListNode reverse(ListNode head, int k){
        if(head == null || head.next == null)
            return head;
        int index = 0;
        int length = getLength(head);
        int count = length / k;
        ListNode previous = null;
        ListNode current = head;
        ListNode next = head.next;
        ListNode tail = new ListNode(-1);
        ListNode temp = null;
        while(count > 0){
            if(index != k){
                temp = index == 0 ? current: temp;
                current.next = previous;
                previous = current;
                current = next;
                next = next != null ? next.next: null;
                index++;
            }else{
                head = tail.value == -1 ? previous: head;
                tail.next = previous;
                tail = temp;
                index = 0;
                count--;
            }
        }
        tail.next = current;
        return head;
    }

    private static int getLength(ListNode head) {
        int length = 0;
        ListNode current = head;
        while(current != null && ++length > 0)current = current.next;
        return length;
    }
}
