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
     * 和分割链表完全一样
     * @param head
     * @return
     */
    private static ListNode oddEvenList(ListNode head){
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
}
