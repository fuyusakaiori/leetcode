package chapter01;

public class ReverseList
{
    public static void main(String[] args)
    {
        ListNode head = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));
        ListNode listNode = reverseList1(head);
    }

    // 递归方式实现反转链表
    // 问题: 如何才能够去除那个循环
    public static ListNode reverseList1(ListNode head) {

        if(head == null || head.next == null)
            return head;

        ListNode res = reverseList1(head.next);
        /* 递归返回值如何处理?
        1. 此前我想到的方法就是, 直接让返回值指向当前的 head
           但是这样就会直接导致之后的返回值始终都是头结点, 没有办法直接指向 head, 需要循环遍历
        2. 另一种思路就是, 让 head 去 "指向" 返回值
           head.next 就是 返回值, 返回值的 next 就可以指向 head 了, 我觉得是相当巧妙了
         */
        head.next.next = head;
        head.next = null;
        return res;
    }

    // 采用循环实现的反转链表
    public static ListNode reverseList2(ListNode head) {
        ListNode list = null;
        ListNode temp = null;
        while(head != null){
            if(list == null)
                list = new ListNode(head.val);
            else{
                temp = new ListNode(head.val, list);
                list = temp;
            }

            head = head.next;
        }

        return list;
    }

}
