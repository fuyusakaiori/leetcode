package chapter03;

import utils.TreeNode;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

/**
 * <p>序列化和反序列化二叉树</p>
 * <p>1. 序列化和反序列化二叉搜索树</p>
 * <p>2. 序列化和反序列化普通的二叉树</p>
 * <p>3. 验证二叉树的前序序列化</p>
 */
public class BinaryTreeSerializable
{
    public static void main(String[] args)
    {
        String string = "123,";
        String substring = string.substring(0, string.length() - 1);
        System.out.println(substring);
    }

    /**
     * <p>采用层次遍历的方式序列化</p>
     * <p>规定: 空结点采用 # 表示, 每个结点结束添加 _</p>
     * @param root 根结点
     */
    private static String levelSerializable(TreeNode root){
        if (root == null)
            return null;
        // 细节: 采用 StringBuilder 拼接会比 String 直接拼接效率更高
        StringBuilder sb = new StringBuilder("");
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        // 细节: 这里最好分开写
        sb.append(root.value).append("_");
        while (!queue.isEmpty()){
            root = queue.poll();
            if (root.left != null){
                queue.offer(root.left);
                sb.append(root.left).append("_");
            }else{
                // 空节点也是需要添加相应的字符的
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
     * <p>反序列化</p>
     * <p>反序列化的操作相对复杂, 但是核心思路就是再来一遍, 重做之前的遍历</p>
     */
    private static TreeNode levelDeserializable(String data){
        // 1. 字符串切割成字符串数组
        String[] strings = data.split("_");
        // 2. 创建队列, 再次进行层次遍历
        Queue<TreeNode> queue = new LinkedList<>();
        // 3. 创建根结点
        TreeNode root = generateTreeNode(strings[0]);
        queue.offer(root);
        // 4. 用于遍历字符串的指针
        int index = 0;
        while (!queue.isEmpty()){
            root = queue.poll();
            root.left = generateTreeNode(strings[++index]);
            root.right = generateTreeNode(strings[++index]);
            // 核心；再来一遍
            if (root.left != null)
                queue.offer(root.left);
            if (root.right != null)
                queue.offer(root.right);
        }
        return root;
    }

    private static TreeNode generateTreeNode(String string){
        return "#".equals(string) ? null : new TreeNode(Integer.parseInt(string));
    }

    /**
     * <p>采用先序遍历的方式序列化</p>
     * <p>注意: 这里使用递归的形式</p>
     * @param root 根结点
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
     * <p>反序列化</p>
     * <p>核心: 再来一遍</p>
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
        if ("#".equals(value))
            return null;
        assert value != null;
        TreeNode root = new TreeNode(Integer.parseInt(value));
        root.left = prefix(queue);
        root.right = prefix(queue);
        return root;
    }

    // TODO 网上博客提到中序遍历似乎无法反序列化, 因为生成的树是不唯一的, 但是后序遍历貌似可以
    private static String infixSerializable(TreeNode root){
        if (root == null)
            return "#_";
        StringBuilder sb = new StringBuilder("");
        sb.append(prefixSerializable(root.left));
        sb.append(root.value).append("_");
        sb.append(prefixSerializable(root.right));
        return sb.toString();
    }

    // TODO 第三个题目的要求比较奇怪, 必须在不重构树的情况下, 判断这个前序序列化是否有效


}
