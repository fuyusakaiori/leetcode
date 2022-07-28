package chapter02;

import utils.ListNode;

import java.util.LinkedList;

/**
 * <h2>回文链表</h2>
 */
public class PalindromeList
{
    public static void main(String[] args)
    {

    }

    /**
     * <h3>思路: 栈实现</h3>
     */
    public static boolean isPalindromeStack(ListNode head) {
        LinkedList<ListNode> stack = new LinkedList<>();
        ListNode current = head;
        while (current != null){
            stack.push(current);
            current = current.next;
        }
        while(!stack.isEmpty()){
            if (stack.pop().value != head.value)
                return false;
            head = head.next;
        }
        return true;
    }

    /**
     * <h3>思路：</h3>
     * <h3>1. 快慢指针找到中点, 然后从中点开始逆序直到结尾</h3>
     * <h3>2. 快指针和慢指针两个分别从头和尾开始遍历, 直到中点</h3>
     */
    public static boolean isPalindrome(ListNode head){
        ListNode fast = head;
        ListNode slow = head;
        // 快慢指针找中点
        while (fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }
        // 开始逆序
        ListNode current = slow;
        ListNode previous = null;
        ListNode next = null;
        while (current != null){
            next = current.next;
            current.next = previous;
            previous = current;
            current = next;
        }
        // 开始对比
        slow = previous;
        fast = head;
        while(slow != null){
            if (slow.value != fast.value)
                return false;
            slow = slow.next;
            fast = fast.next;
        }
        return true;
    }

}
