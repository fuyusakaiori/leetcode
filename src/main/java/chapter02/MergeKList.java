package chapter02;

import utils.ListNode;

import java.util.Comparator;
import java.util.PriorityQueue;

// 合并 K 个有序链表
public class MergeKList
{
    public static void main(String[] args)
    {
        ListNode l1 = null;
        ListNode l2 = new ListNode(-1);
        ListNode node1 = new ListNode(5);
        ListNode node2 = new ListNode(11);
        l2.next = node1;
        node1.next = node2;
        ListNode l3 = null;
        ListNode l4 = new ListNode(6);
        l4.next = new ListNode(10);
        ListNode[] lists = {l1, l2, l3, l4};

        mergeKLists(lists);

        PriorityQueue<ListNode> queue = new PriorityQueue<>((o1, o2) -> o1.value - o2.value);
    }

    public static ListNode mergeKLists(ListNode[] lists) {
        if(lists.length == 0)
            return null;
        fork(lists, 0, lists.length - 1);
        return lists[0];
    }

    public static void fork(ListNode[] lists, int left, int right){
        if(left == right)
            return;

        int mid = left + ((right - left) >> 1);
        fork(lists, left, mid);
        fork(lists, mid + 1, right);
        merge(lists, left, mid, right);
    }

    public static void merge(ListNode[] lists, int left, int mid, int right){

        ListNode l1 = lists[left];
        ListNode l2 = lists[right];
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        while(l1 != null && l2 != null){
            if(l1.value < l2.value){
                current.next = l1;
                l1 = l1.next;
            }else{
                current.next = l2;
                l2 = l2.next;
            }
            current = current.next;
        }
        if(l1 != null)current.next = l1;
        if(l2 != null)current.next = l2;
        lists[left] = dummy.next;
        lists[right] = dummy.next;
    }
}
