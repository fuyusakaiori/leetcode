package chapter01;

public class MergeTwoList
{
    public static void main(String[] args)
    {
        ListNode l1 = null;
        ListNode l2 = new ListNode(0);
        ListNode listNode = mergeTwoLists(l1, l2);
    }

    // 采用循环实现的: 理论上循环和递归应该是等效的
    public static ListNode mergeTwoLists(ListNode l1, ListNode l2)
    {
        // 新链表
        ListNode list = null;
        ListNode tail = list;
        ListNode temp = null;
        // 遍历链表
        while (l1 != null && l2 != null) {

            if (l1.val < l2.val){
                temp = new ListNode(l1.val);
                l1 = l1.next;
            }
            else if (l1.val > l2.val){
                temp = new ListNode(l2.val);
                l2 = l2.next;
            }
            else {
                temp = new ListNode(l1.val, new ListNode(l2.val));
                l1 = l1.next;
                l2 = l2.next;
            }

            if (list != null)
                tail.next = temp;
            else
                list = temp;


            if (temp.next == null)
                tail = temp;
            else
                tail = temp.next;

        }

        // 如果存在一条链表先遍历结束, 那么只需要接上另一条链表就行了
        if (l1 != null)
        {
            if (list != null)
                tail.next = l1;
            else
                list = l1;
        }

        if (l2 != null)
        {
            if (list != null)
                tail.next = l2;
            else
                list = l2;
        }

        return list;
    }

    // 采用递归怎么实现啊?

}

class ListNode
{
    int val;
    ListNode next;

    ListNode() { }

    ListNode(int val)
    {
        this.val = val;
    }

    ListNode(int val, ListNode next)
    {
        this.val = val;
        this.next = next;
    }
}
