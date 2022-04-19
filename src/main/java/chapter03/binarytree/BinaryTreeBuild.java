package chapter03.binarytree;

import utils.TreeNode;

import java.util.*;

/**
 * <h2>利用深度遍历构建二叉树</h2>
 * <h3>1. 从前序和中序遍历构建二叉树</h3>
 * <h3>2. 从后序和中序遍历构建二叉树</h3>
 * <h3>3. 构建最大二叉树</h3>
 */
public class BinaryTreeBuild {

    /**
     * <h3>核心思路: 以中序遍历 + 前序遍历为例, 中序遍历 + 后序遍历是完全相同的</h3>
     * <h3>1. 前序遍历中的每个结点都可以看做是根结点, 而中序遍历中的根结点左右两侧一定是左右子树</h3>
     * <h3>2. 那么我们只需要找到根结点在中序遍历中的位置, 然后连接上左右两侧的子树就可以了</h3>
     * <h3>3. 子树内部就继续采用递归的形式生成</h3>
     * <p>+--------------------------------------+</h3>
     * <h3>pre: 3 9 20 15 7</h3>
     * <h3>infix: 9 |3| 15 20 7</h3>
     * <h3>left: 9, right: 15 20 7</h3>
     * <h3>每个子树内部依旧采用这种方法划分就行</h3>
     * <h3>+--------------------------------------+</h3>
     * <h3>注: 前序和后序是没有办法生成唯一的二叉树的, 根结点是不确定的</h3>
     * <h3>注: 数组中的元素不能够重复, 否则根结点也不确定</h3>
     * @param infixorder 中序遍历生成的序列
     * @param preorder  前序遍历生成的序列
     * @return 树的根结点
     */
    private static TreeNode buildTree(int[] infixorder, int[] preorder){
        for(int i = 0;i < infixorder.length;i++){
            map.put(infixorder[i], i);
        }
        return dfs(0, preorder, 0, infixorder.length - 1, infixorder);
    }

    /**
     * <h3>原始解法中, 每次都需要在中序数组循环遍历找到根结点的位置, 然后划分左右子树, 这样效率肯定低</h3>
     * <h3>所以采用哈希表保存元素的值和索引的关系, 这样就不用每次都去遍历了</h3>
     */
    private final static Map<Integer, Integer> map = new HashMap<>();

    /**
     * <h3>前序 + 中序构建</h3>
     */
    private static TreeNode dfs(int index, int[] preorder, int left, int right, int[] infixorder){
        if(left > right) return null;
        TreeNode root = new TreeNode(preorder[index]);
        int mid = map.get(preorder[index]);
        // 3.1 左右子树的划分不要包括根结点
        // 3.2 右子树的根结点索引没有那么好确定, 不能够直接加一或者加二, 因为左右子树的根结点不一定是相邻的
        // 3.2 相隔的距离恰好是左子树的结点的个数, 所以右子树的根结点应该移动左子树结点数那么多的距离
        root.left = dfs(index + 1, preorder, left, mid - 1, preorder);
        root.right = dfs(index + mid - left + 1, preorder, mid + 1, right, preorder);
        return root;
    }


    /**
     * <h3>前序 + 中序思路: 栈</h3>
     * <h3>注: 这个做法属实是没啥印象了... </h3>
     */
    private static TreeNode buildTreeStack(int[] preorder, int[] infixorder){
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
     * <h3>思路: </h3>
     * <h3>1. 每次都选择数组中的最大值作为根结点, 然后将其左右两次划分为左右子树</h3>
     * <h3>2. 左右子树分别继续找最大值, 构建二叉树, 构建完成后, 返回自己</h3>
     * @param nums 无序的数组
     * @return 树的根结点
     */
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        return dfs(0, nums.length - 1, nums);
    }

    public TreeNode dfs(int left, int right, int[] nums){
        if(left > right) return null;
        int maxIndex = getMaxIndex(left, right, nums);
        TreeNode root = new TreeNode(nums[maxIndex]);
        root.left = dfs(left, maxIndex - 1, nums);
        root.right = dfs(maxIndex + 1, right, nums);
        return root;
    }

    public int getMaxIndex(int left, int right, int[] nums){
        int max = 0;
        int maxIndex = 0;
        for(int index = left;index <= right;index++){
            if(max <= nums[index]){
                max = nums[index];
                maxIndex = index;
            }
        }
        return maxIndex;
    }

    /**
     * <h3>单调栈 </h3>
     * <h3>1. 递归就是找到左右区间相等的位置停止, 那么此时这个元素一定就是树最底部的元素</h3>
     * <h3>2. 也就说递归是需要找到最小值才停止的, 这个非递归的方式应该就是用栈模拟这个思路完成的</h3>
     * <h3>3. 遍历数组的同时, 不停和前一个元素作判断</h3>
     * <h3>3.1 如果发现前一个元素大于自己, 那么只能够证明自己比较小, 但是不一定就是最小的那个, 所以需要进入栈中暂时保存</h3>
     * <h3>3.2 如果发现前一个元素小于自己, 那么就可以证明前一个元素恰好就是树的某一个边界值</h3>
     * <h3>4. 找到边界值之后, 显然就需要自底向上开始构建树了, 就开始不停地出栈</h3>
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
