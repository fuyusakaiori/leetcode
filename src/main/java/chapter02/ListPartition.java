package chapter02;


import utils.ListNode;
import utils.RandomUtil;
import utils.TestUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <h2>链表分区</h2>
 *
 */
public class ListPartition
{
    public static void main(String[] args)
    {
        RandomUtil random = new RandomUtil(10, 100);
        ListNode head = random.randomList();
        TestUtil.printlnList(head);
        int target = new Random().nextInt(100);
        System.out.println(target);
        ListNode partition = partition(head, target);
        TestUtil.printlnList(partition);
    }

    // 链表分区: 解法一（不可以保证相对次序）
    private static ListNode listPartition(ListNode head, int target){
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

    // TODO 快速排序的方式是没有办法确保相对次序的
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

    // 链表分区: 解法二（可以保证相对次序）
    private static ListNode partition(ListNode head, int target) {
        if (head == null || head.next == null)
            return head;
        // 1. 准备六个变量: 左分区的头尾指针, 中间分区的头尾指针, 右分区的头尾指针
        ListNode current = head;
        ListNode next = null;
        ListNode sHead = null, sTail = null;
        ListNode eHead = null, eTail = null;
        ListNode bHead = null, bTail = null;
        // 2. 开始遍历链表
        while (current != null){
            next = current.next;
            current.next = null;
            if (current.value < target){
                if (sHead == null){
                    sHead = current;
                    sTail = sHead;
                }else{
                    sTail.next = current;
                    sTail = current;
                }
            }else if (current.value > target){
                if (bHead == null){
                    bHead = current;
                    bTail = bHead;
                }else{
                    bTail.next = current;
                    bTail = current;
                }
            }else {
                if (eHead == null){
                    eHead = current;
                    eTail = eHead;
                }else{
                    eTail.next = current;
                    eTail = current;
                }
            }
            current = next;
        }

        // 3. 开始串联链表
        // TODO 现列出所有的情况后再进一步优化
        if (sHead == null && eHead == null){
            return bHead;
        }else if (sHead == null){
            eTail.next = bHead;
            return eHead;
        }else{
            sTail.next = eHead == null ? bHead : eHead;
            return sHead;
        }
    }
}
