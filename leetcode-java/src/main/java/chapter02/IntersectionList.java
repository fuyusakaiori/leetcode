package chapter02;

import utils.ListNode;

import java.util.*;

/**
 * <h2>链表相交</h2>
 * <h3>1. 相交链表: 链表中没有环, 判断是否相交</h3>
 * <h3>2. 相交链表 II: 链表存在环, 判断是否相交</h3>
 */
public class IntersectionList
{
    public static void main(String[] args) {

    }


    /**
     * <h3>思路: 哈希表</h3>
     */
    private static ListNode getIntersectionNodeHash(ListNode l1, ListNode l2){
        Set<ListNode> set = new HashSet<>();
        // 将第一个链表的结点全部存放在哈希表中
        while(l1 != null){
            set.add(l1);
            l1 = l1.next;
        }
        // 开始检验是否有重复的结点
        while(l2 != null){
            if(set.contains(l2))
                return l2;
            l2 = l2.next;
        }
        return null;
    }

    /**
     * <h3>思路: 先计算两个链表的长度, 然后让长的链表先走差值, 最后一起开始移动</h3>
     */
    private static ListNode getIntersectionNode(ListNode l1, ListNode l2){
        int firstLength = getLength(l1);
        int secondLength = getLength(l2);
        ListNode longer = firstLength < secondLength ? l2: l1;
        ListNode shorter = firstLength < secondLength ? l1: l2;
        int distance = Math.abs(firstLength - secondLength);
        while (distance-- > 0){
            longer = longer.next;
        }
        while (longer != null && shorter != null){
            if (longer == shorter)
                return longer;
            longer = longer.next;
            shorter = shorter.next;
        }
        return null;
    }

    private static int getLength(ListNode list){
        int length = 0;
        while (list != null && ++length > 0){
            list = list.next;
        }
        return length;
    }

    /**
     * <h3>1. 两个链表都具有环, 但是不相交</h3>
     * <h3>2. 两个链表都有环, 且在入环之前相交</h3>
     * <h3>3. 两个链表都有环, 在入环之后相交</h3>
     * <h3>注: 不存在一个链表有环, 另外一个链表没环, 两个链表还相交的情况</h3>
     */
    private static ListNode getIntersectionNodeLoop(ListNode l1, ListNode l2) {
        // 1. 如果没有环, 那么就调用刚才的方法去执行
        ListNode firstNode = CircleList.detectCycle(l1);
        ListNode secondNode = CircleList.detectCycle(l2);
        if (firstNode == null && secondNode == null){
            return getIntersectionNode(l1, l2);
        }else if (firstNode != null && secondNode != null){
            // 2. 两个链表都有环存在, 根据入环结点判断具体是哪种情况
            // 2.1 如果两个入环结点是相同的, 那么认为两个链表在进入环之前就相交了, 和没有坏一样
            if (firstNode == secondNode){
                return getIntersectionNode(l1, l2);
            }else{
                // 2.2 如果两个入环结点都不相同, 那么就需要判断是剩下两种情况的哪一种
                // 注: 做法就是让第一个入环结点一直遍历, 如果在回到自己之前能够遇见另外一个入环结点, 那么就是第三种情况
                ListNode current = firstNode.next;
                while (current != firstNode){
                    // 这时候第一个入环结点或者第二个入环结点都可以视为相交结点
                    if (current == secondNode)
                        return firstNode;
                    current = current.next;
                }
            }
        }
        // 3. 如果一个链表有环, 另外一个链表没环, 那么是不可能相交的
        return null;
    }

}
