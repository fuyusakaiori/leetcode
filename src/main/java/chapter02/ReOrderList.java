package chapter02;

import utils.ListNode;

/**
 * <h2>重排链表</h2>
 * <p>自己去 LeetCode 上看吧</p>
 */
public class ReOrderList
{
    /**
     * <p>1. 这道题的核心思路其实已经用到了链表好几个常用的技巧</p>
     * <p>2. 先使用快慢指针找到中点, 然后对后半部分逆序, 然后开始修改引用</p>
     */
    public static void main(String[] args)
    {

    }


    /**
     * <p>排序链表</p>
     * @param head 链表
     * @return 重排之后的链表
     */
    private static ListNode reorder(ListNode head){
        ListNode slow = head;
        ListNode fast = head;
        // 1. 确定中点
        while (fast != null && fast.next != null){
            fast = fast.next.next;
            slow = slow.next;
        }
        // 2. 逆序后半部分
        ListNode previous = null;
        ListNode current = slow;
        ListNode next = slow.next;
        while (current != null){
            current.next = previous;
            previous = current;
            current = next;
            next = next != null ? next.next: null;
        }

        // 3. 开始修改引用
        ListNode first = head;
        ListNode second = previous;
        next = second.next;
        while (next != null){
            second.next = first.next;
            first.next = second;
            second = second.next;
            first = first.next.next;
            next = next.next;
        }
        return head;
    }
}
