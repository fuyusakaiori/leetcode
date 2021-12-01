package chapter02;

import utils.ListNode;

import java.util.HashSet;

/**
 * <h2>环形链表</h2>
 * <p>判断链表是否存在环, 如果存在, 返回入环的结点</p>
 */
public class CycleList
{
    public static void main(String[] args)
    {

    }

    /**
     * <p>采用哈希表完成</p>
     */
    private static ListNode detectCycleHashSet(ListNode head){
        ListNode current = head;
        HashSet<ListNode> set = new HashSet<>();
        while (current != null){
            if (!set.contains(current))
                set.add(current);
            else
                return current;
            current = current.next;
        }
        return null;
    }

    /**
     * <p>采用快慢指针完成</p>
     */
    private static ListNode detectCycle(ListNode head){
        if (head == null || head.next == null)
            return null;
        ListNode slow = head.next;
        ListNode fast = head.next.next;
        while (slow != fast){
            if (fast == null || fast.next == null)
                return null;
            slow = slow.next;
            fast = fast.next.next;
        }
        fast = head;
        while (fast != slow){
            fast = fast.next;
            slow = slow.next;
        }
        return fast;
    }
}
