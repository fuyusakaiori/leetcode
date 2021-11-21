package chapter03.travel;

import utils.TreeNode;

import java.util.LinkedList;

/**
 * <h2>采用递归 + 非递归的方式遍历二叉树结构</h2>
 * <p>时间复杂度: O(N)</p>
 * <p>空间复杂度: O(N)</p>
 * <p>注: 这里采用方法是最基础的遍历方式, 有另外一种能让空间复杂度达到 O(N) 的算法</p>
 */
public class BinaryTreeDeepFirstSearch
{
    /**
     * <p>递归和循环两种方式是可以等价转换的</p>
     * <p>不过难易程度存在差别, 有些题可能采用递归实现简单, 有些题可能采用循环实现简单</p>
     */
    public static void main(String[] args)
    {

    }

    /**
     * 采用递归方式实现的前序遍历
     * @param root 根结点
     */
    private static void preorderRecursive(TreeNode root){
        if (root == null)
            return;
        System.out.print(root.value + "->");
        preorderRecursive(root.left);
        preorderRecursive(root.right);
    }

    /**
     * 采用非递归的实现的前序编列, 思路和递归实现的前序遍历完全相同
     * @param root 根结点
     */
    private static void preorderUnRecursive(TreeNode root){
        if (root == null)
            return;
        LinkedList<TreeNode> stack = new LinkedList<>();
        stack.push(root);
        while (!stack.isEmpty()){
            root = stack.pop();
            System.out.print(root.value + "->");
            if (root.left != null)
                stack.push(root.left);
            if (root.right != null)
                stack.push(root.right);
        }
    }

    /**
     * 采用递归实现的中序遍历
     * @param root 根结点
     */
    private static void infixorderRecursive(TreeNode root){
        if(root == null)
            return;
        infixorderRecursive(root.left);
        System.out.print(root.value + "->");
        infixorderRecursive(root.right);
    }

    /**
     * <p>采用非递归实现的中序遍历, 和递归实现的有所区别, 没有办法在递归上进行修改得到</p>
     * <p>原因在于, 中序遍历的结点访问顺序和处理顺序是不同的, 先访问父结点, 但是先处理左子结点</p>
     * <p>实际上我们只处理了左子树和父结点, 右子树也被转化成左子树和父结点处理了</p>
     * @param root 根结点
     */
    private static void infixorderUnRecursive(TreeNode root){
        LinkedList<TreeNode> stack = new LinkedList<>();
        // 0. root != null 是因为栈初始是空, 确保可以进入循环
        while (!stack.isEmpty() || root != null){
            // 1. 持续向左子树遍历, 并且将父结点压入栈中, 暂不处理
            if(root != null){
                // 1.1 只要结点不为空, 那么就压入栈中
                stack.push(root);
                // 1.2 然后继续向左压栈, 直到遍历到最左的结点
                root = root.left;
            }
            // 2. 遍历到左子树的边界结点时, 就可以开始出栈, 并处理结点
            else{
                // 2.1 父结点出栈
                root = stack.pop();
                // 2.2 处理父结点结点
                System.out.print(root.value + "->");
                // 2.3 访问右子树
                root = root.right;
            }

        }
    }

    /**
     * 采用递归实现的后序遍历
     * @param root 根结点
     */
    private static void postorderRecursive(TreeNode root){
        if (root == null)
            return;
        postorderRecursive(root.left);
        postorderRecursive(root.right);
        System.out.print(root.value + "->");
    }

    /**
     * <p>采用非递归实现的后序遍历根据刚才提到的原因, 显然也是没有办法在递归上进行修改得到的</p>
     * <p>有两种实现方式: 1.采用双栈实现 2. 采用单个栈实现</p>
     * <p>1. 采用双栈实现: 得到父->右->左的顺序, 然后逆序输出, 就是左->右->父</p>
     * <p>2. 采用单个栈实现: 出栈的时候借助临时变量判断是继续压栈, 还是接着出栈</p>
     * @param root 根结点
     */
    private static void postorderUnRecursiveDoubleStack(TreeNode root){
        if (root == null)
            return;
        LinkedList<TreeNode> stack = new LinkedList<>();
        LinkedList<TreeNode> collection = new LinkedList<>();
        stack.push(root);
        while (!stack.isEmpty()){
            root = stack.pop();
            // 1. 父结点出栈之后进入另一个栈, 这样确保父结点最后出栈
            collection.push(root);
            // 2. 压入右子结点, 确保之后右子结点先进入另一个栈, 这样就会在左子结点之后输出
            if (root.right != null)
                stack.push(root.right);
            if (root.left != null)
                stack.push(root.left);
        }
        // 3. 将收集栈依次出栈
        while (!collection.isEmpty()){
            System.out.print(collection.pop() + "->");
        }
    }

    private static void postorderUnRecursiveSingleStack(TreeNode root){
        if (root == null)
            return;
        TreeNode current = null;
        TreeNode last = root;
        LinkedList<TreeNode> stack = new LinkedList<>();
        stack.push(root);
        while (!stack.isEmpty()){
            current = stack.peek();
            if (current.left != null && last != current.left)
                stack.push(current.left);
            else if (current.right != null && last != current.right)
                stack.push(current.right);
            else
                System.out.print((last = stack.pop()) + "->");
        }
    }

}
