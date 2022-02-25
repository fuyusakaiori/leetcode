package chapter02;

import utils.ListNode;

/**
 * <h2>扁平化多级双向链表</h2>
 */
public class FlattenLevelDoubleList
{
    public static void main(String[] args)
    {

    }

    /**
     * <h3>注意事项: </h3>
     * <h3>递归返回的是尾结点, 但是方法最后应该返回头结点, 所以需要另起方法, 但不需要这个方法的返回值</h3>
     */
    private static ListNode flatten(ListNode head){
        if (head != null)
            dfs(head);
        return head;
    }

    /**
     * <h3>思路: </h3>
     * <p>1. 如果发现某个结点存在子结点, 那么就直接去遍历子结点的链表</p>
     * <p>2. 遍历到子链表的结尾时, 就直接返回尾结点: 注意这里一定是返回尾结点, 不要返回成空节点/p>
     * <p>3. 将子链表的尾结点连接当前的结点的下个结点, 然后将当前结点的下个结点改为自己的子结点</p>
     * <p>4. 当前结点下次直接移动到尾结点之后, 不要移动到下一个结点, 不然就会又遍历一次子链表</p>
     */
    private static ListNode dfs(ListNode head){
        // 注: 此前使用四个变量, 实际只需要两个变量, 当前结点和尾结点
        ListNode current = head;
        ListNode tail = null;
        // 注: 这里没有采用 current.next != null 作为终止条件, 因为会导致只有一个元素的链表无法判断子结点
        while (current != null){
            // 如果没有子结点就直接向后遍历, 尾结点记录上一个结点
            if (current.child == null){
                tail = current;
                current = current.next;
            }else{
                tail = dfs(current.child);
                // 尾结点连接下一个结点
                tail.next = current.next;
                if (current.next != null)
                    current.next.previous = tail;
                // 当前结点连接子结点
                current.next = current.child;
                current.child.previous = current;
                // 当前结点移动到尾结点
                current = tail;
            }
        }
        return tail;
    }


}
