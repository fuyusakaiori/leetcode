package chapter08;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * <h2>单词搜索问题</h2>
 * <h3>1. 单词搜索</h3>
 * <h3>2. 单词搜索 II</h3>
 */
public class WordSearch {

    /**
     * <h3>思路: 单词搜索</h3>
     * <h3>注: 这个题的思路非常简单, 但是有很多的细节需要注意, 否则答案就是不对的</h3>
     */
    private static boolean exist(char[][] board, String word){
        // 1. 分别选择矩阵中的每个元素作为起始元素来深度遍历, 不要在回溯中进行选择
        for (int row = 0;row < board.length;row++){
            for (int col = 0;col < board[row].length;col++){
                if (dfs(board, row, col, word, 0))
                    return true;
            }
        }
        return false;
    }

    private static boolean dfs(char[][] board, int r, int c, String word, int index){
        // 2. 矩阵的位置和字符串的指针可能同时越界, 所以必须将字符串的指针放在前面, 否则结果就是错的
        if (index == word.length())
            return true;
        // 3. 矩阵的位置越界那么直接返回假
        if (r < 0 || c < 0 || r > board.length - 1 || c > board[r].length - 1)
            return false;
        // 4. 如果相等, 那么就可以选择; 如果不等, 那么并不能直接返回假
        // 5. 这是因为如果第一个元素就不等, 那么还是应该继续向下选择的, 如果直接返回那么结果就会有问题, 这也是为什么不能在回溯中选择的原因
        if (board[r][c] != word.charAt(index))
            return false;
        // 6. 原地修改的目的就是为了标记这个字符已经被选择, 避免重复选择, 如果在回溯中选择, 那么这个标记的策略就会失效
        board[r][c] += 128;
        boolean top = dfs(board, r - 1, c, word, index + 1);
        boolean down = dfs(board, r + 1, c, word, index + 1);
        boolean left = dfs(board, r, c - 1, word, index + 1);
        boolean right = dfs(board, r, c + 1, word, index + 1);
        board[r][c] -= 128;
        return top || right || down || left;
    }

    /**
     * <h3>思路: 单词搜索 II</h3>
     * <h3>1. 暴力回溯</h3>
     * <h3>2. 暴力回溯 + 前缀树剪枝</h3>
     * <h3>注: 单词搜索 II 显然可以利用单词搜索的思路来做, 但是也明显能看出来时间复杂度非常高</h3>
     */
    private static List<String> findWords(char[][] board, String[] words){
        Set<String> set = new HashSet<>();
        // 1. 初始化前缀树
        for (String word : words){
            insert(word);
        }
        // 2. 遍历矩阵, 只让符合条件的作为起点
        for (int row = 0;row < board.length;row++){
            for (int col = 0;col < board[row].length;col++){
                dfs(board, row, col, root, set);
            }
        }
        return new LinkedList<>(set);
    }

    /**
     * <h3>前缀树结点</h3>
     */
    private static class TrieNode{
        // 注: 这里必须要保存字符串, 否则不方便在回溯的过程中进行遍历
        private String str;
        private int pass;
        private int end;
        private final TrieNode[] nexts;

        public TrieNode() {
            this.nexts = new TrieNode[26];
        }
    }

    private static final TrieNode root = new TrieNode();

    /**
     * <h3>注: 这个题只需要插入字符串这个方法</h3>
     */
    private static void insert(String word){
        char[] chars = word.toCharArray();
        TrieNode node = root;
        node.pass++;
        for (char ch : chars) {
            int index = ch - 'a';
            if (node.nexts[index] == null)
                node.nexts[index] = new TrieNode();
            node = node.nexts[index];
            node.pass++;
        }
        node.end++;
        node.str = word;
    }

    /**
     * <h3>前缀树优化核心思路: </h3>
     * <h3>1. 如果对每个单词都在矩阵中采用回溯的方法进行判断, 那么就会导致矩阵无论如何都会被遍历 N 次</h3>
     * <h3>2. 那么能否仅遍历一次矩阵就可以判断到所有的单词呢? 实际上是可以的, 只需要反过来考虑就行</h3>
     * <h3>3. 暴力回溯本质是将矩阵当做集合, 而前缀树优化本质是将所有的单词当做集合, 在前缀树中进行搜索</h3>
     * <h3>4. 对矩阵进行遍历, 只要这个字符满足前缀树的起点, 那么就可以深度遍历, 不满足就直接返回</h3>
     */
    private static void dfs(char[][] board, int r, int c, TrieNode node, Set<String> words){
        // 1. 防止矩阵的位置越界, 所以需要提前进行判断
        if (r < 0 || c < 0 || r > board.length - 1 || c > board[r].length - 1)
            return;
        // 2. 如果这个位置已经被访问过, 那么直接返回
        if (board[r][c] > 'z' || board[r][c] < 'a')
            return;
        // 3. 获取前缀树下一个结点
        TrieNode cur = node.nexts[board[r][c] - 'a'];
        // 4. 如果这个结点为空, 那么证明前缀树中根本没有这个字符, 所以直接返回
        if (cur == null)
            return;
        // 5. 如果这个结点不为空, 并且可以作为结点, 那么证明这个字符串是存在的, 要将字符串添加进去
        if (cur.end != 0)
            words.add(cur.str);
        board[r][c] += 128;
        dfs(board, r - 1, c, cur, words);
        dfs(board, r + 1, c, cur, words);
        dfs(board, r, c - 1, cur, words);
        dfs(board, r, c + 1, cur, words);
        board[r][c] -= 128;
    }


}
