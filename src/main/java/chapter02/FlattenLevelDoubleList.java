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


    private static ListNode flatten(ListNode head){
        recursiveOptimize(head);
        return head;
    }

    /**
     * <p>采用递归实现的扁平化多级链表</p>
     * <p>1. 如果发现某个结点存在子结点, 那么就直接去遍历子结点的链表</p>
     * <p>2. 遍历到结尾的时候, 就将进入子链表的结点指向尾结点就行</p>
     * <p>3. 我之前写的那个就是最优解, 但是为什么和那个嵌套循环的效率一样</p>
     * <p>4. 原因是因为, 我把子链表接上来之后, 是接着遍历子链表的, 实际上应该把下一个结点先保存就可以避免遍历子链表</p>
     */
    private static ListNode recursiveOptimize(ListNode head){
        // 0. 每次返回的都是前一个结点, 因为当前结点是遍历到空值结束
        ListNode previous = null;
        ListNode current = head;
        // 1. 开始遍历
        while(current != null){
            if (current.child == null){
                previous = current;
                current = current.next;
            }else{
                // 1.1 提前记录下一个结点, 避免父链表和子链表链接之后再去遍历
                ListNode next = current.next;
                ListNode tail = recursiveOptimize(current.child);
                tail.next = next;
                if (next != null)
                    next.previous = tail;
                current.next = current.child;
                current.child.previous = current;
                // 1.2 更新前驱结点和当前结点, 因为子链表和父链表链接了, 所以前驱应该是尾结点
                previous = tail;
                current = next;
            }


        }

        return previous;
    }

    /**
     * <p>每次返回的都是头结点</p>
     */
    private static ListNode recursive(ListNode head){

        ListNode current = head;
        while (current != null){
            if (current.child == null){
                current = current.next;
            }else{
                // 0. 记录下一个结点
                ListNode next = current.next;
                // 1. 将孩子结点扁平化, 然后和父结点进行连接, 子链表的尾结点暂时不连接
                ListNode child = recursive(current.child);
                current.next = child;
                child.previous = current;
                // 2. 记得将孩子结点置为空
                current.child = null;
                // 3. 开始遍历子链表直到结尾, 因为尾结点没有连接父链表的结点, 所以会遍历到空
                while (current.next != null)current = current.next;
                // 4. 将子链表的尾结点和父链表的下一个结点相连
                current.next = next;
                if (next != null)
                    next.previous = current.next;
                // 5. 结点继续向后移动
                current = next;
            }
        }
        // 6.1 原来的做法是每次都返回子链表的尾结点, 因为需要将尾结点和父结点的下一个结点连起来
        // 6.2 但是问题在于我们最后是要返回父链表的头结点的, 这个递归规则就出现不同, 此前就是采用利用层数判断是否返回头结点的
        // 6.3
        return head;
    }


}
