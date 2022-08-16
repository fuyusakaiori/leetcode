package chapter04;

/**
 * <h3>等式方程可满足性</h3>
 */
public class EquationsPossible {

    /**
     * <h3>并查集</h3>
     * <h3>注: 字符串转换成图</h3>
     */
    private static boolean equationsPossible(String[] equations){
        int[] unionSet = getUnionSet(26);
        // 1. 遍历所有等式: 合并等式
        for (String equation : equations) {
            if (equation.charAt(1) == '='){
                int from = equation.charAt(0) - 'a';
                int to = equation.charAt(3) - 'a';
                if (find(unionSet, from) != find(unionSet, to))
                    union(unionSet, from, to);
            }
        }
        // 2. 遍历所有不等式: 检查等式是否冲突
        for (String equation : equations) {
            if (equation.charAt(1) == '!'){
                int from = equation.charAt(0) - 'a';
                int to = equation.charAt(3) - 'a';
                if (find(unionSet, from) == find(unionSet, to))
                    return false;
            }
        }
        return true;
    }

    private static int[] getUnionSet(int length){
        int[] unionSet = new int[length];
        for (int idx = 0; idx < unionSet.length; idx++) {
            unionSet[idx] = idx;
        }
        return unionSet;
    }

    private static int find(int[] unionSet, int idx){
        if (unionSet[idx] == idx)
            return idx;
        return unionSet[idx] = find(unionSet, unionSet[idx]);
    }

    private static void union(int[] unionSet, int first, int second){
        unionSet[find(unionSet, second)] = find(unionSet, first);
    }


}
