package utils;

import chapter03.TreeNode;
import lombok.Data;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Data
public class RandomUtil
{
    private int maxSize;
    private int maxValue;
    private final Random random;

    public RandomUtil(int maxSize, int maxValue)
    {
        this.maxSize = maxSize;
        this.maxValue = maxValue;
        random = new Random();
    }

    //==============================随机生成数组=============================
    public int[] randomArrayNoReplica(){
        int index = 0;
        int[] res = new int[this.maxSize];
        Set<Integer> set = new HashSet<>();
        while (set.size() != this.maxSize){
            set.add(random.nextInt(this.maxValue));
        }

        for (int integer : set) {
            res[index++] = integer;
        }
        return res;
    }

    public int[] randomArrayWithReplica(){
        int[] res = new int[this.maxSize];
        for (int i = 0; i < res.length; i++) {
            res[i] = random.nextInt(this.maxValue);
        }
        return res;
    }

    //==============================随机生成链表============================
    public ListNode randomList(){
        int index = 0;
        ListNode head = null;
        while (index < maxSize){
            ListNode node = new ListNode();
            node.value = random.nextInt(this.maxValue);
            node.next = head;
            head = node;
            index++;
        }
        return head;
    }

    // =============================随机生成树==============================
    @Deprecated
    public TreeNode randomNormalTree(){
        int index = 0;
        int choose = -1;
        TreeNode root = null;
        while (index < maxSize){
            if (root == null){
                root = new TreeNode(this.random.nextInt(this.maxValue));
            }
            choose = this.random.nextInt(2);
            if (choose == 0){
                root.left = new TreeNode(this.random.nextInt(this.maxValue));
            }
            else if (choose == 1){
                root.right = new TreeNode(this.random.nextInt(this.maxValue));
            }
            index++;
        }
        return root;
    }
}
