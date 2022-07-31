package chapter03.travel;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>N叉树的相关问题: 非常简单</p>
 * <p>1. N叉树的前序遍历、后序遍历</p>
 * <p>2. N叉树的层序遍历</p>
 * <p>3. N叉树的最大深度</p>
 */
public class NTreeTravel {

    private static final class NTreeNode{
        private int value;
        private NTreeNode[] children;
    }

    private static List<Integer> preorderNTree1(NTreeNode root){
        return null;
    }

    private static List<Integer> preorderNTree2(NTreeNode root){
        List<Integer> values = new LinkedList<>();
        LinkedList<NTreeNode> stack = new LinkedList<>();
        if (root != null){
            stack.push(root);
        }
        while (!stack.isEmpty()){
            root = stack.pop();
            values.add(root.value);
            for (int index = root.children.length - 1; index >= 0; index--) {
                if (root.children[index] != null){
                    stack.push(root.children[index]);
                }
            }
        }
        return values;
    }

    private static List<Integer> postorderNTree1(NTreeNode root){
        return null;
    }

    private static List<Integer> postorderNTree2(NTreeNode root){
        List<Integer> values = new LinkedList<>();
        LinkedList<NTreeNode> stack = new LinkedList<>();
        LinkedList<NTreeNode> collection = new LinkedList<>();
        if (root != null){
            stack.push(root);
        }
        while (!stack.isEmpty()){
            root = stack.pop();
            collection.push(root);
            for (int index = 0; index < root.children.length;index++) {
                NTreeNode node = root.children[index];
                if (node != null){
                    stack.push(node);
                }
            }
        }
        while (!collection.isEmpty()){
            values.add(collection.pop().value);
        }
        return values;
    }

}
