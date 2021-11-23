package chapter03;

import utils.TreeNode;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * <p>深度遍历构建二叉树</p>
 * <p>1. 中序遍历 + 前序遍历 构建二叉树</p>
 * <p>2. 中序遍历 + 后序遍历 构建二叉树</p>
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
     * <p>中序遍历 + 前序遍历构建二叉树</p>
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
}
