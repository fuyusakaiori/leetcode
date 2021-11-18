package chapter02;

import utils.ListNode;

import java.util.*;

// 链表相交
public class IntersectionList
{
    public static void main(String[] args) {

    }

    // ==========================链表中是否存在环=============================
    // 解法一: 借助哈希表完成
    private static ListNode listLoopHash(ListNode head){
        if(head == null)
            return null;
        Set<ListNode> set = new HashSet<>();
        while(head != null){
            if(set.contains(head))
                return head;
            set.add(head);
            head = head.next;
        }
        return null;
    }

    // 解法二: 借助快慢指针完成
    private static ListNode listLoop(ListNode head){
        if(head == null || head.next == null)
            return null;
        // 快慢指针: 快指针走两步, 慢指针走一步
        ListNode slow = head.next;
        ListNode fast = head.next.next;
        // 两指针没有相遇之前就一直遍历
        while(slow != fast){
            // 快指针能够走到头, 就证明没有环
            if(fast == null || fast.next == null)
                return null;
            slow = slow.next;
            fast = fast.next.next;
        }

        // 找到入环的结点
        ListNode loop = null;
        fast = head;
        // 快指针和慢指针都各走一步, 相遇的时候就是入环的结点
        while (slow != fast){
            slow = slow.next;
            fast = fast.next;
        }

        return fast;
    }

    //==========================链表是否相交===============================
    // 无环链表
    // 解法一: 借助哈希表完成
    private static ListNode intersectionListNoLoopHash(ListNode headA, ListNode headB){
        if(headA == null || headB == null)
            return null;

        Set<ListNode> set = new HashSet<>();
        while(headA != null){
            set.add(headA);
            headA = headA.next;
        }
        while(headB != null && !set.contains(headB)){
            headB = headB.next;
        }

        return headB;
    }
    // 解法二: 临时变量完成
    private static ListNode intersectionListLoop(ListNode headA, ListNode headB){

        return null;
    }

    // 有环链表:
    // 3 种情况 ① 两个链表具有各自的环, 不相交 ② 两个链表拥有同一个环, 入环结点相同 ③ 两个链表拥有同一个环, 但是入环结点不同
    private static ListNode intersectionLoopListLoop(ListNode headA, ListNode headB){


        return null;
    }

}
