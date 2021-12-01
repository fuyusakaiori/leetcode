package chapter02;

import utils.ListNode;

import java.util.*;

/**
 * <h2>链表相交</h2>
 * <p>1. 两个链表中都不存在环, 请问是否相交</p>
 * <p>2. 两个链表中不确定是否存在环, 请问是否相交</p>
 */
public class IntersectionList
{
    public static void main(String[] args) {

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
