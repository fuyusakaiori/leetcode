package chapter03.binarytree;

import utils.TreeNode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * <h3>二叉树的最近公共祖先</h3>
 * <h3>1. 二叉树的最近公共祖先</h3>
 * <h3>2. 二叉树的搜索树的最近公共祖先</h3>
 */
public class BinaryTreeLowestAncestor {

    /**
     * <h3>思路: 二叉搜索树的最近公共祖先</h3>
     * <h3>1. 基于性质: 迭代和递归</h3>
     * <h3>1.1 如果两个结点的值都小于根结点, 那么必然最近的公共祖先也一定在左子树中</h3>
     * <h3>1.2 如果两个结点的值都大于根结点, 那么必然最近的公共祖先一定在右子树中</h3>
     * <h3>1.3 如果不满足的上述的情况, 那么就证明出现了分叉点, 这个分叉点实际上就是公共祖先</h3>
     * <h3>2. 直接使用二叉树的公共祖先的方法去做</h3>
     * <h3>注: 二叉搜索树的题目一定要使用性质, 这个题方法是有点巧妙</h3>
     * <h3>注: 如果想不起来这个题怎么做的, 就直接用二叉树的那个解法来做</h3>
     */
    private static TreeNode lowestCommonAncestorBST(TreeNode root, TreeNode firstNode, TreeNode secondNode){
        while (true){
            if (root.value > firstNode.value && root.value > secondNode.value)
                root = root.left;
            else if (root.value < firstNode.value && root.value < secondNode.value)
                root = root.right;
            else
                break;
        }
        return root;
    }

    private static TreeNode dfsBST(TreeNode root, TreeNode firstNode, TreeNode secondNode){
        if(root.value > firstNode.value && root.value > secondNode.value)
            return dfsBST(root.left, firstNode, secondNode);
        if(root.value < firstNode.value && root.value < secondNode.value)
            return dfsBST(root.right, firstNode, secondNode);
        return root;
    }

    /**
     * <h3>思路；二叉树的的最近公共祖先</h3>
     * <h3>1. 找路径, 然后比较: 这两个题实际上都可以共用这个思路</h3>
     * <h3>1.1 采用哈希表记录结点之间的父子关系</h3>
     * <h3>1.2 然后从下向上开始遍历, 获取到根结点到达其中一个结点的路径</h3>
     * <h3>1.3 然后从另一个结点开始, 从下向上遍历, 发现重复的就是公共结点</h3>
     * <h3>2. 深度遍历: 后序遍历</h3>
     */
    private static TreeNode lowestCommonAncestor(TreeNode root, TreeNode firstNode, TreeNode secondNode){
        Map<TreeNode, TreeNode> father = new HashMap<>();
        Set<TreeNode> set = new HashSet<>();
        father.put(root, root);
        dfs(root, father);

        while (firstNode != root){
            set.add(firstNode);
            firstNode = father.get(firstNode);
        }
        while (secondNode != root){
            if (set.contains(secondNode))
                return secondNode;
            secondNode = father.get(secondNode);
        }
        return root;
    }

    private static void dfs(TreeNode root, Map<TreeNode, TreeNode> father){
        if (root == null) return;
        father.put(root.left, root);
        father.put(root.right, root);
        dfs(root.left, father);
        dfs(root.right, father);
    }

    /**
     * <h3>思路: 后序遍历</h3>
     * <h3>1. 如果找到符合要求的结点就立刻返回这个结点, 如果遍历到叶子结点都没有, 那就返回空值</h3>
     * <h3>2. 根结点会受到两个子结点: </h3>
     * <h3>2.1 如果其中一个子结点为空, 那么证明那个子树里面没有符合要求的结点, 自己不是最近的公共祖先</h3>
     * <h3>2.2 如果两个子结点都为空, 也是同理</h3>
     * <h3>2.3 如果两个子结点不为空, 那么证明自己就是最近的公共祖先</h3>
     */
    private static TreeNode lowestCommonAncestorDFS(TreeNode root, TreeNode firstNode, TreeNode secondNode){
        if (root == null || root == firstNode || root == secondNode)
            return root;
        TreeNode left = lowestCommonAncestorDFS(root.left, firstNode, secondNode);
        TreeNode right = lowestCommonAncestorDFS(root.right, firstNode, secondNode);
        if (left != null && right != null)
            return root;
        // 注: 这里把两个结点为空, 和一个结点为空的情况合并到一起了
        return left != null ? left: right;
    }
}
