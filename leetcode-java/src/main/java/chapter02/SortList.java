package chapter02;

import utils.ListNode;
import utils.RandomUtil;

import java.util.*;

/**
 * <h2>排序链表</h2>
 */
public class SortList {

    /**
     * <h3>思路: 直接使用排序工具类解决</h3>
     */
    public static ListNode sortListUtil(ListNode head){
        List<ListNode> nodes = new LinkedList<>();
        while (head != null){
            nodes.add(head);
            head = head.next;
        }
        // 注: 可以继续简化, 但是简化出来就看不出是个什么东西了
        nodes.sort((node1, node2) -> node1.value - node2.value);

        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        for(ListNode node : nodes){
            node.next = null;
            current.next = node;
            current = current.next;
        }
        return dummy.next;
    }

    /**
     * <h3>思路: 自己编写排序算法, 显然不如工具类的算法</h3>
     * <h3>注: 主要目的是复习归并算法</h3>
     * <h3>注: 如果使用数组存放计算中点就更容易, 如果直接使用链表合并就需要快慢指针找中点, 快慢指针找中点更快</h3>
     * <h3>注: 合并 K 个升序链表不需要找中点</h3>
     */
    private static ListNode sortListTop(ListNode head) {
        return fork(head, null);
    }

    private static ListNode fork(ListNode head, ListNode tail){
        if(head.next == tail){
            head.next = null;
            return head;
        }
        ListNode mid = findMid(head, tail);
        ListNode leftNode = fork(head, mid);
        // 注: 因为找到的是第二个中点, 所以不能够传入中点的下一个结点作为头结点, 否则就造成左侧比右侧多一个结点的情况
        ListNode rightNode = fork(mid, tail);
        return mergeList(leftNode, rightNode);
    }

    private static ListNode findMid(ListNode head, ListNode tail){
        ListNode slow = head;
        ListNode fast = head;
        while (fast != tail && fast.next != tail){
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }


    /**
     * <h3>思路: 自底向上的归并排序</h3>
     * <h3>1. 最初每个分区仅有一个结点, 然后两两合并成有两个结点的单元, 依次类推</h3>
     * <h3>2. 每轮合并的次数取决于分区的数量</h3>
     */
    private static ListNode sortListDown(ListNode head){
        int length = getLength(head);
        ListNode dummy = new ListNode(0, head);
        ListNode current = dummy.next;
        ListNode previous = dummy;
        ListNode next = null;
        for (int subLength = 1;subLength < length;subLength <<= 1){
            // 每轮合并结束都需要重置前驱结点和当前结点
            previous = dummy;
            current = dummy.next;
            while (current != null){
                ListNode h1 = current;
                for (int index = 1;index < subLength && current.next != null;index++)
                    current = current.next;
                ListNode t1 = current;
                current = current.next;
                ListNode h2 = current;
                for (int index = 1;index < subLength && current != null && current.next != null;index++)
                    current = current.next;
                ListNode t2 = current;
                t1.next = null;
                if(t2 != null){
                    next = t2.next;
                    t2.next = null;
                }
                previous.next = mergeList(h1, h2);
                while (previous.next != null) previous = previous.next;
                current = next;
                next = null;
            }
        }
        return dummy.next;
    }

    private static int getLength(ListNode head){
        int length = 0;
        while (head != null && ++length > 0){
            head = head.next;
        }
        return length;
    }

    private static ListNode mergeList(ListNode l1, ListNode l2){
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        while (l1 != null && l2 != null){
            if (l1.value <= l2.value){
                current.next = l1;
                l1 = l1.next;
            }else{
                current.next = l2;
                l2 = l2.next;
            }
            current = current.next;
        }
        if (l1 != null) current.next = l1;
        if (l2 != null) current.next = l2;
        return dummy.next;
    }


}

