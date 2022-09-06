package chapter03.balance;

import java.util.Arrays;

/**
 * <h2>设计跳表</h2>
 */
public class SkipList {

    /**
     * <h3>跳表结点</h3>
     */
    private static class Node{
        // 结点的值
        private final int value;
        // 结点每层的下一个结点
        private final Node[] nexts;

        public Node(int value, int maxLevel) {
            this.value = value;
            this.nexts = new Node[maxLevel];
        }
    }

    /**
     * <h3>每层数量之间的关系</h3>
     */
    private static final double SKIP_LIST_THRESH = 0.5;
    /**
     * <h3>最大的层数</h3>
     */
    private static final int MAX_LEVEL = 10;

    /**
     * <h3>每层都有哑元</h3>
     */
    private final Node head;

    private int level;
    public SkipList(){
        this.head = new Node(-1, MAX_LEVEL);
        this.level = 0;
    }

    /**
     * <h3>查询结点</h3>
     */
    public boolean search(int value){
        Node cur = this.head;
        for (int idx = this.level - 1;idx >= 0;idx--){
            while (cur.nexts[idx] != null && cur.nexts[idx].value < value)
                cur = cur.nexts[idx];
        }
        Node target = cur.nexts[0];
        return target != null && target.value == value;
    }

    /**
     * <h3>插入结点</h3>
     */
    public void insert(int value){
        // 1. 记录每层要插入的结点的位置
        Node[] update = new Node[MAX_LEVEL];
        Arrays.fill(update, head);
        // 2. 查询需要插入结点的位置: 从上向下查询
        Node current = this.head;
        for (int idx = level - 1;idx >= 0;idx--){
            // 3. 如果下个结点为空或者下个结点的值大于插入值, 那么直接终止
            while (current.nexts[idx] != null && current.nexts[idx].value < value)
                current = current.nexts[idx];
            // 4. 记录这个不满足条件的前驱结点
            update[idx] = current;
        }
        // 5. 给结点生成层数
        int currentLevel = randomLevel();
        // 6. 更新最大层数
        this.level = Math.max(currentLevel, this.level);
        // 7. 插入结点
        Node node = new Node(value, currentLevel);
        // 8. 从下向上插入
        for(int idx = 0;idx < currentLevel;idx++){
            node.nexts[idx] = update[idx].nexts[idx];
            update[idx].nexts[idx] = node;
        }
    }

    /**
     * <h3>删除结点</h3>
     */
    public boolean erase(int value){
        // 1. 记录每层需要删除的结点的前驱结点
        Node[] update = new Node[MAX_LEVEL];
        // 2. 查找每层需要删除的结点的前驱
        Node cur = this.head;
        for (int idx = level - 1;idx >= 0;idx--){
            while (cur.nexts[idx] != null && cur.nexts[idx].value < value)
                cur = cur.nexts[idx];
            update[idx] = cur;
        }
        // 3. 记录最底层的下一个结点, 理论上下个结点的当前结点就是要删除的结点
        Node removed = cur.nexts[0];
        if (removed == null || removed.value != value)
            return false;
        // 4. 从下到上依次删除
        for (int idx = 0;idx < level;idx++){
            // 5. 如果前驱结点的下一个结点不是要删除的结点, 那么就可以直接跳出
            if (update[idx].nexts[idx] != removed)
                break;
            // 6. 删除结点
            update[idx].nexts[idx] = removed.nexts[idx];
        }
        // 7. 如果被删除的元素所在的层只有一个元素, 那么还要删除对应的层
        while (this.level > 1 && head.nexts[level - 1] == null)
            this.level--;
        return true;
    }

    /**
     * <h3>返回结点生成的最大层数</h3>
     */
    private int randomLevel(){
        int level = 1;
        while (Math.random() < SKIP_LIST_THRESH && level < MAX_LEVEL)
            level++;
        return level;
    }

}
