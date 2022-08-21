package chapter99.netease;

/**
 * <h3>优美的长城</h3>
 * <h3>模拟题</h3>
 */
public class GreatWall {

    /**
     * <h3>问题描述</h3>
     * <h3>1. 有一个数组, 如果所有元素的左右两侧的值都相等, 那么就是长城</h3>
     * <h3>2. 现在可以修改元素的值, 每次可以给元素值 +1, 每次 +1 都视为一次操作</h3>
     * <h3>3. 问最少多少次操作可以将不是长城的数组变为长城</h3>
     * <h3>所有的奇数项都应该一样, 所有的偶数项也都一样</h3>
     */
    private static int makeGreatWall(int[] walls){
        // 1. 统计奇数项最大值: 每个奇数都会变为最大奇数
        // 2. 统计偶数项最大值: 每个偶数都会变为最大偶数
        int oddMax = 0, evenMax = 0;
        for (int idx = 0; idx < walls.length; idx++) {
            if (idx % 2 != 0)
                oddMax = Math.max(oddMax, walls[idx]);
            if (idx % 2 == 0)
                evenMax = Math.max(evenMax, walls[idx]);
        }
        // 3. 偶数增加到偶数最大值, 奇数增加到奇数最大值, 就是最少操作次数
        int cnt = 0;
        for (int idx = 0; idx < walls.length; idx++) {
            if (idx % 2 != 0)
                cnt += oddMax - walls[idx];
            if (idx % 2 == 0)
                cnt += evenMax - walls[idx];
        }
        return cnt;
    }

}
