package chapter04;

/**
 * <h3>连通网络的操作次数</h3>
 */
public class MakeConnected {

    /**
     * <h3>并查集</h3>
     */
    public int makeConnected(int n, int[][] connections){
        int[] unionSet = getUnionSet(n);
        int repeat = 0, count = n;
        for (int[] connection : connections) {
            int from = connection[0], to = connection[1];
            if (find(unionSet, from) == find(unionSet, to) && ++repeat > 0){
                continue;
            }
            union(unionSet, from, to);
            count--;
        }
        return count - 1 <= repeat ? count - 1: -1;
    }

    public int[] getUnionSet(int length){
        int[] unionSet = new int[length];
        for (int idx = 0; idx < length; idx++) {
            unionSet[idx] = idx;
        }
        return unionSet;
    }

    public int find(int[] unionSet, int idx){
        if(unionSet[idx] == idx)
            return unionSet[idx];
        return unionSet[idx] = find(unionSet, unionSet[idx]);
    }

    public void union(int[] unionSet, int first, int second){
        unionSet[find(unionSet, second)] = find(unionSet, first);
    }

}
