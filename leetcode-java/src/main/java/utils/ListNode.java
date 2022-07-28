package utils;

// 用于构造测试链表的类
public class ListNode
{
    public int value;
    public ListNode previous;
    public ListNode next;
    public ListNode random;
    public ListNode child;

    public ListNode()
    {
    }

    public ListNode(int value)
    {
        this.value = value;
    }

    public ListNode(int value, ListNode next){
        this.value = value;
        this.next = next;
    }
}
