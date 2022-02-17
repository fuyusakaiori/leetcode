package chapter02;

import utils.ListNode;
import java.util.Set;
import java.util.HashSet;

/**
 * <h2>环形链表</h2>
 * <h3>1. 环形链表: 仅判断链表中是否存在环, 不判断环结点</h3>
 * <h3>2. 环形链表 II: 不仅需要判断链表中是否存在环, 还需要判断环结点是谁</h3>
 */
public class CircleList
{
    public static void main(String[] args)
    {

    }

    /**
     * <h3>思路: 哈希表</h3>
     */
    private static ListNode detectCycleHashSet(ListNode head){
        Set<ListNode> set = new HashSet<>();
        ListNode current = head;
        while (current != null){
            if (set.contains(current))
                return current;
            set.add(current);
            current = current.next;
        }
        return null;
    }

    /**
     * <h3>采用快慢指针完成</h3>
     * TODO 记得看下这道题的证明过程
     */
    public static ListNode detectCycle(ListNode head){
        if (head == null || head.next == null) return null;

        ListNode slow = head;
        ListNode fast = head;

        do {
           if (fast == null || fast.next == null) return null;
           fast = fast.next.next;
           slow = slow.next;
        }while (slow != fast);

        fast = head;
        while (fast != slow){
            slow = slow.next;
            fast = fast.next;
        }

        return fast;
    }
}
