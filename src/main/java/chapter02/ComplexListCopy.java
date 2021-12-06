package chapter02;

import utils.ListNode;
import utils.RandomUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * <h2>复杂链表复制</h2>
 * <p>问题描述</p>
 * <p>1. 每个结点具有额外的 random 指针</p>
 * <p>2. random 指针可以指向任何一个结点</p>
 * <p>两种方式核心思路都是采用克隆</p>
 */
public class ComplexListCopy
{
    public static void main(String[] args)
    {

    }

    /**
     * 采用哈希表完成
     * @param head 头结点
     * @return 复制的链表
     */
    private static ListNode copyComplexListHash(ListNode head){
        ListNode current = head;
        // 0. Key 代表的是原链表中的结点, Value 代表的是原链表结点的拷贝
        HashMap<ListNode, ListNode> map = new HashMap<>();
        // 1. 先对原链表的进行浅拷贝, 就是说不复制随机指针
        while (current != null){
            // 注意: 这里可以不进行 next 指针的拷贝也可以进行
            map.put(current, new ListNode(current.value, current.next));
            current = current.next;
        }
        // 2. 开始对原链表的深拷贝
        current = head;
        while (current != null) {
            map.get(current).random = map.get(current.random);
            current = current.next;
        }

        return map.get(head);
    }

    /**
     * 不借助额外空间完成
     */
    private static ListNode copyComplexList(ListNode head){
        // 1. 遍历链表对每个结点进行复制, 然后链接在原结点的后面
        ListNode current = head;
        while (current != null){
            ListNode node = new ListNode(current.value);
            node.next = current.next;
            current.next = node;
            current = node.next;
        }
        current = head;
        // 2. 开始拷贝随机指针
        while (current != null){
            // 2.1 如果原结点就没有随机指针那么就不需要设置
            if (current.random != null)
                current.next.random = current.random.next;
            current = current.next.next;
        }
        ListNode dummy = new ListNode(0);
        ListNode copy = dummy;
        current = head;
        // 3. 开始分离拷贝链表和原链表
        while (current != null){
            // 3.1 复制链表链接拷贝结点
            copy.next = current.next;
            // 3.2 原链表结点指向下下个结点
            current.next = current.next.next;
            // 3.3 更新
            current = current.next;
            copy = copy.next;
        }

        return dummy.next;
    }

}
