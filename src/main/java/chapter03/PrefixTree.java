package chapter03;


import utils.TreeNode;

/**
 * <p>实现前缀树</p>
 * <p>前缀树的目的</p>
 * <p>1. 检索前缀树中是否存在该字符串</p>
 * <p>2. 检索多少个字符串以某个字符串作为前缀</p>
 * <p>前缀树的操作</p>
 * <p>1. 向前缀树中添加字符串</p>
 * <p>2. 删除前缀树中的字符串</p>
 * <p>3. 检索前缀树中是否存在某个字符串</p>
 * <p>4. 检索前缀树中是否存在某个前缀</p>
 * <p>前缀树内容</p>
 * <p>1. 使用结点之间的边记录字符串每个字符的信息, 每个结点不不存储字符信息而是存储辅助信息</p>
 * <p>2. 从根结点到结尾结点的路径, 表达的就是整个字符串的信息, 这里的尾结点不一定就是叶子结点</p>
 */
public class PrefixTree
{
    // 前缀树的结点不同于普通树的结点, 需要存储的信息不同
    private class TrieNode{
        // 经过该结点的次数
        private int pass;
        // 以该结点结尾的次数
        private int end;
        // 如果每条边保存的信息种类超过无法估计, 那么可以替换成其余数据结构, 比如哈希表之类的
        private TrieNode[] nexts;

        public TrieNode() {
            // 英文字符合计只存在26个, 也就是从这个结点出发的路径不可能超过26条
            this.nexts = new TrieNode[26];
        }
    }

    // 每个前缀树默认都会存在一个结点
    private TrieNode root;

    /**
     * 向前缀树中添加字符串
     * @param word 添加的字符串
     */
    public void insert(String word){
        if (word == null)
            return;
        // 1. 字符串转换成字符数组（实际上不转换也是可以的）
        char[] chars = word.toCharArray();
        // 2. 默认根结点会经过一次
        TrieNode node = root;
        node.pass++;
        // 3. 开始遍历字符串
        int index = 0;
        for (int i = 0; i < chars.length; i++) {
            // 4. 计算每个字符对应的数组索引是多少
            index = chars[i] - 'a';
            // 5. 检查当前结点有没有去往该字符的路径
            // 5.1 如果没有就新建该路径
            if (node.nexts[index] == null)
                node.nexts[index] = new TrieNode();
            // 5.2 如果已经存在, 那么就在该路径连接的下一个结点上, 使经过的次数增加
            node = node.nexts[index];
            node.pass++;
        }
        // 6. 遍历结束之后, 最后一个结点的位置需要将结尾次数增加
        node.end++;
    }

    /**
     * 删除前缀树中某个字符串
     * @param word 需要删除的字符串
     */
    public void delete(String word){
        // 1. 空字符串没有删除的必要
        if (word == null)
            return;
        // 2. 要删除的字符串必须是存在的, 否则直接返回
        if (search(word) == 0)
            return;
        char[] chars = word.toCharArray();
        TrieNode node = root;
        // 3. 字符串被删除, 相应的经过次数也会减少
        node.pass--;
        int index = 0;
        for (int i = 0; i < chars.length; i++) {
            index = chars[i] - 'a';
            // 4. 判断下一个结点是否会因为经过次数减少而等于 0
            // 4.1 如果经过次数减少到 0, 那么之后所有结点全都可以直接抛弃, 这样就删除了该字符串
            // 4.2 如果经过次数没有减少到 0, 那么可以继续
            if (--node.nexts[index].pass == 0){
                // 注: 这里只有带有自动垃圾回收机制的语言可以这么写, GC 会自动回收的
                node.nexts[index] = null;
                return;
            }
            node = node.nexts[index];
        }
        // 5. 正常遍历结束的才会在最后将末尾次数减一
        node.end--;
    }

    /**
     * 检索向前缀树中添加过几次这个字符串, 比只检查是否添加过更好
     * @param word 检索的字符串
     * @return 出现的次数
     */
    public int search(String word){
        if (word == null)
            return 0;
        char[] chars = word.toCharArray();
        TrieNode node = root;
        int index = 0;
        for (int i = 0; i < chars.length; i++) {
            index = chars[i] - 'a';
            // 如果该结点所关联的路径上不存在这个字符, 那么可以直接断定不存在这个字符串
            if (node.nexts[index] == null)
                return 0;
            // 如果存在这个字符, 那么继续向后遍历
            node = node.nexts[index];
        }
        // 如果字符串遍历结束, 发现最后一个结点的结尾次数为 0, 那么也可以证明这个字符串值不存在的
        return node.end;
    }

    /**
     * 检索前缀树中存在多少个以 prefix 为前缀的字符串
     * @param prefix 字符串前缀
     * @return 以 prefix 作为前缀的字符串个数
     */
    public int startWith(String prefix){
        if (prefix == null)
            return 0;
        char[] chars = prefix.toCharArray();
        TrieNode node = root;
        int index = 0;
        for (int i = 0; i < chars.length; i++) {
            index = chars[i] - 'a';
            if (node.nexts[index] == null)
                return 0;
            node = node.nexts[index];
        }
        return node.pass;
    }

}
