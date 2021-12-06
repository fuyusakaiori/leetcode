package chapter02;

import utils.ListNode;

import java.util.*;

/**
 * <h2>链表相交</h2>
 * <p>1. 两个链表中都不存在环, 请问是否相交</p>
 * <p>2. 两个链表中不确定是否存在环, 请问是否相交</p>
 */
public class IntersectionList
{
    public static void main(String[] args) {

    }


    /**
     * 采用哈希表完成
     * @param l1 给定的第一个链表
     * @param l2 给定的第二个链表
     * @param noLoop 没有环
     * @return 相交的结点
     */
    private static ListNode intersectionListHash(ListNode l1, ListNode l2, String noLoop){
        ListNode current = l1;
        Set<ListNode> set = new HashSet<>();
        // 1. 遍历其中一个链表, 将所有结点添加进哈希表中
        while (current != null){
            set.add(current);
            current = current.next;
        }
        // 2. 遍历第二个链表, 检查是否有结点在哈希表中
        current = l2;
        while (current != null){
            if (set.contains(current))
                return current;
            current = current.next;
        }
        return null;
    }

    /**
     * <p>只使用临时变量完成</p>
     */
    private static ListNode intersectionList(ListNode l1, ListNode l2, String noLoop){
        if (l1 == null || l2 == null)
            return null;
        int length = 0;
        // 1. 计算两个链表相差的长度
        ListNode current = l1;
        while (current != null){
            length++;
            current = current.next;
        }
        current = l2;
        while (current != null){
            length--;
            current = current.next;
        }
        // 2. 长的链表先走多出来的部分
        int index = Math.abs(length);
        if (length > 0){
            while (index-- > 0)l1 = l1.next;
        }else{
            while (index-- > 0)l2 = l2.next;
        }
        // 3. 两个链表一起走, 相等的时候停止
        while (l1 != l2){
            l1 = l1.next;
            l2 = l2.next;
        }
        return l1;
    }

    /**
     *
     * @param loop 有环: ① 两个链表具有各自的环, 不相交 ② 两个链表拥有同一个环, 入环结点相同 ③ 两个链表拥有同一个环, 但是入环结点不同
     * @return
     */
    private static ListNode intersectionLoopListLoop(ListNode l1, ListNode l2, String loop) {


        return null;
    }

}
