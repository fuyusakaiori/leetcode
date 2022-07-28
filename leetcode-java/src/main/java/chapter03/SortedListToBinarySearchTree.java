package chapter03;

import utils.ListNode;
import utils.TreeNode;

import java.util.*;

/**
 * 构建高度平衡的二叉搜索树
 * <p>1. 有序数组转换成二叉搜索树</p>
 * <p>2. 有序链表转换成二叉搜索树</p>
 * <p>相关结论</p>
 * <p>1. 中序遍历二叉搜索树可以得到唯一确定的中序序列, 但是中序序列无法确定唯一的二叉搜索树</p>
 * <p>因为可以以任何结点为根结点重建二叉搜索树, 这样建出来的树基本都是符合要求的</p>
 * <p>2. 中序遍历 + 高度平衡依然不能够确定唯一的二叉搜索树</p>
 * <p>3. 根据数组或者链表重建二叉树相关的题目, 通常都是采用递归的做法完成的, 非递归不太好写</p>
 */
public class SortedListToBinarySearchTree
{
    /**
     * <p>核心思路</p>
     * <p>1. 每次选择数组的中间元素作为根结点构造二叉搜索树</p>
     * <p>2. 这样左右两侧的高度差的绝对值一定小于等于 1</p>
     * <p>3. 然后不断递归构造, 最后就能够得到一棵高度平衡的二叉搜索树</p>
     * <p>产生的问题?</p>
     * <p>1. 为什么选择中间元素作为根结点就一定是高度平衡的二叉搜索树?</p>
     * <p>解释: 有一点贪心的策略在里面, 或者说有一点凭感觉</p>
     * <p>2. 如果数组的长度为偶数, 那么选择靠右的中间结点, 还是选择靠左的中间结点</p>
     * <p>解释: 这里实际上选择哪个都是可以的, 可以一直选择靠左边或者靠右边的中间结点, 也可以随机选择, 类似于快排选择基准</p>
     * <p>无非是构建出来的二叉搜索树的结构是不同的</p>
     */
    public static void main(String[] args)
    {
        LinkedList<Integer> list = new LinkedList<>();
        list.toArray(new Integer[0]);
    }

    /**
     * 有序数组转换高度平衡的二叉搜索树
     * @param infixorder 中序序列
     * @return 树的根结点
     */
    private static TreeNode sortedArrayToBST(int[] infixorder){

        return sortedArrayToBSTRecursive(infixorder, 0, infixorder.length - 1);
    }

    private static TreeNode sortedArrayToBSTRecursive(int[] infixorder, int left, int right){
        if (left > right)
            return null;
        int mid = left + ((right - left) >> 1);
        TreeNode root = new TreeNode(infixorder[mid]);
        root.left = sortedArrayToBSTRecursive(infixorder, left, mid - 1);
        root.right = sortedArrayToBSTRecursive(infixorder, mid + 1, right);
        return root;
    }

    /**
     * <p>有序链表转换成高度平衡的二叉搜索树</p>
     * <p>链表没有办法像数组一样找到中间结点, 但是有两个办法依然可以找到中间结点</p>
     * <p>1. 遍历链表, 将链表存放到数组中处理, 链表的万能解决方案</p>
     * <p>2. 利用快慢指针找中间结点, 避免额外空间的使用</p>
     * <p>3. 优化策略: 采用中序遍历优化</p>
     * @param head 链表的头结点
     * @return 树的根结点
     */
    private static TreeNode sortedListToBST(ListNode head){
        if(head == null)
            return null;
        // 1. 不需要提前找到链表的尾结点作为右边界
        // 1.1 本身快慢指针找中点就只需要一个起始位置就可以, 不需要右边界
        // 1.2 但是左边界找中点的时候是需要右边界的, 右边界找中点的时候是不需要的
        return recursive(head, null);
    }

    public static TreeNode recursive(ListNode left, ListNode right){
        if(left == right)
            return null;
        // 2. 慢指针到达中间结点
        ListNode slow = left;
        ListNode fast = left;
        // 2.1 如果采用官方题解的写法, 是不需要前驱结点的
        // 2.2 这里需要将停止条件换成右边界
        // 2.3 左边界找中点的时候, right 是存在值的; 右边界找中点的时候, right 是空的
        // 2.4 这样可以避免利用前驱结点将 next 指针指向空, 来让循环停止的行为
        while(fast != right && fast.next != right){
            slow = slow.next;
            fast = fast.next.next;
        }
        TreeNode root = new TreeNode(slow.value);
        // 3. 这里传入的结点就是中间结点, 我之前传入的是中间结点的前一个结点, 也是可以做的
        root.left = recursive(left, slow);
        root.right = recursive(slow.next, right);
        return root;
    }

    private static ListNode midNode;
    private static TreeNode sortedListBSTOptimize(ListNode head){

        int length = 0;
        midNode = head;
        while(head != null && ++length > 0)
            head = head.next;
        return recursiveOptimize(0, length - 1);
    }

    /**
     * <p>这个优化思路是非常巧妙的, 目标是避免每次递归的时候循环查找中间结点</p>
     * <p>1. 分治的过程每次都是以中间结点为基准, 找中间结点的目的, 就是为了给新建的树结点赋值</p>
     * <p>2. 这个分治的过程非常类似于中序遍历, 走到最左边之后是会返回到中间结点的, 也就是父结点</p>
     * <p>3. 结合上述两条, 那么我们是否可以考虑第一次到达中间结点的时候, 不进行任何赋值</p>
     * <p>等到第二次回来的时候不就自然是中间结点了吗? 这样就避免每次采用快慢指针查找中间结点</p>
     * @param left 左边界索引
     * @param right 右边界索引
     * @return 树的根结点
     */
    private static TreeNode recursiveOptimize(int left, int right) {
        if(left > right)
            return null;
        int mid = left + ((right - left) >> 1);
        // 暂时不填充根结点的值, 等到中序遍历回到父结点的时候再填充值
        TreeNode root = new TreeNode();
        // 向左侧递归
        root.left = recursiveOptimize(left, mid - 1);
        // 回来的位置一定是中间结点
        root.value = midNode.value;
        // 将结点向后移动, 也是中间结点
        midNode = midNode.next;
        // 向右侧递归
        root.right = recursiveOptimize(mid + 1, right);
        return root;
    }
}
