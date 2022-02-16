package chapter01;

import java.io.File;
import java.util.LinkedList;

/**
 * <h2>两数相加</h2>
 * <h3>1. 两数相加: 链表采用逆序的方式存储数字</h3>
 * <h3>2. 两数相加 II: 链表采用顺序的方式存储数字</h3>
 */
public class CalculateTwoNumbers
{
    public static void main(String[] args)
    {

    }

    /**
     * <h3>思路: 模拟</h3>
     */
    private static ListNode addTwoNumbers(ListNode list1, ListNode list2){
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        ListNode node;
        int result = 0;
        int carry = 0;
        while (list1 != null && list2 != null){
            result = list1.val + list2.val + carry;
            carry = result / 10;
            node = new ListNode(result % 10);
            current.next = node;
            current = current.next;
            list1 = list1.next;
            list2 = list2.next;
        }
        while (list1 != null){
            result = list1.val + carry;
            carry = result / 10;
            node = new ListNode(result % 10);
            current.next = node;
            current = current.next;
            list1 = list1.next;
        }

        while (list2 != null){
            result = list2.val + carry;
            carry = result / 10;
            node = new ListNode(result % 10);
            current.next = node;
            current = current.next;
            list2 = list2.next;
        }

        current.next = carry != 0 ? new ListNode(carry) : null;

        return dummy.next;
    }

    /**
     * <h3>1. 思路: 反转链表 + 模拟</h3>
     * <h3>2. 思路: 栈 + 模拟</h3>
     */
    private static ListNode addTwoNumbersByReverse(ListNode list1, ListNode list2){
        ListNode first = reverse(list1);
        ListNode second = reverse(list2);
        ListNode dummy = null;
        ListNode node = null;
        int result = 0;
        int carry = 0;
        while (first != null && second != null){
            result = first.val + second.val + carry;
            carry = result / 10;
            node = new ListNode(result % 10);
            node.next = dummy;
            dummy = node;
            first = first.next;
            second = second.next;
        }
        while (first != null){
            result = first.val + carry;
            carry = result / 10;
            node = new ListNode(result % 10);
            node.next = dummy;
            dummy = node;
            first = first.next;
        }
        while (second != null){
            result = second.val + carry;
            carry = result / 10;
            node = new ListNode(result % 10);
            node.next = dummy;
            dummy = node;
            second = second.next;
        }
        if (carry != 0){
            node = new ListNode(1);
            node.next = dummy;
            dummy = node;
        }
        return dummy;
    }

    /**
     * <h3>反转链表</h3>
     */
    private static ListNode reverse(ListNode head){
        ListNode previous = null;
        ListNode current = head;
        ListNode next = head.next;
        while (current != null){
            current.next = previous;
            previous = current;
            current = next;
            next = next != null ? next.next: null;
        }

        return previous;
    }

    private static ListNode addTwoNumbersByStack(ListNode list1, ListNode list2){
        LinkedList<Integer> firstStack = new LinkedList<>();
        LinkedList<Integer> secondStack = new LinkedList<>();
        // 压栈
        while (list1 != null){
            firstStack.push(list1.val);
            list1 = list1.next;
        }
        while (list2 != null){
            secondStack.push(list2.val);
            list2 = list2.next;
        }
        // 模拟计算
        int carry = 0;
        int result = 0;
        ListNode node = null;
        ListNode dummy = null;
        while (!firstStack.isEmpty() || !secondStack.isEmpty() || carry != 0){
            int first = firstStack.isEmpty() ? 0: firstStack.pop();
            int second = secondStack.isEmpty() ? 0: secondStack.pop();
            result = first + second + carry;
            carry = result / 10;
            node = new ListNode(result % 10);
            // 头插法
            node.next = dummy;
            dummy = node;
        }
        return dummy;
    }

}
