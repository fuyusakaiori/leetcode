package chapter03.binarytree;

import utils.TreeNode;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

/**
 * <h2>二叉树的序列化和反序列化</h2>
 * <h3>1. 序列化和反序列化二叉搜索树</h3>
 * <h3>2. 序列化和反序列化普通的二叉树</h3>
 * <h3>3. 验证二叉树的前序序列化</h3>
 * <h3>注: 中序遍历和后序遍历是无法确定二叉树的, 所以无法使用中序遍历来序列化</h3>
 */
public class BinaryTreeSerializable {

    /**
     * <h3>序列化思路: 层序遍历</h3>
     * <h3>规定: 空结点采用 # 表示, 每个结点结束添加 _</h3>
     */
    private static String levelSerializable(TreeNode root){
        if (root == null) return null;
        StringBuilder sb = new StringBuilder();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        sb.append(root.value).append("_");
        while (!queue.isEmpty()){
            root = queue.poll();
            if (root.left != null){
                queue.offer(root.left);
                sb.append(root.left).append("_");
            }else{
                sb.append("#_");
            }
            if (root.right != null){
                queue.offer(root.right);
                sb.append(root.right).append("_");
            }else{
                sb.append("#_");
            }
        }
        return sb.toString();
    }

    /**
     * <h3>反序列化思路: 重做层序遍历</h3>
     * <h3>反序列化的操作相对复杂, 但是核心思路就是再来一遍, 重做之前的遍历</h3>
     */
    private static TreeNode levelDeserializable(String data){
        if (data == null) return null;
        // 1. 字符串切割成字符串数组
        String[] strings = data.split("_");
        // 2. 创建队列, 再次进行层次遍历
        Queue<TreeNode> queue = new LinkedList<>();
        // 3. 创建根结点
        TreeNode root = generateTreeNode(strings[0]);
        queue.offer(root);
        // 4. 用于遍历字符串的指针
        int index = 0;
        // 注: 这里不要直接用根结点遍历, 因为最后要返回的是根结点
        TreeNode current = root;
        while (!queue.isEmpty()){
            current = queue.poll();
            current.left = generateTreeNode(strings[++index]);
            current.right = generateTreeNode(strings[++index]);
            // 核心；再来一遍
            if (current.left != null)
                queue.offer(current.left);
            if (current.right != null)
                queue.offer(current.right);
        }
        return current;
    }

    /**
     * <h3>根结点字符串的值创建节点</h3>
     */
    private static TreeNode generateTreeNode(String string){
        return "#".equals(string) ? null : new TreeNode(Integer.parseInt(string));
    }

    /**
     * <h3>序列化思路: 前序遍历</p>
     */
    private static String prefixSerializable(TreeNode root){
        if (root == null)
            return "#_";
        StringBuilder sb = new StringBuilder("");
        sb.append(root.value).append("_");
        sb.append(prefixSerializable(root.left));
        sb.append(prefixSerializable(root.right));
        return sb.toString();
    }

    /**
     * <h3>反序列化思路: 队列 + 前序遍历重做</h3>
     * <h3>1. 不能够想此前两个数组那样重构二叉树, 因为单个数组本身没办法确认</h3>
     * <h3>2. 所以需要依靠队列来每次出队</h3>
     */
    private static TreeNode prefixDeserializable(String data){
        String[] strings = data.split("_");
        // 注意: 这里需要借助队列存放所有字符串元素
        Queue<String> queue = new LinkedList<>();
        Collections.addAll(queue, strings);
        return prefix(queue);
    }

    private static TreeNode prefix(Queue<String> queue){
        String value = queue.poll();
        // 注: 不要将出队操作写在判断条件里, 否则不满足条件的话, 会出两次队
        if ("#".equals(value))
            return null;
        assert value != null;
        TreeNode root = new TreeNode(Integer.parseInt(value));
        root.left = prefix(queue);
        root.right = prefix(queue);
        return root;
    }

    /**
     * <h3>思路: 序列化二叉搜素树</h3>
     */
    private static String serializableBST(TreeNode root){

        return null;
    }

    private static TreeNode deserializableBST(String data){

        return null;
    }


}
