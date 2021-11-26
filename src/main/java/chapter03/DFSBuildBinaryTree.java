package chapter03;

import utils.TreeNode;

import java.util.*;

/**
 * <p>深度遍历构建二叉树</p>
 * <p>1. 中序遍历 + 前序遍历 构建二叉树</p>
 * <p>2. 中序遍历 + 后序遍历 构建二叉树</p>
 * <p>3. 构建最大二叉树: 根结点始终大于右子树和左子树</p>
 * <p>总结: 只要遇见使用数组构建二叉树的, 必然采用递归去构建, 几乎不需要考虑非递归, 而且返回值都是树的结点</p>
 * <p>并且递归的函数需要的参数也是固定的, 就是左边界和右边界, 以及数组</p>
 */
public class DFSBuildBinaryTree
{
    public static void main(String[] args)
    {

    }

    /**
     * <p>核心思路: 以中序遍历 + 前序遍历为例, 中序遍历 + 后序遍历是完全相同的</p>
     * <p>1. 前序遍历中的每个结点都可以看做是根结点, 而中序遍历中的根结点左右两侧一定是左右子树</p>
     * <p>2. 那么我们只需要找到根结点在中序遍历中的位置, 然后连接上左右两侧的子树就可以了</p>
     * <p>3. 子树内部就继续采用递归的形式生成</p>
     * <p>+--------------------------------------+</p>
     * <p>pre: 3 9 20 15 7</p>
     * <p>infix: 9 |3| 15 20 7</p>
     * <p>left: 9, right: 15 20 7</p>
     * <p>每个子树内部依旧采用这种方法划分就行</p>
     * <p>+--------------------------------------+</p>
     * @param infixorder 中序遍历生成的序列
     * @param postorder 后序遍历生成的序列
     * @param preorder  前序遍历生成的序列
     * @return 树的根结点
     */
    private static TreeNode buildBinaryTree(int[] infixorder, int[] postorder, int[] preorder){
        for(int i = 0;i < infixorder.length;i++){
            map.put(infixorder[i], i);
        }
        return buildBinaryTreeRecursive(infixorder, preorder,0, preorder.length - 1,0);
    }

    /**
     * <p>原始解法中, 每次都需要在中序数组循环遍历找到根结点的位置, 然后划分左右子树, 这样显然是浪费时间的</p>
     * <p>因为数组中的内容是不会发生改变的, 所以考虑能不能将索引和根结点的对应关系提前记录下来, 从而避免每次都去循环查询</p>
     * <p>有种特殊解法不需要使用额外空间, 同时也可以省去每次循环的开销, 但是不太容易想明白, 这里就不写了</p>
     */
    private static Map<Integer, Integer> map = new HashMap<>();

    /**
     * <p>中序遍历 + 前序遍历: 递归构建</p>
     * @param left 整个中序数组的左边界
     * @param right 整个中序数组的右边界
     * @param rootIndex 前序数组中根结点的索引
     */
    private static TreeNode buildBinaryTreeRecursive(int[] infixorder, int[] preorder, int left, int right, int rootIndex){
        if(left > right)
            return null;
        // 1.根据传递的根结点的索引, 创建出对应的根结点
        TreeNode root = new TreeNode(preorder[rootIndex]);
        // 2.找到根结点在中序数组中的位置, 然后根据根结点的位置开始划分左右子树
        int mid = map.get(root.value);
        // 3.1 左右子树的划分不要包括根结点
        // 3.2 右子树的根结点索引没有那么好确定, 不能够直接加一或者加二, 因为左右子树的根结点不一定是相邻的
        // 3.2 相隔的距离恰好是左子树的结点的个数, 所以右子树的根结点应该移动左子树结点数那么多的距离
        TreeNode leftNode = buildBinaryTreeRecursive(preorder, infixorder, left, mid - 1, rootIndex + 1);
        TreeNode rightNode = buildBinaryTreeRecursive(preorder, infixorder, mid + 1, right, rootIndex + mid - left + 1);
        // 4. 连接左子树和右子树
        root.left = leftNode;
        root.right = rightNode;

        return root;
    }

    /**
     * 中序遍历 + 前序遍历: 非递归形式构建
     */
    private static TreeNode buildBinaryTreeUnRecursive(int[] infixorder, int[] preorder){
        int index = 0;
        LinkedList<TreeNode> stack = new LinkedList<>();
        /**
         * <p>中序遍历的起始位置是树的最左侧的结点</p>
         * <p>所以不停地将左子结点压入栈中, 直到中序遍历的起始结点和前序遍历到的结点相同时, 就证明走到最左侧了</p>
         * <p>这个时候就可以开始考虑右子结点了</p>
         */
        stack.push(new TreeNode(preorder[0]));
        for (int i = 1; i < preorder.length; i++) {
            TreeNode node = stack.peek();
            // 只要前序遍历的结点还不等于中序遍历的结点, 那么就还没有走到左侧
            if (node.value != infixorder[index]){
                // 压栈的过程中顺便将新结点置为父结点的左孩子
                // 为什么是左子树的原因可以采用反证法证明
                node.left = new TreeNode(preorder[i]);
                stack.push(node.left);
            }
            // 走到左侧之后, 就开始弹出栈中的元素, 并且移动 index 指针
            else{
                // 栈中元素出栈的顺序和中序遍历的顺序恰好相同, 只要出现不同的元素时, 就证明这个元素时右孩子
                // 因为这个结点出现在两个父结点之间, 所以只能够是右孩子
                while (!stack.isEmpty() && stack.peek().value == infixorder[index++])
                    node = stack.pop();
                node.right = new TreeNode(preorder[i]);
                stack.push(node.right);
            }

        }

        return null;
    }


    /**
     * <p>核心思路</p>
     * <p>1. 每次都选择数组中的最大值作为根结点, 然后将其左右两次划分为左右子树</p>
     * <p>2. 左右子树分别继续找最大值, 构建二叉树, 构建完成后, 返回自己</p>
     * @param nums 无序的数组
     * @return 树的根结点
     */
    private static TreeNode buildMaximumBinaryTree(int[] nums){
        return recursive(nums, 0, nums.length - 1);
    }

    /**
     * 递归实现的时间复杂为 O(N^2)
     */
    private static TreeNode recursive(int[] nums, int left, int right){
        // 常见终止条件
        if (left > right)
            return null;
        int max = 0;
        int mid = 0;
        // 递归实现的最大二叉树构建是没有办法优化这个求最大值的过程的
        for (int i = left;i <= right;i++){
            if (max < nums[i]){
                mid = i;
                max = nums[i];
            }
        }
        // 创建根结点
        TreeNode root = new TreeNode(nums[mid]);
        // 连接左右子树
        root.left = recursive(nums, left, mid - 1);
        root.right = recursive(nums, mid + 1, right);

        return root;
    }

    /**
     * <p>评论区中有人提到非递归的写法, 保证空间复杂度的情况下, 可以让时间复杂度降低到 O(N)</p>
     * <p>1. 递归就是找到左右区间相等的位置停止, 那么此时这个元素一定就是树最底部的元素</p>
     * <p>2. 也就说递归是需要找到最小值才停止的, 这个非递归的方式应该就是用栈模拟这个思路完成的</p>
     * <p>3. 遍历数组的同时, 不停和前一个元素作判断</p>
     * <p>3.1 如果发现前一个元素大于字节, 那么只能够证明自己比较小, 但是不一定就是最小的那个, 所以需要进入栈中暂时保存</p>
     * <p>3.2 如果发现前一个元素小于字节, 那么就可以证明前一个元素恰好就是树的某一个边界值</p>
     * <p>4. 找到边界值之后, 显然就需要自底向上开始构建树了, 就开始不停地出栈</p>
     */
    private static TreeNode unrecursive(int[] nums){
        TreeNode node = null;
        LinkedList<TreeNode> stack = new LinkedList<>();
        // 数组最常见的操作自然就是遍历
        for (int i = 0; i < nums.length; i++) {
            node = new TreeNode(nums[i]);
            // 如果后一个元素比前一个元素大, 那么它一定是前一个元素的根结点
            while (!stack.isEmpty() && stack.peek().value < node.value){
                // 然后暂时先保存这个栈顶结点, 不能够直接将当前结点接上栈顶元素
                TreeNode temp = stack.pop();
                // 看下一个结点是否还小于当前结点
                // 如果新的栈顶元素还小于当前的结点并且在栈中, 就证明之前这个新的栈顶元素是大于出栈的元素, 并且小于当前的结点
                // 那么就应该连接之前出栈的元素
                if (!stack.isEmpty() && stack.peek().value < node.value)
                    stack.peek().right = temp;
                else
                    node.left = temp;
            }
            // 如果后一个元素比前一个元素小, 那么它只能是前一个元素的子结点, 那么暂时就无法连接子结点, 直接入栈
            stack.push(node);
        }

        // 最后里面会剩一些元素, 这些元素从栈顶到栈底是从小到大的顺序
        while (!stack.isEmpty()){
            node = stack.pop();
            if (!stack.isEmpty())
                stack.peek().right = node;
        }
        return node;
    }
}
