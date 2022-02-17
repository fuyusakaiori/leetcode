package chapter02;

import utils.ListNode;

/**
 * <h2>合并链表</h2>
 * <p>1. 合并两个有序链表</p>
 * <p>2. 合并两个普通的链表</p>
 * <p>3. 合并 K 个升序链表</p>
 */
public class MergeLists
{
    public static void main(String[] args)
    {

    }

    /**
     * <h3>思路: 哑元 + 比大小</h3>
     */
    private static ListNode mergeTwoSortedLists(ListNode list1, ListNode list2){
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        while (list1 != null && list2 != null){
            if (list1.value < list2.value){
                current.next = list1;
                list1 = list1.next;
            }else{
                current.next = list2;
                list2 = list2.next;
            }
            current = current.next;
        }
        if (list1 != null) current.next = list1;
        if (list2 != null) current.next = list2;
        return dummy.next;
    }


    /**
     * <h3>思路: 找到合并链表的起始位置和终止位置就行</h3>
     */
    private static ListNode mergeBetweenLists(ListNode list1, ListNode list2, int left, int right){
        ListNode head = list1;
        ListNode previous = null;
        ListNode next = null;
        int index = 0;
        // 先找到合并链表的起始位置
        while(index < left - 1){
            list1 = list1.next;
            index++;
        }
        // 记录起始结点
        previous = list1;
        // 找到合并链表为结束位置
        while(index < right + 1){
            list1 = list1.next;
            index++;
        }
        // 找到第二个链表的尾部
        while(list2.next != null) list2 = list2.next;
        next = list1;
        // 开始合并链表
        previous.next = list2;
        list2.next = next;
        return head;
    }

    /**
     * <h3> 思路: </3>
     * <h3>1. 遍历链表数组, 两个链表先合并得到排序的链表, 然后生成链表再和之后的链表合并, 时间复杂度为 O(n^2)</h3>
     * <h3>2. 遍历链表数组, 但是两两合并, 合并的结果再两两合并, 类似于归并排序</h3>
     * @param lists 链表数组
     * @return 新的链表
     */
    private static ListNode mergeKLists(ListNode[] lists){

        return fork(lists, 0, lists.length - 1);
    }

    private static ListNode fork(ListNode[] lists, int left, int right){
        if (left == right)
            return lists[left];
        int mid = left + ((right - left) >> 1);
        ListNode leftList = fork(lists, left, mid);
        ListNode rightList = fork(lists, mid + 1, right);
        return mergeTwoSortedLists(leftList, rightList);
    }


}
