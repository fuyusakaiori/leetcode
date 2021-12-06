package chapter02;

import utils.ListNode;

/**
 * <h2>删除链表中的元素</h2>
 * <p>1. 给定一个值, 移除链表中的所有和给定值一样的元素</p>
 * <p>2. 没有给定值, 删除链表中该元素的副本</p>
 * <p>3. 没有给定值, 删除链表中该元素及其副本</p>
 */
public class DeleteListElement
{
    public static void main(String[] args)
    {

    }

    /**
     *
     * @param head 给定的链表
     * @param value 需要删除的结点的值
     * @return 删除结点之后的新链表
     */
    private static ListNode removeKey(ListNode head, int value){
        ListNode dummy = new ListNode(0, head);
        ListNode current = dummy;
        while (current.next != null){
            if (current.next.value == value)
                current.next = current.next.next;
            else
                current = current.next;
        }
        return dummy.next;
    }

    /**
     * 移除升序链表中所有重复的元素, 不包括本体
     */
    private static ListNode removeDuplicates(ListNode head){
        if(head == null || head.next == null)
            return head;
        ListNode start = head;
        ListNode current = head;
        while (current != null){
            if (start.value != current.value) {
                start.next = current;
                start = current;
            }
            current = current.next;
        }
        start.next = null;
        return head;
    }

    /**
     * 移除升序链表中的所有重复元素, 包括本体
     */
    private static ListNode deleteDuplicates(ListNode head){
        if (head == null || head.next == null)
            return head;
        ListNode dummy = new ListNode(0, head);
        ListNode previous = dummy;
        ListNode current = head;
        ListNode start = head;
        while (current != null){
            // 1. 只要出现和当前结点不同, 那么就需要记录这个结点, 因为这个结点之后的结点可能是重复的
            if (start.value != current.value){
                // 2. 只要起始结点和当前结点互为前后的关系, 那么肯定证明这个是没有重复的, 前驱结点移动
                if (start.next == current)
                    previous = start;
                // 3. 如果不是互为前驱后继的关系, 那么中间肯定存在重复的, 就需要修改前驱结点的指针
                else
                    previous.next = current;
                // 4. 只要和当前结点和起始结点不同, 那么就是需要更新起始结点
                start = current;
            }
            current = current.next;
        }
        // 5. 最后还需要额外处理一次
        if(start.next != null) previous.next = null;
        return dummy.next;
    }
}
