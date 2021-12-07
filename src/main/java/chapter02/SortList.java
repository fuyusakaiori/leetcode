package chapter02;

import utils.ListNode;
import utils.RandomUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * <h2>排序链表</h2>
 * <p>问题描述: 就是对链表排序</p>
 * <p>进阶问题: 使用O(N*logN)时间复杂度完成</p>
 */
public class SortList
{

    /**
     * <p>这个题有两种思路可以解</p>
     * <p>1. 最直接的就是将链表的结点全部存入数组中, 然后对数组进行排序</p>
     * <p>2。第二种方法非常不好想, 是自底向上的归并排序 </p>
     */
    public static void main(String[] args)
    {


    }

    public static ListNode sort(ListNode head){
        // 排除额外情况
        if(head == null)
            return null;

        // 获取链表长度
        int length = 0;
        ListNode current = head;
        while(current != null && ++length > 0)
            current = current.next;

        current = head;
        ListNode h1;
        ListNode h2;
        ListNode previous = null;
        ListNode next = null;
        ListNode dummy = new ListNode(0, head);
        // 初始每个分区长度仅为1
        for(int subLength = 1;subLength < length;subLength<<=1){
            previous = dummy;
            current = dummy.next;
            // 分区两两合并
            while(current != null){
                // 记录第一个分区的头指针
                h1 = current;
                // 开始向后移动, 寻找第二个分区的头指针
                for(int i = 1;i < subLength && current.next != null;i++)
                    current = current.next;
                // 记录第二个分区的头指针
                h2 = current.next;
                // 断开两个链表之间的联系, 否则合并链表的时候没有结束
                current.next = null;
                // 更新当前结点
                current = h2;
                // 开始向后移动, 寻找第三个分区的头结点, 留着下次使用
                for(int i = 1;i < subLength && current != null && current.next != null;i++)
                    current = current.next;
                // 再次断开两个链表之间的联系, 否者合并链表的时候没有结束
                //
                if(current != null){
                    next = current.next;
                    current.next = null;
                }
                // 合并链表
                previous.next = merge(h1, h2);
                while(previous.next != null)
                    previous = previous.next;
                // 重复上述过程
                current = next;
                next = null;
            }
        }

        return dummy.next;
    }

    private static ListNode merge(ListNode h1, ListNode h2) {
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        while(h1 != null && h2 != null){
            if(h1.value < h2.value){
                current.next = h1;
                h1 = h1.next;
            }else{
                current.next = h2;
                h2 = h2.next;
            }

            current = current.next;
        }
        // 这里不使用循环是因为每个链表都是有序的, 所以没有必要
        if(h1 != null)current.next = h1;
        if(h2 != null)current.next = h2;

        return dummy.next;
    }

}

