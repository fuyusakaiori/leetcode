package chapter09.dynamic.robhome;

import chapter03.TreeNode;

import java.util.HashMap;
import java.util.Map;

/**
 * <h3>打家劫舍 III</h3>
 */
public class RobHomeTree {

    private static final Map<TreeNode, Integer> dp = new HashMap<>();
    public static int rob(TreeNode root) {
        if(root == null)
            return 0;
        if(dp.containsKey(root))
            return dp.get(root);
        // 1. 不偷当前的房屋
        int first = rob(root.left) + rob(root.right);
        // 2. 偷取当前的房屋
        int second = root.value;
        if(root.left != null)
            second += rob(root.left.right) + rob(root.left.left);
        if(root.right != null)
            second += rob(root.right.left) + rob(root.right.right);
        int maxMoney = Math.max(first, second);
        dp.put(root, maxMoney);
        return maxMoney;
    }

}
