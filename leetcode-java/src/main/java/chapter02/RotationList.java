package chapter02;

import utils.ListNode;

/**
 * <h2>旋转链表</h2>
 */
public class RotationList {

    /**
     * <h3>思路: </h3>
     * <h3>1. 利用快慢指针找到需要旋转的部分的头结点, 然后记录头结点, 作为新的头结点</h3>
     * <h3>2. 然后继续遍历找到的尾结点, 然后将尾结点连接到旧的头结点, 最后返回新的头结点</h3>
     */
    private static ListNode rotateList(ListNode head, int k){
        if(head == null || head.next == null){
            return head;
        }
        // 1. 获取移动的位置
        int offset = k % getLength(head);
        // 2. 找到第 k 个结点
        ListNode dummy = new ListNode(0, head);
        ListNode fast = head, slow = dummy;
        for(int index = 0;index < offset;index++){
            fast = fast.next;
        }
        // 3. 找到倒数第 k 个结点
        ListNode tail = fast;
        while(fast != null){
            if(fast.next == null){
                tail = fast;
            }
            slow = slow.next;
            fast = fast.next;
        }
        tail.next = dummy.next;
        dummy.next = slow.next;
        slow.next = null;
        return dummy.next;
    }

    private static int getLength(ListNode head){
        int length = 0;
        while(head != null && ++length > 0){
            head = head.next;
        }
        return length;
    }
}
