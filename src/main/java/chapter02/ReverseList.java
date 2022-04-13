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
     * <h3>思路: 反转链表</h3>
     */
    private static ListNode reverse(ListNode head){
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
     * <h3>思路: 反转链表 II</h3>
     */
    public static ListNode reverseBetween(ListNode head, int left, int right) {
        int index = 1;
        // 注: 这个变量的目的是为了记录左边界的前驱结点
        ListNode dummy = new ListNode(0, head);
        ListNode previous = null, current = head, next = null;
        // 1. 找到左边界之后就可以开始反转链表了
        while (index <= left){
            // 2. 记录左边界的前驱结点, 这么处理的原因是可能恰好反转的部分是从头开始的
            dummy = index == left - 1 ? current: dummy;
            // 3. 反转链表的前驱结点会移动到左边界
            previous = current;
            current = current.next;
            index++;
        }
        // 4. 从左边界开始反转链表
        while (index <= right){
            next = current.next;
            current.next = previous;
            previous = current;
            current = next;
            index++;
        }
        // 5. 通过左边界前驱结点修改左边界的后继结点
        dummy.next.next = current;
        // 6. 修改前驱结点的后继结点
        dummy.next = previous;
        // 7. 如果左边界前驱结点仍然是哑元, 那么证明反转就是从头结点开始的
        return dummy.value != 0 ? head: dummy.next;
    }

    /**
     * <h3>思路: K个一组反转链表</h3>
     */
    public static ListNode reverseKGroup(ListNode head, int k) {
        ListNode dummy = new ListNode(0, head);
        ListNode tail = dummy;
        ListNode previous = dummy;
        ListNode current = head;
        ListNode next = null;
        while(current != null){
            // 找到需要反转的区域, 也兼具查看剩下的链表是否需要反转的功能
            for(int index = 0;index < k;index++){
                tail = tail.next;
                // 如果尾指针为空, 证明剩余部分不够
                if(tail == null) return dummy.next;
            }
            // 记录下一部分链表的头结点
            next = tail.next;
            // 反转链表
            ListNode[] nodes = reverse(current, tail);
            // 接回原链表
            current = nodes[0];
            tail = nodes[1];
            previous.next = current;
            tail.next = next;
            // 更新尾指针
            previous = tail;
            // 向后移动
            current = next;
        }
        return dummy.next;
    }

    public static ListNode[] reverse(ListNode head, ListNode tail){
        ListNode previous = null;
        ListNode current = head;
        ListNode next = null;
        ListNode end = tail.next;
        while(current != end){
            next = current.next;
            current.next = previous;
            previous = current;
            current = next;
        }
        return new ListNode[]{tail, head};
    }
}
