package chapter05;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * <h2>最大利润</h2>
 * <p>1. costs[i] 表示项目的花费</p>
 * <p>2. profits[i] 表示项目的利润</p>
 * <p>3. k 表示你最多可以完成的项目数</p>
 * <p>4. m 表示你的起始资金</p>
 * <p> 求最后获取的最大钱数</p>
 */
public class MaxProfit
{
    private static class Project{
        private int cost;
        private int profit;
        public Project(int cost, int profit)
        {
            this.cost = cost;
            this.profit = profit;
        }
    }

    private static class ProjectComparator implements Comparator<Project>{
        @Override
        public int compare(Project o1, Project o2) {
            return o2.profit - o1.profit;
        }
    }


    public static void main(String[] args)
    {

    }

    /**
     * <p>贪心策略: </p>
     * <p>1. 那么显然每次去在可以完成的项目中, 挑选最大利润就行</p>
     * <p>2. 利用两个堆完成, 非常巧妙, 当然也可以暴力尝试完成</p>
     * @param numbers 可以最多完成的项目数
     * @param money 初始资金
     * @param costs 每个项目的花费
     * @param profits 每个项目的利润
     */
    private static int maxProfit(int numbers, int money, int[] costs, int[] profits){
        // 0. 按照最大花费形成的小顶堆
        PriorityQueue<Project> maxCosts = new PriorityQueue<>();
        // 1. 按照最大利润组织的大顶堆, 每次挑选利润最大的项目做
        PriorityQueue<Project> maxProfits = new PriorityQueue<>(new ProjectComparator());
        // 2. 所有项目进入小顶堆中, 这样就能够很快根据自己的花费和项目的花费挑选可以完成的项目
        for (int i = 0; i < costs.length; i++) {
            maxCosts.add(new Project(costs[i], profits[i]));
        }
        // 3. 开始完成项目, 最多完成 k 个
        for (int i = 0;i < numbers;i++){
            // 4. 检查小顶堆中存在多少个能完成的项目
            while (!maxCosts.isEmpty() && maxCosts.peek().cost <= money)
                maxProfits.add(maxCosts.poll());
            // 5. 这样大顶堆的堆顶元素就是利润最大的了
            if (maxProfits.isEmpty())
                return money;
            money += maxProfits.poll().profit;

        }
        return money;
    }

    /**
     * 暴力递归完成
     */
    private static int process(int numbers, int money, int[] costs, int[] profits){

        return 0;
    }
}
