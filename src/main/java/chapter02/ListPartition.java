package chapter02;


import utils.ListNode;
import utils.RandomUtil;
import utils.TestUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <h2>链表分区</h2>
 * <p>1. 给定一个值, 然后将链表分为大于这个值的结点在右边, 小于等于的在左边, 也就是分成两个区域</p>
 * <p>2. 给定一个值, 然后将链表分为大于、等于、小于三个区域</p>
 * <p>3. 给定一个值, 然后将链表分为 k 部分</p>
 */
public class ListPartition
{
    /**
     * <p>1. 前两个问题最容易想到的办法就是快速排序, 但是这里题目要求必须保证相对次序, 快排显然是不行的</p>
     * <p>2. 第三个问题很简单, 只需要先记录长度, 然后遍历就可以</p>
     */
    public static void main(String[] args)
    {

    }

    /**
     * 采用快速排序来给链表分组
     */
    private static ListNode partitionList(ListNode head, int target){
        // 1. 辅助数组
        List<ListNode> help = new ArrayList<>();
        // 2. 遍历链表, 将所有元素放在数组中
        // TODO 所有结点的指针都是没有断开的
        ListNode current = head;
        while (current != null){
            help.add(current);
            current = current.next;
        }
        // 3. 对数组进行排序
        ListNode[] nodes = help.toArray(new ListNode[0]);

        // 4. 分区
        return partition(nodes, target);
    }

    private static ListNode partition(ListNode[] nodes, int target){
        int index = 0;
        int leftIndex = 0;
        int rightIndex = nodes.length - 1;
        // 4.1 划定分区
        while (index < rightIndex + 1){
            if (nodes[index].value < target){
                swap(nodes, index++, leftIndex++);
            }else if (nodes[index].value > target){
                swap(nodes, index, rightIndex--);
            }else{
                index++;
            }
        }
        // 4.2 串成链表
        ListNode head = null;
        ListNode tail = null;
        for (ListNode node : nodes) {
            if (head == null){
                head = node;
                tail = head;
            }else{
                tail.next = node;
                tail = node;
            }
        }
        // TODO 最后这里必须要要断开最后一个结点的指针, 否则就是死循环
        tail.next = null;

        return head;
    }

    private static void swap(ListNode[] nodes, int first, int second){
        ListNode temp = nodes[first];
        nodes[first] = nodes[second];
        nodes[second] = temp;
    }

    /**
     * <p>使用临时变量来给链表分组</p>
     */
    private static ListNode partition(ListNode head, int target) {
        if (head == null || head.next == null)
            return head;
        // 1. 准备六个变量: 左分区的头尾指针, 中间分区的头尾指针, 右分区的头尾指针
        ListNode current = head;
        ListNode next = head.next;
        ListNode sDummy = new ListNode(0), sCurrent = sDummy;
        ListNode eDummy = new ListNode(0), eCurrent = eDummy;
        ListNode bDummy = new ListNode(0), bCurrent = bDummy;
        // 2. 开始遍历链表
        while (current != null){
            current.next = null;
            if (current.value < target){
                sCurrent.next = current;
                sCurrent = sCurrent.next;
            }else if (current.value > target){
                eCurrent.next = current;
                eCurrent = eCurrent.next;
            }else {
                bCurrent.next = current;
                bCurrent = bCurrent.next;
            }
            current = next;
            next = next != null ? next.next: null;
        }

        // 只要使用哑元, 就可以完全避免空指针异常
        sCurrent.next = eDummy.next;
        eCurrent.next = bDummy.next;
        return sDummy.next;
    }

    /**
     *
     * @param head 需要分割的链表
     * @param k 分割的部分
     */
    private static ListNode[] splitList(ListNode head, int k){
        if(head == null)
            return new ListNode[k];
        int length = 0;
        ListNode current = head;
        // 1. 计算长度
        while (current != null && ++length > 0)current = current.next;
        // 2. 开始遍历分割
        int index = 0;
        // 2.1 每个部分多少个结点
        int numbers = length % k == 0 ? length / k : length / k + 1;
        int counts = numbers;
        current = head;
        ListNode next = head.next;
        ListNode [] parts = new ListNode[k];
        while (current != null){
            if (numbers == 1){
                parts[index++] = head;
                head = current.next;
                current.next = null;
                length -= counts;
                if(k != 1){
                    numbers = length % (--k) == 0 ? length / k : length / k + 1;
                    counts = numbers;
                }
            }else{
                numbers--;
            }
            current = next;
            next = next != null ? next.next: null;
        }
        if(head != null)
            parts[index] = head;
        return parts;
    }
}
