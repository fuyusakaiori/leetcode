package chapter02;

import utils.ListNode;

/**
 * <h2>旋转链表</h2>
 */
public class RotationList
{
    public static void main(String[] args)
    {

    }

    /**
     * <h3>思路: </h3>
     * <h3>1. 利用快慢指针找到需要旋转的部分的头结点, 然后记录头结点, 作为新的头结点</h3>
     * <h3>2. 然后继续遍历找到的尾结点, 然后将尾结点连接到旧的头结点, 最后返回新的头结点</h3>
     */
    private static ListNode rotateList(ListNode head, int k){
        if (head == null || head.next == null)
            return head;
        // 注: 让慢指针从哑元开始就可以找到第一个中点
        ListNode dummy = new ListNode(0, head);
        ListNode slow = dummy;
        ListNode fast = head;
        // 注: K 是可以超过链表长度的, 需要模运算减小 K
        if (k % getLength(head) == 0) return head;
        for(int index = 0;index < k;index++){
            fast = fast.next;
        }
        while (fast != null){
            fast = fast.next;
            slow = slow.next;
        }
        ListNode previous = slow;
        while (slow.next != null){
            slow = slow.next;
        }
        ListNode nh = previous.next;
        previous.next = null;
        ListNode tail = slow;
        tail.next = head;

        return nh;
    }

    private static int getLength(ListNode head){
        int length = 0;
        while(head != null && ++length > 0){
            head = head.next;
        }
        return length;
    }
}
