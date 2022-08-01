package chapter02;

import utils.ListNode;

/**
 * <h2>反转链表</h2>
 * <h3>1.反转链表</h3>
 * <h3>2.反转链表 II</h3>
 * <h3>3.两两交换链表中的结点</h3>
 * <h3>4.K个一组反转</h3>
 * <p>万能解法: 集合、数组、栈都是可以完成的</p>
 */
public class ReverseList {

    /**
     * <h3>思路: 反转链表 迭代</h3>
     */
    private static ListNode reverseList1(ListNode head){
        ListNode previous = null, current = head, next = null;
        while (current != null){
            next = current.next;
            current.next = previous;
            previous = current;
            current = next;
        }
        return previous;
    }

    /**
     * <h3>思路: 反转链表 递归</h3>
     */
    private static ListNode reverseList2(ListNode head){
        if (head == null || head.next == null){
            return head;
        }
        ListNode tail = reverseList2(head.next);
        head.next.next = head;
        head.next = null;
        return tail;
    }

    /**
     * <h3>思路: 反转链表 II</h3>
     */
    public static ListNode reverseBetween(ListNode head, int left, int right) {
       ListNode dummy = new ListNode(0, head);
       ListNode previous = dummy;
        // 1. 找到左边界
        for (int index = 0;index < left - 1;index++){
            previous = previous.next;
        }
        // 2. 找到右边界
        ListNode rightNode = previous;
        for (int index = 0;index < right - left + 1;index++){
            rightNode = rightNode.next;
        }
        // 3. 切割链表
        ListNode leftNode = previous.next;
        ListNode next = rightNode.next;
        previous.next = null;
        rightNode.next = null;
        // 4. 反转链表
        reverseList1(leftNode);
        previous.next = rightNode;
        leftNode.next = next;
        return dummy.next;
    }

    /**
     * <h3>思路: K个一组反转链表 <=> 两两交换链表中的结点</h3>
     */
    public static ListNode reverseKGroup(ListNode head, int k) {
        ListNode current = head;
        ListNode next = null;
        ListNode dummy = new ListNode(0, head);
        ListNode previous = dummy, tail = dummy;
        while (current != null){
            for (int index = 0;index < k;index++){
                tail = tail.next;
                if (tail == null) return dummy.next;
            }
            next = tail.next;
            previous.next = null;
            tail.next = null;
            reverseList1(current);
            previous.next = tail;
            current.next = next;
            previous = tail = current;
            current = next;
        }
        return dummy.next;
    }
}
