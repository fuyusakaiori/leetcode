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
     * <p>合并两个有序链表</p>
     * @param list1 第一个链表
     * @param list2 第二个链表
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
        return dummy.next;
    }


    /**
     * 合并两个链表
     * @param left 指定合并的区域的左边界
     * @param right 指定的合并的取悦右边界
     * @return 新的链表
     */
    private static ListNode mergeBetweenLists(ListNode list1, ListNode list2, int left, int right){
        int index = 0;
        ListNode current = list1;
        ListNode leftNode = null;
        ListNode rightNode = null;
        while (current != null && index <= right){
            leftNode = index == left - 1 ? current: leftNode;
            rightNode = index == right ? current: rightNode;
            index++;
            current = current.next;
        }
        current = list2;
        while (current.next != null)
            current = current.next;
        if (leftNode != null)
            leftNode.next = list2;
        current.next = rightNode;

        return list1;
    }

    /**
     * <p>合并 K 个升序链表</p>
     * <p>我之前的想法, 应该是遍历链表, 两两合并</p>
     * <p>但仔细想想, 显然采用归并的方式去两两合并, 时间复杂度是更低的</p>
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
        ListNode list1 = fork(lists, left, mid);
        ListNode list2 = fork(lists, mid + 1, right);
        return merge(list1, list2);
    }

    private static ListNode merge( ListNode list1, ListNode list2){
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
        return dummy.next;
    }
}
