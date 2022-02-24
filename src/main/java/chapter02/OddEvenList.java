package chapter02;

import utils.ListNode;

/**
 * <h2>奇偶链表</h2>
 * <p>问题描述: 让结点编号为奇数的指向奇数, 为偶数的指向偶数</p>
 */
public class OddEvenList
{
    public static void main(String[] args)
    {

    }

    /**
     * <h3>思路: 采用双指针也就是分割链表的解法</h3>
     */
    private static ListNode oddEvenListIndex(ListNode head){
        if (head == null || head.next == null)
            return head;
        ListNode current = head;
        ListNode next = head.next;
        ListNode oDummy = new ListNode(0, head), oCurrent = oDummy;
        ListNode eDummy = new ListNode(0, head.next), eCurrent = eDummy;
        while(current != null){
            current.next = null;
            if (current.value % 2 != 0){
                oCurrent.next = current;
                oCurrent = oCurrent.next;
            }else {
                eCurrent.next = current;
                eCurrent = eCurrent.next;
            }
            current = next;
            next = next != null ? next.next: null;
        }
        oCurrent.next = eDummy.next;
        return oDummy.next;
    }

    /**
     * <h3>思路: 模拟</h3>
     */
    public static ListNode oddEvenList(ListNode head){
        if(head == null || head.next == null) return head;
        ListNode oh = head;
        ListNode eh = head.next;
        ListNode even = eh;
        while(eh != null){
            if(oh.next.next == null) break;
            oh.next = oh.next.next;
            eh.next = eh.next.next;
            oh = oh.next;
            eh = oh.next;
        }
        oh.next = even;
        return head;
    }
}
