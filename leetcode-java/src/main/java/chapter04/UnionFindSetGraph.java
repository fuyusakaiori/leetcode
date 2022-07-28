package chapter04;

import java.util.HashMap;
import java.util.Map;

/**
 * <h2>并查集图论题</h2>
 * <h3>1. 冗余连接</h3>
 * <h3>2. 除法求值</h3>
 * <h3>3. 判断二分图</h3>
 * <h3>4. 等式方程可满足性</h3>
 * <h3>注: 这些题目都是可以使用并查集完成的</h3>
 */
public class UnionFindSetGraph {

    /**
     * <h3>并查集模板</h3>
     */
    private static class UnionFindSet{
        private final int[] unionSet;

        public UnionFindSet(int length) {
            this.unionSet = new int[length];
            // 注: 将所有结点的根结点指向自己
            for (int index = 0;index < unionSet.length;index++){
                unionSet[index] = index;
            }
        }

        public int find(int index){
            if (this.unionSet[index] == index)
                return index;
            return this.unionSet[index] = find(unionSet[index]);
        }

        public void union(int first, int second){
            this.unionSet[find(first)] = this.unionSet[find(second)];
        }
    }

    /**
     * <h3>思路: 冗余连接</h3>
     */
    private static int[] findRedundantConnection(int[][] edges){
        UnionFindSet unionFindSet = new UnionFindSet(edges.length);
        for (int[] edge : edges) {
            int start = edge[0] - 1, end = edge[1] - 1;
            if (unionFindSet.find(start) == unionFindSet.find(end))
                return edge;
            unionFindSet.union(start, end);
        }
        return null;
    }

    /**
     * <h3>思路: 等式方程可满足性</h3>
     */
    private static boolean equationsPossible(String[] equations){
        Map<Character, Integer> conversion = convert(equations);
        UnionFindSet unionFindSet = new UnionFindSet(conversion.size());
        // 1. 遍历等式
        for(String equation : equations){
            if(equation.charAt(1) == '='){
                int first = conversion.get(equation.charAt(0));
                int second = conversion.get(equation.charAt(3));
                if(unionFindSet.find(first) != unionFindSet.find(second))
                    unionFindSet.union(first, second);
            }
        }
        // 2. 遍历不等式
        for(String equation : equations){
            if(equation.charAt(1) == '!'){
                int first = conversion.get(equation.charAt(0));
                int second = conversion.get(equation.charAt(3));
                if(unionFindSet.find(first) == unionFindSet.find(second))
                    return false;
            }
        }
        return true;
    }

    /**
     * <h3>1. 字符串图论最简单的做法还是将其转换为整数来处理</h3>
     * <h3>2. 这个题也可以利用字符的特性来做, 也就是可以采用数组来存储而不用哈希表</h3>
     */
    private static Map<Character, Integer> convert(String[] equations){
        int index = 0;
        Map<Character, Integer> conversion = new HashMap<>();
        for(String equation : equations){
            char first = equation.charAt(0), second = equation.charAt(3);
            if(!conversion.containsKey(first))
                conversion.put(first, index++);
            if(!conversion.containsKey(second))
                conversion.put(second, index++);
        }
        return conversion;
    }

    private static boolean isBipartite(int[][] graph){
        UnionFindSet unionFindSet = new UnionFindSet(graph.length);

        for (int first = 0;first < graph.length;first++){
            for (int second = 0; second < graph[first].length; second++){
                if (unionFindSet.find(first) == unionFindSet.find(graph[first][second]))
                    return false;
                // 注: 这里不是和起始结点合并, 是和第一个相邻接点合并, 相当于划分成了两部分, 所以上面才可以这么判断
                unionFindSet.union(graph[first][0], graph[first][second]);
            }
        }
        return true;
    }

}
