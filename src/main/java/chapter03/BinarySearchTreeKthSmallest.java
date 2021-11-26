package chapter03;

import utils.TreeNode;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>二叉搜索树中第 K 个最小元素</p>
 * <p>1. 完成这个问题非常简单, 中序遍历 + 计数就可以</p>
 * <p>2. 如果需要频繁查询第 K 个元素, 那应该怎么优化算法?</p>
 * <p>换句话说, 能不能将递归造成的时间开销进一步减少?这个进阶问题是不太容易的</p>
 */
public class BinarySearchTreeKthSmallest
{
    public static void main(String[] args)
    {

    }

    private static int count;

    /**
     * <p>通常需要先递归遍历到树最左侧的边界值, 然后开始回溯 K 次</p>
     * <p>这样的时间复杂度, 在通常情况下实际复杂度都是 O(H+K), 遍历到底+回溯, 最坏肯定是 O(N+K)</p>
     * <p>如果需要频繁对第 K 个最小的元素查询, 那么 N 次查询显然就是 NxO(H+K)</p>
     * @param root
     * @param k
     * @return
     */
    private static int kthSmallest(TreeNode root, int k){
        if(root == null)
            return -1;

        int left = kthSmallest(root.left, k);
        if(++count == k)
            return root.value;
        int right = kthSmallest(root.right, k);

        return left != -1 ? left : right;
    }


    /**
     * <p>这个优化策略倒不是很关键, 关键点在于想到这种优化策略的原因</p>
     * <p>假设我们已经知道整颗树的结点个数, 那么就可以利用这个结点个数来判断</p>
     * <p>1. 如果根结点左子树的结点个数小于 K, 那么显然左子树是不可能存在第 K 小的元素的, 向右子树搜索</p>
     * <p>2. 如果根结点左子树的结点个数大于 K<, 那么显然左子树是存在第 K 小的元素的, 继续向左子树搜索</p>
     * <p>3. 这种操作就避免了刚才那种方法的回溯行为, 节省部分的常量开销时间</p>
     * <p>4. 但是为了知道每个结点的左右子树的结点数量, 必须提前预处理, 也就是递归遍历用哈希表保存对应关系</p>
     * <p>N 次查询的效果, 显然是 O(N) + NxO(H), 更加灵活, 但是也没快多少</p>
     */
    private static int kthSmallestOptimize(TreeNode root, int k){
        MyBST bst = new MyBST(root);
        return bst.getKthSmallest(k);
    }

    private static class MyBST{
        private TreeNode root;
        private Map<TreeNode, Integer> map;

        public MyBST(TreeNode root) {
            this.root = root;
            this.map = new HashMap<>();
            this.countNode(this.root);
        }

        /**
         * 统计每个结点的左右子树的结点数量
         */
        private int countNode(TreeNode root){
            if (root == null)
                return 0;
            this.map.put(root, countNode(root.left) + countNode(root.right) + 1);
            return this.map.get(root);
        }

        public int getKthSmallest(int k){
            TreeNode node = root.left;
            while (node != null){
                int leftNodes = map.get(node);
                if (leftNodes < k - 1){
                    node = node.right;
                }else if (leftNodes == k - 1){
                    break;
                }else{
                    node = node.left;
                }
            }
            assert node != null;
            return node.value;
        }
    }
}
