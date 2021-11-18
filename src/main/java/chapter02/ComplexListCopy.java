package chapter02;

import utils.ListNode;
import utils.RandomUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// 复杂链表复制
public class ComplexListCopy
{
    public static void main(String[] args)
    {
        RandomUtil random = new RandomUtil(5, 100);
        ListNode head = random.randomList();
        copyComplexList(head);

    }

    // 解法一: 借助哈希表
    private static ListNode copyComplexListHash(ListNode head){
        ListNode current = head;
        Map<ListNode, ListNode> map = new HashMap<>();
        while (current != null){
            map.put(current, new ListNode(current.value));
            current = current.next;
        }
        current = head;
        while (current != null){
            map.get(current).next = map.get(current.next);
            map.get(current).random = map.get(current.random);
            current = current.next;
        }

        return map.get(head);
    }

    // 解法二: 借助临时变量
    private static ListNode copyComplexList(ListNode head){

        ListNode current = head;
        while (current != null){
            ListNode node = new ListNode(current.value);
            node.next = current.next;
            current.next = node;
            current = node.next;
        }
        current = head;
        ListNode copy = null;
        // 这个循环仅设置随机指针
        while(current != null){
            // 取出克隆结点
            copy = current.next;
            // 设置克隆结点的随机指针
            copy.random = current.random != null ? current.random.next : null;
            // 设置下一个结点的克隆接点的随机指针
            current = current.next.next;
        }

        current = head;
        boolean flag = true;
        ListNode res = null;
        // 分离两个链表
        while(current != null){
            copy = current.next;
            // 记住新链表的头结点
            if(flag){
                res = copy;
                flag = false;
            }
            // 还原原链表
            current.next = current.next.next;
            // 分离新链表
            copy.next = copy.next != null ? copy.next.next : null;
            // 向后移动
            current = current.next;
        }
        return res;
    }

}
