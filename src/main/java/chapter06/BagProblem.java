package chapter06;

/**
 * <h2>经典背包问题</h2>
 * <p>1. 给定两个数组, weights[] values[]; 第一个数组代表物品的重量, 第二个数组代表物品的价值</p>
 * <p>2. 现在给你一个背包, 请问要挑选哪些物品, 在不超过背包重量限制的情况下, 获得最大价值</p>
 */
public class BagProblem
{
    public static void main(String[] args)
    {

    }

    private static int bag(int[] weights, int[] values, int bag){

        return recursive(weights, values,0, 0, bag);
    }

    /**
     *
     * @return 返回值是价值不是重量
     */
    private static int recursive(int[] weights, int[] values, int index, int alreadyWeights, int bag){
        // 0. 物品重量已经大于背包限制了, 那么显然就是不可行的
        if (alreadyWeights > bag)
            return 0;
        // 1. 如果已经到达最后的位置, 那么就证明这种选择方式是可行的
        if (index == weights.length)
            return 0;
        // 2. 选择是否要当前这个物品
        return Math.max(recursive(weights, values, index + 1, alreadyWeights, bag),
                values[index] + recursive(weights, values, index + 1, alreadyWeights + weights[index], bag));
    }
}
