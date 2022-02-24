package chapter02;

import utils.ListNode;
import utils.RandomUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * <h2>复制带随机指针的链表</h2>
 */
public class ComplexListCopy
{
    public static void main(String[] args)
    {

    }

    /**
     * <h3>思路: 哈希表实现</h3>
     */
    private static ListNode copyComplexListHash(ListNode head){
        Map<ListNode, ListNode> map = new HashMap<>();
        ListNode current = head;
        // 1. 克隆结点
        while (current != null){
            map.put(current, new ListNode(current.value));
            current = current.next;
        }
        // 2. 建立 random、next 指针
        current = head;
        while (current != null){
            map.get(current).next = current.next;
            map.get(current).random = map.get(current.random);
            current = current.next;
        }
        return map.get(head);
    }

    /**
     * <h3>思路: 原链表中插入克隆结点, 复制完成后分离</h3>
     */
    private static ListNode copyComplexList(ListNode head){
        ListNode current = head;
        // 1. 复制结点
        while (current != null){
            ListNode node = new ListNode(current.value);
            node.next = current.next;
            current.next = node;
            current = current.next.next;
        }
        // 2. 建立 random 指针
        current = head;
        while (current != null){
            ListNode copy = current.next;
            copy.random = current.random != null ? current.random.next: null;
            current = current.next.next;
        }
        // 3. 建立 next 指针
        ListNode dummy = new ListNode(0);
        ListNode copy = dummy;
        current = head;
        while (current != null){
            copy.next = current.next;
            copy = copy.next;
            current.next = current.next.next;
            current = current.next;
        }

        return dummy.next;

    }

}
