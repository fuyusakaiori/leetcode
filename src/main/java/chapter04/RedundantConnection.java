package chapter04;

/**
 * <h2>冗余连接</h2>
 * <h3>问题: 判断图是否存在环, 图本身是连通的</h3>
 * <h3>1. 拓扑排序</h3>
 * <h3>2. 深度遍历</h3>
 * <h3>3. 并查集</h3>
 * <h3>注: 连通图一定存在环吗? 存在环的图一定是连通图吗? </h3>
 * <h3>连通图必然存在环, 但是非连通图也是存在环的</h3>
 */
public class RedundantConnection {


    /**
     * <h3>思路: 并查集</h3>
     * <h3>注: 直接在原有的结构上进行处理</h3>
     */
    private static int[] findRedundantConnection1(int[][] graph){
        int[] unionSet = getUnionSet(graph);
        for (int[] edge : graph) {
            int start = edge[0] - 1, end = edge[1] - 1;
            if (find(unionSet, start) == find(unionSet, end))
                return edge;
            union(unionSet, start, end);
        }
        return null;
    }

    private static int[] getUnionSet(int[][] graph){
        int[] unionSet = new int[graph.length];
        // 注: 让所有结点全部指向自己
        for (int index = 0;index < unionSet.length;index++){
            unionSet[index] = index;
        }
        return unionSet;
    }

    private static int find(int[] unionSet, int index){
        // 1. 如果并查集中记录的结点和索引相同, 那么证明已经找到父结点
        if (unionSet[index] == index)
            return index;
        // 2. 如果记录的结点和索引不等, 那么继续向上查找; 最后将沿途所有结点的父结点都更新为最终找到的父结点
        return unionSet[index] = find(unionSet, unionSet[index]);
    }

    private static void union(int[] unionSet, int first, int second){
        // 注: 之前在查询的时候就已经将沿途所有结点父结点更新过了, 所以这里再次查询不会很慢
        unionSet[find(unionSet, first)] = find(unionSet, second);
    }

    /**
     * <h3>思路: 深度搜索</h3>
     * <h3>注: 这个方式存在问题, 如果有多个可以删除的边, 那么应该删除最后出现的边, 深度搜索做不到</h3>
     * <h3>注: 这个题无论采用邻接矩阵还是邻接表都是没有办法用深搜做到的</h3>
     */
    private static int[] findRedundantConnection2(int[][] edges){
        return dfs(getGraph(edges), new boolean[edges.length], 0);
    }

    /**
     * <h3>邻接矩阵存储</h3>
     */
    private static int[][] getGraph(int[][] edges){
        int[][] graph = new int[edges.length][edges.length];
        for(int[] edge : edges){
            graph[edge[0] - 1][edge[1] - 1] = 1;
        }
        return graph;
    }

    private static int[] dfs(int[][] graph, boolean[] visited, int current){
        visited[current] = true;
        for(int index = 0;index < graph[current].length;index++){
            if(graph[current][index] == 1){
                if(!visited[index])
                    return new int[]{current + 1, index + 1};
                int[] edge = dfs(graph, visited, index);
                if(edge != null)
                    return edge;
            }
        }
        return null;
    }

    /**
     * <h3>思路: 拓扑排序</h3>
     * <h3>注: 这个题是无向图, 所以严格意义上来说是没有办法用拓扑排序的, 但是我看见评论区有人这么做</h3>
     * <h3>注: 网上有人说直接计算结点的度数, 我感觉不是很对, 所以暂时先搁置在这里</h3>
     */
    private static int[] findRedundantConnection3(int[][] edges){
        return null;
    }
}
