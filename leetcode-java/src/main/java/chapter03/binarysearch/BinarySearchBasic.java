package chapter03.binarysearch;

import utils.TreeNode;

import java.util.LinkedList;

/**
 * <h2>二叉搜索树中的基本操作</h2>
 * <h3>1. 二叉搜索树中的查询</h3>
 * <h3>2. 二叉搜索树中第 K 小的元素</h3>
 * <h3>3. 二叉搜索树中的删除</h3>
 * <h3>注: 二叉搜索树的查询或者遍历使用迭代的方式, 不要用递归, 包括二叉树也一样</h3>
 */
public class BinarySearchBasic {


    /**
     * <h3>思路: </h3>
     * <h3>1. 深度遍历</h3>
     * <h3>2. 迭代</h3>
     * <h3>注: 面试题就不要写递归了...</h3>
     */
    private static TreeNode searchBST(TreeNode root, int target){

        while (root != null){
            if (root.value == target)
                return root;
            root = root.value > target ? root.left: root.right;
        }
        return null;
    }

    private static TreeNode searchDFS(TreeNode root, int target){
        if(root == null || root.value == target) return root;
        return root.value > target ? searchBST(root.left, target): searchBST(root.right, target);
    }

    /**
     * <h3>思路: 二叉搜索树中的第 K 小的元素 </h3>
     * <h3>1. 放入链表中后排序得到: 笔试题就可以这么做, 不用动脑子</h3>
     * <h3>2. 中序遍历: 递归或者迭代</h3>
     * <h3>3. 莫里斯遍历: 如果要求空间复杂度为 O(1) </h3>
     */
    private static int kthSmallest(TreeNode root, int k){
        int index = 0;
        LinkedList<TreeNode> stack = new LinkedList<>();
        while (!stack.isEmpty() || root != null){
            if(root != null){
                stack.push(root);
                root = root.left;
            }else{
                root = stack.pop();
                if(index++ == k)
                    return root.value;
                root = root.right;
            }
        }
        return -1;
    }

    private static int count;

    private static int dfs(TreeNode root, int k){
        if(root == null)
            return -1;

        int left = kthSmallest(root.left, k);
        if(++count == k)
            return root.value;
        int right = kthSmallest(root.right, k);

        return left != -1 ? left : right;
    }

    private static int morris(TreeNode root, int k){
        int index = 1;
        TreeNode current = root;
        TreeNode mostRight = null;
        while (current != null){
            if (current.left != null){
                mostRight = current.left;
                while (mostRight.right != null && mostRight.right != current)
                    mostRight = mostRight.right;
                if (mostRight.right == null){
                    mostRight.right = current;
                    current = current.left;
                    continue;
                }else{
                    mostRight.right = null;
                    if (index++ == k) return current.value;
                }
            }else{
                if(index++ == k) return current.value;
            }
            current = current.right;
        }
        return -1;
    }

    /**
     * <h3>思路: 删除二叉搜索树中的结点</h3>
     * <h3>1. 如果删除的是叶子结点, 那么直接删除就行</h3>
     * <h3>2. 如果删除的是非叶子结点, 那么需要分为两种情况</h3>
     * <h3>2.1 非叶子结点具有一个叶子结点, 直接让父结点指向孙子结点</h3>
     * <h3>2.2 非叶子结点具有两个叶子结点, 找左子树的最右结点或者右子树的最左结点替换删除的结点</h3>
     * <h3>注: 这个应该是没有迭代的写法...</h3>
     * <h3>注: 这个是我自己的写法, 官方题解写得没这么直白...之后有空再看</h3>
     */
    private static TreeNode deleteBST(TreeNode root, int target){
        // 注: 主要目的是为了删除头部, 这里为了简化使用 next 指针
        TreeNode dummy = new TreeNode(root.value);
        dummy.next = root;
        dfs(root, dummy, target);
        return dummy.next;
    }

    private static void dfs(TreeNode root, TreeNode parent, int target){

        if (root.value > target){
            dfs(root.left, root, target);
        }else if (root.value < target){
            dfs(root.right, root, target);
        }else{
            if (root.left == null && root.right == null){
                // 如果删除的叶子结点是父结点的左子结点, 那么父结点的左孩子置为空值即可, 右子结点同理
                if (parent.left == root) parent.left = null;
                if (parent.right == root) parent.right = null;
            }else if (root.left != null && root.right != null){
                TreeNode upper = root;
                TreeNode node = root.right;
                while (node.left != null) {
                    upper = node;
                    node = node.left;
                }
                if (parent.next == root){
                    parent.next = node;
                }else if (parent.left == root){
                    parent.left = node;
                }else if (parent.right == root){
                    parent.right = node;
                }
                // 注: 无论当前结点的右子树是否为空, 当前结点的父结点都需要接手这个右子树
                // 注: 不过如果要删除的结点的右子结点就是当前结点, 那么就不需要接手了
                if (root.right != node) upper.left = node.right;
                // 更改当前结点的引用, 以达到删除目标结点的目的
                node.left = root.left;
                // 如果要删除的结点的右子结点就是当前结点, 那么就不要连接了, 会出现环
                if (root.right != node)
                    node.right = root.right;
            }else{
                TreeNode node = root.left != null ? root.left: root.right;
                if (parent.next == root)
                    parent.next = node;
                else if (parent.left == root)
                    parent.left = node;
                else if (parent.right == root)
                    parent.right = node;
            }
        }
    }

    /**
     * <h3>思路: 向二叉搜索树中的插入结点</h3>
     * <h3>1. 先根据二叉搜索水的特性查找到相应的位置</h3>
     * <h3>2. 然后将当前需要插入的值封装成结点, 然后返回即可</h3>
     * <h3>注: 迭代和递归都可以实现</h3>
     */
    private static TreeNode insertBST(TreeNode root, int value){
        if (root == null)
            return new TreeNode(value);
        TreeNode current = root;
        while(current.left != null || current.right != null){
            if (current.left != null && current.value > value)
                current = current.left;
            else if (current.right != null && current.value < value)
                current = current.right;
            else
                break;
        }
        if (current.value > value) current.left = new TreeNode(value);
        if (current.value < value) current.right = new TreeNode(value);
        return current;
    }

    private static TreeNode insertDFS(TreeNode root, int value){
        if (root == null)
            return new TreeNode(value);
        if (root.value > value)
            root.left = insertDFS(root.left, value);
        if (root.value < value)
            root.right = insertDFS(root.right, value);
        return root;
    }

}
