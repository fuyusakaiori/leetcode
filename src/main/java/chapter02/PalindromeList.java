package chapter02;

import utils.ListNode;

import java.util.LinkedList;

// 判断回文链表
public class PalindromeList
{
    public static void main(String[] args)
    {
        ListNode head = new ListNode();
        ListNode node1 = new ListNode();
        ListNode node2 = new ListNode();
        ListNode node3 = new ListNode();
        head.next = node1;
        node1.next = node2;
        node2.next = node3;
        head.value = 1;
        node1.value = 2;
        node2.value = 2;
        node3.value = 1;
        isPalindrome3(head);
    }

    /* 解法一:
    1. 直接遍历链表将所有的元素全部压入栈中
    2. 再次遍历链表的同时将栈中元素出栈, 进行比对
     */
    public static boolean isPalindrome1(ListNode head)
    {
        // 遍历链表压入栈中
        LinkedList<ListNode> list = new LinkedList<>();
        ListNode temp = head;
        while (temp != null)
        {
            list.push(temp);
            temp = temp.next;
        }
        // 出栈判断
        temp = head;
        while (temp != null)
        {
            if (list.pop().value != temp.value)
                return false;
            temp = temp.next;
        }

        return true;
    }

    /* 解法二:
    1. 快指针移动两次, 慢指针移动一次, 快指针如果移动到末尾的时候慢指针就到达了中点
    2. △末尾判断: 如果快指针是因为空值结束的, 代表链表元素仅为偶数个, 如果快指针是因为下一个值是空值结束的, 代表链表元素是奇数个
    3。如果链表元素是奇数个就不需要压入中点值, 如果链表个数是偶数个就需要压入中点值
    4. 从慢指针开始将所有剩余一半元素压入栈中
    5. 重新遍历链表同时将栈中元素出栈, 两者进行比对
    6. △结束条件: 不要用栈为空来判断, 用指针到达中点值来判断
     */
    public static boolean isPalindrome2(ListNode head){
        // 1. 排除所有的特殊情况：默认空链表和仅有一个元素的链表都是回文链表
        if (head == null || head.next == null)
            return true;
        // 2. 存储结点的栈
        LinkedList<ListNode> stack = new LinkedList<>();
        // 3. 快慢指针
        ListNode slow = head;
        ListNode fast = head;
        // 4. 开始寻找中点
        boolean flag = true;
        while(fast != null && fast.next != null){
            // 快指针移动两次， 慢指针移动一次
            fast = fast.next.next;
            slow = slow.next;
        }
        // 5. 保存中点的位置
        ListNode mid = slow;
        // 6. 链表长度为奇数和链表长度为偶数存在区别
        // 6.1 长度为奇数的时候不要压入中点，长度为偶数的时候需要压入中点
        // 6.2 链表长度为奇数的时候恰好快指针走到最后一个结点退出, 为偶数的时候走到 null 退出
        if (fast == null)
            stack.push(slow);
        slow = slow.next;
        // 6.3 继续压入剩下的元素
        while (slow != null){
            stack.push(slow);
            slow = slow.next;
        }
        // 7. 遍历链表的同时出栈，比较两个结点的值是否相同
        slow = head;
        while (slow != mid){
            // 如果两个结点的值不相同就直接退出
            if (slow.value != stack.pop().value)
                return false;
            slow = slow.next;
        }

        return true;
    }

    public static boolean isPalindrome3(ListNode head) {
        if (head == null || head.next == null)
            return true;
        // 双指针
        ListNode slow = head;
        ListNode fast = head;
        while(fast != null && fast.next != null){
            fast = fast.next.next;
            slow = slow.next;
        }
        if(slow.next == null)
            return head.value == slow.value;
        // 从慢指针开始遍历并且修改引用
        ListNode previous = slow;
        ListNode next = slow.next;
        slow.next = null;
        while(next != null){
            slow = next;
            next = slow.next;
            // 慢指针更改指向之前需要保存下一个结点, 否则下个结点就丢失了
            slow.next = previous;
            previous = slow;
        }
        // 从两边开始遍历
        ListNode temp = head;
        while(temp.next != null && slow != null){
            if(temp.value != slow.value)
                return false;
            temp = temp.next;
            slow = slow.next;
        }
        return true;
    }


}
