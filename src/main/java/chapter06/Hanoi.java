package chapter06;

/**
 * <h2>汉诺塔问题</h2>
 * <p>这章中的所有问题都是基于递归解决的, 也就是完全依赖暴力递归的方式解决</p>
 * <p>二叉树的题目递归套路都是固定的, 而其他的题目虽然也是采用递归, 但是肯定没有固定的套路</p>
 * <p>大致套路: 1.确定大致的规则 2.确定 base case（虽然没啥用）</p>
 * <p>问题描述: 这个问题的描述就不用再多介绍了, 相信上过大学的肯定听过这个问题</p>
 * <p>进阶问题: 采用时间复杂度为 O(N), 空间复杂度为 O(1) 的解法实现</p>
 */
public class Hanoi
{
    /**
     * <p>总结: 这应该是一种单调递归的方式, 持续向某一个方向递归</p>
     */
    public static void main(String[] args)
    {
        hanoi(3, "A", "B", "C");
    }

    /**
     * <p>核心思路</p>
     * <p>如果只有两个圆盘, 那么显然只需要将最上层的移动到最右的柱子中去</p>
     * <p>然后将最下层的圆盘移动最中间的柱子中去, 最后再将最右的圆盘移动到最中间去</p>
     * <p>基于这种思路, 我们可以将N个圆盘也看做两个部分, 上面N-1个看做一个圆盘, 最底层的一个看做一个圆盘</p>
     * <p>显然, 这样就可以很容易按照两个圆盘的思路去移动</p>
     * @param numbers 需要移动的盘子总数量
     * @param end 最底层的盘子需要移动的位置
     * @param other N-1个盘子需要移动到的位置
     */
    private static void hanoi(int numbers, String from, String end, String other){
        //================base case======================
        if (numbers == 1){
            System.out.printf("第 %d 个圆盘从 %s 移动到 %s \n", numbers, from, end);
            return;
        }
        //================大致规则确定=====================
        // N-1 个圆盘从 from->other => other 就是 end
        hanoi(numbers - 1, from, other, end);
        // 第 N 个圆盘从 from->end => 直接输出就行
        System.out.printf("第 %d 个圆盘从 %s 移动到 %s \n", numbers, from, end);
        // N-1 个圆盘再次从 other->end => other 就是起始
        hanoi(numbers - 1, other, end, from);
    }

    /**
     * 采用时间复杂度为 O(N), 空间复杂度为 O(1)
     */
    private static void hanoiUnRecursive(int numbers, String from, String end, String other){

    }
}
