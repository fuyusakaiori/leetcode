package chapter04;

/**
 * <h3>冗余连接</h3>
 * <h3>注: 这个题只能用并查集做</h3>
 */
public class RedundantConnection {

    public int[] findRedundantConnection(int[][] edges){
        // 1. 获取并查集
        int[] unionSet = getUnionSet(edges.length);
        // 2. 遍历所有边
        for (int[] edge : edges) {
            int from = edge[0], to = edge[1];
            // 3. 如果两个结点的父节点相同, 那么就证明合并后会形成环, 应该删除这条边
            if (find(unionSet, from) == find(unionSet, to))
                return edge;
            // 4. 如果两个结点的父节点不同, 那么合并
            union(unionSet, from, to);
        }
        return null;
    }


    public int[] getUnionSet(int length){
        // 1. 初始化并查集
        int[] unionSet = new int[length];
        // 2. 每个结点将自己作为父节点
        for (int idx = 0; idx < unionSet.length; idx++) {
            unionSet[idx] = idx;
        }
        return unionSet;
    }

    public int find(int[] unionSet, int idx){
        // 1. 如果父节点等于自己, 那么递归结束
        if (unionSet[idx] == idx)
            return idx;
        // 2. 如果父节点不等于自己, 那么持续递归
        return unionSet[idx] = find(unionSet, unionSet[idx]);
    }

    public void union(int[] unionSet, int first, int second){
        // 1. 找到两个结点的父节点后, 进行合并
        unionSet[find(unionSet, first)] = find(unionSet, second);
    }

}
