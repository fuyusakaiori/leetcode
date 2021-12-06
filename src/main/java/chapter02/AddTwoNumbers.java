package chapter02;

import utils.ListNode;

import java.util.LinkedList;
import java.util.List;

/**
 * <h2>两数之和 II</h2>
 * <p>官方题解就纯傻逼, 还不然你反转链表完成, 使用栈完成, 这几把和反转链表有啥区别?</p>
 */
public class AddTwoNumbers
{
    public static void main(String[] args)
    {

    }


    /**
     * 就是反转链表从最低位相加
     * @param l1 第一个链表
     * @param l2 第二个链表
     * @return 相加的结果
     */
    private static ListNode addTwoNumbers(ListNode l1, ListNode l2){
        // 0.1 每一位计算的中间结果
        int result = 0;
        // 0.2 是否进位
        int carry = 0;
        ListNode first = reverse(l1);
        ListNode second = reverse(l2);
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        // 1. 两个链表的长度可能不一样, 所以只需要在链表短的遍历结束就退出循环
        while (first != null && second != null){
            // 2. 计算中间结果
            result = first.value + second.value + carry;
            // 3. 创建带有中间结果的结点
            current.next = new ListNode(result % 10);
            // 4. 更新进位
            carry = result / 10;
            // 5. 新链表向前移动
            current = current.next;
            // 注: 记得向前移动, 否则会超出内存限制
            first = first.next;
            second = second.next;
        }
        // 6. 处理较长的链表的剩余部分
        // 6.1 必须是循环处理, 因为可能后面都是 9, 导致持续进位
        while (first != null){
            result = first.value + carry;
            // 6.2 更新值
            first.value = result % 10;
            // 6.3 更新进位
            carry = result / 10;
            current.next = first;
            first = first.next;
        }
        while (second != null){
            result = second.value + carry;
            second.value = result % 10;
            carry = result / 10;
            current.next = second;
            second = second.next;
        }
        // 7. 因为最后一位可能还会进位, 所以要单独处理
        if (carry != 0) current.next = new ListNode(carry);
        // 8. 记得翻转回去
        return reverse(dummy.next);
    }

    /**
     * 反转链表
     */
    private static ListNode reverse(ListNode head){
        if(head == null || head.next == null)return head;
        ListNode previous = null;
        ListNode current = head;
        ListNode next = head.next;
        while(current != null){
            current.next = previous;
            previous = current;
            current = next;
            next = next != null ? next.next: null;
        }
        return previous;
    }

}
