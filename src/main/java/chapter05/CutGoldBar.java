package chapter05;

import java.util.PriorityQueue;

/**
 * <h2>切金条问题</h2>
 * <p>1. 一群人想要切分一块金条, 每次切分金条花费的费用就是金条的长度数值</p>
 * <p>2. 比如要切分长度为 20 的金条, 无论切分成多大, 都需要花费 20 块</p>
 * <p>3. 问, 一群人如何切分金条, 花费最少</p>
 */
public class CutGoldBar
{

    /**
     * 贪心策略最常使用排序和堆结构完成
     */
    public static void main(String[] args)
    {
        System.out.println(minCost(new int[]{10, 20, 30}));
    }


    /**
     * <p>1. 采用哈夫曼的方式去计算, 感觉是自底向上的</p>
     * <p>2. 实际上你可以自顶向下想, 你每次都去按照最长的去切分, 这样就能保证剩下的比较短</p>
     * @param targets 每个人要求的金条长度
     * @return 费用
     */
    private static int minCost(int[] targets){
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        for (int target : targets) {
            heap.add(target);
        }
        int cost = 0;
        while (heap.size() > 1){
            cost += heap.poll() + heap.poll();
            heap.add(cost);
        }
        return cost;
    }
}
