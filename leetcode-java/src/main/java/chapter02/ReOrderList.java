package chapter02;

import utils.ListNode;

/**
 * <h2>重排链表</h2>
 * <p>自己去 LeetCode 上看吧</p>
 */
public class ReOrderList
{
    public static void main(String[] args)
    {

    }


    /**
     * <h3>思路: 找中点 + 逆序 + 衔接</h3>
     * <h3>注: 几乎考察了链表常用的几个技巧</h3>
     */
    private static ListNode reorderList(ListNode head){
        ListNode fast = head;
        ListNode slow = head;
        // 找中点
        while(fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }
        // 开始逆序
        ListNode previous = null;
        ListNode current = slow;
        ListNode next = null;
        while(current != null){
            next = current.next;
            current.next = previous;
            previous = current;
            current = next;
        }
        // 开始衔接
        slow = previous;
        fast = head;
        while(slow.next != null){
            next = slow.next;
            slow.next = fast.next;
            fast.next = slow;
            slow = next;
            fast = fast.next.next;
        }
        return head;
    }
}
