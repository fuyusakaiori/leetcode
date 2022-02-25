package chapter02;

import utils.ListNode;

import java.util.LinkedList;

/**
 * <h2>删除链表中的元素</h2>
 * <h3>1. 给定一个值, 移除链表中的所有和给定值一样的元素</h3>
 * <h3>2. 删除链表中的重复元素</h3>
 * <h3>3. 删除链表中的重复元素 II</h3>
 * <h3>4. 删除链表倒数第 N 个结点</h3>
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
     * <h3>思路: </h3>
     * <h3>1. 找到起始结点, 然后向后遍历, 直到发现第一个和起始结点不一样的, 就开始删除</h3>
     * <h3>2. 只要发现当前结点的值和下个结点的值是相同的, 那么就直接指向下下个结点</h3>
     * <h3>注: 其实也可以使用哈希表完成, 但是占用额外空间</h3>
     */
    private static ListNode removeDuplicates1(ListNode head){
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

    private static ListNode removeDuplicates2(ListNode head){
        if (head == null || head.next == null)
            return head;
        ListNode current = head;
        while (current.next != null){
            if (current.value == current.next.value){
                current.next = current.next.next;
            }else{
                current = current.next;
            }
        }
        return head;
    }

    /**
     * <h3>思路: </h3>
     * <h3>1. 基于思路-1 </h3>
     * <h3>注: 不过需要记录如何找到起始结点的前驱结点,然后需要考虑什么时候前驱结点移动,移动到哪里</h3>
     * <h3>2. 基于思路-2 </h3>
     */
    private static ListNode deleteDuplicates1(ListNode head){
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

    private static ListNode deleteDuplicates2(ListNode head){
        if (head == null || head.next == null)
            return head;
        ListNode dummy = new ListNode(0, head);
        ListNode current = dummy;

        // 1. 将自己直接作为前驱结点, 判断后面是否存在结点相同
        while (current.next != null && current.next.next != null){
            // 2. 这就证明接下来有元素是相同的
            if (current.next.value == current.next.next.value){
                int value = current.next.value;
                // 3. 依次移除每一个重复的元素
                while (current.next != null && current.next.value == value){
                    current.next = current.next.next;
                }
            }else{
                current = current.next;
            }
        }
        return dummy.next;
    }

    /**
     * <h3>思路: </h3>
     * <h3>1. 计算长度</h3>
     * <h3>2. 栈</h3>
     * <h3>3. 快慢指针</h3>
     */
    private static ListNode removeNthFromEnd1(ListNode head, int nth){
        int length = getLength(head);
        int distance = length - nth;
        ListNode dummy = new ListNode(0, head);
        ListNode current = dummy;
        while (distance-- > 0){
            current = current.next;
        }
        current.next = current.next.next;
        return dummy.next;
    }

    private static int getLength(ListNode list) {
        int length = 0;
        while(list != null && ++length > 0){
            list = list.next;
        }
        return length;
    }

    private static ListNode removeNthFromEnd2(ListNode head, int nth){
        LinkedList<ListNode> stack = new LinkedList<>();
        ListNode dummy = new ListNode(0, head);
        ListNode current = dummy;
        while (current != null){
            stack.push(current);
            current = current.next;
        }
        while (nth-- >= 0){
            current = stack.pop();
        }
        assert current != null;
        current.next = current.next.next;
        return dummy.next;
    }

    private static ListNode removeNthFromEnd3(ListNode head, int nth){
        ListNode dummy = new ListNode(0, head);
        ListNode fast = head;
        ListNode slow = dummy;
        while (nth-- > 0){
            fast = fast.next;
        }
        while (fast != null){
            fast = fast.next;
            slow = slow.next;
        }
        slow.next = slow.next.next;
        return dummy.next;
    }
}
